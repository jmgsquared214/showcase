package com.smartgwt.sample.showcase.client.portal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.core.Function;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.tools.EditContext;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.SelectedEditNodesUpdatedEvent;
import com.smartgwt.client.tools.SelectedEditNodesUpdatedHandler;
import com.smartgwt.client.tools.TilePalette;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.XMLSyntaxHiliter;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.drawing.DrawItem;
import com.smartgwt.client.widgets.drawing.DrawPane;
import com.smartgwt.client.widgets.drawing.Gradient;
import com.smartgwt.client.widgets.drawing.SimpleGradient;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;
import com.smartgwt.sample.showcase.client.portal.ShapePalette.DrawItemTile;

public class DiagramEditorSample extends ShowcasePanel {

    private static final String DESCRIPTION =
			"<p>With the Tools framework it is easy to create a simple drawing editor. "
            + "This example uses a palette of pre-configured DrawItems that can be dropped into the "
            + "target DrawPane. Position, size, and styling properties of the DrawItems are "
            + "automatically persisted.</p>"
            + "<p>This sample persists into a JavaScript variable.  Double-click or drag some "
            + "Draw Items into the Draw Pane, then click the \"Destroy and Recreate\" button to "
            + "save the state, destroy the DrawItems, and then recreate them.</p>";

	class DrawingEditContext extends EditContext {
		
		private String sampleDrawing;

		public DrawingEditContext() {
			super();
			this.setEnableInlineEdit(true);
			this.setCanSelectEditNodes(true);
			loadSampleDrawing();
		}

	    private void setSampleDrawing(String componentXml) {
	        this.sampleDrawing = componentXml;
	        this.showSampleDrawing();
	    }
	    
	    private void loadSampleDrawing() {
            RPCRequest requestProperties = new RPCRequest();
            requestProperties.setUseSimpleHttp(true);
            requestProperties.setActionURL(Page.getURL("[APP]data/sampleDrawingData.xml"));
            
            RPCManager.sendRequest(requestProperties, new RPCCallback() {
                @Override
			     public void execute(RPCResponse response, Object rawData, RPCRequest request) {
                     setSampleDrawing((String)rawData);
                 }
            });
	    }

	    public void showSampleDrawing() {
	        this.destroyAll();

	        final DrawingEditContext self = this;
	        this.addPaletteNodesFromXML(this.sampleDrawing, null, null, new Function() {
				@Override
				public void execute() {
					self.configureDrawPane();
				}
	        });
	    }
	    
	    private void configureDrawPane() {
            // Node drops should be assigned to DrawPane
	    	EditNode drawPaneEditNode = this.getDrawPaneEditNode();
	    	this.setDefaultParent(drawPaneEditNode);
	    }

	    private EditNode getDrawPaneEditNode() {
	    	Tree editTree = this.getEditNodeTree();
	    	EditNode rootNode = this.getRootEditNode();
	    	TreeNode[] childNodes = editTree.getChildren(rootNode);
	    	EditNode editNode = (childNodes != null && childNodes.length > 0 ? new EditNode(childNodes[0].getJsObj()) : null);

	    	return editNode;
	    }
	};

	private DrawingEditContext editContext;
	private Gradient[] commonGradients;
    private PaletteNode emptyDrawPanePaletteNode;

	private VLayout vLayout;
	private DynamicForm exportForm;
	private DynamicForm itemPropertiesForm;
    private ShapeEditingForm shapeEditingForm;
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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#diagramming\" target=\"\">here</a> " +
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
            return new DiagramEditorSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }


    public interface MetaFactory extends BeanFactory.MetaFactory {
    	BeanFactory<Canvas> getCanvasBeanFactory();
    	BeanFactory<Img> getImgBeanFactory();
    	BeanFactory<DrawItemTile> getDrawItemTileBeanFactory();
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

    	if (commonGradients == null) commonGradients = createCommonGradients();
    	if (emptyDrawPanePaletteNode == null) emptyDrawPanePaletteNode = createEmptyDrawPanePaletteNode();

    	DrawItemTile.setCommonGradients(commonGradients);

        ShapePalette shapePalette = new ShapePalette();
    	TilePalette palette = shapePalette.createPalette();

    	editContext = new DrawingEditContext();
    	editContext.setDefaultPalette(palette);

        shapeEditingForm = new ShapeEditingForm(editContext);
        Canvas editCanvas = shapeEditingForm.getEditCanvas();
    	
    	PaletteNode rootComponent = new PaletteNode();
    	rootComponent.setType("Canvas");
    	rootComponent.setCanvasLiveObject(editCanvas);
    	editContext.setRootComponent(rootComponent);

    	// Set the defaultEditContext on palette which is used when double-clicking on components.
    	palette.setDefaultEditContext(editContext);

    	// Place editCanvas into editMode to allow paletteNode drops
    	EditNode editCanvasEditNode = editContext.getRootEditNode();
    	editCanvas.setEditMode(true, editContext, editCanvasEditNode);

    	// Start with an empty drawing until the sample is loaded
    	editContext.addFromPaletteNode(emptyDrawPanePaletteNode);
    	editContext.configureDrawPane();

    	editContext.addSelectedEditNodesUpdatedHandler(new SelectedEditNodesUpdatedHandler() {
			@Override
			public void onSelectedEditNodesUpdated(SelectedEditNodesUpdatedEvent event) {
				selectedEditNodesUpdated();
			}
		});

    	exportForm = this.createExportForm();
    	itemPropertiesForm = shapeEditingForm.createItemPropertiesForm();

    	VLayout leftLayout = new VLayout();
    	leftLayout.setMembersMargin(5);
    	leftLayout.setMembers(palette, exportForm);

    	VLayout rightLayout = new VLayout();
    	rightLayout.setWidth100();
    	rightLayout.setMembersMargin(5);
    	rightLayout.setMembers(editCanvas, itemPropertiesForm);

    	HLayout hLayout = new HLayout();
    	hLayout.setWidth100();
    	hLayout.setHeight100();
    	hLayout.setMembersMargin(20);
    	hLayout.setMembers(leftLayout, rightLayout);

    	vLayout = new VLayout();
    	vLayout.setWidth100();
    	vLayout.setHeight100();
    	vLayout.setMembersMargin(10);
    	vLayout.setMembers(createButtonBar(editContext, editCanvas), hLayout);

    	return vLayout;
	}

	 // The following gradients are shared by various DrawItem shapes and are 
	 // applied to the empty DrawPane as well as the palette tile DrawPanes
    private Gradient[] createCommonGradients() {
        final SimpleGradient ovalGradient = new SimpleGradient();
        ovalGradient.setId("oval");
        ovalGradient.setDirection(90);
        ovalGradient.setStartColor("#ffffff");
        ovalGradient.setEndColor("#99ccff");

        final SimpleGradient diamondGradient = new SimpleGradient();
        diamondGradient.setId("diamond");
        diamondGradient.setDirection(90);
        diamondGradient.setStartColor("#d3d3d3");
        diamondGradient.setEndColor("#666699");

        final SimpleGradient rectGradient = new SimpleGradient();
        rectGradient.setId("rect");
        rectGradient.setDirection(90);
        rectGradient.setStartColor("#f5f5f5");
        rectGradient.setEndColor("#a9b3b8");

        final SimpleGradient triangleGradient = new SimpleGradient();
        triangleGradient.setId("triangle");
        triangleGradient.setDirection(90);
        triangleGradient.setStartColor("#f5f5f5");
        triangleGradient.setEndColor("#667766");

        return new Gradient[] { ovalGradient, diamondGradient, rectGradient, triangleGradient }; 
	}

    private PaletteNode createEmptyDrawPanePaletteNode() {
    	// Empty DrawPane palette node use when clearing edit canvas
    	DrawPane defaults = new DrawPane();
        // The DrawPane must be focusable in order for a keypress to trigger inline editing
        // of a selected draw item whose EditProxy is configured with inlineEditEvent:"dblOrKeypress".
        defaults.setCanFocus(true);
    	defaults.setWidth100();
    	defaults.setHeight100();
    	defaults.setGradients(commonGradients);

    	PaletteNode node = new PaletteNode();
    	node.setType("DrawPane");
    	node.setCanvasDefaults(defaults);

    	return node;
    }

    public Layout createButtonBar(final DrawingEditContext editContext, final Canvas editCanvas) {
    	IButton showComponentXmlButton = new IButton("Show Component XML");
    	showComponentXmlButton.setAutoFit(true);
    	showComponentXmlButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String paletteNodes = editContext.serializeAllEditNodes();

				XMLSyntaxHiliter syntaxHiliter = new XMLSyntaxHiliter();
				String formattedText = syntaxHiliter.hilite(paletteNodes);
				Canvas canvas = new Canvas();
				canvas.setContents(formattedText);
				canvas.setCanSelectText(true);

				Window window = new Window();
				window.setWidth(Math.round(vLayout.getWidth() / 2));
				window.setDefaultHeight(Math.round(vLayout.getHeight() * 2/3));
				window.setTitle("Component XML");
				window.setAutoCenter(true);
				window.setShowMinimizeButton(false);
				window.setCanDragResize(true);
				window.setIsModal(true);
				window.setKeepInParentRect(true);
				window.addItem(canvas);

				window.show();
			}
		});

    	IButton reloadSampleButton = new IButton("Reload Sample Drawing");
    	reloadSampleButton.setAutoFit(true);
    	reloadSampleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
    	        // Recreate sample drawing
				editContext.showSampleDrawing();
			}
		});

    	IButton clearButton = new IButton("Clear Drawing");
    	clearButton.setAutoFit(true);
    	clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Destroy all the nodes
				editContext.destroyAll();

    	        // Create default DrawPane
    	        editContext.addFromPaletteNode(emptyDrawPanePaletteNode);
    	    	editContext.configureDrawPane();
			}
		});

    	IButton destroyAndRecreateButton = new IButton("Destroy and Recreate");
    	destroyAndRecreateButton.setAutoFit(true);
    	destroyAndRecreateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// We save the editPane node data in a variable
				final String paletteNodes = editContext.serializeAllEditNodes();

				// Animate the disappearance of the editCanvas, since otherwise
				// everything happens at once.
				editCanvas.animateFade(0, new AnimationCallback() {
					@Override
					public void execute(boolean earlyFinish) {
						// Once the animation is finished, destroy all the nodes
						editContext.destroyAll();

						// Then add them back from the serialized form
						editContext.addPaletteNodesFromXML(paletteNodes, null, null, new Function () {
							@Override
							public void execute() {
						    	editContext.configureDrawPane();
							}
						});

						// And make us visible again
						editCanvas.setOpacity(100);
					}
				}, 2000, AnimationAcceleration.SMOOTH_END);
			}
		});

    	LayoutSpacer leftSpace = new LayoutSpacer();
    	leftSpace.setWidth("*");

    	LayoutSpacer centerSpace = new LayoutSpacer();
    	centerSpace.setWidth(20);

    	LayoutSpacer rightSpace = new LayoutSpacer();
    	rightSpace.setWidth(20);

    	HLayout layout = new HLayout();
    	layout.setWidth100();
    	layout.setHeight(30);
    	layout.setMembersMargin(10);

    	layout.setMembers(leftSpace, showComponentXmlButton, centerSpace, reloadSampleButton, clearButton, rightSpace, destroyAndRecreateButton);

    	return layout;
    }

    private DynamicForm createExportForm() {
    	DynamicForm form = new DynamicForm();
    	form.setWidth100();
    	form.setNumCols(3);
    	form.setWrapItemTitles(false);

    	SelectItem formatItem = new SelectItem("format", "Export format");
    	LinkedHashMap valueMap = new LinkedHashMap();
    	valueMap.put("png", "PNG");
    	valueMap.put("pdf", "PDF");
    	formatItem.setValueMap(valueMap);
    	formatItem.setDefaultValue("png");

    	ButtonItem exportButton = new ButtonItem("export", "Export");
    	exportButton.setStartRow(false);
    	exportButton.setEndRow(false);
    	exportButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				EditNode drawPaneNode = editContext.getDrawPaneEditNode();
				DrawPane drawPane = drawPaneNode.getDrawPaneLiveObject();
				String format = event.getForm().getValueAsString("format");
				DSRequest requestProperties = new DSRequest();
				requestProperties.setExportDisplay(ExportDisplay.DOWNLOAD);
				requestProperties.setExportFilename("Diagram");
				if ("png".equals(format)) RPCManager.exportImage(drawPane.getSvgString(), requestProperties);
				else RPCManager.exportContent(drawPane, requestProperties);
			}
		});

    	form.setFields(formatItem, exportButton);

    	return form;
    }
    
    private void selectedEditNodesUpdated() {
    	final DynamicForm form = itemPropertiesForm;

    	EditNode[] selection = shapeEditingForm.getSelectedNodes();
        if (selection.length == 0 || selection.length > 1 || 
            selection[0].getLiveObject() instanceof DrawPane) 
        {
    		// No selection or multiple selection
    		form.getField("lineColor").disable();
    		form.getField("fillColor").disable();
    		form.getField("arrows").disable();
    		boolean disabled = (selection.length == 0);
    		form.getField("removeItem").setDisabled(disabled);

    		form.clearValue("lineColor");
    		form.clearValue("fillColor");
    		form.clearValue("arrows");
    	} else {
    		form.getField("removeItem").enable();
    		
    		// Enable only property controls that are applicable to selection
    		EditNode node = selection[0];
    		DrawItem item = node.getDrawItemLiveObject();
    		String itemClassName = SC.getSCClassName(item.getJsObj());
    		boolean supportsStartArrow = item.supportsStartArrow();
    		boolean supportsEndArrow = item.supportsEndArrow();

    		form.getField("lineColor").setDisabled(!SC.isMethodSupported(itemClassName, "setLineColor"));
    		form.getField("fillColor").setDisabled(!SC.isMethodSupported(itemClassName, "setFillColor"));
    		form.getField("arrows").setDisabled(!supportsStartArrow && !supportsEndArrow);

    		// Update the arrow selections based on the item's support
    		List<String> arrowsValueMap = new ArrayList<String>();
    		arrowsValueMap.add("None");
    		if (supportsStartArrow) arrowsValueMap.add("Start");
    		if (supportsEndArrow) arrowsValueMap.add("End");
    		if (supportsStartArrow && supportsEndArrow) arrowsValueMap.add("Both");
    		form.getField("arrows").setValueMap(arrowsValueMap.toArray(new String[] {}));

    		// Update the form with current values
    		String arrows = (item.getStartArrow() != null && item.getEndArrow() != null ? "Both" : (item.getStartArrow() != null? "Start" : (item.getEndArrow() != null ? "End" : "None")));
    		form.setValue("lineColor", item.getLineColor());
    		form.setValue("fillColor", item.getFillColor());
    		form.setValue("arrows", arrows);
    	}
    }

    public String getIntro() {
    	return DESCRIPTION;
    }

    
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
            new SourceEntity("ShapePalette.java", JAVA, "source/portal/ShapePalette.java.html", false),
            new SourceEntity("ShapeEditingForm.java", JAVA, "source/portal/ShapeEditingForm.java.html", false),
            new SourceEntity("sampleDrawingData.xml", XML, "source/sampleDrawingData.xml.html", false)
        };
    }
}
