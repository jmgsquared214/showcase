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

package com.smartgwt.sample.showcase.server;

import java.io.Serializable;

//----------------------------------------------------------------------
// Minimal Java server integration example (Tree bean databinding)
//
// SupplyCategory Bean
//
//    -- provides a simple persistent data structure for this example
//

//----------------------------------------------------------------------
public class SupplyCategory implements Serializable {
    protected String categoryName;
    protected String parentID;

    public SupplyCategory() {
    }

    public void setCategoryName(String name) {
        categoryName = name;
    }

    public void setParentID(String id) {
        parentID = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getParentID() {
        return parentID;
    }

}