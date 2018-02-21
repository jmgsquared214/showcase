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

public class HistogramChartData extends Record {

    public HistogramChartData(String water, String pollutant, int min, int max, int danger) {
        setAttribute("water", water);
        setAttribute("pollutant", pollutant);
        setAttribute("minValue", min);
        setAttribute("maxValue", max);
        setAttribute("danger", danger);
    }

    public static HistogramChartData[] getData() {
        return new HistogramChartData[] {
            new HistogramChartData("Tap", "Metals",    10, 20, 100),
            new HistogramChartData("Tap", "Organics",  20, 40, 80),
            new HistogramChartData("Tap", "Pathogens", 3,  8,  25),

            new HistogramChartData("Lake", "Metals",    50, 60, 100),
            new HistogramChartData("Lake", "Organics",  55, 80, 80),
            new HistogramChartData("Lake", "Pathogens", 15, 25, 25),

            new HistogramChartData("Ocean", "Metals",    20, 70, 100),
            new HistogramChartData("Ocean", "Organics",  50, 95, 80),
            new HistogramChartData("Ocean", "Pathogens", 10, 45, 25),

            new HistogramChartData("Bottled", "Metals",    5, 10, 100),
            new HistogramChartData("Bottled", "Organics",  5, 25, 80),
            new HistogramChartData("Bottled", "Pathogens", 0, 0,  25)
        };
    }
}
