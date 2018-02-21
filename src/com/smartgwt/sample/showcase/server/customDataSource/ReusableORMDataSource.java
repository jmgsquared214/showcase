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

package com.smartgwt.sample.showcase.server.customDataSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.isomorphic.datasource.BasicDataSource;
import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.util.DataTools;

public class ReusableORMDataSource extends BasicDataSource {

    protected String entityName;
    protected Session currentSession;

    protected static Configuration hibernateConfig;
    protected static SessionFactory sessionFactory;

    // We are only overriding execute() to provide a central point for initialization and
    // session/transaction management - for actual data operations, it is more appropriate to
    // override executeFetch(), et al, as we do further down in this class
    public DSResponse execute(DSRequest req) throws Exception {

        // Initialize the Hibernate Configuration if necessary
        if (hibernateConfig == null) {
            createConfig();
        }

        if (entityName == null) {
            // Pick up the fully-qualified class name from the DataSource definition. The property
            // can be called anything you like - here, we are expecting to find a "mappedBeanClass"
            // property in the DataSource definition
            entityName = getProperty("mappedBeanClass");
            if (hibernateConfig.getClassMapping(entityName) == null) {
                // Config problem - the bean named in the .ds.xml file is not mapped in Hibernate
            }
        }

        this.currentSession = sessionFactory.openSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            return super.execute(req);
        } finally {
            tx.commit();
            currentSession.close();
        }
    }

    // Implementation of basic DataSource operations
    // --------------------------------------------------------------------------------------------
    public DSResponse executeFetch(DSRequest req) throws Exception {

        DSResponse dsResponse = new DSResponse();
        dsResponse.setSuccess();

        List criterions = new ArrayList();

        // Implement simple filter criteria
        Map rCriteria = req.getCriteria();
        if (rCriteria != null) {
            boolean isFilter = "substring".equals(req.getOperationProperty("textMatchStyle"));
            for (Iterator i = rCriteria.keySet().iterator(); i.hasNext();) {
                String fieldName = (String) i.next();
                Object value = rCriteria.get(fieldName);
                Criterion criterion = null;

                String fieldType = getField(fieldName).getType();
                // Support OR on multiple values
                if (value instanceof List) {
                    criterion = Restrictions.disjunction();
                    for (Iterator j = ((List) value).iterator(); j.hasNext();) {
                        ((Disjunction) criterion).add(Restrictions.eq(fieldName, j.next()));
                    }
                } else {
                    if (isFilter && ("text".equals(fieldType) || "string".equals(fieldType))) {
                        criterion = Restrictions.like(fieldName, value.toString(), MatchMode.ANYWHERE);
                    } else {
                        // exact equality
                        criterion = Restrictions.eq(fieldName, value);
                    }
                }
                criterions.add(criterion);
            }
        }

        Criteria criteria = currentSession.createCriteria(entityName);
        addAllCriterions(criteria, criterions);

        // Implement data paging
        long totalRows = -1;
        if (req.isPaged()) {
            if (req.getEndRow() != DSRequest.ENDROW_UNSET) {
                // if specified, endRow overrides batchSize
                if (req.getEndRow() - req.getStartRow() > req.getBatchSize()) {
                    req.setBatchSize(req.getEndRow() - req.getStartRow());
                }
            }

            criteria.setProjection(Projections.rowCount());
            Object rowCount = criteria.uniqueResult();
            totalRows = 0;
            // Later versions of Hibernate return a Long rather than an Integer here, for all
            // those occasions when a fetch returns more than 2.1 billion rows...
            if (rowCount instanceof Integer) {
                totalRows = ((Integer)rowCount).intValue();
            } else if (rowCount instanceof Long) {
                totalRows = ((Long)rowCount).longValue();
            }

            // rebuild criteria, minus the count projection for the actual query
            criteria = currentSession.createCriteria(entityName);
            addAllCriterions(criteria, criterions);

            criteria.setMaxResults((int) req.getBatchSize());
            criteria.setFirstResult((int) req.getStartRow());
        }

        List results = null;

        // Implement sorting
        List sortBy = req.getSortByFields();
        for (Iterator i = sortBy.iterator(); i.hasNext();) {
            String sortByField = (String) i.next();
            if (sortByField.startsWith("-")) {
                // leading minus means sort in descending order
                criteria.addOrder(Order.desc(sortByField.substring(1)));
            } else {
                criteria.addOrder(Order.asc(sortByField));
            }
        }

        // Run the query
        results = criteria.list();

        // if we're not paged, we're returning all rows
        if (totalRows == -1) totalRows = results.size();
        dsResponse.setTotalRows(totalRows);

        // set startRow/endRow
        long startRow = 0;
        long endRow = 0;
        if (totalRows != 0) {
            startRow = req.getStartRow();
            endRow = startRow + results.size();
        }
        dsResponse.setStartRow(startRow);
        dsResponse.setEndRow(endRow);

        // DataSource protocol: return list of matching records
        dsResponse.setData(results);
        return dsResponse;
    }

    public DSResponse executeAdd(DSRequest req) throws Exception {

        DSResponse dsResponse = new DSResponse();
        dsResponse.setSuccess();

        Object record = Class.forName(entityName).newInstance();
        // populate the record from the submitted values
        DataTools.setProperties(req.getValues(), record);

        currentSession.saveOrUpdate(entityName, record);

        // DataSource protocol: return the committed bean to the client for cache update
        dsResponse.setData(record);
        return dsResponse;
    }

    public DSResponse executeRemove(DSRequest req) throws Exception {

        DSResponse dsResponse = new DSResponse();
        dsResponse.setSuccess();

        String primaryKey = getPrimaryKey();

        Serializable id = (Serializable) req.getFieldValue(primaryKey);
        Object record = currentSession.get(entityName, id);
        currentSession.delete(entityName, record);

        // DataSource protocol: return the primary key of the deleted record to the client for
        // cache update
        dsResponse.setData(req.getCriteria());
        return dsResponse;
    }

    public DSResponse executeUpdate(DSRequest req) throws Exception {

        DSResponse dsResponse = new DSResponse();
        dsResponse.setSuccess();

        String primaryKey = getPrimaryKey();

        Serializable id = (Serializable) req.getFieldValue(primaryKey);
        Object record = null;
        if (id != null) {
            record = currentSession.get(entityName, id);
        } else {
            // Error: the primary key value was not supplied in the update request
        }

        // populate the record from the submitted values
        DataTools.setProperties(req.getValues(), record);
        currentSession.saveOrUpdate(entityName, record);

        // DataSource protocol: return the committed bean to the client for cache update
        dsResponse.setData(record);
        return dsResponse;
    }

    private Criteria addAllCriterions(Criteria criteria, List criterions) {
        for (Iterator i = criterions.iterator(); i.hasNext();) {
            criteria.add((Criterion) i.next());
        }
        return criteria;
    }

    // This method is static and synchronized to avoid threading issues when multiple requests
    // are sent during server startup
    private static synchronized void createConfig() {
        hibernateConfig = new Configuration();
        sessionFactory = hibernateConfig.configure().buildSessionFactory();
    }

}
