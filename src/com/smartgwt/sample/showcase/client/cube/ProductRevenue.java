package com.smartgwt.sample.showcase.client.cube;

import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * ProductRevenue record class.
 */
public class ProductRevenue extends ListGridRecord {

    public ProductRevenue(String quarter, String month, String region, String product, String metric, int value, int percentNational) {
        setQuarter(quarter);
        setMonth(month);
        setRegion(region);
        setProduct(product);
        setMetric(metric);
        setValue(value);
        setPercentNational(percentNational);
    }

    public ProductRevenue(String quarter, String month, String region, String product, String metric, int value, int percentNational, String hilite) {
        setQuarter(quarter);
        setMonth(month);
        setRegion(region);
        setProduct(product);
        setMetric(metric);
        setValue(value);
        setPercentNational(percentNational);
    }

    public void setQuarter(String quarter) {
        setAttribute("quarter", quarter);
    }

    public String getQuarter(String quarter) {
        return getAttribute("quarter");
    }

    public void setRegion(String region) {
        setAttribute("region", region);
    }

    public String getRegion(String region) {
        return getAttribute("region");
    }

    public void setMonth(String month) {
        setAttribute("month", month);
    }

    public String getMonth(String month) {
        return getAttribute("month");
    }


    public void setProduct(String product) {
        setAttribute("product", product);
    }

    public String getProduct(String product) {
        return getAttribute("product");
    }

    public void setMetric(String metric) {
        setAttribute("metric", metric);
    }

    public String getMetric(String metric) {
        return getAttribute("metric");
    }

    public void setValue(Integer value) {
        setAttribute("_value", value);
    }

    public Integer getValue(Integer value) {
        return getAttributeAsInt("_value");
    }

    public void setPercentNational(Integer percentNational) {
        setAttribute("percentNational", percentNational);
    }

    public Integer getPercentNational(Integer percentNational) {
        return getAttributeAsInt("percentNational");
    }

    public void setHilite(String hilite) {
        setAttribute("_hilite", hilite);
    }

    public String getHilite(String hilite) {
        return getAttribute("_hilite");
    }
}
