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
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class InlineScriptValidationSample extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "Use the \"Item Id\" ComboBox to select an item,  enter a very large quantity (999999) "+
        "and press the \"Submit Order\" button."+
        "<P/>The resulting validation error is based upon a server-side condition specified in "+
        "the validator using inline scripting. It checks a related DataSource "+
        "(StockItem) to see if there is sufficient quantity in stock to fulfill the order.";
        
    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            InlineScriptValidationSample panel = new InlineScriptValidationSample();
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
        DataSource dataSource = DataSource.get("inlineScript_orderForm");
        form.setDataSource(dataSource);

        HeaderItem header = new HeaderItem("header");
        header.setDefaultValue("Add an item to your Order");

        ComboBoxItem item = new ComboBoxItem("itemId", "Item");
        item.setOptionDataSource(DataSource.get("StockItem"));
        item.setValueField("id");
        item.setDisplayField("description");
        
        IntegerItem quantity = new IntegerItem("quantity");
        quantity.setValidateOnExit(true);
        
        TextAreaItem instructions = new TextAreaItem("instructions");
        
        ButtonItem submit = new ButtonItem("submit", "Submit Order");
        
        form.setFields(header, item, quantity, instructions, submit);
        
        return form;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

}
