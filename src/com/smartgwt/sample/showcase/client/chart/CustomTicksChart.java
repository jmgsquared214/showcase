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
import com.smartgwt.client.types.DataLineType;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

import java.util.Map;
import java.util.HashMap;

public class CustomTicksChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
        "The chart below shows how you can control the gradations that appear on charts.  " +
        "Its vertical gradations will always appear as 2 times a power of ten, and its " + 
        "horizontal gradations will always appear as one of the gradation time intervals " + 
        "configured by the picklist.  If you make the chart narrow, you can see that if " + 
        "you've configured the gradation times to exclude quarters of a year, then only " + 
        "whole years will be shown.<P>" +
        "Tick marks can also be shown along the axes of a chart showing gradations.  The " + 
        "chart below is configured to show a major tick mark at every quarter of a year, and " +
        "a minor tick mark for shorter intervals - months in this case.<P>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "scatterPlotCharting";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new CustomTicksChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setData(CustomTicksChartData.getData());
        chart.setID("multiScatterChart");
        chart.setWidth100();

        chart.setShowXTicks(true);
        chart.setGradationGaps(2);
        chart.setOtherAxisGradationTimes("1m","1y");
        chart.setMajorTickTimeIntervals("1q");
        
        Facet metric = new Facet();
        metric.setValues(new FacetValue("value"), new FacetValue("Time"));
        metric.setInlinedValues(true);
        metric.setId("metric");
        Facet animal = new Facet();
        animal.setId("animal");

        chart.setFacets(metric, animal);
        chart.setValueProperty("value");
        chart.setChartType(ChartType.SCATTER);
        chart.setShowScatterLines(true);
        chart.setDataLineType(DataLineType.SMOOTH);

        DynamicForm typeForm = new DynamicForm();
        typeForm.setTitleWidth(120);
        typeForm.setWidth(220);

        final SelectItem gradationTimesItem = new SelectItem("times", "Gradation Times");
        gradationTimesItem.setType("integer");
        gradationTimesItem.setDefaultValue(0);
        gradationTimesItem.setWrapTitle(false);

        Map times = new HashMap();
        times.put(0, "Month/Year");
        times.put(1, "Month/Quarter/Year");
        gradationTimesItem.setValueMap(times);

        gradationTimesItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                Integer value = (Integer)event.getValue();
                chart.setOtherAxisGradationTimes(value == 0 ?
                    new String[] {"1m", "1y"} : new String[] {"1m", "1q", "1y"});
            }
        });
        typeForm.setItems(gradationTimesItem);

        VLayout layout = new VLayout(15);
        layout.setMembers(typeForm, chart);
        layout.setMinBreadthMember(chart);

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("CustomTicksChartData.java", JAVA, 
                             "source/chart/CustomTicksChartData.java.html", false)
        };
    }
}
