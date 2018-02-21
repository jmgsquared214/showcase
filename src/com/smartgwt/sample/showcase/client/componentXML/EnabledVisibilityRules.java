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

import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.LoadScreenCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class EnabledVisibilityRules extends ShowcasePanel {

    private static final String DESCRIPTION =
        "Select records in the grid below and note that: "+
        "<ul>"+
        "<li> the \"Preview\" pane only becomes visible if there is a selection "+
        "<li> the \"Delete\" button is enabled if there is a selection "+
        "<li> the \"Edit\" button only becomes enabled if exactly one record is selected "+
        "</ul>"+
        "<p>"+
        "All of these behaviors are driven by simple XML declarations that make use of the "+
        "<code>ruleScope</code>: data exposed by <code>DataBoundComponents</code> that other "+
        "components can use to control appearance or behavior.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            EnabledVisibilityRules panel = new EnabledVisibilityRules();
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

		final Canvas container = new Canvas();
		container.setWidth100();
		container.setHeight100();
		
		RPCManager.loadScreen("enabledAndVisibilityRules", new LoadScreenCallback() {
			@Override
			public void execute() {
				container.addChild(this.getScreen());
			}
		});

        return container;
    }
    
    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("enabledAndVisibilityRules.ui.xml", XML, "source/enabledAndVisibilityRules.ui.xml.html", true),
            new SourceEntity("countryDS.ds.xml", XML, "source/countryDS.ds.xml.html", true)
        };
    }
}