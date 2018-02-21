package com.smartgwt.sample.showcase.client.cube.advanced;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * A convenience Menu sublcass to display the facet menu.
 */
public class FacetMenu extends Menu {
    private CubeGrid cubeGrid;
    private String facetId;

    public FacetMenu(CubeGrid grid, String facet) {

        this.cubeGrid = grid;
        this.facetId = facet;

        // Assertion: we create 1 facetMenu per facet.
        // Make this available via the ID facetId + "Menu"
        this.setID(facet + "Menu");

        MenuItem addToRowFacets = new MenuItem("Add To Row Facets");
        addToRowFacets.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                addToRowFacets(facetId);
            }
        });

        addToRowFacets.setEnableIfCondition(new MenuItemIfFunction() {
            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return !containsFacet(cubeGrid.getRowFacets(), facetId);
            }
        });

        MenuItem addToColFacets = new MenuItem("Add To Column Facets");
        addToColFacets.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MenuItemClickEvent event) {
                addToColFacets(facetId);
            }
        });
        addToColFacets.setEnableIfCondition(new MenuItemIfFunction() {
            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return !containsFacet(cubeGrid.getColumnFacets(), facetId);
            }
        });

        // Hierarchical Menu to fix facet values on the current facet
        MenuItem fixFacetValue = new MenuItem("Fix " + facetId + " value");

        Menu fixFacetValueMenu = new Menu();

        // Just fix the top level values for now.
        FacetValue[] facetValues = cubeGrid.getFacet(facetId).getValues();

        Tree facetValueTree = new Tree();
        facetValueTree.setModelType(TreeModelType.PARENT);
        TreeNode[] nodes = new TreeNode[facetValues.length];
        for (int i = 0; i < facetValues.length; i++) {
            TreeNode node = new TreeNode();
            node.setParentID(facetValues[i].getParentId());
            node.setID(facetValues[i].getIdAsString());
            node.setTitle(facetValues[i].getTitle());
            nodes[i] = node;
        }
        facetValueTree.setData(nodes);
        fixFacetValueMenu.setData(facetValueTree);
        fixFacetValueMenu.setCanSelectParentItems(true);

        fixFacetValueMenu.addItemClickHandler(new ItemClickHandler() {
            @Override
            public void onItemClick(ItemClickEvent event) {
                TreeNode node = (TreeNode) event.getRecord();
                fixFacetValue(node.getAttribute("id"));
            }
        });
        fixFacetValue.setSubmenu(fixFacetValueMenu);

        setItems(addToRowFacets, addToColFacets, fixFacetValue);
    }

    private void addToRowFacets(String facetId) {
        String[] colFacets = cubeGrid.getColumnFacets();
        if (colFacets.length == 1 && containsFacet(colFacets, facetId)) {
            SC.warn("Unable to add " + facetId + " to row facets.<br>" +
                    "This would remove the only column-facet for this cubeGrid.");
            return;
        }
        cubeGrid.addRowFacet(facetId);
    }

    private void addToColFacets(String facetId) {
        String[] rowFacets = cubeGrid.getRowFacets();
        if (rowFacets.length == 1 && containsFacet(rowFacets, facetId)) {
            SC.warn("Unable to add " + facetId + " to column facets.<br>" +
                    "This would remove the only row-facet for this cubeGrid.");
            return;
        }
        cubeGrid.addColumnFacet(facetId);
    }

    private void fixFacetValue(String facetValueId) {

        String[] columnFacets = cubeGrid.getColumnFacets();
        String[] rowFacets = cubeGrid.getRowFacets();

        boolean onlyRowFacet = (rowFacets.length == 1 && containsFacet(rowFacets, facetId));
        boolean onlyColFacet = (columnFacets.length == 1 && containsFacet(columnFacets, facetId));
        if (onlyRowFacet || onlyColFacet) {
            SC.warn("Unable to fix value for " + facetId + ".<br>" +
                    "This would remove the only " + (onlyRowFacet ? "row" : "column") +
                    "-facet for this cubeGrid.");
            return;
        }
        cubeGrid.setFixedFacetValue(facetId, facetValueId);
    }

    private boolean containsFacet(String[] facetIds, String facet) {
        for (int i = 0; i < facetIds.length; i++) {
            if (facetIds[i].equals(facet)) return true;
        }
        return false;
    }
}