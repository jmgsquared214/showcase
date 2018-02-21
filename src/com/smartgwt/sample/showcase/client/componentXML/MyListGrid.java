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
package com.smartgwt.sample.showcase.client.componentXML;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class MyListGrid extends ListGrid {

    private boolean hilitePricesOverTen = false;
    
    public void setHilitePricesOverTen(boolean hilitePricesOverTen) {
        this.hilitePricesOverTen = hilitePricesOverTen;
    }
    
    public void setDataSource(String datasourceName) {
        this.setDataSource(DataSource.get(datasourceName));
    }
    
    @Override
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
        if (this.hilitePricesOverTen && record.getAttributeAsDouble("unitCost") > 10) {
            return "color:red;";
        }
        return super.getCellCSSText(record, rowNum, colNum);
    }
}
