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

public class BubbleChartData extends Record {

    private static enum Series { A, B };

    private BubbleChartData(Series series, double seconds, double measurement, double volume) {
        setAttribute("series", series.name());
        setAttribute("time", seconds);
        setAttribute("value", measurement);
        setAttribute("volume", volume);
    }

    public static Record[] getData() {
        return new Record[] {
            new BubbleChartData( Series.A,   124.76,    872.74,   119.34),
            new BubbleChartData( Series.A,     67.7,    803.78,   150.98),
            new BubbleChartData( Series.A,   121.52,    893.74,   122.34),
            new BubbleChartData( Series.A,   140.26,    1058.9,   175.75),
            new BubbleChartData( Series.B,    13.99,    330.99,   132.89),
            new BubbleChartData( Series.A,   182.62,   1271.87,   188.63),
            new BubbleChartData( Series.B,   101.02,    741.39,    61.22),
            new BubbleChartData( Series.B,   123.63,    889.17,    82.86),
            new BubbleChartData( Series.A,   157.81,   1120.41,   149.94),
            new BubbleChartData( Series.A,   113.58,   1082.05,   201.14),
            new BubbleChartData( Series.B,   101.32,    745.61,    57.95),
            new BubbleChartData( Series.B,    79.68,    647.01,    70.15),
            new BubbleChartData( Series.B,    43.76,    358.67,   122.88),
            new BubbleChartData( Series.B,    42.98,    352.61,   121.42),
            new BubbleChartData( Series.A,    44.93,    758.24,   174.26),
            new BubbleChartData( Series.B,   136.74,    776.52,   120.16),
            new BubbleChartData( Series.A,   152.33,   1032.01,   127.49),
            new BubbleChartData( Series.A,   187.01,   1376.61,   229.85),
            new BubbleChartData( Series.A,    37.39,    607.86,   153.11),
            new BubbleChartData( Series.B,    80.81,    649.03,     62.1),
            new BubbleChartData( Series.B,    131.7,    852.09,    118.1),
            new BubbleChartData( Series.A,   180.23,   1206.12,   151.13),
            new BubbleChartData( Series.B,    30.43,    450.04,   108.26),
            new BubbleChartData( Series.B,    46.86,    423.69,   124.61),
            new BubbleChartData( Series.A,   182.51,   1268.61,   186.93),
            new BubbleChartData( Series.B,   140.82,    881.86,   126.84),
            new BubbleChartData( Series.B,   163.98,   1055.45,   148.91),
            new BubbleChartData( Series.B,     8.63,    249.77,   111.12),
            new BubbleChartData( Series.A,    94.42,    990.07,   191.69),
            new BubbleChartData( Series.B,    28.85,    395.48,   115.25),
            new BubbleChartData( Series.B,   184.12,   1120.43,   149.84),
            new BubbleChartData( Series.B,    60.95,    513.08,    77.84),
            new BubbleChartData( Series.A,     3.36,    447.98,   266.62),
            new BubbleChartData( Series.A,    71.07,    824.26,   162.78),
            new BubbleChartData( Series.B,    51.19,    534.95,   109.77),
            new BubbleChartData( Series.B,    64.99,    499.07,    94.86),
            new BubbleChartData( Series.B,     56.2,     556.6,    81.48),
            new BubbleChartData( Series.B,    93.39,     633.6,   137.34),
            new BubbleChartData( Series.B,   137.18,    781.01,   120.42),
            new BubbleChartData( Series.A,   175.62,    1140.6,   111.41),
            new BubbleChartData( Series.A,     7.47,    318.45,   172.31),
            new BubbleChartData( Series.A,    24.53,     599.9,   213.34),
            new BubbleChartData( Series.A,     54.6,     674.9,   125.08),
            new BubbleChartData( Series.B,   106.79,     785.4,    66.24),
            new BubbleChartData( Series.A,   188.03,    1383.8,   227.84),
            new BubbleChartData( Series.B,    20.75,    232.63,   180.98),
            new BubbleChartData( Series.B,    81.52,    648.43,    58.11),
            new BubbleChartData( Series.B,    63.52,    499.85,    87.56),
            new BubbleChartData( Series.A,    32.72,    524.03,   146.33),
            new BubbleChartData( Series.A,    84.16,    714.43,    76.38)
        };
    }
}
