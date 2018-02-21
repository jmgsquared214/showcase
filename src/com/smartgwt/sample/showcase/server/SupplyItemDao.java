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
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.datasource.DataSource;
import com.isomorphic.log.Logger;
import com.isomorphic.util.DataTools;

public class SupplyItemDao {

    Logger log = new Logger(SupplyItemDao.class.getName());

    // autoconfigured by Spring
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DSResponse fetch(DSRequest dsRequest)
            throws Exception {
        log.info("procesing DMI fetch operation");
        DSResponse dsResponse = new DSResponse(dsRequest == null ? (DataSource)null :
                                                                   dsRequest.getDataSource());

        Session hibernateSession = sessionFactory.getCurrentSession();

        // DataSource protocol: get filter criteria
        String itemName = (String) dsRequest.getFieldValue("itemName");

        // DataSource protocol: get requested row range
        long startRow = (int) dsRequest.getStartRow();
        long endRow = (int) dsRequest.getEndRow();

        Criteria criteria = hibernateSession.createCriteria(SupplyItem.class);
        Criterion itemNameRestriction = null;
        if (itemName != null) {
            itemNameRestriction = Restrictions.like("itemName", itemName, MatchMode.ANYWHERE);
            criteria.add(itemNameRestriction);
        }

        // determine total available rows
        // this is used by e.g. the ListGrid to auto-size its scrollbar
        criteria.setProjection(Projections.rowCount());
        Object rowCount = criteria.uniqueResult();
        long totalRows = 0;
        // Later versions of Hibernate return a Long rather than an Integer here, for all
        // those occasions when a fetch returns more than 2.1 billion rows...
        if (rowCount instanceof Integer) {
            totalRows = ((Integer)rowCount).intValue();
        } else if (rowCount instanceof Long) {
            totalRows = ((Long)rowCount).longValue();
        }

        // clamp endRow to available rows and slice out requested range
        endRow = Math.min(endRow, totalRows);

        // rebuilt the criteria minus the rowCount projection
        criteria = hibernateSession.createCriteria(SupplyItem.class);
        if (itemName != null) criteria.add(itemNameRestriction);

        // limit number of rows returned to just what the ListGrid asked for
        criteria.setFirstResult((int) startRow);
        criteria.setMaxResults((int)(endRow - startRow));
        List matchingItems = criteria.list();

        // DataSource protocol: return matching item beans
        dsResponse.setData(matchingItems);
        // tell client what rows are being returned, and what's available
        dsResponse.setStartRow(startRow);
        dsResponse.setEndRow(endRow);
        dsResponse.setTotalRows(totalRows);

        return dsResponse;
    }

    public DSResponse add(DSRequest dsRequest, SupplyItem item)
            throws Exception {
        log.info("procesing DMI add operation");

        DSResponse dsResponse = new DSResponse();

        Session hibernateSession = sessionFactory.getCurrentSession();
        hibernateSession.saveOrUpdate(item);
        dsResponse.setData(item);
        return dsResponse;
    }


    public DSResponse update(DSRequest dsRequest, Map newValues)
            throws Exception {
        log.info("procesing DMI update operation");

        DSResponse dsResponse = new DSResponse();

        // primary key
        Serializable id = (Serializable) dsRequest.getFieldValue("itemID");

        Session hibernateSession = sessionFactory.getCurrentSession();
        SupplyItem item = (SupplyItem) hibernateSession.get(SupplyItem.class, id);

        log.warn("fetched item: " + DataTools.prettyPrint(item));

        // apply new values to the as-saved bean
        DataTools.setProperties(newValues, item);

        log.warn("Saving record: " + DataTools.prettyPrint(item));

        // persist
        hibernateSession.saveOrUpdate(item);
        dsResponse.setData(item);
        return dsResponse;
    }


    public SupplyItem remove(SupplyItem item)
            throws Exception {
        log.info("procesing DMI remove operation");

        Session hibernateSession = sessionFactory.getCurrentSession();
        hibernateSession.delete(item);

        return item;
    }
}
