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
import com.smartgwt.client.widgets.drawing.DrawRect;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class BubbleChart extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>A Bubble Chart is a scatter plot where an additional data value is represented " +
        "by the size of the shape drawn at the data point.</p>" +
        "<p>Bubble Charts can show multiple data series, optionally as different shapes.  " +
        "Use the \"Use Multiple Shapes\" checkbox below to switch between modes.</p>";

    public static class Factory extends ChartSamplePanelFactory {
        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "bubbleChart";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new BubbleChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setID("bubbleChart");
        chart.setTitle("Bubble Chart");
        chart.setChartType(ChartType.BUBBLE);

        Facet metricFacet = new Facet("metric");
        metricFacet.setInlinedValues(true);
        metricFacet.setValues(
            new FacetValue("value"),
            new FacetValue("time"),
            new FacetValue("volume", "Volume"));
        Facet legendFacet = new Facet("series");
        legendFacet.setValues(
            new FacetValue("A", "Series A"),
            new FacetValue("B", "Series B"));
        chart.setFacets(metricFacet, legendFacet);

        chart.setData(BubbleChartData.getData());

        chart.setXAxisMetric("time");
        chart.setYAxisMetric("value");
        chart.setPointSizeMetric("volume");

        chart.setUseMultiplePointShapes(true);
        chart.setMinDataPointSize(10);
        chart.setMaxDataPointSize(50);

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

        DynamicForm form = new DynamicForm();
        form.setAutoDraw(false);
        form.setWidth("50%");

        CheckboxItem multipleShapesItem = new CheckboxItem("useMultiplePointShapes", "Use Multiple Shapes");
        multipleShapesItem.setDefaultValue(chart.getUseMultiplePointShapes());
        multipleShapesItem.addChangedHandler(new ChangedHandler() {
                public void onChanged(ChangedEvent event) {
                    Boolean value = (Boolean) event.getValue();
                    chart.setUseMultiplePointShapes(value != null && value.booleanValue());
                }
            });
        form.setItems(multipleShapesItem);

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
            new SourceEntity("BubbleChartData.java", JAVA, "source/chart/BubbleChartData.java.html", false)
        };
    }
}
