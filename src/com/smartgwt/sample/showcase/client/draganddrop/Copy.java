package com.smartgwt.sample.showcase.client.draganddrop;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class Copy extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "Drag employee records into the Project Team Members list. "+
        "Smart GWT recognizes that the two DataSources are linked by a <code>foreignKey</code> relationship," +
        " and automatically uses that relationship to populate values in the record that is added when a drop occurs. "+
        "Smart GWT also populates fields based on current criteria and maps explicit "+
        "<code>titleFields</code> as necessary.<br/><br/>"+
        "In this example, note that Smart GWT is automatically populating all three of "+
        "the fields in the \"teamMembers\" DataSource, even though none of those fields is "+
        "present in the \"employees\" DataSource being dragged from. "+
        "Change the \"Team for Project\" select box, then try dragging employees across. "+
        "Note that the \"Project Code\" column is being correctly populated for the dropped "+
        "records.";

    public static class Factory implements PanelFactory {
        private String id;
        public ShowcasePanel create() {
            Copy panel = new Copy();
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
        final String[] projects = {"New Costing System", "Warehousing Improvements",
        		"Evaluate AJAX Frameworks", "Upgrade Postgres", "Online Billing"};
        final String defaultProject = projects[0];
        final int formHeight = 30;
        
        final ListGrid employeeList = new ListGrid();
        employeeList.setDataSource(DataSource.get("employees"));
        employeeList.setCanDragRecordsOut(true);
        employeeList.setDragDataAction(DragDataAction.COPY);
        employeeList.setAlternateRecordStyles(true);
        employeeList.setAutoFetchData(true);
        ListGridField empIdFld1 = new ListGridField("EmployeeId"); 
        empIdFld1.setWidth("30%");
        employeeList.setFields(empIdFld1, new ListGridField("Name"));
        
        final ListGrid projectList = new ListGrid();

        final DynamicForm form = new DynamicForm();
        form.setHeight(formHeight);
        SelectItem projectSelector = new SelectItem("projectCode", "Team for Project");
        projectSelector.setWrapTitle(false);
        projectSelector.setValueMap(projects);
        projectSelector.setDefaultValue(defaultProject);
        projectSelector.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                projectList.fetchData(form.getValuesAsCriteria());
                
            }
        });
        form.setFields(projectSelector);

        projectList.setDataSource(DataSource.get("teamMembers"));
        projectList.setCanAcceptDroppedRecords(true);
        projectList.setCanRemoveRecords(true);
        projectList.setAlternateRecordStyles(true);
        projectList.setAutoFetchData(true);
        projectList.setInitialCriteria(form.getValuesAsCriteria());
        projectList.setAutoFetchTextMatchStyle(TextMatchStyle.EXACT);
        projectList.setPreventDuplicates(true);
        ListGridField empIdFld2 = new ListGridField("employeeId");
        empIdFld2.setWidth("25%");
        ListGridField empNameFld = new ListGridField("employeeName"); 
        empNameFld.setWidth("40%");
        ListGridField projectCode = new ListGridField("projectCode");
        projectList.setFields(empIdFld2, empNameFld, projectCode);
        
        LayoutSpacer formSpacer = new LayoutSpacer();
        formSpacer.setHeight(formHeight);
        
        VLayout leftVLayout = new VLayout();
        leftVLayout.addMember(formSpacer);
        leftVLayout.addMember(employeeList);
        
        Img arrowImg = new Img("icons/32/arrow_right.png", 32, 32);
        arrowImg.setLayoutAlign(Alignment.CENTER);
        arrowImg.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		projectList.transferSelectedData(employeeList);
        	}
        });
        
        VLayout rightVLayout = new VLayout();
        rightVLayout.addMember(form);
        rightVLayout.addMember(projectList);
        
        HLayout mainLayout = new HLayout();
        mainLayout.addMember(leftVLayout);
        mainLayout.addMember(arrowImg);
        mainLayout.addMember(rightVLayout);
        
        return mainLayout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}

