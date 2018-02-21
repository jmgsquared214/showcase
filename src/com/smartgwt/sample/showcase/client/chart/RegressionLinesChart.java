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
import com.smartgwt.client.types.RegressionLineType;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class RegressionLinesChart extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Scatter Charts support calculation of best-fit lines or curves, also known as " +
        "\"trend lines\" for time series data.  Shown below is a 3rd-degree polynomial " +
        "regression curve.</p>" +
        "<p>Use the controls above the chart to switch between linear or polynomial " +
        "regression and change the regression degree.  Users can also enable or disable " +
        "regression lines via the default context menu.</p>";

    public static class Factory extends ChartSamplePanelFactory {
        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "regressionLines";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new RegressionLinesChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setID("regressionChart");
        chart.setTitle("Time Series Trend Line");
        chart.setChartType(ChartType.SCATTER);

        Facet metricFacet = new Facet("metric");
        metricFacet.setInlinedValues(true);
        metricFacet.setValues(new FacetValue("value"), new FacetValue("time"));
        chart.setFacets(metricFacet);

        chart.setData(RegressionLinesChartData.getData());

        chart.setXAxisMetric("time");
        chart.setYAxisMetric("value");

        chart.setShowRegressionLine(true);
        chart.setRegressionLineType(RegressionLineType.POLYNOMIAL);
        chart.setRegressionPolynomialDegree(3);

        chart.setAutoDraw(false);
        chart.setChartRectMargin(15);

        DynamicForm form = new DynamicForm();
        form.setAutoDraw(false);

        CheckboxItem showItem = new CheckboxItem("showRegression", "Show / Hide Regression Line");
        showItem.setDefaultValue(chart.getShowRegressionLine());
        showItem.addChangedHandler(new ChangedHandler() {
                public void onChanged(ChangedEvent event) {
                    Boolean value = (Boolean) event.getValue();
                    chart.setShowRegressionLine(value == null || value.booleanValue() == false ? false : true);
                }
            });

        SelectItem lineTypeItem = new SelectItem("lineType", "Regression Type");
        lineTypeItem.setWrapTitle(false);
        lineTypeItem.setWidth(222);
        lineTypeItem.setValueMap("linear", "curve");
        lineTypeItem.setDefaultValue(chart.getRegressionLineType() == RegressionLineType.LINE ? "linear" : "curve");
        lineTypeItem.addChangedHandler(new ChangedHandler() {
                public void onChanged(ChangedEvent event) {
                    RegressionLineType type;
                    if ("linear".equals(event.getValue())) {
                        type = RegressionLineType.LINE;
                    } else {
                        type = RegressionLineType.POLYNOMIAL;
                    }
                    chart.setRegressionLineType(type);
                }
            });

        SpinnerItem degree = new SpinnerItem("degree", "Polynomial Degree");
        degree.setWrapTitle(false);
        degree.setDisabled(chart.getRegressionLineType() == RegressionLineType.LINE);
        degree.setDefaultValue(chart.getRegressionPolynomialDegree());
        degree.setMin(1);
        degree.setMax(5);
        degree.addChangedHandler(new ChangedHandler() {
                public void onChanged(ChangedEvent event) {
                    Integer degree = (Integer) event.getValue();
                    chart.setRegressionPolynomialDegree(degree.intValue());
                }
            });

        form.setItems(showItem, lineTypeItem, degree);

        VLayout layout = new VLayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        layout.setMembersMargin(20);
        layout.setMembers(form, chart);

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
            new SourceEntity("RegressionLinesChartData.java", JAVA, "source/chart/RegressionLinesChartData.java.html", false)
        };
    }
}
