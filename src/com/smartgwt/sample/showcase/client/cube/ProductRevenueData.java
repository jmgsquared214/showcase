package com.smartgwt.sample.showcase.client.cube;

/**
 * ProductRevenue data
 */
public class ProductRevenueData {

    private static ProductRevenue[] data = new ProductRevenue[]{
            new ProductRevenue("Q1, 2016", "January", "Western U.S.", "Pens", "Revenue", 10000, 25),
            new ProductRevenue("Q1, 2016", "January", "Western U.S.", "Chairs", "Revenue", 50000, 45),
            new ProductRevenue("Q1, 2016", "January", "Western U.S.", "Monitors", "Revenue", 120000, 49),

            new ProductRevenue("Q1, 2016", "January", "Western U.S.", "Pens", "Profit", 2000, 25),
            new ProductRevenue("Q1, 2016", "January", "Western U.S.", "Chairs", "Profit", 5000, 45),
            new ProductRevenue("Q1, 2016", "January", "Western U.S.", "Monitors", "Profit", 44000, 59, "over50"),

            new ProductRevenue("Q1, 2016", "January", "Midwest U.S.", "Pens", "Revenue", 8000, 20),
            new ProductRevenue("Q1, 2016", "January", "Midwest U.S.", "Chairs", "Revenue", 22000, 20),
            new ProductRevenue("Q1, 2016", "January", "Midwest U.S.", "Monitors", "Revenue", 20000, 8, "under10"),

            new ProductRevenue("Q1, 2016", "January", "Midwest U.S.", "Pens", "Profit", 2000, 25),
            new ProductRevenue("Q1, 2016", "January", "Midwest U.S.", "Chairs", "Profit", 2000, 18),
            new ProductRevenue("Q1, 2016", "January", "Midwest U.S.", "Monitors", "Profit", 5000, 7, "under10"),

            new ProductRevenue("Q1, 2016", "January", "Eastern U.S.", "Pens", "Revenue", 22000, 55, "over50"),
            new ProductRevenue("Q1, 2016", "January", "Eastern U.S.", "Chairs", "Revenue", 40000, 36),
            new ProductRevenue("Q1, 2016", "January", "Eastern U.S.", "Monitors", "Revenue", 105000, 43),

            new ProductRevenue("Q1, 2016", "January", "Eastern U.S.", "Pens", "Profit", 4000, 50, "over50"),
            new ProductRevenue("Q1, 2016", "January", "Eastern U.S.", "Chairs", "Profit", 4000, 36),
            new ProductRevenue("Q1, 2016", "January", "Eastern U.S.", "Monitors", "Profit", 25000, 34),

            new ProductRevenue("Q1, 2016", "February", "Western U.S.", "Pens", "Revenue", 12000, 23),
            new ProductRevenue("Q1, 2016", "February", "Western U.S.", "Chairs", "Revenue", 42000, 47),
            new ProductRevenue("Q1, 2016", "February", "Western U.S.", "Monitors", "Revenue", 160000, 40),

            new ProductRevenue("Q1, 2016", "February", "Western U.S.", "Pens", "Profit", 4000, 23),
            new ProductRevenue("Q1, 2016", "February", "Western U.S.", "Chairs", "Profit", 4000, 47),
            new ProductRevenue("Q1, 2016", "February", "Western U.S.", "Monitors", "Profit", 68000, 40),

            new ProductRevenue("Q1, 2016", "February", "Midwest U.S.", "Pens", "Revenue", 10000, 19),
            new ProductRevenue("Q1, 2016", "February", "Midwest U.S.", "Chairs", "Revenue", 12000, 13),
            new ProductRevenue("Q1, 2016", "February", "Midwest U.S.", "Monitors", "Revenue", 75000, 19),

            new ProductRevenue("Q1, 2016", "February", "Midwest U.S.", "Pens", "Profit", 3000, 20),
            new ProductRevenue("Q1, 2016", "February", "Midwest U.S.", "Chairs", "Profit", 1000, 11),
            new ProductRevenue("Q1, 2016", "February", "Midwest U.S.", "Monitors", "Profit", 32000, 17),

            new ProductRevenue("Q1, 2016", "February", "Eastern U.S.", "Pens", "Revenue", 31000, 58, "over50"),
            new ProductRevenue("Q1, 2016", "February", "Eastern U.S.", "Chairs", "Revenue", 35000, 39),
            new ProductRevenue("Q1, 2016", "February", "Eastern U.S.", "Monitors", "Revenue", 164000, 41),

            new ProductRevenue("Q1, 2016", "February", "Eastern U.S.", "Pens", "Profit", 8000, 53, "over50"),
            new ProductRevenue("Q1, 2016", "February", "Eastern U.S.", "Chairs", "Profit", 4000, 44),
            new ProductRevenue("Q1, 2016", "February", "Eastern U.S.", "Monitors", "Profit", 88000, 47),

            new ProductRevenue("Q1, 2016", "March", "Western U.S.", "Pens", "Revenue", 18000, 26),
            new ProductRevenue("Q1, 2016", "March", "Western U.S.", "Chairs", "Revenue", 25000, 54, "over50"),
            new ProductRevenue("Q1, 2016", "March", "Western U.S.", "Monitors", "Revenue", 220000, 40),

            new ProductRevenue("Q1, 2016", "March", "Western U.S.", "Pens", "Profit", 9000, 29),
            new ProductRevenue("Q1, 2016", "March", "Western U.S.", "Chairs", "Profit", 2000, 40),
            new ProductRevenue("Q1, 2016", "March", "Western U.S.", "Monitors", "Profit", 112000, 38),

            new ProductRevenue("Q1, 2016", "March", "Midwest U.S.", "Pens", "Revenue", 7000, 10),
            new ProductRevenue("Q1, 2016", "March", "Midwest U.S.", "Chairs", "Revenue", 6000, 13),
            new ProductRevenue("Q1, 2016", "March", "Midwest U.S.", "Monitors", "Revenue", 135000, 25),

            new ProductRevenue("Q1, 2016", "March", "Midwest U.S.", "Pens", "Profit", 2000, 6, "under10"),
            new ProductRevenue("Q1, 2016", "March", "Midwest U.S.", "Chairs", "Profit", 1000, 20),
            new ProductRevenue("Q1, 2016", "March", "Midwest U.S.", "Monitors", "Profit", 66000, 23),

            new ProductRevenue("Q1, 2016", "March", "Eastern U.S.", "Pens", "Revenue", 44000, 64, "over50"),
            new ProductRevenue("Q1, 2016", "March", "Eastern U.S.", "Chairs", "Revenue", 15000, 33),
            new ProductRevenue("Q1, 2016", "March", "Eastern U.S.", "Monitors", "Revenue", 190000, 35),

            new ProductRevenue("Q1, 2016", "March", "Eastern U.S.", "Pens", "Profit", 20000, 65, "over50"),
            new ProductRevenue("Q1, 2016", "March", "Eastern U.S.", "Chairs", "Profit", 2000, 40),
            new ProductRevenue("Q1, 2016", "March", "Eastern U.S.", "Monitors", "Profit", 115000, 39)
    };

    public static ProductRevenue[] getData() {
        return data;
    }
}
