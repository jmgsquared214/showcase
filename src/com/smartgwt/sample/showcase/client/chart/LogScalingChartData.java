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

public class LogScalingChartData extends Record {

    public LogScalingChartData(Integer year, Float index) {
        setAttribute("year", year);
        setAttribute("index", index);
    }

    public static LogScalingChartData[] getData() {
        return new LogScalingChartData[] {
            new LogScalingChartData(1950, 17.05f),
            new LogScalingChartData(1951, 21.66f),
            new LogScalingChartData(1952, 24.14f),
            new LogScalingChartData(1953, 26.38f),
            new LogScalingChartData(1954, 26.08f),
            new LogScalingChartData(1955, 36.63f),
            new LogScalingChartData(1956, 43.82f),
            new LogScalingChartData(1957, 44.72f),
            new LogScalingChartData(1958, 41.70f),
            new LogScalingChartData(1959, 55.45f),
            new LogScalingChartData(1960, 55.61f),
            new LogScalingChartData(1961, 61.78f),
            new LogScalingChartData(1962, 68.84f),
            new LogScalingChartData(1963, 66.20f),
            new LogScalingChartData(1964, 77.04f),
            new LogScalingChartData(1965, 87.56f),
            new LogScalingChartData(1966, 92.88f),
            new LogScalingChartData(1967, 86.61f),
            new LogScalingChartData(1968, 92.24f),
            new LogScalingChartData(1969, 103.01f),
            new LogScalingChartData(1970, 85.02f),
            new LogScalingChartData(1971, 95.88f),
            new LogScalingChartData(1972, 103.94f),
            new LogScalingChartData(1973, 116.03f),
            new LogScalingChartData(1974, 96.57f),
            new LogScalingChartData(1975, 76.98f),
            new LogScalingChartData(1976, 100.86f),
            new LogScalingChartData(1977, 102.03f),
            new LogScalingChartData(1978, 89.25f),
            new LogScalingChartData(1979, 99.93f),
            new LogScalingChartData(1980, 114.16f),
            new LogScalingChartData(1981, 129.55f),
            new LogScalingChartData(1982, 120.40f),
            new LogScalingChartData(1983, 145.30f),
            new LogScalingChartData(1984, 163.41f),
            new LogScalingChartData(1985, 179.63f),
            new LogScalingChartData(1986, 211.78f),
            new LogScalingChartData(1987, 274.08f),
            new LogScalingChartData(1988, 257.07f),
            new LogScalingChartData(1989, 297.47f),
            new LogScalingChartData(1990, 329.08f),
            new LogScalingChartData(1991, 343.93f),
            new LogScalingChartData(1992, 408.78f),
            new LogScalingChartData(1993, 438.78f),
            new LogScalingChartData(1994, 481.61f),
            new LogScalingChartData(1995, 470.42f),
            new LogScalingChartData(1996, 636.02f),
            new LogScalingChartData(1997, 786.16f),
            new LogScalingChartData(1998, 980.28f),
            new LogScalingChartData(1999, 1279.64f),
            new LogScalingChartData(2000, 1394.46f),
            new LogScalingChartData(2001, 1366.01f),
            new LogScalingChartData(2002, 1130.20f),
            new LogScalingChartData(2003, 855.70f),
            new LogScalingChartData(2004, 1131.13f),
            new LogScalingChartData(2005, 1181.27f),
            new LogScalingChartData(2006, 1280.08f),
            new LogScalingChartData(2007, 1438.24f),
            new LogScalingChartData(2008, 1378.55f),
            new LogScalingChartData(2009, 825.88f),
            new LogScalingChartData(2010, 1073.87f),
            new LogScalingChartData(2011, 1286.12f)
        };
    }

}
