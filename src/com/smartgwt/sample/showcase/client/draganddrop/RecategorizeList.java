package com.smartgwt.sample.showcase.client.draganddrop;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class RecategorizeList extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "The two lists are showing items in different categories. Drag items from one list "+
        "to another to automatically recategorize the items without writing any code. "+
        "Make changes, then reload the page; the changes persist.";

    public static class Factory implements PanelFactory {
        private String id;
        public ShowcasePanel create() {
            RecategorizeList panel = new RecategorizeList();
            id = panel.getID();
            return panel;
        }
        public String getID() {
            return id;
        }
        public String getDescription() {
            return DESCRIPTION;
        }
    }

    public Canvas getViewPanel() {
        final DataSource ds = DataSource.get("supplyItem");
        
        final ListGrid leftList = new ListGrid();
        leftList.setCanDragRecordsOut(true);
        leftList.setCanAcceptDroppedRecords(true);
        leftList.setCanReorderRecords(true);
		leftList.setAlternateRecordStyles(true);
		leftList.setDataSource(ds);
        leftList.setAutoFetchData(true);
        leftList.setInitialCriteria(new Criteria("category", "Manilla Folders"));
        leftList.setAutoFetchTextMatchStyle(TextMatchStyle.EXACT);
		leftList.setDragDataAction(DragDataAction.MOVE);
		leftList.setFields(new ListGridField[] {
	        	new ListGridField("itemName"),
	        	new ListGridField("SKU"),
	        	new ListGridField("category")
	        });
        
        final ListGrid rightList = new ListGrid();
        rightList.setCanDragRecordsOut(true);
        rightList.setCanAcceptDroppedRecords(true);
        rightList.setCanReorderRecords(true);
		rightList.setAlternateRecordStyles(true);
		rightList.setDataSource(ds);
        rightList.setAutoFetchData(true);
        rightList.setInitialCriteria(new Criteria("category", "Lever Arch Files"));
        rightList.setAutoFetchTextMatchStyle(TextMatchStyle.EXACT);
		rightList.setDragDataAction(DragDataAction.MOVE);
		rightList.setFields(new ListGridField[] {
	        	new ListGridField("itemName"),
	        	new ListGridField("SKU"),
	        	new ListGridField("category")
	        });
        
        Img arrowRightImg = new Img("icons/32/arrow_right.png", 32, 32);
        arrowRightImg.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		rightList.transferSelectedData(leftList);
        	}
        });
        
        Img arrowLeftImg = new Img("icons/32/arrow_left.png", 32, 32);
        arrowLeftImg.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		leftList.transferSelectedData(rightList);
        	}
        });
        
        VStack arrowStack = new VStack(10);
        arrowStack.setWidth(32);
        arrowStack.setHeight(74);
        arrowStack.setLayoutAlign(Alignment.CENTER);
        arrowStack.addMember(arrowRightImg);
        arrowStack.addMember(arrowLeftImg);
        
        HLayout hLayout = new HLayout();
        hLayout.addMember(leftList);
        hLayout.addMember(arrowStack);
        hLayout.addMember(rightList);
        return hLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}

