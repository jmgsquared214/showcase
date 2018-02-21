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

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IPickTreeItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class DynamicDataChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "<p>Charts can be created directly from a DataSource without a ListGrid.</p>" +
            "<p>Use the \"Time Period\" menu to change the criteria passed to the DataSource.</p>" +
            "<p>Use the \"Chart Type\" selector to see the same data rendered by multiple different chart types. " +
            "Right-click on the chart to change the way data is visualized.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "dynamicDataCharting";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new DynamicDataChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final DataSource ds = DataSource.get("productRevenue");

        final FacetChart chart = new FacetChart();
        chart.setFacets(new Facet("Regions", "Region"), new Facet("Scenarios", "Scenario"));
        chart.setValueProperty("value");
        chart.setChartType(ChartType.AREA);
        chart.setStacked(Boolean.FALSE);
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

        final Tree timeTree = new Tree();
        timeTree.setModelType(TreeModelType.PARENT);
        timeTree.setRootValue("sum");
        timeTree.setShowRoot(Boolean.TRUE);
        timeTree.setData(DynamicDataChartData.getData());

        IPickTreeItem timeSelector = new IPickTreeItem("timeSelector", "Time Period");
        timeSelector.setWidth(60);
        timeSelector.setValueTree(timeTree);
        timeSelector.setCanSelectParentItems(Boolean.TRUE);
        timeSelector.setDisplayField("title");
        timeSelector.setValueField("id");
        timeSelector.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                Criteria c = new Criteria("Products", "Prod01");
                c.addCriteria("Regions", new String[] {"North", "South", "East", "West"});
                c.addCriteria("Time", (String) event.getValue());
                final String newTitle = "Revenue for " + timeTree.findById((String) event.getValue()).getTitle();
                ds.fetchData(c, new DSCallback() {
                    public void execute(DSResponse response, Object rawData, DSRequest request) {
                        chart.setTitle(newTitle);
                        chart.setData(response.getData());
                    }
                });
            }
        });

        chartSelector.setFields(chartType, timeSelector);

        VLayout layout = new VLayout(15);
        layout.addMember(chartSelector);
        layout.addMember(chart);

        Criteria c = new Criteria("Products", "Prod01");
        c.addCriteria("Regions", new String[] {"North", "South", "East", "West"});
        c.addCriteria("Time", "sum");
        ds.fetchData(c, new DSCallback() {
            public void execute(DSResponse response, Object rawData, DSRequest request) {
                chart.setTitle("Revenue for All Years");
                chart.setData(response.getData());
            }
        });

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                    new SourceEntity("DynamicDataChartData.java", JAVA, "source/chart/DynamicDataChartData.java.html", false)
                };
    }
}
