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

package com.smartgwt.sample.showcase.client.dataintegration.java.tree;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;


public class TreeInitialDataSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Begin opening folders and note the load on "+
    "demand behavior. Trees that use load on demand can optionally specify an initial dataset "+
    "set up front.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            TreeInitialDataSample panel = new TreeInitialDataSample();
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

    public static class EmployeeTreeNode extends TreeNode {
        public EmployeeTreeNode(int employeeId, int reportsTo, String name, String job,
                                boolean isOpen) {  
            setAttribute("EmployeeId", employeeId);
            setAttribute("ReportsTo", reportsTo);
            setAttribute("Name", name);
            setAttribute("Job", job);
            setAttribute("isOpen",isOpen);
        }
        public EmployeeTreeNode(int employeeId, int reportsTo, String name, String job) {
            this(employeeId, reportsTo, name, job, false);
        }
    }
    
    public Canvas getViewPanel() {
        final TreeGrid treeGrid = new TreeGrid();
        treeGrid.setWidth(500);
        treeGrid.setHeight(400);
        treeGrid.setDataSource(DataSource.get("employees"));
        treeGrid.setShowOpenIcons(false);
        treeGrid.setShowDropIcons(false);
        treeGrid.setClosedIconSuffix("");
        treeGrid.setNodeIcon("silk/user_orange.png");
        treeGrid.setFolderIcon("silk/user_orange.png");
        treeGrid.setDataProperties(new Tree() {{ setOpenProperty("isOpen"); }});
        TreeNode[] initialData = {
            new EmployeeTreeNode(4,1,"Charles Madigen","Chief Operating Officer",true),
            new EmployeeTreeNode(192,4,"Ralph Brogan","Mgr Software Client Supp"),
            new EmployeeTreeNode(191,4,"Tammy Plant","Mgr Cap Rptg Dist"),
            new EmployeeTreeNode(190,4,"Carol Finley","Mgr Fin Rpts Budgets"),
            new EmployeeTreeNode(189,4,"Gene Porter","Mgr Tech Plng IntIS T"),
            new EmployeeTreeNode(188,4,"Rogine Leger","Mgr Syst P P"), 
            new EmployeeTreeNode(187,4,"Abigail Lippman","Mgr Proj Del"), 
            new EmployeeTreeNode(186,4,"John Garrison","Mgr Site Services"), 
            new EmployeeTreeNode(185,4,"Rui Shu","Mgr Proj Del"), 
            new EmployeeTreeNode(184,4,"Kirill Amirov","Mgr Tech Plng IntIS T"), 
            new EmployeeTreeNode(183,4,"Joan Little","Mgr Ther Gen"), 
            new EmployeeTreeNode(183,4,"Joan Little","Mgr Ther Gen"), 
            new EmployeeTreeNode(182,4,"Tamara Kane","Mgr Site Services")
        };
        treeGrid.setInitialData(initialData);
        treeGrid.setFields(new TreeGridField("Name") {{ setTreeField(true); }},
                           new TreeGridField("Job"));
        return treeGrid;
    }

    public String getIntro() {
        return DESCRIPTION;
    }    
}
