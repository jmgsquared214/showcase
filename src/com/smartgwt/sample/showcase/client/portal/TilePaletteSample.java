package com.smartgwt.sample.showcase.client.portal;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.EditProxy;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.TilePalette;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class TilePaletteSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Tile Palettes organize available components in a tile grid structure.  The user " +
        "can double-click or drag to create a component.</p>";

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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#tilePalette\" target=\"\">here</a> " +
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
            return new TilePaletteSample();
        }
    }


    public interface MetaFactory extends BeanFactory.MetaFactory {
        BeanFactory<Canvas> getCanvasBeanFactory();
        BeanFactory<Img> getImgBeanFactory();
    }

    protected void enableReflection() {
        if (!enabledReflection) {
            GWT.create(MetaFactory.class);
            enabledReflection = true;
        }
    }

    protected EditPane editPane;
    protected TilePalette tilePalette;
    protected HLayout hlayout;
    protected VLayout vlayout;

    public Canvas getViewPanel() {
        enableReflection();

        // The EditPane is the area in which the components can be placed
        EditPane editPane = this.editPane = new EditPane();
        editPane.setBorder("1px solid black");
        EditProxy properties = new EditProxy();
        properties.setAutoMaskChildren(true);
        editPane.setAutoChildProperties("editProxy", properties);

        // The TilePalette contains components available
        // for use, with default settings.
        TilePalette tilePalette = this.tilePalette = new TilePalette();
        tilePalette.setWidth(300);
        tilePalette.setTileWidth(125);
        tilePalette.setTileHeight(132);
        tilePalette.setCanDragTilesOut(true);

        // The usual TileGrid property, specifying how to draw the fields
        // for the tiles. We use a custom formatCellValue function to
        // draw the tiles in a way that reflects some of their default
        // values ... you would customize depending on which default values
        // are useful to present in this way. Note that formatCellValue() only affects
        // the appearance of the components in the TilePalette itself ... it
        // has no effect on how the components appear in the EditPane once
        // instantiated there (that is controlled by the "defaults" specified
        // below).

        DetailViewerField typeField = new DetailViewerField();
        typeField.setName("type");
        typeField.setDetailFormatter(new TypeDetailFormatter());
        tilePalette.setFields(typeField, new DetailViewerField("title", "Component"));

        // We are supplying the component data inline for this example.
        // However, the TilePalette is a subclass of TileGrid, so you could
        // also use a DataSource.
        tilePalette.setData(getTilePaletteData());

        // Make the editPane the default target when double-clicking on
        // components in the tilePalette
        tilePalette.setDefaultEditContext(editPane);

        HLayout hlayout = this.hlayout = new HLayout();
        hlayout.setMembersMargin(20);
        hlayout.setWidth("100%");
        hlayout.setHeight("100%");
        hlayout.setMembers(tilePalette, editPane);

        // Layout for the example. The layouts are nested because this
        // is used as a basis for other examples, in which some
        // user interface elements are added.
        VLayout vlayout = this.vlayout = new VLayout();
        vlayout.setWidth("100%");
        vlayout.setHeight("100%");
        vlayout.setMembersMargin(10);
        vlayout.setMembers(hlayout);

        return vlayout;
    }

    private static class TypeDetailFormatter implements DetailFormatter {

        public String format(Object value, Record record, DetailViewerField field) {
            PaletteNode paletteNode = (PaletteNode) record;
            Canvas defaults = paletteNode.getCanvasDefaults();
            if ("com.smartgwt.client.widgets.Canvas".equals(value)) {
                return "<div style='background-color: " +
                    defaults.getBackgroundColor() +
                    "; width: 100px; height: 100px; margin-left: auto; margin-right: auto;'>";
            } else if ("com.smartgwt.client.widgets.Img".equals(value)) {
                return "<img src='" +
                    (Page.getAppImgDir() + defaults.getAttribute("src")) +
                    "' width='100' height='100'>";
            } else {
                return null;
            }
        }
    }

    private static Record[] getTilePaletteData() {

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
