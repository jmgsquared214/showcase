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

public class MixedPlotsChartData extends Record {

    private MixedPlotsChartData(String time, String region, double value, Double avg) {
        setAttribute("time", time);
        setAttribute("region", region);
        setAttribute("value", value);
        setAttribute("avg", avg);
    }

    public static MixedPlotsChartData[] getData() {
        return new MixedPlotsChartData[] {
            new MixedPlotsChartData("1/1/2014", "North", 273301.3, 252814.96),
            new MixedPlotsChartData("1/1/2014", "South", 236640.29, null),
            new MixedPlotsChartData("1/1/2014", "East", 248503.3, null),
            new MixedPlotsChartData("2/1/2014", "North", 223927.43, 207244.16),
            new MixedPlotsChartData("2/1/2014", "South", 204233.69, null),
            new MixedPlotsChartData("2/1/2014", "East", 193571.37, null),
            new MixedPlotsChartData("3/1/2014", "North", 194384.87, 195947.73),
            new MixedPlotsChartData("3/1/2014", "South", 158575.65, null),
            new MixedPlotsChartData("3/1/2014", "East", 200993.35, null),
            new MixedPlotsChartData("4/1/2014", "North", 120787.66, 173890.17),
            new MixedPlotsChartData("4/1/2014", "South", 108664.38, null),
            new MixedPlotsChartData("4/1/2014", "East", 159873.11, null),
            new MixedPlotsChartData("5/1/2014", "North", 105341.85, 140977.45),
            new MixedPlotsChartData("5/1/2014", "South", 118100.72, null),
            new MixedPlotsChartData("5/1/2014", "East", 102075.45, null),
            new MixedPlotsChartData("6/1/2014", "North", 129535.28, 124700.46),
            new MixedPlotsChartData("6/1/2014", "South", 124477.2, null),
            new MixedPlotsChartData("6/1/2014", "East", 153448.5, null),
            new MixedPlotsChartData("7/1/2014", "North", 188027.83, 145510.17),
            new MixedPlotsChartData("7/1/2014", "South", 184790.62, null),
            new MixedPlotsChartData("7/1/2014", "East", 203794.04, null),
            new MixedPlotsChartData("8/1/2014", "North", 253405.04, 191837.88),
            new MixedPlotsChartData("8/1/2014", "South", 268266.22, null),
            new MixedPlotsChartData("8/1/2014", "East", 220796.2, null),
            new MixedPlotsChartData("9/1/2014", "North", 208493.32, 210964.16),
            new MixedPlotsChartData("9/1/2014", "South", 180585.5, null),
            new MixedPlotsChartData("9/1/2014", "East", 190518.7, null),
            new MixedPlotsChartData("10/1/2014", "North", 232447.47, 233237.09),
            new MixedPlotsChartData("10/1/2014", "South", 268766.1, null),
            new MixedPlotsChartData("10/1/2014", "East", 275855.25, null),
            new MixedPlotsChartData("11/1/2014", "North", 255063.17, 238155.79),
            new MixedPlotsChartData("11/1/2014", "South", 232309.79, null),
            new MixedPlotsChartData("11/1/2014", "East", 299362.79, null),
            new MixedPlotsChartData("12/1/2014", "North", 327862, 271830.43),
            new MixedPlotsChartData("12/1/2014", "South", 257422.85, null),
            new MixedPlotsChartData("12/1/2014", "East", 297384.45, null),
            new MixedPlotsChartData("1/1/2015", "North", 198744.42, 251929.62),
            new MixedPlotsChartData("1/1/2015", "South", 202448.27, null),
            new MixedPlotsChartData("1/1/2015", "East", 196768.88, null),
            new MixedPlotsChartData("2/1/2015", "North", 247706.63, 250501.81),
            new MixedPlotsChartData("2/1/2015", "South", 261700.59, null),
            new MixedPlotsChartData("2/1/2015", "East", 264478.17, null),
            new MixedPlotsChartData("3/1/2015", "North", 162535.11, 218096.33),
            new MixedPlotsChartData("3/1/2015", "South", 214015.47, null),
            new MixedPlotsChartData("3/1/2015", "East", 214469.42, null),
            new MixedPlotsChartData("4/1/2015", "North", 179732.18, 206283.06),
            new MixedPlotsChartData("4/1/2015", "South", 140931.62, null),
            new MixedPlotsChartData("4/1/2015", "East", 170978.35, null),
            new MixedPlotsChartData("5/1/2015", "North", 139900.11, 174773.97),
            new MixedPlotsChartData("5/1/2015", "South", 181184.74, null),
            new MixedPlotsChartData("5/1/2015", "East", 169218.76, null),
            new MixedPlotsChartData("6/1/2015", "North", 150541.13, 158317.9),
            new MixedPlotsChartData("6/1/2015", "South", 135382.93, null),
            new MixedPlotsChartData("6/1/2015", "East", 156991.25, null),
            new MixedPlotsChartData("7/1/2015", "North", 206302.8, 171598.23),
            new MixedPlotsChartData("7/1/2015", "South", 179606.25, null),
            new MixedPlotsChartData("7/1/2015", "East", 225256.12, null),
            new MixedPlotsChartData("8/1/2015", "North", 238812.8, 195490.23),
            new MixedPlotsChartData("8/1/2015", "South", 259538.78, null),
            new MixedPlotsChartData("8/1/2015", "East", 206980, null),
            new MixedPlotsChartData("9/1/2015", "North", 328968.24, 250363.66),
            new MixedPlotsChartData("9/1/2015", "South", 333338.53, null),
            new MixedPlotsChartData("9/1/2015", "East", 274469.39, null),
            new MixedPlotsChartData("10/1/2015", "North", 254834.63, 277934.27),
            new MixedPlotsChartData("10/1/2015", "South", 291766.69, null),
            new MixedPlotsChartData("10/1/2015", "East", 312699.37, null),
            new MixedPlotsChartData("11/1/2015", "North", 276865.79, 292184.73),
            new MixedPlotsChartData("11/1/2015", "South", 279437.91, null),
            new MixedPlotsChartData("11/1/2015", "East", 277282.05, null),
            new MixedPlotsChartData("12/1/2015", "North", 315322.45, 289039.35),
            new MixedPlotsChartData("12/1/2015", "South", 282709.72, null),
            new MixedPlotsChartData("12/1/2015", "East", 310435.53, null),
            new MixedPlotsChartData("1/1/2016", "North", 297229.72, 288590.58),
            new MixedPlotsChartData("1/1/2016", "South", 298069.62, null),
            new MixedPlotsChartData("1/1/2016", "East", 259962.44, null),
            new MixedPlotsChartData("2/1/2016", "North", 250639.62, 277592.73),
            new MixedPlotsChartData("2/1/2016", "South", 245414.34, null),
            new MixedPlotsChartData("2/1/2016", "East", 238551.11, null),
            new MixedPlotsChartData("3/1/2016", "North", 237270.12, 250380.75),
            new MixedPlotsChartData("3/1/2016", "South", 224388.74, null),
            new MixedPlotsChartData("3/1/2016", "East", 201901.06, null),
            new MixedPlotsChartData("4/1/2016", "North", 218251.5, 237105.82),
            new MixedPlotsChartData("4/1/2016", "South", 260283.96, null),
            new MixedPlotsChartData("4/1/2016", "East", 257251.93, null),
            new MixedPlotsChartData("5/1/2016", "North", 279112.79, 251849.67),
            new MixedPlotsChartData("5/1/2016", "South", 326799.53, null),
            new MixedPlotsChartData("5/1/2016", "East", 261387.39, null),
            new MixedPlotsChartData("6/1/2016", "North", 340678.71, 275323.81),
            new MixedPlotsChartData("6/1/2016", "South", 269953.2, null),
            new MixedPlotsChartData("6/1/2016", "East", 264195.29, null),
            new MixedPlotsChartData("7/1/2016", "North", 250903.88, 282271.84),
            new MixedPlotsChartData("7/1/2016", "South", 273476.68, null),
            new MixedPlotsChartData("7/1/2016", "East", 273939.08, null),
            new MixedPlotsChartData("8/1/2016", "North", 335509.77, 301101.45),
            new MixedPlotsChartData("8/1/2016", "South", 322519.62, null),
            new MixedPlotsChartData("8/1/2016", "East", 378736.78, null),
            new MixedPlotsChartData("9/1/2016", "North", 340214, 306887.06),
            new MixedPlotsChartData("9/1/2016", "South", 286959.27, null),
            new MixedPlotsChartData("9/1/2016", "East", 299724.47, null),
            new MixedPlotsChartData("10/1/2016", "North", 317074.09, 323779.85),
            new MixedPlotsChartData("10/1/2016", "South", 309380.6, null),
            new MixedPlotsChartData("10/1/2016", "East", 323900.04, null),
            new MixedPlotsChartData("11/1/2016", "North", 356229.82, 330691.48),
            new MixedPlotsChartData("11/1/2016", "South", 395235.13, null),
            new MixedPlotsChartData("11/1/2016", "East", 347505.9, null),
            new MixedPlotsChartData("12/1/2016", "North", 361720.95, 348046.81),
            new MixedPlotsChartData("12/1/2016", "South", 398000.15, null),
            new MixedPlotsChartData("12/1/2016", "East", 323374.59, null)
        };
    }
}
