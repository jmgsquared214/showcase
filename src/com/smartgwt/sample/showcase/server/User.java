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
// Simple queuing example
//
// User Bean - to be managed by Hibernate
//----------------------------------------------------------------------

import java.io.Serializable;

public class User implements Serializable {

    protected Long userID;
    protected String firstName;
    protected String surname;
    protected String department;
    protected String email;

    public User() { }

    // Getters
    public Long getUserID() { return userID; }
    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }

    // Setters
    public void setUserID(Long value) { userID = value; }
    public void setFirstName(String value) { firstName = value; }
    public void setSurname(String value) { surname = value; }
    public void setDepartment(String value) { department = value; }
    public void setEmail(String value) { email = value; }

}