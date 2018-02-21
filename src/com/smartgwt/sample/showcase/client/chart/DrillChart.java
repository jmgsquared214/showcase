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

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.chart.DataLabelClickEvent;
import com.smartgwt.client.widgets.chart.DataLabelClickHandler;
import com.smartgwt.client.widgets.chart.DataLabelHoverCustomizer;
import com.smartgwt.client.widgets.chart.DrawnValue;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.chart.LegendClickEvent;
import com.smartgwt.client.widgets.chart.LegendClickHandler;
import com.smartgwt.client.widgets.chart.LegendHoverCustomizer;
import com.smartgwt.client.widgets.chart.ValueClickEvent;
import com.smartgwt.client.widgets.chart.ValueClickHandler;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class DrillChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "<p>Click on any swatch in the legend area of the chart below to focus in on data for just one Region.</p>" +  
            "<p>Click the \"X\" button next to the \"Selected Region\" text to return to viewing all Regions.</p>" +
            "<p>Click on any segment in the stacked columns to likewise focus in on data for just the region that the segment represents.</p>" +
            "<p>This simple Drill-down interface is created by responding to the <code>valueClick</code> and <code>legendClick</code> events - see the code for details.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "drillCharting";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new DrillChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    private FacetChart dynamicChart;
    private Label selectedValue;
    private Label selectedLabel;
    private VLayout dynamicChartLayout;
    
    public Canvas getViewPanel() {
        // Label to clear region selection 
        selectedValue = new Label();
        selectedValue.setCursor(Cursor.HAND);
        selectedValue.setIconOrientation("right");
        selectedValue.setShowRollOver(true);
        selectedValue.setShowRollOverIcon(true);
        selectedValue.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!"All".equals(selectedValue.getContents())) {
                    createChart(null, null);
                }
            }
        });
        
        // Overall layout
        HStack labelsStack = new HStack();
        labelsStack.setHeight(5);
        labelsStack.setPadding(10);
        selectedLabel = new Label();
        labelsStack.setMembers(selectedLabel, selectedValue);

        dynamicChartLayout = new VLayout();
        dynamicChartLayout.setWidth100();
        dynamicChartLayout.setHeight100();
        dynamicChartLayout.setMembers(labelsStack);
        
        createChart(null, null);
        return dynamicChartLayout;        
    }
    
    public void createChart(String region, String time) {
        boolean facetsSwapped = false;
        if (dynamicChart != null) {
            facetsSwapped = "Regions".equals(dynamicChart.getFacets()[0].getIdAsString());
            dynamicChart.destroy();
        }
        
        dynamicChart = new FacetChart();
        dynamicChart.setTitle("Revenue");
        Facet timeFacet = new Facet("Time", "Time");
        Facet regionFacet = new Facet("Regions", "Region");
        regionFacet.setValues(
            new FacetValue("North", "North"),
            new FacetValue("South", "South"),
            new FacetValue("East", "East"),
            new FacetValue("West", "West"));
        if (region == null && time == null) {
            if (facetsSwapped) {
                dynamicChart.setFacets(regionFacet, timeFacet);
                selectedLabel.setContents("Selected Region:");
            } else {
                dynamicChart.setFacets(timeFacet, regionFacet);
                selectedLabel.setContents("Selected Time");
            }
            selectedValue.setContents("All");
            selectedValue.setIcon(null);
        } else {
            if (region == null) {
                dynamicChart.setFacets(regionFacet);
                selectedValue.setContents(time);
                selectedLabel.setContents("Selected Time");
            } else {
                dynamicChart.setFacets(timeFacet);
                selectedValue.setContents(region);
                selectedLabel.setContents("Selected Region:");
            }
            selectedValue.setIcon("[SKIN]/DynamicForm/Remove_icon.png");
        }
        dynamicChart.setChartType(ChartType.COLUMN);
        dynamicChart.setStacked(true);
        // the property in the data that is the numerical value to chart
        dynamicChart.setValueProperty("value");
        
        // activates when user clicks a segment of a column, drawnValue is a type of DrawnValue
        dynamicChart.addValueClickHandler(new ValueClickHandler() {
            @Override
            public void onValueClick(ValueClickEvent event) {
                DrawnValue drawnValue = event.getDrawnValue();
                if ("Time".equals(dynamicChart.getFacets()[0].getIdAsString())) {
                    createChart(drawnValue.getFacetValues().getMapping("Regions"), null);    
                } else {
                    createChart(null, drawnValue.getFacetValues().getMapping("Time"));
                }
            }
        });
        
        // activates when user clicks legend item
        dynamicChart.addLegendClickHandler(new LegendClickHandler() {
            @Override
            public void onLegendClick(LegendClickEvent event) {
                FacetValue facetValue = event.getFacetValue();
                if ("Time".equals(dynamicChart.getFacets()[0].getIdAsString())) {
                    createChart(facetValue.getIdAsString(), null);
                } else {
                    createChart(null, facetValue.getIdAsString());
                }
            }
        });
        
        // activates when user clicks a label on x scale
        dynamicChart.addDataLabelClickHandler(new DataLabelClickHandler() {
            @Override
            public void onDataLabelClick(DataLabelClickEvent event) {
                FacetValue facetValue = event.getFacetValue();
                if ("Time".equals(dynamicChart.getFacets()[0].getIdAsString())) {
                    createChart(null, facetValue.getIdAsString());
                } else {
                    createChart(facetValue.getIdAsString(), null);
                }
            }
        });

        dynamicChart.setLegendHoverCustomizer(new LegendHoverCustomizer() {
            @Override
            public String hoverHTML(FacetValue facetValue, FacetValue metricFacetValue) {
                return "Click to show only data from " + facetValue.getTitle();
            }
        });

        dynamicChart.setDataLabelHoverHTMLCustomizer(new DataLabelHoverCustomizer() {
            @Override
            public String hoverHTML(FacetValue facetValue) {
                return "Click to show only data from " + facetValue.getTitle();
            }
        });

        AdvancedCriteria crit = new AdvancedCriteria(OperatorId.AND);
        crit.addCriteria("Time", OperatorId.STARTS_WITH, "Q");
        crit.addCriteria("Scenarios", OperatorId.EQUALS, "Actual");
        crit.addCriteria("Products", OperatorId.EQUALS, "Prod01");
        // if user selected a specific region by clicking a value or legend then fetch values
        // of the region
        if (region != null) {
            crit.addCriteria("Regions", OperatorId.EQUALS, region);
        } else if (time != null) {
            crit.addCriteria("Time", OperatorId.EQUALS, time);
        }
        // this sets the chart's data with values fetched from the dataSource
        DataSource ds = DataSource.get("productRevenue");
        ds.fetchData(crit, new DSCallback() {
            @Override
            public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                dynamicChart.setData(dsResponse.getData());
            }
        });
        dynamicChartLayout.addMember(dynamicChart);
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }
}
