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

//----------------------------------------------------------------------
// Master-Detail load and save example (Hibernate)
//
// Order Bean - to be managed by Hibernate
//----------------------------------------------------------------------


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Order implements Serializable {

    protected long orderID;
    protected String customerName;
    protected Date orderDate;
    protected long trackingNumber;
    protected Set items;

    public Order() { }

    // Getters
    public String getCustomerName() { return customerName; }
    public Date getOrderDate() { return orderDate; }
    public long getOrderID() { return orderID; }
    public long getTrackingNumber() { return trackingNumber; }
    public Set getItems() { return items; }

    // Setters
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public void setOrderID(long orderID) { this.orderID = orderID; }
    public void setTrackingNumber(long trackingNumber) { this.trackingNumber = trackingNumber; }
    public void setItems(Set items) { this.items = items; }

}