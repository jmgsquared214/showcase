package com.smartgwt.sample.showcase.client.portal;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.TilePalette;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.util.SC;

import com.google.gwt.core.client.JavaScriptObject;


public class DataSourcePersistenceSample extends TilePaletteSample {

    private static final String DESCRIPTION =
        "<p>The state of an Edit Context can be saved to a variable. That variable can then " +
        "be used to duplicate or recreate the Edit Context.</p><p>Try dragging some " +
        "components from the Tile Palette to the Edit Pane. Click the \"Destroy and Recreate\" " +
        "button to save the Edit Pane's state, destroy it, and then recreate it. The process " +
        "is animated, to illustrate the process (which would otherwise occur instantly).</p>";

    public static class Factory extends AdvancedPanelFactory {
        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public HTMLFlow getDisabledViewPanel() {
            final HTMLFlow htmlFlow = new HTMLFlow(
                "<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the optional " +
                "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Dashboard &amp; Tools module</a>.</p>" +
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#dataSourcePersistence\" target=\"\">here</a> " +
                "to see this example on SmartClient.com.</p></div>"
            );
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return SC.hasDashboardAndTools();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new DataSourcePersistenceSample();
        }
    }

    @Override
    public Canvas getViewPanel() {
        enableReflection();

        // Creates the editPane, hlayout, tilePalette, and vlayout ... see next tab.
        super.getViewPanel();

        // The next tab contains the code for setting up the TilePalette and EditPane.
        // This code shows how to persist the EditPane's state to a DataSource.

        // This button will save the state of the EditPane to a DataSource
        IButton saveButton = new IButton("Save");
        saveButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    DataSourcePersistenceSample.this.saveEditPane();
                }
            });

        // This button restores the state of the EditPane from the DataSource.
        IButton restoreButton = new IButton("Restore");
        restoreButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    DataSourcePersistenceSample.this.restoreEditPane();
                }
            });

        // Insert the buttons into the example layout
        HLayout buttonsLayout = new HLayout();
        buttonsLayout.setMembersMargin(10);
        buttonsLayout.setMembers(new LayoutSpacer(), saveButton, restoreButton);

        this.vlayout.addMember(buttonsLayout, 0);

        // Automatically do a restore, to apply any previously saved content.
        restoreEditPane();

        return this.vlayout;
    }

    private void saveEditPane() {
        // editPane.getSaveData returns the state of the editPane
        // as an array of EditNodes.
        final PaletteNode[] paletteNodes = this.editPane.getSaveData();
        final DataSource ds = DataSource.get("editNodes");

        // First we delete the existing data on the server. This
        // example is set up with a table where each row represents
        // one EditNode in this EditPane. In an application, you would
        // likely use criteria to filter fetchData, to delete only
        // the relevant EditNodes.
        DSCallback callback = new DSCallback() {
                public void execute(DSResponse dsResponse, Object rawData, DSRequest dsRequest) {

                    // Use the transaction queue feature of RPCManager to combine the
                    // DataSource operations into a single HTTP request.
                    RPCManager.startQueue();

                    for (Record record : dsResponse.getData()) {
                        ds.removeData(record);
                    }

                    // Then we insert our new data. The "defaults" field in
                    // the node is an object with properties, so we serialize
                    // it to a string (to be stored in a text field on the server).
                    // Depending on how you set up your DataSource, another strategy
                    // would be to serialize the entire EditNode to a string, or
                    // serialize the entire array of EditNodes to a string.
                    if (paletteNodes != null) {
                        for (PaletteNode node : paletteNodes) {
                            JavaScriptObject defaults = node.getAttributeAsJavaScriptObject("defaults");
                            node.setAttribute("defaults", JSON.encode(defaults));
                            ds.addData(node);
                        }
                    }

                    // Send the queued DataSource requests to the server.
                    RPCManager.sendQueue();
                }
            };

        ds.fetchData(null, callback);
    }

    private void restoreEditPane() {
        // Remove the editPane and destroy it
        this.hlayout.removeMember(this.editPane);
        this.editPane.destroy();

        // Then recreate it ...
        final EditPane editPane = this.editPane = new EditPane();
        editPane.setBorder("1px solid black");
        this.hlayout.addMember(editPane);

        // And restore it as the default context
        this.tilePalette.setDefaultEditContext(editPane);

        // And recreate the nodes that we saved earlier. In a real
        // application, you would use criteria to filter the relevant
        // EditNodes.
        DSCallback callback = new DSCallback() {
                public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                    // See the note above concerning the structure of the DataSource.
                    // This DataSource has one row for each EditNode, so we
                    // serialize the "defaults" property into a text field.
                    // You could also set up the DataSource so that the whole
                    // EditNode was serialized to a string, or the whole
                    // array of EditNodes.
                    for (Record record : dsResponse.getData()) {
                        JavaScriptObject defaults = JSON.decode(record.getAttribute("defaults"));
                        record.setAttribute("defaults", defaults);
                        editPane.addFromPaletteNode(new PaletteNode(record.getJsObj()));
                    }
                }
            };
        DataSource ds = DataSource.get("editNodes");
        ds.fetchData(null, callback);
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[] {
            new SourceEntity("TilePaletteSample.java", JAVA, "source/portal/TilePaletteSample.java.html", false)
        };
    }
}
