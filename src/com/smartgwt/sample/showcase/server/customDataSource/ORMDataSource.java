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

//----------------------------------------------------------------------
// Example of a custom DataSource implementation.  This class demonstrates 
// how to write an adapter to plug support for an ORM system (Hibernate, in
// this case).  Note that this is just an example to demonstrate the principles
// involved - SmartClient Server already has considerably more sophisticated
// Hibernate support than this, built-in
//
// NOTE: In the interests of clarity and brevity, this example intentionally 
// omits exception handling
//
//----------------------------------------------------------------------

package com.smartgwt.sample.showcase.server.customDataSource;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.isomorphic.datasource.BasicDataSource;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;

public class ORMDataSource extends BasicDataSource {

    // Override all four CRUD operations - create, retrieve, update and delete
    // (add, fetch, update and remove in SmartClient terminology).

    // Note that the parameters sent by the client arrive here already converted to Java Maps
    // by the SmartClient Server - with SmartClient Pro, Power and Enterprise Editions, there's
    // no need to worry about conversion to and from XML or JSON even in a custom DS
    // implementation

    public DSResponse executeAdd(DSRequest req) throws Exception {
        Map createdRecord = createRecord(req.getValues());
        return new DSResponse(createdRecord);
    }

    public DSResponse executeFetch(DSRequest req) throws Exception {
        List records = fetchRecords(req.getCriteria());
        return new DSResponse(records);
    }

    public DSResponse executeRemove(DSRequest req) throws Exception {
        Map removedRecord = removeRecord(req.getValues().get(getPrimaryKey()));
        return new DSResponse(removedRecord);
    }

    public DSResponse executeUpdate(DSRequest req) throws Exception {
        Map updatedRecord = updateRecord(req.getValues());
        return new DSResponse(updatedRecord);
    }

// -----------------------------------------------------------------------------------------
// Code for actual data creation and manipulation.
//
// You can replace code below to implement any data access approah you actually want to use.
//
// -----------------------------------------------------------------------------------------

    // Creates record and saves it.
    private Map createRecord (Map values) {
        Country record = new Country();
        setProperties(values, record);
        Session ses = null;
        Transaction tx = null;
        try {
            ses = getSession();
            tx = ses.beginTransaction();
            ses.saveOrUpdate(record);
            tx.commit();
            return getProperties(record);
        } finally {
            if (tx != null) {
                if (tx.isActive()) tx.rollback();
            }
            if (ses != null) ses.close();
        }
    }

    // Returns full data list.
    // This sample does not support server side sorting/filtering.
    private List fetchRecords (Map criteria) {
        Session ses = null;
        Transaction tx = null;
        try {
            ses = getSession();
            tx = ses.beginTransaction();
            Query q = ses.createQuery("from com.smartgwt.sample.showcase.server.customDataSource.Country");
            List records = q.list();
            tx.commit();
            return records;
        } finally {
            if (tx != null) {
                if (tx.isActive()) tx.rollback();
            }
            if (ses != null) ses.close();
        }
    }

    // Finds record by id. Removes it.
    // Returns removed record.
    private Map removeRecord (Object id) {
        Session ses = null;
        Transaction tx = null;
        try {
            ses = getSession();
            tx = ses.beginTransaction();
            Object record = ses.get(Country.class, (Serializable) id);
            ses.delete(record);
            tx.commit();
            return getProperties(record);
        } finally {
            if (tx != null) {
                if (tx.isActive()) tx.rollback();
            }
            if (ses != null) ses.close();
        }
    }

    // Finds record by id. Updates it.
    // Returns updated record.
    private Map updateRecord (Map values) {
        Object id = values.get(getPrimaryKey());
        Session ses = null;
        Transaction tx = null;
        try {
            ses = getSession();
            tx = ses.beginTransaction();
            Object record = ses.get(Country.class, (Serializable) id);
            setProperties(values, record);
            ses.saveOrUpdate(record);
            tx.commit();
            return getProperties(record);
        } finally {
            if (tx != null) {
                if (tx.isActive()) tx.rollback();
            }
            if (ses != null) ses.close();
        }
    }

    private static Configuration hibernateConfig = null;
    private static SessionFactory sessionFactory = null;

    private static synchronized Session getSession() {
        if (sessionFactory == null) {
            hibernateConfig = new Configuration();
            sessionFactory = hibernateConfig.configure().buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

}
