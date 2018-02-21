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

public class SimpleChartData extends Record {

    public SimpleChartData(String region, String product, Integer sales) {
        setAttribute("region", region);
        setAttribute("product", product);
        setAttribute("sales", sales);
    }

    public static SimpleChartData[] getData() {
        return new SimpleChartData[] {
            new SimpleChartData("West", "Cars", 37),
            new SimpleChartData("North", "Cars", 29),
            new SimpleChartData("East", "Cars", 80),
            new SimpleChartData("South", "Cars", 87),

            new SimpleChartData("West", "Trucks", 23),
            new SimpleChartData("North", "Trucks", 45),
            new SimpleChartData("East", "Trucks", 32),
            new SimpleChartData("South", "Trucks", 67),

            new SimpleChartData("West", "Motorcycles", 12),
            new SimpleChartData("North", "Motorcycles", 4),
            new SimpleChartData("East", "Motorcycles", 23),
            new SimpleChartData("South", "Motorcycles", 45)
        };
    }

}
