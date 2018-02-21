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

import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.drawing.DrawItem;
import com.smartgwt.client.widgets.drawing.DrawRect;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.SubmitValuesEvent;
import com.smartgwt.client.widgets.form.events.SubmitValuesHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ColorItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class ColorScaleChart extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>A Color Scale Chart is a scatter plot where an additional data value is " +
        "represented by the color of each data point.</p>" +
        "<p>In this sample, the color scale feature has been combined with the bubble chart " +
        "feature, so each point represents 4 distinct data values: X-axis placement, " +
        "Y-axis placement, size of point and color of point.</p>";

    public static class Factory extends ChartSamplePanelFactory {
        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "colorScaleChart";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new ColorScaleChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setID("colorScaleChart");
        chart.setChartType(ChartType.BUBBLE);

        Facet metricFacet = new Facet("metric");
        metricFacet.setInlinedValues(true);
        metricFacet.setValues(
            new FacetValue("value"),
            new FacetValue("time"),
            new FacetValue("volume", "Volume"),
            new FacetValue("heat", "Heat"));
        Facet legendFacet = new Facet("series");
        legendFacet.setValues(
            new FacetValue("A", "Series A"),
            new FacetValue("B", "Series B"));
        chart.setFacets(metricFacet, legendFacet);

        chart.setData(ColorScaleChartData.getData());

        chart.setXAxisMetric("time");
        chart.setYAxisMetric("value");
        chart.setPointSizeMetric("volume");
        chart.setColorScaleMetric("heat");
        chart.setScaleStartColor("#0000FF");
        chart.setScaleEndColor("#FF0000");

        chart.setUseMultiplePointShapes(true);
        chart.setMinDataPointSize(10);
        chart.setMaxDataPointSize(50);

        DrawItem bubbleProperties = new DrawItem();
        bubbleProperties.setLineWidth(1);
        bubbleProperties.setLineColor("#000000");
        chart.setBubbleProperties(bubbleProperties);
        chart.setDataColors("#ffffff");

        chart.setShowChartRect(true);
        DrawRect chartRect = new DrawRect();
        chartRect.setLineWidth(1);
        chartRect.setLineColor("#bbbbbb");
        chartRect.setRounding(0.05f);
        chart.setChartRectProperties(chartRect);
        chart.setBandedBackground(false);
        chart.setChartRectMargin(15);
        chart.setShowValueOnHover(true);
        chart.setAutoDraw(false);

        final DynamicForm form = new DynamicForm();
        form.setAutoDraw(false);
        form.setNumCols(6);
        form.setColWidths(20, "*", 90, "*", 80, "*");
        form.setWidth(600);
        form.setSaveOnEnter(true);
        form.addSubmitValuesHandler(new SubmitValuesHandler() {
                @Override
                public void onSubmitValues(SubmitValuesEvent event) {
                    form.setValues(event.getValuesAsMap());
                }
            });

        CheckboxItem multipleShapesItem = new CheckboxItem("useMultiplePointShapes", "Use Multiple Shapes");
        multipleShapesItem.setDefaultValue(chart.getUseMultiplePointShapes());
        multipleShapesItem.addChangedHandler(new ChangedHandler() {
                @Override
                public void onChanged(ChangedEvent event) {
                    Boolean value = (Boolean)event.getValue();
                    chart.setUseMultiplePointShapes(value != null && value.booleanValue());
                }
            });

        ColorItem startColorItem = new ColorItem("scaleStartColor", "Start Color");
        startColorItem.setDefaultValue(chart.getScaleStartColor());
        startColorItem.setWidth(100);
        startColorItem.setWrapTitle(false);
        startColorItem.setKeyPressFilter("[0-9a-fA-F#]");
        startColorItem.addChangedHandler(new ChangedHandler() {
                @Override
                public void onChanged(ChangedEvent event) {
                    chart.setScaleStartColor((String)event.getValue());
                }
            });

        ColorItem endColorItem = new ColorItem("scaleEndColor", "End Color");
        endColorItem.setDefaultValue(chart.getScaleEndColor());
        endColorItem.setWidth(100);
        endColorItem.setWrapTitle(false);
        endColorItem.setKeyPressFilter("[0-9a-fA-F#]");
        endColorItem.addChangedHandler(new ChangedHandler() {
                @Override
                public void onChanged(ChangedEvent event) {
                    chart.setScaleEndColor((String)event.getValue());
                }
            });

        form.setItems(multipleShapesItem, startColorItem, endColorItem);

        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.setMembersMargin(2);
        layout.setMembers(form, chart);

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
            new SourceEntity("ColorScaleChartData.java", JAVA, "source/chart/ColorScaleChartData.java.html", false)
        };
    }
}
