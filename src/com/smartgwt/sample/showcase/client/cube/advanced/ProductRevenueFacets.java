package com.smartgwt.sample.showcase.client.cube.advanced;

import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;

/**
 * Helper class to return the Product Revenue Facets
 */
public class ProductRevenueFacets {

    /**
     * @return the product revenue facets
     */
    public static Facet[] getProductRevenueFacets() {
        // Region
        Facet region = new Facet();
        region.setId("Regions");
        region.setTitle("Region");
        region.setIsTree(true);

        FacetValue allRegion = new FacetValue("sum", "All Regions");
        allRegion.setCollapsed(false);

        region.setValues(
                allRegion,
                new FacetValue("North", "North", "sum"),
                new FacetValue("South", "South", "sum"),
                new FacetValue("East", "East", "sum"),
                new FacetValue("West", "West", "sum")
        );

        // Scenarios
        Facet scenario = new Facet();
        scenario.setId("Scenarios");
        scenario.setTitle("Scenario");
        scenario.setValues(
                new FacetValue("Actual", "Actual"),
                new FacetValue("Budget", "Budget")
        );

        // Time
        Facet time = new Facet();
        time.setId("Time");
        time.setTitle("Time");
        time.setIsTree(true);
        time.setCollapsed(true);
        time.setWidth(150);

        FacetValue allYears = new FacetValue("sum", "All Years");
        allYears.setCollapsed(false);


        time.setValues(
                allYears,
                new FacetValue("2014", "2014", "sum"),
                new FacetValue("2015", "2015", "sum"),
                new FacetValue("2016", "2016", "sum") {{
                    setCollapsed(false);
                }},
                new FacetValue("Q1-2014", "Q1-2014", "2014"),
                new FacetValue("Q2-2014", "Q2-2014", "2014"),
                new FacetValue("Q3-2014", "Q3-2014", "2014"),
                new FacetValue("Q4-2014", "Q4-2014", "2014"),
                new FacetValue("Q1-2015", "Q1-2015", "2015"),
                new FacetValue("Q2-2015", "Q2-2015", "2015"),
                new FacetValue("Q3-2015", "Q3-2015", "2015"),
                new FacetValue("Q4-2015", "Q4-2015", "2015"),
                new FacetValue("Q1-2016", "Q1-2016", "2016"),
                new FacetValue("Q2-2016", "Q2-2016", "2016"),
                new FacetValue("Q3-2016", "Q3-2016", "2016"),
                new FacetValue("Q4-2016", "Q4-2016", "2016"),
                new FacetValue("1/1/2014", "1/1/2014", "Q1-2014"),
                new FacetValue("2/1/2014", "2/1/2014", "Q1-2014"),
                new FacetValue("3/1/2014", "3/1/2014", "Q1-2014"),
                new FacetValue("4/1/2014", "4/1/2014", "Q2-2014"),
                new FacetValue("5/1/2014", "5/1/2014", "Q2-2014"),
                new FacetValue("6/1/2014", "6/1/2014", "Q2-2014"),
                new FacetValue("7/1/2014", "7/1/2014", "Q3-2014"),
                new FacetValue("8/1/2014", "8/1/2014", "Q3-2014"),
                new FacetValue("9/1/2014", "9/1/2014", "Q3-2014"),
                new FacetValue("10/1/2014", "10/1/2014", "Q4-2014"),
                new FacetValue("11/1/2014", "11/1/2014", "Q4-2014"),
                new FacetValue("12/1/2014", "12/1/2014", "Q4-2014"),
                new FacetValue("1/1/2015", "1/1/2015", "Q1-2015"),
                new FacetValue("2/1/2015", "2/1/2015", "Q1-2015"),
                new FacetValue("3/1/2015", "3/1/2015", "Q1-2015"),
                new FacetValue("4/1/2015", "4/1/2015", "Q2-2015"),
                new FacetValue("5/1/2015", "5/1/2015", "Q2-2015"),
                new FacetValue("6/1/2015", "6/1/2015", "Q2-2015"),
                new FacetValue("7/1/2015", "7/1/2015", "Q3-2015"),
                new FacetValue("8/1/2015", "8/1/2015", "Q3-2015"),
                new FacetValue("9/1/2015", "9/1/2015", "Q3-2015"),
                new FacetValue("10/1/2015", "10/1/2015", "Q4-2015"),
                new FacetValue("11/1/2015", "11/1/2015", "Q4-2015"),
                new FacetValue("12/1/2015", "12/1/2015", "Q4-2015"),
                new FacetValue("1/1/2016", "1/1/2016", "Q1-2016"),
                new FacetValue("2/1/2016", "2/1/2016", "Q1-2016"),
                new FacetValue("3/1/2016", "3/1/2016", "Q1-2016"),
                new FacetValue("4/1/2016", "4/1/2016", "Q2-2016"),
                new FacetValue("5/1/2016", "5/1/2016", "Q2-2016"),
                new FacetValue("6/1/2016", "6/1/2016", "Q2-2016"),
                new FacetValue("7/1/2016", "7/1/2016", "Q3-2016"),
                new FacetValue("8/1/2016", "8/1/2016", "Q3-2016"),
                new FacetValue("9/1/2016", "9/1/2016", "Q3-2016"),
                new FacetValue("10/1/2016", "10/1/2016", "Q4-2016"),
                new FacetValue("11/1/2016", "11/1/2016", "Q4-2016"),
                new FacetValue("12/1/2016", "12/1/2016", "Q4-2016")
        );

        // Products
        Facet product = new Facet();
        product.setId("Products");
        product.setTitle("Product");
        product.setIsTree(true);
        product.setCollapsed(true);
        product.setWidth(175);

        product.setValues(
                new FacetValue("sum", "All Products") {{
                    setCollapsed(false);
                }},
                new FacetValue("ProdFamily1", "Office Paper Products", "sum"),
                new FacetValue("ProdFamily2", "Office Filing and Storage", "sum"),
                new FacetValue("ProdFamily3", "Office Machines", "sum"),
                new FacetValue("ProdFamily4", "Computer Consumables", "sum") {{
                    setCollapsed(false);
                }},
                new FacetValue("ProdGroup1", "Photocopy / Lasercopy", "ProdFamily1") {{
                    setCollapsed(false);
                }},
                new FacetValue("ProdGroup2", "Post-it Products", "ProdFamily1"),
                new FacetValue("ProdGroup3", "Folders", "ProdFamily2"),
                new FacetValue("ProdGroup4", "Overhead Projectors", "ProdFamily3"),
                new FacetValue("ProdGroup5", "Printing Supplies", "ProdFamily4"),
                new FacetValue("ProdGroup6", "Storage / Accessories", "ProdFamily4"),
                new FacetValue("Prod01", "Copy Paper A4", "ProdGroup1"),
                new FacetValue("Prod02", "Copy Paper Letter", "ProdGroup1"),
                new FacetValue("Prod03", "Copy Paper Legal", "ProdGroup1"),
                new FacetValue("Prod04", "Paper Reflex A4", "ProdGroup1"),
                new FacetValue("Prod05", "Paper Reflex Letter", "ProdGroup1"),
                new FacetValue("Prod06", "Paper Reflex Legal", "ProdGroup1"),
                new FacetValue("Prod07", "Paper Optix A4", "ProdGroup1"),
                new FacetValue("Prod08", "Paper Optix Letter", "ProdGroup1"),
                new FacetValue("Prod09", "Paper Optix Legal", "ProdGroup1"),
                new FacetValue("Prod10", "Renew Recycled A4", "ProdGroup1"),
                new FacetValue("Prod11", "Renew Recycled Letter", "ProdGroup1"),
                new FacetValue("Prod12", "Renew Recycled Legal", "ProdGroup1"),
                new FacetValue("Prod13", "653 Neon Colors", "ProdGroup2"),
                new FacetValue("Prod14", "654 Blue", "ProdGroup2"),
                new FacetValue("Prod15", "654 Yellow", "ProdGroup2"),
                new FacetValue("Prod16", "654 Green", "ProdGroup2"),
                new FacetValue("Prod17", "654 Neon Yellow", "ProdGroup2"),
                new FacetValue("Prod18", "Manilla Legal Buff", "ProdGroup3"),
                new FacetValue("Prod19", "Manilla Legal Blue", "ProdGroup3"),
                new FacetValue("Prod20", "Manilla Legal Red", "ProdGroup3"),
                new FacetValue("Prod21", "Manilla Legal Green", "ProdGroup3"),
                new FacetValue("Prod22", "Manilla Legal Yellow", "ProdGroup3"),
                new FacetValue("Prod23", "OHP Deltascreen", "ProdGroup4"),
                new FacetValue("Prod24", "Label Copier 98x38", "ProdGroup5"),
                new FacetValue("Prod25", "Label Copier 63x25", "ProdGroup5"),
                new FacetValue("Prod26", "Label Laser 63x25", "ProdGroup5"),
                new FacetValue("Prod27", "Label Copier Avery", "ProdGroup5"),
                new FacetValue("Prod28", "Label Laser Avery", "ProdGroup5"),
                new FacetValue("Prod29", "Label Laser/Copier Avery", "ProdGroup5"),
                new FacetValue("Prod30", "Labels Inkjet J8666", "ProdGroup5"),
                new FacetValue("Prod31", "Labels L7675 Video", "ProdGroup5"),
                new FacetValue("Prod32", "Comp. Paper Bhs7GSM", "ProdGroup5"),
                new FacetValue("Prod33", "Comp. Paper 15x11", "ProdGroup5"),
                new FacetValue("Prod34", "InkJet Cartridge", "ProdGroup5"),
                new FacetValue("Prod35", "BubbleJet Cartridge", "ProdGroup5"),
                new FacetValue("Prod36", "Laserjet Cartidge", "ProdGroup5"),
                new FacetValue("Prod37", "Toner Canon C-f554", "ProdGroup5"),
                new FacetValue("Prod38", "OHP Film 3M pp2900", "ProdGroup5"),
                new FacetValue("Prod39", "OHP Film Blk/Clr", "ProdGroup5"),
                new FacetValue("Prod40", "OHP Film 3M (laser)", "ProdGroup5"),
                new FacetValue("Prod41", "Label Floppy 3.5", "ProdGroup5"),
                new FacetValue("Prod42", "Label CD", "ProdGroup5"),
                new FacetValue("Prod43", "Label Zip", "ProdGroup5"),
                new FacetValue("Prod44", "Diskette Box", "ProdGroup6"),
                new FacetValue("Prod45", "Label Holders", "ProdGroup6"),
                new FacetValue("Prod46", "Keyboard Cover", "ProdGroup6"),
                new FacetValue("Prod47", "Pen tidy", "ProdGroup6"),
                new FacetValue("Prod48", "Screen Filter 15''", "ProdGroup6"),
                new FacetValue("Prod49", "Screen Filter 18''", "ProdGroup6"),
                new FacetValue("Prod50", "Screen Mesh 15''", "ProdGroup6")
        );

        return new Facet[]{region, scenario, time, product};
    }
}
