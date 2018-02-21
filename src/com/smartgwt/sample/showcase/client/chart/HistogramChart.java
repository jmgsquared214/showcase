/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2016 and beyond Isomorphic Software, Inc.
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

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.chart.ColorMapper;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.layout.VLayout;

import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class HistogramChart extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>A histogram chart shows a number of segments for each facet value on the x-axis, " +
        "one for each legend facet value.  However, unlike a column chart, in which each " +
        "facet only has a length, in a histogram each segment has both a start (value " +
        "property) and end value (defined via a metric).</p>" +

        "<p>In the sample below, move the mouse across the various segments to see the data " + 
        "values shown in a hover, and note how the segments can overlap each other.  The " + 
        "stacking order can be changed through use of an additional metric - you can see how " +
        "this works by picking a different Z-Ordering in the form above the chart.  The " +
        "chart below also uses customizers to specify segment gradient and border colors.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "errorBars";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new HistogramChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public FacetChart  chart;
    public DynamicForm configForm;

    private DynamicForm getConfigForm() {
        final SelectItem zOrderItem = new SelectItem("zOrder", "Z-Ordering");
        zOrderItem.setWrapTitle(false);
        zOrderItem.setAllowEmptyValue(true);
        zOrderItem.setEmptyDisplayValue("Draw order (default)");
        zOrderItem.setValueMap(new LinkedHashMap<String, String>() {{
            put("danger", "Use 'danger' Metric");
        }});
        zOrderItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                chart.setZIndexMetric((String)event.getValue());
            }
        });

        final SelectItem borderColorItem = new SelectItem("borderColor", "Border Color");
        borderColorItem.setWrapTitle(false);
        borderColorItem.setValueMap(new String[]{"Grey", "Orange", "Cyan"});
        borderColorItem.setDefaultToFirstOption(true);
        borderColorItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                chart.setData(chart.getDataAsRecordList());
            }
        });

        configForm = new DynamicForm();
        configForm.setNumCols(4);
        configForm.setWidth(500);
        configForm.setItems(zOrderItem, borderColorItem);

        return configForm;
    }

    private Map<String, String> lineColors = new HashMap<String, String>() {{
        put("Grey", "#333333"); put("Orange", "#FF8C00"); put("Cyan", "#00FFFF");
    }};
    private Map<String, String> dataColors = new HashMap<String, String>() {{
        put("Metals", "#DC143C"); put("Organics", "#7FFF00");  put("Pathogens", "#0000FF");
    }};

    public Canvas getViewPanel() {
        chart = new FacetChart();
        chart.setID("histogram");
        chart.setTitle("Measured Pollutant Levels");

        chart.setShowValueOnHover(true);
        chart.setChartType(ChartType.HISTOGRAM);
        chart.setAllowedChartTypes(new ChartType[] {ChartType.BAR, ChartType.COLUMN, 
                                                    ChartType.HISTOGRAM});
        chart.setValueProperty("minValue");
        chart.setEndValueMetric("maxValue");

        Facet metricFacet = new Facet("valueMetrics");
        metricFacet.setInlinedValues(true);
        metricFacet.setValues(new FacetValue("minValue"), 
                              new FacetValue("maxValue"), new FacetValue("danger"));

        chart.setFacets(new Facet("water", "Water Source"),
                        new Facet("pollutant", "Pollutant"), metricFacet);
        chart.setMetricFacetId("valueMetrics");
        chart.setData(HistogramChartData.getData());

        // override the default chart segment colors

        chart.setDataLineColorMapper(new ColorMapper() {
            @Override
            public String getColor(int index, Object facetValueId, String purpose) {
                FormItem item = configForm.getItem("borderColor");
                return (String) lineColors.get(item.getValue());
            }
        });
        chart.setDataColorMapper(new ColorMapper() {
            @Override
            public String getColor(int index, Object facetValueId, String purpose) {
                return (String) dataColors.get(facetValueId);
            }
        });        

        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.setMembers(getConfigForm(), chart);

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("HistogramChartData.java", JAVA, 
                "source/chart/HistogramChartData.java.html", false),
         };
    }

}
