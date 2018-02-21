package com.smartgwt.sample.showcase.client.draganddrop;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class RecategorizeTile extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "Drag and drop animals between the grids in either direction, and the status of the "+
        "dropped tile will change to match the filtered status of the TileGrid in which it "+
        "was dropped. Select different values in the drop down lists above each TileGrid "+
        "to change the animals that will appear in each grid.";

    public static class Factory implements PanelFactory {
        private String id;
        public ShowcasePanel create() {
            RecategorizeTile panel = new RecategorizeTile();
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
        final DataSource ds = DataSource.get("animals");
        final String[] statusValueMap = {
            "Threatened",
            "Endangered",
            "Not Endangered",
            "Not currently listed",
            "May become threatened",
            "Protected"
        };
        final String leftDefaultStatus = statusValueMap[1];
        final String rightDefaultStatus = statusValueMap[0];
        
        final TileGrid leftGrid = new TileGrid();
        leftGrid.setTileWidth(150);
        leftGrid.setTileHeight(185);
        leftGrid.setShowAllRecords(true);
        leftGrid.setDataSource(ds);
        leftGrid.setAutoFetchData(true);
        leftGrid.setInitialCriteria(new Criteria("status", leftDefaultStatus));
        leftGrid.setAutoFetchTextMatchStyle(TextMatchStyle.EXACT);
        leftGrid.setAnimateTileChange(true);
        leftGrid.setCanAcceptDrop(true);
        leftGrid.setCanDrag(true);
        leftGrid.setFields(new DetailViewerField("picture"), 
                             new DetailViewerField("commonName"), 
                             new DetailViewerField("status"));
          
        final DynamicForm leftForm = new DynamicForm();
        leftForm.addItemChangedHandler(new ItemChangedHandler() {
        	public void onItemChanged(ItemChangedEvent event) {
        		leftGrid.fetchData(leftForm.getValuesAsCriteria());
        	}
        });
        SelectItem leftSelectItem = new SelectItem("status", "<b>Status</b>");
        leftSelectItem.setType("select");
        leftSelectItem.setValueMap(statusValueMap);
        leftSelectItem.setDefaultValue(leftDefaultStatus);
        leftForm.setFields(leftSelectItem);
        
        final TileGrid rightGrid = new TileGrid();
        rightGrid.setTileWidth(150);
        rightGrid.setTileHeight(185);
        rightGrid.setShowAllRecords(true);
        rightGrid.setDataSource(ds);
        rightGrid.setAutoFetchData(true);
        rightGrid.setInitialCriteria(new Criteria("status", rightDefaultStatus));
        rightGrid.setAutoFetchTextMatchStyle(TextMatchStyle.EXACT);
        rightGrid.setAnimateTileChange(true);
        rightGrid.setCanAcceptDrop(true);
        rightGrid.setCanDrag(true);
        rightGrid.setFields(new DetailViewerField("picture"), 
                             new DetailViewerField("commonName"), 
                             new DetailViewerField("status"));
        
        final DynamicForm rightForm = new DynamicForm();
        rightForm.addItemChangedHandler(new ItemChangedHandler() {
        	public void onItemChanged(ItemChangedEvent event) {
        		rightGrid.fetchData(rightForm.getValuesAsCriteria());
        	}
        });
        SelectItem rightSelectItem = new SelectItem("status", "<b>Status</b>");
        rightSelectItem.setType("select");
        rightSelectItem.setDefaultValue(rightDefaultStatus);
        rightSelectItem.setValueMap(statusValueMap);
        rightForm.setFields(rightSelectItem);
        
        VLayout leftLayout = new VLayout();
        leftLayout.addMember(leftForm);
        leftLayout.addMember(leftGrid);
        
        VLayout rightLayout = new VLayout();
        rightLayout.addMember(rightForm);
        rightLayout.addMember(rightGrid);
        
        HLayout mainLayout = new HLayout();
        mainLayout.addMember(leftLayout);
        mainLayout.addMember(rightLayout);
        return mainLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}

