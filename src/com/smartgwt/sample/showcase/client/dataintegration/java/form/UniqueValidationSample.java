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
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class UniqueValidationSample extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "Enter the email address \"kamirov@server.com\" in the \"email\" field and press Tab. "+
        "Do so with any other email address as well."+
        "<P/>The resulting validation error is based upon the server-side <code>isUnique</code> "+
        "validator that checks to see if there is already a record in the DataSource and "+
        "if so fails validation.";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            UniqueValidationSample panel = new UniqueValidationSample();
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
        DataSource dataSource = DataSource.get("queuing_userHB");
        form.setDataSource(dataSource);

        HeaderItem header = new HeaderItem("header");
        header.setDefaultValue("Registration Form");

        TextItem email = new TextItem("email");
        email.setValidateOnExit(true);
        email.setRequired(true);
        
        TextItem firstName = new TextItem("firstName");
        TextItem surname = new TextItem("surname");
        TextItem department = new TextItem("department");
        
        ButtonItem validateBtn = new ButtonItem("validateBtn", "Validate");
        validateBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                form.validate();
            }
        });
        
        form.setFields(header, email, firstName, surname, department, validateBtn);
        
        return form;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

}
