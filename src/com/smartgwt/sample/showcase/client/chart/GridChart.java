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
package com.smartgwt.sample.showcase.client.chart;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class GridChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "<p>Data loaded into a ListGrid can be charted with a single API call.</p>" +
            "<p>Use the \"Chart Type\" selector to see the same data rendered by multiple different " +
            "chart types.  Right-click on the chart to change the way data is visualized.</p>" +
            "Edit the data in the grid to have the chart regenerated automatically.";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "gridCharting";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new GridChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final List<ListGridField> fields = new ArrayList<ListGridField>();
        fields.add(new ListGridField ("Jan", "January"));
        fields.add(new ListGridField ("Feb", "February"));
        fields.add(new ListGridField ("Mar", "March"));
        fields.add(new ListGridField ("Apr", "April"));
        fields.add(new ListGridField ("May", "May"));
        fields.add(new ListGridField ("Jun", "June"));
        fields.add(new ListGridField ("Jul", "July"));
        fields.add(new ListGridField ("Aug", "August"));
        fields.add(new ListGridField ("Sep", "September"));
        fields.add(new ListGridField ("Oct", "October"));
        fields.add(new ListGridField ("Nov", "November"));
        fields.add(new ListGridField ("Dec", "December"));

        // Creating product sales
        char maxProduct = 'E';
        List<ListGridRecord> salesData = new ArrayList<ListGridRecord>();
        for (char prod = 'A'; prod <= maxProduct; prod++) {
            ListGridRecord rec = new ListGridRecord();
            rec.setAttribute("product", "Product " + prod); // Product name
            long minSales = Math.round(Math.random() * 8000) + 2000; // 2k-10k
            long maxVariance = minSales / 3; // up to 33% of min value for this product
            for (ListGridField field : fields) {
                rec.setAttribute(field.getName(), Math.round(Math.random() * maxVariance) + minSales);
            }
            salesData.add(rec);
        }

        // Creating product name field
        ListGridField field = new ListGridField("product", "Products");
        field.setCanEdit(Boolean.FALSE);
        fields.add(0, field);

        // Creating grid and overriding getCellStyle() to use custom styling for product field
        // Use Dark
        final ListGrid grid = new ListGrid() {
            @Override
            protected String getCellStyle (ListGridRecord record, int rowNum, int colNum) {
                if ("product".equals(getFieldName(colNum))) {
                    return super.getCellStyle(record, rowNum, colNum) + "Dark";
                } else {
                    return super.getCellStyle(record, rowNum, colNum);
                }
            }
        };
        grid.setAlternateRecordStyles(Boolean.FALSE);
        grid.setHeight(90);
        grid.setCanEdit(Boolean.TRUE);
        grid.setEditEvent(ListGridEditEvent.CLICK);
        grid.setFields(fields.toArray(new ListGridField[0]));
        grid.setData(salesData.toArray(new ListGridRecord[0]));
        grid.setChartType(ChartType.AREA);

        // Creating chart
        final FacetChart chart = grid.chartData("product");
        chart.setHeight("*");
        grid.addEditCompleteHandler(new EditCompleteHandler() {
            public void onEditComplete(EditCompleteEvent event) {
                // Updating chart data
                chart.setData(grid.getRecords());
            }
        });

        final DynamicForm chartSelector = new DynamicForm();
        final SelectItem chartType = new SelectItem("chartType", "Chart Type");
        chartType.setValueMap(
                ChartType.AREA.getValue(),
                ChartType.BAR.getValue(),
                ChartType.COLUMN.getValue(),
                ChartType.DOUGHNUT.getValue(),
                ChartType.LINE.getValue(),
                ChartType.PIE.getValue(),
                ChartType.RADAR.getValue());
        chartType.setDefaultToFirstOption(true);
        chartType.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                String selectedChartType = chartType.getValueAsString();
                if (ChartType.AREA.getValue().equals(selectedChartType)) {
                    chart.setChartType(ChartType.AREA);
                } else if (ChartType.BAR.getValue().equals(selectedChartType)) {
                    chart.setChartType(ChartType.BAR);
                } else if (ChartType.COLUMN.getValue().equals(selectedChartType)) {
                    chart.setChartType(ChartType.COLUMN);
                } else if (ChartType.DOUGHNUT.getValue().equals(selectedChartType)) {
                    chart.setChartType(ChartType.DOUGHNUT);
                } else if (ChartType.LINE.getValue().equals(selectedChartType)) {
                    chart.setChartType(ChartType.LINE);
                } else if (ChartType.PIE.getValue().equals(selectedChartType)) {
                    chart.setChartType(ChartType.PIE);
                } else if (ChartType.RADAR.getValue().equals(selectedChartType)) {
                    chart.setChartType(ChartType.RADAR);
                }
            }
        });
        chartSelector.setFields(chartType);

        VLayout layout = new VLayout(5);
        layout.setHeight(350);
        layout.addMember(chartSelector);
        layout.addMember(grid);
        layout.addMember(chart);

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

}
