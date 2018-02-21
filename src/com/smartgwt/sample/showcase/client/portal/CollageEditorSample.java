package com.smartgwt.sample.showcase.client.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.tools.EditContext;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.tools.EditProxy;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.TilePalette;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.XMLSyntaxHiliter;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.drawing.DrawLabel;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileRecord;
import com.smartgwt.client.widgets.tree.ResultTree;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class CollageEditorSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>This examples presents a simple collage editor based on a target canvas and an "+
        "EditContext. Photos from a Tile Palette can be dropped into the collage. These photos "+
        "can then be resized and dragged into the desired location to create a collage. Multiple "+
        "photos can be selected by dragging a rectangle around the desired photos allowing them "+
        "to be moved together.</p>"+
        "<P>"+
        "A snap-to grid is also defined to limit the default placement and resize options. "+
        "The grid is 20x20 pixels and is only shown when an active drag or resize takes place. "+
        "To temporarily disable the snap-to feature hold down the shift key while dragging.</p>"+
        "<P>"+
        "The resulting Component XML can be viewed at any time. Clicking on the \"Destroy and Recreate\" "+
        "button will recreate the collage from saved state.</p>";

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
            return new CollageEditorSample();
        }
    }

    public Canvas getViewPanel() {
	    // List of photos that will be placed on palette node tiles for selection
		List<String> photos = new ArrayList<String>();
		photos.add("shutterstock_102554147.jpg");
		photos.add("shutterstock_104226131.jpg");
		photos.add("shutterstock_107225813.jpg");
		photos.add("shutterstock_113330455.jpg");
		photos.add("shutterstock_115306546.jpg");
		photos.add("shutterstock_115736281.jpg");
		photos.add("shutterstock_127886615.jpg");
		photos.add("shutterstock_127992746.jpg");
		photos.add("shutterstock_68247031.jpg");
		photos.add("shutterstock_69642121.jpg");
		photos.add("shutterstock_69642124.jpg");
		photos.add("shutterstock_94938694.jpg");
		photos.add("shutterstock_97530329.jpg");
		photos.add("shutterstock_98725502.jpg");
		
		// The TilePalette contains components available
		// for use, with default settings.
		TilePalette tilePalette = new TilePalette();
		tilePalette.setWidth(300);
		tilePalette.setTileWidth(125);
		tilePalette.setTileHeight(125);
		tilePalette.setCanDragTilesOut(true);
	    
	    // We are supplying the component data inline for this example.
	    // However, the TilePalette is a subclass of TileGrid, so you could
	    // also use a dataSource.
		TileRecord[] data = new TileRecord[photos.size()];
	    for (int i = 0; i < photos.size(); i++) {
	        String title = "Photo " + (i+1);

	        Img img = new Img();
	        img.setTitle(title);
	        img.setSrc("stockPhotos/" + photos.get(i));
            
	        TileRecord record = new TileRecord();
	        record.setAttribute("title", title);
	        record.setAttribute("type", "Img");
            record.setAttribute("defaults", img.getPaletteDefaults());
            data[i] = record;
	    }
		tilePalette.setData(data);		
		
		DetailViewerField fieldType = new DetailViewerField();
		fieldType.setName("type");
        fieldType.setDetailFormatter(new DetailFormatter() {

			@Override
			public String format(Object value, Record record,
					DetailViewerField field) {
				String appDir = Page.getAppDir();
	            return "<img src='" + appDir + "images/" + 
                (JSOHelper.getAttribute((JavaScriptObject)record.getAttributeAsObject("defaults"), "src")) + 
                "' width='100' height='100'>";
			}
			
		});
				
		DetailViewerField fieldComponent = new DetailViewerField();
		fieldComponent.setName("title");
		fieldComponent.setTitle("Component");
		tilePalette.setFields(fieldType, fieldComponent);
		
		// The editCanvas is the root component in which the items can be placed.
		// This canvas will not be serialized - only the child nodes.
		final Canvas editCanvas = new Canvas();
		editCanvas.setBorder("1px solid black");
		editCanvas.setWidth100();
		editCanvas.setHeight100();
		
		EditProxy editProxy = new EditProxy();
		// Mask contained components for editing
		editProxy.setAutoMaskChildren(true);
		// Enable snapToGrid for all children.
        // In EditMode this also triggers display of the visual grid when
        // dragging and resizing.
		editProxy.setChildrenSnapToGrid(true);
		
		editCanvas.setAutoChildProperties("editProxy", editProxy);
		
		PaletteNode rootComponent = new PaletteNode();
		rootComponent.setType("Canvas");
		rootComponent.setCanvasLiveObject(editCanvas);
		
		final EditContext editContext = new EditContext();
		editContext.setDefaultPalette(tilePalette);
		editContext.setRootComponent(rootComponent);
		// Enable Canvas-based component selection, positioning and resizing support
		editContext.setCanSelectEditNodes(true);
		
		// Set the defaultEditContext on palette which is used when double-clicking on components.
		tilePalette.setDefaultEditContext(editContext);
		
		// Place editCanvas into editMode to allow paletteNode drops
		EditNode editCanvasEditNode = editContext.getRootEditNode();
		editCanvas.setEditMode(true, editContext, editCanvasEditNode);
		
		// A serialized sample collage to show initially 
        final StringBuffer sampleCollage = new StringBuffer();
		sampleCollage.append("<Img ID=\"Img0\" width=\"480\" height=\"280\" left=\"60\" top=\"60\" autoDraw=\"false\">\r\n");
		sampleCollage.append("<src>stockPhotos/shutterstock_104226131.jpg</src>\r\n<title>Photo 2</title>\r\n</Img>\r\n\r\n");
		sampleCollage.append("<Img ID=\"Img1\" width=\"480\" height=\"280\" left=\"400\" top=\"10\" autoDraw=\"false\">\r\n ");
		sampleCollage.append("<src>stockPhotos/shutterstock_107225813.jpg</src>\r\n<title>Photo 3</title>\r\n</Img>\r\n\r\n");
		sampleCollage.append("<Img ID=\"Img2\" width=\"440\" height=\"280\" left=\"10\" top=\"280\" autoDraw=\"false\">\r\n");
		sampleCollage.append("<src>stockPhotos/shutterstock_113330455.jpg</src>\r\n <title>Photo 4</title>\r\n</Img>\r\n\r\n");
		sampleCollage.append("<Img ID=\"Img3\" width=\"480\" height=\"260\" left=\"200\" top=\"160\" autoDraw=\"false\">\r\n");
		sampleCollage.append("<src>stockPhotos/shutterstock_102554147.jpg</src>\r\n <title>Photo 1</title>\r\n</Img>\r\n");
        
		// Place sample collage into editContext
		editContext.addPaletteNodesFromXML(sampleCollage.toString());
		
		HLayout hLayout = new HLayout();
		hLayout.setMembersMargin(20);
		hLayout.setWidth100();
		hLayout.setHeight100();
		hLayout.addMember(tilePalette);
		hLayout.addMember(editCanvas);
		
		final VLayout vLayout = new VLayout();
		vLayout.setHeight100();
		vLayout.setWidth100();
		vLayout.setMembersMargin(10);
		vLayout.addMember(hLayout);
		
		IButton showComponentXMLButton = new IButton();
		showComponentXMLButton.setTitle("Show Component XML");
		showComponentXMLButton.setAutoFit(true);
		showComponentXMLButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String paletteNodes = editContext.serializeAllEditNodes();
				XMLSyntaxHiliter syntaxHiliter = new XMLSyntaxHiliter();
				String formattedText = syntaxHiliter.hilite(paletteNodes);
				Window window = new Window();
				window.setWidth(Math.round(vLayout.getWidth() / 2));
				window.setDefaultHeight(Math.round(vLayout.getHeight() * 2/3));
				window.setTitle("Component XML");
				window.setAutoCenter(true);
				window.setShowMinimizeButton(false);
				window.setCanDragResize(true);
				window.setIsModal(true);
				window.setKeepInParentRect(true);
				
				Canvas component = new Canvas();
				component.setContents(formattedText);
				component.setCanSelectText(true);
				
				window.addItem(component);
				
				window.show();
			}
			
		});
		
		IButton reloadSampleButton = new IButton();
		reloadSampleButton.setTitle("Reload Sample Collage");
		reloadSampleButton.setAutoFit(true);
		reloadSampleButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Destroy all the nodes
		        editContext.destroyAll();

		        // Recreate sample drawing
		        editContext.addPaletteNodesFromXML(sampleCollage.toString());
			}
			
		});

		IButton clearButton = new IButton();
		clearButton.setTitle("Clear Collage");
		clearButton.setAutoFit(true);
		clearButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Destroy all the nodes
		        editContext.destroyAll();
			}
			
		});
		
		IButton destroyAndRecreateButton = new IButton();
		destroyAndRecreateButton.setTitle("Destroy and Recreate");
		destroyAndRecreateButton.setAutoFit(true);
		destroyAndRecreateButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// We save the context node data in a variable
		        final String paletteNodes = editContext.serializeAllEditNodes();

		        // Animate the disappearance of the editCanvas, since otherwise
		        // everything happens at once.
		        editCanvas.animateFade(0, new AnimationCallback() {

					@Override
					public void execute(boolean earlyFinish) {
			            // Once the animation is finished, destroy all the nodes
			            editContext.destroyAll();

			            // Then add them back from the serialized form
			            editContext.addPaletteNodesFromXML(paletteNodes);

			            // And make us visible again
			            editCanvas.setOpacity(100);
					}
		        	
		        });//, 2000, "smoothEnd" OJO
			}
		});
		
		HLayout actionBar = new HLayout();
		actionBar.setMembersMargin(10);
		actionBar.setWidth100();
		actionBar.setHeight(30);
		
		LayoutSpacer layoutSpacer1 = new LayoutSpacer();
		layoutSpacer1.setWidth("*");
		
		LayoutSpacer layoutSpacer2 = new LayoutSpacer();
		layoutSpacer2.setWidth(20);
		
		LayoutSpacer layoutSpacer3 = new LayoutSpacer();
		layoutSpacer3.setWidth(20);
		
		actionBar.addMember(layoutSpacer1);
		actionBar.addMember(showComponentXMLButton);
		actionBar.addMember(layoutSpacer2);
		actionBar.addMember(reloadSampleButton);
		actionBar.addMember(clearButton);
		actionBar.addMember(layoutSpacer3);
		actionBar.addMember(destroyAndRecreateButton);
		
		// inserts the action buttons into the overall layout for the example
		vLayout.addMember(actionBar, 0);
		
		return vLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

}
