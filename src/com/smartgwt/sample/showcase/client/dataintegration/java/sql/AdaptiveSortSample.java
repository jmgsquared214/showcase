package com.smartgwt.sample.showcase.client.dataintegration.java.sql;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.ResponseTransformer;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.BooleanItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class AdaptiveSortSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Smart GWT combines large dataset handling with <b>adaptive</b> use of "+
        "client-side sort.  Click any header and server-side sort will be used for this "+
        "large dataset.  Check \"Limit to Electronics\" to limit the dataset and sort again. "+
        "When the dataset becomes small enough, Smart GWT switches to client-side "+
        "sorting automatically.  The label underneath the grid flashes briefly "+ 
        "every time Smart GWT needs to visit the server.</p>";
    
    public static class Factory implements PanelFactory {
        private String id;

        public ShowcasePanel create() {
            AdaptiveSortSample panel = new AdaptiveSortSample();
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
        supplyItemGrid.setDataPageSize(20);
        supplyItemGrid.setDataSource(ds);

        ListGridField skuField = new ListGridField("SKU", 64);
        ListGridField nameField = new ListGridField("itemName", 192);
        ListGridField descriptionField = new ListGridField("description", 256);
        ListGridField categoryField = new ListGridField("category", 128);

        supplyItemGrid.setFields(skuField, nameField, descriptionField, categoryField );
        
        final DynamicForm form = new DynamicForm();
        form.setWidth(300);
        form.setTop(310);
        
        final BooleanItem restrictField = new BooleanItem();
        restrictField.setTitle("Limit to Electronics");
        restrictField.addChangedHandler(new ChangedHandler() {
        	public void onChanged(ChangedEvent e) {
        		Criteria c = new Criteria();
        		if (restrictField.getValue() != null && restrictField.getValueAsBoolean() == Boolean.TRUE) {
        			c.addCriteria("category", "Office Machines and Electronics");
        		}
        		supplyItemGrid.fetchData(c);
        	}
        });
        form.setFields(restrictField);
        
        Canvas canvas = new Canvas();
        canvas.addChild(supplyItemGrid);
        canvas.addChild(form);
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
        setTop(350);
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