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

import java.util.Date;

public class CustomTicksChartData extends Record {

    public CustomTicksChartData(Date time, float value, String animal) {
        setAttribute("Time", time);
        setAttribute("value", value);
        setAttribute("animal", animal);
    }

    public static CustomTicksChartData[] getData() {
        return new CustomTicksChartData[] {
            new CustomTicksChartData(new Date(115, 0, 2),  0.02f, "Moose"),
            new CustomTicksChartData(new Date(115, 0, 8),  0.15f, "Moose"),
            new CustomTicksChartData(new Date(115, 0, 22), 0.77f, "Moose"),

            new CustomTicksChartData(new Date(115, 1, 12), 0.87f, "Moose"),
            new CustomTicksChartData(new Date(115, 3, 1),  1.15f, "Moose"),
            new CustomTicksChartData(new Date(115, 6, 1),  1.15f, "Moose"),
            new CustomTicksChartData(new Date(116, 0, 1),  0.71f, "Moose"),

            new CustomTicksChartData(new Date(116, 3, 1),   0.67f, "Moose"),
            new CustomTicksChartData(new Date(116, 6, 1),   0.61f, "Moose"),
            new CustomTicksChartData(new Date(116, 9, 1),   0.41f, "Moose"),
            new CustomTicksChartData(new Date(116, 11, 31), 0.22f, "Moose"),

            new CustomTicksChartData(new Date(115, 0, 2),  0.02f, "Platypus"),
            new CustomTicksChartData(new Date(115, 0, 8),  0.28f, "Platypus"),
            new CustomTicksChartData(new Date(115, 0, 22), 0.71f, "Platypus"),
            new CustomTicksChartData(new Date(115, 1, 12), 0.81f, "Platypus"),

            new CustomTicksChartData(new Date(115, 3, 1),   1.06f, "Platypus"),
            new CustomTicksChartData(new Date(115, 6, 1),   1.06f, "Platypus"),
            new CustomTicksChartData(new Date(116, 0, 1),   0.52f, "Platypus"),
            new CustomTicksChartData(new Date(116, 11, 31), 0.10f, "Platypus")
        };
    }

}
