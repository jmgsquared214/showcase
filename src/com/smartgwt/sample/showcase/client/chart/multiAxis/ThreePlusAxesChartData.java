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

public class ThreePlusAxesChartData extends Record {

    public ThreePlusAxesChartData(String area, String date, float percent, int events, int throughput) {
        setAttribute("area", area);
        setAttribute("date", date);
        setAttribute("percent", percent);
        setAttribute("events", events);
        setAttribute("throughput", throughput);
    }

    public static ThreePlusAxesChartData[] getData() {
        return new ThreePlusAxesChartData[] {
            new ThreePlusAxesChartData("1", "13 Sep 16", 0.55f, 8751, 20),
            new ThreePlusAxesChartData("2", "13 Sep 16", 0.32f, 3210, 24),
            new ThreePlusAxesChartData("3", "13 Sep 16", 0.21f, 2071, 28),
            new ThreePlusAxesChartData("1", "14 Sep 16", 0.49f, 6367, 30),
            new ThreePlusAxesChartData("2", "14 Sep 16", 0.41f, 3771, 36),
            new ThreePlusAxesChartData("3", "14 Sep 16", 0.22f, 2166, 39),
            new ThreePlusAxesChartData("1", "15 Sep 16", 0.7f, 6011, 41),
            new ThreePlusAxesChartData("2", "15 Sep 16", 0.19f, 1950, 45),
            new ThreePlusAxesChartData("3", "15 Sep 16", 0.25f, 2375, 48),
            new ThreePlusAxesChartData("1", "16 Sep 16", 0.47f, 9234, 51),
            new ThreePlusAxesChartData("2", "16 Sep 16", 0.25f, 4321, 55),
            new ThreePlusAxesChartData("3", "16 Sep 16", 0.3f, 909, 59),
            new ThreePlusAxesChartData("1", "17 Sep 16", 0.3f, 6144, 54),
            new ThreePlusAxesChartData("2", "17 Sep 16", 0.44f, 4077, 50),
            new ThreePlusAxesChartData("3", "17 Sep 16", 0.06f, 1477, 52),
            new ThreePlusAxesChartData("1", "18 Sep 16", 0.7f, 8502, 48),
            new ThreePlusAxesChartData("2", "18 Sep 16", 0.29f, 3061, 44),
            new ThreePlusAxesChartData("3", "18 Sep 16", 0.22f, 2955, 42),
            new ThreePlusAxesChartData("1", "19 Sep 16", 0.45f, 7020, 53),
            new ThreePlusAxesChartData("2", "19 Sep 16", 0.22f, 3040, 59),
            new ThreePlusAxesChartData("3", "19 Sep 16", 0.31f, 2177, 53),
            new ThreePlusAxesChartData("1", "20 Sep 16", 0.69f, 6712, 48),
            new ThreePlusAxesChartData("2", "20 Sep 16", 0.21f, 4981, 42),
            new ThreePlusAxesChartData("3", "20 Sep 16", 0.12f, 1234, 45),
            new ThreePlusAxesChartData("1", "21 Sep 16", 0.6f, 9321, 48),
            new ThreePlusAxesChartData("2", "21 Sep 16", 0.29f, 6532, 49),
            new ThreePlusAxesChartData("3", "21 Sep 16", 0.35f, 6622, 45),
            new ThreePlusAxesChartData("1", "22 Sep 16", 0.37f, 8389, 48),
            new ThreePlusAxesChartData("2", "22 Sep 16", 0.35f, 5104, 51),
            new ThreePlusAxesChartData("3", "22 Sep 16", 0.3f, 5111, 55),
            new ThreePlusAxesChartData("1", "23 Sep 16", 0.4f, 7555, 49),
            new ThreePlusAxesChartData("2", "23 Sep 16", 0.34f, 2345, 52),
            new ThreePlusAxesChartData("3", "23 Sep 16", 0.16f, 3456, 57),
            new ThreePlusAxesChartData("1", "24 Sep 16", 0.62f, 9567, 60),
            new ThreePlusAxesChartData("2", "24 Sep 16", 0.12f, 5678, 55),
            new ThreePlusAxesChartData("3", "24 Sep 16", 0.37f, 6789, 51)
        };
    }

}
