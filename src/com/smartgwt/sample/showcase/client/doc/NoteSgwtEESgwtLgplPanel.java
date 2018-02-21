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

package com.smartgwt.sample.showcase.client.doc;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class NoteSgwtEESgwtLgplPanel extends ShowcasePanel {
    private static final String DESCRIPTION = "A brief note to " + Showcase.getSGWTProductName() + ".";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            NoteSgwtEESgwtLgplPanel panel = new NoteSgwtEESgwtLgplPanel();
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

    public Canvas getViewPanel() {
        final HTMLPane pane = new HTMLPane();
        pane.setHideUsingDisplayNone(true);
        pane.setWidth100();
        pane.setHeight100();
        pane.setPadding(10);
        pane.setContentsURL("doc/noteSgwtEESgwtLgpl.html");
        pane.setBorder("1px solid gray");
        return pane;
    }

    protected boolean isNoSource() {
        return true;
    }
}
