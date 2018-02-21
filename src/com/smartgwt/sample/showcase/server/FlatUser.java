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
// Flattened Beans example
//
// FlatUser Bean - to be managed by Hibernate
//----------------------------------------------------------------------


import java.io.Serializable;

public class FlatUser implements Serializable {

    protected Long userId;
    protected String firstName;
    protected String surname;
    protected String email;
    protected Address address = new Address();
    protected String password;

    // In a complete system, User objects are typically related to several other entities
    // similar to the samples commented out below.  Naive data delivery systems send 
    // all related entities to the browser unless extra code is written to define exactly
    // what to send.  By contrast, SmartClient Server delivers just those fields defined in
    // the DataSource, if DataSource.dropExtraFields is set.  Using the DataSource definition 
    // to select and trim data in this way avoids extra work and makes it easy to audit 
    // exactly what data is visible to users.
    //
    // Collection<Account> accounts;
    // Collection<Project> projects;
    

    public FlatUser() { }

    // Getters
    public Long getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public Address getAddress() { return address; }
    public String getPassword() { return password; }

    // Setters
    public void setUserId(Long value) { userId = value; }
    public void setFirstName(String value) { firstName = value; }
    public void setSurname(String value) { surname = value; }
    public void setEmail(String value) { email = value; }
    public void setAddress(Address value) { address = value; }
    public void setPassword(String value) { password = value; }

}
