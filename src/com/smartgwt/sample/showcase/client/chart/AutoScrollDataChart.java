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
n *  copied or moved from this file.
 */
package com.smartgwt.sample.showcase.client.chart;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.types.AutoScrollDataApproach;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.chart.ClusterSizeMapper;
import com.smartgwt.client.widgets.drawing.DrawPane;
import com.smartgwt.client.widgets.drawing.DrawLabel;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.layout.VLayout;

import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class AutoScrollDataChart extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>The chart below automatically expands horizontally to show content - this is " + 
        "enabled by the <b><code>autoScrollData</code></b> property.  Automatic expansion " +
        "can be driven either to fit per-facet-value bar thicknesses specified by setting a " +
        "customizer for the chart with <b><code>setMinClusterSizeMapper()</code></b>, or by " + 
        "the requirement that all labels fit on the horizontal axis without overlapping.</p>" +

        "<p>By toggling \"Scroll to Fit\" below, you can see that without it, the labels " +
        "must be rotated to fit the chart data in a typically-sized browser window.  By " + 
        "toggling dynamic bar thickness, you can see that sizing bars precisely to fit their " +
        "labels enables a better use of space than driving expansion by average label width, " +
        "which is the default.</p>";

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
            return new AutoScrollDataChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public static class FittedChart extends FacetChart {

        DrawPane measurePane;
        DrawLabel measureLabel;

        public FittedChart() {
            setMinLabelGap(5);
            setAutoScrollData(true);
            setAutoScrollDataApproach(AutoScrollDataApproach.CLUSTERS);
            setMinClusterSizeMapper(clusterSizeMapper);

            measurePane = new DrawPane();
            measurePane.setVisibility(Visibility.HIDDEN);
            measurePane.setWidth(200);
            measurePane.setHeight(50);
            
            measureLabel = makeLabelFromTemplate(getDataLabelProperties());
            measureLabel.setDrawPane(measurePane);
        }

        public static DrawLabel makeLabelFromTemplate(DrawLabel templateLabel) {
            DrawLabel realLabel = new DrawLabel();
            realLabel.setConfig(JSOHelper.cleanProperties(templateLabel.getConfig(), true));
            return realLabel;
        }
        
        ClusterSizeMapper clusterSizeMapper = new ClusterSizeMapper() {
            public int getClusterSize(int index, Object facetValueId) {
                measureLabel.setContents((String)facetValueId);
                int[] box = measureLabel.getBoundingBox();
                return box[2] - box[0];
            }
        };
        
        public void setBarSizing(boolean enabled) {
            setMinClusterSizeMapper(enabled ? clusterSizeMapper : null);
            setAutoScrollDataApproach(enabled ? AutoScrollDataApproach.CLUSTERS :
                                                AutoScrollDataApproach.LABELS);
        }
    }

    public FittedChart chart;
    public DynamicForm configForm;

    private DynamicForm getConfigForm() {
        final CheckboxItem scrollItem = new CheckboxItem("scroll", "Scroll Chart to Fit");
        scrollItem.setHeight(30);
        scrollItem.setWidth(140);
        scrollItem.setShowTitle(false);
        scrollItem.setDefaultValue(true);
        scrollItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                FormItem sizingItem = configForm.getItem("sizing");
                Boolean value = (Boolean) event.getValue();
                if (!value) {
                    chart.setBarSizing(false);
                    sizingItem.setValue(false);
                }
                sizingItem.setDisabled(!value);
                chart.setAutoScrollData(value);
            }
        });

        final CheckboxItem sizingItem = new CheckboxItem("sizing", "Dynamic Bar Thickness");
        sizingItem.setShowTitle(false);
        sizingItem.setDefaultValue(true);
        sizingItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                chart.setBarSizing((Boolean) event.getValue());
            }
        });        

        configForm = new DynamicForm();
        configForm.setNumCols(4);
        configForm.setWidth(500);
        configForm.setItems(scrollItem, sizingItem);

        return configForm;
    }

    public Canvas getViewPanel() {
        chart = new FittedChart();
        chart.setID("scrollToFitChart");
        chart.setValueProperty("population");
        chart.setValueTitle("Population (Thousands)");
        chart.setTitle("2000 Census Population by State");
        
        chart.setData(AutoScrollDataChartData.getData());

        chart.setAllowedChartTypes(new ChartType[] {
            ChartType.COLUMN, ChartType.AREA, ChartType.LINE
        });

        chart.setFacets(new Facet("state", "State"));

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
            new SourceEntity("AutoScrollDataChartData.java", JAVA, 
                "source/chart/AutoScrollDataChartData.java.html", false),
         };
    }
}
