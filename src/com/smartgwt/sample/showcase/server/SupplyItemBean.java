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

package com.smartgwt.sample.showcase.server;

import java.io.Serializable;
import java.util.Date;

// a typical Java Bean which can be stored by many different ORM (object-relational mapping)

// systems, including Hibernate, Toplink, JDO, EJB3, etc.
public class SupplyItemBean implements Serializable {

    // this bean has no business logic.  It simply stores data in these instance variables.
    protected Long itemID;
    protected String SKU;
    protected String category;
    protected String itemName;
    protected String description;
    protected double unitCost;
    protected String units;
    protected boolean inStock;
    protected Date nextShipmentDate;

    // a zero-argument constructor is not required, but does enable certain convenience
    // features (see the docs for DMI)
    public SupplyItemBean() {
    }

    // when receiving data from client-side SmartClient components, SmartClient will call these
    // setters to modify properties.  The setters are found via the Java Beans naming
    // convention, for example, a DataSource field named "category" will be applied via a
    // setter called setCategory().
    public void setItemID(Long id) {
        itemID = id;
    }

    public void setSKU(String sku) {
        SKU = sku;
    }

    public void setCategory(String c) {
        category = c;
    }

    public void setItemName(String name) {
        itemName = name;
    }

    public void setDescription(String d) {
        description = d;
    }

    public void setUnitCost(double cost) {
        unitCost = cost;
    }

    public void setUnits(String newUnits) {
        units = newUnits;
    }

    public void setInStock(boolean val) {
        inStock = val;
    }

    public void setNextShipment(Date date) {
        nextShipmentDate = date;
    }

    // SmartClient will call these getters when serializing a Java Bean to be transmitted to
    // client-side components.
    public Long getItemID() {
        return itemID;
    }

    public String getSKU() {
        return SKU;
    }

    public String getCategory() {
        return category;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public String getUnits() {
        return units;
    }

    public boolean getInStock() {
        return inStock;
    }

    public Date getNextShipment() {
        return nextShipmentDate;
    }

}