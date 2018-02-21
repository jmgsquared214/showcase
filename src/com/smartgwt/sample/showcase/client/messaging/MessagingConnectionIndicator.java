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

package com.smartgwt.sample.showcase.client.messaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.Messaging;
import com.smartgwt.client.rpc.MessagingCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

import com.smartgwt.client.rpc.Messaging;
import com.smartgwt.client.rpc.MessagingConnectionUpCallback;
import com.smartgwt.client.rpc.MessagingConnectionDownCallback;
    
public class MessagingConnectionIndicator extends Label {

    
    @Override 
    protected void onInit() {
        super.onInit();

        setHeight(20);
        setContents("Connection DOWN");

        Messaging.setConnectionUpCallback(new MessagingConnectionUpCallback() {
                public void execute() {
                    setContents("Connection UP");
                    setBackgroundColor("lightgreen");
                }
            });
        Messaging.setConnectionDownCallback(new MessagingConnectionDownCallback() {
                public void execute() {
                    setContents("Connection DOWN");
                    setBackgroundColor("red");
                }
            });
        
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        
        Messaging.setConnectionUpCallback(new MessagingConnectionUpCallback() {
                public void execute() {

                }
            });
        Messaging.setConnectionDownCallback(new MessagingConnectionDownCallback() {
                public void execute() {

                }
            });
        
    }
}
