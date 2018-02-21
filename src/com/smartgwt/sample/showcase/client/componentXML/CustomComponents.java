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

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.rpc.LoadScreenCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

import java.util.Map;

public class CustomComponents extends ShowcasePanel {

    private static final String DESCRIPTION =
        "You can use custom components in screens created via Component XML.  Just use " +
        "the \"constructor\" attribute to indicate that your custom class should be used. " +
        "<P> You can even provide custom properties to your custom class.  In the sample " +
        "below, the custom ListGrid subclass \"MyListGrid\" has a boolean setting " +
        "\"hilitePricesOverTen\" that causes prices over $10 to appear in red color.  This " +
        "boolean setting is false by default, but the Component XML file sets the property " +
        "to true on the grid. " +
        "<P> It's also possible to declare a Component Schema so that &lt;MyListGrid&gt; " +
        "can be used directly as the XML tag, with no need to set the constructor or " +
        "declare the types of custom properties like \"hilitePricesOverTen\".";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            CustomComponents panel = new CustomComponents();
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

    public interface MyMetaFactory extends BeanFactory.MetaFactory{
        BeanFactory<MyListGrid> getMyListGridFactory();
    }

    public Canvas getViewPanel() {
        GWT.create(MyMetaFactory.class);
        final Canvas layout = new Canvas();
        RPCManager.loadScreen("customComponents", new LoadScreenCallback() {
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
            new SourceEntity("customComponents.ui.xml", XML, "source/customComponents.ui.xml.html", true),
            new SourceEntity("MyListGrid.java", JAVA, "source/componentXML/MyListGrid.java.html", false),
            new SourceEntity("supplyItem.ds.xml", XML, "source/supplyItem.ds.xml.html", true)
        };
    }
}
