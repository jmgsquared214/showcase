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

public class ColorScaleChartData extends Record {

    private static enum Series { A, B };

    private ColorScaleChartData(Series series, double seconds, double measurement, double volume, double heat) {
        setAttribute("series", series.name());
        setAttribute("time", seconds);
        setAttribute("value", measurement);
        setAttribute("volume", volume);
        setAttribute("heat", heat);
    }

    public static Record[] getData() {
        return new Record[] {
            new ColorScaleChartData( Series.B,  121.33,   1108.02,    270.75,    14.52),
            new ColorScaleChartData( Series.A,  91.13,    808.26,     174.3,     112.18),
            new ColorScaleChartData( Series.B,  40.59,    1343.79,    372.61,    100.35),
            new ColorScaleChartData( Series.B,  43.26,    1669.22,    383.46,    217.34),
            new ColorScaleChartData( Series.A,  160.12,   1252.15,    221.53,    23.59),
            new ColorScaleChartData( Series.B,  84.16,    1225.68,    209.86,    41.66),
            new ColorScaleChartData( Series.B,  77.49,    1070.77,    280.73,    12.06),
            new ColorScaleChartData( Series.A,  98.58,    849.73,     210.82,    99.87),
            new ColorScaleChartData( Series.B,  181.56,   1238.87,    290.31,    10.97),
            new ColorScaleChartData( Series.A,  197.37,   1509.43,    236.01,    103.29),
            new ColorScaleChartData( Series.B,  137.08,   1157.38,    152.69,    2.37),
            new ColorScaleChartData( Series.A,  92.05,    885.46,     180.25,    84.54),
            new ColorScaleChartData( Series.A,  104.73,   831.93,     232.78,    108.56),
            new ColorScaleChartData( Series.A,  194.69,   1406.32,    225.47,    66.89),
            new ColorScaleChartData( Series.A,  87.87,    879.87,     155.13,    85.04),
            new ColorScaleChartData( Series.B,  52.27,    1402.3,     482.11,    117.3),
            new ColorScaleChartData( Series.A,  193.7,    1571.23,    219.45,    127.02),
            new ColorScaleChartData( Series.A,  131.35,   1161.24,    123.79,    1.12),
            new ColorScaleChartData( Series.A,  3.6,      553.28,     449.69,    172.73),
            new ColorScaleChartData( Series.A,  54.11,    725.29,     358.03,    128.78),
            new ColorScaleChartData( Series.A,  26.21,    867.39,     200.06,    67.11),
            new ColorScaleChartData( Series.B,  137.87,   1165.61,    152.19,    0.33),
            new ColorScaleChartData( Series.A,  119.94,   1139.37,    204.71,    2.65),
            new ColorScaleChartData( Series.A,  74.47,    905.56,     228.56,    70.85),
            new ColorScaleChartData( Series.A,  42.99,    596.3,      273.07,    171.48),
            new ColorScaleChartData( Series.A,  149.48,   1082.28,    143.72,    34.11),
            new ColorScaleChartData( Series.B,  122.86,   976.88,     253.24,    62.62),
            new ColorScaleChartData( Series.B,  108.43,   1328.97,    344.17,    70.27),
            new ColorScaleChartData( Series.A,  151.42,   1158.54,    158.97,    7.17),
            new ColorScaleChartData( Series.A,  199.35,   1392.55,    237.92,    60.2),
            new ColorScaleChartData( Series.A,  54.33,    813.15,     359.18,    97.01),
            new ColorScaleChartData( Series.B,  155.06,   1542.14,    267.23,    130.55),
            new ColorScaleChartData( Series.A,  93.08,    791.92,     186.6,     118.82),
            new ColorScaleChartData( Series.B,  169.81,   1443.66,    315.8,     89.48),
            new ColorScaleChartData( Series.B,  83.2,     1115.06,    214.21,    1.92),
            new ColorScaleChartData( Series.B,  16.94,    1041.48,    446.34,    0.62),
            new ColorScaleChartData( Series.A,  95.29,    832.98,     198.3,     104.74),
            new ColorScaleChartData( Series.A,  151.94,   1105.88,    163.43,    26.45),
            new ColorScaleChartData( Series.B,  152.93,   1574.95,    241.01,    143.22),
            new ColorScaleChartData( Series.B,  33.78,    1257.78,    304.3,     71.65),
            new ColorScaleChartData( Series.A,  67.42,    943.87,     272.6,     54.39),
            new ColorScaleChartData( Series.B,  183.59,   1354.74,    273.41,    52.23),
            new ColorScaleChartData( Series.B,  54.46,    1346.56,    503.73,    96.3),
            new ColorScaleChartData( Series.B,  119.28,   1117.24,    293.94,    10.44),
            new ColorScaleChartData( Series.B,  90.32,    1492.93,    236.66,    136.3),
            new ColorScaleChartData( Series.B,  182.89,   1237.01,    278.86,    9.81),
            new ColorScaleChartData( Series.A,  172.44,   1438.14,    229.65,    86.52),
            new ColorScaleChartData( Series.B,  0.96,     871.81,     657.88,    56.31),
            new ColorScaleChartData( Series.A,  137.14,   1191.7,     109.02,    10.05),
            new ColorScaleChartData( Series.B,  69.8,     922.65,     361.29,    62.95)
        };
    }
}
