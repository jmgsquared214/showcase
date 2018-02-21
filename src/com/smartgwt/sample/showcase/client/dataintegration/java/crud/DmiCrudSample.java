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

package com.smartgwt.sample.showcase.client.dataintegration.java.crud;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class DmiCrudSample extends ShowcasePanel {
    private static final String DESCRIPTION = "Direct Method Invocation (DMI) allows for the mapping of DataSource operations directly to Java methods " +
            "via XML configuration in a DataSource descriptor (*.ds.xml file). The arguments of the Java methods are automatically populated from the " +
            "inbound request. See the sample implementation in <code>SupplyItemDMI.java</code> ";


    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            DmiCrudSample panel = new DmiCrudSample();
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

        DataSource dataSource = DataSource.get("supplyItemDMI");

        VStack vStack = new VStack();
        vStack.setLeft(175);
        vStack.setTop(75);
        vStack.setWidth("70%");
        vStack.setMembersMargin(20);

        Label label = new Label();
        label.setContents("<ul>" +
                "<li>click a record in the grid to view and edit that record in the form</li>" +
                "<li>click \"Save\" to save changes to an edited record in the form</li>" +
                "<li>click \"Clear\" to clear all fields in the form</li>" +
                "<li>enter text such as \"board\" in the Item field and click \"Filter\" to filter (substring match) the grid based on the value Item form value only.</li>" +
                "<li>select a row and click \"Fetch\" to fetch records (exact match) for the grid based on the value of the \"Item\" form value only.</li>" +
                "<li>click \"Delete\" to delete all selected records</li>" +
                "<li>double-click a record in the grid to edit inline (press Return, or arrow/tab to another record, to save)</li>" +
                "</ul>");
        vStack.addMember(label);

        // databound ListGrid
        //   * click records to edit in boundForm and view in boundViewer
        //   * double-click record to edit inline (Return or arrow/tab off current row to save)
        final ListGrid boundList = new ListGrid();
        boundList.setDataSource(dataSource);
        boundList.setHeight(200);
        boundList.setCanEdit(true);
        vStack.addMember(boundList);

        final DynamicForm boundForm = new DynamicForm();
        boundForm.setDataSource(dataSource);
        boundForm.setNumCols(4);
        boundForm.setAutoFocus(false);
        vStack.addMember(boundForm);

        HLayout toolbar = new HLayout();
        toolbar.setMembersMargin(10);
        toolbar.setHeight(22);

        final IButton saveBtn = new IButton("Save");
        saveBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                boundForm.saveData();
                if (!boundForm.hasErrors()) {
                    boundForm.clearValues();
                    saveBtn.disable();
                }
            }
        });
        toolbar.addMember(saveBtn);

        final IButton newBtn = new IButton("New");
        newBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                boundForm.editNewRecord();
                saveBtn.enable();
            }
        });
        toolbar.addMember(newBtn);

        IButton clearBtn = new IButton("Clear");
        clearBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                boundForm.clearValues();
                saveBtn.disable();
            }
        });
        toolbar.addMember(clearBtn);

        IButton filterBtn = new IButton("Filter");
        filterBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                boundList.filterData(boundForm.getValuesAsCriteria());
                saveBtn.disable();
            }
        });
        toolbar.addMember(filterBtn);

        IButton fetchBtn = new IButton("Fetch");
        fetchBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                boundList.fetchData(boundForm.getValuesAsCriteria());
                saveBtn.disable();
            }
        });
        toolbar.addMember(fetchBtn);

        vStack.addMember(toolbar);

        final DetailViewer boundViewer = new DetailViewer();
        boundViewer.setDataSource(dataSource);
        vStack.addMember(boundViewer);

        boundList.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                Record record = event.getRecord();
                boundForm.editRecord(record);
                saveBtn.enable();
                boundViewer.viewSelectedData(boundList);
            }
        });

        boundList.filterData(new Criteria());

        return vStack;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                new SourceEntity("WEB-INF/web.xml", XML, "source/ds/common/web.xml.html", true),
                new SourceEntity("server/SupplyItemDMI.java", JAVA, "source/ds/si-dmi/SupplyItemDMI.java.html", true),
                new SourceEntity("server/SupplyItem.java", JAVA, "source/beans/SupplyItem.java.html", true),
                new SourceEntity("SupplyItemStore.java", JAVA, "source/ds/si-dmi/SupplyItemStore.java.html", true)};        
    }
}