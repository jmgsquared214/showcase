/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

package com.smartgwt.sample.showcase.client;

import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.data.CommandTreeNode;
import com.smartgwt.sample.showcase.client.data.ExplorerTreeNode;
import com.smartgwt.sample.showcase.client.data.ShowcaseData;


public class ShowcaseGrid extends TreeGrid {

    public ShowcaseGrid() {
        setWidth100();
        setHeight100();
        setCustomIconProperty("icon");
        setAnimateFolderTime(100);
        setAnimateFolders(true);
        setAnimateFolderSpeed(1000);
        setNodeIcon("silk/application_view_list.png");
        setShowSortArrow(SortArrow.CORNER);
        setShowAllRecords(true);
        setLoadDataOnDemand(false);
        setCellHeight(40);
        setAlternateRecordStyles(true);
        setWrapCells(true);
        setAnimateFolders(false);
        setFixedRecordHeights(false);

        TreeGridField nameField = new TreeGridField();
        nameField.setName("name");
        nameField.setTitle("<b>Example </b>");
        nameField.setWidth(200);
        //nameField.setCanFilter(true);

        TreeGridField descriptionField = new TreeGridField();
        descriptionField.setName("description");
        descriptionField.setCanSort(false);
        //descriptionField.setCanFilter(false);
        descriptionField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (record instanceof CommandTreeNode) {
                    return ((CommandTreeNode) record).getDescription();
                } else if (record instanceof ExplorerTreeNode) {
                    ExplorerTreeNode treeNode = (ExplorerTreeNode) record;
                    PanelFactory factory = treeNode.getFactory();
                    if (factory != null) {
                        return factory.getDescription();
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        });
        descriptionField.setTitle("<b>Description</b>");

        setFields(nameField, descriptionField);

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setNameProperty("name");
        tree.setOpenProperty("isOpen");
        tree.setIdField("nodeID");
        tree.setParentIdField("parentNodeID");

        String idSuffix = "_gridview";
        tree.setRootValue("root" + idSuffix);
        TreeNode[] showcaseData = ShowcaseData.getData(idSuffix);
        tree.setData(showcaseData);

        setData(tree);
    }

    protected void onInit() {
        //getData().openAll();
    }
}
