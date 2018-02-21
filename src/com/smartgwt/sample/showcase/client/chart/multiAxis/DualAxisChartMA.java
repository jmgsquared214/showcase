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
package com.smartgwt.sample.showcase.client.chart.multiAxis;

import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.chart.MetricSettings;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.chart.ChartSamplePanelFactory;

public class DualAxisChartMA extends ShowcasePanel {

    private static final String DESCRIPTION = "<p>Dual axis charts can be used to create a single chart that shows two data sets with different units (for example: sales in dollars and total units shipped) and/or very different ranges (for example: gross revenue, profit).</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "dualAxisChartMA";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new DualAxisChartMA();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        FacetChart chart = new FacetChart();
        chart.setWidth(508);
        chart.setHeight(400);
        chart.setChartType(ChartType.COLUMN);
        chart.setStacked(true);
        chart.setValueTitle("Percent");
        chart.setBorder("1px solid black");
        chart.setShowTitle(false);
        chart.setShowChartRect(true);

        Facet areaFacet = new Facet();
        areaFacet.setValues(new FacetValue("1", "North America"));
        areaFacet.setId("area");
        Facet metricFacet = new Facet();
        metricFacet.setValues(new FacetValue("percent", "Percent"),
                              new FacetValue("events", "Events"));
        metricFacet.setInlinedValues(true);
        metricFacet.setId("metric");

        chart.setFacets(new Facet("date"), areaFacet, metricFacet);
        chart.setExtraAxisMetrics("events");
        MetricSettings metricSettings = new MetricSettings();
        metricSettings.setChartType(ChartType.LINE);
        metricSettings.setMultiFacet(true);
        metricSettings.setShowDataPoints(true);
        metricSettings.setValueTitle("Events");
        chart.setExtraAxisSettings(metricSettings);

        chart.setData(MultiAxisChartData.getData());

        HStack layout = new HStack();
        layout.addMember(chart);
        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                    new SourceEntity("MultiAxisChartData.java", JAVA, "source/chart/multiAxis/MultiAxisChartData.java.html", false)
                };
    }
}
