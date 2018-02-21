package com.smartgwt.sample.showcase.client.draganddrop;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class RecategorizeTree extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "Dragging items from the list and dropping them on categories in the tree "+
        "automatically re-categorizes the item, without any code needed. "+
        "Make changes, then reload the page; the changes persist. "+
        "This behavior is (optionally) automatic where Smart GWT can establish a "+
        "relationship via a <code>foreignKey</code> between the DataSources to components that are bound to it.";

    public static class Factory implements PanelFactory {
        private String id;
        public ShowcasePanel create() {
            RecategorizeTree panel = new RecategorizeTree();
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
        TreeGrid categoryTree = new TreeGrid();
        categoryTree.setWidth("30%");
        categoryTree.setDataSource(DataSource.get("supplyCategory"));
        categoryTree.setAutoFetchData(true);
        categoryTree.setShowResizeBar(true);
        categoryTree.setCanAcceptDroppedRecords(true);
        
        ListGrid itemList = new ListGrid();
        itemList.setWidth("70%");
        itemList.setDataSource(DataSource.get("supplyItem"));
        itemList.setAutoFetchData(true);
        itemList.setCanDragRecordsOut(true);
        itemList.setFields(new ListGridField[] {
        	new ListGridField("itemName"),
        	new ListGridField("SKU"),
        	new ListGridField("category")
        });
        
        HLayout mainLayout = new HLayout();
        mainLayout.addMember(categoryTree);
        mainLayout.addMember(itemList);
        return mainLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}

