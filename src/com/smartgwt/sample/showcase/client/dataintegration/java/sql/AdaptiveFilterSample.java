package com.smartgwt.sample.showcase.client.dataintegration.java.sql;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.ResponseTransformer;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class AdaptiveFilterSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Smart GWT combines large dataset handling with <b>adaptive</b> use of "+
        "client-side filtering. Begin typing an Item name in the filter box above the "+
        "\"Item\" column (for example, enter \"add\"). When the dataset "+
        "becomes small enough, Smart GWT switches to client-side filtering automatically "+
        "&mdash; enter more letters, or criteria on other columns, to see this. The label "+
        "underneath the grid flashes briefly every time Smart GWT needs to visit the " +
        "server.</p>"+
        "<p>Delete part of the item name to see Smart GWT automatically switch back to "+
        "server-side filtering when necessary.</p>"+
        "<p>Adaptive filtering eliminates up to 90% of the most costly types of server contact "+
        "(searching through large datasets), <b>dramatically improving responsiveness and "+
        "scalability.</b></p>";
    
    public static class Factory implements PanelFactory {
        private String id;

        public ShowcasePanel create() {
            AdaptiveFilterSample panel = new AdaptiveFilterSample();
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

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {

        Canvas canvas = new Canvas();

        final ServerCountLabel serverCountLabel = new ServerCountLabel();

        DataSource ds = DataSource.get("supplyItem", null, new ResponseTransformer() {
            protected void transformResponse(DSResponse response, DSRequest request, Object data) {
                if(request.getOperationType() == DSOperationType.FETCH) {
                    int totalRows = response.getTotalRows();
                    int startRow = response.getStartRow();
                    int endRow = response.getEndRow();
                    serverCountLabel.incrementAndUpdate(totalRows, startRow, endRow);
                    serverCountLabel.setBackgroundColor("ffff77");
                    new Timer() {
                        public void run() {
                            serverCountLabel.setBackgroundColor("ffffff");
                        }
                    }.schedule(500);
                }
                defaultTransformResponse(response, request, data);
            }});
        
        final ListGrid supplyItemGrid = new ListGrid();
        supplyItemGrid.setWidth(660);
        supplyItemGrid.setHeight(300);
        supplyItemGrid.setAutoFetchData(true);
        supplyItemGrid.setShowFilterEditor(true);
        supplyItemGrid.setFilterOnKeypress(true);
        supplyItemGrid.setFetchDelay(500);
        supplyItemGrid.setDataSource(ds);

        ListGridField skuField = new ListGridField("SKU", 64);
        ListGridField nameField = new ListGridField("itemName", 192);
        ListGridField descriptionField = new ListGridField("description", 256);
        ListGridField categoryField = new ListGridField("category", 128);

        supplyItemGrid.setFields(skuField, nameField, descriptionField, categoryField );
                
        canvas.addChild(supplyItemGrid);
        canvas.addChild(serverCountLabel);

        return canvas;
    }


    public String getIntro() {
        return DESCRIPTION;
    }

    class ServerCountLabel extends Label {
        private int count = 0;

        ServerCountLabel() {
        setContents("<b>Number of server trips : 0</b>");
        setTop(320);
        setPadding(10);
        setWidth(500);
        setHeight(30);
        setBorder("1px solid #6a6a6a");
        }

        public void incrementAndUpdate(int totalRows, int startRow, int endRow) {
            count++;
            setContents("<b>Number of server trips: " + count +
                    "<br/>Total rows in this filter set: " + totalRows +
                    "<br/>Last range of records returned: " + startRow + " to " + endRow + "</b>");
        }
    }
}