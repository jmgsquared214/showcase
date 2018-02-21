package com.smartgwt.sample.showcase.client.portal;

import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.tools.EditContext;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class ColorMenuCanvas extends Canvas {

    public interface MetaFactory extends BeanFactory.MetaFactory {
        BeanFactory<ColorMenuCanvas> getColorMenuCanvasBeanFactory();
    }

	public ColorMenuCanvas () {
		Menu menu = new Menu();

		MenuItem[] items = new MenuItem[] {
			createMenuItem("red"),	
			createMenuItem("blue"),	
			createMenuItem("green"),	
			createMenuItem("black")	
		};
		menu.setItems(items);
		
		this.setContextMenu(menu);
	}

	
	// This method is called just-in-time when serializing an EditContext,
	// so that you can update any properties that you want to
	// automatically persist.
	@Override
	public void updateEditNode(EditContext editContext, EditNode editNode) {
		super.updateEditNode(editContext, editNode);

		Canvas properties = new Canvas();
		properties.setBackgroundColor(getBackgroundColor());

		editContext.setNodeProperties(editNode, properties, true);
	}


	private MenuItem createMenuItem(final String color) {
		MenuItem item = new MenuItem(color);
		item.setCheckIfCondition(new MenuItemIfFunction() {
			@Override
			public boolean execute(Canvas target, Menu menu, MenuItem item) {
				return (target.getBackgroundColor() == color);
			}
		});
		item.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				// We set the background color in the ordinary way,
				// and then use updateEditNode() to record
				// the state just-in-time, when serialization occurs
				event.getTarget().setBackgroundColor(color);
			}
		});
		return item;
	}
}
