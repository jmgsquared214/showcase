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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.log.Logger;

// CustomExportDMI implementation for the Custom Export example
//
// This example shows one way to insert your own logic into SmartClient's 
// normal client/server flow to export data using a DataSource to collect
// data and reformatting it prior to export. 


public class CustomExportDMI {

    private static Logger log = new Logger(DSRequest.class.getName());

    public static DSResponse customExport(DSRequest dsRequest, HttpServletRequest servletRequest)
    throws Exception
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        
        DSResponse response = dsRequest.execute();

        List data = response.getDataList();

        List fields = (List)dsRequest.getExportFields();

        fields.add("gdppercapita");

        dsRequest.setExportFields(fields);

        for (Iterator i = data.iterator(); i.hasNext(); ) {
            Map record = (Map)i.next();
            Date dateField = (Date)record.get("independence");
            if (dateField != null) {
                String field = sdf.format(dateField);
                record.put("independence", field);
            }
            double population = 1;
            Object populationObj = record.get("population");
            if (populationObj != null) population = Double.parseDouble(populationObj.toString());
            double gdp = Double.parseDouble(record.get("gdp").toString());
            double gdppercapita = (gdp * (double)1000000) / population;

            record.put("gdppercapita", new Long(Math.round(gdppercapita)));
        }

        response.setData(data);

        return response;      
    }
}
