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

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.rpc.LoadScreenCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

import java.util.Map;

public class ReplacePlaceholder extends ShowcasePanel {

    private static final String DESCRIPTION =
        "Programmatically created components can be inserted into the middle of a layout " +
        "that is otherwise controlled by Component XML.  Just leave a placeholder component " +
        "in the Component XML layout and replace it programatically." +
        "<P>In the example below, the form and placeholder have been loaded from a " +
        "Component XML file.  Use the \"Replace PlaceHolder\" button to replace the " +
        "placeholder with a programatically generated grid component." +
        "<P>Or, check the \"Auto-replace Placeholder\" checkbox and press the \"Reload " +
        "Component XML\" button to see the replacement take place automatically as soon " +
        "as the Component XML is loaded.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            ReplacePlaceholder panel = new ReplacePlaceholder();
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

    private VLayout xmlLayout;

    private Label placeholder;

    public Canvas getViewPanel() {
        final Canvas container = new Canvas("container");
        container.setBorder("3px solid green");

        VStack mainLayout = new VStack(5);
        mainLayout.setWidth("300");
        mainLayout.setBorder("1px solid red");
        HStack innerLayout = new HStack(5);
        innerLayout.setHeight(30);
        final DynamicForm autoReplacePlaceholderDynamicForm = new DynamicForm();
        autoReplacePlaceholderDynamicForm.setItems(
            new CheckboxItem("autoReplacePlaceholderCheckbox", "Auto-replace Placeholder")
        );

        final Button replacePlaceholderButton = new Button("Replace Placeholder");
        replacePlaceholderButton.setWidth(200);
        replacePlaceholderButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                replacePlaceholderButtonClick();
            }
        });

        Button reloadComponentXMLButton = new Button("Reload component XML");
        reloadComponentXMLButton.setWidth(200);
        reloadComponentXMLButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (xmlLayout != null) {
                    xmlLayout.markForDestroy();
                    xmlLayout = null;
                    RPCManager.loadScreen("replacePlaceholder", new LoadScreenCallback() {
                        @Override
                            public void execute() {
                            xmlLayout = (VLayout) this.getScreen();
                            placeholder = (Label) xmlLayout.getByLocalId("placeholder");
                            container.addChild(xmlLayout);
                            Boolean autoReplace = (Boolean)autoReplacePlaceholderDynamicForm.getValue("autoReplacePlaceholderCheckbox");
                            if (autoReplace != null && autoReplace.booleanValue()) {
                                replacePlaceholderButtonClick();
                            }
                        }
                    });
                }
            }
        });

        innerLayout.setMembers(reloadComponentXMLButton, replacePlaceholderButton, autoReplacePlaceholderDynamicForm);
        mainLayout.setMembers(innerLayout, container);

        RPCManager.loadScreen("replacePlaceholder", new LoadScreenCallback() {
            @Override
            public void execute() {
                xmlLayout = (VLayout) this.getScreen();
                placeholder = (Label) xmlLayout.getByLocalId("placeholder");
                container.addChild(xmlLayout);
            }
        });
        return mainLayout;
    }

    public void replacePlaceholderButtonClick() {
        if (placeholder != null) {
            ListGrid grid = new ListGrid();
            grid.setDataSource(DataSource.get("supplyItem"));
            grid.setAutoFetchData(true);
            grid.setWidth(400);
            grid.setHeight(200);
            xmlLayout.removeMember(placeholder);
            placeholder = null;
            xmlLayout.addMember(grid);
        }
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("replacePlaceholder.ui.xml", XML, "source/replacePlaceholder.ui.xml.html", true),
            new SourceEntity("supplyItem.ds.xml", XML, "source/supplyItem.ds.xml.html", true)
        };
    }
}
