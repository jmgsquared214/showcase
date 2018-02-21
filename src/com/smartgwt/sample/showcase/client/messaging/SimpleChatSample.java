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

import com.smartgwt.client.rpc.Messaging;
import com.smartgwt.client.rpc.MessagingCallback;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class SimpleChatSample extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "<p>This preview sample illustrates using the optional Real-Time Messaging (RTM) "+
        "module to build a simple chat application, using server push to distribute data "+
        "from the server.</p>";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the optional " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Real Time Messaging module</a>.</p>" +
                    "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#messaging_simple_chat\" target=\"\">here</a> to see this example on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return SC.hasRealtimeMessaging();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new SimpleChatSample();
        }
    }

    private Canvas chatLog;
    private DynamicForm chatForm;

    public Canvas getViewPanel() {
        VLayout layout = new VLayout();
        layout.setWidth(500);
        layout.setHeight100();

        chatLog = new Canvas();
        chatLog.setBackgroundColor("white");
        chatLog.setBorder("2px solid gray");
        chatLog.setContents("Chat Session<br>" +
                "Open this page in multiple client browsers for multi-user chat.<br><br>");
        chatLog.setWidth(500);
        chatLog.setHeight(200);
        chatLog.setOverflow(Overflow.AUTO);

        layout.addMember(chatLog);

        chatForm = new DynamicForm();
        chatForm.setHeight(200);

        TextItem userName = new TextItem("user");
        userName.setRequired(true);
        userName.setTitle("User Name");

        TextAreaItem messageArea = new TextAreaItem("msg");
        messageArea.setHeight(50);
        messageArea.setTitle("Message");
        messageArea.setWidth(400);

        ButtonItem send = new ButtonItem();
        send.setTitle("Send");
        send.setColSpan("*");
        send.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                sendChatMessage();
            }
        });

        chatForm.setItems(userName, messageArea, send);

        Messaging.subscribe("chatChannel", new MessagingCallback() {
            @Override
            public void execute(Object data) {
                chatLog.setContents(chatLog.getContents() + (String)data);
            }
        });

        layout.addMember(chatForm);

        return layout;
    }

    public void sendChatMessage() {
        if (!chatForm.validate()) return;
        String userName = (String) chatForm.getValue("user");
        Object messageText = chatForm.getValue("msg");
        if (messageText == null) return;
        String message = "<b>" + userName + ":</b> " + (String)messageText + "<br><br>";
        Messaging.send("chatChannel", message, new RPCCallback () {
            @Override
            public void execute(RPCResponse response, Object rawData,
                    RPCRequest request) {
                if (response.getStatus() != RPCResponse.STATUS_SUCCESS) SC.say("Failed send message to server.");
            }
        });
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}