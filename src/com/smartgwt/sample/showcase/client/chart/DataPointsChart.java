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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.chart.ChartPointClickEvent;
import com.smartgwt.client.widgets.chart.ChartPointClickHandler;
import com.smartgwt.client.widgets.chart.ChartPointHoverCustomizer;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.SubmitValuesEvent;
import com.smartgwt.client.widgets.form.events.SubmitValuesHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.sample.showcase.client.ShowcasePanel;
import com.smartgwt.sample.showcase.client.SourceEntity;

public class DataPointsChart extends ShowcasePanel {

    private static final String DESCRIPTION = 
            "<p>The data points in a chart can be interactive. Hover over a data point to see additional information, " +
            "and click to edit.</p>";

    public static class Factory extends ChartSamplePanelFactory {

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getNodeID() {
            return "dataPointsChart";
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new DataPointsChart();
        }
    }

    @Override
    protected boolean isTopIntro() {
        return true;
    }

    public Canvas getViewPanel() {
        // Used to holds current record
        class Holder {
            Record record;
        }
        final Holder holder = new Holder();

        final FacetChart chart = new FacetChart();
        chart.setData(DataPointsChartData.getData());
        chart.setFacets(new Facet("commonName", "Name"));
        chart.setValueProperty("lifeSpan");
        chart.setChartType(ChartType.LINE);
        chart.setShowDataPoints(Boolean.TRUE);
        chart.setTitle("Lifespan of Animals in Years");
        chart.setPointHoverCustomizer(new ChartPointHoverCustomizer() {
            public String hoverHTML(Float value, Record record) {
                String imgStyle = "padding-top: 6px; padding-bottom: 8px;";
                String imgBase = "images/tiles/images/";
                return (
                    "<b>" + record.getAttribute("commonName") + "</b><br />" +
                    "<i>" + record.getAttribute("scientificName") + "</i><br />" +
                    "<img width=120 height=100 style=\"" + imgStyle + "\" src=\"" + imgBase + record.getAttribute("picture") + "\"><br />" +
                    "<b>Life Span:</b> " + record.getAttribute("lifeSpan") + "<br />" +
                    "<b>Diet:</b> " + record.getAttribute("diet") + "<br />" +
                    "<b>" + record.getAttribute("status") + "</b><br />" +
                    "<hr>" + record.getAttribute("information")
                );
            }
        });

        // Window for editting record
        final Window editWindow = new Window();
        editWindow.setIsModal(Boolean.TRUE);
        editWindow.setShowModalMask(Boolean.TRUE);
        editWindow.setDismissOnEscape(Boolean.TRUE);
        editWindow.setAutoSize(Boolean.TRUE);
        editWindow.setAutoCenter(Boolean.TRUE);
        editWindow.setShowMinimizeButton(Boolean.FALSE);

        // Form items
        List<FormItem> formItems = new ArrayList<FormItem>();
        FormItem formItem = new FormItem();
        formItem.setName("commonName");
        formItem.setTitle("Common Name");
        formItem.setType("text");
        formItems.add(formItem);
        formItem = new FormItem();
        formItem.setName("scientificName");
        formItem.setTitle("Scientific Name");
        formItem.setType("text");
        formItems.add(formItem);
        formItem = new FormItem();
        formItem.setName("lifeSpan");
        formItem.setTitle("Life Span");
        formItem.setType("Integer");
        formItems.add(formItem);
        formItem = new FormItem();
        formItem.setName("diet");
        formItem.setTitle("Diet");
        formItem.setType("text");
        formItems.add(formItem);
        formItem = new FormItem();
        formItem.setName("status");
        formItem.setTitle("Status");
        formItem.setValueMap("Threatened", "Endangered", "Not Endangered",
            "Not currently listed", "May become threatened", "Protected");
        formItems.add(formItem);
        formItem = new FormItem();
        formItem.setName("information");
        formItem.setTitle("Information");
        formItem.setType("textArea");
        formItems.add(formItem);
        formItem = new FormItem();
        formItem.setTitle("Save");
        formItem.setType("submit");
        formItem.setColSpan(2);
        formItem.setAlign(Alignment.RIGHT);
        formItems.add(formItem);

        // Form for editting record
        final DynamicForm editForm = new DynamicForm();
        editForm.setAutoHeight();
        editForm.setAutoWidth();
        editForm.setSaveOnEnter(Boolean.TRUE);
        editForm.setWrapItemTitles(Boolean.FALSE);
        editForm.setFields(formItems.toArray(new FormItem[0]));
        editForm.addSubmitValuesHandler(new SubmitValuesHandler() {
            public void onSubmitValues(SubmitValuesEvent event) {
                // Hiding edit window
                editWindow.hide();
                // Retrieving new values
                Map<?, ?> values = event.getValuesAsMap();
                // Applying new values to record we are editing
                for (Object key : values.keySet()) {
                    holder.record.setAttribute((String) key, values.get(key));
                }
                // Force chart to redraw
                chart.setData(chart.getDataAsRecordList());
            }
        });
        editWindow.addItem(editForm);

        // Setting chart point click handler
        chart.setPointClickHandler(new ChartPointClickHandler() {
            public void onPointClick(ChartPointClickEvent event) {
                // Saving reference to record we are editing, for ease of updating
                holder.record = event.getRecord();
                // Setting title for edit window
                editWindow.setTitle(holder.record.getAttribute("commonName"));
                editWindow.show();
                // Starting edit
                editForm.editRecord(holder.record);
            }
        });

        VLayout layout = new VLayout(15);
        layout.addMember(chart);

        return layout;
    }

    @Override
    public String getIntro() {
        return DESCRIPTION;
    }

    @Override
    public SourceEntity[] getSourceUrls() {
        return new SourceEntity[]{
                    new SourceEntity("DataPointsChartData.java", JAVA, "source/chart/DataPointsChartData.java.html", false)
                };
    }
}
