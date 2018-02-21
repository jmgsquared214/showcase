/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */
package com.smartgwt.sample.showcase.client.chart.zoom;

import java.util.Arrays;
import java.util.Comparator;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.LabelCollapseMode;
import com.smartgwt.client.types.LabelRotationMode;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.events.MovedEvent;
import com.smartgwt.client.widgets.events.MovedHandler;
import com.smartgwt.client.widgets.events.ParentMovedHandler;
import com.smartgwt.client.widgets.events.ParentMovedEvent;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.chart.ChartSamplePanelFactory;

public class StockPricesZoom extends ShowcasePanel {

    private static final String DESCRIPTION = "<p>This example shows historical pricing data for several well-known stocks. The miniature \"zoom chart\" (underneath the main chart) shows the complete dataset and the main chart shows a subset of this range in more detail.</p>"+
                                              "<p>Use the zoom chart to choose a visible range in the main chart, either by dragging the scrollbar or by dragging the range boundaries.</p>" +
                                              "<p>Note how the main chart will intelligently use different labels for the horizontal axis depending on how much data is shown - years, months or days may be labeled depending on how deeply you zoom.</p>";


    private static final String LOADING_MESSAGE = "Loading data ...";

    private static final String ERROR_MESSAGE =
        "Data is not currently available.  Please make sure that you are connected " +
        "to the Internet and then refresh the sample to try again.";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "stockPricesZoom";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new StockPricesZoom();
        }
    }

    private FacetChart stockChart;

    private SelectItem symbolItem;

    private StockPricesDataSource stockDs;

    private Label label;

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {

        DataSource nasdaqSymbols = new StockSymbolDataSource();
        stockDs = new StockPricesDataSource();

        stockChart = new FacetChart();
        stockChart.setID("stockChart");
        stockChart.setAutoDraw(false);
        stockChart.setShowTitle(false);
        stockChart.setShowDataAxisLabel(false);
        stockChart.setCanZoom(true);

        stockChart.setAllowedChartTypes(new ChartType[] {ChartType.AREA, ChartType.LINE});
        stockChart.setChartType(ChartType.AREA);

        stockChart.setFacets(new Facet("date", "Day"));
        stockChart.setValueProperty("close");
        stockChart.setLabelCollapseMode(LabelCollapseMode.TIME);
        stockChart.setMinLabelGap(4);
        stockChart.setRotateLabels(LabelRotationMode.NEVER);

        // Add a resized handler to keep the label, if any, in the center
        // of the chart.
        stockChart.addResizedHandler(new ResizedHandler() {
                public void onResized(ResizedEvent event) {
                    StockPricesZoom.this.centerLabel();
                }
            });
        stockChart.addMovedHandler(new MovedHandler() {
                public void onMoved(MovedEvent event) {
                    StockPricesZoom.this.centerLabel();
                }
            });
        stockChart.addParentMovedHandler(new ParentMovedHandler() {
                public void onParentMoved(ParentMovedEvent event) {
                    StockPricesZoom.this.centerLabel();
                }
            });

        DynamicForm symbolForm = new DynamicForm();
        symbolForm.setNumCols(8);
        symbolItem = new SelectItem("symbol", "Stock Symbol");
        symbolItem.setCanEdit(false);
        symbolItem.setAutoFetchData(false);
        symbolItem.setOptionDataSource(nasdaqSymbols);
        symbolItem.setDefaultValue("AAPL");
        symbolItem.setPickListWidth(450);
        symbolItem.setPickListFields(new ListGridField("symbol"), new ListGridField("name"));
        symbolItem.setDisplayField("name");
        symbolItem.setValueField("symbol");
        symbolItem.setSortField("symbol");
        symbolItem.setInitHandler(new FormItemInitHandler() {
            @Override
            public void onInit(FormItem item) {
                symbolItem.fetchData(new DSCallback() {
                    @Override
                    public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                        if (dsResponse.getStatus() < 0) return;

                        symbolItem.setCanEdit(true);
                        updateData();
                    }
                });
            }
        });
        symbolItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                StockPricesZoom.this.updateData();
            }
        });

        symbolForm.setItems(symbolItem);

        VLayout layout = new VLayout(20);
        layout.setAutoDraw(false);
        layout.setWidth100();
        layout.setHeight100();
        layout.setMargin(5);
        layout.setMembers(symbolForm, stockChart);
        return layout;
    }

    private void centerLabel() {
        if (label != null) {
            String widthStr = stockChart.getWidthAsString();
            String heightStr = stockChart.getHeightAsString();

            float widthRatio = 0.6f, heightRatio = 0.6f;
            try {
                int width = Integer.parseInt(widthStr, 10),
                    height = Integer.parseInt(heightStr, 10);

                label.setTop(Math.round((1 - heightRatio) / 2 * height));
                label.setLeft(Math.round((1 - widthRatio) / 2 * width));
                label.setHeight(Math.round(heightRatio * height));
                label.setWidth(Math.round(widthRatio * width));
            } catch (NumberFormatException e) {}
        }
    }

    private void showMessage(String message, boolean alignCenter) {
        Label label = this.label;
        if (label == null) {
            label = this.label = new Label();
            label.setAutoDraw(true);
            label.setContents("");
            label.setValign(VerticalAlignment.CENTER);
            label.setWrap(true);
            label.setShowEdges(false);

            stockChart.addChild(label);
        }

        label.setContents(message);

        Alignment alignment = (alignCenter ? Alignment.CENTER : Alignment.LEFT);
        label.setAlign(alignment);
        centerLabel();
        label.show();
    }

    private void hideMessage() {
        if (this.label != null) {
            this.label.hide();
        }
    }

    protected void updateData() {
	    stockChart.destroyItems();
        this.showMessage(LOADING_MESSAGE, true);

        final String symbol = symbolItem.getValueAsString();
        final String name = symbolItem.getDisplayValue();
        if (symbol == null || "".equals(symbol)) {
            stockChart.setData(new Record[0]);
            return;
        }

        stockDs.fetchData(new Criteria("symbol", symbol), new DSCallback() {
            @Override
            public void execute(DSResponse response, Object rawData, DSRequest request) {
                Record[] data = response.getData();
                if (data != null && data.length > 0) {
                    StockPricesZoom.this.hideMessage();
                    stockChart.setTitle(name);
                    Arrays.sort(data, new Comparator<Record>() {
                        @Override
                        public int compare(Record lhs, Record rhs) {
                            return lhs.getAttributeAsDate("date").compareTo(rhs.getAttributeAsDate("date"));
                        }
                    });
                    stockChart.setData(data);
                } else {
                    StockPricesZoom.this.showMessage(ERROR_MESSAGE, false);
                    stockChart.setData(new Record[0]);
                }
            }
        });
	}

	@Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("StockPricesDataSource.java", JAVA, "source/chart/zoom/StockPricesDataSource.java.html", false),
            new SourceEntity("StockSymbolDataSource.java", JAVA, "source/chart/zoom/StockSymbolDataSource.java.html", false),
        };
    }
}
