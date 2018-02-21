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

public class FormRules extends ShowcasePanel {

    private static final String DESCRIPTION =
        "The form below has several rules applied which are declared directly in XML, "+
        "without the need to write JavaScript code: "+
        "<ul>"+
        "<li> an email field becomes required only if you choose to sign up for a newsletter. "+
        "Note how the label \"Email\" becomes bold when it is required "+
        "<li> a field for entering a password appears only if you choose to enter a password instead of "+
        "accepting a default password "+
        "<li> the \"Proceed\" button is not enabled until terms and conditions are accepted "+
        "</ul>.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            FormRules panel = new FormRules();
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
		
		RPCManager.loadScreen("formRules", new LoadScreenCallback() {
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
            new SourceEntity("formRules.ui.xml", XML, "source/formRules.ui.xml.html", true)
        };
    }
}