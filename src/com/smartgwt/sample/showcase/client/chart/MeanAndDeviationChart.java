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
package com.smartgwt.sample.showcase.client.chart;

import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.util.EnumUtil;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.drawing.DrawLine;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;


public class MeanAndDeviationChart extends ShowcasePanel {

    public static final String DESCRIPTION =
        "<p>Charts can show the average value for the data set as well as the standard " +
        "deviation (a measure of how far data points are from the average).</p>" +
        "<p>In the chart below, the bright green line shows the average, and the bright " +
        "blue lines show one standard deviation above average and below average.</p>" +
        "<p>Use the \"Regenerate Random Data\" button to see a new random data set.</p>";

    private static final String[] LABELS = new String[] {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"
    };

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "meanAndDeviation";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new MeanAndDeviationChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        final FacetChart chart = new FacetChart();
        chart.setID("randomDataChart");
        chart.setTitle("Random Data Chart");
        chart.setChartType(ChartType.COLUMN);
        chart.setFacets(new Facet("label", "Label"));

        // Draw bright blue lines showing one standard deviation above the mean
        // and one standard deviation below the mean, as per the default
        // configuration of the FacetChart.standardDeviations property.
        chart.setShowStandardDeviationLines(true);
        DrawLine stdDevProps = new DrawLine();
        stdDevProps.setLineWidth(2);
        stdDevProps.setLineColor("#0000EE");
        chart.setStandardDeviationLineProperties(stdDevProps);

        // Draw a bright green line to show the average of the data.
        chart.setShowExpectedValueLine(true);
        DrawLine meanProps = new DrawLine();
        meanProps.setLineWidth(2);
        meanProps.setLineColor("#00EE00");
        chart.setExpectedValueLineProperties(meanProps);

        chart.setAutoDraw(false);
        chart.setShowDataAxisLabel(false);
        chart.setChartRectMargin(15);
        chart.setValueTitle("Random Values");
        chart.setData(generateRandomData());

        DynamicForm form = new DynamicForm();
        form.setWidth("30%");
        form.setAutoDraw(false);
        SelectItem select = new SelectItem("chartType", "Chart Type");
        select.setValueMap(
            ChartType.AREA.getValue(),
            ChartType.COLUMN.getValue(),
            ChartType.BAR.getValue(),
            ChartType.LINE.getValue(),
            ChartType.RADAR.getValue());
        select.setDefaultValue(chart.getChartType().getValue());
        select.addChangedHandler(new ChangedHandler() {
                public void onChanged(ChangedEvent event) {
                    chart.setChartType(EnumUtil.getEnum(ChartType.values(), (String) event.getValue()));
                }
            });
        form.setItems(select);

        Button button = new Button();
        button.setID("regenerateButton");
        button.setWidth("30%");
        button.setTitle("Regenerate Random Data");
        button.setAutoFit(true);
        button.setPadding(5);
        button.setAutoDraw(false);
        button.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    chart.setData(generateRandomData());
                }
            });

        HStack hstack = new HStack();
        hstack.setHeight(30);
        hstack.setMembers(form, button);

        VLayout layout = new VLayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        layout.setMembersMargin(20);
        layout.setMembers(hstack, chart);

        return layout;
    }

    private static final Record[] generateRandomData() {
        int length = LABELS.length;
        Record[] newChartData = new Record[length];
        final double min = 0.0f, max = 25.0f;

        // Fill newChartData with a random value for each label, generated
        // uniformly over the range from min to max.
        for (int i = 0; i < length; ++i) {
            Record record = new Record();
            record.setAttribute("label", LABELS[i]);
            record.setAttribute("_value", (float) (min + (max - min) * Math.random()));
            newChartData[i] = record;
        }
        return newChartData;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }
}
