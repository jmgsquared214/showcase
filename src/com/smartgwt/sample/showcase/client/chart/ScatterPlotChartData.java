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

import com.smartgwt.client.data.Record;

public class ScatterPlotChartData extends Record {

    public ScatterPlotChartData(float time, float value, String animal) {
        setAttribute("Time", time);
        setAttribute("value", value);
        setAttribute("animal", animal);
    }

    public static ScatterPlotChartData[] getData() {
        return new ScatterPlotChartData[] {
            new ScatterPlotChartData(0.033f, 0.02f, "Moose"),
            new ScatterPlotChartData(0.083f, 0.15f, "Moose"),
            new ScatterPlotChartData(0.25f, 0.77f, "Moose"),
            new ScatterPlotChartData(0.25f, 0.77f, "Moose"),

            new ScatterPlotChartData(0.5f, 0.87f, "Moose"),
            new ScatterPlotChartData(1f, 1.15f, "Moose"),
            new ScatterPlotChartData(2f, 1.15f, "Moose"),
            new ScatterPlotChartData(4f, 0.71f, "Moose"),

            new ScatterPlotChartData(5f, 0.67f, "Moose"),
            new ScatterPlotChartData(6f, 0.61f, "Moose"),
            new ScatterPlotChartData(7f, 0.41f, "Moose"),
            new ScatterPlotChartData(8f, 0.22f, "Moose"),

            new ScatterPlotChartData(0.033f, 0.02f, "Platypus"),
            new ScatterPlotChartData(0.083f, 0.28f, "Platypus"),
            new ScatterPlotChartData(0.25f, 0.71f, "Platypus"),
            new ScatterPlotChartData(0.5f, 0.81f, "Platypus"),

            new ScatterPlotChartData(1f, 1.06f, "Platypus"),
            new ScatterPlotChartData(2f, 1.06f, "Platypus"),
            new ScatterPlotChartData(4f, 0.52f, "Platypus"),
            new ScatterPlotChartData(8f, 0.10f, "Platypus")
        };
    }

}
