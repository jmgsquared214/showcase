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

package com.smartgwt.sample.showcase.client.dataintegration.java.others;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.PartialCommitOption;
import com.smartgwt.client.widgets.BatchUploader;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class BatchUploaderSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<u>BatchUploader Example</u></h3><ul>" +
            "<li>Download the <a href=\"data/supplyItemTest.csv\" target=\"_blank\">supplyItemTest.csv</a> file to a local hard drive.</li>" +
            "<li>Click the \"Browse\" or \"Choose files\" button and use the file picker to select the \"supplyItemTest.csv\" file " +
            "you just downloaded (HINT: In some browsers you can drag the file directly from the browser's download area onto the button)</li>" +
            "<li>The BatchUploader will upload and validate the contents of that CSV file against the " +
            "DataSource declared on the BatchUploader, which in this case is \"supplyItemCustom\".</li>" +
            "<li>Import data can optionally be transformed during import.  In this example, the 'Units' " +
            "field is really an integer - a foreignKey value to the supplyItemUnits DataSource - but it " +
            "declares a displayField.  SmartGWT automatically recognizes this pattern, and transforms " +
            "user-visible values in the import dataset - 'Ea', 'Roll', etc - into the proper foreignKey " +
            "value needed for successful import</li>" +
            "<li>Validated data will then be streamed back to the client and displayed in an " +
            "editable ListGrid for review and correction of errors.</li>" +
            "<li>Click \"Commit\" to save the data back to the DataSource's persistent store (in this " +
            "case, a database table accessed via SmartGWT's built-in SQL engine).</li>" +
            "<li>Use the select box to switch the BatchUploader's \"partial commit\" mode. \"Allow\" commits valid records and discards " +
    		"records in error, \"Prevent\" forbids commit if there are any outstanding errors, and \"Prompt\" asks the user to choose between " +
            "those two.  \"Retain\" commits the valid records and retains the records in error, so you can continue to fix errors: it allows " +
            "an iterative approach</li>" + 
            "<li>This end-to-end functionality is encapsulated by the BatchUploader, and requires " +
            "no application code.</li></ul>";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the Batch Uploader Feature of " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Smart GWT Power Edition</a> or better.</p>" +
                    "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#batch_uploader\" target=\"\">here</a> to see this example on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return Showcase.hasBatchUploader();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new BatchUploaderSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        DataSource dataSource = DataSource.get("supplyItemCustom");
        
        // Include the supplyItemUnits DataSource so it shows up in the source window
        DataSource supplyItemUnits = DataSource.get("supplyItemUnits");

        final BatchUploader batchUploader = new BatchUploader();
        batchUploader.setWidth100();
        batchUploader.setUploadDataSource(dataSource);
        ListGrid properties = new ListGrid();
        properties.setHeight(300);
        batchUploader.setAutoChildProperties("grid", properties);

        TextItem stringValue = new TextItem("stringValue", "String Value");
        IntegerItem numberValue = new IntegerItem();
        numberValue.setName("numericValue");
        numberValue.setTitle("Numeric Value");

        batchUploader.setUploadFormItems(stringValue, numberValue);

        batchUploader.setDataURL(GWT.getModuleBaseURL() + "/exampleTransactionManager.do");
        
        DynamicForm partialCommitsForm = new DynamicForm();
        SelectItem partialCommits = new SelectItem("partialCommits", "Partial Commit Mode");
        partialCommits.setWrapTitle(false);
        partialCommits.setValueMap(new LinkedHashMap() {{
        	put(PartialCommitOption.ALLOW, "Allow");
        	put(PartialCommitOption.PREVENT, "Prevent");
        	put(PartialCommitOption.PROMPT, "Prompt");
        	put(PartialCommitOption.RETAIN, "Retain");
        }});
        partialCommits.setDefaultValue("Prompt");
        partialCommits.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				batchUploader.setPartialCommit(PartialCommitOption.valueOf((String)event.getValue()));
			}
		});
        
        partialCommitsForm.setItems(partialCommits);

        ListGrid supplyItemsGrid = new ListGrid();
        supplyItemsGrid.setShowFilterEditor(true);
        supplyItemsGrid.setDataSource(dataSource);
        supplyItemsGrid.setUseAllDataSourceFields(true);
        supplyItemsGrid.setWidth100();
        supplyItemsGrid.setHeight(300);
        supplyItemsGrid.setAutoFetchData(true);

        VLayout layout = new VLayout(15);
        layout.setWidth100();
        layout.addMember(partialCommitsForm);
        layout.addMember(batchUploader);

        VLayout layout2 = new VLayout();
        layout2.addMember(new Label("<b>Supply Items Table Contents</b>"));
        layout2.addMember(supplyItemsGrid);

        layout.addMember(layout2);
        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
                new SourceEntity("web.xml", XML, "source/ds/si-custom-hb/web.xml.html", true),
                new SourceEntity("showcase-servlet.xml", XML, "source/ds/si-custom-hb/showcase-servlet.xml.html", true),
        };
    }
}