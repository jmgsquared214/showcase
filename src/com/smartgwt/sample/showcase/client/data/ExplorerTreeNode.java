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

package com.smartgwt.sample.showcase.client.data;

import java.util.List;
import java.util.ArrayList;

import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcaseConfiguration;

public class ExplorerTreeNode extends TreeNode {

    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon,
                            PanelFactory factory, boolean enabled, String idSuffix)
    {
        this(name, nodeID, parentNodeID, icon, factory, enabled, true, idSuffix);
    }

    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon,
                            PanelFactory factory, boolean enabled, String idSuffix, String version)
    {
        this(name, nodeID, parentNodeID, icon, factory, enabled, true, idSuffix, false, version);
    }
    
    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon,
               PanelFactory factory, boolean enabled, boolean testEnabled, String idSuffix)
    {
        this(name, nodeID, parentNodeID, icon, factory, enabled, testEnabled, idSuffix,
             !"new-category".equals(nodeID), null);
    }
    
    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon,
                            PanelFactory factory, boolean enabled, boolean testEnabled,
                            String idSuffix, boolean isOpen)
    {
        this(name, nodeID, parentNodeID, icon, factory, enabled, testEnabled, idSuffix, isOpen, null);
    }
                            
    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon,
                            PanelFactory factory, boolean enabled, boolean testEnabled,
                            String idSuffix, boolean isOpen, String version)
    {
        this(name, nodeID, parentNodeID, icon, null, factory, enabled, testEnabled, idSuffix, isOpen, version);
    	
    }
    
    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, String selectedIcon,
            PanelFactory factory, boolean enabled, String idSuffix)
	{
    	this(name, nodeID, parentNodeID, icon, selectedIcon, factory, enabled, true, idSuffix);
	}
	
    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, String selectedIcon,
            boolean showSelectedIcon, PanelFactory factory, boolean enabled, String idSuffix)
	{
    	this(name, nodeID, parentNodeID, icon, selectedIcon, factory, enabled, true, idSuffix, false, null, showSelectedIcon);
	}
    
	public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, String selectedIcon,
	            PanelFactory factory, boolean enabled, String idSuffix, String version)
	{
		this(name, nodeID, parentNodeID, icon, selectedIcon, factory, enabled, true, idSuffix, false, version);
	}

	public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, String selectedIcon,
				PanelFactory factory, boolean enabled, boolean testEnabled, String idSuffix)
	{
		this(name, nodeID, parentNodeID, icon, selectedIcon, factory, enabled, testEnabled, idSuffix,
		!"new-category".equals(nodeID), null);
	}

	public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, String selectedIcon,
	            PanelFactory factory, boolean enabled, boolean testEnabled,
	            String idSuffix, boolean isOpen)
	{
		this(name, nodeID, parentNodeID, icon, selectedIcon, factory, enabled, testEnabled, idSuffix, isOpen, null);
	}
            
	public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, String selectedIcon,
                PanelFactory factory, boolean enabled, boolean testEnabled,
                String idSuffix, boolean isOpen, String version)
    {
        this(name, nodeID, parentNodeID, icon, selectedIcon, factory, enabled, testEnabled, idSuffix, isOpen, version, false);
    }

    public ExplorerTreeNode(String name, String nodeID, String parentNodeID, String icon, String selectedIcon,
            PanelFactory factory, boolean enabled, boolean testEnabled,
            String idSuffix, boolean isOpen, String version, boolean showSelectedIcon)
    {
        setName(name.replaceAll("<.*?>", ""));
        setHTML(enabled ? name : "<span style='color:808080'>" + name + "</span>");
        setNodeID      (      nodeID.replace("-", "_") + idSuffix);
        setParentNodeID(parentNodeID.replace("-", "_") + idSuffix);
        setThumbnail("thumbnails/" + nodeID.replace("-", "_") + ".gif");
        setIcon(icon);
        setSelectedIcon(selectedIcon);
        setVersion(version);
        setFactory(factory);
        setShowSelectedIcon(showSelectedIcon);

        if (!testEnabled) setTestDisabled();

        if (ShowcaseConfiguration.getSingleton().isOpenForTesting() && null != factory) {
            String className = factory.getClass().getName().replaceFirst("\\$.*$","");
            setSampleClassName(className);
        }
        
        setIsOpen(isOpen);
    }

    // create a copy of the provided node with "_new" added to the ID
    public ExplorerTreeNode(ExplorerTreeNode originalNode, String idSuffix) {
        setNodeID         (originalNode.getNodeID("_new" + idSuffix, idSuffix));
        setName           (originalNode.getName());
        setHTML           (originalNode.getHTML());
        setThumbnail      (originalNode.getThumbnail());
        setIcon           (originalNode.getIcon());
        setSelectedIcon   (originalNode.getSelectedIcon());
        setVersion        (originalNode.getVersion());
        setFactory        (originalNode.getFactory());
        setSampleClassName(originalNode.getSampleClassName());
        setIsOpen         (originalNode.getIsOpen());
        if (originalNode.getTestDisabled()) setTestDisabled();
    }

    public void setShowSelectedIcon(boolean showSelectedIcon) {
        setAttribute("showSelectedIcon",showSelectedIcon);
    }
    public Boolean getShowSelectedIcon() {
        return getAttributeAsBoolean("showSelectedIcon");
    }
    public void setSampleClassName(String name) {
        setAttribute("sampleClassName",name);
    }
    public String getSampleClassName() {
        return getAttribute("sampleClassName");
    }

    public void setVersion(String version) {
        setAttribute("version",version);
    }
    public String getVersion() {
        return getAttribute("version");
    }
    
    public void setFactory(PanelFactory factory) {
        setAttribute("factory", factory);
    }

    public PanelFactory getFactory() {
        return (PanelFactory) getAttributeAsObject("factory");
    }

    public void setNodeID(String value) {
        setAttribute("nodeID", value);
    }

    public String getNodeID() {
        return getAttribute("nodeID");
    }

    public String getNodeID(String newSuffix, String oldSuffix) {
        String nodeID = getAttribute("nodeID");
        return nodeID.substring(0, nodeID.length() - oldSuffix.length()) + newSuffix;
    }

    public void setParentNodeID(String value) {
        setAttribute("parentNodeID", value);
    }
    public String getParentNodeID() {
        return getAttribute("parentNodeID");
    }

    public void setName(String name) {
        setAttribute("nodeTitle", name);
    }

    public String getName() {
        return getAttributeAsString("nodeTitle");
    }

    public void setHTML(String html) {
        setAttribute("nodeHTML", html);
    }

    public String getHTML() {
        return getAttributeAsString("nodeHTML");
    }

    public void setIcon(String icon) {
        setAttribute("icon", icon);
    }

    public String getIcon() {
        return getAttributeAsString("icon");
    }

    public void setSelectedIcon(String icon) {
        setAttribute("selectedIcon", icon);
    }

    public String getSelectedIcon() {
        return getAttributeAsString("selectedIcon");
    }

    public void setThumbnail(String thumbnail) {
        setAttribute("thumbnail", thumbnail);
    }

    public String getThumbnail() {
        return getAttributeAsString("thumbnail");
    }

    public void setIsOpen(boolean isOpen) {
        setAttribute("isOpen", isOpen);
    }

    public Boolean getIsOpen() {
        return getAttributeAsBoolean("isOpen");
    }

    public void setIconSrc(String iconSrc) {
        setAttribute("iconSrc", iconSrc);
    }

    public String getIconSrc() {
        return getAttributeAsString("iconSrc");
    }

    public void setTestDisabled() {
        setAttribute("testDisabled", true);
    }

    public Boolean getTestDisabled() {
        return getAttributeAsBoolean("testDisabled");
    }

    // Java doesn't allow downcasting of arrays (e.g. from TreeNode[] to ExplorerTreeNode[])
    public static ExplorerTreeNode[] arrayOfExplorerTreeNode(TreeNode[] data) {
        ExplorerTreeNode[] convertedData = new ExplorerTreeNode[data.length];
        for (int i = 0; i < data.length; i++) convertedData[i] = (ExplorerTreeNode) data[i];
        return convertedData;
    }
    public static List<ExplorerTreeNode> listOfExplorerTreeNode(TreeNode[] data) {
        List<ExplorerTreeNode> convertedData = new ArrayList<ExplorerTreeNode>();
        for (int i = 0; i < data.length; i++) convertedData.add((ExplorerTreeNode) data[i]);
        return convertedData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExplorerTreeNode that = (ExplorerTreeNode) o;

        if (!getName().equals(that.getName())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
