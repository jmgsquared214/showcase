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

import javax.servlet.http.HttpServletRequest;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.datasource.DataSource;

public class CartDMI {

    public DSResponse enforceUserAccess(DSRequest dsRequest, HttpServletRequest servletRequest)
    throws Exception
    {
        String sessionId = servletRequest.getSession().getId();
        if (DataSource.isAdd(dsRequest.getOperationType()))
            dsRequest.setFieldValue("sessionId", sessionId);
        else
            dsRequest.setCriteriaValue("sessionId", sessionId);
        
        return dsRequest.execute();
    }
}