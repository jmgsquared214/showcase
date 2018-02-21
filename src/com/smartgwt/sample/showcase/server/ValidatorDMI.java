//----------------------------------------------------------------------
// SmartGWT ValidatorDMI sample
//----------------------------------------------------------------------

package com.smartgwt.sample.showcase.server;

import java.util.Map;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DataSource;
import com.isomorphic.datasource.Validator;
import com.isomorphic.log.Logger;
import com.isomorphic.util.DataTools;

// the <serverObject> declaration in a <validator> delcaration in a DataSource .ds.xml file
// directs the SmartClient Java server libraries to find the ValidatorDMI class.  
// Several ways of finding or creating the DMI target are supported, including via Spring, or
// via session or request attributes.  See ServerObject.lookupStyle in the SmartClient Reference.
// You can also provide your own servlet, invoking SmartClient DMI from a Spring Controller or
// Struts Action.
public class ValidatorDMI {

    Logger log = new Logger(ValidatorDMI.class.getName());

    // the first four arguments (value, validator, fieldName, record) are required.
    // After those for arguments, you can optionally declare further arguments for any of the
    // objects that are available at validation time, in any order.  
    //
    // Here, we declare the four required arguments, and then a fifth argument of type
    // "DataSource" - it receives the DataSource that this validator was declared in.
    public boolean condition(Object value, Validator validator, 
                             String fieldName, Map record, DataSource ds)
        throws Exception 
    {
        log.warn("validating value: '" + value +
                 "' for fieldName: '" + fieldName +
                 "' in DataSource: '" + ds.getID() +
                 "'\nin record: " + DataTools.prettyPrint(record) +
                 "\nvalidator definition: " + DataTools.prettyPrint(validator));

        int quantityOrdered = Integer.valueOf(value.toString()).intValue();

        // look up the StockItem by id to see current quanity
        DSRequest dsRequest = new DSRequest("StockItem", "fetch");
        dsRequest.setCriteria(DataTools.buildMap("id", record.get("itemId")));

        long quantityAvailable =
            ((Integer)dsRequest.execute().getDataMap().get("quantity")).intValue();

        if (quantityOrdered > quantityAvailable) {
            validator.addErrorMessageVariable("available", "" + quantityAvailable);
            return false;
        }
        
        return true;
    }
}
