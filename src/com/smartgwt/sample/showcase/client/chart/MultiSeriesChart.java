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
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class MultiSeriesChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "<p>Multi-series charts can be viewed with \"stacked\" data (to show totals) or \"unstacked\" to compare " +
            "values from each series. The \"Area\" chart type defaults to using stacked data, while the \"Line\" chart " +
            "type defaults to unstacked. Use the default settings, or explicitly specify whether to stack data.</p>" +
            "<p>Use the \"Chart Type\" selector to see the same data rendered by multiple different chart types. " +
            "Right-click on the chart to change the way data is visualized.</p>" +
            "<p>Note that the right-click menu offers the ability to switch to proportional rendering whenever " +
            "the chart is in \"Stacked\" mode.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "multiSeriesChart";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new MultiSeriesChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setData(MultiSeriesChartData.getData());
        chart.setFacets(new Facet("time", "Period"), new Facet("region", "Region"));
        chart.setValueProperty("value");
        chart.setChartType(ChartType.AREA);
        chart.setTitle("Revenue");

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

        VLayout layout = new VLayout(15);
        layout.addMember(chartSelector);
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
                    new SourceEntity("MultiSeriesChartData.java", JAVA, "source/chart/MultiSeriesChartData.java.html", false)
                };
    }
}
