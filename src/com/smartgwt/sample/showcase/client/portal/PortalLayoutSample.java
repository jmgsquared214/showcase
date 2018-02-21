package com.smartgwt.sample.showcase.client.portal;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.TreePalette;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.util.SC;


public class PortalLayoutSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>With the Tools framework, you can create Palettes from which to drag Portlets."
        + "Try dragging from the TreePalette to the PortalLayout." 
        + " Portlets will be created on the fly.</p>"
        + "<p>The PortalLayout in this example is embedded in an EditPane, so that the state of the"
        + " PortalLayout (and its Portlets) can be persisted. "
        + "Here the state is saved to a JavaScript variable, but other persistence mechanisms may be used." 
        + " Click the \"Destroy and Recreate\" button to save the PortalLayout's state, destroy it, and then"
        + " recreate it. The sequence is animated to illustrate the process.</p>"
        + "<p>Once you've created some Canvas portlets, try right-clicking on them to change"
        + " their background color. Notice how code in the example updates the PortalLayout's edited state,"
        + " so that the colors persist when you \"Destroy and Recreate\".</p>";

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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#portalLayout\" target=\"\">here</a> " +
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
            return new PortalLayoutSample();
        }
    }

    public interface MetaFactory extends BeanFactory.MetaFactory {
        BeanFactory<Canvas> getCanvasBeanFactory();
        BeanFactory<Img> getImgBeanFactory();
    }

    private static void enableReflection() {
        if (!enabledReflection) {
            GWT.create(MetaFactory.class);
            GWT.create(ColorMenuCanvas.MetaFactory.class);
            enabledReflection = true;
        }
    }

    public Canvas getViewPanel() {
        enableReflection();

        // The EditPane is the area in which the components can be placed
        final EditPane editPane = new EditPane();
        editPane.setBorder("1px solid black");
        JSOHelper.setAttribute(editPane.getConfig(), "editMode", false);

        // The TreePalette contains a tree of components available
        // for use, with default settings.
        TreePalette treePalette = new TreePalette();
        treePalette.setWidth("25%");

        treePalette.setFields(new TreeGridField("title", "Component"));

        // We are supplying the component data inline for this example.
        // However, the TreePalette is a subclass of TreeGrid, so you could
        // also use a DataSource.
        final Tree componentTree = getTreePaletteData();
        treePalette.setData(componentTree);

        // Open the folders in the Tree
        treePalette.addDrawHandler(new DrawHandler() {
            public void onDraw(DrawEvent event) {
                componentTree.openAll();
            }
        });

        editPane.setDefaultPalette(treePalette);

        // Make the editPane the default target when double-clicking on
        // components in the treePalette
        treePalette.setDefaultEditContext(editPane);

        // Add a PortalLayout to the editPane
        PaletteNode portalLayoutComponent = new PaletteNode();
        // type indicates the class of object to create for this component
        portalLayoutComponent.setType("PortalLayout");
        // defaults specifies the properties to use when
        // creating the component
        Canvas portalLayoutDefaults = new Canvas();
        portalLayoutDefaults.setWidth100();
        portalLayoutDefaults.setHeight100();
        portalLayoutComponent.setCanvasDefaults(portalLayoutDefaults);
        EditNode editNode = editPane.addFromPaletteNode(portalLayoutComponent);
        editPane.getEditContext().setDefaultParent(editNode);

        IButton destroyButton = new IButton("Destroy and Recreate");
        destroyButton.setAutoFit(true);
        destroyButton.setLayoutAlign(Alignment.RIGHT);
        destroyButton.addClickHandler(new ClickHandler() {
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
        
        // Layout for the example
        HLayout hlayout = new HLayout();
        hlayout.setWidth("100%");
        hlayout.setHeight("100%");
        hlayout.setMembersMargin(20);
        hlayout.setMembers(treePalette, editPane);

        VLayout layout = new VLayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        layout.setMembersMargin(10);
        layout.setMembers(destroyButton, hlayout);

        return layout;
    }

    private static Tree getTreePaletteData() {
        PaletteNode blueComponent = new PaletteNode();
        // title is the normal TreeNode property
        blueComponent.setTitle("Blue Canvas");
        // type indicates the class of object to create for this component
        blueComponent.setType("com.smartgwt.sample.showcase.client.portal.ColorMenuCanvas");

        // defaults specifies the properties to use when
        // creating the component
        Canvas blueDefaults = new Canvas();
        blueDefaults.setBackgroundColor("blue");
        blueDefaults.setWidth(60);
        blueDefaults.setHeight(60);
        blueDefaults.setCanDragResize(true);
        blueDefaults.setKeepInParentRect(true);
        blueDefaults.setDragAppearance(DragAppearance.TARGET);
        blueComponent.setCanvasDefaults(blueDefaults);

        PaletteNode redComponent = new PaletteNode();
        redComponent.setTitle("Red Canvas");
        redComponent.setType("com.smartgwt.sample.showcase.client.portal.ColorMenuCanvas");
        Canvas redDefaults = new Canvas();
        redDefaults.setBackgroundColor("red");
        redDefaults.setWidth(60);
        redDefaults.setHeight(60);
        redDefaults.setCanDragResize(true);
        redDefaults.setKeepInParentRect(true);
        redDefaults.setDragAppearance(DragAppearance.TARGET);
        redComponent.setCanvasDefaults(redDefaults);

        PaletteNode alligatorComponent = new PaletteNode();
        alligatorComponent.setTitle("Alligator");
        alligatorComponent.setType("com.smartgwt.client.widgets.Img");
        Img alligatorDefaults = new Img();
        alligatorDefaults.setCanDragResize(true);
        alligatorDefaults.setWidth(60);
        alligatorDefaults.setHeight(60);
        alligatorDefaults.setKeepInParentRect(true);
        alligatorDefaults.setDragAppearance(DragAppearance.TARGET);
        alligatorDefaults.setSrc("tiles/images/Alligator.jpg");
        alligatorComponent.setCanvasDefaults(alligatorDefaults);

        PaletteNode anteaterComponent = new PaletteNode();
        anteaterComponent.setTitle("Anteater");
        anteaterComponent.setType("com.smartgwt.client.widgets.Img");
        Img anteaterDefaults = new Img();
        anteaterDefaults.setCanDragResize(true);
        anteaterDefaults.setWidth(60);
        anteaterDefaults.setHeight(60);
        anteaterDefaults.setKeepInParentRect(true);
        anteaterDefaults.setDragAppearance(DragAppearance.TARGET);
        anteaterDefaults.setSrc("tiles/images/AntEater.jpg");
        anteaterComponent.setCanvasDefaults(anteaterDefaults);

        TreeNode canvasComponents = new TreeNode();
        canvasComponents.setTitle("Canvas");
        canvasComponents.setCanDrag(false);
        canvasComponents.setIsFolder(true);
        canvasComponents.setChildren(new TreeNode[] {
                new TreeNode(blueComponent.getJsObj()),
                new TreeNode(redComponent.getJsObj())
            });

        TreeNode imageComponents = new TreeNode();
        imageComponents.setTitle("Images");
        imageComponents.setCanDrag(false);
        imageComponents.setIsFolder(true);
        imageComponents.setChildren(new TreeNode[] {
                new TreeNode(alligatorComponent.getJsObj()),
                new TreeNode(anteaterComponent.getJsObj())
            });

        TreeNode root = new TreeNode();
        root.setChildren(new TreeNode[] { canvasComponents, imageComponents });

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.CHILDREN);
        tree.setShowRoot(false);
        tree.setRoot(root);

        return tree;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
   
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("ColorMenuCanvas.java", JAVA, "source/portal/ColorMenuCanvas.java.html", false),
        };
    }
}
