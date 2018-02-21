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
  
import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.DrawnValue;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.drawing.DrawSector;
import com.smartgwt.client.widgets.drawing.Point;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseMoveEvent;
import com.smartgwt.client.widgets.events.MouseMoveHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.smartgwt.client.widgets.viewer.DetailViewerRecord;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
    
public class CustomHovers extends ShowcasePanel {
    private static final int CHART_H = 400;
    
    private static final String DESCRIPTION =
    		"This example shows a custom hover interaction built using the " +
    		"<code>getNearestDrawnValue()</code> API to identify which point " +
    		"is nearest the mouse cursor.<p>" +
    		"A bright blue marker is placed over the data point nearest " +
    		"the mouse.  Clicking anywhere on the chart shows the information " +
    		"the chart provides about the nearest data point in a DetailViewer " +
    		"under the chart.<p>" +
    		"Note that FacetChart has two built-in hover behaviors - " +
    		"<code>showValueOnHover</code> and <code>showDataPoints</code> " +
    		"(which can show custom hovers).  This example focuses on custom " +
    		"hovers.";

    public static class Factory extends ChartSamplePanelFactory {

    	@Override
    	public String getDescription() {
    		return DESCRIPTION;
    	}

    	@Override
    	public String getNodeID() {
    		return "chartCustomHovers";
    	}

    	@Override
    	public ShowcasePanel createShowcasePanel() {
    		return new CustomHovers();
    	}
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }
    
    @Override
    public String getIntro() {
        return DESCRIPTION;
    }
    
	@Override
	public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();  
        chart.setData(MultiSeriesChartData.getData());  
        chart.setFacets(new Facet("time", "Period"), new Facet("region", "Region"));  
        chart.setValueProperty("value");  
        chart.setChartType(ChartType.AREA);  
        chart.setTitle("Revenue");
        chart.setHeight(CHART_H); 

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

        final Canvas marker = new Canvas();
        marker.setStyleName("blueMarker");
        marker.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                marker.hide();
            }
        });
        chart.addChild(marker);

        final DetailViewer viewer = new DetailViewer();
        viewer.setWidth100();
        viewer.setFields(new DetailViewerField());

        chart.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
                final Canvas eventTarget = EventHandler.getTarget();
                if (!marker.equals(eventTarget)) {
                    marker.hide();
                }
			}
		});
        chart.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				marker.show();
			}
		});
        chart.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
                final DrawnValue nearestDV = chart.getNearestDrawnValue();
                if (nearestDV == null) return;
                final Double radius = nearestDV.getRadius();
                if (radius != null) {
                    final Point arcMidpoint = DrawSector.getArcMidpoint(nearestDV.getX(), nearestDV.getY(),
                                                                        nearestDV.getStartAngle(),
                                                                        nearestDV.getEndAngle(),
                                                                        radius.doubleValue());
                    marker.moveTo(arcMidpoint.getX(), arcMidpoint.getY());
                } else {
                    marker.moveTo(nearestDV.getX(), nearestDV.getY());
                }
                marker.bringToFront();
            }
		});

        chart.addClickHandler(new ClickHandler() {
        	@Override
			public void onClick(ClickEvent event) {
                final DrawnValue nearestDV = chart.getNearestDrawnValue();
                if (nearestDV == null) return;

				DetailViewerRecord data[] = new DetailViewerRecord[1];
				data[0] = new DetailViewerRecord();
                data[0].setAttribute("value", nearestDV.getValueAsDouble());
                data[0].setAttribute("x", nearestDV.getX());
                data[0].setAttribute("y", nearestDV.getY());
                data[0].setAttribute("facetValue", JSON.encode(nearestDV.getFacetValues().getJsObj()));
                data[0].setAttribute("record", JSON.encode(nearestDV.getRecord().getJsObj()));

				List<DetailViewerField> fields = initialFields();

				if(nearestDV.getBarThickness()!=null) {
					DetailViewerField field;
					field = new DetailViewerField("barThickness", "Bar Thickness");
					fields.add(field);
					data[0].setAttribute("barThickness", nearestDV.getBarThickness());
				}

				if(nearestDV.getStartAngle()!=null) {
					DetailViewerField field;
					field = new DetailViewerField("startAngle", "Start Angle");
					fields.add(field);
					data[0].setAttribute("startAngle", nearestDV.getStartAngle());
					field = new DetailViewerField("endAngle", "End Angle");
					fields.add(field);
					data[0].setAttribute("endAngle", nearestDV.getEndAngle());
                    field = new DetailViewerField("radius", "Radius");
                    fields.add(field);
                    data[0].setAttribute("radius", nearestDV.getRadius());
				}

				viewer.setFields(fields.toArray(new DetailViewerField[fields.size()]));
				viewer.setData(data);

				viewer.show();
			}

            private List<DetailViewerField> initialFields() {
				List<DetailViewerField> retVal = new ArrayList<DetailViewerField>();
				DetailViewerField field;
				field = new DetailViewerField("value", "Value");
				retVal.add(field);
				field = new DetailViewerField("x", "X");
				retVal.add(field);
				field = new DetailViewerField("y", "Y");
				retVal.add(field);
				field = new DetailViewerField("facetValue", "Facet Value");
				retVal.add(field);
				field = new DetailViewerField("record", "Record");
				retVal.add(field);

				return retVal;
			}
		});


        VLayout layout = new VLayout(20);
        layout.setWidth100();
        layout.addMember(chartSelector);
        layout.addMember(chart);
        layout.addMember(viewer);

        return layout;
    }
}
