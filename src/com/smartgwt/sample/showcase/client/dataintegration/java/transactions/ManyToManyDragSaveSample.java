package com.smartgwt.sample.showcase.client.dataintegration.java.transactions;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.ListGridRemoveField;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.grid.events.RemoveRecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RemoveRecordClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class ManyToManyDragSaveSample extends ShowcasePanel {

    private static final String DESCRIPTION = "<p>Select a team.  The ListGrid on the left will populate with employees not currently " +
            "in the selected team while the ListGrid on the right will populate with the employees who " +
            "are currently part of the selected team.  Drag employees from the left to the right " +
            "to add the selected employees to the team." +

            "<p>This sample uses a traditional pivot table design for the many-to-many relationship " +
            "(many employees can be part of a team and an employee can be part of many teams)." +

            "<p>Multi-record selection is enabled on the \"Employees\" grid, so multiple " +
            "employees can be selected and dropped into a team all at once.  Because the two grids are databound, " +
            "Smart GWT Server will automatically utilize queuing to ensure that all updates are performed on " +
            "the server in a single transaction.";

    public static class Factory implements PanelFactory {

        private String id;

        @Override
        public ShowcasePanel create() {
            ManyToManyDragSaveSample sample = new ManyToManyDragSaveSample();
            id = sample.getID();
            return sample;
        }

        @Override
        public String getID() {
            return id;
        }

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }
    }

    private DataSource employeesDS;
    private DataSource teamsDS;
    private DataSource teamMembers2DS;

    protected boolean isTopIntro() {
        return true;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    // To remove entries of `employeesGrid` without deleting the corresponding rows from
    // the database, we can mock a "remove" DSResponse and post it to `employeesDS` via
    // DataSource#updateCaches().  All grids that are bound to the data source respond to
    // these events as appropriate.  In this case, posting a "remove" DSResponse will cause
    // `employeesGrid` to remove the records from its list.
    private void mockRemoveEmployees(ListGridRecord... employeeRecords) {
        if (employeeRecords.length == 0) {
            return;
        }

        DSResponse mockRemoveResponse = new DSResponse("employeesByTeam", DSOperationType.REMOVE, employeeRecords);
        employeesDS.updateCaches(mockRemoveResponse);
    }

    private void mockAddEmployeesFromTeamMemberRecords(ListGridRecord... teamMemberRecords) {
        if (teamMemberRecords.length == 0) {
            return;
        }

        ListGridRecord[] mockEmployeeRecords = new ListGridRecord[teamMemberRecords.length];
        for (int i = 0; i < teamMemberRecords.length; ++i) {
            ListGridRecord teamMemberRecord = teamMemberRecords[i];

            ListGridRecord mockEmployeeRecord = new ListGridRecord();
            Record.copyAttributesInto(mockEmployeeRecord, teamMemberRecord, "EmployeeId");
            mockEmployeeRecord.setAttribute("Name", teamMemberRecord.getAttributeAsString("EmployeeName"));
            mockEmployeeRecord.setAttribute("Job", teamMemberRecord.getAttributeAsString("EmployeeJob"));

            mockEmployeeRecords[i] = mockEmployeeRecord;
        }

        DSResponse mockAddResponse = new DSResponse("employeesByTeam", DSOperationType.ADD, mockEmployeeRecords);
        employeesDS.updateCaches(mockAddResponse);
    }

    @Override
    public Canvas getViewPanel() {
        employeesDS = DataSource.get("employeesByTeam");
        teamsDS = DataSource.get("teams");
        teamMembers2DS = DataSource.get("teamMembers2");

        final DynamicForm teamSelectionForm = new DynamicForm();
        teamSelectionForm.setWidth(300);
        teamSelectionForm.setHeight(30);

        final SelectItem teamSelectItem = new SelectItem("TeamId", "Team");
        teamSelectItem.setOptionDataSource(teamsDS);
        teamSelectItem.setValueField("TeamId");
        teamSelectItem.setDisplayField("TeamName");

        final ListGrid employeesGrid = new ListGrid(employeesDS);
        employeesGrid.setWidth(300);
        employeesGrid.setHeight(224);
        employeesGrid.setCanDragRecordsOut(true);
        employeesGrid.setDragDataAction(DragDataAction.NONE);
        employeesGrid.setDragType("nonTeamMemberEmployee");
        employeesGrid.setAutoFetchData(false);
        employeesGrid.setSortField("EmployeeId");

        ListGridField employeeIdField = new ListGridField("EmployeeId", "ID");
        employeeIdField.setWidth(50);
        ListGridField employeeNameField = new ListGridField("Name", "Employee Name");
        employeesGrid.setFields(employeeIdField, employeeNameField);

        final ListGrid teamMembersGrid = new ListGrid(teamMembers2DS);
        teamMembersGrid.setWidth(350);
        teamMembersGrid.setHeight(264);
        teamMembersGrid.setCanAcceptDroppedRecords(true);
        teamMembersGrid.setDropTypes("nonTeamMemberEmployee");
        teamMembersGrid.setCanRemoveRecords(true);
        teamMembersGrid.setAutoFetchData(false);
        teamMembersGrid.setSortField("EmployeeId");

        teamMembersGrid.addRecordDropHandler(new RecordDropHandler() {

            @Override
            public void onRecordDrop(RecordDropEvent event) {
                ListGridRecord[] draggedRecords = event.getDropRecords();
                mockRemoveEmployees(draggedRecords);
            }
        });

        teamMembersGrid.addRemoveRecordClickHandler(new RemoveRecordClickHandler() {
                @Override
                public void onRemoveRecordClick(RemoveRecordClickEvent event) {
                    final ListGridRecord record = teamMembersGrid.getRecord(event.getRowNum());

                    teamMembersGrid.removeData(record, new DSCallback() {

                        @Override
                        public void execute(DSResponse response, Object rawData, DSRequest request) {
                            // Update `employeesGrid` now that an employee has been removed from
                            // the selected team.  This will add the employee back to `employeesGrid`,
                            // the list of employees who are not in the team.
                            mockAddEmployeesFromTeamMemberRecords(record);
                        }
                    });
                }
            });

        ListGridField employeeIdField2 = new ListGridField("EmployeeId", "EID");
        employeeIdField2.setWidth("25%");
        ListGridField employeeNameField2 = new ListGridField("EmployeeName", "Employee Name");
        employeeNameField2.setWidth("45%");
        ListGridField teamNameField = new ListGridField("TeamName", "Team Name");
        teamMembersGrid.setFields(employeeIdField2, employeeNameField2, teamNameField);

        HStack hStack = new HStack(10);
        hStack.setHeight(160);

        VStack vStack = new VStack();
        LayoutSpacer spacer = new LayoutSpacer();
        spacer.setHeight(30);
        vStack.addMember(spacer);
        vStack.addMember(employeesGrid);
        hStack.addMember(vStack);

        Img arrowImg = new Img("icons/32/arrow_right.png", 32, 32);
        arrowImg.setLayoutAlign(Alignment.CENTER);
        arrowImg.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                ListGridRecord[] selectedEmployeeRecords = employeesGrid.getSelectedRecords();
                teamMembersGrid.transferSelectedData(employeesGrid);
                mockRemoveEmployees(selectedEmployeeRecords);
            }
        });
        hStack.addMember(arrowImg);

        VStack vStack2 = new VStack();

        teamSelectItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                splitEmployeesByTeam(teamSelectionForm, employeesGrid, teamMembersGrid);
            }
        });
        teamSelectItem.addDataArrivedHandler(new DataArrivedHandler() {
                @Override
                public void onDataArrived(DataArrivedEvent event) {
                    if (teamSelectItem.getValue() == null &&
                        event.getStartRow() == 0 &&
                        event.getEndRow() > 0)
                    {
                        Record record = event.getData().get(0);
                        Object value;
                        if (record == null || record == ResultSet.getLoadingMarker()) {
                            value = null;
                        } else {
                            String valueFieldName = teamSelectItem.getValueFieldName();
                            value = record.getAttributeAsObject(valueFieldName);
                        }
                        teamSelectItem.setValue(value);
                        splitEmployeesByTeam(teamSelectionForm, employeesGrid, teamMembersGrid);
                    }
                }
            });
        teamSelectionForm.setFields(teamSelectItem);

        vStack2.addMember(teamSelectionForm);
        vStack2.addMember(teamMembersGrid);

        hStack.addMember(vStack2);

        return hStack;
    }

    private void splitEmployeesByTeam(
        DynamicForm teamSelectionForm, ListGrid employeesGrid, ListGrid teamMembersGrid)
    {
        Criteria criteria = teamSelectionForm.getValuesAsCriteria();

        teamMembersGrid.fetchData(criteria);

        DSRequest request = new DSRequest(DSOperationType.FETCH, "fetchEmployeesNotInTeam");
        employeesGrid.fetchData(criteria, null, request);
    }
}
