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

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.LabelCollapseMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.chart.ChartSamplePanelFactory;

public class CityPopulationZoom  extends ShowcasePanel {
    private static final String DESCRIPTION = "<p>This example shows population change for 271 U.S. cities, sorted alphabetically.</p>"+
            "<p>When there are too many cities visible, the chart will label every Nth city depending on available space. This allows the end user to rapidly find the desired city since the cities are sorted alphabetically. It would also work with other sorting orders such as east to west or north to south.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "cityPopulationZoom";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new CityPopulationZoom();
        }
    }

    @Override
    public Canvas getViewPanel() {
        FacetChart chart = new FacetChart();
        chart.setWidth100();
        chart.setHeight100();
        chart.setMargin(5);

        chart.setCanZoom(true);
        chart.setZoomStartValue("Abilene, Texas");
        chart.setZoomEndValue("Greensboro, North Carolina");
        FacetChart zcp = new FacetChart();
        zcp.setShowInlineLabels(false);
        chart.setZoomChartProperties(zcp);
        chart.setZoomShowSelection(false);

        chart.setChartType(ChartType.COLUMN);
        chart.setData(getData());
        chart.setBarMargin(2);
        chart.setMinBarThickness(2);
        chart.setLabelCollapseMode(LabelCollapseMode.SAMPLE);
        chart.setMinLabelGap(3);

        chart.setTitle("Percentage Change in Populations of U.S. Cities from 2000 to 2009");
        chart.setFacets(new Facet("city", "City"));
        chart.setValueTitle("% Change");
        chart.setValueProperty("change");

        return chart;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }
    
    public Record getRecord(String city, double change) {
        Record res = new Record();
        res.setAttribute("city", city);
        res.setAttribute("change", change);
        return res;
    }
    
    private Record[] getData() {
        return new Record[]{
                getRecord("Abilene, Texas", 1.1297045853),
                getRecord("Akron, Ohio", -4.4472522861),
                getRecord("Albuquerque, New Mexico", 17.4588342873),
                getRecord("Alexandria, Virginia", 15.9027691927),
                getRecord("Allentown, Pennsylvania", 1.1445189737),
                getRecord("Amarillo, Texas", 8.8365945464),
                getRecord("Anaheim, California", 2.6206695478),
                getRecord("Anchorage, Alaska", 9.8194070288),
                getRecord("Ann Arbor, Michigan", -1.4994897025),
                getRecord("Antioch, California", 10.5235562061),
                getRecord("Arlington, Texas", 13.5286984157),
                getRecord("Arlington, Virginia", 14.7504049555),
                getRecord("Arvada, Colorado", 5.8237900111),
                getRecord("Atlanta, Georgia", 28.3865347963),
                getRecord("Aurora, Colorado", 16.3854944659),
                getRecord("Aurora, Illinois", 19.7084636895),
                getRecord("Austin, Texas", 16.9797736229),
                getRecord("Bakersfield, California", 32.4598288644),
                getRecord("Baltimore, Maryland", -1.732202376),
                getRecord("Baton Rouge, Louisiana", -1.2971315962),
                getRecord("Beaumont, Texas", -2.9884935948),
                getRecord("Bellevue, Washington", 12.576458037),
                getRecord("Berkeley, California", -0.2164481155),
                getRecord("Billings, Montana", 15.111473627),
                getRecord("Birmingham, Alabama", -4.9245820096),
                getRecord("Boise City, Idaho", 5.1730925563),
                getRecord("Boston, Massachusetts", 9.2704845427),
                getRecord("Boulder, Colorado", 5.8796169052),
                getRecord("Bridgeport, Connecticut", -1.6842105263),
                getRecord("Brownsville, Texas", 23.3601640533),
                getRecord("Buffalo, New York", -7.5185654153),
                getRecord("Burbank, California", 2.6406417964),
                getRecord("Cambridge, Massachusetts", 6.9706833984),
                getRecord("Cape Coral, Florida", 49.2224469454),
                getRecord("Carrollton, Texas", 17.1995609858),
                getRecord("Cary, North Carolina", 40.2685528328),
                getRecord("Cedar Rapids, Iowa", 4.8655569782),
                getRecord("Centennial, Colorado", -1.8371909136),
                getRecord("Chandler, Arizona", 38.9571047517),
                getRecord("Charleston, South Carolina", 16.7895449128),
                getRecord("Charlotte, North Carolina", 23.6136019851),
                getRecord("Chattanooga, Tennessee", 9.3002487721),
                getRecord("Chesapeake, Virginia", 11.0703354753),
                getRecord("Chicago, Illinois", -1.5334269674),
                getRecord("Chula Vista, California", 27.4082046376),
                getRecord("Cincinnati, Ohio", 0.5294966461),
                getRecord("Clarksville, Tennessee", 19.8385669206),
                getRecord("Clearwater, Florida", -3.1462561743),
                getRecord("Cleveland, Ohio", -9.4854104504),
                getRecord("Colorado Springs, Colorado", 10.3927263892),
                getRecord("Columbia, Missouri", 19.0007675668),
                getRecord("Columbia, South Carolina", 8.3463181704),
                getRecord("Columbus, Georgia", 2.0959223613),
                getRecord("Columbus, Ohio", 7.4529554968),
                getRecord("Concord, California", -0.0441616644),
                getRecord("Coral Springs, Florida", 5.6667752416),
                getRecord("Corona, California", 17.0643040957),
                getRecord("Corpus Christi, Texas", 3.6739007333),
                getRecord("Costa Mesa, California", 0.8696684846),
                getRecord("Dallas, Texas", 9.1381680665),
                getRecord("Daly City, California", -1.4013144561),
                getRecord("Davenport, Iowa", 3.1630492324),
                getRecord("Dayton, Ohio", -7.2139393501),
                getRecord("Denton, Texas", 46.8538157124),
                getRecord("Denver, Colorado", 9.7557247516),
                getRecord("Des Moines, Iowa", 0.4664165084),
                getRecord("Detroit, Michigan", -3.6365290485),
                getRecord("Downey, California", -0.3395917456),
                getRecord("Durham, North Carolina", 21.3379573251),
                getRecord("Elgin, Illinois", 13.2804433487),
                getRecord("Elizabeth, New Jersey", 3.6938637003),
                getRecord("Elk Grove, California", 65.6199500514),
                getRecord("El Monte, California", 4.511892878),
                getRecord("El Paso, Texas", 9.855663678),
                getRecord("Erie, Pennsylvania", -0.0848937381),
                getRecord("Escondido, California", 4.3583787486),
                getRecord("Eugene, Oregon", 10.1915224018),
                getRecord("Evansville, Indiana", -3.8791646398),
                getRecord("Fairfield, California", 6.9771764949),
                getRecord("Fayetteville, North Carolina", -0.6809440954),
                getRecord("Flint, Michigan", -10.6348353789),
                getRecord("Fontana, California", 28.1309844277),
                getRecord("Fort Collins, Colorado", 15.5511319152),
                getRecord("Fort Lauderdale, Florida", 8.0614147365),
                getRecord("Fort Wayne, Indiana", 0.7782162464),
                getRecord("Fort Worth, Texas", 33.2575692362),
                getRecord("Fremont, California", 0.5986470479),
                getRecord("Fresno, California", 11.421234944),
                getRecord("Frisco, Texas", 190.2698259736),
                getRecord("Fullerton, California", 4.8263433296),
                getRecord("Gainesville, Florida", 3.2027398957),
                getRecord("Garden Grove, California", 0.5592266349),
                getRecord("Garland, Texas", 2.7761832457),
                getRecord("Gilbert, Arizona", 89.3125671321),
                getRecord("Glendale, Arizona", 14.1712507891),
                getRecord("Glendale, California", 0.8022937306),
                getRecord("Grand Prairie, Texas", 27.5362658297),
                getRecord("Grand Rapids, Michigan", -2.135527971),
                getRecord("Green Bay, Wisconsin", -1.3473155831),
                getRecord("Greensboro, North Carolina", 11.6428465154),
                getRecord("Gresham, Oregon", 12.8672779231),
                getRecord("Hampton, Virginia", -1.4942905535),
                getRecord("Hartford, Connecticut", -0.0950248836),
                getRecord("Hayward, California", 2.336945729),
                getRecord("Henderson, Nevada", 43.0727344748),
                getRecord("Hialeah, Florida", -3.2041814435),
                getRecord("High Point, North Carolina", 19.3501830064),
                getRecord("Hollywood, Florida", 2.089432582),
                getRecord("Honolulu, Hawaii", 0.9952394559),
                getRecord("Houston, Texas", 14.162880073),
                getRecord("Huntington Beach, California", 1.7699720531),
                getRecord("Huntsville, Alabama", 12.2144699776),
                getRecord("Independence, Missouri", 6.6903795529),
                getRecord("Indianapolis, Indiana", 3.2134581308),
                getRecord("Inglewood, California", -0.4275968525),
                getRecord("Irvine, California", 42.9664119327),
                getRecord("Irving, Texas", 7.0866264803),
                getRecord("Jackson, Mississippi", -5.5268271618),
                getRecord("Jacksonville, Florida", 10.4448514288),
                getRecord("Jersey City, New Jersey", 1.0311297015),
                getRecord("Joliet, Illinois", 36.4205857895),
                getRecord("Kansas City, Kansas", -2.4395394782),
                getRecord("Kansas City, Missouri", 9.0264010362),
                getRecord("Killeen, Texas", 32.5789847355),
                getRecord("Knoxville, Tennessee", 5.0075734798),
                getRecord("Lafayette, Louisiana", 2.685193459),
                getRecord("Lakewood, Colorado", -1.712485285),
                getRecord("Lancaster, California", 22.3497024684),
                getRecord("Lansing, Michigan", -4.6157456688),
                getRecord("Laredo, Texas", 25.979987966),
                getRecord("Las Vegas, Nevada", 17.1633088194),
                getRecord("Lewisville, Texas", 34.1471192235),
                getRecord("Lincoln, Nebraska", 11.5502347377),
                getRecord("Little Rock, Arkansas", 4.6675101159),
                getRecord("Long Beach, California", 0.0947711428),
                getRecord("Los Angeles, California", 3.4543663323),
                getRecord("Lowell, Massachusetts", -0.7520369649),
                getRecord("Lubbock, Texas", 12.7992169045),
                getRecord("Madison, Wisconsin", 12.0669300709),
                getRecord("Manchester, New Hampshire", 1.9136971099),
                getRecord("McAllen, Texas", 23.5401289358),
                getRecord("McKinney, Texas", 128.0142160627),
                getRecord("Memphis, Tennessee", -1.9626593411),
                getRecord("Mesa, Arizona", 15.9621499654),
                getRecord("Mesquite, Texas", 6.7892594044),
                getRecord("Miami, Florida", 19.2630590594),
                getRecord("Miami Gardens, Florida", 8.5094980051),
                getRecord("Midland, Texas", 14.3825523136),
                getRecord("Milwaukee, Wisconsin", 1.333893868),
                getRecord("Minneapolis, Minnesota", 0.7405664693),
                getRecord("Miramar, Florida", 46.4388228666),
                getRecord("Mobile, Alabama", -2.8016742632),
                getRecord("Modesto, California", 6.6075287758),
                getRecord("Montgomery, Alabama", 0.2032580783),
                getRecord("Moreno Valley, California", 33.8540794103),
                getRecord("Murfreesboro, Tennessee", 48.4786474357),
                getRecord("Naperville, Illinois", 11.0766613832),
                getRecord("Newark, New Jersey", 2.0130929896),
                getRecord("New Haven, Connecticut", -0.3466414564),
                getRecord("New Orleans, Louisiana", -26.6328001108),
                getRecord("Newport News, Virginia", 7.1588255199),
                getRecord("New York, New York", 4.6976500584),
                getRecord("Norfolk, Virginia", -0.4076179745),
                getRecord("Norman, Oklahoma", 12.6429183751),
                getRecord("North Las Vegas, Nevada", 90.1842622729),
                getRecord("Norwalk, California", -1.9662598982),
                getRecord("Oakland, California", 2.1251690901),
                getRecord("Oceanside, California", 6.7652598104),
                getRecord("Odessa, Texas", 11.3590420538),
                getRecord("Oklahoma City, Oklahoma", 10.3932589804),
                getRecord("Olathe, Kansas", 29.9126544525),
                getRecord("Omaha, Nebraska", 10.9457191096),
                getRecord("Ontario, California", 8.1549689911),
                getRecord("Orange, California", 5.4409987608),
                getRecord("Orlando, Florida", 21.1259070577),
                getRecord("Overland Park, Kansas", 15.294156422),
                getRecord("Oxnard, California", 9.2772151456),
                getRecord("Palm Bay, Florida", 26.8433281005),
                getRecord("Palmdale, California", 22.5112313661),
                getRecord("Pasadena, California", 6.7838561023),
                getRecord("Pasadena, Texas", 2.6986855267),
                getRecord("Paterson, New Jersey", -2.3267184162),
                getRecord("Pembroke Pines, Florida", 6.0466865836),
                getRecord("Peoria, Arizona", 47.1167192429),
                getRecord("Peoria, Illinois", 2.2047634215),
                getRecord("Philadelphia, Pennsylvania", 2.212775796),
                getRecord("Phoenix, Arizona", 20.0951471631),
                getRecord("Pittsburgh, Pennsylvania", -6.6094700977),
                getRecord("Plano, Texas", 22.0456846678),
                getRecord("Pomona, California", 2.8728259695),
                getRecord("Pompano Beach, Florida", 2.275581604),
                getRecord("Portland, Oregon", 6.8351568721),
                getRecord("Port St. Lucie, Florida", 73.1539108495),
                getRecord("Providence, Rhode Island", -1.1227359787),
                getRecord("Provo, Utah", 13.3545956996),
                getRecord("Pueblo, Colorado", 2.6866928417),
                getRecord("Raleigh, North Carolina", 39.7775204698),
                getRecord("Rancho Cucamonga, California", 33.0470673874),
                getRecord("Reno, Nevada", 19.3849097422),
                getRecord("Richardson, Texas", 11.8334218311),
                getRecord("Richmond, California", 3.5502067692),
                getRecord("Richmond, Virginia", 3.3928047658),
                getRecord("Riverside, California", 15.2515023585),
                getRecord("Rochester, Minnesota", 15.789827019),
                getRecord("Rochester, New York", -5.5496323027),
                getRecord("Rockford, Illinois", 3.5922700987),
                getRecord("Roseville, California", 42.2876331521),
                getRecord("Round Rock, Texas", 73.7662765782),
                getRecord("Sacramento, California", 14.0375827774),
                getRecord("Salem, Oregon", 12.732216663),
                getRecord("Salinas, California", 0.5442622494),
                getRecord("Salt Lake City, Utah", 0.7278068424),
                getRecord("San Antonio, Texas", 18.0304599059),
                getRecord("San Bernardino, California", 4.968812659),
                getRecord("San Buenaventura, California", 3.1531843013),
                getRecord("San Diego, California", 6.4799267039),
                getRecord("San Francisco, California", 4.8880827416),
                getRecord("San Jose, California", 6.7683777143),
                getRecord("Santa Ana, California", 0.5412016331),
                getRecord("Santa Clara, California", 9.3848888542),
                getRecord("Santa Clarita, California", 8.7040892384),
                getRecord("Santa Rosa, California", 5.4878212171),
                getRecord("Savannah, Georgia", 1.4865211036),
                getRecord("Scottsdale, Arizona", 16.4275399564),
                getRecord("Seattle, Washington", 9.3131971381),
                getRecord("Shreveport, Louisiana", -0.6635921725),
                getRecord("Simi Valley, California", 7.9912121672),
                getRecord("Sioux Falls, South Dakota", 25.8045510279),
                getRecord("South Bend, Indiana", -3.8243246985),
                getRecord("Spokane, Washington", 3.2252167157),
                getRecord("Springfield, Illinois", 5.1650094444),
                getRecord("Springfield, Massachusetts", 2.4260179729),
                getRecord("Springfield, Missouri", 3.5465838101),
                getRecord("Stamford, Connecticut", 3.015755471),
                getRecord("Sterling Heights, Michigan", 2.0101066816),
                getRecord("St. Louis, Missouri", 2.7912621359),
                getRecord("Stockton, California", 17.5654406384),
                getRecord("St. Paul, Minnesota", -1.9245254069),
                getRecord("St. Petersburg, Florida", -1.7796913379),
                getRecord("Sunnyvale, California", 1.6210762672),
                getRecord("Syracuse, New York", -5.1413705758),
                getRecord("Tacoma, Washington", 2.9709404883),
                getRecord("Tallahassee, Florida", 12.8177973027),
                getRecord("Tampa, Florida", 13.0633192726),
                getRecord("Tempe, Arizona", 12.3750472114),
                getRecord("Thornton, Colorado", 41.2626547695),
                getRecord("Thousand Oaks, California", 5.1484609098),
                getRecord("Toledo, Ohio", 0.7931371955),
                getRecord("Topeka, Kansas", 0.7658891608),
                getRecord("Torrance, California", 1.509068154),
                getRecord("Tucson, Arizona", 11.1483483361),
                getRecord("Tulsa, Oklahoma", -0.8209240168),
                getRecord("Vallejo, California", -2.3329925017),
                getRecord("Vancouver, Washington", 13.0773534188),
                getRecord("Victorville, California", 71.8985850884),
                getRecord("Virginia Beach, Virginia", 1.6507476543),
                getRecord("Visalia, California", 27.7097975234),
                getRecord("Waco, Texas", 10.1154178335),
                getRecord("Warren, Michigan", -3.0580176109),
                getRecord("Washington, District of Columbia", 4.8820800918),
                getRecord("Waterbury, Connecticut", -0.1565543141),
                getRecord("West Covina, California", 0.1728690564),
                getRecord("West Jordan, Utah", 32.063460111),
                getRecord("Westminster, Colorado", 7.7299548083),
                getRecord("West Valley City, Utah", 14.5200714057),
                getRecord("Wichita Falls, Texas", -2.6949673454),
                getRecord("Wichita, Kansas", 5.62483327),
                getRecord("Wilmington, North Carolina", 12.905921016),
                getRecord("Winston-Salem, North Carolina", 13.6648235888),
                getRecord("Worcester, Massachusetts", 6.0185507246),
                getRecord("Yonkers, New York", 2.4691546776)
        };
    }
}