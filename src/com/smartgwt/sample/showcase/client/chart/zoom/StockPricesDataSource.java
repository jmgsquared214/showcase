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
package com.smartgwt.sample.showcase.client.chart.zoom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.events.ErrorEvent;
import com.smartgwt.client.data.events.HandleErrorHandler;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StockPricesDataSource extends DataSource {

    public StockPricesDataSource() {
        setDataProtocol(DSProtocol.CLIENTCUSTOM);
        setCanMultiSort(false);
        setAllowAdvancedCriteria(false);

        setFields(
            new DataSourceField("symbol", FieldType.TEXT, "Symbol"),
            new DataSourceField("date", FieldType.DATE, "Date"),
            new DataSourceField("open", FieldType.FLOAT, "Open"),
            new DataSourceField("high", FieldType.FLOAT, "High"),
            new DataSourceField("low", FieldType.FLOAT, "Low"),
            new DataSourceField("close", FieldType.FLOAT, "Close"),
            new DataSourceField("volume", FieldType.FLOAT, "Volume"),
            new DataSourceField("adjClose", FieldType.FLOAT, "Adj Close"));

        // Suppress RPCManager.handleError() from being called.  In the sample,
        // an error label is drawn rather than showing the default warning dialog.
        addHandleErrorHandler(new HandleErrorHandler() {
                public void onHandleError(ErrorEvent event) {
                    event.cancel();
                }
            });
    }

    @Override
    protected Object transformRequest(final DSRequest dsRequest) {
        if (dsRequest.getOperationType() == DSOperationType.FETCH) {
            Criteria criteria = new Criteria(dsRequest.getData());
            final String symbol = criteria.getAttribute("symbol");
            RPCRequest requestProperties = new RPCRequest();
            requestProperties.setActionURL(Page.getURL("[APP]data/" + symbol + ".txt"));
            requestProperties.setWillHandleError(true);
            RPCManager.sendRequest(requestProperties, new RPCCallback() {
                @Override
                public void execute(RPCResponse response, Object rawData, RPCRequest request) {
                    final DSResponse dsResponse = new DSResponse();
                    dsResponse.setStatus(response.getStatus());
                    if (response.getStatus() >= 0) {
                        dsResponse.setData(parseData((String)rawData, symbol));
                    }
                    processResponse(dsRequest.getRequestId(), dsResponse);
                }
            });
        }
        return null;
    }

    private Record[] parseData(String data, String symbol) {
        List<Record> records = new ArrayList<Record>();
        List<DataSourceField> fields = new ArrayList<DataSourceField>();
        int len = data.length();
        int i = -1, j;
        DataSourceField[] dsFields = this.getFields();

        // The line separator is "\n" and the value separator is ",".
        while (true) {
        	j = data.indexOf("\n", i + 1);
        	if (j == -1) j = len;
        	if (j > i + 1) { // Skip consecutive newlines.
        		Record record = new Record();
        		int k = i, l, index = 0;
        		while (true) {
        			l = Math.min(data.indexOf(",", k + 1), j);
                  if (l == -1) l = j;

                  if (i == -1) {
                      // The first line is a header:
                      // e.g. "Date,Open,High,Low,Close,Volume,Adj Close"
                	  String fieldTitle = data.substring(k + 1, l);
                	  for (DataSourceField dataSourceField : dsFields) {
						  if (fieldTitle.equals(dataSourceField.getTitle())) {
							  fields.add(index, dataSourceField);
							  break;
						  }
					  }
                  } else {
                	  DataSourceField field = fields.get(index);
                      if (field != null) {
                          String fieldName = field.getName();
                          FieldType fieldType = field.getType();
                          String value = data.substring(k + 1, l);

                          if (fieldType == FieldType.FLOAT) {
                              record.setAttribute(fieldName, Float.parseFloat(value));
                          } else if (fieldType == FieldType.DATE) {
                              // e.g. "2012-07-19" is July 19, 2012
                              int year = Integer.parseInt(value.substring(0, 4));
                              int month = Integer.parseInt(String.valueOf(value.substring(5,7)));
                              int day = Integer.parseInt(String.valueOf(value.substring(8,10)));
                              record.setAttribute(fieldName, DateUtil.createLogicalDate(year, month - 1, day));
                          } else {
                        	  record.setAttribute(fieldName, value);
                          }
                      }
                  }

                  if (!(l != -1 && l < j)) break;
                  k = l;
                  ++index;
        		}

        		if (i != -1) {
                  record.setAttribute("symbol", symbol);
                  records.add(record);
              }
        	}
            if (!(j != -1 && j < len)) break;
            i = j;
        }
        Record[] res = new Record[records.size()];
        for (int k = 0; k < records.size(); k++) {
            Record r = new ListGridRecord();
            r.setAttribute("date", records.get(k).getAttributeAsDate("date"));
            r.setAttribute("close", records.get(k).getAttributeAsFloat("close"));
            res[k] = r;
        }
        return res;
    }
}