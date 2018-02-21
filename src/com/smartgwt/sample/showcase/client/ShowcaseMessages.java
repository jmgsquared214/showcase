package com.smartgwt.sample.showcase.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface ShowcaseMessages extends Messages {

    public static final ShowcaseMessages INSTANCE = GWT.create(ShowcaseMessages.class);

    @DefaultMessage("Smart GWT EE Showcase")
    public SafeHtml navigationPaneTitle();
    @DefaultMessage("Samples")
    public SafeHtml shortNavigationPaneTitle();

    @DefaultMessage("Search samples")
    public SafeHtml searchSamplesTitle();
    @DefaultMessage("Search samples...")
    public String searchSamplesHint();

    @DefaultMessage("Version: {0}<br>Built: {1}")
    public SafeHtml versionLabelContents(String version, String buildDate);

    @DefaultMessage("Skin")
    public SafeHtml skinItemTitle();
    @DefaultMessage("Enterprise")
    public String enterpriseSkinName();
    @DefaultMessage("Enterprise Blue")
    public String enterpriseBlueSkinName();
    @DefaultMessage("Graphite")
    public String graphiteSkinName();
    @DefaultMessage("Simplicity")
    public String simplicitySkinName();
    @DefaultMessage("Tahoe")
    public String tahoeSkinName();
    @DefaultMessage("TreeFrog")
    public String treeFrogSkinName();
    @DefaultMessage("Black Ops")
    public String blackOpsSkinName();
    
    @DefaultMessage("Density")
    public SafeHtml densityItemTitle();
    @DefaultMessage("Dense")
    public SafeHtml denseDensityName();
    @DefaultMessage("Compact")
    public SafeHtml compactDensityName();
    @DefaultMessage("Medium")
    public SafeHtml mediumDensityName();
    @DefaultMessage("Expanded")
    public SafeHtml expandedDensityName();
    @DefaultMessage("Spacious")
    public SafeHtml spaciousDensityName();    

    @DefaultMessage("Print")
    public SafeHtml printButtonTitle();

    @DefaultMessage("View Source")
    public SafeHtml viewSourceButtonTitle();

    @DefaultMessage("{0} Showcase&nbsp;&nbsp;")
    public SafeHtml homeTabTitle(String productName);

    @DefaultMessage("Home&nbsp;&nbsp;")
    public SafeHtml homeMainTabTitle();

    @DefaultMessage("{0} Showcase")
    public SafeHtml homeNodeName(String productName);

    @DefaultMessage("Search")
    public SafeHtml searchTitle();

    @DefaultMessage("# of Samples")
    public SafeHtml numSamplesTitle();

    @DefaultMessage("Ascending")
    public SafeHtml ascendingTitle();

    @DefaultMessage("Disabled Mode")
    public SafeHtml disabledModeTitle();

    @DefaultMessage("Cubes")
    public SafeHtml cubesCategoryName();
    @DefaultMessage("New Samples")
    public SafeHtml newSamplesCategoryName();
    @DefaultMessage("Charting")
    public SafeHtml chartingCategoryName();
    @DefaultMessage("Component XML")
    public SafeHtml componentXmlCategoryName();
    @DefaultMessage("Java Data Integration")
    public SafeHtml javaDataIntegrationCategoryName();
    @DefaultMessage("Validation")
    public SafeHtml validationCategoryName();
    @DefaultMessage("SQL")
    public SafeHtml sqlCategoryName();
    @DefaultMessage("Hibernate / Beans")
    public SafeHtml hibernateBeansCategoryName();
    @DefaultMessage("JPA")
    public SafeHtml jpaCategoryName();
    @DefaultMessage("Custom DataSources")
    public SafeHtml customDataSourcesCategoryName();
    @DefaultMessage("Transactions")
    public SafeHtml transactionsCategoryName();
    @DefaultMessage("Export")
    public SafeHtml exportCategoryName();
    @DefaultMessage("Upload / Download")
    public SafeHtml uploadDownloadCategoryName();
    @DefaultMessage("Server Scripting")
    public SafeHtml serverScriptingCategoryName();
    @DefaultMessage("Drag &amp; Drop Data Binding")
    public SafeHtml dragDropDataBindingCategoryName();
    @DefaultMessage("Real-Time Messaging")
    public SafeHtml realTimeMessagingCategoryName();
    @DefaultMessage("Large Trees")
    public SafeHtml largeTreesCategoryName();
    @DefaultMessage("Web Services (WSDL) and RSS")
    public SafeHtml webServicesAndRssCategoryName();
    @DefaultMessage("Dashboards &amp; Tools")
    public SafeHtml dashboardsToolsCategoryName();
    @DefaultMessage("<span style=\"color:red;font-weight:700;\">BETA</span>")
    public SafeHtml betaSamplesName();

    @DefaultMessage("Categories")
    public SafeHtml categoriesTitle();
}
