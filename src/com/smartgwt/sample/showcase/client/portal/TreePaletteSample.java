package com.smartgwt.sample.showcase.client.portal;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.EditProxy;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.TreePalette;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class TreePaletteSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Tree Palettes organize available components in a tree structure. "+
        "The user can double-click or drag to create a component. "+
        "Components can be copied and pasted using keyboard shortcuts Ctrl-C/V (or Cmd-C/V on Mac). "+
        "To see this in action, create a component and confirm it is selected by clicking on it. Then press the copy "+
        "key, move the source component somewhere else and finally press the paste key.</p>";

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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#treePalette\" target=\"\">here</a> " +
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
            return new TreePaletteSample();
        }
    }


    public interface MetaFactory extends BeanFactory.MetaFactory {
        BeanFactory<Canvas> getCanvasBeanFactory();
        BeanFactory<Img> getImgBeanFactory();
    }

    private static void enableReflection() {
        if (!enabledReflection) {
            GWT.create(MetaFactory.class);
            enabledReflection = true;
        }
    }

    public Canvas getViewPanel() {
        enableReflection();

        // The EditPane is the area in which the components can be placed
        EditPane editPane = new EditPane();
        editPane.setBorder("1px solid black");
        EditProxy properties = new EditProxy();
        properties.setAutoMaskChildren(true);
        editPane.setAutoChildProperties("editProxy", properties);

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

        // Make the editPane the default target when double-clicking on
        // components in the treePalette
        treePalette.setDefaultEditContext(editPane);

        // Open the folders in the Tree
        treePalette.addDrawHandler(new DrawHandler() {
            public void onDraw(DrawEvent event) {
                componentTree.openAll();
            }
        });

        // Layout for the example
        HLayout layout = new HLayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        layout.setMembersMargin(20);
        layout.setMembers(treePalette, editPane);
        return layout;
    }

    private static Tree getTreePaletteData() {
        PaletteNode blueComponent = new PaletteNode();
        // title is the normal TreeNode property
        blueComponent.setTitle("Blue Canvas");
        // type indicates the class of object to create for
        // this component
        blueComponent.setType("com.smartgwt.client.widgets.Canvas");

        // defaults specifies the properties to use when
        // creating the component
        Canvas blueDefaults = new Canvas();
        blueDefaults.setBackgroundColor("blue");
        // By default, the EditPane will persist coordinates
        // so setting canDrag and canDragResize is enough
        // to allow simple editing of coordinates. You can
        // turn the persistence of coordinates off in EditPane
        // if you want to allow for editing them in a different
        // way.
        blueDefaults.setCanDragReposition(true);
        blueDefaults.setCanDragResize(true);
        blueDefaults.setKeepInParentRect(true);
        blueDefaults.setDragAppearance(DragAppearance.TARGET);
        blueComponent.setCanvasDefaults(blueDefaults);

        PaletteNode redComponent = new PaletteNode();
        redComponent.setTitle("Red Canvas");
        redComponent.setType("com.smartgwt.client.widgets.Canvas");
        Canvas redDefaults = new Canvas();
        redDefaults.setBackgroundColor("red");
        redDefaults.setCanDragReposition(true);
        redDefaults.setCanDragResize(true);
        redDefaults.setKeepInParentRect(true);
        redDefaults.setDragAppearance(DragAppearance.TARGET);
        redComponent.setCanvasDefaults(redDefaults);

        PaletteNode alligatorComponent = new PaletteNode();
        alligatorComponent.setTitle("Alligator");
        alligatorComponent.setType("com.smartgwt.client.widgets.Img");
        Img alligatorDefaults = new Img();
        alligatorDefaults.setCanDragReposition(true);
        alligatorDefaults.setCanDragResize(true);
        alligatorDefaults.setKeepInParentRect(true);
        alligatorDefaults.setDragAppearance(DragAppearance.TARGET);
        alligatorDefaults.setSrc("tiles/images/Alligator.jpg");
        alligatorComponent.setCanvasDefaults(alligatorDefaults);

        PaletteNode anteaterComponent = new PaletteNode();
        anteaterComponent.setTitle("Anteater");
        anteaterComponent.setType("com.smartgwt.client.widgets.Img");
        Img anteaterDefaults = new Img();
        anteaterDefaults.setCanDragReposition(true);
        anteaterDefaults.setCanDragResize(true);
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
}
