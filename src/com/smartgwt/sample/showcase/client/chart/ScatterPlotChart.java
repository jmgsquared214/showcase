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

public class ScatterPlotChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "<p>Scatter plots can show two axes of continuous numeric data. Multiple data sets can be plotted in different colors.</p>";

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
            return new ScatterPlotChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setData(ScatterPlotChartData.getData());
        chart.setID("multiScatterChart");
        chart.setWidth(700);
        Facet metric = new Facet();
        metric.setValues(new FacetValue("value"), new FacetValue("Time"));
        metric.setInlinedValues(true);
        metric.setId("metric");
        Facet animal = new Facet();
        animal.setId("animal");

        chart.setFacets(metric, animal);
        chart.setValueProperty("value");
        chart.setChartType(ChartType.SCATTER);
        chart.setTitle("Multi Scatter");
        chart.setShowScatterLines(true);
        chart.setDataLineType(DataLineType.SMOOTH);

        DynamicForm typeForm = new DynamicForm();
        typeForm.setTitleWidth(120);
        typeForm.setWidth(220);
        final SelectItem linesTypeItem = new SelectItem("linesType", "Show Lines");
        linesTypeItem.setWrapTitle(false);
        linesTypeItem.setDefaultValue("Smooth");
        linesTypeItem.setValueMap("None", "Straight", "Smooth");
        linesTypeItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                if ("None".equals(linesTypeItem.getValue())) {
                    chart.setShowScatterLines(false);
                } else {
                    if ("Smooth".equals(linesTypeItem.getValue())) {
                        chart.setDataLineType(DataLineType.SMOOTH);
                    } else {
                        chart.setDataLineType(DataLineType.STRAIGHT);
                    }
                    chart.setShowScatterLines(true);
                }
            }
        });
        typeForm.setItems(linesTypeItem);

        VLayout layout = new VLayout(15);
        layout.setMembers(typeForm, chart);

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                    new SourceEntity("ScatterPlotChartData.java", JAVA, "source/chart/ScatterPlotChartData.java.html", false)
                };
    }
}
