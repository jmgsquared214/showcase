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
import com.smartgwt.client.util.EnumUtil;
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

public class SimpleChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "<p>Charts can be created with inline Javascript data.</p>" +
            "<p>Use the \"Chart Type\" selector to see the same data rendered by multiple different chart types. " +
            "Right-click on the chart to change the way data is visualized.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "simpleChart";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new SimpleChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setData(SimpleChartData.getData());
        chart.setFacets(new Facet("region", "Region"), new Facet("product", "Product"));
        chart.setValueProperty("sales");
        chart.setChartType(ChartType.AREA);
        chart.setTitle("Sales by Product and Region");

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
                final String selectedChartType = chartType.getValueAsString();
                final ChartType chartType = EnumUtil.getEnum(ChartType.values(), selectedChartType);
                if (chartType != null) chart.setChartType(chartType);
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
                    new SourceEntity("SimpleChartData.java", JAVA, "source/chart/SimpleChartData.java.html", false)
                };
    }
}
