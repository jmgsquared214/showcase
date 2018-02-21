package com.smartgwt.sample.showcase.client.portal;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.tools.EditContext;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.widgets.grid.ListGrid;

public class PortalGrid extends ListGrid {

    public interface MetaFactory extends BeanFactory.MetaFactory {
        BeanFactory<PortalGrid> getPortalGridBeanFactory();
    }

	// This method is called just-in-time when serializing an EditContext,
	// so that you can update any properties that you want to
	// automatically persist.
	@Override
	public void updateEditNode(EditContext editContext, EditNode editNode) {
		super.updateEditNode(editContext, editNode);

		ListGrid setProperties = new ListGrid();
		List<String> removeProperties = new ArrayList<String>();

        // View state could be used but it contains selection as well
        // which is not desired.

		setProperties.setFieldState(this.getFieldState());
		setProperties.setGroupState(this.getGroupState());

        // Save current sort
        SortSpecifier[] sort = this.getSort();
        if (sort == null) {
        	removeProperties.add("initialSort");
        } else {
        	setProperties.setInitialSort(sort);
        }

        // Save filter criteria
        Criteria criteria = this.getFilterEditorCriteria();
        if (sort == null) {
        	removeProperties.add("initialCriteria");
        } else {
        	setProperties.setInitialCriteria(criteria);
        }

        editContext.setNodeProperties(editNode, setProperties, true);
        if (!removeProperties.isEmpty()) editContext.removeNodeProperties(editNode, removeProperties.toArray(new String[] {}));
	}
}
