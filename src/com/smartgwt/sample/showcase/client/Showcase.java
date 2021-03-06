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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.i18n.client.DateTimeFormat;

import com.smartgwt.client.Version;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.CurrentPane;
import com.smartgwt.client.types.DeviceMode;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.PageOrientation;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.NavigationDirection;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.AutoTest;
import com.smartgwt.client.util.Browser;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.events.OrientationChangeEvent;
import com.smartgwt.client.util.events.OrientationChangeHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.StatefulCanvas;
import com.smartgwt.client.widgets.TitleFormatter;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.NavigationBar;
import com.smartgwt.client.widgets.layout.SplitPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.events.PaneChangedEvent;
import com.smartgwt.client.widgets.layout.events.PaneChangedHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.TabBar;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;
import com.smartgwt.sample.showcase.client.data.CommandTreeNode;
import com.smartgwt.sample.showcase.client.data.ExplorerTreeNode;
import com.smartgwt.sample.showcase.client.data.FolderTreeNode;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Showcase implements EntryPoint, ShowcaseNavigator {
    private static final ShowcaseMessages M = ShowcaseMessages.INSTANCE;
    private static final String preReleaseVersion = SC.getSgwtVersionNumber();

    private static final String skinCookieName = "skin_name_2_4";

    private static final String BETA_MESSAGE = 
        "<br><br><span style='color: red;font-size:11px;font-weight: 700;'>BETA</span> : " + 
        "This sample demonstrates features available in the <a href=\"" + 
        "http://www.smartclient.com/product/downloadOtherReleases.jsp#nextVersion\" " + 
        "target=\"_blank\"> next available version</a> of Smart GWT, ";

    private static native boolean _leaveMinimalUISpace() /*-{
        return ($wnd.isc.Browser.isIPhone && !$wnd.isc.Browser.isIPad &&
                $wnd.isc.Browser.isMobileSafari && $wnd.isc.Browser.iOSMinorVersion >= 7.1);
    }-*/;

    private static final native boolean isSeleniumPresent() /*-{
        return !!$wnd.isc.Browser.seleniumPresent;
    }-*/;    

    public static native boolean isIOs() /*-{
        return ($wnd.isc.Browser.isIPhone);
    }-*/;
    
    public static String getSGWTProductName() {
        String editionString = "";
        if (isEEOrEnterpriseEval()) editionString = " EE";
        else if (isProEdition())    editionString = " Pro Edition";
        else if (isPowerEdition())  editionString = " Power Edition";
        return "Smart GWT" + editionString;
    }

    private static int fontIncreaseValue;
    private static int sizeIncreaseValue;

    public static int getSizeIncrease() {
        return sizeIncreaseValue;
    }

    public static String getCurrentSkin() {
        String currentSkin = Cookies.getCookie(skinCookieName);
        return currentSkin != null ? currentSkin : M.tahoeSkinName();
    }

    public static boolean usingTahoe() {
        return M.tahoeSkinName().equals(getCurrentSkin());
    }

    private static String getWebRightButtonStyle(String color) {
        return color + (usingTahoe() ? "_EbuttonTahoe ERbuttonTahoe" :
                                       "_Ebutton ERbutton");
    }

    private static String getWebBottomButtonStyle(String color) {
        return color + (usingTahoe() ? "_EbuttonTahoe EBbuttonTahoe" :
                                       "_Ebutton EBbutton");
    }

    private static String getWebButtonBoxStyle(boolean hasGradient) {
        if (usingTahoe()) {
            return "explorerButtonBoxRTahoe";
        } else {
            String style = "explorerButtonBoxR";
            if (hasGradient) style += " explorerButtonBoxGradient";
            return style;
        }
    }

    public static String getPreReleaseVersion() {
        return preReleaseVersion;
    }

    private static boolean isEEOrEnterpriseEval() {
        final String licenseType = SC.getLicenseType();
        return "Enterprise".equals(licenseType) || "Eval".equals(licenseType) ||
            licenseType.startsWith("Eval") || "AllModules".equals(licenseType);
    }

    private static boolean isProEdition() {
        return SC.getLicenseType().startsWith("Pro");
    }

    private static boolean isPowerEdition() {
        return SC.getLicenseType().startsWith("Power");
    }

    public static boolean hasBatchUploader() {
        return (isEEOrEnterpriseEval() || isPowerEdition());
    }

    public static boolean hasClientExport() {
        return (isEEOrEnterpriseEval() || isPowerEdition());
    }

    public static boolean hasServerAdvancedFiltering() {
        return isEEOrEnterpriseEval();
    }

    public static boolean hasSQLTemplating() {
        return isEEOrEnterpriseEval();
    }

    public static boolean hasTransactionChaining() {
        return isEEOrEnterpriseEval();
    }

    public static native boolean websiteModeSetOnPage() /*-{
        return $wnd.isc_websiteMode === true || ($wnd.isc.params.isc_websiteMode === "true");
    }-*/;
    public static boolean isc_websiteMode = websiteModeSetOnPage();

    private static String smartclientShowcase = SC.isStable() ? "/smartclient/showcase/" : 
                                                         "/smartclient-latest/showcase/";
    
    private SplitPane splitPane;
    private ShowcaseTabSet mainTabSet;
    private Menu contextMenu;
    private SideNavTree sideNav;
    private Layout homePanel;
    private HLayout topPane;
    private VStack rightPane;
    private HStack bottomPane;
    private HLayout bottomPaneLeft;
    private HLayout bottomPaneRight;
    private TileView tileView;
    private DynamicForm searchForm;
    
    private List<Canvas> detailTools;
    private ToolStripButton printButton;
    private ToolStripButton sourceButton;
    private ToolStripButton showOverviewButton;

    private ExplorerTreeNode lastMatch;
    private String lastValue;
    private String lastName;
    private List<ExplorerTreeNode> lastOpenedFolders = new ArrayList<ExplorerTreeNode>();

    public void onModuleLoad() {
        final boolean leaveMinimalUISpace = _leaveMinimalUISpace();
        final boolean useDesktopMode = ShowcaseConfiguration.
            getSingleton().isOpenForTesting() || Browser.getIsDesktop();

        ShowcaseCustomTile.useDesktopMode = useDesktopMode;

        if (leaveMinimalUISpace) {
            Canvas.setDefaultPageSpace(Page.getOrientation() == PageOrientation.LANDSCAPE ? 20 : 0);

            final Label minimalUISpacer = new Label();
            minimalUISpacer.setWidth100();
            minimalUISpacer.setHeight(20);
            minimalUISpacer.setLeavePageSpace(0);
            minimalUISpacer.setBackgroundColor("Black");
            minimalUISpacer.setIcon("cubes.png");
            minimalUISpacer.setIconWidth(16);
            minimalUISpacer.setIconHeight(16);
            minimalUISpacer.setContents("<strong style='color: White; font-size: 15px;'>Isomorphic Software</strong>");
            minimalUISpacer.setAlign(Alignment.CENTER);
            minimalUISpacer.setAriaRole("presentation");
            minimalUISpacer.setAriaState("hidden", "true");
            minimalUISpacer.draw();
        }

        final String initToken = History.getToken();
        // If the request contains param "websiteMode", override the configured value
        {
            final String isc_websiteModeParam = com.google.gwt.user.client.Window.Location.getParameter("isc_websiteMode");
            if (isc_websiteModeParam != null) {
                isc_websiteMode = isc_websiteModeParam.isEmpty() || 
                    Boolean.parseBoolean(isc_websiteModeParam);
            }
        }
        isc_websiteMode = isc_websiteMode && useDesktopMode;

        // setup overall layout
        final VLayout main = new VLayout() {
            {
                setID("isc_Showcase_1_0");
                setWidth100();
                setHeight100();
                //setLayoutMargin(5);
                //setStyleName("tabSetContainer");
            }

            @Override
            protected void onInit() {
                super.onInit();
                if (initToken.length() != 0) {
                    onHistoryChanged(initToken);
                }
            }
        };

        splitPane = new SplitPane();
        if (useDesktopMode) splitPane.setDeviceMode(DeviceMode.DESKTOP);

        if (isc_websiteMode) {
            splitPane.setShowResizeBar(true);
            splitPane.setResizeBarTarget("next");
        }

        splitPane.setShowMiniNav(false);
        splitPane.setWidth100();
        splitPane.setHeight100();
        splitPane.setAddHistoryEntries(false);

        if (useDesktopMode) splitPane.setShowNavigationBar(false);
        else splitPane.setNavigationTitle(M.navigationPaneTitle().asString());

        VLayout featureExplorer = new VLayout();
        featureExplorer.setWidth100();
        featureExplorer.setHeight100();

        topPane = new HLayout();
        topPane.setHeight100();
        topPane.setOverflow(Overflow.HIDDEN);

        detailTools = new ArrayList<Canvas>();
        if (useDesktopMode) {
            final SelectItem selectItem = new SelectItem("skin", M.skinItemTitle().asString());
            selectItem.setWidth(130);
            final LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
            valueMap.put("Enterprise", M.enterpriseSkinName());
            valueMap.put("EnterpriseBlue", M.enterpriseBlueSkinName());
            valueMap.put("Graphite", M.graphiteSkinName());
            valueMap.put("Tahoe", M.tahoeSkinName());
            valueMap.put("Simplicity", M.simplicitySkinName());
            valueMap.put("TreeFrog", M.treeFrogSkinName());
            valueMap.put("BlackOps", M.blackOpsSkinName());
            selectItem.setValueMap(valueMap);
            selectItem.setHoverWidth(300);
            selectItem.setHoverHeight(20);
            selectItem.setPrompt("Several look and feel options are available. <P>"+
                                 "<b>Note:</b> some samples that have a limited number of "+
                                 "controls will look nearly identical in different skins");
            final String densityCookieName = "density_name_selected";
            
            if (usingTahoe()) Page.setAppImgDir("[APP]imagesTahoe/");
            else              Page.setAppImgDir("[APP]images/");
            
            selectItem.setDefaultValue(getCurrentSkin());
            selectItem.setShowTitle(false);
            selectItem.addChangeHandler(new ChangeHandler() {
                public void onChange(ChangeEvent event) {
                    Cookies.setCookie(skinCookieName, (String) event.getValue());
                    String dhcCookie = Cookies.getCookie("dhc");
                    if ("Tahoe".equals((String) event.getValue())) {
                        Cookies.setCookie(densityCookieName, "fontIncrease=3&sizeIncrease=10");
                    } else if (dhcCookie == null) {
                        Cookies.setCookie(densityCookieName, "fontIncrease=1&sizeIncrease=2");
                    }
                    com.google.gwt.user.client.Window.Location.reload();
                }
            });
            
            final SelectItem selectItemDensity = new SelectItem("density", M.densityItemTitle().asString());
            selectItemDensity.setWidth(130);
            final LinkedHashMap<String, String> valueMapDensity = new LinkedHashMap<String, String>();
            valueMapDensity.put("fontIncrease=0&sizeIncrease=0", M.denseDensityName().asString());
            valueMapDensity.put("fontIncrease=1&sizeIncrease=2", M.compactDensityName().asString());
            valueMapDensity.put("fontIncrease=2&sizeIncrease=4", M.mediumDensityName().asString());
            valueMapDensity.put("fontIncrease=2&sizeIncrease=6", M.expandedDensityName().asString());
            valueMapDensity.put("fontIncrease=3&sizeIncrease=10", M.spaciousDensityName().asString());
            selectItemDensity.setValueMap(valueMapDensity);
            selectItemDensity.setHoverWidth(300);
            selectItemDensity.setHoverHeight(20);
            selectItemDensity.setPrompt("Most skins support the ability to choose different data density "+
                          "options, allowing tradeoffs between a 'spacious' UI feel vs "+ 
                          "productivity gains in some applications when more data "+
                          "is viewable at once");

            String currentDensity = Cookies.getCookie(densityCookieName);
            if (currentDensity == null) {
                currentDensity = isSeleniumPresent() ? "fontIncrease=0&sizeIncrease=0" :
                                                       "fontIncrease=3&sizeIncrease=10";
            }
            selectItemDensity.setDefaultValue(currentDensity);
            selectItemDensity.setShowTitle(false);
            selectItemDensity.addChangeHandler(new ChangeHandler() {
                public void onChange(ChangeEvent event) {
                    Cookies.setCookie("dhc", "1");
                    Cookies.setCookie(densityCookieName, (String) event.getValue());
                    com.google.gwt.user.client.Window.Location.reload();
                }
            });
            final String[] densityParameters = currentDensity.split("&");
            final String[] fontIncreaseParameters = densityParameters[0].split("=");
            final String[] sizeIncreaseParameters = densityParameters[1].split("=");
            fontIncreaseValue = Integer.parseInt(fontIncreaseParameters[1]);
            sizeIncreaseValue = Integer.parseInt(sizeIncreaseParameters[1]);
        
            Canvas.resizeFonts(fontIncreaseValue);
            Canvas.resizeControls(sizeIncreaseValue);
            
            SpacerItem spacer = new SpacerItem();
            spacer.setWidth(3);
            
            final DynamicForm skinSwitcherForm = new DynamicForm();
            skinSwitcherForm.setPadding(0);
            skinSwitcherForm.setMargin(usingTahoe() ? 1 : 0);
            skinSwitcherForm.setCellPadding(1);
            skinSwitcherForm.setNumCols(3);
            skinSwitcherForm.setFields(selectItem, spacer, selectItemDensity);
            skinSwitcherForm.setLayoutAlign(VerticalAlignment.CENTER);
            
            detailTools.add(new LayoutSpacer(5, 1));
            detailTools.add(skinSwitcherForm);
            detailTools.add(new LayoutSpacer(5, 1));
        }

        VLayout sideNavLayout = new VLayout();
        sideNavLayout.setHeight100();
        sideNavLayout.setWidth(290);

        searchForm = new DynamicForm();
        searchForm.setCellPadding(0);
        searchForm.setWidth100();
        searchForm.setNumCols(1);

        // override search box styling for Tahoe
        if (usingTahoe()) {
            searchForm.setStyleName("gridSearchFormTahoe");
            searchForm.setHeight((new SectionStack()).getHeight());
        }

        // Use the name 'search' so that we get a Search button on iOS instead of a Go button.
        final TextItem searchItem = new TextItem("search", M.searchSamplesTitle().asString());
        searchItem.setShowTitle(false);
        searchItem.setHint(M.searchSamplesHint());
        searchItem.setShowHintInField(true);
        searchItem.setWidth("*");
        searchItem.setColSpan(useDesktopMode ? 2 : 1);
        searchItem.setBrowserAutoCorrect(false);

        if (usingTahoe()) searchItem.setTextBoxStyle("gridSearchItemTahoe");

        searchItem.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String keyName = event.getKeyName();
                if ("enter".equalsIgnoreCase(keyName)) {
                    if (EventHandler.shiftKeyDown()) {
                        findNode();
                        return;
                    }
                    String value = searchItem.getValueAsString();
                    if (value != null) {
                        value = value.trim();
                        if (!value.isEmpty()) {
                            searchItem.blurItem();
                            showHomePanel();
                            tileView.updateTiles(value);
                        }
                    }
                }
                if ("escape".equalsIgnoreCase(keyName)) {
                    revertState(false);
                    return;
                }
            }
        });
        searchItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                findNode();					
            }
        });
        searchForm.setFields(searchItem);

        sideNavLayout.addMember(searchForm);

        sideNav = new SideNavTree();
        sideNav.setID("isc_SideNavTree_0");
        sideNav.setShowHover(true);
        sideNav.setCanHover(useDesktopMode);
        sideNav.setHoverStyle("hoverTreeGridCustom");
        sideNav.setHoverCustomizer(new HoverCustomizer() {
            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (record instanceof ExplorerTreeNode) {
                    final ExplorerTreeNode node = (ExplorerTreeNode)record;
                    final PanelFactory factory = node.getFactory();
                    if (factory != null && factory.getDescription() != null) {
                        String customDiv = "<div style=\"width:450px; " +
                                   "margin-top:10px; " +
                                   "margin-bottom:10px; " +
                                   "margin-left:10px; " +
                                   "margin-right:10px;\">" +
                                   "<b>Sample Description</b>: " + factory.getDescription();

                        if (node.getHTML().contains("BETA")) {
                            customDiv +=
                                   "<br><br><span style='color: red;font-size:11px;font-weight: 700;'>BETA</span> : " +
                                   "This sample demonstrates features available in the next available version of " +
                                   "Smart GWT, " + preReleaseVersion + ".  To download a " + preReleaseVersion + " SDK, " +
                                   "click on \"Pre-release versions\" on the Download page.";
                        }

                        customDiv += "</div>";
                        return customDiv;
                    }
                }
                return null; // no hover
            }
        });
        sideNav.addNodeClickHandler(new NodeClickHandler() {
            public void onNodeClick(NodeClickEvent event) {
                TreeNode node = event.getNode();
                showSample(node, NavigationDirection.FORWARD);
            }
        });

        // open example tree completely for automated test framework
        if (ShowcaseConfiguration.getSingleton().isOpenForTesting()) {
            sideNav.getData().openAll();
        }

        sideNavLayout.addMember(sideNav);

        ToolStrip toolStripVersion = new ToolStrip();
        toolStripVersion.setWidth100();
        toolStripVersion.setHeight(useDesktopMode ? 34 : 44);

        Date buildDate = Version.getBuildDate();
        final Label version = new Label(M.versionLabelContents(Version.getVersion(), 
            DateTimeFormat.getFormat("yyyy-MM-dd").format(buildDate)).asString());

        version.setWidth100();
        version.setHeight100();
        version.setPadding(5);
        version.setValign(VerticalAlignment.CENTER);
        if (usingTahoe()) version.setStyleName("versionLabelTahoe");

        toolStripVersion.addMember(version);
        sideNavLayout.addMember(toolStripVersion);

        if (useDesktopMode) {
            mainTabSet = new ShowcaseTabSet();
            mainTabSet.setWidth100();
            mainTabSet.setHeight100();
            mainTabSet.setDestroyPanes(true);
            mainTabSet.setPaneContainerOverflow(Overflow.AUTO);

            Layout paneContainerProperties = new Layout();
            paneContainerProperties.setLayoutMargin(0);
            paneContainerProperties.setLayoutTopMargin(1);
            mainTabSet.setPaneContainerProperties(paneContainerProperties);
            
            mainTabSet.setAutoChildProperties("tab", new StatefulCanvas() {{
            	// redraw on state change so we can show different icons for different states
            	setRedrawOnStateChange(true);
            	// dynamically build the title based on the extra icon for the current state, plus the
            	// actual title string
            	setTitleFormatter(new TitleFormatter() {

					@Override
					public String formatTitle(StatefulCanvas component, String title) {
						String icon = null;

						// History token is copied from the tab config onto the live tab button
						String nodeID = component.getAttribute("historyToken");
						// No history token - likely the home tab
						if (nodeID == null) {
							Tab tab = mainTabSet.getTab(component.getID());
							if (tab.getPane() == homePanel) {
								nodeID = "main";
							}
						}
                        Tree samples = sideNav.getData();
                        ExplorerTreeNode node = (ExplorerTreeNode)samples.find("nodeID", nodeID);
                        if (node == null) return title;

                    	// replace the default title with the result of a getHTML call on the node
                    	// and pick up the appropriate icon
                    	String nodeTitle = node.getHTML();
	                    if (component.isSelected()) icon = node.getSelectedIcon();
	                    
	                    if (icon == null) {
	                    	icon = node.getIcon();
	                    }
                        
	                    if (icon == null) {
	                        icon = "silk/plugin.png";
	                    }
	                    
	                    return "<nobr>" + Canvas.imgHTML(icon, 16, 16) + "&nbsp;<span " +
	                      "style='display:inline-block;line-height:16px;vertical-align:" +
	                      "text-top'>" + nodeTitle + "</span></nobr>";
					}

					
				});
            }});
            mainTabSet.addTabSelectedHandler(new TabSelectedHandler() {
                public void onTabSelected(TabSelectedEvent event) {
                    final Tab selectedTab = event.getTab();

                    final Canvas pane = selectedTab.getPane();
                    if (pane instanceof ShowcasePanel) {
                        final ShowcasePanel panel = (ShowcasePanel)pane;
                        final Canvas viewPanel = panel.viewPanel;
                        AutoTest.setTestRoot(panel.shouldWrapViewPanel() ? 
                                             viewPanel.getParentCanvas() : viewPanel);
                    }

                    final String historyToken = selectedTab.getAttribute("historyToken");
                    if (historyToken != null) {
                        History.newItem(historyToken, false);
                    } else {
                        History.newItem("main", false);
                    }
                }
            });
            if (usingTahoe()) {
                Canvas containerProperties = new Canvas();
                containerProperties.setStyleName("homeInterfacePageTahoe");
                mainTabSet.setPaneContainerProperties(containerProperties);
            }

            contextMenu = createContextMenu();
        }

        homePanel = new HLayout();
        homePanel.setHeight100();
        homePanel.setWidth100();
        homePanel.setOverflow(Overflow.HIDDEN);

        tileView = new TileView(sideNav.getShowcaseData(), useDesktopMode,
                                sideNav.hasBetaSamples(), this);
        tileView.setWidth100();
        homePanel.addMember(tileView);

        if (isc_websiteMode) {
            // Build and add the rightPane, with callToAction buttons
            rightPane = new VStack();
            rightPane.setDefaultLayoutAlign(Alignment.CENTER);
            rightPane.setOverflow(Overflow.HIDDEN);
            rightPane.setWidth(234);
            rightPane.hide();

            rightPane.addVisibilityChangedHandler(new VisibilityChangedHandler() {
                public void onVisibilityChanged(VisibilityChangedEvent event) {
                    if (event.getIsVisible()) {
                        topPane.setHeight100();
                        bottomPane.hide();
                    } else {
                        int bottomPaneHeight = bottomPane.getHeight();
                        topPane.setHeight(topPane.getHeight() - bottomPaneHeight);
                        bottomPane.show();
                    }
                }
            });

            // Add bottomPane
            bottomPaneLeft = new HLayout();
            bottomPaneLeft.setWidth("40%");
            bottomPaneLeft.setHeight(30);
            bottomPaneLeft.setAlign(Alignment.LEFT);
            bottomPaneLeft.setLayoutLeftMargin(10);
            bottomPaneLeft.setMembersMargin(10);

            bottomPaneRight = new HLayout();
            bottomPaneRight.setWidth("60%");
            bottomPaneRight.setHeight(30);
            bottomPaneRight.setLayoutRightMargin(10);
            bottomPaneRight.setLayoutLeftMargin(10);
            bottomPaneRight.setAlign(Alignment.RIGHT);

            bottomPane = new HStack();
            bottomPane.addMember(bottomPaneLeft);
            bottomPane.addMember(bottomPaneRight);
            bottomPane.setOverflow(Overflow.HIDDEN);
            bottomPane.setHeight(30);
            bottomPane.setWidth100();
            bottomPane.setStyleName("explorerBottomPane");
            bottomPane.hide();

            // Right Pane buttons
            // SmartGWT Call to action button in right panel
            Layout smartGwtButtonBoxR = new Layout();
            smartGwtButtonBoxR.setStyleName(getWebButtonBoxStyle(false));
            smartGwtButtonBoxR.setWidth(200);
            rightPane.addMember(smartGwtButtonBoxR);

            Label smartGwtButtonR = new Label(
                "<div style='font-size: 11pt;'>Prefer to write UI in JavaScript?</div>" +
                "<img src='images/icon_javascript_t.png'" +
                "style='height: 30pt; float: left; margin-right: 2pt; " +
                "margin-left: 2pt; margin-top: 10pt;'/>" +
                "<a target='_top' style='line-height: " + (usingTahoe() ? "14" : "12") +
                "pt; padding-top: 5pt; width: 130px;' href='" + smartclientShowcase +
                "'>" + "SmartClient<br/>Hands-On Demo" + "</a>");
            smartGwtButtonR.setStyleName(getWebRightButtonStyle("lightgrey"));
            smartGwtButtonR.setWidth(200);
            smartGwtButtonR.setHeight(usingTahoe() ? 95 : 1);
            smartGwtButtonR.setAlign(Alignment.CENTER);
            smartGwtButtonR.setValign(VerticalAlignment.CENTER);
            smartGwtButtonBoxR.addMember(smartGwtButtonR);

            // Contact Us Call to action button in right panel
            Layout contactUsButtonBoxR = new Layout();
            contactUsButtonBoxR.setStyleName(getWebButtonBoxStyle(true));
            contactUsButtonBoxR.setWidth(200);
            rightPane.addMember(contactUsButtonBoxR);

            Label contactUsButtonR = new Label("<div>Got questions?" +
                "<div style='" + (usingTahoe() ? "font-size: 10pt;" :
                                                 "font-size: 8pt;") +
                "'>We'd love to hear from you!</div></div>" +
                "<a target='_top' href='/company/contact.jsp" + "' " +
                ">" + "Contact Us" + "</a>");
            contactUsButtonR.setStyleName(getWebRightButtonStyle("darkgrey"));
            contactUsButtonR.setWidth(200);
            contactUsButtonR.setHeight(1);
            contactUsButtonR.setAlign(Alignment.CENTER);
            contactUsButtonR.setValign(VerticalAlignment.CENTER);
            contactUsButtonBoxR.addMember(contactUsButtonR);

            // Free Trial Call to action button in right panel
            Layout freeTrialButtonBoxR = new Layout();
            freeTrialButtonBoxR.setStyleName(getWebButtonBoxStyle(true));
            freeTrialButtonBoxR.setWidth(200);
            rightPane.addMember(freeTrialButtonBoxR);

            Label freeTrialButtonR = new Label("<div>Try it out for yourself!</div>" +
                        "<a target='_top' style='line-height: " + (usingTahoe() ? "14" : "12") + 
                        "pt; padding-top: 5pt;' href='/product/download.jsp" + "' " +
                        ">" + "Free Trial<br/><span style='font-size: " + 
                            (usingTahoe() ? "10" : "8") + 
                        "pt;'>60 days</span>" + "</a>");
            freeTrialButtonR.setStyleName(getWebRightButtonStyle("blue"));
            freeTrialButtonR.setWidth(200);
            freeTrialButtonR.setHeight(1);
            freeTrialButtonR.setAlign(Alignment.CENTER);
            freeTrialButtonR.setValign(VerticalAlignment.CENTER);
            freeTrialButtonBoxR.addMember(freeTrialButtonR);

            // Pricing Call to action button in right panel
            Layout pricingTrialButtonBoxR = new Layout();
            pricingTrialButtonBoxR.setStyleName(getWebButtonBoxStyle(true));
            pricingTrialButtonBoxR.setWidth(200);
            rightPane.addMember(pricingTrialButtonBoxR);

            Label pricingTrialButtonR = new Label("<div>Want your own?</div>" +
                    "<a target='_top' href='/product/" + "' " +
                    ">" + "Editions & Pricing" + "</a>");
            pricingTrialButtonR.setStyleName(getWebRightButtonStyle("orange"));
            pricingTrialButtonR.setWidth(200);
            pricingTrialButtonR.setHeight(1);
            pricingTrialButtonR.setAlign(Alignment.CENTER);
            pricingTrialButtonR.setValign(VerticalAlignment.CENTER);
            pricingTrialButtonBoxR.addMember(pricingTrialButtonR);

            // Learn More Call to action button in right panel
            Layout learnMoreButtonBoxR = new Layout();
            learnMoreButtonBoxR.setStyleName(getWebButtonBoxStyle(true));
            learnMoreButtonBoxR.setWidth(200);
            rightPane.addMember(learnMoreButtonBoxR);

            Label learnMoreButtonR = new Label("<div>Isomorphic has the advantage.</div>" +
                    "<a target='_top' href='/technology/whysmart.jsp" + "' " +
                    ">" + "Learn More >" + "</a>");
            learnMoreButtonR.setStyleName(getWebRightButtonStyle("lightgrey"));
            learnMoreButtonR.setWidth(200);
            learnMoreButtonR.setHeight(usingTahoe() ? 84 : 1);
            learnMoreButtonR.setAlign(Alignment.CENTER);
            learnMoreButtonR.setValign(VerticalAlignment.CENTER);
            learnMoreButtonBoxR.addMember(learnMoreButtonR);

            // Bottom Pane buttons
            // Free Trial Call to action button in bottom panel
            Label freeTrialButtonB = new Label("<a target='_top' href='/product/download.jsp' " +
                    ">Free Trial</a>");
            freeTrialButtonB.setStyleName(getWebBottomButtonStyle("blue"));
            bottomPaneLeft.addMember(freeTrialButtonB);

            // Pricing Call to action button in bottom panel
            Label pricingButtonB = new Label("<a target='_top' href='/product/' " +
                ">Editions & Pricing</a>");
            pricingButtonB.setStyleName(getWebBottomButtonStyle("orange"));
            bottomPaneLeft.addMember(pricingButtonB);

            // SmartGWT Call to action button in bottom panel
            Label smartGWTButtonPreB = new Label(
                "<span>Prefer to write UI in JavaScript?</span>");
            smartGWTButtonPreB.setWidth(180);
            smartGWTButtonPreB.setStyleName(usingTahoe() ? "EBbuttonTahoe" : "EBbutton");
            bottomPaneRight.addMember(smartGWTButtonPreB);

            Label smartGWTButtonB = new Label("<a target='_top' style='width: 160px;' " +
                "href='"+smartclientShowcase+"'" +
                " >SmartClient Live Demo</a>");
            smartGWTButtonB.setWidth(160);            
            smartGWTButtonB.setStyleName(getWebBottomButtonStyle("darkgrey"));
            bottomPaneRight.addMember(smartGWTButtonB);
        }

        final NavigationBar navigationBarProperties = new NavigationBar();
        final Label navTitleLabelProperties = new Label();
        navTitleLabelProperties.setIcon("silk/database_connect.png");
        navTitleLabelProperties.setIconWidth(24);
        navTitleLabelProperties.setIconHeight(24);
        navigationBarProperties.setAutoChildProperties("titleLabel", navTitleLabelProperties);
        splitPane.setAutoChildProperties("navigationBar", navigationBarProperties);
        final Label detailTitleLabelProperties = new Label();
        detailTitleLabelProperties.setIconWidth(24);
        detailTitleLabelProperties.setIconHeight(24);
        splitPane.setAutoChildProperties("detailTitleLabel", detailTitleLabelProperties);
        splitPane.setNavigationPane(sideNavLayout);

        showOverviewButton = new ToolStripButton();
        showOverviewButton.setWidth(20);
        showOverviewButton.setHeight(20);
        showOverviewButton.setDisabled(true);
        showOverviewButton.setIcon("silk/book_open.png");
        showOverviewButton.setShowFocused(false);
        showOverviewButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                final Canvas pane;
                if (useDesktopMode) {
                    final Tab tab = mainTabSet.getSelectedTab();
                    pane = tab.getPane();
                } else {
                    pane = splitPane.getDetailPane();
                }
                if (pane instanceof ShowcasePanel) {
                    ((ShowcasePanel)pane).showOverview(useDesktopMode);
                }
            }
        });
        

        showOverviewButton = new ToolStripButton();
        showOverviewButton.setWidth(20);
        showOverviewButton.setDisabled(true);
        showOverviewButton.setIcon("silk/book_open.png");
        showOverviewButton.setShowFocused(false);
        showOverviewButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                final ShowcasePanel showcasePanel;
                if (useDesktopMode) {
                    final Tab tab = mainTabSet.getSelectedTab();
                    showcasePanel = (ShowcasePanel)tab.getPane();
                } else {
                    showcasePanel = (ShowcasePanel)splitPane.getDetailPane();
                }
                showcasePanel.showOverview(useDesktopMode);
            }
        });
        if (!useDesktopMode) {
            detailTools.add(showOverviewButton);
            detailTools.add(new LayoutSpacer(5, 1));
        }

        printButton = new ToolStripButton();
        printButton.setWidth(20);
        printButton.setDisabled(true);
        printButton.setLayoutAlign(VerticalAlignment.CENTER);
        //if (useDesktopMode) printButton.setTitle(M.printButtonTitle().asString());
        printButton.setIcon("silk/printer.png");
        printButton.setPrompt("Print");
        printButton.setShowFocused(false);
        printButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                final Canvas pane;
                if (useDesktopMode) {
                    final Tab tab = mainTabSet.getSelectedTab();
                    pane = tab.getPane();
                } else {
                    pane = splitPane.getDetailPane();
                }
                if (pane instanceof ShowcasePanel) {
                    Canvas.showPrintPreview(((ShowcasePanel)pane).viewPanel);
                }
            }
        });

        sourceButton = new ToolStripButton();
        sourceButton.setWidth(20);
        sourceButton.setDisabled(true);
        sourceButton.setLayoutAlign(VerticalAlignment.CENTER);
        if (useDesktopMode) sourceButton.setTitle(M.viewSourceButtonTitle().asString());
        sourceButton.setIcon("silk/page_white_cup.png");
        sourceButton.setShowFocused(false);
        sourceButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                final Canvas pane;
                if (useDesktopMode) {
                    final Tab tab = mainTabSet.getSelectedTab();
                    pane = tab.getPane();
                } else {
                    pane = splitPane.getDetailPane();
                }
                if (pane instanceof ShowcasePanel) {
                    final ShowcasePanel showcasePanel = (ShowcasePanel)pane;
                    SourceEntity[] sourceUrls = showcasePanel.getSourceUrls();
                    showcasePanel.showSource(sourceUrls, 640, 600, useDesktopMode);
                }
            }
        });
        detailTools.add(sourceButton);
        detailTools.add(new LayoutSpacer(5, 1));
        detailTools.add(printButton);

        if (useDesktopMode) {
            splitPane.setShowDetailToolStrip(false);
            final Tab tab = new Tab();
            tab.setID("main_tab");
            tab.setTitle(M.homeMainTabTitle().asString());
//            tab.setIcon("silk/database_connect.png");
            tab.setPaneMargin(0);
            tab.setPane(homePanel);
            mainTabSet.addTab(tab);

            final List<Object> actualControls = new ArrayList<Object>(2 + detailTools.size());
            actualControls.add(TabBarControls.TAB_SCROLLER);
            actualControls.add(TabBarControls.TAB_PICKER);
            actualControls.addAll(detailTools);
            mainTabSet.setTabBarControls(actualControls.toArray(new Object[actualControls.size()]));
            splitPane.setDetailPane(mainTabSet);

            // align paneContainer baseline with the grid search form border
            if (usingTahoe()) splitPane.addDrawHandler(new DrawHandler() {
                @Override
                public void onDraw(DrawEvent event) {
                    int containerOffset = mainTabSet.getPaneContainerInnerTopOffset();
                    mainTabSet.setTop(searchForm.getVisibleHeight() - containerOffset);
                }
            });

        } else {
            splitPane.setDetailTitle(M.homeNodeName(getSGWTProductName()).asString());
            splitPane.setDetailPane(homePanel);
            splitPane.setDetailToolButtons(detailTools.toArray(new Canvas[detailTools.size()]));
            splitPane.addPaneChangedHandler(new PaneChangedHandler() {
                @Override
                public void onPaneChanged(PaneChangedEvent event) {
                    // Disable the search field whenever we're not displaying the 'navigation'
                    // pane so that the user cannot use the Prev button on the virtual keyboard
                    // to focus the field, which is out of sight.
                    searchItem.setDisabled(event.getPane() != CurrentPane.NAVIGATION);
                }
            });
        }
    
        topPane.addMember(splitPane);
        featureExplorer.addMember(topPane);

        if (isc_websiteMode) {
            topPane.addMember(rightPane);
            featureExplorer.addMember(bottomPane);
        }

        main.addMember(featureExplorer);

        if (SC.hasFirebug() && useDesktopMode) {
            Label label = new Label();
            label.setWidth100();
            label.setHeight(50);
            label.setValign(VerticalAlignment.CENTER);
            label.setAlign(Alignment.CENTER);
            label.setContents("Firebug can make the Showcase run slowly. For the best performance, we suggest disabling Firebug on this site.");

            Window fbWindow = new Window();
            fbWindow.setTitle("Firebug Detected");
            fbWindow.setWidth100();
            fbWindow.setHeight(80);
            fbWindow.addItem(label);
            fbWindow.setRedrawOnResize(true);
            main.addMember(fbWindow);
        }

        splitPane.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				pageResized();
			}
		});

        main.addResizedHandler(new ResizedHandler() {
			@Override
			public void onResized(ResizedEvent event) {
				pageResized();
			}
		});

        main.draw();

        if (leaveMinimalUISpace) {
            Page.addOrientationChangeHandler(new OrientationChangeHandler() {
                @Override
                public void onOrientationChange(OrientationChangeEvent event) {
                    com.google.gwt.user.client.Window.scrollTo(0, 0);
                    Canvas.setDefaultPageSpace(Page.getOrientation() == PageOrientation.LANDSCAPE ? 20 : 0);
                }
            });
        }

        // Add history listener
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                onHistoryChanged(event.getValue());
            }
        });

        final Element elem = Document.get().getElementById("loadingWrapper");
        if (elem != null) {
            elem.removeFromParent();
        }
    }

    private void pageResized() {
        if (isc_websiteMode) {
            if (Page.getWidth() <= 1050) {
                rightPane.hide();
                bottomPane.show();
            } else {
                rightPane.show();
                bottomPane.hide();
            }
        }
    }

    private Menu createContextMenu() {
        Menu menu = new Menu();
        menu.setWidth(140);

        MenuItemIfFunction enableCondition = new MenuItemIfFunction() {
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                int selectedTab = mainTabSet.getSelectedTabNumber();
                return selectedTab != 0;
            }
        };

        MenuItem closeItem = new MenuItem("<u>C</u>lose");
        closeItem.setEnableIfCondition(enableCondition);
        closeItem.setKeyTitle("Alt+C");
        KeyIdentifier closeKey = new KeyIdentifier();
        closeKey.setAltKey(true);
        closeKey.setKeyName("C");
        closeItem.setKeys(closeKey);
        closeItem.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                int selectedTab = mainTabSet.getSelectedTabNumber();
                mainTabSet.removeTab(selectedTab);
                mainTabSet.selectTab(selectedTab - 1);
            }
        });

        MenuItem closeAllButCurrent = new MenuItem("Close All But Current");
        closeAllButCurrent.setEnableIfCondition(enableCondition);
        closeAllButCurrent.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                int selected = mainTabSet.getSelectedTabNumber();
                Tab[] tabs = mainTabSet.getTabs();
                int[] tabsToRemove = new int[tabs.length - 2];
                int cnt = 0;
                for (int i = 1; i < tabs.length; i++) {
                    if (i != selected) {
                        tabsToRemove[cnt] = i;
                        cnt++;
                    }
                }
                mainTabSet.removeTabs(tabsToRemove);
            }
        });

        MenuItem closeAll = new MenuItem("Close All");
        closeAll.setEnableIfCondition(enableCondition);
        closeAll.addClickHandler(new ClickHandler() {
            public void onClick(MenuItemClickEvent event) {
                Tab[] tabs = mainTabSet.getTabs();
                int[] tabsToRemove = new int[tabs.length - 1];

                for (int i = 1; i < tabs.length; i++) {
                    tabsToRemove[i - 1] = i;
                }
                mainTabSet.removeTabs(tabsToRemove);
                mainTabSet.selectTab(0);
            }
        });

        menu.setItems(closeItem, closeAllButCurrent, closeAll);
        return menu;
    }

    private void disableDetailTools() {
        printButton.disable();
        sourceButton.disable();
        showOverviewButton.disable();

        if (splitPane.getDeviceMode() == DeviceMode.DESKTOP) {
            // TabSet.tabBarControls is not writable.
            //mainTabSet.setTabBarControls((Object[])null);
        } else {
            splitPane.setDetailToolButtons((Canvas[])null);
        }
    }

    private void enableDetailTools(boolean isNoSource) {
        printButton.enable();
        if (!isNoSource) sourceButton.enable();
        else sourceButton.disable();
        showOverviewButton.enable();

        if (splitPane.getDeviceMode() == DeviceMode.DESKTOP) {
            // TabSet.tabBarControls is not writable.
            //mainTabSet.setTabBarControls(detailTools.toArray(new Object[detailTools.size()]));
        } else {
            splitPane.setDetailToolButtons(detailTools.toArray(new Canvas[detailTools.size()]));
        }
    }

    protected void showSample(TreeNode node, NavigationDirection direction) {
        final boolean autotest = ShowcaseConfiguration.getSingleton().isOpenForTesting();
        final boolean useDesktopMode = splitPane.getDeviceMode() == DeviceMode.DESKTOP;
        assert !autotest || useDesktopMode;
        assert !useDesktopMode || mainTabSet != null;

        if (!(node instanceof ExplorerTreeNode)) {
            SC.logWarn("Showcase.showSample() must be passed an ExplorerTreeNode");
            return;
        }
        if (node instanceof CommandTreeNode) {
            disableDetailTools();
            final CommandTreeNode commandTreeNode = (CommandTreeNode)node;
            commandTreeNode.getCommand().execute();
            return;
        }

        final ExplorerTreeNode explorerTreeNode = (ExplorerTreeNode)node;

        // clear auto-opened folders; show new node
        revertState(false);
        showNode(explorerTreeNode, false);

        if (node instanceof FolderTreeNode && sideNav.getTree().hasChildren(node)) {
            final FolderTreeNode folderTreeNode = (FolderTreeNode)explorerTreeNode;
            final String folderName = folderTreeNode.getHTML();

            String panelID = folderTreeNode.getNodeID();

            String icon = folderTreeNode.getIcon();
            if (icon == null) {
                icon = "silk/plugin.png";
            }

            if (useDesktopMode) {
                Tab tab = null;
                if (panelID != null) {
                    String tabID = panelID + "_tab";
                    tab = mainTabSet.getTab(tabID);
                }
                if (tab == null) {
                    tab = new Tab();
                    tab.setID(folderTreeNode.getNodeID() + "_tab");
                    // store history token on tab so that when an already open is selected, one
                    // can retrieve the history token and update the URL
                    tab.setAttribute("historyToken", folderTreeNode.getNodeID());
                    tab.setContextMenu(contextMenu);

                    tab.setTitle("<nobr>" + Canvas.imgHTML(icon, 16, 16) + "&nbsp;<span " +
                        "style='display:inline-block;line-height:16px;vertical-align:" +
                        "text-top'>" + folderName + "</span></nobr>");

                    Window window = new Window();
                    window.setTitle(tab.getTitle());
                    window.setWidth(500);
                    window.setHeight(375);
                    window.setKeepInParentRect(true);
                    window.setTop(30);
                    window.setLeft(30);
                    window.setCanDragResize(true);

                    final Canvas tableCanvas = createTableCanvas(folderTreeNode);
                    window.addItem(tableCanvas);

                    VLayout panel = new VLayout();
                    panel.addChild(window);
                    tab.setPane(panel);

                    tab.setCanClose(true);
                    mainTabSet.addTab(tab);
                    mainTabSet.selectTab(tab);
                }
                mainTabSet.selectTab(tab);
            } else {
                final Canvas panel = createTableCanvas(folderTreeNode);
                panel.setOverflow(Overflow.AUTO);
                final Canvas oldDetailPane = splitPane.getDetailPane();
                splitPane.setDetailPane(panel);
                if (oldDetailPane != null && oldDetailPane != homePanel) {
                    oldDetailPane.destroy();
                }
                updateSampleIcon(icon);
                splitPane.showDetailPane(folderName, M.shortNavigationPaneTitle().asString(),
                                         direction);
            }

            disableDetailTools();
            History.newItem(folderTreeNode.getNodeID(), false);
        } else {
            final PanelFactory factory;
            if ("main".equals(explorerTreeNode.getNodeID())) {
                showHomePanel();
            } else if ((factory = explorerTreeNode.getFactory()) != null) {
                final String sampleName = explorerTreeNode.getHTML();
                String icon = explorerTreeNode.getIcon();
                if (icon == null) {
                    icon = "silk/application_view_list.png";
                }

                if (useDesktopMode) {
                    Tab tab = null;
                    String panelID = factory.getID();
                    if (panelID != null) {
                        String tabID = panelID + "_tab";
                        tab = mainTabSet.getTab(tabID);
                    }
                    final Canvas panel;
                    if (tab == null) {
                        if (autotest) {
                            panel = SampleResultsManager.create(mainTabSet, factory);
                        } else if (factory instanceof AdvancedPanelFactory) {
                            final AdvancedPanelFactory advancedFactory = (AdvancedPanelFactory)factory;
                            panel = (advancedFactory.isEnabled() ? advancedFactory.create() : advancedFactory.getDisabledViewPanel());
                        } else {
                            panel = factory.create();
                        }
                        if (panel instanceof ShowcasePanel) {
                            String betaMessage = "";
                            if (explorerTreeNode.getHTML().contains("BETA")) {
                                betaMessage = BETA_MESSAGE + preReleaseVersion + ".";
                            }
                            ((ShowcasePanel)panel).setBetaMessage(betaMessage);
                            ((ShowcasePanel)panel).showOverview(useDesktopMode);
                        }
                        tab = new Tab();
                        tab.setID(factory.getID() + "_tab");
                        // store history token on tab so that when an already open is selected,
                        // one can retrieve the history token and update the URL
                        tab.setAttribute("historyToken", explorerTreeNode.getNodeID());
                        tab.setContextMenu(contextMenu);
                        tab.setTitle("<nobr>" + Canvas.imgHTML(icon, 16, 16) + "&nbsp;" +
                            "<span style='display:inline-block;line-height:16px;" +
                            "vertical-align:text-top'>" + sampleName + "</span></nobr>");
                        tab.setPane(panel);
                        tab.setCanClose(true);
                        mainTabSet.addTab(tab);
                    } else {
                        panel = tab.getPane();
                    }
                    if (panel instanceof ShowcasePanel) {
                        enableDetailTools(((ShowcasePanel)panel).isNoSource());
                    } else {
                        disableDetailTools();
                    }
                    assert tab != null;
                    mainTabSet.selectTab(tab);
                } else {
                    final Canvas panel = autotest ? 
                        SampleResultsManager.create(mainTabSet, factory) : factory.create();
                    panel.setOverflow(Overflow.AUTO);
                    if (panel instanceof ShowcasePanel) {
                        ((ShowcasePanel)panel).setLayoutMargin(15);
                        enableDetailTools(((ShowcasePanel)panel).isNoSource());
                    } else {
                        disableDetailTools();
                    }
                    final Canvas oldDetailPane = splitPane.getDetailPane();
                    splitPane.setDetailPane(panel);
                    if (oldDetailPane != null && oldDetailPane != homePanel) {
                        oldDetailPane.destroy();
                    }
                    updateSampleIcon(icon);
                    splitPane.showDetailPane(sampleName, M.shortNavigationPaneTitle().
                                             asString(), direction);
                }
            }
        }
    }

    private Canvas createTableCanvas(FolderTreeNode folderTreeNode) {
        final Layout layout = new VLayout(10);
        layout.setLayoutMargin(10);
        final List<Canvas> layoutMembers = new ArrayList<Canvas>();

        if (folderTreeNode.getDescription() != null) {
            String descriptionText = "<p class='intro-para'>" + 
                folderTreeNode.getDescription() + "</p>";
            Canvas descriptionCanvas = new Canvas();
            descriptionCanvas.setPadding(10);
            descriptionCanvas.setContents(descriptionText);
            layoutMembers.add(descriptionCanvas);
        }

        TreeNode[] children = sideNav.getTree().getChildren(folderTreeNode);
        for (TreeNode l: children) {
            System.out.println("leave: " + l.getName());
        }
        int numRows = (int) Math.round(children.length / 2f);
        StringBuilder buf = new StringBuilder();
        int firstColIndex = 0;
        int secondColIndex = numRows;
        buf.append("<table class='explorerFolderList' align='center' cellSpacing='5'>");
        for (int i = 0; i < numRows; i++) {
            ExplorerTreeNode node1 = (ExplorerTreeNode) children[firstColIndex++];
            ExplorerTreeNode node2 = (secondColIndex < children.length ? 
                                      (ExplorerTreeNode) children[secondColIndex++] : null);

            this._htmlForCell(node1, buf);
            buf.append("<td width=10>&nbsp;</td>");
            this._htmlForCell(node2, buf);
            buf.append("</tr>");
        }
        buf.append("</table>");

        final Canvas tableCanvas = new Canvas();
        tableCanvas.setContents(buf.toString());
        layoutMembers.add(tableCanvas);

        layout.setMembers(layoutMembers.toArray(new Canvas[layoutMembers.size()]));
        return layout;
    }

    private void _htmlForCell(ExplorerTreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("<td>&nbsp;</td>");
            return;
        }

        String icon = node.getIcon() != null ? node.getIcon() : "silk/plugin.png";
        String imgHTML = Canvas.imgHTML(icon, 16, 16);

        sb.append("<td>");
        sb.append(imgHTML);
        sb.append("&nbsp;");
        sb.append("<a target='_top' href='#" + node.getNodeID() + "'>" + node.getHTML() + "</a>");
        sb.append("</td>");
    }

    private void updateSampleIcon(String icon) {
        if (splitPane.getDeviceMode() == DeviceMode.DESKTOP) {
            ((Label)splitPane.getCanvasAutoChild("detailTitleLabel")).setIcon(icon);
        } else {
            final Label titleLabel = splitPane.getNavigationBar().getTitleLabel();
            titleLabel.setStyleName("navBarHeader");
            titleLabel.setIcon(icon);
        }
    }

    private void showHomePanel() {
        disableDetailTools();

        if (splitPane.getDeviceMode() == DeviceMode.DESKTOP) {
            final Tab tab = mainTabSet.getTab("main_tab");
            assert tab != null;
            mainTabSet.selectTab(tab);
        } else {
            splitPane.setDetailPane(homePanel);
            splitPane.showDetailPane(M.homeNodeName(getSGWTProductName()).asString(), 
                                     M.shortNavigationPaneTitle().asString());
            if (homePanel.isDrawn()) homePanel.redraw();
            updateSampleIcon(null);
        }
    }

    private void onHistoryChanged(String historyToken) {
        if (historyToken == null || "".equals(historyToken) || "main".equals(historyToken)) {
            showHomePanel();
            final ListGridRecord selectedRecord = sideNav.getSelectedRecord();
            if (selectedRecord != null) {
                sideNav.deselectRecord(selectedRecord);
            }
            sideNav.selectRecord(0);
            new Timer() {
                @Override
                public void run() {
                    sideNav.scrollToRow(0, VerticalAlignment.TOP);
                }
            }.schedule(100);
        } else {
            ExplorerTreeNode[] showcaseData = sideNav.getShowcaseData();
            for (final ExplorerTreeNode explorerTreeNode : showcaseData) {
                final String nodeID = explorerTreeNode.getNodeID();
                if (historyToken.equals(nodeID)) {
                    showSample(explorerTreeNode, null);
                    final ListGridRecord selectedRecord = sideNav.getSelectedRecord();
                    if (selectedRecord != null) {
                        sideNav.deselectRecord(selectedRecord);
                    }
                    sideNav.selectRecord(explorerTreeNode);
                    final int r = sideNav.getRecordIndex(explorerTreeNode);
                    if (r >= 0) {
                        new Timer() {
                            @Override
                            public void run() {
                                sideNav.scrollToRow(r, VerticalAlignment.CENTER);
                            }
                        }.schedule(100);
                    }
                    Tree tree = sideNav.getData();
                    TreeNode categoryNode = tree.getParent(explorerTreeNode);
                    while (categoryNode != null && !"/".equals(tree.getName(categoryNode))) {
                        tree.openFolder(categoryNode);
                        categoryNode = tree.getParent(categoryNode);
                    }
                }
            }
        }
    }

    private void revertState(boolean preserveSearch) {
        if (!preserveSearch) {
            if (lastMatch != null) {
                lastMatch.setHTML(lastName);
                sideNav.refreshRow(sideNav.getRecordIndex(lastMatch));
            }
            lastValue = null;
            lastMatch = null;
            lastName = null;

            searchForm.clearValue("search");
        }
        if (lastOpenedFolders != null) {
            for (int i = 0; i < lastOpenedFolders.size(); i++) {
                sideNav.getTree().closeFolder(lastOpenedFolders.get(i));
            }
        }
        lastOpenedFolders = null;
    }

    private void showNode(ExplorerTreeNode node, boolean saveLastOpened) {
        if (saveLastOpened) lastOpenedFolders = new ArrayList<ExplorerTreeNode>();
        
        // open intervening folders to the requested node
        TreeNode[] parents = (sideNav.getTree().getParents(node) != null) ? 
                              sideNav.getTree().getParents(node) : null;
        if (parents != null) {
            for (int i = 0; i < parents.length; i++) {
                TreeNode parent = parents[i];
                if (!sideNav.getTree().isOpen(parent)) {
                    if (saveLastOpened) lastOpenedFolders.add((ExplorerTreeNode)parent);
                        sideNav.getTree().openFolder(parent);
                }
            }
        }
        // if the matched node is a folder, auto-expand it (probably want to see what's inside)
        if (sideNav.getTree().isFolder(node) && !sideNav.getTree().isOpen(node)) {
            if (saveLastOpened) lastOpenedFolders.add(node);
            sideNav.getTree().openFolder(node);
        }
    }
	
    private void findNode() {
        if ((sideNav == null) || (sideNav.getData() == null)) return;
        String search = (String)searchForm.getValue("search");
        if (search == null) {
            revertState(false);
            return;
        }
        search = search.toLowerCase();
        boolean findNext = ((lastMatch != null) && (lastValue.equalsIgnoreCase(search))) ? true : false;
        lastValue = search;
        ExplorerTreeNode[] des = sideNav.getShowcaseData();

        int startIndex = 0;
        if (lastMatch != null) {
            for (int i = 0; i < des.length; i++) {
                if (des[i].getNodeID().equals(lastMatch.getNodeID())) {
                    startIndex = i;
                    break;
                }
            }
        }
        if (findNext) startIndex++;

        if (lastMatch != null) {
            lastMatch.setHTML(lastName);
            lastName = null;
            sideNav.refreshRow(sideNav.getRecordIndex(lastMatch));
            lastMatch = null;
        }
        ExplorerTreeNode match = findNext(des, startIndex, search);
        if (match == null) match = findNext(des, 0, search);

        if (match != null) {
            lastMatch = match;

            revertState(true);
            this.showNode(match, true);

            int recordIndex = sideNav.getRecordIndex(match);
            sideNav.refreshRow(recordIndex);
            sideNav.scrollToRow(recordIndex);
        }
    }
	
    private ExplorerTreeNode findNext (ExplorerTreeNode[] des, int startIndex, String search) {
        for (int i = startIndex; i < des.length; i++) {
            ExplorerTreeNode node = des[i];
            if (node.getName().toLowerCase().contains(search)) {
                lastName = node.getHTML();
                String newValue = null;
                if (lastName.matches("/<.*>/")) {
                    // if it looks like html, make sure not to replace in tags
        	        RegExp searchRe = RegExp.compile("(^|>)([^<]*?)("+search+")", "ig");
                    newValue = searchRe.replace(node.getHTML(), "$1$2<span style='background-color:#00B2FA;'>$3</span>");
                } else {
                    RegExp searchRe = RegExp.compile("("+search+")", "ig");
                    newValue = searchRe.replace(node.getHTML(), "<span style='background-color:#00B2FA;'>$1</span>");
                }
                node.setHTML(newValue);
                return node;
            }
        }
        return null;
    }

    public static class ShowcaseTabSet extends TabSet {
        public native int getPaneContainerInnerTopOffset() /*-{
            var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(),
                paneContainer = self.paneContainer;                
            return paneContainer.top + paneContainer.getTopBorderSize();
        }-*/;            
    }

    // actions on the Showcase allowed to the TileView

    public void incrementalSearch(String text) {
        revertState(false);
        if (text != null) {
            searchForm.setValue("search", text);
            findNode();
        }
    }

    public boolean iterateCurrentMatch(String text) {
        // text is guaranteed not to be null
        if (text.equals(searchForm.getValue("search"))) {
            findNode();
            return true;
        }
        return false;
    }

    public void clearSelectedSamples() {
        sideNav.deselectAllRecords();
    }
}
