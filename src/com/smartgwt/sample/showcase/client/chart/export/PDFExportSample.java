package com.smartgwt.sample.showcase.client.chart.export;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.chart.ChartSamplePanelFactory;

public class PDFExportSample extends ShowcasePanel {

    private static final String DESCRIPTION = "Screens can be exported as PDF (Portable Document Format) files.  This includes " +
            "FacetCharts and other widgets based on DrawPane (such as Gauge)." +
            "<p>" +
            "Press the \"View as PDF\" button below to view the exported version of the grid and chart " +
            "below in a new browser window.  Press \"Download as PDF\" to save the generated PDF to " +
            "disk." +
            "<p>" +
            "Note that the grid is editable and the chart will react to changes in the grid, so you " +
            "can view or download several different PDFs showing the grid and chart.";

    public static class Factory extends ChartSamplePanelFactory {
        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "chartPDFExport";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new PDFExportSample();
        }
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    @Override
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

        char maxProduct = 'E';
        List<ListGridRecord> salesData = new ArrayList<ListGridRecord>();
        for (char prod = 'A'; prod <= maxProduct; prod++) {
            ListGridRecord rec = new ListGridRecord();
            rec.setAttribute("product", "Product " + prod);
            long minSales = Math.round(Math.random() * 8000) + 2000;
            long maxVariance = minSales / 3;
            for (ListGridField field : fields) {
                rec.setAttribute(field.getName(), Math.round(Math.random() * maxVariance) + minSales);
            }
            salesData.add(rec);
        }

        final ListGridField field = new ListGridField("product", "Products");
        field.setCanEdit(Boolean.FALSE);
        fields.add(0, field);

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
        final FacetChart chartProperties = new FacetChart();
        chartProperties.setHeight100();
        grid.setAutoChildProperties("chart", chartProperties);
        grid.setLeaveScrollbarGap(false);
        grid.setAlternateRecordStyles(Boolean.FALSE);
        grid.setWidth100();
        grid.setAutoFitData(Autofit.VERTICAL);
        grid.setCanEdit(Boolean.TRUE);
        grid.setEditEvent(ListGridEditEvent.CLICK);
        grid.setFields(fields.toArray(new ListGridField[0]));
        grid.setData(salesData.toArray(new ListGridRecord[0]));
        grid.setChartType(ChartType.AREA);

        final FacetChart chart = grid.chartData("product");
        grid.addEditCompleteHandler(new EditCompleteHandler() {
            public void onEditComplete(EditCompleteEvent event) {
                chart.setData(grid.getRecords());
            }
        });

        final VLayout theLayout = new VLayout();

        final IButton viewAsPDFButton = new IButton("View as PDF");
        viewAsPDFButton.setWidth("50%");
        viewAsPDFButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final DSRequest requestProperties = new DSRequest();
                requestProperties.setExportDisplay(ExportDisplay.WINDOW);
                RPCManager.exportContent(theLayout);
            }
        });
        final IButton downloadAsPDFButton = new IButton("Download as PDF");
        downloadAsPDFButton.setWidth("50%");
        downloadAsPDFButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final DSRequest requestProperties = new DSRequest();
                requestProperties.setExportDisplay(ExportDisplay.DOWNLOAD);
                RPCManager.exportContent(theLayout);
            }
        });

        final HLayout buttonsLayout = new HLayout();
        buttonsLayout.setMargin(5);
        buttonsLayout.setWidth100();
        buttonsLayout.setMembersMargin(20);
        buttonsLayout.addMember(viewAsPDFButton);
        if (!Showcase.isIOs()) {
            buttonsLayout.addMember(downloadAsPDFButton);
        }

        theLayout.setWidth100();
        theLayout.setHeight(600);
        theLayout.setMembersMargin(20);
        theLayout.addMember(grid);
        theLayout.addMember(chart);
        theLayout.addMember(buttonsLayout);
        return theLayout;
    }
}
