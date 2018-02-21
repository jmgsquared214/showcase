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

// a typical Java Bean which can be stored by many different ORM (object-relational mapping)
// systems, including Hibernate, Toplink, JDO, EJB3, etc.
public class Country {

    public Country() {
    }

    // SmartClient will call these getters when serializing a Java Bean to be transmitted to
    // client-side components.
    public Float getArea() { return area; }
    public String getCapital() { return capital; }
    public String getCountryCode() { return countryCode; }
    public String getCountryName() { return countryName; }
    public Float getGdp() { return gdp; }
    public Long getPk() { return pk; }
    public Long getPopulation() { return population; }

    // when receiving data from client-side SmartClient components, SmartClient will call these
    // setters to modify properties.  The setters are found via the Java Beans naming
    // convention, for example, a DataSource field named "countryCode" will be applied via a
    // setter called setCountryCode().
    public void setArea(Float area) { this.area = area; }
    public void setCapital(String capital) { this.capital = capital; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
    public void setGdp(Float gdp) { this.gdp = gdp; }
    public void setPk(Long pk) { this.pk = pk; }
    public void setPopulation(Long population) { this.population = population; }
    
    // this bean has no business logic.  It simply stores data in these instance variables.
    private Long pk;
    private String countryCode;
    private String countryName;
    private String capital;
    private Float area;
    private Long population;
    private Float gdp;

}
