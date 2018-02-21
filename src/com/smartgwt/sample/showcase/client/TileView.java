package com.smartgwt.sample.showcase.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.safehtml.shared.SafeHtml;

import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.Browser;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.data.CommandTreeNode;
import com.smartgwt.sample.showcase.client.data.ExplorerTreeNode;
import com.smartgwt.sample.showcase.client.data.ShowcaseData;

public class TileView extends VLayout {
    private static final ShowcaseMessages M = ShowcaseMessages.INSTANCE;

    private static final native boolean _useRoundedSearchItem() /*-{
        var isc = $wnd.isc;
        return (!isc.Browser.isIE || isc.Browser.isIE9);
    }-*/;

    private final boolean useDesktopMode;
    private final ShowcaseNavigator navigator;

    private Map<String, Integer> rankOfSamples;
    private boolean considerForRanking;

    private Integer maxResults;

    private TileGrid tileGrid;
    private final String idSuffix = SideNavTree.ID_SUFFIX;
    private DynamicForm filterForm;

    private TextItem searchItem;
    private SliderItem numSamplesItem;
    private ShowcaseCheckboxItem ascendingItem;
    // this checkBox was disabled because of it does nothing.
    //private CheckboxItem disabledModeCB;

    private ShowcaseCheckboxItem cubesCB;
    private ShowcaseCheckboxItem newSamplesCB;
    private ShowcaseCheckboxItem chartingCB;
    private ShowcaseCheckboxItem componentXmlCB;
    private ShowcaseCheckboxItem javaDataIntegrationCB;
    private ShowcaseCheckboxItem validationCB;
    private ShowcaseCheckboxItem sqlCB;
    private ShowcaseCheckboxItem hibernateBeansCB;
    private ShowcaseCheckboxItem jpaCB;
    private ShowcaseCheckboxItem customDataSourcesCB;
    private ShowcaseCheckboxItem transactionsCB;
    private ShowcaseCheckboxItem exportCB;
    private ShowcaseCheckboxItem uploadDownloadCB;
    private ShowcaseCheckboxItem serverScriptingCB;
    private ShowcaseCheckboxItem dragDropDataBindingCB;
    private ShowcaseCheckboxItem realTimeMessagingCB;
    private ShowcaseCheckboxItem largeTreesCB;
    private ShowcaseCheckboxItem webServicesAndRssCB;
    private ShowcaseCheckboxItem dashboardsToolsCB;
    private ShowcaseCheckboxItem betaSamplesCB;
    // ---- OR ----
    private SelectItem categoriesItem;

    private DynamicForm categoriesForm;

    private Tree tree;

    public interface ShowcaseCustomTileMetaFactory extends BeanFactory.MetaFactory {  
        BeanFactory<ShowcaseCustomTile> getShowcaseCustomTileFactory();  
    }

    public TileView(final TreeNode[] showcaseData, final boolean useDesktopMode,
                    final boolean hasBetaSamples, final ShowcaseNavigator navigator)
    {
        this.navigator = navigator;
        this.useDesktopMode = useDesktopMode;

        tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.setNameProperty("name");
        tree.setOpenProperty("isOpen");
        tree.setIdField("nodeID");
        tree.setParentIdField("parentNodeID");

        tree.setRootValue("root" + idSuffix);

        tree.setData(showcaseData);

        boolean usingTahoe = Showcase.usingTahoe();
        if (usingTahoe) setStyleName("homeInterfaceTahoe");

        setMembersMargin(usingTahoe ? 15 : 5);

        if (useDesktopMode) {
            setLayoutTopMargin   (usingTahoe ? 12 : 5);
            setLayoutRightMargin (usingTahoe ? 15 : 10);
            setLayoutLeftMargin  (usingTahoe ? 15 : 10);
            setLayoutBottomMargin(usingTahoe ? 15 : 5);
        }

        setWidth100();
        setOverflow(Overflow.HIDDEN);

        GWT.create(ShowcaseCustomTileMetaFactory.class);

        tileGrid = new TileGrid() {
			@Override
			public Canvas getTile(int recordNum) {
				ShowcaseCustomTile customTile = (ShowcaseCustomTile) super.getTile(recordNum);
				if (customTile != null) customTile.applyRecord();
				return customTile;
			}
		};
        if (useDesktopMode) tileGrid.setStyleName("showcaseTileGrid");
        tileGrid.setShowEdges(false);

        // Mobile height is not half of "normal" because there needs to be room for the
        // label, especially since the labels tend to wrap, so they require 2-3 lines.
        tileGrid.setTileWidth(useDesktopMode ? (usingTahoe ? 135 : 140) : 70);
        tileGrid.setTileHeight(useDesktopMode ? (usingTahoe ? 135: 130) :
                                                (usingTahoe ? 90 : 100));
        if (usingTahoe) tileGrid.setStyleName("showcaseTileGridTahoe");

        tileGrid.setWidth100();
        tileGrid.setHeight100();
        tileGrid.setShowAllRecords(true);
        tileGrid.setTileConstructor(ShowcaseCustomTile.class.getName());  
        tileGrid.setAutoFetchData(false);
        tileGrid.setAnimateTileChange(true);
        tileGrid.setValuesShowDown(usingTahoe);
        tileGrid.setEmptyMessage("No samples match your criteria.");
        tileGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                Record record = event.getRecord();
                showSample(record);
            }
        });
    
        filterForm = new DynamicForm();
        filterForm.setFixedColWidths(!usingTahoe);
        if (useDesktopMode) {
            filterForm.setNumCols(9);
            filterForm.setColWidths(60, 125, "40%", "20%", 100, 230, "20%", 100, "20%");
        } else {
            filterForm.setNumCols(3);
            filterForm.setColWidths("*", 10, "*");
        }
        filterForm.setAutoFocus(false);
        if (usingTahoe) {
            filterForm.setStyleName("showcaseFilterFormTahoe");
        } else {
            filterForm.setBorder("1px solid #9C9C9C");
            filterForm.setPadding(5);
        }

        searchItem = new TextItem("description_search", M.searchTitle().asString());
        searchItem.setWidth("*");
        searchItem.setBrowserAutoCorrect(false);
        searchItem.setTitleAlign(Alignment.RIGHT);
        searchItem.setColSpan(useDesktopMode ? 2 : 1);
        searchItem.setTitleOrientation(useDesktopMode ? TitleOrientation.LEFT :
                                                        TitleOrientation.TOP);
        if (usingTahoe) searchItem.setTitleStyle("explorerSearchItemTitleTahoe");

        searchItem.addKeyPressHandler(new KeyPressHandler() {
            public void onKeyPress(KeyPressEvent event) {
                String searchText = (String)searchItem.getValue();
                if (searchText == null) return;

                // hitting enter searches for the next match in the SideNavTree
                if ("Enter".equals(event.getKeyName()) && useDesktopMode) {
                    if (navigator.iterateCurrentMatch(searchText)) event.cancel();
                }
            }
        });
        searchItem.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent event) {
                navigator.incrementalSearch((String)event.getValue());
                updateTilesOnPause();
            }
        });
        searchItem.addIconClickHandler(new IconClickHandler() {
            public void onIconClick(IconClickEvent event) {
                if ("clear".equals(event.getIcon().getName())) {
                    filterForm.reset();
                    if (useDesktopMode) {
                        cubesCB.setValue(true);
                        ascendingItem.setValue(true);
                    } else {
                        categoriesItem.setValue(new String[] {"cubes", "new_category", "charts",
                                                              "portal", "beat_samples"});
                    }
                    navigator.incrementalSearch(null);
                }
                updateTiles();
            }
        });

        final FormItemIcon searchIcon = new FormItemIcon();
        searchIcon.setName("search");
        searchIcon.setInline(true);
        searchIcon.setAttribute("imgOnly", true);
        searchIcon.setSrc("[SKINIMG]actions/view.png");
        searchIcon.setWidth(16);
        searchIcon.setHeight(16);
        searchIcon.setShowRTL(true);
        searchIcon.setHspace(5);

        final FormItemIcon clearIcon = new FormItemIcon();
        int closeIconSize = usingTahoe ? 16 : 10;
        clearIcon.setName("clear");
        clearIcon.setInline(true);
        clearIcon.setAttribute("imgOnly", true);
        clearIcon.setSrc("[SKINIMG]actions/close.png");
        clearIcon.setWidth(closeIconSize);
        clearIcon.setHeight(closeIconSize);
        clearIcon.setHspace(3);

        searchItem.setIcons(searchIcon, clearIcon);
        if (usingTahoe) {
            searchItem.setTextBoxStyle("explorerSearchItemTahoe");
        } else if (_useRoundedSearchItem()) {
            searchItem.setTextBoxStyle("explorerSearchItem");
        }

        final List<FormItem> filterFormItems = new ArrayList<FormItem>();
        filterFormItems.add(searchItem);
        {
            final SpacerItem spacerItem = new SpacerItem();
            spacerItem.setWidth(1);
            filterFormItems.add(spacerItem);
        }

        if (useDesktopMode) {
            numSamplesItem = new SliderItem("numSamples");
            numSamplesItem.setTitle(M.numSamplesTitle().asString());
            numSamplesItem.setTitleOrientation(TitleOrientation.LEFT);
            numSamplesItem.setColSpan(1);
            numSamplesItem.setTitleAlign(Alignment.RIGHT);
            numSamplesItem.setMinValue(1.0);
            // grep '^ *new ExplorerTreeNode' ShowcaseData.java | grep -o 'new [^.,]*\.Factory()' | sort | uniq | wc
            numSamplesItem.setMaxValue(141.0);
            numSamplesItem.setDefaultValue(100);
            numSamplesItem.setHeight(50);
            numSamplesItem.setOperator(OperatorId.LESS_THAN);
            filterFormItems.add(numSamplesItem);

            SpacerItem spacerItem = new SpacerItem();
            spacerItem.setWidth(1);
            filterFormItems.add(spacerItem);            

            ascendingItem = new ShowcaseCheckboxItem("chkSortDir");
            ascendingItem.setTitle(M.ascendingTitle().asString());
            ascendingItem.setValue(true);
            filterFormItems.add(ascendingItem);

            StaticTextItem rowSeparatorItem = new StaticTextItem();
            rowSeparatorItem.setStartRow(true);
            rowSeparatorItem.setEndRow(true);
            rowSeparatorItem.setColSpan("*");
            rowSeparatorItem.setWidth("*");
            rowSeparatorItem.setHeight(5);
            rowSeparatorItem.setShouldSaveValue(false);
            rowSeparatorItem.setCellStyle("rowSeparatorItem");
            rowSeparatorItem.setShowTitle(false);
            filterFormItems.add(rowSeparatorItem);
        }
        // End of the row

        //disabledModeCB = new ShowcaseCheckboxItem("disabledModeCB", M.disabledModeTitle());
        //filterFormItems.add(disabledModeCB);

        final List<FormItem> categoriesFormItems = new ArrayList<FormItem>();

        if (useDesktopMode) {
            cubesCB      = new ShowcaseCheckboxItem("cubesCB",      M.cubesCategoryName());
            cubesCB.setValue(true);
            newSamplesCB = new ShowcaseCheckboxItem("newSamplesCB", M.newSamplesCategoryName());
            newSamplesCB.setValue(true);
            chartingCB   = new ShowcaseCheckboxItem("chartingCB",   M.chartingCategoryName());
            chartingCB.setValue(true);

            componentXmlCB        = new ShowcaseCheckboxItem("componentXmlCB", 
                                                         M.componentXmlCategoryName());
            javaDataIntegrationCB = new ShowcaseCheckboxItem("javaDataIntegrationCB", 
                                                         M.javaDataIntegrationCategoryName());
            validationCB = new ShowcaseCheckboxItem("validationCB", M.validationCategoryName());
            sqlCB        = new ShowcaseCheckboxItem("sqlCB",        M.sqlCategoryName());
            hibernateBeansCB = new ShowcaseCheckboxItem("hibernateBeansCB", 
                                                        M.hibernateBeansCategoryName());

            jpaCB               = new ShowcaseCheckboxItem("jpaCB", M.jpaCategoryName());
            customDataSourcesCB = new ShowcaseCheckboxItem("customDataSourcesCB", 
                                                           M.customDataSourcesCategoryName());
            transactionsCB   = new ShowcaseCheckboxItem("transactionsCB", 
                                                        M.transactionsCategoryName());
            exportCB         = new ShowcaseCheckboxItem("exportCB", M.exportCategoryName());
            uploadDownloadCB = new ShowcaseCheckboxItem("uploadDownloadCB", 
                                                        M.uploadDownloadCategoryName());
            serverScriptingCB     = new ShowcaseCheckboxItem("serverScriptingCB", 
                                                         M.serverScriptingCategoryName());
            dragDropDataBindingCB = new ShowcaseCheckboxItem("dragDropDataBindingCB", 
                                                         M.dragDropDataBindingCategoryName());
            realTimeMessagingCB   = new ShowcaseCheckboxItem("realTimeMessagingCB", 
                                                         M.realTimeMessagingCategoryName());
            largeTreesCB        = new ShowcaseCheckboxItem("largeTreesCB", 
                                                           M.largeTreesCategoryName());
            webServicesAndRssCB = new ShowcaseCheckboxItem("webServicesAndRssCB", 
                                                           M.webServicesAndRssCategoryName());
            dashboardsToolsCB = new ShowcaseCheckboxItem("dashboardsToolsCB", 
                                                         M.dashboardsToolsCategoryName());
            dashboardsToolsCB.setValue(true);
            betaSamplesCB = new ShowcaseCheckboxItem("betaSamplesCB", M.betaSamplesName());
            betaSamplesCB.setValue(true);

            categoriesFormItems.addAll(Arrays.asList(
                betaSamplesCB, chartingCB, componentXmlCB, cubesCB,
                customDataSourcesCB, dashboardsToolsCB, dragDropDataBindingCB, 
                exportCB, hibernateBeansCB, javaDataIntegrationCB, jpaCB, 
                largeTreesCB, newSamplesCB, realTimeMessagingCB, serverScriptingCB, 
                sqlCB, transactionsCB, uploadDownloadCB, validationCB, webServicesAndRssCB));
            if (!hasBetaSamples) categoriesFormItems.remove(betaSamplesCB);

        } else {
            categoriesItem = new SelectItem("categories", M.categoriesTitle().asString());
            categoriesItem.setTitleOrientation(TitleOrientation.TOP);
            categoriesItem.setColSpan(1);
            categoriesItem.setMultiple(true);
            categoriesItem.setMultipleAppearance(MultipleAppearance.PICKLIST);
            final LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
            if (hasBetaSamples) valueMap.put("beta_samples", M.betaSamplesName().asString());
            valueMap.put("charts", M.chartingCategoryName().asString());
            valueMap.put("component_xml", M.componentXmlCategoryName().asString());
            valueMap.put("cubes", M.cubesCategoryName().asString());
            valueMap.put("custom_ds", M.customDataSourcesCategoryName().asString());
            valueMap.put("portal", M.dashboardsToolsCategoryName().asString());
            valueMap.put("drag_and_drop", M.dragDropDataBindingCategoryName().asString());
            valueMap.put("export", M.exportCategoryName().asString());
            valueMap.put("hibernate", M.hibernateBeansCategoryName().asString());
            valueMap.put("data_integration_java", 
                         M.javaDataIntegrationCategoryName().asString());
            valueMap.put("jpa", M.jpaCategoryName().asString());
            valueMap.put("large_trees", M.largeTreesCategoryName().asString());
            valueMap.put("new_category", M.newSamplesCategoryName().asString());
            valueMap.put("messaging", M.realTimeMessagingCategoryName().asString());
            valueMap.put("scripting", M.serverScriptingCategoryName().asString());
            valueMap.put("sql", M.sqlCategoryName().asString());
            valueMap.put("transactions", M.transactionsCategoryName().asString());
            valueMap.put("upload_download", M.uploadDownloadCategoryName().asString());
            valueMap.put("validation", M.validationCategoryName().asString());
            valueMap.put("data_integration_ws_rss", 
                         M.webServicesAndRssCategoryName().asString());

            categoriesItem.setValueMap(valueMap);
            categoriesItem.setDefaultValue(new String[] {"cubes", "new_category", "charts",
                                                         "portal", "beat_samples"});

            final LinkedHashMap<String, String> valueMapIcons = new LinkedHashMap<String, String>();
            valueMapIcons.put("cubes", ((ExplorerTreeNode)tree.
                find("nodeID", "cubes"+ idSuffix)).getIcon());
            valueMapIcons.put("new_category", ((ExplorerTreeNode)tree.
                find("nodeID", "new_category"+ idSuffix)).getIcon());
            valueMapIcons.put("charts", ((ExplorerTreeNode)tree.
                find("nodeID", "charts"+ idSuffix)).getIcon());
            valueMapIcons.put("component_xml", ((ExplorerTreeNode)tree.
                find("nodeID", "component_xml"+ idSuffix)).getIcon());
            valueMapIcons.put("data_integration_java", ((ExplorerTreeNode)tree.
                find("nodeID", "data_integration_java"+ idSuffix)).getIcon());
            valueMapIcons.put("validation", ((ExplorerTreeNode)tree.
                find("nodeID", "validation"+ idSuffix)).getIcon());
            valueMapIcons.put("sql", ((ExplorerTreeNode)tree.
                find("nodeID", "sql"+ idSuffix)).getIcon());
            valueMapIcons.put("hibernate", ((ExplorerTreeNode)tree.
                find("nodeID", "hibernate"+ idSuffix)).getIcon());
            valueMapIcons.put("jpa", ((ExplorerTreeNode)tree.
                find("nodeID", "jpa"+ idSuffix)).getIcon());
            valueMapIcons.put("custom_ds", ((ExplorerTreeNode)tree.
                find("nodeID", "custom_ds"+ idSuffix)).getIcon());
            valueMapIcons.put("transactions", ((ExplorerTreeNode)tree.
                find("nodeID", "transactions"+ idSuffix)).getIcon());
            valueMapIcons.put("export", ((ExplorerTreeNode)tree.
                find("nodeID", "export"+ idSuffix)).getIcon());
            valueMapIcons.put("upload_download", ((ExplorerTreeNode)tree.
                find("nodeID", "upload_download"+ idSuffix)).getIcon());
            valueMapIcons.put("scripting", ((ExplorerTreeNode)tree.
                find("nodeID", "scripting"+ idSuffix)).getIcon());
            valueMapIcons.put("drag_and_drop", ((ExplorerTreeNode)tree.
                find("nodeID", "drag_and_drop"+ idSuffix)).getIcon());
            valueMapIcons.put("messaging", ((ExplorerTreeNode)tree.
                find("nodeID", "messaging"+ idSuffix)).getIcon());
            valueMapIcons.put("large_trees", ((ExplorerTreeNode)tree.
                find("nodeID", "large_trees"+ idSuffix)).getIcon());
            valueMapIcons.put("data_integration_ws_rss", ((ExplorerTreeNode)tree.
                find("nodeID", "data_integration_ws_rss"+ idSuffix)).getIcon());
            valueMapIcons.put("portal", ((ExplorerTreeNode)tree.
                find("nodeID", "portal"+ idSuffix)).getIcon());
            categoriesItem.setValueIcons(valueMapIcons);

            categoriesFormItems.add(categoriesItem);
        }

        categoriesForm = new DynamicForm();
        categoriesForm.setPadding(0);
        categoriesForm.setBorder("0px solid");
        categoriesForm.setFixedColWidths(!usingTahoe);
        if (useDesktopMode) {
            categoriesForm.setNumCols(4);
            categoriesForm.setColWidths("*", "*", "*", "*");
        } else {
            categoriesForm.setNumCols(1);
            categoriesForm.setColWidths("*");
        }
        categoriesForm.setAutoFocus(false);
        if (usingTahoe) categoriesForm.setStyleName("showcaseFilterFormTahoe");
        categoriesForm.setItems(categoriesFormItems.
                                toArray(new FormItem[categoriesFormItems.size()]));
        categoriesForm.addItemChangedHandler(new ItemChangedHandler() {
            public void onItemChanged(ItemChangedEvent event) {
                FormItem item = event.getItem();
                if (item instanceof CheckboxItem || item == categoriesItem) {
                    updateTiles();
                }
            }
        });

        CanvasItem categoriesFormItem = new CanvasItem();
        categoriesFormItem.setColSpan(useDesktopMode ? 9 : 1);
        categoriesFormItem.setShowTitle(false);
        categoriesFormItem.setCanvas(categoriesForm);
        filterFormItems.add(categoriesFormItem);

        filterForm.setItems(filterFormItems.toArray(new FormItem[filterFormItems.size()]));
        filterForm.addItemChangedHandler(new ItemChangedHandler() {
            public void onItemChanged(ItemChangedEvent event) {
                FormItem item = event.getItem();
                if (item instanceof CheckboxItem || item instanceof SliderItem ||
                    item == categoriesItem) 
                {
                    updateTiles();
                }
            }
        });

        addMember(filterForm);
        addMember(tileGrid);
        updateTiles();
    }

    public native void updateTilesOnPause() /*-{
        var searchItemJ = this.@com.smartgwt.sample.showcase.client.TileView::searchItem,
            formItem = searchItemJ.@com.smartgwt.client.core.JsObject::getJsObj()();
        if (formItem.pendingActionOnPause("tileSearch")) return;
        var selfJ = this;
        formItem.fireOnPause("tileSearch", $entry(function() {
            selfJ.@com.smartgwt.sample.showcase.client.TileView::updateTiles()();
        }));
    }-*/;

    public void updateTiles(String searchText) {
        searchItem.setValue(searchText);
        updateTiles();
        // Don't focusInItem() on mobile because the browser will attempt to scroll the newly-focused
        // searchItem into view, as the SplitPane page transition is underway.
        if (useDesktopMode) filterForm.focusInItem(searchItem);
    }

    private void updateTiles() {
        final String searchText = (String)searchItem.getValue();
        this.considerForRanking = searchText != null && searchText.length() > 0;
        final List<String> categories = new ArrayList<String>();
        if (useDesktopMode) {
            if (cubesCB.getValueAsBoolean()) categories.add("cubes");
            if (newSamplesCB.getValueAsBoolean()) categories.add("new_category");
            if (chartingCB.getValueAsBoolean()) categories.add("charts");
            if (componentXmlCB.getValueAsBoolean()) categories.add("component_xml");
            if (javaDataIntegrationCB.getValueAsBoolean()) categories.add("data_integration_java");
            if (validationCB.getValueAsBoolean()) categories.add("validation");
            if (sqlCB.getValueAsBoolean()) categories.add("sql");
            if (hibernateBeansCB.getValueAsBoolean()) categories.add("hibernate");
            if (jpaCB.getValueAsBoolean()) categories.add("jpa");
            if (customDataSourcesCB.getValueAsBoolean()) categories.add("custom_ds");
            if (transactionsCB.getValueAsBoolean()) categories.add("transactions");
            if (exportCB.getValueAsBoolean()) categories.add("export");
            if (uploadDownloadCB.getValueAsBoolean()) categories.add("upload_download");
            if (serverScriptingCB.getValueAsBoolean()) categories.add("scripting");
            if (dragDropDataBindingCB.getValueAsBoolean()) categories.add("drag_and_drop");
            if (realTimeMessagingCB.getValueAsBoolean()) categories.add("messaging");
            if (largeTreesCB.getValueAsBoolean()) categories.add("large_trees");
            if (webServicesAndRssCB.getValueAsBoolean()) categories.add("data_integration_ws_rss");
            if (dashboardsToolsCB.getValueAsBoolean()) categories.add("portal");
            if (betaSamplesCB.getValueAsBoolean()) categories.add("beta_samples");
        } else {
            categories.addAll(Arrays.asList(categoriesItem.getValues()));
        }

        showTiles(searchText, categories);

        // truncate the node list _after_ sorting it so the most meaningful nodes are kept
        maxResults = useDesktopMode ? numSamplesItem.getValueAsFloat().intValue() : null;
        if (this.considerForRanking) {
            sortAndTruncateNodeList("position", false, maxResults);
        } else {
            boolean sortDir = useDesktopMode ? ascendingItem.getValueAsBoolean() : true;
            sortAndTruncateNodeList("nodeTitle", sortDir, maxResults);
        }
    }

    private void showSample(Record node) {
        boolean isExplorerTreeNode = node instanceof ExplorerTreeNode;
        if (node instanceof CommandTreeNode) {
            CommandTreeNode commandTreeNode = (CommandTreeNode) node;
            commandTreeNode.getCommand().execute();
        } else if (isExplorerTreeNode) {
            ExplorerTreeNode explorerTreeNode = (ExplorerTreeNode)node;
            History.newItem(explorerTreeNode.getNodeID(), true);
        }
    }

    private void showTiles(String searchText, List<String> categories) {

        // sample will be replaced now by the tile search results
        navigator.clearSelectedSamples();

        // clear any existing ranking from previous search
        rankOfSamples = new HashMap<String, Integer>();

        final Set<TreeNode> data = new HashSet<TreeNode>();

        if (searchText != null) {
            TreeNode[] children = tree.getAllNodes();
            applyFilterAccordingToRanking(tree, children, data, searchText);
        } else {
            for (final String category : categories) {
                if (category.equalsIgnoreCase("beta_samples")) {
                    TreeNode[] children = tree.getAllNodes();
                    searchBetaSamples(tree, children, data, searchText, false);
                } else {
                    TreeNode categoryNode = tree.find("nodeID", category + idSuffix);
                    if (categoryNode == null) continue;
                    TreeNode[] children = tree.getChildren(categoryNode);
                    applyFilter(tree, children, data, searchText, false);
                }
            }
        }
        tileGrid.setData((Record[])data.toArray(new Record[data.size()]));
    }

    private void searchBetaSamples(Tree tree, TreeNode[] children, Set<TreeNode> data, 
                                   String searchText, boolean skipCategories) 
    {
        for (int i = 0; i < children.length; i++) {
            final TreeNode child = children[i];
            if (!tree.hasChildren(child)) {
                boolean isExplorerTreeNode = child instanceof ExplorerTreeNode;
                if (isExplorerTreeNode) {
                    final ExplorerTreeNode explorerTreeNode = (ExplorerTreeNode) child;
                    // note that BETA tag is only in HTML
                    if (explorerTreeNode.getHTML().contains("BETA")) { 
                        children[i].setAttribute("description", 
                            explorerTreeNode.getFactory().getDescription());
                        data.add(children[i]);
                    }
                }
            } else if(!skipCategories) {
                searchBetaSamples(tree, tree.getChildren(child), data, searchText, 
                                  skipCategories);
            }
        }
    }

    private void applyFilterAccordingToRanking(Tree tree, TreeNode[] children, 
                                               Set<TreeNode> data, String searchText)
    {
        String[] arraySearchText = searchText.trim().split(" ");
        for (int j = 0; j < arraySearchText.length; j++) {
            if (arraySearchText[j] == null || arraySearchText[j].length() == 0) continue;
            searchText = arraySearchText[j].toLowerCase();

            for (int i = 0; i < children.length; i++) {
                TreeNode child = children[i];
                if (!tree.hasChildren(child) && child.getClass() == ExplorerTreeNode.class) {
                    ExplorerTreeNode explorerTreeNode = (ExplorerTreeNode) child;
                    String canonicalName = explorerTreeNode.getName().toLowerCase();
                    if (canonicalName.contains(searchText)) {
                        int rank = rankSamples(canonicalName, 5);
                        child.setAttribute("position", rank);
                        data.add(child);
                    } else {
                        PanelFactory factory = explorerTreeNode.getFactory();
                        if (factory != null) {
                            String description = factory.getDescription();
                            if (description != null && 
                                description.toLowerCase().contains(searchText)) 
                            {
                                int rank = rankSamples(canonicalName, 1);
                                child.setAttribute("position", rank);
                                data.add(child);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void applyFilter(Tree tree, TreeNode[] children, Set<TreeNode> data, 
                             String searchText, boolean skipCategories) 
    {
        for (int i = 0; i < children.length; i++) {
            TreeNode child = children[i];
            if (!tree.hasChildren(child)) {
                if (searchText == null) data.add(child);

            } else if(!skipCategories) {
                //skip categories when searching all nodes so that duplicates are not included
                //Due to the amount of samples that Java Data Integration has, its sub-categories are visible 
                //in the list of checkboxes so that users can filter samples according to these sub-categories,
                //therefore, the children of these sub-categories are not included in the result when users select
                //the "Java Data Integration" CheckBox 
                ExplorerTreeNode explorerTreeNode = (ExplorerTreeNode) child;
                if (explorerTreeNode.getParentNodeID().equals("data_integration_java")) continue;

                applyFilter(tree, tree.getChildren(child), data, searchText, skipCategories);
            }
        }
    }
    
    private int rankSamples(String name, int amount) {
        if (!this.considerForRanking) return 0;

        if (rankOfSamples.get(name) == null) {
            rankOfSamples.put(name, amount);
        } else {
            rankOfSamples.put(name, (Integer)rankOfSamples.get(name) + amount);
        }
        return (Integer)rankOfSamples.get(name);
    }

    // truncating the sorted array can be done in JSNI without having to copy the nodes
    private native void sortAndTruncateNodeList(String property, boolean ascending,
                                                Integer maxLength)
    /*-{
        var tileGridJ = this.@com.smartgwt.sample.showcase.client.TileView::tileGrid,
            tileGridJS = tileGridJ.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()(),
            data = tileGridJS.data;
        if (data) {
            if (property && property != "") data.sortByProperty(property, ascending);
            if (maxLength != null && data.length > maxLength) data.length = maxLength;
        }
    }-*/;

    public static class ShowcaseCheckboxItem extends CheckboxItem {
        public ShowcaseCheckboxItem(String name) {
            this(name, (String)null);
        }
        public ShowcaseCheckboxItem(String name, SafeHtml html) {
            this(name, html.asString());
        }
        public ShowcaseCheckboxItem(String name, String title) {
            super(name, title);
            if (Showcase.usingTahoe()) setTextBoxStyle("sampleTypeLabelAnchorTahoe");
            setShowTitle(false);
            setWidth(1);
        }
    }
}
