/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2016 and beyond Isomorphic Software, Inc.
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

public class AutoScrollDataChartData extends Record {

    public AutoScrollDataChartData(String state, int population) {
        setAttribute("state",      state);
        setAttribute("population", population);
    }

    public static AutoScrollDataChartData[] getData() {
        return new AutoScrollDataChartData[] {
            new AutoScrollDataChartData("California", 33872),
            new AutoScrollDataChartData("Texas", 20852),
            new AutoScrollDataChartData("New York", 18976),
            new AutoScrollDataChartData("Florida", 15982),
            new AutoScrollDataChartData("Illinois", 12419),
            new AutoScrollDataChartData("Pennsylvania", 12281),
            new AutoScrollDataChartData("Ohio", 11353),
            new AutoScrollDataChartData("Michigan", 9938),
            new AutoScrollDataChartData("New Jersey", 8414),
            new AutoScrollDataChartData("Georgia", 8186),
            new AutoScrollDataChartData("North Carolina", 8049),
            new AutoScrollDataChartData("Virginia", 7079),
            new AutoScrollDataChartData("Massachusetts", 6349),
            new AutoScrollDataChartData("Indiana", 6080),
            new AutoScrollDataChartData("Washington", 5894),
            new AutoScrollDataChartData("Tennessee", 5689),
            new AutoScrollDataChartData("Missouri", 5595),
            new AutoScrollDataChartData("Wisconsin", 5364),
            new AutoScrollDataChartData("Maryland", 5296),
            new AutoScrollDataChartData("Arizona", 5131),
            new AutoScrollDataChartData("Minnesota", 4919),
            new AutoScrollDataChartData("Louisiana", 4469),
            new AutoScrollDataChartData("Alabama", 4447),
            new AutoScrollDataChartData("Colorado", 4301),
            new AutoScrollDataChartData("Kentucky", 4042),
            new AutoScrollDataChartData("South Carolina", 4012),
            new AutoScrollDataChartData("Oklahoma", 3451),
            new AutoScrollDataChartData("Oregon", 3421),
            new AutoScrollDataChartData("Connecticut", 3406),
            new AutoScrollDataChartData("Iowa", 2926),
            new AutoScrollDataChartData("Mississippi", 2845),
            new AutoScrollDataChartData("Kansas", 2688),
            new AutoScrollDataChartData("Arkansas", 2673),
            new AutoScrollDataChartData("Utah", 2233),
            new AutoScrollDataChartData("Nevada", 1998),
            new AutoScrollDataChartData("New Mexico", 1819),
            new AutoScrollDataChartData("West Virginia", 1808),
            new AutoScrollDataChartData("Nebraska", 1711),
            new AutoScrollDataChartData("Idaho", 1294),
            new AutoScrollDataChartData("Maine", 1275),
            new AutoScrollDataChartData("New Hampshire", 1236),
            new AutoScrollDataChartData("Hawaii", 1212),
            new AutoScrollDataChartData("Rhode Island", 1048),
            new AutoScrollDataChartData("Montana", 902),
            new AutoScrollDataChartData("Delaware", 784),
            new AutoScrollDataChartData("South Dakota", 755),
            new AutoScrollDataChartData("North Dakota", 642),
            new AutoScrollDataChartData("Alaska", 627),
            new AutoScrollDataChartData("Vermont", 609),
            new AutoScrollDataChartData("Wyoming", 494),
        };
    }
}
