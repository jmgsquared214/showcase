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
package com.smartgwt.sample.showcase.client.chart.multiAxis;

import com.smartgwt.client.data.Record;

public class MultiAxisChartData extends Record {

    public MultiAxisChartData(String area, String date, float percent, int events) {
        setAttribute("area", area);
        setAttribute("date", date);
        setAttribute("percent", percent);
        setAttribute("events", events);
    }

    public static MultiAxisChartData[] getData() {
        return new MultiAxisChartData[] {
            new MultiAxisChartData("1", "13 Sep 16", 0.55f, 8751),
            new MultiAxisChartData("2", "13 Sep 16", 0.32f, 3210),
            new MultiAxisChartData("3", "13 Sep 16", 0.21f, 2071),
            new MultiAxisChartData("1", "14 Sep 16", 0.49f, 6367),
            new MultiAxisChartData("2", "14 Sep 16", 0.41f, 3771),
            new MultiAxisChartData("3", "14 Sep 16", 0.22f, 2166),
            new MultiAxisChartData("1", "15 Sep 16", 0.7f, 6011),
            new MultiAxisChartData("2", "15 Sep 16", 0.19f, 1950),
            new MultiAxisChartData("3", "15 Sep 16", 0.25f, 2375),
            new MultiAxisChartData("1", "16 Sep 16", 0.47f, 9234),
            new MultiAxisChartData("2", "16 Sep 16", 0.25f, 4321),
            new MultiAxisChartData("3", "16 Sep 16", 0.3f, 909),
            new MultiAxisChartData("1", "17 Sep 16", 0.3f, 6144),
            new MultiAxisChartData("2", "17 Sep 16", 0.44f, 4077),
            new MultiAxisChartData("3", "17 Sep 16", 0.06f, 1477),
            new MultiAxisChartData("1", "18 Sep 16", 0.7f, 8502),
            new MultiAxisChartData("2", "18 Sep 16", 0.29f, 3061),
            new MultiAxisChartData("3", "18 Sep 16", 0.22f, 2955),
            new MultiAxisChartData("1", "19 Sep 16", 0.45f, 7020),
            new MultiAxisChartData("2", "19 Sep 16", 0.22f, 3040),
            new MultiAxisChartData("3", "19 Sep 16", 0.31f, 2177),
            new MultiAxisChartData("1", "20 Sep 16", 0.69f, 6712),
            new MultiAxisChartData("2", "20 Sep 16", 0.21f, 4981),
            new MultiAxisChartData("3", "20 Sep 16", 0.12f, 1234),
            new MultiAxisChartData("1", "21 Sep 16", 0.6f, 9321),
            new MultiAxisChartData("2", "21 Sep 16", 0.29f, 6532),
            new MultiAxisChartData("3", "21 Sep 16", 0.35f, 6622),
            new MultiAxisChartData("1", "22 Sep 16", 0.37f, 8389),
            new MultiAxisChartData("2", "22 Sep 16", 0.35f, 5104),
            new MultiAxisChartData("3", "22 Sep 16", 0.3f, 5111),
            new MultiAxisChartData("1", "23 Sep 16", 0.4f, 7555),
            new MultiAxisChartData("2", "23 Sep 16", 0.34f, 2345),
            new MultiAxisChartData("3", "23 Sep 16", 0.16f, 3456),
            new MultiAxisChartData("1", "24 Sep 16", 0.62f, 9567),
            new MultiAxisChartData("2", "24 Sep 16", 0.12f, 5678),
            new MultiAxisChartData("3", "24 Sep 16", 0.37f, 6789)
        };
    }

}
