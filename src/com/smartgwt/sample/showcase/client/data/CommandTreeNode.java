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

import com.smartgwt.sample.showcase.client.Command;


public class CommandTreeNode extends ExplorerTreeNode {

    private String description;

    public CommandTreeNode(String name, String nodeID, String parentNodeID, String icon, Command command, boolean enabled, String idSuffix) {
        super(name, nodeID, parentNodeID, icon, null, enabled, idSuffix);
        setCommand(command);
    }

    public void setCommand(Command command) {
        setAttribute("command", command);
    }

    public Command getCommand() {
        return (Command) getAttributeAsObject("command");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
