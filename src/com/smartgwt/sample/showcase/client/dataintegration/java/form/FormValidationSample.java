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

package com.smartgwt.sample.showcase.client.dataintegration.java.form;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class FormValidationSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Validation rules are automatically enforced on both the " +
            "client- and server-side based on a single, shared declaration. Press \"Save\" to see errors from " +
            "client-side validation.</p>" +
            "<p>Press \"Clear Errors\" then \"Disable Client Side Validation\" " +
            "then \"Save\" again to see the same errors caught by the Smart GWT Server.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            FormValidationSample panel = new FormValidationSample();
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
        final DynamicForm form = new DynamicForm();
        DataSource dataSource = DataSource.get("supplyItem");
        form.setDataSource(dataSource);

        //prefill some values
        form.setValue("unitCost", -1.234);
        form.setValue("SKU", "mySKU");

        IButton saveButton = new IButton("Save");
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                form.saveData();
            }
        });

        IButton clearErrorsButton = new IButton("Clear Errors");
        clearErrorsButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                form.clearErrors(true);
            }
        });

        final IButton disableValidationButton = new IButton("Disable Client Side Validation");
        disableValidationButton.setAutoFit(true);
        disableValidationButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Boolean validation = form.getDisableValidation();
                validation = validation == null ? false : validation;
                form.setDisableValidation(!validation);
                disableValidationButton.setTitle((form.getDisableValidation() ? "Enable" : "Disable") + " Client Side Validation");
            }
        });

        HStack buttons = new HStack(10);
        buttons.setHeight(24);
        buttons.setMembers(saveButton, clearErrorsButton, disableValidationButton);

        VLayout layout = new VLayout(10);
        layout.setMembers(form, buttons);
        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

}