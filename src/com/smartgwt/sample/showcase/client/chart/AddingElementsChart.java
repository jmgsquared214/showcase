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
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.ChartBackgroundDrawnEvent;
import com.smartgwt.client.widgets.chart.ChartBackgroundDrawnHandler;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.drawing.DrawLine;
import com.smartgwt.client.widgets.drawing.Point;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class AddingElementsChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "<p>New elements can be added to charts as shown in this example.</p>" +
            "<p>The average revenue is computed then drawn on the chart with a light blue line.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "addingNewElements";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new AddingElementsChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final DataSource ds = DataSource.get("productRevenue");

        final FacetChart chart = new FacetChart();

        chart.setFacets(new Facet("Regions", "Region"));
        chart.setValueProperty("value");
        chart.setChartType(ChartType.AREA);
        chart.setStacked(Boolean.FALSE);
        chart.setTitle("Revenue");
        chart.setChartType(ChartType.COLUMN);

        chart.addChartBackgroundDrawnHandler(new ChartBackgroundDrawnHandler() {
            @Override
            public void onChartBackgroundDrawn(ChartBackgroundDrawnEvent event) {

                RecordList rec = chart.getDataAsRecordList();

                float sum = 0;

                for(int i = 0;i < rec.getLength();i++) {

                    Record r = rec.get(i);

                    sum += Float.parseFloat(r.getAttribute("value"));
                }

                //GWT.log("sum="+sum+" rec len="+rec.getLength()+" avg="+sum/rec.getLength());

                int top = (int)chart.getYCoord( sum / rec.getLength());
                int x1 = (int)chart.getChartLeft();
                int x2 = (int)(chart.getChartLeft() + chart.getChartWidth(false));

                DrawLine d = new DrawLine();
                d.setStartPoint(new Point(x1, top));
                d.setEndPoint(new Point(x2, top));
                d.setLineColor("lightblue");
                d.setLineWidth(2);

                chart.addDrawItem(d, true);
            }
        });

        VLayout layout = new VLayout(15);
        layout.addMember(chart);

        Criteria c = new Criteria("Products", "Prod01");
        c.addCriteria("Regions", new String[] {"North", "South", "East", "West"});
        c.addCriteria("Time", "sum");
        c.addCriteria("Scenarios","budget");

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
}
