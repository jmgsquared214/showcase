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

package com.smartgwt.sample.showcase.client.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartgwt.client.util.SC;
import com.smartgwt.sample.showcase.client.Showcase;
import com.smartgwt.sample.showcase.client.ShowcaseMessages;
import com.smartgwt.sample.showcase.client.chart.AddingElementsChart;
import com.smartgwt.sample.showcase.client.chart.BubbleChart;
import com.smartgwt.sample.showcase.client.chart.ColorScaleChart;
import com.smartgwt.sample.showcase.client.chart.CustomHovers;
import com.smartgwt.sample.showcase.client.chart.DataPointsChart;
import com.smartgwt.sample.showcase.client.chart.DrillChart;
import com.smartgwt.sample.showcase.client.chart.DynamicDataChart;
import com.smartgwt.sample.showcase.client.chart.ErrorBarsChart;
import com.smartgwt.sample.showcase.client.chart.GridChart;
import com.smartgwt.sample.showcase.client.chart.LogScalingChart;
import com.smartgwt.sample.showcase.client.chart.MeanAndDeviationChart;
import com.smartgwt.sample.showcase.client.chart.MixedPlotsChart;
import com.smartgwt.sample.showcase.client.chart.MultiSeriesChart;
import com.smartgwt.sample.showcase.client.chart.RegressionLinesChart;
import com.smartgwt.sample.showcase.client.chart.ScatterPlotChart;
import com.smartgwt.sample.showcase.client.chart.SimpleChart;
import com.smartgwt.sample.showcase.client.chart.HistogramChart;
import com.smartgwt.sample.showcase.client.chart.AutoScrollDataChart;
import com.smartgwt.sample.showcase.client.chart.CustomTicksChart;
import com.smartgwt.sample.showcase.client.chart.export.ImageExportSample;
import com.smartgwt.sample.showcase.client.chart.export.PDFExportSample;
import com.smartgwt.sample.showcase.client.chart.multiAxis.DualAxisChartMA;
import com.smartgwt.sample.showcase.client.chart.multiAxis.MultiSeriesChartMA;
import com.smartgwt.sample.showcase.client.chart.multiAxis.ThreePlusChartMA;
import com.smartgwt.sample.showcase.client.chart.zoom.CityPopulationZoom;
import com.smartgwt.sample.showcase.client.chart.zoom.MeasurementDataZoom;
import com.smartgwt.sample.showcase.client.chart.zoom.StockPricesZoom;
import com.smartgwt.sample.showcase.client.componentXML.AddingHandlers;
import com.smartgwt.sample.showcase.client.componentXML.CompleteApplication;
import com.smartgwt.sample.showcase.client.componentXML.VariousControls;
import com.smartgwt.sample.showcase.client.componentXML.CustomComponents;
import com.smartgwt.sample.showcase.client.componentXML.ReplacePlaceholder;
import com.smartgwt.sample.showcase.client.componentXML.ScreenReuse;
import com.smartgwt.sample.showcase.client.componentXML.EnabledVisibilityRules;
import com.smartgwt.sample.showcase.client.componentXML.FormRules;
import com.smartgwt.sample.showcase.client.componentXML.HelloWorld;
import com.smartgwt.sample.showcase.client.cube.BasicCubeSample;
import com.smartgwt.sample.showcase.client.cube.advanced.AdvancedCubeSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.auditing.DataSourceAuditingSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.crud.DmiCrudSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.crud.HibernateProductionCrudSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.datasource.ORMDataSourceSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.datasource.ReusableORMDataSourceSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.datasource.SimpleCustomDataSourceSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.datasource.EditableServerSideDataSourceSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.file.MultiUploadSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.form.BlockingValidationSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.form.DMIValidationSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.form.FormValidationSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.form.InlineScriptValidationSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.form.RelatedValidationSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.form.UniqueValidationSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.form.VelocityValidationSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.AutoDeriveHibernateSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.BasicConnectorHibernateSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.FlattenedDataModelSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.JavaBeansGridSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.MasterDetailHibernateSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.ServerAdvancedFilteringHibernateSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.relations.HBIncludeFrom;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.relations.HBIncludeFromDynamic;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.relations.HBRelationManyToOneSimple;
import com.smartgwt.sample.showcase.client.dataintegration.java.hibernate.relations.HBRelationOneToMany;
import com.smartgwt.sample.showcase.client.dataintegration.java.jpa.JPASample;
import com.smartgwt.sample.showcase.client.dataintegration.java.jpa.ServerAdvancedFilteringJPASample;
import com.smartgwt.sample.showcase.client.dataintegration.java.jpa.relations.JPAIncludeFrom;
import com.smartgwt.sample.showcase.client.dataintegration.java.jpa.relations.JPAIncludeFromDynamic;
import com.smartgwt.sample.showcase.client.dataintegration.java.jpa.relations.JPARelationManyToOneSimple;
import com.smartgwt.sample.showcase.client.dataintegration.java.jpa.relations.JPARelationOneToMany;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.BatchUploaderSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.CustomExportCustomResponseSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.CustomExportSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.DrawingExportSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.ExcelExportSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.FormattedExportBuiltinSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.FormattedExportSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.PDFExportContentSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.others.ServerFormattedExportSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.AdaptiveFilterSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.AdaptiveSortSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.BasicConnectorSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.DynamicReportingSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.LargeValueMapSQLSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.LiveGridFetchSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.ServerAdvancedFilteringSQLSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.UploadSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.UserSpecificDataSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.relations.SQLIncludeFrom;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.relations.SQLIncludeFromDynamic;
import com.smartgwt.sample.showcase.client.dataintegration.java.sql.relations.SQLIncludeVia;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.AutomaticTransactionManagementSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.GridMassUpdateSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.JDBCOperationsSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.ManyToManyDragSaveSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.MultiRowDragSaveSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.QueuedMasterDetailAddSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.RollbackSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.transactions.SimpleQueuingSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.tree.TreeBindingSQLSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.tree.TreeInitialDataSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.tree.TreeReparentSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.uploadDownload.CustomBinaryFieldSample;
import com.smartgwt.sample.showcase.client.dataintegration.java.uploadDownload.customDownload.CustomDownloadSample;
import com.smartgwt.sample.showcase.client.doc.GettingStartedPanel;
import com.smartgwt.sample.showcase.client.doc.IntroductionPanel;
import com.smartgwt.sample.showcase.client.doc.NoteSgwtEESgwtLgplPanel;
import com.smartgwt.sample.showcase.client.draganddrop.Copy;
import com.smartgwt.sample.showcase.client.draganddrop.PersistentReorderableListGrid;
import com.smartgwt.sample.showcase.client.draganddrop.PersistentReorderableTreeGrid;
import com.smartgwt.sample.showcase.client.draganddrop.RecategorizeList;
import com.smartgwt.sample.showcase.client.draganddrop.RecategorizeTile;
import com.smartgwt.sample.showcase.client.draganddrop.RecategorizeTree;
import com.smartgwt.sample.showcase.client.draganddrop.TreeReparent;
import com.smartgwt.sample.showcase.client.largetrees.LoadOnDemandSample;
import com.smartgwt.sample.showcase.client.largetrees.MultiLevelChildPagingSample;
import com.smartgwt.sample.showcase.client.largetrees.MultiLevelLoadOnDemandSample;
import com.smartgwt.sample.showcase.client.largetrees.PagingForChildrenSample;
import com.smartgwt.sample.showcase.client.messaging.SimpleChatSample;
import com.smartgwt.sample.showcase.client.messaging.StockChartSample;
import com.smartgwt.sample.showcase.client.messaging.StockQuotesSample;
import com.smartgwt.sample.showcase.client.offline.OfflineDataSourceSupportSample;
import com.smartgwt.sample.showcase.client.portal.AutomaticPersistenceSample;
import com.smartgwt.sample.showcase.client.portal.CollageEditorSample;
import com.smartgwt.sample.showcase.client.portal.DataSourcePersistenceSample;
import com.smartgwt.sample.showcase.client.portal.DiagramEditorSample;
import com.smartgwt.sample.showcase.client.portal.FormBuilderSample;
import com.smartgwt.sample.showcase.client.portal.GridPortletSample;
import com.smartgwt.sample.showcase.client.portal.ListPaletteSample;
import com.smartgwt.sample.showcase.client.portal.MenuPaletteSample;
import com.smartgwt.sample.showcase.client.portal.MockupEditorSample;
import com.smartgwt.sample.showcase.client.portal.OfflinePersistenceSample;
import com.smartgwt.sample.showcase.client.portal.PortalDashboardSample;
import com.smartgwt.sample.showcase.client.portal.PortalLayoutSample;
import com.smartgwt.sample.showcase.client.portal.TilePaletteSample;
import com.smartgwt.sample.showcase.client.portal.TreePaletteSample;
import com.smartgwt.sample.showcase.client.tools.DataSourceConsoleCommand;
import com.smartgwt.sample.showcase.client.tools.DataSourceWizardEditorStubPanel;
import com.smartgwt.sample.showcase.client.tools.DatabaseWizardBrowserStubPanel;
import com.smartgwt.sample.showcase.client.tools.DebugConsoleCommand;
import com.smartgwt.sample.showcase.client.tools.VisualBuilderCommand;
import com.smartgwt.sample.showcase.client.webservice.RssSample;
import com.smartgwt.sample.showcase.client.webservice.WsdlDataBindingSample;
import com.smartgwt.sample.showcase.client.webservice.WsdlOperationSample;

public class ShowcaseData {
    private static final ShowcaseMessages M = ShowcaseMessages.INSTANCE;
    private String idSuffix;

    public ShowcaseData(String idSuffix) {
        this.idSuffix = idSuffix;
    }

    private List<ExplorerTreeNode> data;

    private ExplorerTreeNode[] getData() {
        if (data == null) {
            data = new ArrayList<ExplorerTreeNode>();
            final String licenseType = SC.getLicenseType();
            if (!"LGPL".equals(licenseType)) {
                data.addAll(Arrays.asList(new ExplorerTreeNode[] {
                        new ExplorerTreeNode(M.homeNodeName(Showcase.getSGWTProductName()).asString(), "main", "root", "silk/database_connect.png", null, true, idSuffix),
                        new ExplorerTreeNode("Introduction", "doc-introduction", "main", "silk/help.png", new IntroductionPanel.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Getting Started", "doc-getting-started", "main", "silk/help.png", new GettingStartedPanel.Factory(), true, idSuffix),

                        // New samples since previous release
                        // Note: this node is auto-populated with copies of each node tagged with the current release
                        new FolderTreeNode("New Samples in " + getNewSamplesSuffix(), "new-category", "main", "silk/new.png", true, idSuffix, true) {{
                            setDescription("");
                        }},
                        /*new   CommandTreeNode("New Samples in " + getNewSamplesSuffix(), "new-category", "main", "silk/new.png", new com.smartgwt.sample.showcase.client.SmartGWTCommand(), true, idSuffix),*/
                    
                        new FolderTreeNode("Cubes", "cubes", "root", "pieces/16/cube_blue.png", true, idSuffix) {{
                            setDescription(
                                    "Multidimensional \"cube\" data sets as used in BI, Analytics and OLAP applications. " +
                                    "Load-on-demand, drill-down, roll-up, in-browser dataset pivoting, multiple frozen panes, " +
                                    "resizing and reorder of fields, tree dimensions, chart generation, editing and other " +
                                    "features."
                            );
                        }},
                        new ExplorerTreeNode("Basic CubeGrid", "cube-basic", "cubes", "pieces/16/cube_green.png", new BasicCubeSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Advanced Cube Grid (Analytics)", "cube-analytics", "cubes", "pieces/16/cube_green.png", new AdvancedCubeSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Charting", "charts", "root", "silk/chart_bar.png", true, idSuffix) {{
                            setDescription(
                                    "Smart GWT supports advanced charting components that work in all supported browsers, " +
                                    "including mobile browsers, without requiring plugins and without writing browser-specific " +
                                    "code." +
                                    "<P>" +
                                    "Smart GWT charting components are data-aware, and allow end users to switch both the type " +
                                    "of chart and the placement of data on the fly."
                            );
                        }},
                        new ExplorerTreeNode("Simple Chart", "simpleChart", "charts", "silk/chart_curve.png", new SimpleChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Multi-Series Chart", "multiSeriesChart", "charts", "silk/chart_curve.png", new MultiSeriesChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Grid Charting", "gridCharting", "charts", "silk/chart_curve.png", new GridChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Dynamic Data", "dynamicDataCharting", "charts", "silk/chart_curve.png", new DynamicDataChart.Factory(), true, idSuffix),
                        
                        new FolderTreeNode("Multi-Axis", "multiAxischarts", "charts", "silk/chart_bar.png", true, idSuffix, false),
                        new ExplorerTreeNode("Dual Axis", "dualAxisChartMA", "multiAxischarts", "silk/chart_curve.png", new DualAxisChartMA.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Multi-Series", "multiSeriesChartMA", "multiAxischarts", "silk/chart_curve.png", new MultiSeriesChartMA.Factory(), true, idSuffix),
                        new ExplorerTreeNode("3+ Axes", "threePlusChartMA", "multiAxischarts", "silk/chart_curve.png", new ThreePlusChartMA.Factory(), true,idSuffix),

                        new ExplorerTreeNode("Mixed Plots", "mixedPlotsChart", "charts", "silk/chart_curve.png", new MixedPlotsChart.Factory(), true, idSuffix),

                        new FolderTreeNode("Export", "chart-export", "charts", "silk/arrow_out.png", true, idSuffix, false) {{
                            setDescription(
                                    "Exporting Charts and other widgets based on DrawPane (such as Gauge) as images."
                            );
                        }},
                        new ExplorerTreeNode("Chart Image Export", "chart-image-export", "chart-export", "silk/arrow_out.png", new ImageExportSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Chart PDF Export", "chart-pdf-export", "chart-export", "silk/printer.png", new PDFExportSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Drawing Export", "drawing-export", "chart-export", "silk/palette.png", new DrawingExportSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Zoom Charts", "zoomCharts", "charts", "silk/chart_bar.png", true, idSuffix, false) {{
                            setDescription(
                                    "These examples show charts that allow users to intuitively " +
                                    "navigate very large datasets by providing the ability to zoom into parts of the data." +
                                    "<p>" +
                                    "These charts intelligently choose which data labels to show based on whether the data set " +
                                    "covers a date range, a numeric range, or is simply a large set of unrelated values."
                            );
                        }},
                        new ExplorerTreeNode("Stock Prices", "stockPricesZoom", "zoomCharts", "silk/chart_curve.png", new StockPricesZoom.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Measurement Data", "measurementDataZoom", "zoomCharts", "silk/chart_curve.png", new MeasurementDataZoom.Factory(), true, idSuffix),
                        new ExplorerTreeNode("City Population", "cityPopulationZoom", "zoomCharts", "silk/chart_curve.png", new CityPopulationZoom.Factory(), true, idSuffix),

                        new ExplorerTreeNode("Histogram Chart", "histogramChart", "charts", "silk/chart_bar.png", new HistogramChart.Factory(), true, idSuffix, "6.1"),
                        new ExplorerTreeNode("Scrolling Chart", "autoScrollDataChart", "charts", "silk/chart_bar.png", new AutoScrollDataChart.Factory(), true, idSuffix, "6.1"),
                        new ExplorerTreeNode("Custom Date Ticks", "customTicksChart", "charts", "silk/chart_curve.png", new CustomTicksChart.Factory(), true, idSuffix, "6.1"),

                        new FolderTreeNode("Scatter Plots", "scatterPlots", "charts", "silk/chart_bar.png", true, idSuffix, false) {{
                            setDescription(
                                    "Scatter Plots, Bubble Charts and Color charts allow anywhere from 2 to "+
                                    "4 continuous data values to be shown in a single plot, by representing data values as X "+
                                    "axis position, Y axis position, shape size and shape color."
                            );
                        }},
                        new ExplorerTreeNode("Scatter Plot", "scatterPlotCharting", "scatterPlots", "silk/chart_line.png", new ScatterPlotChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Bubble Chart", "bubbleChart", "scatterPlots", "silk/chart_line.png", new BubbleChart.Factory(), true, idSuffix, "5.0"),
                        new ExplorerTreeNode("Color Scale Chart", "colorScaleChart", "scatterPlots", "silk/chart_line.png", new ColorScaleChart.Factory(), true, idSuffix, "5.0"),
                        new ExplorerTreeNode("Curve Fitting", "SPregressionLines", "scatterPlots", "silk/chart_curve.png", new RegressionLinesChart.Factory(), true, idSuffix),
                        
                        new FolderTreeNode("Statistics", "statistics", "charts", "silk/chart_bar.png", true, idSuffix, false) {{
                            setDescription(
                                    "Scatter Charts support calculation of best-fit lines or curves, also known as \"trend " +
                                    "lines\" for time series data.  Shown below is a 3rd-degree polynomial regression curve." +
                                    "<P>" +
                                    "Use the controls above the chart to switch between linear or polynomial regression and " +
                                    "change the regression degree.  Users can also enable or disable regression lines via the " +
                                    "default context menu."
                            );
                        }},
                        new ExplorerTreeNode("Curve Fitting", "regressionLines", "statistics", "silk/chart_curve.png", new RegressionLinesChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Mean &amp; Deviation", "meanAndDeviation", "statistics", "silk/chart_line.png", new MeanAndDeviationChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Error Bars", "errorBars", "statistics", "silk/chart_line.png", new ErrorBarsChart.Factory(), true, idSuffix),

                        new ExplorerTreeNode("Drill Up/Down", "drillCharting", "charts", "silk/chart_bar.png", new DrillChart.Factory(), true, idSuffix, "4.1"),
                        new ExplorerTreeNode("Log Scaling Chart", "logScalingChart", "charts", "silk/chart_line.png", new LogScalingChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Interactive Data Points", "dataPointsChart", "charts", "silk/chart_line.png", new DataPointsChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Adding New Elements", "addingNewElements", "charts", "silk/chart_bar.png", new AddingElementsChart.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Custom Hovers", "chartCustomHovers", "charts", "silk/chart_bar.png", new CustomHovers.Factory(), true, idSuffix),

                        new FolderTreeNode("Dashboards &amp; Tools", "portal", "root", "silk/world.png", true, idSuffix, 
true) {{
                            setDescription(
                                    "The Dashboards &amp; Tools framework provides a set of components for building customizable " +
                                    "user interfaces and tools. Examples include portals that can persist layout, report builders "+
                                    "that allow end users to arrange data into a sharable \"dashboard\", diagramming or flow charting "+
                                    "tools, and tools for UI creation such as form designers."+
                                    "<P>"+
                                    "The components in the Dashboards &amp; Tools framework are the foundation on which Visual Builder was "+
                                    "created, and many of the features of Visual Builder can achieved by simply creating and configuring "+
                                    "components in the Dashboards &amp; Tools framework."
                            );
                        }},

                        new FolderTreeNode("Palettes", "palettes", "portal", "silk/palette.png", true, idSuffix, false),
                        new ExplorerTreeNode("Tree Palette", "treePalette", "palettes", "silk/chart_organisation.png", new TreePaletteSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("List Palette", "listPalette", "palettes", "silk/application_view_columns.png", new ListPaletteSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Tile Palette", "tilePalette", "palettes", "gears.png", new TilePaletteSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Menu Palette", "menuPalette", "palettes", "silk/combo_box.gif", new MenuPaletteSample.Factory(), true, idSuffix),
                        new FolderTreeNode("Edit Contexts", "editContexts", "portal", "silk/vcard_edit.png", true, idSuffix, true),
                        new ExplorerTreeNode("Portal Layout", "portalLayout", "editContexts", "silk/application_view_tile.png", new PortalLayoutSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Grid Portlets", "gridPortlets", "editContexts", "silk/application_view_tile.png", new GridPortletSample.Factory(), true, idSuffix, "5.0"),
                        new ExplorerTreeNode("Form Builder", "formBuilder", "editContexts", "silk/vcard_edit.png", new FormBuilderSample.Factory(), true, idSuffix, "5.0"),
                        new ExplorerTreeNode("Diagramming", "diagramming", "editContexts", "silk/chart_organisation.png", new DiagramEditorSample.Factory(), true, idSuffix, "5.0"),
                        new ExplorerTreeNode("Mockup Editor", "mockupEditor", "editContexts", "silk/vcard_edit.png", new MockupEditorSample.Factory(), true, idSuffix, "5.0"),
                        new FolderTreeNode("Persistence", "portalPersistence", "portal", "silk/database_table.png", true, idSuffix, false),
                        new ExplorerTreeNode("Automatic", "automaticPersistence", "portalPersistence", "silk/server_lightning.png", new AutomaticPersistenceSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("DataSource", "dataSourcePersistence", "portalPersistence", "silk/database_table.png", new DataSourcePersistenceSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Offline", "offlinePersistence", "portalPersistence", "gears.png", new OfflinePersistenceSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Portal Dashboard", "portalDashboard", "portal", "silk/application_view_tile.png", new PortalDashboardSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Collage Editor", "collageEditor", "editContexts", "silk/palette.png", new CollageEditorSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Component XML", "component-xml", "root", "silk/script_code.png", true, idSuffix) {{
                            setDescription(
                                    "Component XML is a format for specifying UI components declaratively in XML." +
                                    "<P>" +
                                    "Using Component XML, you can separate the layout of your application from its " +
                                    "business logic, so that less technical users can edit the layout while Java " +
                                    "developers implement business logic." +
                                    "<P>" +
                                    "Component XML also allows visual tools such as Visual Builder to be used to modify " +
                                    "the layout of your applications."
                            );
                        }},
                        new ExplorerTreeNode("Hello World", "helloWorld", "component-xml", "silk/script_code.png", new HelloWorld.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Adding Handlers", "addingHandlers", "component-xml", "silk/script_code.png", new AddingHandlers.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Various Controls", "variousControls", "component-xml", "silk/script_code.png", new VariousControls.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Replace Placeholder", "replacePlaceholder", "component-xml", "silk/script_code.png", new ReplacePlaceholder.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Custom Components", "customComponents", "component-xml", "silk/script_code.png", new CustomComponents.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Screen Reuse", "screenReuse", "component-xml", "silk/script_code.png", new ScreenReuse.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Enabled &amp; Visibility Rules", "enabledVisibilityRules", "component-xml", "silk/script_code.png", new EnabledVisibilityRules.Factory(), true, idSuffix, "6.0"),
                        new ExplorerTreeNode("Form Rules", "formRules", "component-xml", "silk/script_code.png", new FormRules.Factory(), true, idSuffix, "6.0"),
                        new ExplorerTreeNode("Complete Application", "completeApplication", "component-xml", "silk/script_code.png", new CompleteApplication.Factory(), true, idSuffix),

                        new FolderTreeNode("Java Data Integration", "data-integration-java", "root", "silk/database_connect.png", true, idSuffix) {{
                            setDescription(
                                    "The Smart GWT Server framework is a collection of .jar files and optional servlets that work with " +
                                    "any J2EE or J2SE container and are easily integrated into existing applications.  Its major " +
                                    "features include:<ul> " +
                                    "<li><b>Simplified server integration:</b> A pre-built network protocol for browser-server " +
                                    "    communication, which handles data paging, transactions/batch operations, server-side " +
                                    "    sort, automatic cache updates, validation and other error handling, optimistic " +
                                    "    concurrency (aka long transactions) and binary file uploads.<P></li> " +
                                    "<li><b>SQL, JPA & Hibernate Connectors:</b> Secure, flexible, transactional support for all " +
                                    "    CRUD operations, either directly via JDBC or via Hibernate or JPA beans.<P></li> " +
                                    "<li><b>Rapid integration with Java Beans:</b> Robust, complete, bi-directional translation " +
                                    "    between Java and Javascript objects for rapid integration with any Java beans-based " +
                                    "    persistence system, such as Spring services or custom ORM implementations.  Send and " +
                                    "    receive complex structures including Java Enums and Java Generics without the need to " +
                                    "    write mapping or validation code.  Declaratively trim and rearrange data so that only " +
                                    "    selected data is sent to the client <b>without</b> the need to create and populate " +
                                    "    redundant DTOs (data transfer objects).<P></li> " +
                                    "<li><b>Server enforcement of Validators:</b> A single file specifies validation rules " +
                                    "    which are enforced on both the client and server side<P></li> " +
                                    "<li><b>Declarative Security:</b> Easily attach role or capability-based security rules to " +
                                    "    data operations with server-side enforcement, plus automatic client-side effects such as " +
                                    "    hiding fields or showing fields as read-only based on the user role.<P></li> " +
                                    "<li><b>Export:</b> Export any dataset to CSV or true Excel spreadsheets, including data " +
                                    "    highlights and formatting rules<br><br></li> " +
                                    "<li><b>High speed data delivery / data compression:</b> automatically use the fastest  " +
                                    "    possible mechanism for delivering data to the browser<br></li> " +
                                    "</ul> " +
                                    "The Smart GWT Server framework is an optional, commercially-licensed package.  See the  " +
                                    "<a href=http://www.smartclient.com/product/index.jsp>products page</a> for details. "
                            );
                        }},
                        
                        new ExplorerTreeNode("Adaptive Filter", "adaptive-filter", "data-integration-java", "iconexperience/funnel.png", null, true, new AdaptiveFilterSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Adaptive Sort", "adaptive-sort", "data-integration-java", "silk/table_relationship.png", new AdaptiveSortSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Validation", "validation", "data-integration-java", "silk/server_lightning.png", true, idSuffix) {{
                            setDescription(
                                    "The Smart GWT Server provides powerful support for server-based validation."
                            );
                        }},
                        new ExplorerTreeNode("Single Source", "validation-form", "validation", "silk/table_row_delete.png", new FormValidationSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("DMI Validation", "validation-dmi", "validation", "silk/table_row_delete.png", new DMIValidationSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Inline Script Validation", "validation-script", "validation", "silk/table_row_delete.png", new InlineScriptValidationSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Velocity Expression", "validation-velocity", "validation", "silk/table_row_delete.png", new VelocityValidationSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Unique Check", "validation-unique", "validation", "silk/table_row_delete.png", new UniqueValidationSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Related Records", "validation-related", "validation", "silk/table_row_delete.png", new RelatedValidationSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Blocking Errors", "validation-blocking", "validation", "silk/table_row_delete.png", new BlockingValidationSample.Factory(), true, idSuffix),

                        new FolderTreeNode("SQL", "sql", "data-integration-java", "silk/database_table.png", true, idSuffix) {{
                            setDescription(
                                    "The Smart GWT Server provides powerful built-in support for codeless connection to mainstream SQL databases."
                            );
                        }},
                        new ExplorerTreeNode("DataBase Browser", "sql-db-browser-wizard", "sql", "silk/database_gear.png", new DatabaseWizardBrowserStubPanel.Factory(), true, idSuffix),
                        new ExplorerTreeNode("DataSource Editor", "sql-db-editor-wizard", "sql", "silk/database_gear.png", new DataSourceWizardEditorStubPanel.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Basic Connector", "sql-basic-connector", "sql", "silk/database_gear.png", new BasicConnectorSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Server Advanced Filter", "filterbuilder-sql", "sql", "iconexperience/funnel.png", null, true, new ServerAdvancedFilteringSQLSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Large Value Map", "large-valuemap-sql", "sql", "silk/table_relationship.png", new LargeValueMapSQLSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("User Specific Data", "sql-user-specific-data", "sql", "silk/user_orange.png", new UserSpecificDataSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Dynamic Reporting", "sql-dynamic-reporting", "sql", "silk/table_multiple.png", new DynamicReportingSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Editable Live Grid", "livegrid-sql", "sql", "silk/application_put.png", new LiveGridFetchSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Load Tree on Demand", "load-tree-sql", "sql", "silk/chart_organisation.png", new TreeBindingSQLSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Tree Initial Data &amp; Load on Demand", "load-tree-initial-data", "sql", "silk/chart_organisation.png", new TreeInitialDataSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Tree Reparent", "tree-reparent-sql", "sql", "silk/chart_organisation.png", new TreeReparentSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("SQL Field Include", "sqlIncludeFrom", "sql", "silk/disconnect.png", new SQLIncludeFrom.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Multiple Field Include", "sqlIncludeVia", "sql", "silk/disconnect.png", new SQLIncludeVia.Factory(), true, idSuffix),
                        new ExplorerTreeNode("SQL Dynamic Field Include", "sqlIncludeFromDynamic", "sql", "silk/disconnect.png", new SQLIncludeFromDynamic.Factory(), true, idSuffix),

                        new FolderTreeNode("Hibernate / Beans", "hibernate", "data-integration-java", "iconexperience/coffeebean.png", true, idSuffix, false) {{
                            setDescription(
                                "The Smart GWT Server provides powerful built-in support for Hibernate"
                            );
                        }},
                        // Replaced by auto-derivation sample.
                        //new ExplorerTreeNode("Hibernate Wizard", "hibernate-wizard", "hibernate", "iconexperience/coffeebean.png", new HibernateWizardStubPanel.Factory(), true, idSuffix),
                        new ExplorerTreeNode("HB Auto Derivation", "hibernateAutoDerivation", "hibernate", "iconexperience/coffeebean.png", new AutoDeriveHibernateSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Beanless Mode", "hibernate-connector", "hibernate", "iconexperience/coffeebean.png", new BasicConnectorHibernateSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("HB Advanced Filtering", "filterbuilder-hibernate", "hibernate", "iconexperience/funnel.png", new ServerAdvancedFilteringHibernateSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("HB Many-to-One Relation", "hbRelationManyToOneSimple", "hibernate", "iconexperience/coffeebean.png", new HBRelationManyToOneSimple.Factory(), true, idSuffix),
                        new ExplorerTreeNode("HB One-to-Many Relation", "hbRelationOneToMany", "hibernate", "iconexperience/coffeebean.png", new HBRelationOneToMany.Factory(), true, idSuffix),
                        new ExplorerTreeNode("HB Field Include", "hbIncludeFrom", "hibernate", "silk/disconnect.png", new HBIncludeFrom.Factory(), true, idSuffix),
                        new ExplorerTreeNode("HB Dynamic Field Include", "hbIncludeFromDynamic", "hibernate", "silk/disconnect.png", new HBIncludeFromDynamic.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Master-Detail (Batch Load &amp; Save)", "master-detail-batch", "hibernate", "silk/table_multiple.png", new MasterDetailHibernateSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Data Selection", "data-selection", "hibernate", "iconexperience/branch.png", new FlattenedDataModelSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Spring with Beans", "hibernate-spring", "hibernate", "silk/database_save.png", new HibernateProductionCrudSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("JavaBeans", "javabeans", "hibernate", "iconexperience/coffeebean.png", new JavaBeansGridSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Direct Method Invocation (DMI)", "dmi", "hibernate", "silk/database_edit.png", new DmiCrudSample.Factory(), true, idSuffix),

                        new FolderTreeNode("JPA", "jpa", "data-integration-java", "silk/cup.png", true, idSuffix, false) {{
                            setDescription(
                                    "The Smart GWT Server's built-in support for JPA/JPA2 allows you to easily use your JPA annotated entities in Smart GWT's client-side widgets."
                            );
                        }},
                        new ExplorerTreeNode("JPA Auto Derivation", "jpa1-connector", "jpa", "iconexperience/coffeebean.png", new JPASample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("JPA Advanced Filtering", "filterbuilder-jpa", "jpa", "iconexperience/funnel.png", new ServerAdvancedFilteringJPASample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("JPA Many-to-One Relation", "jpaRelationManyToOneSimple", "jpa", "iconexperience/coffeebean.png", new JPARelationManyToOneSimple.Factory(), true, idSuffix),
                        new ExplorerTreeNode("JPA One-to-Many Relation", "jpaRelationOneToMany", "jpa", "iconexperience/coffeebean.png", new JPARelationOneToMany.Factory(), true, idSuffix),
                        new ExplorerTreeNode("JPA Field Include", "jpaIncludeFrom", "jpa", "silk/disconnect.png", new JPAIncludeFrom.Factory(), true, idSuffix),
                        new ExplorerTreeNode("JPA Dynamic Field Include", "jpaIncludeFromDynamic", "jpa", "silk/disconnect.png", new JPAIncludeFromDynamic.Factory(), true, idSuffix),

                        new FolderTreeNode("Custom DataSources", "custom-ds", "data-integration-java", "silk/database_edit.png", true, idSuffix) {{
                            setDescription(
                                    "Examples showing how to leverage the Smart GWT Server to create partially or completely customized DataSource implementations."
                            );
                        }},
                        new ExplorerTreeNode("Simple", "simple-custom-ds", "custom-ds", "silk/table_row_insert.png", new SimpleCustomDataSourceSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("ORM DataSource", "orm-ds", "custom-ds", "iconexperience/objects_exchange.png", new ORMDataSourceSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Reusable ORM DataSource", "reusable-orm-ds", "custom-ds", "iconexperience/objects_exchange.png", new ReusableORMDataSourceSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Editable Server-Side DataSource", "editable-serverside-datasource", "custom-ds", 
                        null, new EditableServerSideDataSourceSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Transactions", "transactions", "data-integration-java", "iconexperience/branch.png", true, idSuffix) {{
                            setDescription(
                                    "Smart GWT provides robust support for transactional applications." +
                                    "<P>" +
                                    "Queueing makes combining operations together into a single " +
                                    "transaction extremely easy, for more efficient data loading and transactional saves." +
                                    "<P>" +
                                    "Automatic Transaction Management support in the Smart GWT Server, with " +
                                    "specific implementations for the built-in SQL and Hibernate DataSources, allows " +
                                    "for queued requests to be committed or rolled back as a single database transaction. " +
                                    "This feature is only available in Power and Enterprise editions." +
                                    "<P>" +
                                    "Transaction Chaining allows for declarative handling of data dependencies " +
                                    "between operations submitted together in a queue.  This feature is only available " +
                                    "in Power and Enterprise editions."
                            );
                        }},
                        new ExplorerTreeNode("Simple Queuing", "transactions-queuing", "transactions", "gears.png", new SimpleQueuingSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Automatic Transaction Management", "autoTransactions", "transactions", "silk/database_gear.png", new AutomaticTransactionManagementSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Queued Master / Detail Add", "transactions-queued-md", "transactions", "silk/table_row_insert.png", new QueuedMasterDetailAddSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Mass Update", "mass-update", "transactions", "silk/arrow_out.png", new GridMassUpdateSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Multi-Row Drag &amp; Save", "row-drag-save", "transactions", "silk/database_link.png", new MultiRowDragSaveSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Many-to-Many Drag &amp; Save", "row-drag-save-pivot", "transactions", "silk/database_link.png", new ManyToManyDragSaveSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Rollback", "rollback", "transactions", "silk/arrow_undo.png", new RollbackSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Transactional User Operations", "jdbcOperations", "transactions", "silk/user_go.png", new JDBCOperationsSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Export", "export", "data-integration-java", "silk/arrow_out.png", true, idSuffix) {{
                            setDescription(
                                    "Exporting Data from DataSources and DataBoundComponents."
                            );
                        }},
                        new ExplorerTreeNode("Excel Export", "excel-export", "export", "silk/page_white_excel.png", new ExcelExportSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Formatted Export (Declared Formats)", "formatted-export-builtin", "export", "silk/page_white_excel.png", new FormattedExportBuiltinSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Formatted Export (Custom formatting)", "formatted-export", "export", "silk/page_white_excel.png", new FormattedExportSample.Factory(), true, idSuffix, "4.1"),
                        new ExplorerTreeNode("Server-Side Formatted Export", "server-formatted-export", "export", "silk/page_white_excel.png", new ServerFormattedExportSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("PDF Export", "pdf-export", "export", "silk/printer.png", new PDFExportContentSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Custom Export", "custom-export", "export", "silk/page_white_excel.png", new CustomExportSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Custom Export (Custom Response)", "custom-export-custom-response", "export", "silk/arrow_out.png", new CustomExportCustomResponseSample.Factory(), true, idSuffix),

                        new ExplorerTreeNode("Auditing", "auditing", "data-integration-java", "silk/application_side_boxes.png", new DataSourceAuditingSample.Factory(), true, idSuffix, "4.1"),
                        
                        new FolderTreeNode("Upload / Download", "upload-download", "data-integration-java", "silk/application_get.png", true, idSuffix, false) {{
                            setDescription(
                                    "Samples with Upload and Download files"
                            );
                        }},
                        new ExplorerTreeNode("File Upload", "upload-sql", "upload-download", "silk/application_get.png", new UploadSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Batch Data Upload", "batch-uploader", "upload-download", "silk/page_white_excel.png", new BatchUploaderSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Multi File", "multi-file", "upload-download", "silk/application_get.png", new MultiUploadSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Custom Download", "custom-download", "upload-download", "silk/application_put.png", new CustomDownloadSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Custom Binary Field", "custom-binary-field", "upload-download", "silk/database_save.png", new CustomBinaryFieldSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Server Scripting", "scripting", "data-integration-java", "silk/sc_insertformula.png", true, idSuffix) {{
                            setDescription(
                                    "Simple business logic and validation rules can be embedded directly in *.ds.xml files. Use Java, or scripting languages such as Groovy or JavaScript."
                            );
                        }},
                        new ExplorerTreeNode("User Specific Data", "scripting-user-specific-data", "scripting", "silk/user_orange.png", new com.smartgwt.sample.showcase.client.dataintegration.java.scripting.ScriptingUserSpecificDataSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Validation", "scripting-validation", "scripting", "silk/table_row_delete.png", new InlineScriptValidationSample.Factory(), true, idSuffix),
                        
                        new ExplorerTreeNode("HTTP Proxy", "http-proxy", "data-integration-java", "silk/feed.png", new RssSample.Factory(), true, idSuffix),

//                      Disabled for now. We don't want to expose this folder until we have a certain number of samples complete.
//                      new FolderTreeNode("Mobile", "mobile", "root", "silk/phone.png", true, idSuffix, true) {{
//                          setDescription(
//                                  "<h3>Additional Mobile Samples</h3>" +
//                                  "<p>" +
//                                  "The <a href='http://www.smartclient.com/smartgwt/showcase' target='_blank'>Smart GWT LGPL Showcase</a> contains additional mobile samples which do not requirethe features of Pro (or better) Edition to run."
//                          );
//                      }},
//                      new CommandTreeNode("More Mobile Samples", "more-mobile-samples", "mobile", "silk/house.png", new SmartGWTCommand(), true, idSuffix),

                        new FolderTreeNode("Drag & Drop Data Binding", "drag-and-drop", "root", "silk/database_connect.png", true, idSuffix) {{
                            setDescription(
                                    "Databound components have built-in dragging behaviors that operate on persistent datasets."
                            );
                        }},
                        new ExplorerTreeNode("Tree Reparent", "tree-reparent", "drag-and-drop", "silk/database_refresh.png", new TreeReparent.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Recategorize (List)", "recategorize-list", "drag-and-drop", "silk/database_refresh.png", new RecategorizeList.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Recategorize (Tree)", "recategorize-tree", "drag-and-drop", "silk/database_refresh.png", new RecategorizeTree.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Recategorize (Tile)", "recategorize-tile", "drag-and-drop", "silk/database_refresh.png", new RecategorizeTile.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Copy", "form-copy", "drag-and-drop", "silk/database_refresh.png", new Copy.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Persistent Reorderable ListGrid", "persistent-reorderable-ListGrid", "drag-and-drop", "silk/table_refresh.png", new PersistentReorderableListGrid.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Persistent Reorderable TreeGrid", "persistent-reorderable-TreeGrid", "drag-and-drop", "silk/chart_organisation.png", new PersistentReorderableTreeGrid.Factory(), true, idSuffix),

                        new FolderTreeNode("Real-Time Messaging", "messaging", "root", "silk/server_lightning.png", true, idSuffix) {{
                            setDescription(
                                    "RTM module provides low-latency, high data volume streaming " +
                                    "capabilities for latency-sensitive applications such as trading desks and operations " +
                                    "centers."
                            );
                        }},
                        new ExplorerTreeNode("Simple Chat", "messaging-simple-chat", "messaging", "silk/user_comment.png", new SimpleChatSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Simulated Stock Quotes", "messaging-stock-quotes", "messaging", "silk/coins.png", new StockQuotesSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Simulated Stock Chart", "messaging-stock-chart", "messaging", "silk/chart_line.png", new StockChartSample.Factory(), true, idSuffix),

                        new ExplorerTreeNode("Offline DataSource support", "offline-ds", "root", "silk/database_gear.png", new OfflineDataSourceSupportSample.Factory(), true, idSuffix),

                        new ExplorerTreeNode("Large Trees", "large-trees", "root", "silk/table_relationship.png", null, true, idSuffix),
                        new ExplorerTreeNode("Load On Demand", "tree-load-on-demand", "large-trees", "silk/chart_organisation.png", new LoadOnDemandSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Multi-Level LOD", "tree-multilevel-load-on-demand", "large-trees", "silk/chart_organisation.png", new MultiLevelLoadOnDemandSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("Paging for Children", "tree-paging-for-children", "large-trees", "silk/chart_organisation.png", new PagingForChildrenSample.Factory(), true, idSuffix, "4.1"),
                        new ExplorerTreeNode("Multi-Level Child Paging", "tree-multi-level-child-paging", "large-trees", "silk/chart_organisation.png", new MultiLevelChildPagingSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Web Services (WSDL) and RSS", "data-integration-ws-rss", "root", "silk/cog_go.png", true, idSuffix, false) {{
                            setDescription(
                                    "Smart GWT can declaratively bind to standard formats like WSDL or RSS."
                            );
                        }},

                        // This test has been disabled for automated testing because it relies on an external
                        // service that occasionally fails (creating noise in our test results)
                        new ExplorerTreeNode("WSDL operation (generic)", "data-integration-server-wsdl-generic", "data-integration-ws-rss", "silk/cog_go.png", new WsdlOperationSample.Factory(), true, false, idSuffix),

                        new ExplorerTreeNode("WSDL databinding (SOAP IP lookup)", "data-integration-server-wsdl-iplookup", "data-integration-ws-rss",  "silk/cog.png", new WsdlDataBindingSample.Factory(), true, idSuffix),
                        new ExplorerTreeNode("RSS ListGrid binding", "data-integration-server-rss", "data-integration-ws-rss", "silk/feed.png", new RssSample.Factory(), true, idSuffix),

                        new FolderTreeNode("Tools", "tools-category", "root", "silk/wrench_orange.png", true, idSuffix),

                        //new ExplorerTreeNode("DataSource Wizard", "tools-ds-wizard", "tools-category", "silk/database_lightning.png", new DataSourceGeneratorPanel.Factory(), true, idSuffix),
                        //new ExplorerTreeNode("DataSource Wizard", "tools-ds-wizard", "tools-category", "silk/database_lightning.png", new DataSourceGeneratorStubPanel.Factory(), true, idSuffix),

                        //new ExplorerTreeNode("DataSource Admin Console", "tools-admin-console", "tools-category", "silk/server_database.png", new DataSourceConsoleStubPanel.Factory(), true, idSuffix),
                        new CommandTreeNode("DataSource Admin Console", "tools-admin-console", "tools-category", "silk/server_database.png", new DataSourceConsoleCommand(), true, idSuffix) {
                            {
                                setDescription("Smart GWT DataSource Administrator console.");
                            }
                        },

                        //new ExplorerTreeNode("SmartClient Visual Builder", "tools-visualbuilder", "tools-category", "silk/palette.png", new VisualBuilderStubPanel.Factory(), true, idSuffix),
                        new CommandTreeNode("SmartClient Visual Builder", "tools-visualbuilder", "tools-category", "silk/palette.png", new VisualBuilderCommand(), true, idSuffix) {
                            {
                                setDescription("SmartClient's powerful WYSWIG tool with ability to connect DataSource's to DataBound components.");
                            }
                        },

                        new CommandTreeNode("Developer Console", "tools-developer-console", "tools-category", "silk/bug.png", new DebugConsoleCommand(), true, idSuffix) {
                            {
                                setDescription("Smart GWT Developer console for troubleshooting, viewing client & server logs, and more.. ");
                            }
                        },

            /*            new CommandTreeNode("Smart GWT Showcase", "smartgwt-category", "root", "silk/house.png", new SmartGWTCommand(), true, idSuffix),*/
                        new ExplorerTreeNode("Smart GWT Showcase", "smartgwt-category", "root", "silk/house.png", new NoteSgwtEESgwtLgplPanel.Factory(), true, idSuffix),
                        
                        new CopyrightTreeNode("root",
                				"Copyright &copy; 2000 and beyond Isomorphic Software. All rights reserved. " +
                				"<a target='_top' href='http://smartclient.com/licenses/isc_eval_license_050316.html'>Terms of use</a>",
                				idSuffix)
                }));
            }
        }
        return data.toArray(new ExplorerTreeNode[data.size()]);
    }

    public static ExplorerTreeNode[] getData(String idSuffix) {
        return new ShowcaseData(idSuffix).getData();
    }

    public static String getNewSamplesSuffix() {
        String sgwtVersion = SC.getSgwtVersion();
        String sgwtVersionNumber = SC.getSgwtVersionNumber();

        String newSamplesSuffix = sgwtVersionNumber;
        if (!sgwtVersion.endsWith("p")) {
            newSamplesSuffix = SC.getSgwtParityStableVersionNumber() + " and " + sgwtVersionNumber;
        }
        return newSamplesSuffix;
    }
}
