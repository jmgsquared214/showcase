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

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class StockSymbolDataSource extends DataSource {

    public StockSymbolDataSource() {
        setID("nasdaqSymbols");
        setClientOnly(true);

        setCacheData(
            prepareRecord("AAPL", "Apple Inc.", "Technology", "Computer Manufacturing", "http://www.nasdaq.com/symbol/aapl"),
            prepareRecord("XOM", "Exxon Mobil Corporation", "Energy", "Integrated oil Companies", "http://www.nasdaq.com/symbol/xom"),
            prepareRecord("MSFT", "Microsoft Corporation", "Technology", "Computer Software: Prepackaged Software", "http://www.nasdaq.com/symbol/msft"),
            prepareRecord("WMT", "Wal-Mart Stores, Inc.", "Consumer Services", "Department/Specialty Retail Stores", "http://www.nasdaq.com/symbol/wmt"),
            prepareRecord("IBM", "International Business Machines Corporation", "Technology", "Computer Manufacturing", "http://www.nasdaq.com/symbol/ibm"),
            prepareRecord("GE", "General Electric Company", "Energy", "Consumer Electronics/Appliances", "http://www.nasdaq.com/symbol/ge"),
            prepareRecord("T", "AT&T Inc.", "Public Utilities", "Telecommunications Equipment", "http://www.nasdaq.com/symbol/t"),
            prepareRecord("CVX", "Chevron Corporation", "Energy", "Integrated oil Companies", "http://www.nasdaq.com/symbol/cvx"),
            prepareRecord("JNJ", "Johnson & Johnson", "Consumer Durables", "Major Pharmaceuticals", "http://www.nasdaq.com/symbol/jnj"),
            prepareRecord("KO", "Coca-Cola Company (The)", "Consumer Non-Durables", "Beverages (Production/Distribution)", "http://www.nasdaq.com/symbol/ko")
        );

        DataSourceField symbolKey = new DataSourceField("symbol", FieldType.TEXT, "Symbol");
        symbolKey.setPrimaryKey(true);
        setFields(
            symbolKey,
            new DataSourceField("name", FieldType.TEXT, "Name"),
            new DataSourceField("sector", FieldType.TEXT, "Sector"),
            new DataSourceField("industry", FieldType.TEXT, "industry"),
            new DataSourceField("summaryQuote", FieldType.LINK, "Summary Quote"));
    }

    private Record prepareRecord(String symbol, String name, String sector, String industry, String summaryQuote) {
        Record res = new Record();
        res.setAttribute("symbol", symbol);
        res.setAttribute("name", name);
        res.setAttribute("sector", sector);
        res.setAttribute("industry", industry);
        res.setAttribute("summaryQuote", summaryQuote);
        return res;
    }
}
