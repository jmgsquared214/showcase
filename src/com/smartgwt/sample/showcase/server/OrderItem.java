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

//----------------------------------------------------------------------
// Master-Detail load and save example (Hibernate)
//
// OrderItem Bean - to be managed by Hibernate
//----------------------------------------------------------------------
public class OrderItem implements Serializable {

    // this bean has no business logic.  It simply stores data in these instance variables.
    protected Long pk;
    protected Order order;
    protected String itemDescription;
    protected float unitPrice;
    protected Long quantity;

    public OrderItem() { }

    // Getters
    public String getItemDescription() { return itemDescription; }
    public Order getOrder() { return order; }
    public Long getPk() { return pk; }
    public Long getQuantity() { return quantity; }
    public float getUnitPrice() { return unitPrice; }

    // Setters
    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
    public void setOrder(Integer orderID) { this.order = order; }
    public void setPk(Long pk) { this.pk = pk; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }
    public void setUnitPrice(float unitPrice) { this.unitPrice = unitPrice; }

}
