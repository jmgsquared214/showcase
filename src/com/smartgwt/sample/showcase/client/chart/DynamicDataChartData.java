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

import com.smartgwt.client.widgets.tree.TreeNode;

public class DynamicDataChartData extends TreeNode {

    public DynamicDataChartData(String id, String parentId, String title) {
        setID(id);
        if (parentId != null) {
            setParentID(parentId);
        }
        setTitle(title);
    }

    public static DynamicDataChartData[] getData() {
        return new DynamicDataChartData[] {
            new DynamicDataChartData("sum", null, "All Years"),
            new DynamicDataChartData("2014", "sum", "2014"),
            new DynamicDataChartData("2015", "sum", "2015"),
            new DynamicDataChartData("2016", "sum", "2016"),
            new DynamicDataChartData("Q1-2014", "2014", "Q1-2014"),
            new DynamicDataChartData("Q2-2014", "2014", "Q2-2014"),
            new DynamicDataChartData("Q3-2014", "2014", "Q3-2014"),
            new DynamicDataChartData("Q4-2014", "2014", "Q4-2014"),
            new DynamicDataChartData("Q1-2015", "2015", "Q1-2015"),
            new DynamicDataChartData("Q2-2015", "2015", "Q2-2015"),
            new DynamicDataChartData("Q3-2015", "2015", "Q3-2015"),
            new DynamicDataChartData("Q4-2015", "2015", "Q4-2015"),
            new DynamicDataChartData("Q1-2016", "2016", "Q1-2016"),
            new DynamicDataChartData("Q2-2016", "2016", "Q2-2016"),
            new DynamicDataChartData("Q3-2016", "2016", "Q3-2016"),
            new DynamicDataChartData("Q4-2016", "2016", "Q4-2016"),
            new DynamicDataChartData("1/1/2014", "Q1-2014", "1/1/2014"),
            new DynamicDataChartData("2/1/2014", "Q1-2014", "2/1/2014"),
            new DynamicDataChartData("3/1/2014", "Q1-2014", "3/1/2014"),
            new DynamicDataChartData("4/1/2014", "Q2-2014", "4/1/2014"),
            new DynamicDataChartData("5/1/2014", "Q2-2014", "5/1/2014"),
            new DynamicDataChartData("6/1/2014", "Q2-2014", "6/1/2014"),
            new DynamicDataChartData("7/1/2014", "Q3-2014", "7/1/2014"),
            new DynamicDataChartData("8/1/2014", "Q3-2014", "8/1/2014"),
            new DynamicDataChartData("9/1/2014", "Q3-2014", "9/1/2014"),
            new DynamicDataChartData("10/1/2014", "Q4-2014", "10/1/2014"),
            new DynamicDataChartData("11/1/2014", "Q4-2014", "11/1/2014"),
            new DynamicDataChartData("12/1/2014", "Q4-2014", "12/1/2014"),
            new DynamicDataChartData("1/1/2015", "Q1-2015", "1/1/2015"),
            new DynamicDataChartData("2/1/2015", "Q1-2015", "2/1/2015"),
            new DynamicDataChartData("3/1/2015", "Q1-2015", "3/1/2015"),
            new DynamicDataChartData("4/1/2015", "Q2-2015", "4/1/2015"),
            new DynamicDataChartData("5/1/2015", "Q2-2015", "5/1/2015"),
            new DynamicDataChartData("6/1/2015", "Q2-2015", "6/1/2015"),
            new DynamicDataChartData("7/1/2015", "Q3-2015", "7/1/2015"),
            new DynamicDataChartData("8/1/2015", "Q3-2015", "8/1/2015"),
            new DynamicDataChartData("9/1/2015", "Q3-2015", "9/1/2015"),
            new DynamicDataChartData("10/1/2015", "Q4-2015", "10/1/2015"),
            new DynamicDataChartData("11/1/2015", "Q4-2015", "11/1/2015"),
            new DynamicDataChartData("12/1/2015", "Q4-2015", "12/1/2015"),
            new DynamicDataChartData("1/1/2016", "Q1-2016", "1/1/2016"),
            new DynamicDataChartData("2/1/2016", "Q1-2016", "2/1/2016"),
            new DynamicDataChartData("3/1/2016", "Q1-2016", "3/1/2016"),
            new DynamicDataChartData("4/1/2016", "Q2-2016", "4/1/2016"),
            new DynamicDataChartData("5/1/2016", "Q2-2016", "5/1/2016"),
            new DynamicDataChartData("6/1/2016", "Q2-2016", "6/1/2016"),
            new DynamicDataChartData("7/1/2016", "Q3-2016", "7/1/2016"),
            new DynamicDataChartData("8/1/2016", "Q3-2016", "8/1/2016"),
            new DynamicDataChartData("9/1/2016", "Q3-2016", "9/1/2016"),
            new DynamicDataChartData("10/1/2016", "Q4-2016", "10/1/2016"),
            new DynamicDataChartData("11/1/2016", "Q4-2016", "11/1/2016"),
            new DynamicDataChartData("12/1/2016", "Q4-2016", "12/1/2016"),
        };
    }

}
