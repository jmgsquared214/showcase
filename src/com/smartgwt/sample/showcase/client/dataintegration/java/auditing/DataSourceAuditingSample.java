package com.smartgwt.sample.showcase.client.dataintegration.java.auditing;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RemoveRecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RemoveRecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class DataSourceAuditingSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>DataSources can automatically capture a log of changes by storing Records in a second " + 
    				"DataSource, called an \"audit DataSource\"." +
					"<p>" +
					"Click in the grid below to edit <code>supplyItem</code> records.  Underneath the separator " +
					"labeled \"Browse Audit Data\" is a second grid that shows the audit data captured as changes " +
					"are made to <code>supplyItem</code> records." +
					"<p>" +
					"The audit DataSource is automatically created, and because it's a normal DataSource, you " +
					"can simply bind a ListGrid to it to view the audit data, and the audit data is also " +
					"searchable just like any DataSource."+
					"<p>"+
					"Enabling this feature just requires setting <code>audit=\"true\"</code> in the DataSource " +
					"definition (.ds.xml file); the audit DataSource is created automatically, and for " +
					"SQLDataSource, even the underlying SQL table is automatically created so no other settings "+
					"are needed. ";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
        	DataSourceAuditingSample panel = new DataSourceAuditingSample();
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

        final DataSource auditedDS = DataSource.get("supplyItemAudited");
        final DataSource auditDS = DataSource.get("audit_supplyItemAudited");
        final RadioGroupItem auditFor = new RadioGroupItem();

        final ListGrid auditList = new ListGrid();
        auditList.setWidth(700);
        auditList.setHeight(224);
        auditList.setDataSource(auditDS);
        auditList.setAutoFetchData(false);
        auditList.setSortField("audit_revision");
        auditList.setSortDirection(SortDirection.DESCENDING);
        auditList.setShowFilterEditor(true);
        
        final ListGrid supplyItemList = new ListGrid();
        supplyItemList.setWidth(700);
        supplyItemList.setHeight(224);
        supplyItemList.setDataSource(auditedDS);
        supplyItemList.setCanEdit(true);
        supplyItemList.setAutoFetchData(true);
        supplyItemList.setCanRemoveRecords(true);
        
        supplyItemList.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if (!"Selected Record".equals(auditFor.getValue())) {
					auditFor.setValue("Selected Record");
				}
				
				auditList.fetchData(new Criteria("itemID", event.getRecord().getAttribute("itemID")));
			}
		});
        
        supplyItemList.addRemoveRecordClickHandler(new RemoveRecordClickHandler() {
			
			@Override
			public void onRemoveRecordClick(RemoveRecordClickEvent event) {
				
				if ("Selected Record".equals(auditFor.getValue())) {
					auditFor.setValue("All Records");
					auditList.clearCriteria();
					
				} else {
					auditList.invalidateCache();
				}
			}
		});

        supplyItemList.addEditCompleteHandler(new EditCompleteHandler() {
			
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				auditList.invalidateCache();
			}
		});
        
        
        final Label separator = new Label();
       	
        separator.setContents("Browse Audit Data");
        separator.setWidth("100%");
        separator.setHeight(25);
        separator.setBaseStyle("exampleSeparator");
        separator.setAutoDraw(true);
        
        auditFor.setTitle("Show Audit Trail for");  
        auditFor.setValueMap("Selected Record", "All Records"); 
        auditFor.setDefaultValue("Selected Record");
        auditFor.setVertical(false);
        
        auditFor.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if ("All Records".equals(event.getValue())) {
					auditList.clearCriteria();
				} else {
					ListGridRecord record = supplyItemList.getSelectedRecord();
					
					if (record != null) {
						auditList.fetchData(new Criteria("itemID", record.getAttributeAsString("itemID")));
					} else {
						auditList.fetchData(new Criteria("itemID", "-1"));
					}
				}
			}
		});
        
        DynamicForm auditForm = new DynamicForm();
        auditForm.setTitleWidth("140");
        
        auditForm.setFields( auditFor );
        VLayout layout = new VLayout(15);
        layout.addMembers(supplyItemList, separator, auditForm, auditList);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}