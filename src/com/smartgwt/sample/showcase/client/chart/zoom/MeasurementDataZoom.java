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
package com.smartgwt.sample.showcase.client.chart.zoom;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.LabelCollapseMode;
import com.smartgwt.client.types.LabelRotationMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.chart.ChartSamplePanelFactory;

public class MeasurementDataZoom  extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows about 800 samples points from a continuous varying numerical function (a <a href='http://en.wikipedia.org/wiki/Bode_plot'>Bode plot</a> of a <a href='http://en.wikipedia.org/wiki/Chebyshev_filter'>Chebyshev</a> filter).</p>"+
            "<p>Use the zoom chart to focus in on small parts of the dataset and note that the line remains smooth due to the large number of samples. Horizontal gradations automatically adjust to the range of the dataset being shown.</p>" +
            "<p>This zoom mode can be used to show various kinds of measurement data where many samples are taken.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "measurementDataZoom";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new MeasurementDataZoom();
        }
    }

    // 4th order Chebyshev polynomial
    private static final int POLYNOMIAL_ORDER = 4;

    @Override
    public Canvas getViewPanel() {
        FacetChart chart = new FacetChart();
        chart.setShowTitle(false);
        chart.setWidth100();
        chart.setHeight100();
        chart.setMargin(5);
        
        chart.setAllowedChartTypes(new ChartType[] {ChartType.AREA, ChartType.LINE});
        chart.setChartType(ChartType.LINE);
        chart.setRotateLabels(LabelRotationMode.NEVER);
        chart.setMinLabelGap(5);
        chart.setLabelCollapseMode(LabelCollapseMode.NUMERIC);

        chart.setData(getData());
        chart.setFacets(new Facet("frequency", "Frequency / Cutoff Frequency"),
            new Facet("rippleFactor"));
        chart.setValueTitle("Gain (in decibels)");
        chart.setValueProperty("gain");
        chart.setDecimalPrecision(6);

        chart.setCanZoom(true);
        chart.setZoomLogScale(false);
        chart.setZoomShowSelection(false);

        return chart;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }
    
    private Record[] getData() {
        int kHz = 1000;
        int f = 10 * kHz;
        double w0 = 2.0 * Math.PI * f;
        
        double wdw0Min = 0.0;
        double wdw0Max = 2.0;
        
        int len = 800;
        Record[] res = new Record[len * 2];
        for (int i = 0; i < len; i++) {
            double lambda = (double)i / (double)(len - 1);
            double wdw0 = (1.0 - lambda) * wdw0Min + lambda * wdw0Max;
            double w = wdw0 * w0;
            
            res[i * 2] = new Record();
            res[i * 2].setAttribute("frequency", wdw0);
            res[i * 2].setAttribute("rippleFactor", "Ripple Factor 2.0");
            res[i * 2].setAttribute("gain", this.dB(this.gain(2.0, w0, w)));

            res[i * 2 + 1] = new Record();
            res[i * 2 + 1].setAttribute("frequency", wdw0);
            res[i * 2 + 1].setAttribute("rippleFactor", "Ripple Factor 10.0");
            res[i * 2 + 1].setAttribute("gain", this.dB(this.gain(10.0, w0, w)));
        }
        return res;
    }

    private double dB(double x) {
        return 20.0 * Math.log10(x);
    }

    private double gain(double e, double w0, double w) {
        double e2 = e * e;
        double wdw0 = w / w0;
        double tnwdw0 = this.createChebyshevPolynomial(wdw0);
        double tnwdw02 = tnwdw0 * tnwdw0;
        return 1.0 / Math.sqrt(1.0 + e2 * tnwdw02);
    }

    public double createChebyshevPolynomial(double x) {
        return expression(POLYNOMIAL_ORDER, x);
    }

    public double expression(int n, double x) {
        double res = 0.0;
        if (n == 0) {
            res = 1.0;
        } else if (n == 1) {
            res = x;
        } else {
            res = 2.0 * x * (expression(n - 1, x)) - expression(n - 2, x);
        }
        return res;
    }
}