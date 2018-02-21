package com.smartgwt.sample.showcase.client.draganddrop;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderDropEvent;
import com.smartgwt.client.widgets.tree.events.FolderDropHandler;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class PersistentReorderableTreeGrid extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>Click on a node to select it.  Or, press and hold the Ctrl key and " +
        "click on several nodes to select those nodes.  Click on one of the selected nodes " +
        "and drag the selection to a new position among their siblings.  Release the mouse once the " +
        "selection is in the desired position to drop the selected nodes." +

        "<p>On the ISC Developer Console, RPC tab, check the \"Track RPCs\" checkbox to be able to " +
        "monitor <code>DSRequests</code>.  Notice that there is only one request sent per drag &amp; drop operation.";

    public static class Factory implements PanelFactory {

        private String id;

        @Override
        public ShowcasePanel create() {
            PersistentReorderableTreeGrid sample = new PersistentReorderableTreeGrid();
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

    protected boolean isTopIntro() {
        return true;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    private static int findIndex(RecordList records, String propertyName, Object value) {
        final int records_length = records.getLength();
        for (int i = 0; i < records_length; ++i) {
            if (value.equals(records.get(i).getAttributeAsObject(propertyName))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Canvas getViewPanel() {
        DataSource employeesDS = DataSource.get("employees");

        final TreeGrid treeGrid = new TreeGrid();
        treeGrid.setWidth(430);
        treeGrid.setHeight(310);
        treeGrid.setDataSource(employeesDS);
        treeGrid.setAutoFetchData(true);
        treeGrid.setAutoSaveEdits(true);
        treeGrid.setCanAcceptDroppedRecords(true);
        treeGrid.setCanDragRecordsOut(true);
        treeGrid.setCanEdit(true);
        treeGrid.setCanReorderRecords(true);
        treeGrid.setCanReparentNodes(true);
        treeGrid.setCanSort(false); // Disable user sorting.
        treeGrid.setNodeIcon("icons/16/person.png");
        treeGrid.setFolderIcon("icons/16/person.png");
        treeGrid.setShowOpenIcons(false);
        treeGrid.setShowDropIcons(true);
        treeGrid.setDropIconSuffix("into");
        treeGrid.setClosedIconSuffix("");
        treeGrid.setDragDataAction(DragDataAction.MOVE);
        treeGrid.setSortField("userOrder");

        TreeGridField eidField = new TreeGridField("EmployeeId", "ID");
        eidField.setWidth("15%");
        TreeGridField employeeNameField = new TreeGridField("Name");
        employeeNameField.setCellFormatter(new CellFormatter() {
            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                return value.toString() + "&nbsp;-&nbsp;" + record.getAttributeAsString("Job");
            }
        });
        employeeNameField.setTreeField(true);
        treeGrid.setFields(eidField, employeeNameField);

        treeGrid.addFolderDropHandler(new FolderDropHandler() {

            @Override
            public void onFolderDrop(FolderDropEvent event) {
                TreeNode[] draggedNodes = event.getNodes();
                TreeNode folder = event.getFolder();
                int targetIndex = event.getIndex();

                if (event.getSource() == treeGrid && draggedNodes.length > 0) {
                    RecordList folderChildren = folder.getAttributeAsRecordList("children");

                    RPCManager.startQueue();

                    int numDraggedNodesBeforeTargetIndex = 0;
                    for (int ri = draggedNodes.length; ri > 0; --ri) {
                        TreeNode draggedNode = draggedNodes[ri - 1];
                        int pos = findIndex(folderChildren, "EmployeeId", draggedNode.getAttributeAsInt("EmployeeId"));
                        if (pos >= 0) {
                            folderChildren.removeAt(pos);
                            if (pos < targetIndex) {
                                numDraggedNodesBeforeTargetIndex++;
                            }
                            folderChildren.addAt(draggedNode, targetIndex - numDraggedNodesBeforeTargetIndex);
                        }
                    }
                    
                    
                    // Assign the correct index to any nodes that were not already children of the target folder
                    int numDraggedNodesNotInTargetFolder = 0;
                    for (int ri = 0; ri < draggedNodes.length; ri++) {
                        TreeNode draggedNode = draggedNodes[ri];
                        int pos = folderChildren.findIndex("EmployeeId", draggedNode.getAttributeAsInt("EmployeeId"));
                        if (pos == -1) {
                            draggedNode.setAttribute("userOrder", (targetIndex + ri) - numDraggedNodesBeforeTargetIndex);
                            numDraggedNodesNotInTargetFolder++;
                        }
                    }

                    // Update the userOrder field of all children of `folder`.
                    final int folderChildren_length = folderChildren.getLength();
                    for (int i = 0; i < folderChildren_length; ++i) {
                        Record node = folderChildren.get(i);

                        DSRequest request = new DSRequest(DSOperationType.UPDATE, treeGrid.getUpdateOperation());
                        // `oldValues` is optional, but supplying it with the request allows the server
                        // to detect concurrent edits.
                        Record oldValues = new Record();
                        request.setOldValues(oldValues);

                        Record updates = new Record();
                        updates.setAttribute("EmployeeId", node.getAttributeAsInt("EmployeeId"));
                        updates.setAttribute("ReportsTo", folder.getAttributeAsInt("EmployeeId"));
                        updates.setAttribute("userOrder", i < targetIndex ? i : i + numDraggedNodesNotInTargetFolder);
                        oldValues.setAttribute("ReportsTo", node.getAttributeAsInt("ReportsTo"));
                        oldValues.setAttribute("userOrder", node.getAttributeAsInt("userOrder"));
                        node.setAttribute("ReportsTo", folder.getAttributeAsInt("EmployeeId"));
                        node.setAttribute("userOrder", i < targetIndex ? i : i + numDraggedNodesNotInTargetFolder);
                        treeGrid.updateData(updates, null, request);
                    }

                    RPCManager.sendQueue();
                }
            }
        });

        return treeGrid;
    }
}
