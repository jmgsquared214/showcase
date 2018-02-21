package com.smartgwt.sample.showcase.client.cube.advanced;

import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.events.DragStartEvent;
import com.smartgwt.client.widgets.events.DragStartHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;

/**
 * A convenience ListGrid subclass to display the facet list.
 */
public class FacetList extends ListGrid {

    private boolean isRowFacetGrid;

    public FacetList(CubeGrid grid, boolean isRowFacetGrid) {
        this.grid = grid;
        this.isRowFacetGrid = isRowFacetGrid;
        setCanSort(false);
        setShowSortArrow(SortArrow.NONE);
        setLeaveScrollbarGap(false);
        setSelectionType(SelectionStyle.SINGLE);

        addCellContextClickHandler(new CellContextClickHandler() {

            @Override
            public void onCellContextClick(CellContextClickEvent event) {
                String facetId = event.getRecord().getAttribute("id");
                // the appropriate menus are named "RegionsMenu", etc.
                FacetMenu facetMenu = (FacetMenu) Canvas.getById(facetId + "Menu");
                if (facetMenu != null) facetMenu.showContextMenu();
                event.cancel();
            }
        });

        // We're going to override default ListGrid drag and drop behavior to support
        // - dragging the facet menu-buttons into the ListGrid
        // - dragging the records directly from the ListGrid to the CubeGrid header bars
        // - updating the CubeGrid header bars whenever drag and drop of records occurs
        setCanReorderRecords(true);
        setCanDragRecordsOut(true);
        setCanAcceptDroppedRecords(true);

        setDragType("facet");

        // we do entirely custom drag/drop
        setDragDataAction(DragDataAction.NONE);

        addRecordDropHandler(new RecordDropHandler() {

            @Override
            public void onRecordDrop(RecordDropEvent event) {
                Canvas source = event.getSourceWidget();
                if (source instanceof ListGrid && ((ListGrid) source).getTotalRows() == 1) {
                    SC.warn("Unable to shift all row facets onto columns - cubeGrid requires at least "
                            + "one facet on each axis.");
                    event.cancel();
                    return;
                }
                String facetId = JSOHelper.getAttribute(source.getJsObj(), "facetId");
                if (getIsRowFacetGrid()) {
                    getCubeGrid().addRowFacet(facetId, event.getIndex());
                } else {
                    getCubeGrid().addColumnFacet(facetId, event.getIndex());
                }
                // suppress standard drop behavior
                event.cancel();
            }
        });

        // on drag start, set up the facetId directly on this component
        // we'll read it back off on drop
        // This also allows the cubeGrid to know which facet is being dragged and react
        // appropriately
        addDragStartHandler(new DragStartHandler() {

            @Override
            public void onDragStart(DragStartEvent event) {
                JSOHelper.setAttribute(getJsObj(), "facetId", getSelectedRecord().getAttribute("id"));
            }
        });
    }

    // Allow anything with dragType facet to drop onto us
    @Override
    public Boolean willAcceptDrop() {
        Canvas dragTarget = EventHandler.getDragTarget();
        if (dragTarget != null && "facet".equals(dragTarget.getDragType())) {
            return true;
        }
        return false;
    }

    public boolean getIsRowFacetGrid() {
        return isRowFacetGrid;
    }

    private CubeGrid grid;

    public CubeGrid getCubeGrid() {
        return grid;
    }
}