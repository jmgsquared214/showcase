package com.smartgwt.sample.showcase.client.portal;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.HiddenPalette;
import com.smartgwt.client.tools.ListPalette;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.PortalLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class GridPortletSample extends ShowcasePanel {

    private static final String DESCRIPTION =
			" <p>In Edit Mode, Grid components can automatically save changes to "
    		+ "criteria, fields, hiliting rules and other end-user settings.</p>"
			+ "<p>Drag DataSource names from the palette on the left to the portal on "
    		+ "the right.  Portlets containing grids will be created on the fly. "
			+ "Change the criteria, field order or size, sort order, highlights, or "
    		+ "grouping, then press \"Destroy and Recreate\" to see the grids re-created "
    		+ "in the same layout and with the same settings.</p>"
    		+ "<p>In this sample, the state of the portal and grids is held "
    		+ "temporarily in a variable; the state of the portal and grids can also "
    		+ "be serialized to JSON or XML, and stored as a DataSource field "
    		+ "value.</p>";


	private VLayout vLayout;

    private static boolean enabledReflection = false;

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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#gridPortlets\" target=\"\">here</a> " +
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
            return new GridPortletSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }


    public interface MetaFactory extends BeanFactory.MetaFactory {
    	BeanFactory<Canvas> getCanvasBeanFactory();
    }

    private static void enableReflection() {
    	if (!enabledReflection) {
    		GWT.create(MetaFactory.class);
    		enabledReflection = true;
    	}
    }

	@Override
	public Canvas getViewPanel() {
    	enableReflection();

    	ListPalette palette = createPalette();
    
    	HiddenPalette extraPalette = new HiddenPalette();
    	PaletteNode extraPaletteNode = new PaletteNode();
    	extraPaletteNode.setTitle("ListGridField");
    	extraPaletteNode.setType("ListGridField");
    	extraPalette.setData(extraPaletteNode);

    	EditPane editPane = new EditPane();
    	editPane.setBorder("1px solid black");
    	editPane.setExtraPalettes(extraPalette);

    	editPane.setDefaultPalette(palette);

    	// Make the new editPane the default Edit Context for the palette,
    	// to support double-clicking on paletteNodes to create them.
    	palette.setDefaultEditContext(editPane);

    	// Add a PortalLayout to the editPane
    	PaletteNode node = new PaletteNode();
    	node.setType("PortalLayout");
    	PortalLayout defaults = new PortalLayout();
    	defaults.setWidth100();
    	defaults.setHeight100();
    	defaults.setCanResizePortlets(true);
    	node.setCanvasDefaults(defaults);
        EditNode editNode = editPane.addFromPaletteNode(node);
        editPane.getEditContext().setDefaultParent(editNode);

    	HLayout hLayout = new HLayout();
    	hLayout.setWidth100();
    	hLayout.setHeight100();
    	hLayout.setMembersMargin(20);
    	hLayout.setMembers(palette, editPane);

    	vLayout = new VLayout();
    	vLayout.setWidth100();
    	vLayout.setHeight100();
    	vLayout.setMembersMargin(10);
    	vLayout.setMembers(createButtonBar(editPane), hLayout);

    	return vLayout;
	}

    private ListPalette createPalette() {
    	ListPalette palette = new ListPalette();
    	palette.setWidth(150);
    	palette.setLeaveScrollbarGap(false);

    	// The regular TileGrid property "fields"
    	palette.setFields(new ListGridField("title", "DataSource Name"));

    	// We are supplying the component data inline for this example.
    	// However, ListPalette is a subclass of ListGrid, so you could
    	// also use a DataSource.
    	palette.setData(getPaletteData());

    	return palette;
    }

    // specify paletteNodes that will create ListGrids bound to specific DataSources
    private static Record[] getPaletteData() {
    	List<PaletteNode> nodes = new ArrayList<PaletteNode>();

    	PaletteNode node = new PaletteNode();
    	node.setTitle("Animals");
    	node.setType("ListGrid");
    	ListGrid defaults = new ListGrid();
    	defaults.setDataSource(DataSource.get("animals"));
    	defaults.setAutoFetchData(true);
    	defaults.setShowFilterEditor(true);
    	node.setCanvasDefaults(defaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Supply Categories");
    	node.setType("ListGrid");
    	defaults = new ListGrid();
    	defaults.setDataSource(DataSource.get("supplyCategory"));
    	defaults.setAutoFetchData(true);
    	defaults.setShowFilterEditor(true);
    	node.setCanvasDefaults(defaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Supply Items");
    	node.setType("ListGrid");
    	defaults = new ListGrid();
    	defaults.setDataSource(DataSource.get("supplyItem"));
    	defaults.setAutoFetchData(true);
    	defaults.setShowFilterEditor(true);
    	node.setCanvasDefaults(defaults);
    	nodes.add(node);

    	return nodes.toArray(new PaletteNode[] {});
    }

    public Layout createButtonBar(final EditPane editPane) {
    	IButton destroyAndRecreateButton = new IButton("Destroy and Recreate");
    	destroyAndRecreateButton.setAutoFit(true);
    	destroyAndRecreateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// We save the editPane node data in a variable
				final String paletteNodes = editPane.serializeAllEditNodes();

				// Animate the disappearance of the editPane, since otherwise
				// everything happens at once.
				editPane.animateFade(0, new AnimationCallback() {
					@Override
					public void execute(boolean earlyFinish) {
						// Once the animation is finished, destroy all the nodes
						editPane.destroyAll();

						// Then add them back from the serialized form
						editPane.addPaletteNodesFromXML(paletteNodes);

						// And make us visible again
						editPane.setOpacity(100);
					}
				}, 2000, AnimationAcceleration.SMOOTH_END);
			}
		});

    	LayoutSpacer leftSpace = new LayoutSpacer();
    	leftSpace.setWidth("*");

    	HLayout layout = new HLayout();
    	layout.setWidth100();
    	layout.setHeight(30);
    	layout.setMembersMargin(10);

    	layout.setMembers(leftSpace, destroyAndRecreateButton);

    	return layout;
    }

    public String getIntro() {
    	return DESCRIPTION;
    }
}
