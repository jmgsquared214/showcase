package com.smartgwt.sample.showcase.client.chart.export;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportImageFormat;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.drawing.DataURLCallback;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.chart.ChartSamplePanelFactory;
import com.smartgwt.sample.showcase.client.chart.MultiSeriesChartData;

public class ImageExportSample extends ShowcasePanel {

    private static final String DESCRIPTION =
            "FacetCharts and other widgets based on DrawPane (such as Gauge) can be exported to PNG and other " +
            "image formats." +
            "<p>" +
            "Click the \"Download as Image\" button to save an image of the chart.  You can drag resize the " +
            "chart or right-click it to change how it displays so that you can obtain multiple " +
            "different images of the chart." +
            "<p>" +
            "Click the \"Get Data URL\" button to display a PNG snapshot of the chart in the blue " +
            "bordered area to the right of the chart.  In most browsers, right-clicking the PNG will " +
            "then provide options to save it.  Note that this particular feature cannot be supported " +
            "for Internet Explorer 7 and earlier.";

    public static class Factory extends ChartSamplePanelFactory {
        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "chartImageExport";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new ImageExportSample();
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

    public static native int getBrowserVersion() /*-{
        return $wnd.isc.Browser.version;
    }-*/;

    @Override
    public Canvas getViewPanel() {
        final boolean browserSupportsDataURLs = !(SC.isIE() && getBrowserVersion() < 8);

        final FacetChart multiSeriesChart = new FacetChart();
        multiSeriesChart.setTitle("Revenue");
        multiSeriesChart.setFacets(new Facet("time", "Period"), new Facet("region", "Region"));
        multiSeriesChart.setMinWidth(360);
        multiSeriesChart.setMaxWidth(860);
        multiSeriesChart.setMinHeight(180);
        multiSeriesChart.setMaxHeight(670);
        multiSeriesChart.setData(MultiSeriesChartData.getData());
        multiSeriesChart.setValueProperty("value");
        multiSeriesChart.setChartType(ChartType.AREA);
        multiSeriesChart.setCanDragResize(true);
        multiSeriesChart.setDragResizeAppearance(DragAppearance.OUTLINE);
        multiSeriesChart.setShowEdges(true);

        final VLayout imageExportSideLayout = new VLayout();
        imageExportSideLayout.setWidth(250);
        imageExportSideLayout.setMembersMargin(20);

        final DynamicForm downloadForm = new DynamicForm();
        downloadForm.setTop(5);
        downloadForm.setWidth100();
        downloadForm.setNumCols(2);

        final SelectItem formatItem = new SelectItem("format", "Export Format");
        final SliderItem qualityItem = new SliderItem("quality", "JPEG quality");
        final ButtonItem downloadButton = new ButtonItem("_downloadButton", "Download as Image");

        final LinkedHashMap<String, String> formatValueMap = new LinkedHashMap<String, String>();
        formatValueMap.put("png", "PNG");
        formatValueMap.put("jpeg", "JPEG");
        formatItem.setValueMap(formatValueMap);
        formatItem.setRequired(true);
        formatItem.setDefaultValue("png");
        formatItem.setRedrawOnChange(true);

        qualityItem.setType("integer");
        qualityItem.setMinValue(0);
        qualityItem.setMaxValue(100);
        qualityItem.setNumValues(21);
        qualityItem.setDefaultValue(80);
        qualityItem.setShowIfCondition(new FormItemIfFunction() {
            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return "jpeg".equals(formatItem.getValueAsString());
            }
        });
        qualityItem.setTitleVAlign(VerticalAlignment.TOP);
        qualityItem.setColSpan(2);
        qualityItem.setHeight(50);
        qualityItem.setRequired(true);

        downloadButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                final String format = formatItem.getValueAsString();
                final DSRequest requestProperties = new DSRequest();
                requestProperties.setExportDisplay(ExportDisplay.DOWNLOAD);
                requestProperties.setExportFilename("Revenue");
                requestProperties.setExportImageFormat(EnumUtil.getEnum(ExportImageFormat.values(), format));
                final Float quality = qualityItem.getValueAsFloat();
                if (quality != null) {
                    requestProperties.setExportImageQuality(quality / 100.0F);
                }
                RPCManager.exportImage(multiSeriesChart.getSvgString(), requestProperties);
            }
        });

        downloadForm.setItems(formatItem, qualityItem, downloadButton);

        imageExportSideLayout.addMember(downloadForm);

        final Img snapshotImg = new Img();

        final IButton getDataURLButton = new IButton("Get Data URL");
        getDataURLButton.addClickHandler(new ClickHandler() {
            private String getDataURLRes = null;

            @Override
            public void onClick(ClickEvent event) {
                if (!browserSupportsDataURLs) {
                    SC.say("This feature is not supported in IE 6 or 7.");
                    return;
                }

                getDataURLRes = multiSeriesChart.getDataURL(new DataURLCallback() {
                    @Override
                    public void execute(String dataURL) {
                        if (getDataURLRes == null) {
                            SC.clearPrompt();
                        } else {
                            getDataURLRes = null;
                        }

                        if (SC.isIE() && getBrowserVersion() < 9 && dataURL.length() > 32768) {
                            SC.say("A data URL was generated, but it cannot be displayed in Internet Explorer 8 because it is longer than the 32 KiB limit." +
                                    "<p>See the <a href='http://msdn.microsoft.com/en-us/library/ie/cc848897.aspx' target='_blank'><code>data</code> Protocol</a> page on MSDN for more information.");
                        } else {
                            snapshotImg.setSrc(dataURL);
                        }
                    }
                });
                if (getDataURLRes == null) {
                    SC.showPrompt("Loading", "${loadingImage} The chart is being converted to an image.");
                }
            }
        });
        imageExportSideLayout.addMember(getDataURLButton);

        snapshotImg.setBorder("3px solid blue");
        snapshotImg.setWidth(200);
        snapshotImg.setHeight(150);
        imageExportSideLayout.addMember(snapshotImg);

        if (!browserSupportsDataURLs) {
            getDataURLButton.disable();
            snapshotImg.hide();
        }

        // Overall layout
        final HLayout chartImageExportLayout = new HLayout();
        chartImageExportLayout.setWidth100();
        chartImageExportLayout.setHeight100();
        chartImageExportLayout.setMembersMargin(20);
        chartImageExportLayout.addMember(multiSeriesChart);
        chartImageExportLayout.addMember(imageExportSideLayout);
        return chartImageExportLayout;
    }
}
