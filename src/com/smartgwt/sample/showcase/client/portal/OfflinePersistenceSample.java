package com.smartgwt.sample.showcase.client.portal;

import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.TilePalette;
import com.smartgwt.client.util.ConvertTo;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.Offline;
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
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.util.SC;

import com.google.gwt.core.client.JavaScriptObject;


public class OfflinePersistenceSample extends TilePaletteSample {

    private static final String DESCRIPTION =
        "<p>The state of an Edit Context can be connected to Offline storage. Try dragging " +
        "some components from the Tile Palette to the Edit Pane. Click on \"Save\" to save " +
        "the state of the Edit Pane to a DataSource. Make some changes to the Edit Pane, " +
        "and then click \"Restore\". Note how the state of the Edit Pane is restored to its " +
        "saved state.</p>" +
        "<p>Try reloading the page to see saved state automatically restored. (Note that " +
        "the example does not automatically save state).</p>";

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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#offlinePersistence\" target=\"\">here</a> " +
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
            return new OfflinePersistenceSample();
        }
    }

    @Override
    public Canvas getViewPanel() {
        enableReflection();

        // Creates the editPane, hlayout, tilePalette, and vlayout ... see next tab.
        super.getViewPanel();

        // The next tab contains the code for setting up the TilePalette and EditPane.
        // This code shows how to persist the EditPane's state to Offline storage.

        // This button will save the state of the EditPane to Offline storage.
        IButton saveButton = new IButton("Save");
        saveButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    OfflinePersistenceSample.this.saveEditPane();
                }
            });

        // This button will restore the state of the EditPane from Offline storage.
        IButton restoreButton = new IButton("Restore");
        restoreButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    OfflinePersistenceSample.this.restoreEditPane();
                }
            });

        // Insert the buttons into the example layout
        HLayout buttonsLayout = new HLayout();
        buttonsLayout.setMembersMargin(10);
        buttonsLayout.setMembers(new LayoutSpacer(), saveButton, restoreButton);

        this.vlayout.addMember(buttonsLayout, 0);

        // Automatically do a restore, to apply any previous offline content.
        restoreEditPane();

        return this.vlayout;
    }

    private void saveEditPane() {
        PaletteNode[] paletteNodes = this.editPane.getSaveData();
        String json = JSON.encode(convertToJavaScriptObject(paletteNodes));
        Offline.put("exampleEditPaneNodes", json);
    }

    private void restoreEditPane() {
        EditPane editPane = this.editPane;

        // First, destroy the editPane
        this.hlayout.removeMember(editPane);
        editPane.destroy();

        // Then recreate it ...
        editPane = this.editPane = new EditPane();
        editPane.setBorder("1px solid black");
        this.hlayout.addMember(editPane);

        // And restore it as the default context
        this.tilePalette.setDefaultEditContext(editPane);

        // And recreate the saved EditNodes
        String json = (String) Offline.get("exampleEditPaneNodes");
        if (json != null) {
            PaletteNode[] paletteNodes = ConvertTo.arrayOfPaletteNode(JSON.decode(json));
            if (paletteNodes != null) {
                for (PaletteNode node : paletteNodes) {
                    editPane.addFromPaletteNode(node);
                }
            }
        }
    }

    private static JavaScriptObject convertToJavaScriptObject(PaletteNode[] paletteNodes) {
        if (paletteNodes == null) {
            return null;
        } else {
            JavaScriptObject jsArray = JSOHelper.createJavaScriptArray();
            for (int i = 0; i < paletteNodes.length; i++) {
                JSOHelper.setArrayValue(jsArray, i, paletteNodes[i].getJsObj());
            }
            return jsArray;
        }
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
