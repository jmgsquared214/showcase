package com.smartgwt.sample.showcase.client.portal;

import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.TilePalette;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.util.SC;

public class AutomaticPersistenceSample extends TilePaletteSample {

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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#automaticPersistence\" target=\"\">here</a> " +
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
            return new AutomaticPersistenceSample();
        }
    }

    @Override
    public Canvas getViewPanel() {
        enableReflection();

        // Creates the editPane, hlayout, tilePalette, and vlayout ... see next tab.
        super.getViewPanel();

        // The next tab shows the code for setting up the EditPane and the TilePalette.
        // This code shows how to use a variable to save the state of the EditPane
        // and then recreate it.

        // This button will destroy the Edit Pane and then recreate it from saved state.
        IButton button = new IButton("Destroy and Recreate");
        button.setAutoFit(true);
        button.setLayoutAlign(Alignment.RIGHT);

        button.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    AutomaticPersistenceSample.this.destroyAndRecreateEditPane();
                }
            });

        // This inserts the button into the overall layout for the example ... see next tab
        this.vlayout.addMember(button, 0);

        return this.vlayout;
    }

    private void destroyAndRecreateEditPane() {

        // We save the editPane node data in a variable
        final PaletteNode[] paletteNodes = this.editPane.getSaveData();

        // Animate the disappearnce of the pane, since otherwise
        // everything happens at once.
        AnimationCallback callback = new AnimationCallback() {

                public void execute(boolean earlyFinish) {
                    final AutomaticPersistenceSample self = AutomaticPersistenceSample.this;
                    EditPane editPane = self.editPane;

                    // Once the animation is finished, remove the editPane
                    // and destroy it.
                    self.hlayout.removeMember(editPane);
                    editPane.destroy();

                    // Then recreate it ...
                    editPane = self.editPane = new EditPane();
                    editPane.setBorder("1px solid black");

                    self.hlayout.addMember(editPane);

                    // Make the new editPane the default Edit Context for the palette,
                    // to support double-clicking on components.
                    self.tilePalette.setDefaultEditContext(editPane);

                    // And recreate the nodes that we saved earlier
                    if (paletteNodes != null) {
                        for (PaletteNode node : paletteNodes) {
                            editPane.addFromPaletteNode(node);
                        }
                    }
                }
            };

        this.editPane.animateFade(0, callback, 2000, AnimationAcceleration.SMOOTH_END);
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
