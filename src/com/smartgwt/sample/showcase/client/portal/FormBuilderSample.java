package com.smartgwt.sample.showcase.client.portal;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.tools.EditContext;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.tools.FormEditProxy;
import com.smartgwt.client.tools.ListPalette;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.SelectItemsMode;
import com.smartgwt.client.types.SelectedAppearance;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.XMLSyntaxHiliter;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class FormBuilderSample extends ShowcasePanel {

    private static final String DESCRIPTION =
    		"<p>With the Tools framework it is easy to create a simple form editor. "
    		+ "This example uses a palette of pre-configured form items that can be dropped into the "
			+ "target DynamicForm to assemble the desired fields.</p>"
    		+ "<p>The field title can be edited by double-clicking on the field or title. The value "
			+ "entered into a field is saved in the FormItem's <code>defaultValue</code> property. "
    		+ "The <code>defaultValue</code> for a BlurbItem is edited like a title. <code>ValueMap</code> "
			+ "values can be entered by right-clicking on the field and selecting Edit Options.<p>"
    		+ "<p>An EditContext is used to save and restore the configured form and FormItems.</p>";

	class FormEditContext extends EditContext {
		
		public void configureForm() {
	    	EditNode formEditNode = this.getFormEditNode();
	    	if (formEditNode != null) {
	            // Node drops should be assigned to Form
	    		this.setDefaultParent(formEditNode);
	    	} else {
	    		this.setDefaultParent(null);
	    	}
	    }

	    private EditNode getFormEditNode() {
	    	Tree editTree = this.getEditNodeTree();
	    	EditNode rootNode = this.getRootEditNode();
	    	TreeNode[] childNodes = editTree.getChildren(rootNode);
	    	EditNode editNode = (childNodes != null && childNodes.length > 0 ? new EditNode(childNodes[0].getJsObj()) : null);

	    	return editNode;
	    }
	};

    private PaletteNode initialFormPaletteNode;

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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#formBuilder\" target=\"\">here</a> " +
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
            return new FormBuilderSample();
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

    	if (initialFormPaletteNode == null) initialFormPaletteNode = createInitialFormPaletteNode();

    	ListPalette palette = createPalette();

    	// The editCanvas is the root component in which the items can be placed.
    	Canvas editCanvas = new Canvas("editCanvas");
    	editCanvas.setWidth100();
    	editCanvas.setBorder("1px solid black");

    	final FormEditContext editContext = new FormEditContext();
    	editContext.setDefaultPalette(palette);
    	
    	// Enable display of a selectionOutline for the currently
    	// selected item
    	editContext.setSelectedAppearance(SelectedAppearance.OUTLINEEDGES);

    	// Customize the outline and the associated label
    	editContext.setSelectedBorder("1px dashed teal");
    	editContext.setSelectedLabelBackgroundColor("yellow");

    	PaletteNode rootComponent = new PaletteNode();
    	rootComponent.setType("Canvas");
    	rootComponent.setCanvasLiveObject(editCanvas);
    	editContext.setRootComponent(rootComponent);

    	// Set the defaultEditContext on palette which is used when double-clicking on components.
    	palette.setDefaultEditContext(editContext);

    	// Place editCanvas into editMode to allow paletteNode drops
    	EditNode editCanvasEditNode = editContext.getRootEditNode();
    	editCanvas.setEditMode(true, editContext, editCanvasEditNode);


    	// Place base component (DynamicForm) into editContext. All paletteNodes are
    	// FormItems that will be added to this form
    	editContext.addFromPaletteNode(initialFormPaletteNode);
    	editContext.configureForm();

    	HLayout hLayout = new HLayout();
    	hLayout.setWidth100();
    	hLayout.setHeight100();
    	hLayout.setMembersMargin(20);
    	hLayout.setMembers(palette, editCanvas);

    	vLayout = new VLayout();
    	vLayout.setWidth100();
    	vLayout.setHeight100();
    	vLayout.setMembersMargin(10);
    	vLayout.setMembers(createButtonBar(editContext, editCanvas), hLayout);

    	return vLayout;
    }

    private PaletteNode createInitialFormPaletteNode() {
    	// Palette Node used to seed edit context
    	DynamicForm defaults = new DynamicForm();
    	defaults.setWidth100();
    	defaults.setHeight100();
    	PaletteNode node = new PaletteNode();
    	node.setType("DynamicForm");
    	node.setCanvasDefaults(defaults);

    	FormEditProxy properties = new FormEditProxy();
    	// Don't allow the Form to be selected
    	properties.setCanSelect(false);
    	// By default a FormItem is selectable only by clicking the item
    	// itself. This setting allows selection by clicking the title as well.
    	properties.setSelectItemsMode(SelectItemsMode.ITEMORTITLE);
    	node.setEditProxyProperties(properties);

    	return node;
    }

    private ListPalette createPalette() {
    	ListPalette palette = new ListPalette() {
    		public String getValueIcon(ListGridField field, Object value, ListGridRecord record) {
    			return (record.getAttribute("icon") != null ? "formItemIcons/" + record.getAttribute("icon") : null);
    		}
    	};
    	palette.setWidth(125);
    	palette.setHeight(100);	// Minimum height
    	palette.setAutoFitData(Autofit.VERTICAL);
    	palette.setLeaveScrollbarGap(false);

    	// The regular ListGrid property "fields"
    	palette.setFields(new ListGridField("title", "Form Component"));

    	// We are supplying the component data inline for this example.
    	// However, ListPalette is a subclass of ListGrid, so you could
    	// also use a DataSource.
    	palette.setData(getPaletteData());

    	return palette;
    }

    private static Record[] getPaletteData() {
    	List<PaletteNode> nodes = new ArrayList<PaletteNode>();

    	PaletteNode node = new PaletteNode();
    	// Title as you want it to appear in the list
    	node.setTitle("Text");
    	node.setIcon("text.gif");
    	// type indicates the class of object to create for
    	// this component
    	node.setType("TextItem");
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Static Text");
    	node.setIcon("staticText.gif");
    	node.setType("StaticTextItem");
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Blurb");
    	node.setIcon("blurb.gif");
    	node.setType("BlurbItem");
    	FormItem defaults = new FormItem();
    	defaults.setDefaultValue("This is a blurb");
    	node.setFormItemDefaults(defaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Checkbox");
    	node.setIcon("checkbox.gif");
    	node.setType("CheckboxItem");
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Select");
    	node.setIcon("select.gif");
    	node.setType("SelectItem");
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("ComboBox");
    	node.setIcon("comboBox.gif");
    	node.setType("ComboBoxItem");
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Radio");
    	node.setIcon("radioGroup.gif");
    	node.setType("RadioGroupItem");
    	defaults = new FormItem();
    	defaults.setValueMap("Yes", "No");
    	node.setFormItemDefaults(defaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Date");
    	node.setIcon("date.gif");
    	node.setType("DateItem");
    	DateItem dateDefaults = new DateItem();
    	dateDefaults.setUseTextField(true);
    	node.setFormItemDefaults(dateDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Time");
    	node.setIcon("time.gif");
    	node.setType("TimeItem");
    	nodes.add(node);

    	return nodes.toArray(new PaletteNode[] {});
    }

    public Layout createButtonBar(final EditContext editContext, final Canvas editCanvas) {
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
				window.setAutoCenter(true);;
				window.setShowMinimizeButton(false);
				window.setCanDragResize(true);
				window.setIsModal(true);
				window.setKeepInParentRect(true);
				window.addItem(canvas);

				window.show();
			}
		});

    	IButton clearButton = new IButton("Clear Form");
    	clearButton.setAutoFit(true);
    	clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Destroy all the nodes
				editContext.destroyAll();

    	        // Create default DynamicForm
    	        editContext.addFromPaletteNode(initialFormPaletteNode);
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
						editContext.addPaletteNodesFromXML(paletteNodes);

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

    	layout.setMembers(leftSpace, showComponentXmlButton, centerSpace, clearButton, rightSpace, destroyAndRecreateButton);

    	return layout;
    }

    public String getIntro() {
    	return DESCRIPTION;
    }
}
