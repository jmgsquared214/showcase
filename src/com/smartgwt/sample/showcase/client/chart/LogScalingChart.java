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
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class LogScalingChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "Charts can use logarithmic scaling, which shows equal percentage changes as the same " +
            "difference in height.  This is useful for data that spans a very large range.";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "logScalingChart";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new LogScalingChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setData(LogScalingChartData.getData());
        chart.setFacets(new Facet("year", "Year"));
        chart.setValueProperty("index");
        chart.setChartType(ChartType.LINE);
        chart.setLogScale(Boolean.TRUE); // makes the chart use a logarithmic scale
        chart.setLogBase(10);
        chart.setUseLogGradations(Boolean.TRUE); // draws gradations based on the logBase
        chart.setLogGradations(1f, 2f, 5f, 7.5f); // specifies which gradations to draw within the logBase
        chart.setTitle("S & P 500 Index");

        VLayout layout = new VLayout(15);
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
                    new SourceEntity("LogScalingChartData.java", JAVA, "source/chart/LogScalingChartData.java.html", false)
                };
    }
}
