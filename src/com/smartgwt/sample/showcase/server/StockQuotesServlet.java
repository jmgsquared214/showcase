/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright (c) 2011 Isomorphic Software, Inc.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isomorphic.messaging.ISCMessage;
import com.isomorphic.messaging.ISCMessageDispatcher;

/**
 * Servlet for generating random data for stock quotes grid.
 */
public class StockQuotesServlet extends HttpServlet {

    private static final long serialVersionUID = 6200724895227971697L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // RPC transactions may be encoded as a GET request, so handle those as well
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Servlet entry point to process the request. sends random stock updates to
     * the "stockQuotes" channel for 30 seconds
     * 
     * @param request The HttpServletRequest
     * @param response The HttpServletResponse
     * @exception ServletException As per HttpServlet.service()
     * @exception IOException As per HttpServlet.service()
     * 
     * @visibility external
     */
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        final String startParameter = request.getParameter("sp");
        new Thread() {
            public void run() {
                try {
                    Random generator = new Random();

                    ISCMessageDispatcher dispatcher = ISCMessageDispatcher.instance();
                    List<Object[]> stockData = new ArrayList<Object[]>();
                    long endTime = System.currentTimeMillis() + 90000;
                    while (System.currentTimeMillis() < endTime) {
                        stockData.clear();
                        for (int i = 1; i <= 10; i++) {
                            stockData.add(new Object[] { i, 0 });
                        }
                        int cnt = 1 + generator.nextInt(3);
                        for (int i = 0; i < cnt; i++) {
                            int id = 1 + generator.nextInt(10);
                            for (Object[] sd : stockData) {
                                if (((Integer) sd[0]).intValue() == id) {
                                    sd[1] = ((float) (generator.nextInt(101) - 50)) / 100;
                                    break;
                                }
                            }
                        }

                        dispatcher.send(new ISCMessage("stockQuotes" + startParameter, stockData));
                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            };
        }.start();
    }
}
