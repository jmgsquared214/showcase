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

package com.smartgwt.sample.showcase.client;

import java.util.Arrays;
import java.util.List;

import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.Browser;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public abstract class ShowcasePanel extends VLayout {

    public static final String XML = "xml";
    public static final String HTML = "html";
    public static final String JAVA = "java";

    protected Canvas viewPanel;

    private Window introWindow;
    private String betaMessage = "";

    public ShowcasePanel() {
        setWidth100();
        setHeight100();

        viewPanel = getViewPanel();
        if (Browser.getIsDesktop()) {
            final boolean topIntro = isTopIntro();
            final Layout layout = topIntro ? new VLayout() : new HLayout();

            layout.setWidth100();
            layout.setMargin(10);
            layout.setMembersMargin(10);

            final HLayout wrapper = new HLayout();
            wrapper.setWidth100();
            wrapper.addMember(viewPanel);

            layout.addMember(wrapper);
            addMember(layout);
        } else {
            addMember(viewPanel);
        }
    }

    protected boolean shouldWrapViewPanel() {
        List<Class<?>> optimalPanelClasses = Arrays.<Class<?>>asList(
            Canvas.class, HLayout.class, HStack.class, VLayout.class, VStack.class 
        );
        return optimalPanelClasses.indexOf(viewPanel.getClass()) < 0;
    }

    protected boolean isNoSource() {
        return false;
    }

    protected boolean isTopIntro() {
        return false;
    }

    public SourceEntity[] getSourceUrls() {
        return null;
    }

    protected String getIntro() {
        return null;
    }

    public abstract Canvas getViewPanel();

    protected void showSource(SourceEntity[] sourceURLs, int width, int height, boolean useDesktopMode) {
        final Window win = new Window();
        win.setShouldPrint(false);
        win.setTitle("Source");
        win.setHeaderIcon("pieces/16/cube_green.png", 16, 16);
        win.setHideUsingDisplayNone(true);
        win.setKeepInParentRect(true);
        if (!useDesktopMode) {
            win.setMaximized(true);
            win.setShowMaximizeButton(false);
            win.setShowMinimizeButton(false);
        }

        int userWidth = com.google.gwt.user.client.Window.getClientWidth() - 20;
        win.setWidth(userWidth < width ? userWidth : width);

        int userHeight = com.google.gwt.user.client.Window.getClientHeight() - 96;
        win.setHeight(userHeight < height ? userHeight : height);

        int windowTop = 40;
        int windowLeft = com.google.gwt.user.client.Window.getClientWidth() - (win.getWidth() + 20) - getPageLeft();
        win.setLeft(windowLeft);
        win.setTop(windowTop);
        win.setCanDragReposition(true);
        win.setCanDragResize(true);

        final TabSet tabs = new TabSet();
        tabs.setTabBarPosition(Side.TOP);
        tabs.setWidth100();
        tabs.setHeight100();

        String className = getClass().getName();
        String htmlPath = className.replace("com.smartgwt.sample.showcase.client.", "").replace(".", "/") + ".java.html";
        String sourceURL = "source/" + htmlPath;

        tabs.addTab(buildSourceTab("Source", "silk/script_go.png", sourceURL));

        int lastPeriodIndex = getClass().getName().lastIndexOf('.');
        String simpleClassName = getClass().getName().substring(lastPeriodIndex + 1);
        String[] dataURLs = DataURLRecords.getDataURLs(simpleClassName);

        if (dataURLs != null) {
            for (String dataURL : dataURLs) {
                String url = "source/" + dataURL + ".html";
                int lastSlashIndex = dataURL.lastIndexOf('/');
                String tabTitle;
                tabTitle = lastSlashIndex >= 0 ? dataURL.substring(lastSlashIndex + 1) : dataURL;
                tabs.addTab(buildSourceTab(tabTitle, "silk/script_go.png", url));
            }
        }

        if(sourceURLs != null) {
            for (int i = 0; i < sourceURLs.length; i++) {
                SourceEntity sourceURL2 = sourceURLs[i];
                tabs.addTab(buildSourceTab(sourceURL2));
            }
        }

        String[] dataSources = dataURLs == null ? new String[]{} : new String[dataURLs.length];
        if (dataURLs != null) {
            for (int i = 0; i < dataURLs.length; i++) {
                String dataURL = dataURLs[i];
                int fromIndex = dataURL.lastIndexOf("/");

                dataSources[i] = dataURL.substring(fromIndex + 1, dataURL.indexOf("."));
            }
        }

        //assume standard web.xml if no additional sources specified
        if(sourceURLs == null) {
            tabs.addTab(buildSourceTab("WEB-INF/web.xml", "silk/script_go.png", "source/ds/common/web.xml.html"));
        }

        tabs.addTab(getServerPropertiesTab());
        tabs.addTab(getIndexHtmlTab(dataSources));

        win.addItem(tabs);
        if (useDesktopMode) addChild(win);
        win.show();
    }

    protected void setBetaMessage(String message) {
        this.betaMessage = message;
    }

    protected void showOverview(boolean useDesktopMode) {
        final boolean topIntro = isTopIntro();
        final String intro = getIntro();
        if (intro == null || intro.isEmpty()) return;

        if (introWindow == null) {
            introWindow = new Window();
            introWindow.setShouldPrint(false);
            introWindow.setTitle("Overview");
            introWindow.setHeaderIcon("pieces/16/cube_green.png", 16, 16);
            introWindow.setKeepInParentRect(true);
            if (!useDesktopMode) {
                introWindow.setMaximized(true);
                introWindow.setShowMaximizeButton(false);
                introWindow.setShowMinimizeButton(false);
            }

            String introContents = "<p class='intro-para'>" + intro + this.betaMessage + "</p>";
            Canvas contents = new Canvas();
            contents.setCanSelectText(true);
            contents.setPadding(10);
            contents.setContents(introContents);
            if (topIntro) {
                contents.setWidth100();
            } else {
                contents.setDefaultWidth(200);
            }

            if (useDesktopMode) {
                introWindow.setAutoSize(true);
                introWindow.setAutoHeight();
            }
            introWindow.setCanDragReposition(false);
            introWindow.setCanDragResize(false);
            introWindow.addItem(contents);
        }

        if (useDesktopMode) {
            final Layout layout = ((Layout)getMember(0));
            if (topIntro) {
                layout.addMember(introWindow, 0);
            } else {
                layout.addMember(introWindow);
            }
        } else {
            introWindow.show();
        }
    }

    private Tab getServerPropertiesTab() {
        final VLayout layout = new VLayout(5);
        layout.setHideUsingDisplayNone(true);

        final HTMLFlow htmlFlow = new HTMLFlow();
        htmlFlow.setWidth("100%");
        String contents = "<hr>This file is included in the Smart GWT Pro / Power / EE distribution and the defaults have not been changed, and " +
                " it is shown here for completeness only.<hr>";
        htmlFlow.setContents(contents);
        layout.addMember(htmlFlow);

        final HTMLPane htmlPane = new HTMLPane();
        htmlPane.setWidth100();
        htmlPane.setHeight100();
        htmlPane.setContentsURL("source/ds/common/server.properties.html");
        htmlPane.setContentsType(ContentsType.PAGE);
        layout.addMember(htmlPane);

        final Tab tab = new Tab("server.properties", "silk/script_go.png");
        tab.setPane(layout);
        return tab;
    }

    private Tab getIndexHtmlTab(String[] dataSources) {
        final VLayout layout = new VLayout(5);

        final HTMLFlow htmlFlow = new HTMLFlow();
        htmlFlow.setWidth("100%");
        String contents = "<hr>This is the normal GWT auto-generated bootstrap host html file, with the only addition being the " +
                "   &lt;script&gt; include that uses the DataSourceLoader<hr>";
        htmlFlow.setContents(contents);
        layout.addMember(htmlFlow);

        final HTMLPane htmlPane = new HTMLPane();
        htmlPane.setWidth100();
        htmlPane.setHeight100();
        htmlPane.setContents(getHostHtml(dataSources));
        layout.addMember(htmlPane);

        final Tab tab = new Tab("index.html", "silk/script_go.png");
        tab.setPane(layout);
        return tab;
    }

    public Tab buildSourceTab(SourceEntity sourceEntity) {
        return buildSourceTab(sourceEntity.getTitle(), "silk/script_go.png", sourceEntity.getUrl());
    }

    public Tab buildSourceTab(String title, String icon, String url) {
        final HTMLPane tabPane = new HTMLPane();
        tabPane.setHideUsingDisplayNone(true); // work-around for http://crbug.com/338105
        tabPane.setWidth100();
        tabPane.setHeight100();
        tabPane.setContentsURL(url);
        tabPane.setContentsType(ContentsType.PAGE);
        tabPane.setOverflow(Overflow.AUTO);

        final Tab tab = new Tab(title, icon);
        tab.setPane(tabPane);
        return tab;
    }

    public String getHostHtml(String[] dataSources) {
        String dataSourcesString = null;
        if (dataSources.length > 0) {
            dataSourcesString = dataSources[0];
        }
        for (int i = 1; i < dataSources.length; i++) {
            if (i <= dataSources.length - 1) {
                dataSourcesString += ",";
            }
            dataSourcesString += dataSources[i];

        }
        String html = "&lt;html&gt;<br>" +
                "&lt;head&gt;<br>" +
                "    &lt;meta http-equiv=&quot;content-type&quot; content=&quot;text/html; charset=UTF-8&quot;&gt;<br>" +
                "    &lt;title&gt;" + Showcase.getSGWTProductName() + " Showcase&lt;/title&gt;<br>" +
                "<br>" +
                "    &lt;script type=&quot;text/javascript&quot; language=&quot;javascript&quot;<br>" +
                "            src=&quot;com.smartgwt.sample.showcase.Showcase.nocache.js&quot;&gt;&lt;/script&gt;<br>" +
                "&lt;/head&gt;<br>" +
                "<br>" +
                "&lt;!--Host HTML file--&gt;<br>" +
                "&lt;body&gt;<br>" +
                "<br>";
        if (dataSources.length > 0) {
            html += "&lt;!--load datasources from server --&gt;<br>" +
                    "&lt;script src=&quot;sc/DataSourceLoader?dataSource= " + dataSourcesString + "&quot;&gt;&lt;/script&gt;<br>" +
                    "<br>";
        }
        html += "&lt;!-- history support --&gt;<br>" +
                "&lt;iframe src=&quot;javascript:''&quot; id=&quot;__gwt_historyFrame&quot; tabIndex='-1' style=&quot;position:absolute;width:0;height:0;border:0&quot;&gt;&lt;/iframe&gt;<br>" +
                "<br>" +
                "&lt;/body&gt;<br>" +
                "&lt;/html&gt;";

        return html;
    }
}
