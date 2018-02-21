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

import com.smartgwt.client.rpc.LoadScreenCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class HelloWorld extends ShowcasePanel {

    private static final String DESCRIPTION =
        "This SmartGWT DynamicForm provides a text field and a button control. " +
        "Type a name in the field, then click the button for a personalized message. " +
        "This example shows embedding JavaScript event handling code directly into Component XML; " +
        "see the sample \"Adding Handlers\" for keeping Java event handling and XML layout separate.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
        	HelloWorld panel = new HelloWorld();
            id = panel.getID();
            return panel;
        }

        public String getID() {
            return id;
        }

        public String getDescription() {
            return DESCRIPTION;
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final Canvas layout = new Canvas();
        RPCManager.loadScreen("helloWorld", new LoadScreenCallback() {
            @Override
            public void execute() {
                layout.addChild(this.getScreen());
            }
        });
        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("helloWorld.ui.xml", XML, "source/helloWorld.ui.xml.html", true),
        };
    }
}
