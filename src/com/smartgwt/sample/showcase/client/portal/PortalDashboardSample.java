package com.smartgwt.sample.showcase.client.portal;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.core.Function;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.tools.EditContext;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.tools.EditPane;
import com.smartgwt.client.tools.HiddenPalette;
import com.smartgwt.client.tools.ListPalette;
import com.smartgwt.client.tools.Palette;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.PortalLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.sample.showcase.client.AdvancedPanelFactory;
import com.smartgwt.sample.showcase.client.ShowcasePanel;

public class PortalDashboardSample extends ShowcasePanel {

    private static final String DESCRIPTION =
        "<p>With the Tools framework, you can create dashboards of portlets."
        + "This example uses a list of pre-configured grid portlets that can be viewed or edited.</p>"
        + "<p>Double-click a pre-configured dashboard to view the saved portal layout consisting of"
        + " one or more grids. To make changes, select the dashboard and click Edit button."
        + " Change the criteria, field order or size, sort order, highlights,"
        + " or grouping to see how these properties are persisted.</p>"
        + "<p>Select a dashboard and click the Clone button to generate another copy for experimentation. "
        + "Rename the new dashboard by right-clicking on the record and choosing Rename option.</p>";

    private ListGrid dashboardsList;
    private TabSet selector;
    private EditPane editPane;
    private HLayout editToolbar;
    private Record currentRecord;

    private PaletteNode initialPortalPaletteNode;

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
                "<p>Click <a href=\"http://www.smartclient.com/smartgwtee/showcase/#portalDashboard\" target=\"\">here</a> " +
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
            return new PortalDashboardSample();
        }
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

        // Palette Node used to create a default portal
        PortalLayout defaults = new PortalLayout();
        defaults.setWidth100();
        defaults.setHeight100();
        defaults.setCanResizePortlets(true);
        initialPortalPaletteNode = new PaletteNode();
        initialPortalPaletteNode.setType("PortalLayout");
        initialPortalPaletteNode.setCanvasDefaults(defaults);

        ListPalette palette = createPalette();
		Palette extraPalette = createExtraPalette();

        dashboardsList = createDashboardsList();
        HLayout dashboardsToolbar = createDashboardsToolbar();
        VLayout dashboardsPane = new VLayout();
        dashboardsPane.setMembers(dashboardsList, dashboardsToolbar);

        selector = new TabSet();
		Tab tab = new Tab("Dashboards");
		tab.setPane(dashboardsPane);
		selector.addTab(tab);
		tab = new Tab("Palette");
		tab.setPane(palette);
		tab.setDisabled(true);
		selector.addTab(tab);

		editPane = new EditPane();
		editPane.setBorder("1px solid black");
		editPane.setVisibility(Visibility.HIDDEN);
		editPane.setExtraPalettes(extraPalette);
		JSOHelper.setAttribute(editPane.getConfig(), "editMode", false);

		editPane.setDefaultPalette(palette);

		// Make the new editPane the default Edit Context for the palette,
		// to support double-clicking on components in the palette to create them
		palette.setDefaultEditContext(editPane);

		// Add a PortalLayout to the editPane
        EditNode editNode = editPane.addFromPaletteNode(initialPortalPaletteNode);
        editPane.getEditContext().setDefaultParent(editNode);

		editToolbar = createEditToolbar();

        VLayout rightPane = new VLayout();
        rightPane.setMembers(editPane, editToolbar);

        // Layout for the example
        HLayout layout = new HLayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        layout.setMembersMargin(20);
        layout.setMembers(selector, rightPane);
        return layout;
    }

    private ListGrid createDashboardsList() {
    	final PortalDashboardSample dashboard = this;

    	final ListGrid grid = new ListGrid();
    	grid.setDataSource(DataSource.get("dashboards"));
    	grid.setAutoFetchData(true);
    	grid.setSelectionType(SelectionStyle.SINGLE);
    	grid.setSortField("description");
    	grid.setLeaveScrollbarGap(false);

	    // Allow edit of portal description (via context menu)
    	grid.setCanEdit(true);
    	grid.setEditEvent(ListGridEditEvent.NONE);
	    // And removal of dashboards
    	grid.setCanRemoveRecords(true);

    	grid.setFields(new ListGridField("description"));

    	grid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				dashboard.clearCurrentDashboard();
				dashboard.viewDashboard();
			}
		});

    	final Menu contextMenu = new Menu();
    	grid.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(CellContextClickEvent event) {
				MenuItem renameItem = new MenuItem("Rename");
				renameItem.setAttribute("rowNum", event.getRowNum());
				renameItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					@Override
					public void onClick(MenuItemClickEvent event) {
						int rowNum = event.getItem().getAttributeAsInt("rowNum");
						((ListGrid)event.getTarget()).startEditing(rowNum);
					}
				});
				MenuItem separator = new MenuItemSeparator();
				MenuItem editItem = new MenuItem("Edit");
				editItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					@Override
					public void onClick(MenuItemClickEvent event) {
						dashboard.clearCurrentDashboard();
						dashboard.editDashboard();
					}
				});
				MenuItem cloneItem = new MenuItem("Clone");
				cloneItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					@Override
					public void onClick(MenuItemClickEvent event) {
						dashboard.cloneDashboard();
					}
				});

				contextMenu.setItems(renameItem, separator, editItem, cloneItem);
				contextMenu.setTarget(grid);
				contextMenu.showContextMenu();

				// return false to kill the standard context menu
				event.cancel();
			}
		});
    	return grid;
    }

    private HLayout createDashboardsToolbar() {
    	final PortalDashboardSample dashboard = this;

    	IButton viewButton = new IButton("View");
    	viewButton.setAutoFit(true);
    	viewButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dashboard.clearCurrentDashboard();
				dashboard.viewDashboard();
			}
		});

    	IButton editButton = new IButton("Edit");
    	editButton.setAutoFit(true);
    	editButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dashboard.clearCurrentDashboard();
				dashboard.editDashboard();
			}
		});

    	IButton newButton = new IButton("New");
    	newButton.setAutoFit(true);
    	newButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dashboard.newDashboard();
			}
		});

    	IButton cloneButton = new IButton("Clone");
    	cloneButton.setAutoFit(true);
    	cloneButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dashboard.cloneDashboard();
			}
		});

    	HLayout toolbar = new HLayout();
    	toolbar.setHeight(30);
    	toolbar.setMembersMargin(10);
    	toolbar.setDefaultLayoutAlign(Alignment.CENTER);
    	toolbar.addMembers(new LayoutSpacer(), viewButton, editButton, newButton, cloneButton);

    	return toolbar;
    }
    
	private static ListPalette createPalette() {
		// The ListPalette contains components available
		// for use, with default settings.
		ListPalette palette = new ListPalette();
		palette.setFields(new ListGridField("title", "Component"));
    	palette.setLeaveScrollbarGap(false);

		palette.setData(new PaletteNode[] {
			createPaletteNode("Animals", "animals"), 
			createPaletteNode("Supply Categories", "supplyCategory"), 
			createPaletteNode("Supply Items", "supplyItem")
		});

		return palette;
	}

	private static Palette createExtraPalette() {
		HiddenPalette palette = new HiddenPalette();

		PaletteNode node = new PaletteNode();
		node.setTitle("ListGridField");
		node.setType("ListGridField");
		palette.setData(node);

		return palette;
	}

	private static PaletteNode createPaletteNode(String title, String dataSource) {
		ListGrid defaults = new ListGrid();
        defaults.setDataSource(DataSource.get(dataSource));
        defaults.setAutoFetchData(true);
        defaults.setShowFilterEditor(true);
        PaletteNode node = new PaletteNode();
        node.setType("ListGrid");
        node.setTitle(title);
        node.setCanvasDefaults(defaults);

        return node;
	}

    private HLayout createEditToolbar() {
    	final PortalDashboardSample dashboard = this;

    	IButton saveButton = new IButton("Save");
    	saveButton.setAutoFit(true);
    	saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dashboard.saveDashboard();
			}
		});

    	IButton discardButton = new IButton("Discard changes");
    	discardButton.setAutoFit(true);
    	discardButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dashboard.refreshDashboard();
			}
		});

    	HLayout toolbar = new HLayout();
    	toolbar.setHeight(30);
    	toolbar.setMembersMargin(10);
    	toolbar.setDefaultLayoutAlign(Alignment.CENTER);
    	toolbar.setVisibility(Visibility.HIDDEN);
    	toolbar.addMembers(new LayoutSpacer(), saveButton, discardButton);

    	return toolbar;
    }

    private void clearCurrentDashboard() {
    	editPane.destroyAll();
    	editPane.hide();
    	editToolbar.hide();
    }

    private void editDashboard() {
    	ListGridRecord record = dashboardsList.getSelectedRecord();
    	if (record != null) {
            final EditContext editContext = editPane.getEditContext();
            final Tree editTree = editContext.getEditNodeTree();
            final EditNode rootNode = editContext.getRootEditNode();

    		editPane.addPaletteNodesFromXML(record.getAttribute("layout"), null, null, new Function() {
    			@Override
    			public void execute() {
		            // PortalLayout is assumed to be the first node under root.
		            TreeNode[] childNodes = editTree.getChildren(rootNode);
		            EditNode editNode = (childNodes != null && childNodes.length > 0 ? new EditNode(childNodes[0].getJsObj()) : null);
		            editContext.setDefaultParent(editNode);
    			}
    		});
    		editPane.show();
    		editToolbar.show();
    		showPalette();
    	}
    	currentRecord = record;
    }

    private void viewDashboard() {
    	ListGridRecord record = dashboardsList.getSelectedRecord();
    	if (record != null) {
    		editPane.addPaletteNodesFromXML(record.getAttribute("layout"));
    		editPane.show();
    		editToolbar.hide();
    		hidePalette();
    	}
    	currentRecord = record;
    }

    private void newDashboard() {
        clearCurrentDashboard();
        currentRecord = null;

        // Add a PortalLayout to the editPane
        EditNode editNode = editPane.addFromPaletteNode(initialPortalPaletteNode);
        editPane.getEditContext().setDefaultParent(editNode);
        editPane.show();
        editToolbar.show();
        showPalette();

        saveDashboard();
    }

    private void cloneDashboard() {
    	ListGridRecord record = dashboardsList.getSelectedRecord();
    	if (record != null) {
    		cloneRecord(record);
    	}
    }

    private void showPalette() {
    	selector.enableTab(1);
    	selector.selectTab(1);
    }

    private void hidePalette() {
    	selector.disableTab(1);
    }

    private void refreshDashboard() {
    	clearCurrentDashboard();
    	editDashboard();
    }

    private void saveDashboard() {
		String editNodes = editPane.serializeAllEditNodes();

        if (currentRecord != null) {
            currentRecord.setAttribute("layout", editNodes);
            dashboardsList.updateData(currentRecord);
        } else {
        	ListGridRecord newRecord = new ListGridRecord();
        	newRecord.setAttribute("description", "New dashboard");
        	newRecord.setAttribute("layout", editNodes);

        	dashboardsList.addData(newRecord, new DSCallback() {
    			@Override
    			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
    				Record[] records = dsResponse.getData();
    				if (records != null) {
    					dashboardsList.selectSingleRecord(records[0]);
                        currentRecord = records[0];
    				}
    			}
    		});
        }
    }

    private void cloneRecord(ListGridRecord record) {
    	ListGridRecord newRecord = new ListGridRecord();
    	newRecord.setAttribute("description", record.getAttribute("description"));
    	newRecord.setAttribute("layout", record.getAttribute("layout"));
    	
    	dashboardsList.addData(newRecord);
    }

    public String getIntro() {
        return DESCRIPTION;
    }
}
