package com.smartgwt.sample.showcase.client.dataintegration.java.others;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.types.ExportImageFormat;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.drawing.ColorStop;
import com.smartgwt.client.widgets.drawing.DrawCurve;
import com.smartgwt.client.widgets.drawing.DrawImage;
import com.smartgwt.client.widgets.drawing.DrawOval;
import com.smartgwt.client.widgets.drawing.DrawPane;
import com.smartgwt.client.widgets.drawing.DrawRect;
import com.smartgwt.client.widgets.drawing.DrawTriangle;
import com.smartgwt.client.widgets.drawing.LinearGradient;
import com.smartgwt.client.widgets.drawing.Point;
import com.smartgwt.client.widgets.drawing.RadialGradient;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class DrawingExportSample extends ShowcasePanel {

    private static final String DESCRIPTION = "Select an export format and then click the Save button. " +
        "SmartGWT will trigger the browser's save dialog allowing the DrawPane to be saved " +
        "as an image in the specified format.";

    public static class Factory implements PanelFactory {
        private String id;

        public ShowcasePanel create() {
            final DrawingExportSample panel = new DrawingExportSample();
            id = panel.getID();
            return panel;
        }

        public String getID() {
            return id;
        }

        public String getDescription() {
            return DESCRIPTION;
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
        final DrawPane mainPane = new DrawPane();
        mainPane.setAutoDraw(false);
        mainPane.setShowEdges(true);
        mainPane.setWidth(400);
        mainPane.setHeight(400);
        mainPane.setCanDrag(true);

        final RadialGradient rg = new RadialGradient();
        rg.setCx("0");
        rg.setCy("0");
        rg.setR("90%");
        rg.setFx("0");
        rg.setFy("0");
        rg.setColorStops(new ColorStop("teal", 0.0F),
                new ColorStop("#ffff00", 0.3F),
                new ColorStop("#00ff00", 0.8F),
                new ColorStop("#0000ff", 1.0F));
        mainPane.createRadialGradient("rg", rg);

        final LinearGradient lg = new LinearGradient();
        lg.setX1("51%");
        lg.setY1("32%");
        lg.setX2("80%");
        lg.setY2("80%");
        lg.setColorStops(new ColorStop("#ff0000", 0.0F),
                new ColorStop("#ffff00", 0.33F),
                new ColorStop("#00ff00", 0.66F),
                new ColorStop("#0000ff", 1.0F));
        mainPane.createLinearGradient("lg", lg);

        final DrawOval drawOval = new DrawOval();
        drawOval.setFillGradient(rg);
        drawOval.setLeft(50);
        drawOval.setTop(200);
        drawOval.setWidth(100);
        drawOval.setHeight(150);
        drawOval.setCanDrag(true);
        mainPane.addDrawItem(drawOval, true);

        final DrawImage drawImage = new DrawImage();
        drawImage.setLeft(50);
        drawImage.setTop(10);
        drawImage.setSrc("tiles/images/Elephant.jpg");
        drawImage.setWidth(120);
        drawImage.setHeight(100);
        drawImage.setCanDrag(true);
        mainPane.addDrawItem(drawImage, true);

        final DrawTriangle drawTriangle = new DrawTriangle();
        drawTriangle.setPoints(new Point(180, 250), new Point(150, 150), new Point(375, 100));
        drawTriangle.setLineColor("#ff8000");
        drawTriangle.setFillColor("#ffff00");
        drawTriangle.setCanDrag(true);
        mainPane.addDrawItem(drawTriangle, true);

        final DrawCurve drawCurve = new DrawCurve();
        drawCurve.setStartPoint(new Point(200, 50));
        drawCurve.setEndPoint(new Point(300, 150));
        drawCurve.setControlPoint1(new Point(250, 0));
        drawCurve.setControlPoint2(new Point(250, 200));
        drawCurve.setCanDrag(true);
        mainPane.addDrawItem(drawCurve, true);

        final DrawRect drawRect = new DrawRect();
        drawRect.setFillGradient(lg);
        drawRect.setLeft(200);
        drawRect.setTop(270);
        drawRect.setWidth(150);
        drawRect.setHeight(100);
        drawRect.setCanDrag(true);
        mainPane.addDrawItem(drawRect, true);

        final DynamicForm form = new DynamicForm();
        form.setTop(5);
        form.setWidth(300);
        form.setNumCols(2);

        final SelectItem formatItem = new SelectItem("format", "Export Format");
        formatItem.setWrapTitle(false);
        final SliderItem qualityItem = new SliderItem("quality", "JPEG quality");
        final ButtonItem saveButton = new ButtonItem("_saveButton", "Save");

        final LinkedHashMap<String, String> formatValueMap = new LinkedHashMap<String, String>();
        formatValueMap.put("png", "PNG");
        formatValueMap.put("jpeg", "JPEG");
        formatValueMap.put("pdf", "PDF");
        formatItem.setValueMap(formatValueMap);
        formatItem.setRequired(true);
        formatItem.setDefaultValue("png");
        formatItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                final String format = formatItem.getValueAsString();

                if ("jpeg".equals(format)) qualityItem.show();
                else qualityItem.hide();

                saveButton.setTitle("pdf".equals(format) ? "Save" : "Save Image");
            }
        });

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

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String format = formatItem.getValueAsString();
                final DSRequest requestProperties = new DSRequest();
                requestProperties.setExportDisplay(ExportDisplay.DOWNLOAD);
                requestProperties.setExportFilename("masterpiece");
                if ("pdf".equals(format)) {
                    RPCManager.exportContent(mainPane, requestProperties);
                } else {
                    requestProperties.setExportImageFormat(EnumUtil.getEnum(ExportImageFormat.values(), format));
                    final Float quality = qualityItem.getValueAsFloat();
                    if (quality != null) {
                        requestProperties.setExportImageQuality(quality / 100.0F);
                    }
                    RPCManager.exportImage(mainPane.getSvgString(), requestProperties);
                }
            }
        });

        form.setItems(formatItem, qualityItem, saveButton);

        final HLayout layout = new HLayout();
        layout.setMembersMargin(20);
        layout.setMembers(mainPane, form);
        return layout;
    }
}
