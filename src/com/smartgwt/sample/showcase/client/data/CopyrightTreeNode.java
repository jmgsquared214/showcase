package com.smartgwt.sample.showcase.client.data;

public class CopyrightTreeNode extends ExplorerTreeNode {

	public CopyrightTreeNode(String parentNodeID, String description, String idSuffix) {
		super("Copyright", "copyright", parentNodeID, "blank.gif", null, false, idSuffix);
		setSingleCellValue("<div class='copyright_footer'>" + description + "</div>");
	}
}
