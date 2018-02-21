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

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Transaction;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.hibernate.HibernateDataSource;
import com.isomorphic.rpc.RPCManager;

public class ExampleTransactionManagerController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        RPCManager rpcManager = new RPCManager(request, response);
        Transaction transaction = HibernateDataSource.startTransaction();

        for (Iterator i = rpcManager.getRequests().iterator(); i.hasNext();) {
            Object requestObj = i.next();
            if (requestObj instanceof DSRequest) {  // If it isn't a DSRequest, just ignore it
                DSRequest dsRequest = (DSRequest) requestObj;
                dsRequest.setParameter("transactionObject", transaction);
                try {
                    rpcManager.send(dsRequest, dsRequest.execute());
                } catch (Exception e) {
                    HibernateDataSource.rollbackTransaction(transaction);
                    transaction = null;
                    rpcManager.sendFailure(dsRequest, e);
                }
            }
        }

        if (transaction != null) HibernateDataSource.commitTransaction(transaction);

        return null;
    }
}