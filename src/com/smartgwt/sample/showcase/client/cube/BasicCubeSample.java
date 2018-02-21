package com.smartgwt.sample.showcase.client.cube;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.types.ExportFormat;
import com.smartgwt.client.types.ExportDisplay;
import com.smartgwt.client.widgets.cube.CubeGrid;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class BasicCubeSample extends ShowcasePanel {
    private static final String DESCRIPTION = 
        "<p>This is an example of a basic CubeGrid. In this multi-dimensional dataset, each " + 
        "cell value has a series of attributes, called \"facets\", that appear as stacked " + 
        "headers labelling the cell value. Right click on any of the facet values and select " +
        "a value from the menu to filter based on that facet value.</p>" +
        "You can get a WYSIWYG export to Excel after hiding/showing facet " +
        "values by clicking the \"Export\" button.";

    public static class Factory extends AdvancedPanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public com.smartgwt.client.widgets.HTMLFlow getDisabledViewPanel() {
            final com.smartgwt.client.widgets.HTMLFlow htmlFlow = new com.smartgwt.client.widgets.HTMLFlow("<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the optional " +
                    "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Analytics module</a>.</p>" +
                    "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#cube_basic\" target=\"\">here</a> to see this example on SmartClient.com.</p></div>");
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return SC.hasAnalytics();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new BasicCubeSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final CubeGrid cubeGrid = new CubeGrid();
        //in order to enable charting, the Drawing module must be present
        if(SC.hasDrawing()) {
            cubeGrid.setEnableCharting(true);
        }
        cubeGrid.setData(ProductRevenueData.getData());

        cubeGrid.setWidth100();
        cubeGrid.setHeight100();
        cubeGrid.setHideEmptyFacetValues(true);
        cubeGrid.setShowCellContextMenus(true);
        cubeGrid.setValueFormat("\u00A4,0.00");

        // begin configure export colors
        cubeGrid.setExportFacetTextColor("blue");
        cubeGrid.setExportFacetBGColor("yellow");

        cubeGrid.setExportColumnFacetTextColor("red");
        cubeGrid.setExportColumnFacetBGColor("#44FF44");

        cubeGrid.setExportDefaultBGColor("#FFDDAA");
        // end configure export colors

        cubeGrid.setColumnFacets("quarter", "month", "metric");
        cubeGrid.setRowFacets("region", "product");

        Button exportButton = new Button("Export");
        exportButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                DSRequest dsRequestProperties = new DSRequest();
                dsRequestProperties.setExportAs(ExportFormat.XLS);
                dsRequestProperties.setExportDisplay(ExportDisplay.DOWNLOAD);
                cubeGrid.exportClientData(dsRequestProperties);            
            }
        });

        VLayout layout = new VLayout(15);
        layout.addMember(cubeGrid);
        layout.addMember(exportButton);

        return layout;
    }

    public String getIntro() {
        return DESCRIPTION;
    }

    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                new SourceEntity("ProductRevenue.java", JAVA, "source/cube/ProductRevenue.java.html", false),
                new SourceEntity("ProductRevenueData.java", JAVA, "source/cube/ProductRevenueData.java.html", false)
        };
    }
    
    @Override
    protected boolean shouldWrapViewPanel() {
        return true;
    }
}
