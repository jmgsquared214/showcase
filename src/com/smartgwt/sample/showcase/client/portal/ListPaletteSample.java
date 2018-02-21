package com.smartgwt.sample.showcase.client.portal;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.EditProxy;
import com.smartgwt.client.tools.ListPalette;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class ListPaletteSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>List Palettes organize available components in a list grid structure. "+
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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#listPalette\" target=\"\">here</a> " +
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
            return new ListPaletteSample();
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

        ListPalette listPalette = new ListPalette();
        listPalette.setWidth("25%");

        // The regular ListGrid property "fields"
        listPalette.setFields(new ListGridField("title", "Component"));

        // We are supplying the component data inline for this example.
        // However, ListPalette is a subclass of ListGrid, so you could
        // also use a DataSource.
        listPalette.setData(getListPaletteData());

        // Make the editPane the default target when double-clicking on
        // components in the listPalette
        listPalette.setDefaultEditContext(editPane);

        // Layout for the example
        HLayout layout = new HLayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        layout.setMembersMargin(20);
        layout.setMembers(listPalette, editPane);
        return layout;
    }

    private static Record[] getListPaletteData() {
        PaletteNode blueComponent = new PaletteNode();

        // Title as you want it to appear in the list
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

        return new Record[] {
            redComponent, blueComponent, alligatorComponent, anteaterComponent
        };
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
