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
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class ErrorBarsChart extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Charts can display error bars showing intervals of values around each data point.</p>";

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
            return new ErrorBarsChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final DataSource ds = DataSource.get("productRevenue");

        final FacetChart chart = new FacetChart();
        chart.setID("errorBars");
        chart.setTitle("Error Bars");
        chart.setChartType(ChartType.LINE);

        chart.setLowErrorMetric("lowValue");
        chart.setHighErrorMetric("highValue");
        chart.setShowDataPoints(true);
        chart.setStacked(false);
        chart.setShowLegend(false);

        Facet metricFacet = new Facet("metric");
        metricFacet.setInlinedValues(true);
        metricFacet.setValues(
            new FacetValue("value"), new FacetValue("lowValue"), new FacetValue("highValue"));

        Facet timeFacet = new Facet("Time");

        // Chart can auto-derive facet values from data.  The productRevenue
        // data source has values that are parents/sums of other values.  For example,
        // the value for "Q4-2016" is a sum of the values for "10/1/2016", "11/1/2016",
        // and "12/1/2016".  The sum values need to be excluded from the time facet
        // to avoid having a bizarre chart comparing sums to parts.
        String[] timeFacetValueIds = new String[] {
            "12/1/2016", "11/1/2016", "10/1/2016", "9/1/2016", "8/1/2016",
            "7/1/2016", "6/1/2016", "5/1/2016", "4/1/2016", "3/1/2016",
            "2/1/2016", "1/1/2016", "12/1/2015", "11/1/2015", "10/1/2015",
            "9/1/2015", "8/1/2015", "7/1/2015", "6/1/2015", "5/1/2015",
            "4/1/2015", "3/1/2015", "2/1/2015", "1/1/2015", "12/1/2014",
            "11/1/2014", "10/1/2014", "9/1/2014", "8/1/2014", "7/1/2014",
            "6/1/2014", "5/1/2014", "4/1/2014", "3/1/2014", "2/1/2014",
            "1/1/2014"
        };
        final int length = timeFacetValueIds.length;
        FacetValue[] timeFacetValues = new FacetValue[length];

        for (int i = 0; i < length; ++i) {
            String valueId = timeFacetValueIds[i];
            String year = valueId.substring(valueId.lastIndexOf("/") + 1);
            String month = valueId.substring(0, valueId.indexOf("/"));
            int quarter = (int) Math.ceil((double) Integer.parseInt(month) / 3.0);
            FacetValue facetValue = new FacetValue(valueId, valueId);
            facetValue.setParentId("Q" + quarter + "-" + year);
            timeFacetValues[i] = facetValue;
        }
        timeFacet.setValues(timeFacetValues);

        chart.setFacets(timeFacet, new Facet("Regions"), metricFacet);

        VLayout layout = new VLayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        layout.setMembers(chart);

        updateChartData(chart, ds);

        return layout;
    }

    private static void updateChartData(final FacetChart chart, DataSource productRevenue) {
        Criteria c = new Criteria();
        c.addCriteria("Scenarios", "Actual");
        c.addCriteria("Products", "sum");
        c.addCriteria("Regions", "North");

        productRevenue.fetchData(c, new DSCallback() {
                public void execute(DSResponse response, Object rawData, DSRequest request) {
                    setChartData(chart, response.getData());
                }
            });
    }

    private static void setChartData(FacetChart chart, Record[] data) {
        if (data != null) {
            for (int i = 0, length = data.length; i < length; i++) {
                Record record = data[i];
                float value = Float.parseFloat(record.getAttributeAsString("value"));
                record.setAttribute("lowValue", value * (1 - randomErrorPercentage()));
                record.setAttribute("highValue", value * (1 + randomErrorPercentage()));
            }
        }
        chart.setData(data);
    }

    private static float randomErrorPercentage() {
        return Math.max(0.05f, (float) Math.random() * 0.15f);
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }
}
