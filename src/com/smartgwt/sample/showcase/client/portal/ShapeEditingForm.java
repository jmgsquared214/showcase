package com.smartgwt.sample.showcase.client.portal;

import com.smartgwt.client.tools.EditContext;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.drawing.DrawItem;
import com.smartgwt.client.widgets.drawing.Gradient;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ColorItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.PickerColorSelectedEvent;
import com.smartgwt.client.widgets.form.fields.events.PickerColorSelectedHandler;
import com.smartgwt.sample.showcase.client.portal.DiagramEditorSample.DrawingEditContext;

public class ShapeEditingForm {
    
    private DrawingEditContext editContext = null;
    
    public ShapeEditingForm(DrawingEditContext editContext) {
        this.editContext = editContext;
    }
 
    public Canvas getEditCanvas() {
    	// The editCanvas is the root component in which the items can be placed.
    	// This canvas will not be serialized - only the child nodes.
    	Canvas editCanvas = new Canvas("editCanvas");
    	editCanvas.setWidth100();
    	editCanvas.setBorder("1px solid black");
        editCanvas.setCanFocus(true);
        editCanvas.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
            	if ("Delete".equals(event.getKeyName())) {
                    removeSelectedItems();
                }
            }
        });
        return editCanvas;
    }
    
    public DynamicForm createItemPropertiesForm() {
    	DynamicForm form = new DynamicForm();
    	form.setWidth100();
    	form.setNumCols(8);
    	form.setColWidths(100, 100, 100, 50, 50, 50, 50, 50);
    	form.setTitleOrientation(TitleOrientation.TOP);

    	ColorItem lineColorItem = new ColorItem("lineColor", "Line color");
    	lineColorItem.setSupportsTransparency(true);
    	lineColorItem.setDisabled(true);
    	lineColorItem.addPickerColorSelectedHandler(new PickerColorSelectedHandler() {
			@Override
			public void onPickerColorSelected(PickerColorSelectedEvent event) {
				setPropertyOnSelection("lineColor", event.getColor());
				setPropertyOnSelection("lineOpacity", event.getOpacity());
			}
		});

    	ColorItem fillColorItem = new ColorItem("fillColor", "Fill color");
    	fillColorItem.setSupportsTransparency(true);
    	fillColorItem.setDisabled(true);
    	fillColorItem.addPickerColorSelectedHandler(new PickerColorSelectedHandler() {
			@Override
			public void onPickerColorSelected(PickerColorSelectedEvent event) {
				setPropertyOnSelection("fillGradient", null);
				setPropertyOnSelection("fillColor", event.getColor());
				setPropertyOnSelection("fillOpacity", event.getOpacity() / 100);
			}
		});

    	SelectItem arrowsItem = new SelectItem("arrows", "Arrows");
    	arrowsItem.setDisabled(true);
    	arrowsItem.setValueMap("None", "Start", "End", "Both");
    	arrowsItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String value = (String)event.getValue();
				setPropertyOnSelection("startArrow", (value == "Start" || value == "Both" ? "block" : null));
	            setPropertyOnSelection("endArrow", (value == "End" || value == "Both" ? "block" : null));
			}
		});

    	SpacerItem spacerItem = new SpacerItem();
    	spacerItem.setShowTitle(false);

    	ButtonItem removeButton = new ButtonItem("removeItem", "Remove");
    	removeButton.setVAlign(VerticalAlignment.BOTTOM);
    	removeButton.setStartRow(false);
    	removeButton.setEndRow(false);
    	removeButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				removeSelectedItems();
			}
		});

    	form.setFields(lineColorItem, fillColorItem, arrowsItem, spacerItem, removeButton);

    	return form;
    	
    }

    private void setPropertyOnSelection(String property, String value) {
    	DrawItem properties = new DrawItem();
    	properties.setAttribute(property, value, false);

    	EditNode[] selection = this.getSelectedNodes();
    	for (EditNode editNode : selection) {
    		this.editContext.setNodeProperties(editNode, properties);
    		if (value == null) {
    			// Remove property when null - set to null above to trigger UI change
    			this.editContext.removeNodeProperties(editNode, new String[] { property });
    		}

    	}
    }

    private void setPropertyOnSelection(String property, int value) {
    	DrawItem properties = new DrawItem();
    	properties.setAttribute(property, value, false);

    	EditNode[] selection = this.getSelectedNodes();
    	for (EditNode editNode : selection) {
    		this.editContext.setNodeProperties(editNode, properties);
    	}
    }
    
    public EditNode[] getSelectedNodes() {
    	return (editContext != null ? editContext.getSelectedEditNodes() : new EditNode[] {});
    }
    
    private void removeSelectedItems() {
    	EditNode[] selection = this.getSelectedNodes();
    	for (EditNode editNode : selection) {
    		// Remove node from editContext and destroy it
    		this.editContext.removeNode(editNode);
    		editNode.getDrawItemLiveObject().destroy();
    	}
    }
}