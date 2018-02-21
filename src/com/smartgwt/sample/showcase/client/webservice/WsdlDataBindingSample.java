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

package com.smartgwt.sample.showcase.client.webservice;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.WSDLLoadCallback;
import com.smartgwt.client.data.WebService;
import com.smartgwt.client.data.XMLTools;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.sample.showcase.client.PanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class WsdlDataBindingSample extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>Select or enter a valid IP address to find out which Country it relates to. " +
    										  "DataSources can bind directly to the structure of WSDL messages.</p>";

    public static class Factory implements PanelFactory {

        private String id;

        public ShowcasePanel create() {
            WsdlDataBindingSample panel = new WsdlDataBindingSample();
            id = panel.getID();
            return panel;
        }

        public String getID() {
            return id;
        }

        public String getDescription() {
            return DESCRIPTION;
        }
    }
    
    private static final String WSDL_ADDRESS = "http://www.webservicex.net/geoipservice.asmx?WSDL";

    private WebService webService;
    private DataSource ipDataSource;
    private DynamicForm ipForm;

    public Canvas getViewPanel() {
    	
		ipForm = new DynamicForm();
		ipForm.setID("ipForm");
		ComboBoxItem ipAddress = new ComboBoxItem("ipAddress", "<nobr>IP Address</nobr>");
		ipAddress.setValueMap(new String[] {
			"108.1.1.1", "112.1.1.12", "202.65.32.1", "85.1.1.5", "141.32.1.1"
		});
		ipAddress.setChangeOnKeypress(false);
		ipAddress.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				callService();
			}
		});
		
		TextItem country = new TextItem("country", "Country");
		
		ipForm.setItems(ipAddress, country);
	
		RPCRequest handlesError = new RPCRequest();
		handlesError.setWillHandleError(true);
	    XMLTools.loadWSDL(WSDL_ADDRESS, new WSDLLoadCallback() {
	        public void execute(WebService webService) {
	            setGeoIpService(webService);
	        }
	    }, handlesError);

	    SC.showPrompt("Loading WSDL from " + WSDL_ADDRESS);
	
	    return ipForm;
    }
    
    private void setGeoIpService(WebService webService) {
    	this.webService = webService;
        SC.clearPrompt();
        if (webService == null) {
            SC.warn("WSDL not currently available from service (tried "
            		+ WSDL_ADDRESS + ")");
            return;
        }
        ipDataSource = this.webService.getFetchDS("GetGeoIP", "GetGeoIPResponse");
    }
    
    private void callService() {
        ipForm.setValue("country", "Loading...");
        String ip = ipForm.getValueAsString("ipAddress");

        Criteria crit = new Criteria("IPAddress", ip);

        ipDataSource.fetchData(crit, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
	            if (dsResponse.getHttpResponseCode() == 500) {
	                SC.warn("Invalid IP address");
	                return;
	            }
	            Record row = dsResponse.getData()[0].getAttributeAsRecord("GetGeoIPResult");
	            ipForm.setValue("country", row.getAttribute("CountryName") + " (" + row.getAttribute("CountryCode") + ")");
			}
		});
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}