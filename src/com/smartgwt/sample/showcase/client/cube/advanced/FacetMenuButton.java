package com.smartgwt.sample.showcase.client.cube.advanced;

import com.smartgwt.client.widgets.menu.MenuButton;

public class FacetMenuButton extends MenuButton {
    public FacetMenuButton(String facetId) {

        setFacetId(facetId);

        // Quick way to make this button available anywhere
        setID(facetId + "Button");

        setWidth(150);

        setTitle(facetId);
        setCanDrag(true);
        setCanDrop(true);
        setDragType("facet");
        setPrompt("Drag to desired facet position, or click to display menu.");
    }
}
