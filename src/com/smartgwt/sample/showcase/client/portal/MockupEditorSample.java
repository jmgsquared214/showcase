package com.smartgwt.sample.showcase.client.portal;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.MockDataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.tools.EditContext;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.EditProxy;
import com.smartgwt.client.tools.HiddenPalette;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.SelectedEditNodesUpdatedEvent;
import com.smartgwt.client.tools.SelectedEditNodesUpdatedHandler;
import com.smartgwt.client.tools.TreePalette;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ChartType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.MockDataType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectedAppearance;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.Snapbar;
import com.smartgwt.client.widgets.chart.FacetChart;
import com.smartgwt.client.widgets.cube.CellRecord;
import com.smartgwt.client.widgets.cube.Facet;
import com.smartgwt.client.widgets.cube.FacetValue;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuBar;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class MockupEditorSample extends ShowcasePanel {

    private static final String DESCRIPTION =
    		"<p>Most UI widgets have built-in editing behaviors that allow them to be configured by just "
    		+ "typing in a simple string of text.</p>"
			+ "<p>A simple Mockup Editor can be created by just enabling these behaviors plus drag and drop "
    		+ "positioning.</p>"
			+ "<p>This sample also shows snap-to-grid dragging.  Hold down shift "
    		+ "before starting a drag to turn off snap-to-grid.  Arrow keys can also "
			+ "be used to move the selected component.</p>";

    // A serialized sample mockup to show initially 
    private static final String sampleMockup = "<VLayout ID=\"VLayout0\" width=\"88\" height=\"65\" left=\"5\" top=\"5\" autoDraw=\"false\"></VLayout><VLayout ID=\"VLayout1\" width=\"88\" height=\"100\" left=\"5\" top=\"5\" autoDraw=\"false\"></VLayout><TabSet ID=\"TabSet0\" width=\"440\" height=\"180\" left=\"20\" top=\"20\" autoDraw=\"false\"><tabs><Tab title=\"View\"><pane ref=\"VLayout0\"/><ID>Tab0</ID></Tab><Tab title=\"Edit\"><pane ref=\"VLayout1\"/><ID>Tab1</ID></Tab></tabs></TabSet><IButton ID=\"IButton0\" width=\"100\" height=\"32\" left=\"360\" top=\"220\" autoDraw=\"false\"><title>Save</title></IButton>";

	private VLayout vLayout;
	private EditPane editPane;
	private boolean addedSelectionHandler = false;

    private static boolean enabledReflection = false;

    public static class Factory extends AdvancedPanelFactory {
        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public HTMLFlow getDisabledViewPanel() {
            final HTMLFlow htmlFlow = new HTMLFlow(
                "<div class='explorerCheckErrorMessage'><p>This example is disabled because it requires the optional " +
                "<a href=\"http://www.smartclient.com/product/index.jsp\" target=\"_blank\">Dashboard &amp; Tools module</a>.</p>" +
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#mockupEditor\" target=\"\">here</a> " +
                "to see this example on SmartClient.com.</p></div>"
            );
            htmlFlow.setWidth100();
            return htmlFlow;
        }

        @Override
        public boolean isEnabled() {
            return SC.hasDashboardAndTools();
        }

        @Override
        public ShowcasePanel createShowcasePanel() {
            return new MockupEditorSample();
        }
    }

    protected boolean isTopIntro() {
        return true;
    }

    public interface MetaFactory extends BeanFactory.MetaFactory {
    	BeanFactory<Canvas> getCanvasBeanFactory();
    	BeanFactory<Img> getImgBeanFactory();
    }

    private static void enableReflection() {
    	if (!enabledReflection) {
    		GWT.create(MetaFactory.class);
    		enabledReflection = true;
    	}
    }

    public Canvas getViewPanel() {
    	enableReflection();

    	createDataSources();

    	TreePalette palette = createPalette();

    	HiddenPalette extraPalette = new HiddenPalette();
    	PaletteNode extraPaletteNode = new PaletteNode();
    	extraPaletteNode.setTitle("Tab");
    	extraPaletteNode.setType("Tab");
    	extraPalette.setData(extraPaletteNode);

    	editPane = new EditPane();
    	editPane.setBorder("1px solid black");
    	editPane.setCanFocus(true);
        // We want all selections/drops to be into the EditPane
    	editPane.setAllowNestedDrops(false);

        // Don't show the label for current selection
    	editPane.setShowSelectedLabel(false);

        // Turn on component inline editing
    	EditContext editContextProperties = new EditContext();
    	editContextProperties.setEnableInlineEdit(true);
        // Enable display of a selectionOutline for the currently selected item
    	editContextProperties.setSelectedAppearance(SelectedAppearance.OUTLINEMASK);
    	editPane.setAutoChildProperties("editContext", editContextProperties);

    	EditProxy editProxyProperties = new EditProxy();
    	editProxyProperties.setAutoMaskChildren(true);
        // Enable snapToGrid for all children.
        // In EditMode this also triggers display of the visual grid when
        // dragging and resizing.
    	editProxyProperties.setChildrenSnapToGrid(true);
    	editPane.setAutoChildProperties("editProxy", editProxyProperties);

    	editPane.setExtraPalettes(extraPalette);

    	// Make the new editPane the default Edit Context for the palette,
    	// to support double-clicking on paletteNodes to create them.
    	editPane.setDefaultPalette(palette);
    	palette.setDefaultEditContext(editPane);

    	// Place sample mockup into editContext
    	editPane.addPaletteNodesFromXML(sampleMockup);

    	DynamicForm quickAddForm = createQuickAddForm();

    	VLayout leftPane = new VLayout();
    	leftPane.setWidth(200);
    	leftPane.setMembersMargin(3);
    	leftPane.setMembers(quickAddForm, palette);

    	VLayout rightPane = new VLayout();
    	rightPane.setWidth100();
    	rightPane.setMembersMargin(10);
    	rightPane.setMembers(editPane, createActionBar());

    	HLayout hLayout = new HLayout();
    	hLayout.setWidth100();
    	hLayout.setHeight100();
    	hLayout.setMembersMargin(20);
    	hLayout.setMembers(leftPane, rightPane);

    	vLayout = new VLayout();
    	vLayout.setWidth100();
    	vLayout.setHeight100();
    	vLayout.setMembersMargin(10);
    	vLayout.setMembers(hLayout);

    	return vLayout;
    }

    // The TreePalette contains components available
    // for use, with default settings.
    private TreePalette createPalette() {
    	final TreePalette palette = new TreePalette() {
    		@Override
			protected String getIcon(Record node, boolean defaultState) {
    			String icon = (node.getAttribute("icon") != null ? "formItemIcons/" + node.getAttribute("icon") : null);
    			if (icon == null && true != node.getAttributeAsBoolean("isFolder")) icon = "blank.gif";
    			if (icon != null) return icon;

				return super.getIcon(node, defaultState);
			}
    	};
    	palette.setHeight100();

    	// The regular ListGrid property "fields"
    	palette.setFields(new ListGridField("title"));

    	palette.setDataSource(DataSource.get("paletteDS"));
    	palette.setAutoFetchData(true);
    	palette.setLoadDataOnDemand(false);

    	palette.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				palette.getData().openAll();
			}
		});

    	return palette;
    }

    public Layout createActionBar() {
        final IButton duplicateButton = new IButton("Duplicate");
        duplicateButton.setAutoFit(true);
        duplicateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                EditNode editNodes[] = getSelectedNodes();
                for (EditNode editNode : editNodes) {
                    editPane.getEditContext().copyEditNodes(editNode);
                    editPane.getEditContext().pasteEditNodes();
                }
            }
        });
        
    	final IButton removeButton = new IButton("Remove");
    	removeButton.setAutoFit(true);
    	removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				removeSelectedItems();
			}
		});

    	final IButton sendToBackButton = new IButton("Send to back");
    	sendToBackButton.setAutoFit(true);
    	sendToBackButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditNode editNodes[] = getSelectedNodes();
				for (EditNode node : editNodes) {
					node.getCanvasLiveObject().sendToBack();
				}
			}
		});

    	final IButton bringToFrontButton = new IButton("Bring to front");
    	bringToFrontButton.setAutoFit(true);
    	bringToFrontButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditNode editNodes[] = getSelectedNodes();
				for (EditNode node : editNodes) {
					node.getCanvasLiveObject().bringToFront();
				}
			}
		});

    	HLayout layout = new HLayout();
    	layout.setWidth100();
    	layout.setHeight(30);
    	layout.setMembersMargin(10);

    	layout.setMembers(duplicateButton, removeButton, sendToBackButton, bringToFrontButton);

    	// Register selection handler *after* editPane is drawn and editContext has been created
    	editPane.addDrawHandler(new DrawHandler() {
			@Override
			public void onDraw(DrawEvent event) {
				if (!addedSelectionHandler) {
			    	editPane.getEditContext().addSelectedEditNodesUpdatedHandler(new SelectedEditNodesUpdatedHandler() {
						@Override
						public void onSelectedEditNodesUpdated(SelectedEditNodesUpdatedEvent event) {
					    	EditNode editNodes[] = getSelectedNodes();
					    	if (editNodes.length == 0) {
					    		// No selection
					    		removeButton.disable();
					    		sendToBackButton.disable();
					    		bringToFrontButton.disable();
					    	} else {
					    		removeButton.enable();
					    		sendToBackButton.enable();
					    		bringToFrontButton.enable();
					    	}
						}
					});
		    		addedSelectionHandler = true;
				}
				
			}
		});

    	return layout;
    }

    private EditNode[] getSelectedNodes() {
    	return (editPane != null ? editPane.getEditContext().getSelectedEditNodes() : new EditNode[] {});
    }

    private void removeSelectedItems() {
    	EditNode[] selection = this.getSelectedNodes();
    	for (EditNode editNode : selection) {
    		// Remove node from editContext and destroy it
    		editPane.getEditContext().removeNode(editNode);
    		editNode.getCanvasLiveObject().destroy();
    	}
    }

    private DynamicForm createQuickAddForm() {
    	DynamicForm form = new DynamicForm();
    	form.setNumCols(1);
    	form.setCellPadding(0);

    	ComboBoxItem quickAddItem = new ComboBoxItem("quickAdd");
    	quickAddItem.setShowTitle(false);
    	quickAddItem.setWidth("*");
    	quickAddItem.setCompleteOnTab(true);
    	quickAddItem.setHint("Quick Add..");
    	quickAddItem.setShowHintInField(true);
    	quickAddItem.setOptionDataSource(DataSource.get("paletteDS"));
    	quickAddItem.setValueField("id");
    	quickAddItem.setDisplayField("title");

        AdvancedCriteria optionCriteria = new AdvancedCriteria(OperatorId.AND);
        optionCriteria.addCriteria("isFolder", OperatorId.NOT_EQUAL, true);
        quickAddItem.setOptionCriteria(optionCriteria);
        
    	quickAddItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				ListGridRecord node = event.getItem().getSelectedRecord();
				if (node != null) {
					EditNode editNode = editPane.getEditContext().addFromPaletteNode(new PaletteNode(node.getJsObj()));
					editNode.getCanvasLiveObject().moveTo(20, 20);
					event.getItem().clearValue();
				}
			}
		});

    	form.setFields(quickAddItem);
    	
    	return form;
    }

    private void createDataSources() {
    	if (DataSource.get("gridMockDS") == null) createGridMockDS();
    	if (DataSource.get("treeMockDS") == null) createTreeMockDS();
    	if (DataSource.get("paletteDS") == null) createPaletteDS();
    }
    
    private void createGridMockDS() {
    	MockDataSource ds = new MockDataSource();
    	ds.setID("gridMockDS");
    	ds.setMockData("Country,Continent,Area,Population,G8?\n" +
	        "United States,North America,9\\,631\\,420,298\\,444\\,215,[x]\n" +
	        "China,Asia,9\\,596\\,960,1\\,313\\,973\\,713,[]\n" +
	        "Japan,Asia,377\\,835,127\\,463\\,611,[x]\n" +
	        "Brazil,South America,8\\,511\\,965,188\\,078\\,227,[]\n" +
	        "[50L,50L,30R,0R,0C]");
    }

    private void createTreeMockDS() {
    	MockDataSource ds = new MockDataSource();
    	ds.setID("treeMockDS");
    	ds.setMockDataType(MockDataType.TREE);
    	ds.setMockData("F Charles Madigen\n" +
    	        " _ Rogine Leger\n" +
    	        " F Gene Porter\n" +
    	        "  _ Olivier Doucet\n" +
    	        "  _ Cheryl Pearson\n" +
    	        "f George Sampson");
    }

    private void createPaletteDS() {
    	// Palette client-only dataSource
    	DataSource ds = new DataSource();
    	ds.setID("paletteDS");
    	ds.setClientOnly(true);

    	DataSourceField pkField = new DataSourceField("id", FieldType.INTEGER);
    	pkField.setPrimaryKey(true);
    	ds.setFields(pkField, new DataSourceField("title", FieldType.TEXT, "Component"), new DataSourceField("isFolder", FieldType.BOOLEAN));

    	List<ListGridRecord> records = new ArrayList<ListGridRecord>();

    	records.add(createPaletteRecord(100, "Grids"));

    	ListGridRecord record = createPaletteRecord(101, "Grid", 100, "ListGrid.png", "ListGrid");
    	ListGrid gridDefaults = new ListGrid();
    	gridDefaults.setWidth(350);
    	gridDefaults.setHeight(115);
    	gridDefaults.setAutoFetchData(true);
    	gridDefaults.setHeaderHeight(25);
    	gridDefaults.setAutoFitFieldWidths(true);
    	gridDefaults.setAutoFitWidthApproach(AutoFitWidthApproach.TITLE);
    	gridDefaults.setLeaveScrollbarGap(false);
    	gridDefaults.setDataSource("gridMockDS");
    	record.setAttribute("defaults", gridDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(102, "Tree", 100, "TreeGrid.png", "TreeGrid");
    	TreeGrid treeDefaults = new TreeGrid();
    	treeDefaults.setHeight(160);
    	treeDefaults.setAutoFetchData(true);
    	treeDefaults.setLoadDataOnDemand(false);
    	Tree dataProperties = new Tree();
    	dataProperties.setOpenProperty("isOpen");
    	treeDefaults.setDataProperties(dataProperties);
    	treeDefaults.setDataSource("treeMockDS");
    	record.setAttribute("defaults", treeDefaults.getConfig());
    	records.add(record);
    	
    	record = createPaletteRecord(103, "DetailViewer", 100, "DetailViewer.png", "DetailViewer");
    	records.add(record);

    	record = createPaletteRecord(150, "Button", null, "button.gif", "IButton");
    	Canvas canvasDefaults = new Canvas();
    	canvasDefaults.setTitle("Button");
    	record.setAttribute("defaults", canvasDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(151, "Label", null, "ui-label.png", "Label");
    	canvasDefaults = new Canvas();
    	canvasDefaults.setContents("Some text");
    	canvasDefaults.setHeight(1);
    	record.setAttribute("defaults", canvasDefaults.getConfig());
    	records.add(record);

    	MenuItem recentSubMenuItems[] = new MenuItem[] {
        		createMenuItem("data.xml", null, true, null, null),
        		new MenuItem("Component Guide.doc"),
        		createMenuItem("SmartClient.doc", null, true, null, null),
        		new MenuItem("AJAX.doc"),
    	};
    	Menu recentSubMenu = new Menu();
    	recentSubMenu.setData(recentSubMenuItems);

    	MenuItem fileMenuItems[] = new MenuItem[] {
    		new MenuItem("Open", "icons/16/folder_out.png", "Ctrl+O"),
    		createMenuItem("Open Recent", "icons/16/folder_document.png", null, null, recentSubMenu),
    		new MenuItemSeparator(),
    		createMenuItem("Option 1", null, true, null, null),
    		new MenuItem("Option 2"),
    		new MenuItemSeparator(),
    		createMenuItem("Toggle Item", null, true, null, null),
    		createMenuItem("Disabled Item", null, null, true, null),
    		new MenuItemSeparator(),
    		new MenuItem("Exit", null, "Ctrl+Q")	
    	};
    	Menu fileMenu = new Menu();
    	fileMenu.setTitle("File");
    	fileMenu.setData(fileMenuItems);
    	Menu editMenu = new Menu();
    	editMenu.setTitle("Edit");
    	Menu viewMenu = new Menu();
    	viewMenu.setTitle("View");
    	Menu helpMenu = new Menu();
    	helpMenu.setTitle("Help");

    	record = createPaletteRecord(152, "Menu", null, "text_padding_top.png", "MenuButton");
    	Menu menuDefaults = new Menu();
    	menuDefaults.setTitle("File Menu");
    	menuDefaults.setData(fileMenuItems);
    	record.setAttribute("defaults", menuDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(153, "Menubar", null, "shape_align_top.png", "MenuBar");
    	MenuBar menuBarDefaults = new MenuBar();
    	menuBarDefaults.setWidth(200);
    	menuBarDefaults.setMenus(fileMenu, editMenu, viewMenu, helpMenu);
    	record.setAttribute("defaults", menuBarDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(154, "Progress Bar", null, "ui-progress-bar.png", "Progressbar");
    	Progressbar progressbarDefaults = new Progressbar();
    	progressbarDefaults.setPercentDone(70);
    	record.setAttribute("defaults", progressbarDefaults.getConfig());
    	records.add(record);

    	records.add(createPaletteRecord(200, "Containers"));

    	record = createPaletteRecord(201, "Tabs", 200, "TabSet.png", "TabSet");
    	TabSet tabSetDefaults = new TabSet();
    	tabSetDefaults.setWidth(200);
    	record.setAttribute("defaults", tabSetDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(202, "Box", 200, "shape_handles.png", "Canvas");
    	canvasDefaults = new Canvas();
    	canvasDefaults.setBorder("1px solid black");
    	record.setAttribute("defaults", canvasDefaults.getConfig());
    	// Disable inline editing
    	EditProxy editProxyProperties = new EditProxy();
    	editProxyProperties.setSupportsInlineEdit(false);
    	record.setAttribute("editProxyProperties", editProxyProperties.getConfig());
    	records.add(record);

    	record = createPaletteRecord(203, "Group", 200, "ui-group-box.png", "Canvas");
    	canvasDefaults = new Canvas();
    	canvasDefaults.setIsGroup(true);
    	canvasDefaults.setGroupTitle("Group");
    	record.setAttribute("defaults", canvasDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(204, "Window", 200, "Window.png", "Window");
    	canvasDefaults = new Canvas();
    	canvasDefaults.setTitle("Window");
    	record.setAttribute("defaults", tabSetDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(205, "H. Resizer", 200, "ui-splitter-horizontal.png", "Snapbar");
    	Snapbar snapBarDefaults = new Snapbar();
    	snapBarDefaults.setHeight(5);
    	snapBarDefaults.setVertical(false);
    	snapBarDefaults.setTitle("&nbsp;");
    	record.setAttribute("defaults", snapBarDefaults.getConfig());
    	// Disable inline editing
    	editProxyProperties = new EditProxy();
    	editProxyProperties.setSupportsInlineEdit(false);
    	record.setAttribute("editProxyProperties", editProxyProperties.getConfig());
    	records.add(record);

    	record = createPaletteRecord(206, "V. Resizer", 200, "ui-splitter.png", "Snapbar");
    	snapBarDefaults = new Snapbar();
    	snapBarDefaults.setWidth(5);
    	snapBarDefaults.setTitle("&nbsp;");
    	record.setAttribute("defaults", snapBarDefaults.getConfig());
    	// Disable inline editing
    	editProxyProperties = new EditProxy();
    	editProxyProperties.setSupportsInlineEdit(false);
    	record.setAttribute("editProxyProperties", editProxyProperties.getConfig());
    	records.add(record);

    	records.add(createPaletteRecord(300, "Inputs"));

    	record = createPaletteRecord(301, "Text Box", 300, "text.gif", "TextItem");
    	FormItem formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);
    	formItemDefaults.setWidth("*");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(302, "Text Area", 300, "textArea.gif", "TextAreaItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);;
    	formItemDefaults.setWidth("*");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(303, "Combo Box", 300, "comboBox.gif", "ComboBoxItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);
    	formItemDefaults.setWidth("*");
    	formItemDefaults.setValueMap("Selected Option", "Option 1", "Option 2");
    	formItemDefaults.setValue("Selected Option");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(304, "Select List", 300, "select.gif", "SelectItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);
    	formItemDefaults.setWidth("*");
    	formItemDefaults.setValueMap("Selected Option", "Option 1", "Option 2");
    	formItemDefaults.setValue("Selected Option");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(305, "Check Box", 300, "checkbox.gif", "CheckboxItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setTitle("Checkbox");
    	formItemDefaults.setWidth("*");
    	formItemDefaults.setValue(true);
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(306, "Date Input", 300, "date.gif", "DateItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);
    	formItemDefaults.setWidth("*");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(307, "Spinner", 300, "textfield_rename.png", "SpinnerItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);
    	formItemDefaults.setWidth("*");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(308, "Password", 300, "password.gif", "PasswordItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);
    	formItemDefaults.setWidth("*");
    	formItemDefaults.setValue("password");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(309, "Color", 300, "color_swatch.png", "ColorItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);
    	formItemDefaults.setWidth("*");
    	formItemDefaults.setValue("navy");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	// Disable inline editing
    	editProxyProperties = new EditProxy();
    	editProxyProperties.setSupportsInlineEdit(false);
    	record.setAttribute("editProxyProperties", editProxyProperties.getConfig());
    	records.add(record);

    	record = createPaletteRecord(310, "Upload Item", 300, "upload.gif", "UploadItem");
    	formItemDefaults = new FormItem();
    	formItemDefaults.setShowTitle(false);
    	formItemDefaults.setWidth("*");
    	record.setAttribute("defaults", formItemDefaults.getConfig());
    	// Disable inline editing
    	editProxyProperties = new EditProxy();
    	editProxyProperties.setSupportsInlineEdit(false);
    	record.setAttribute("editProxyProperties", editProxyProperties.getConfig());
    	records.add(record);

    	records.add(createPaletteRecord(400, "Charts"));

    	// Shared chart facets/data
    	Facet chartFacets = new Facet();
    	chartFacets.setInlinedValues(true);
    	chartFacets.setValues(
    			new FacetValue("West", "West"),
    			new FacetValue("North", "North"),
    			new FacetValue("East", "East"),
    			new FacetValue("South", "South")
    			);

    	CellRecord carsData = new CellRecord();
    	carsData.setAttribute("series", "Cars");
    	carsData.setAttribute("West", 37);
    	carsData.setAttribute("North", 29);
    	carsData.setAttribute("East", 80);
    	carsData.setAttribute("South", 87);
    	CellRecord trucksData = new CellRecord();
    	trucksData.setAttribute("series", "Trucks");
    	trucksData.setAttribute("West", 23);
    	trucksData.setAttribute("North", 45);
    	trucksData.setAttribute("East", 32);
    	trucksData.setAttribute("South", 67);
    	CellRecord motorcyclesData = new CellRecord();
    	motorcyclesData.setAttribute("series", "Motorcycles");
    	motorcyclesData.setAttribute("West", 12);
    	motorcyclesData.setAttribute("North", 4);
    	motorcyclesData.setAttribute("East", 23);
    	motorcyclesData.setAttribute("South", 45);

    	CellRecord[] chartData = new CellRecord[] {
    			carsData,
    			trucksData,
    			motorcyclesData
    	};

    	record = createPaletteRecord(401, "Column Chart", 400, "shape_align_bottom.png", "FacetChart");
    	FacetChart chartDefaults = new FacetChart();
    	chartDefaults.setBackgroundColor("white");
    	chartDefaults.setChartType(ChartType.COLUMN);
    	chartDefaults.setTitle("Sales by Product and Region");
    	chartDefaults.setFacets(chartFacets, new Facet("series"));
    	chartDefaults.setData(chartData);
    	record.setAttribute("defaults", chartDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(402, "Bar Chart", 400, "shape_align_left.png", "FacetChart");
    	chartDefaults = new FacetChart();
    	chartDefaults.setBackgroundColor("white");
    	chartDefaults.setChartType(ChartType.BAR);
    	chartDefaults.setTitle("Sales by Product and Region");
    	chartDefaults.setFacets(chartFacets, new Facet("series"));
    	chartDefaults.setData(chartData);
    	record.setAttribute("defaults", chartDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(403, "Line Chart", 400, "chart_line.png", "FacetChart");
    	chartDefaults = new FacetChart();
    	chartDefaults.setBackgroundColor("white");
    	chartDefaults.setChartType(ChartType.LINE);
    	chartDefaults.setTitle("Sales by Product and Region");
    	chartDefaults.setFacets(chartFacets, new Facet("series"));
    	chartDefaults.setData(chartData);
    	record.setAttribute("defaults", chartDefaults.getConfig());
    	records.add(record);

    	record = createPaletteRecord(404, "Pie Chart", 400, "chart_pie.png", "FacetChart");
    	chartDefaults = new FacetChart();
    	chartDefaults.setBackgroundColor("white");
    	chartDefaults.setChartType(ChartType.PIE);
    	chartDefaults.setTitle("Sales by Product and Region");
    	chartDefaults.setFacets(chartFacets, new Facet("series"));
    	chartDefaults.setData(chartData);
    	record.setAttribute("defaults", chartDefaults.getConfig());
    	records.add(record);

    	ds.setCacheData(records.toArray(new ListGridRecord[] {}));
    }

    private MenuItem createMenuItem(String title, String icon, Boolean checked, Boolean enabled, Menu submenu) {
    	MenuItem item = new MenuItem(title);
    	if (icon != null) item.setIcon(icon);
    	if (checked != null) item.setChecked(checked);
    	if (enabled != null) item.setEnabled(enabled);
    	if (submenu != null) item.setSubmenu(submenu);
    	return item;
    }

    private ListGridRecord createPaletteRecord(final int id, final String title) {
    	ListGridRecord record = createPaletteRecord(id, title, null, null, null);
    	record.setAttribute("isFolder", true);
    	record.setAttribute("canDrag", false);
    	return record;
    }

    private ListGridRecord createPaletteRecord(final int id, final String title, final Integer parentId, final String icon, final String type) {
    	ListGridRecord record = new ListGridRecord();
    	record.setAttribute("id", id);
    	record.setAttribute("title", title);
    	if (parentId != null) record.setAttribute("parentId", parentId);
    	if (icon != null) record.setAttribute("icon", icon);
    	if (type != null) record.setAttribute("type", type);
    	return record;
    }

    public String getIntro() {
    	return DESCRIPTION;
    }
}
