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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class RelatedValidationSample extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "Enter a complaint for a received shipment using its tracking number. The tracking "+
        "number must reference an existing tracking number, so use 4110884 or 9631143 as existing numbers. "+
        "Then try again with a random number (such as 1234). "+
        "<P/> "+
        "The <code>relatedRecord</code> validator can be used to validate that an ID entered by "+
        "a user actually exists.  This is useful in situations where using a comboBox for record "+
        "lookup is inappropriate (the user should not be able to select from all valid tracking "+
        "numbers, or from other types of IDs, such as license keys or driver's license numbers), "+
        "or in situations such as a batch upload of many records. "+
        "<P/> "+
        "The <code>relatedRecord</code> validator can also be used with a ComboBox as the UI in order to "+
        "enforce that related records are checked <b>before</b> a request reaches business logic, "+
        "where it would be convenient to assume the ID is already validated, or as a means of "+
        "enforcing referential integrity in systems that don't have built-in enforcement. ";
        
    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            RelatedValidationSample panel = new RelatedValidationSample();
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
        
        DataSource dataSource = DataSource.get("complaint");
        // .. references DataSource.get("masterDetail_orderHB") as relatedDataSource
        
        form.setDataSource(dataSource);
        form.setWrapItemTitles(false);

        HeaderItem header = new HeaderItem("header");
        header.setDefaultValue("Shipment Complaint Form");

        IntegerItem trackingNumber = new IntegerItem("trackingNumber");
        trackingNumber.setValidateOnExit(true);
        
        DateItem receiptDate = new DateItem("receiptDate");
        receiptDate.setUseTextField(true);
        receiptDate.setValidateOnExit(true);
        
        TextAreaItem comment = new TextAreaItem("comment");
        
        form.setFields(header, trackingNumber, receiptDate, comment);
        
        return form;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

}
