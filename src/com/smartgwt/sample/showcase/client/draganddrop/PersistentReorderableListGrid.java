package com.smartgwt.sample.showcase.client.draganddrop;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class PersistentReorderableListGrid extends ShowcasePanel {

    private static final String DESCRIPTION =
            "<p>Click on a record to select it, or press and hold the Ctrl key and " +
            "click on several records to select those records.  Click on one of the selected records " +
            "and drag the selection to a new position within the ListGrid.  Release the mouse once the " +
            "selection is in the desired position to drop the selected records." +

            "<p>On the ISC Developer Console, RPC tab, check the \"Track RPCs\" checkbox to be able to " +
            "monitor <code>DSRequests</code>.  Notice that there is only one request sent per drag &amp; drop operation.";

    public static class Factory implements PanelFactory {

        private String id;

        @Override
        public ShowcasePanel create() {
            PersistentReorderableListGrid sample = new PersistentReorderableListGrid();
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

    private static class Pair<T1, T2> {
        public static <T1, T2> Pair<T1, T2> makePair(T1 first, T2 second) {
            return new Pair<T1, T2>(first, second);
        }

        public final T1 first;
        public final T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }

    private DataSource employeesDS;
    private ListGrid listGrid;

    protected boolean isTopIntro() {
        return true;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public Canvas getViewPanel() {
        employeesDS = DataSource.get("employees");

        listGrid = new ListGrid();
        listGrid.setWidth(900);
        listGrid.setHeight(310);
        listGrid.setDataSource(employeesDS);
        listGrid.setAutoFetchData(true);
        listGrid.setAutoSaveEdits(true);
        listGrid.setCanEdit(true);
        listGrid.setCanGroupBy(false);
        listGrid.setCanReorderRecords(true);
        listGrid.setCanSort(false); // Disable user sorting because we rely on records being
                                    // sorted by userOrder.
        listGrid.setSortField("userOrder");

        listGrid.addRecordDropHandler(new RecordDropHandler() {

            @Override
            public void onRecordDrop(RecordDropEvent event) {
                ListGridRecord[] draggedRecords = event.getDropRecords();
                int targetIndex = event.getIndex();
                if (event.getSourceWidget() == listGrid && draggedRecords.length != 0) {
                    Arrays.sort(draggedRecords, new Comparator<ListGridRecord>() {
                        @Override
                        public int compare(ListGridRecord lhs, ListGridRecord rhs) {
                            return lhs.getAttributeAsInt("userOrder") -
                                   rhs.getAttributeAsInt("userOrder");
                        }
                    });

                    RecordList data = listGrid.getDataAsRecordList();

                    // Compute the range of records that need to be updated as all records at `minIndex`
                    // through `maxIndex`, inclusive.
                    Integer[] draggedIndicesArr = new Integer[draggedRecords.length];
                    int minIndex = targetIndex;
                    int maxIndex = targetIndex - 1;
                    for (int i = 0; i < draggedIndicesArr.length; ++i) {
                        draggedIndicesArr[i] = data.findIndex("EmployeeId", draggedRecords[i].getAttributeAsInt("EmployeeId"));
                        minIndex = Math.min(draggedIndicesArr[i], minIndex);
                        maxIndex = Math.max(draggedIndicesArr[i], maxIndex);
                    }
                    List<Integer> draggedIndices = Arrays.asList(draggedIndicesArr);

                    // Start the queue.
                    RPCManager.startQueue();

                    // We need to update the userOrders of all records in [`minIndex`, `maxIndex`].
                    // To do this, we utilize a deque.

                    // First consider the records in [`minIndex`, `targetIndex`).  For each record in
                    // this range, we push the userOrder of the record to the back of the deque and if
                    // the record was *not* a dragged record, then we update its userOrder to the value
                    // popped from the front of the deque. The deque's size at the end of that loop is
                    // exactly the number of dragged records that were before the target record,
                    // `numDraggedRecordsBeforeTargetIndex`.  Furthermore, the deque contains the
                    // userOrder values that need to be assigned to the first
                    // `numDraggedRecordsBeforeTargetIndex` dragged records, so we empty the deque and
                    // update the userOrder values of those dragged records accordingly.

                    // This same algorithm can be used in reverse to handle the records at and after
                    // the target record, but we instead simplify the code with an observation:
                    // instead of thinking about the dragged records at and after the target record as
                    // the dragged records, treat the *non-dragged* records at and after the target
                    // record as the dragged records being dragged to `maxIndex`.  Then we do not need
                    // to use the reverse algorithm to process the affected records at and after the
                    // target record.

                    final LinkedList<Integer> deque = new LinkedList<Integer>();

                    // The purpose of `prevUserOrder` is to give each record a unique userOrder even
                    // if the assumption that records have unique userOrders does not hold.
                    // Other Showcase samples utilize the employees.userOrder field and may break
                    // this invariant.
                    int prevUserOrder = -1;
                    if (minIndex > 0) {
                        Record record = data.get(minIndex - 1);
                        prevUserOrder = record.getAttributeAsInt("userOrder");
                    }

                    Pair<Integer, Integer> res = updateUserOrders(deque, prevUserOrder, minIndex, targetIndex, draggedIndices);
                    int numDraggedRecordsBeforeTargetIndex = res.first;
                    prevUserOrder = res.second;

                    if (draggedIndicesArr.length - numDraggedRecordsBeforeTargetIndex > 0) {
                        Integer[] nonDraggedIndicesArr = new Integer[maxIndex + 1 - targetIndex - (draggedIndicesArr.length - numDraggedRecordsBeforeTargetIndex)];
                        int j = 0;
                        for (int i = targetIndex; i <= maxIndex; ++i) {
                            if (!draggedIndices.contains(i)) {
                                nonDraggedIndicesArr[j++] = i;
                            }
                        }
                        assert j == nonDraggedIndicesArr.length;

                        res = updateUserOrders(deque, prevUserOrder, targetIndex, maxIndex + 1, Arrays.asList(nonDraggedIndicesArr));
                        prevUserOrder = res.second;
                    }

                    RPCManager.sendQueue();
                }
            }
        });

        return listGrid;
    }

    private Pair<Integer, Integer> updateUserOrders(LinkedList<Integer> deque, int prevUserOrder, int minIndex, int targetIndex, List<Integer> draggedIndices) {
        RecordList data = listGrid.getDataAsRecordList();

        DSRequest request = new DSRequest(DSOperationType.UPDATE, listGrid.getUpdateOperation());
        // `oldValues` is optional, but supplying it with the request allows the server
        // to detect concurrent edits.
        Record oldValues = new Record();
        request.setOldValues(oldValues);

        for (int i = minIndex; i < targetIndex; ++i) {
            Record record = data.get(i);
            deque.addLast(record.getAttributeAsInt("userOrder"));
            if (!draggedIndices.contains(i)) {
                Record updates = new Record();
                updates.setAttribute("EmployeeId", record.getAttributeAsInt("EmployeeId"));
                oldValues.setAttribute("userOrder", record.getAttributeAsInt("userOrder"));

                int newUserOrder = Math.max(deque.removeFirst(), prevUserOrder + 1);
                updates.setAttribute("userOrder", newUserOrder);
                record.setAttribute("userOrder", newUserOrder); // Update the record's userOrder to
                                                                // what it will be set to.
                prevUserOrder = newUserOrder;
                employeesDS.updateData(updates, null, request);
            }
        }

        // Empty the deque.
        int numDraggedRecordsBeforeTargetIndex = deque.size();

        for (int j = 0; !deque.isEmpty(); ++j) {
            Record record = data.get(draggedIndices.get(j));

            Record updates = new Record();
            updates.setAttribute("EmployeeId", record.getAttributeAsInt("EmployeeId"));
            oldValues.setAttribute("userOrder", record.getAttributeAsInt("userOrder"));

            int newUserOrder = Math.max(deque.removeFirst(), prevUserOrder + 1);
            updates.setAttribute("userOrder", newUserOrder);
            record.setAttribute("userOrder", newUserOrder); // Update the record's userOrder to
                                                            // what it will be set to.
            prevUserOrder = newUserOrder;
            employeesDS.updateData(updates, null, request);
        }

        return Pair.makePair(numDraggedRecordsBeforeTargetIndex, prevUserOrder);
    }
}
