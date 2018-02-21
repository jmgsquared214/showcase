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
import com.smartgwt.client.widgets.chart.MetricSettings;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class MixedPlotsChart extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>In some cases you want to show some data series as one shape and other data " +
        "series as another shape <i>but use the same axis</i>.  This is commonly used when " +
        "one series is of a fundamentally different kind than the other series (for " +
        "example, a projection or average) but still has the same scale.<p>" +
        "<p>This example demonstrates a technique for doing this based on Dual-Axis charts, " +
        "where the second axis has been explicitly hidden, and data values that do not " +
        "apply have been omitted from the dataset.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "mixedPlotsChart";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new MixedPlotsChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setData(MixedPlotsChartData.getData());

        Facet metricFacet = new Facet("metric");
        metricFacet.setInlinedValues(true);
        metricFacet.setValues(
            new FacetValue("value", "Value"),
            new FacetValue("avg", "Projected Average"));
        chart.setFacets(new Facet("time", "Period"), new Facet("region", "Region"), metricFacet);

        chart.setChartType(ChartType.COLUMN);
        chart.setStacked(false);
        chart.setTitle("Revenue");

        chart.setExtraAxisMetrics("avg");

        MetricSettings metricSettings = new MetricSettings();
        metricSettings.setShowAxis(false);
        metricSettings.setMatchGradations("value");
        metricSettings.setMultiFacet(false);
        metricSettings.setFixedFacetValue("North");
        metricSettings.setLegendLabel("Projected Average");
        metricSettings.setChartType(ChartType.LINE);
        chart.setExtraAxisSettings(metricSettings);

        HLayout layout = new HLayout(15);
        layout.addMember(chart);

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
            new SourceEntity("MixedPlotsChartData.java", JAVA, "source/chart/MixedPlotsChartData.java.html", false)
        };
    }
}
