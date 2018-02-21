package com.smartgwt.sample.showcase.client.portal;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.tools.EditNode;
import com.smartgwt.client.tools.Palette;
import com.smartgwt.client.tools.PaletteNode;
import com.smartgwt.client.tools.TilePalette;
import com.smartgwt.client.types.ArrowStyle;
import com.smartgwt.client.widgets.tile.SimpleTile;  
import com.smartgwt.client.widgets.tile.TileRecord;
import com.smartgwt.client.widgets.drawing.DrawItem;
import com.smartgwt.client.widgets.drawing.DrawCurve;
import com.smartgwt.client.widgets.drawing.DrawDiamond;
import com.smartgwt.client.widgets.drawing.DrawItem;
import com.smartgwt.client.widgets.drawing.DrawLabel;
import com.smartgwt.client.widgets.drawing.DrawLine;
import com.smartgwt.client.widgets.drawing.DrawLinePath;
import com.smartgwt.client.widgets.drawing.DrawOval;
import com.smartgwt.client.widgets.drawing.DrawPane;
import com.smartgwt.client.widgets.drawing.DrawRect;
import com.smartgwt.client.widgets.drawing.DrawTriangle;
import com.smartgwt.client.widgets.drawing.Gradient;
import com.smartgwt.client.widgets.drawing.Point;
import com.smartgwt.client.widgets.drawing.Shadow;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class ShapePalette {
    
    public TilePalette createPalette() {
    	TilePalette palette = new TilePalette();
    	palette.setWidth(270);
    	palette.setMinHeight(100);
    	palette.setTileWidth(80);
    	palette.setTileHeight(80);
    	palette.setCanDragTilesOut(true);
    	palette.setTileConstructor(DrawItemTile.class.getName());

    	// The regular TileGrid property "fields"
    	palette.setFields(new DetailViewerField("type"), new DetailViewerField("title", "Component"));

    	// We are supplying the component data inline for this example.
    	// However, ListPalette is a subclass of ListGrid, so you could
    	// also use a DataSource.
    	palette.setData(getPaletteData(palette.getTileWidth(), palette.getTileHeight()));

    	return palette;
    }
    
    // Creates PaletteNodes for each of different types of DrawItems.  The
    // defaults of the nodes are set so that the shapes will fit in the grid
    // tiles.
    private Record[] getPaletteData(int tileWidth, int tileHeight) {
    	// Creates PaletteNodes for each of the different types of DrawItems.  The
    	// defaults of the nodes are set so that the shapes will fit in the grid
    	// tiles.
    	final int topPadding = 2;
    	final int leftPadding = 2;
    	final int rightPadding = leftPadding;
    	final int bottomPadding = topPadding;

    	tileWidth -= (leftPadding + rightPadding);
    	tileHeight -= (topPadding + bottomPadding);

    	final int xc = leftPadding + (tileWidth / 2);
    	final int yc = topPadding + (tileHeight / 2);
    	final int width = tileWidth - leftPadding - rightPadding;
    	final int height = tileHeight - topPadding - bottomPadding;

		// variables for the DrawRect:
    	final double smallAngle = Math.PI / 5;
    	
    	final Point[] rectPoints = DrawPane.getPolygonPoints(
    			width, height, xc, yc, 
    			new double[] {smallAngle, Math.PI - smallAngle, Math.PI + smallAngle, -smallAngle});

    	final int rectTop = rectPoints[1].getY();
    	final int rectLeft = rectPoints[1].getX();
    	final int rectWidth = rectPoints[3].getX() - rectLeft;
    	final int rectHeight = rectPoints[3].getX() - rectTop;

    	// Define the default properties for three DrawCurves.
    	DrawCurve curveDefaults = new DrawCurve();
    	curveDefaults.setStartPoint(new Point(200, 50));
    	curveDefaults.setEndPoint(new Point(300, 150));
    	curveDefaults.setControlPoint1(new Point(250, 0));
    	curveDefaults.setControlPoint2(new Point(250, 200));

    	DrawCurve oneArrowCurveDefaults = new DrawCurve();
    	oneArrowCurveDefaults.setEndArrow(ArrowStyle.BLOCK);
    	oneArrowCurveDefaults.setStartPoint(new Point(200, 50));
    	oneArrowCurveDefaults.setEndPoint(new Point(300, 150));
    	oneArrowCurveDefaults.setControlPoint1(new Point(250, 0));
    	oneArrowCurveDefaults.setControlPoint2(new Point(250, 200));

    	DrawCurve twoArrowCurveDefaults = new DrawCurve();
    	twoArrowCurveDefaults.setStartArrow(ArrowStyle.BLOCK);
    	twoArrowCurveDefaults.setEndArrow(ArrowStyle.BLOCK);
    	twoArrowCurveDefaults.setStartPoint(new Point(200, 50));
    	twoArrowCurveDefaults.setEndPoint(new Point(300, 150));
    	twoArrowCurveDefaults.setControlPoint1(new Point(250, 0));
    	twoArrowCurveDefaults.setControlPoint2(new Point(250, 200));

    	// Rescale the three DrawCurves to center them at (xc, yc) and to fit them within the
    	// width and height.
    	DrawPane.scaleAndCenterBezier(width, height - 20, xc, yc, curveDefaults.getStartPoint(), curveDefaults.getEndPoint(), curveDefaults.getControlPoint1(), curveDefaults.getControlPoint2());
    	DrawPane.scaleAndCenterBezier(width, height - 20, xc, yc, oneArrowCurveDefaults.getStartPoint(), oneArrowCurveDefaults.getEndPoint(), oneArrowCurveDefaults.getControlPoint1(), oneArrowCurveDefaults.getControlPoint2());
    	DrawPane.scaleAndCenterBezier(width, height - 20, xc, yc, twoArrowCurveDefaults.getStartPoint(), twoArrowCurveDefaults.getEndPoint(), twoArrowCurveDefaults.getControlPoint1(), twoArrowCurveDefaults.getControlPoint2());


    	List<PaletteNode> nodes = new ArrayList<PaletteNode>();

    	PaletteNode node = new PaletteNode();
    	node.setTitle("Line");
    	node.setType("DrawLine");
    	DrawLine lineDefaults = new DrawLine();
    	lineDefaults.setStartPoint(new Point(Math.round(xc - width / 2), Math.round(yc - height / 2)));
    	lineDefaults.setEndPoint(new Point(Math.round(xc + width / 2), Math.round(yc + height / 2)));
    	node.setDrawItemDefaults(lineDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Line w/arrow");
    	node.setType("DrawLine");
    	lineDefaults = new DrawLine();
    	lineDefaults.setStartPoint(new Point(Math.round(xc - width / 2), Math.round(yc - height / 2)));
    	lineDefaults.setEndPoint(new Point(Math.round(xc + width / 2), Math.round(yc + height / 2)));
    	lineDefaults.setEndArrow(ArrowStyle.BLOCK);
    	node.setDrawItemDefaults(lineDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Line w/two arrows");
    	node.setType("DrawLine");
    	lineDefaults = new DrawLine();
    	lineDefaults.setStartPoint(new Point(Math.round(xc - width / 2), Math.round(yc - height / 2)));
    	lineDefaults.setEndPoint(new Point(Math.round(xc + width / 2), Math.round(yc + height / 2)));
    	lineDefaults.setStartArrow(ArrowStyle.BLOCK);
    	lineDefaults.setEndArrow(ArrowStyle.BLOCK);
    	node.setDrawItemDefaults(lineDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Curve");
    	node.setType("DrawCurve");
    	node.setDrawItemDefaults(curveDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Curve w/arrow");
    	node.setType("DrawCurve");
    	node.setDrawItemDefaults(oneArrowCurveDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Curve w/two arrows");
    	node.setType("DrawCurve");
    	node.setDrawItemDefaults(twoArrowCurveDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Line Path");
    	node.setType("DrawLinePath");
    	DrawLinePath linePathDefaults = new DrawLinePath();
    	linePathDefaults.setStartPoint(new Point(Math.round(xc - width / 2), Math.round(yc - (height - 10) / 2)));
    	linePathDefaults.setEndPoint(new Point(Math.round(xc + width / 2), Math.round(yc + (height - 20)/ 2)));
    	linePathDefaults.setEndArrow(null);
    	node.setDrawItemDefaults(linePathDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Line Path w/arrow");
    	node.setType("DrawLinePath");
    	linePathDefaults = new DrawLinePath();
    	linePathDefaults.setStartPoint(new Point(Math.round(xc - width / 2), Math.round(yc - (height - 10) / 2)));
    	linePathDefaults.setEndPoint(new Point(Math.round(xc + width / 2), Math.round(yc + (height - 20)/ 2)));
    	linePathDefaults.setEndArrow(ArrowStyle.BLOCK);
    	node.setDrawItemDefaults(linePathDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Line Path w/two arrows");
    	node.setType("DrawLinePath");
    	linePathDefaults = new DrawLinePath();
    	linePathDefaults.setStartPoint(new Point(Math.round(xc - width / 2), Math.round(yc - (height - 10) / 2)));
    	linePathDefaults.setEndPoint(new Point(Math.round(xc + width / 2), Math.round(yc + (height - 20)/ 2)));
    	linePathDefaults.setStartArrow(ArrowStyle.BLOCK);
    	linePathDefaults.setEndArrow(ArrowStyle.BLOCK);
    	node.setDrawItemDefaults(linePathDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Rectangle");
    	node.setType("DrawRect");
    	DrawRect rectDefaults = new DrawRect();
    	rectDefaults.setTop(rectTop);
    	rectDefaults.setLeft(rectLeft);
    	rectDefaults.setWidth(rectWidth);
    	rectDefaults.setHeight(rectHeight);
    	rectDefaults.setFillGradient("rect");
    	node.setDrawItemDefaults(rectDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Rounded Rectangle");
    	node.setType("DrawRect");
    	rectDefaults = new DrawRect();
    	rectDefaults.setTop(rectTop);
    	rectDefaults.setLeft(rectLeft);
    	rectDefaults.setWidth(rectWidth);
    	rectDefaults.setHeight(rectHeight);
    	rectDefaults.setFillGradient("rect");
    	rectDefaults.setRounding(0.25f);
    	node.setDrawItemDefaults(rectDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Oval");
    	node.setType("DrawOval");
    	DrawOval ovalDefaults = new DrawOval();
    	ovalDefaults.setTop(rectTop);
    	ovalDefaults.setLeft(rectLeft);
    	ovalDefaults.setWidth(rectWidth);
    	ovalDefaults.setHeight(rectHeight);
    	ovalDefaults.setFillGradient("oval");
    	node.setDrawItemDefaults(ovalDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Triangle");
    	node.setType("DrawTriangle");
    	DrawTriangle triangleDefaults = new DrawTriangle();
    	triangleDefaults.setPoints(DrawPane.getRegularPolygonPoints(3, width, height, xc, yc, Math.PI / 2));
    	triangleDefaults.setFillGradient("triangle");
    	node.setDrawItemDefaults(triangleDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Diamond");
    	node.setType("DrawDiamond");
    	DrawDiamond diamondDefaults = new DrawDiamond();
    	diamondDefaults.setTop(rectTop);
    	diamondDefaults.setLeft(rectLeft);
    	diamondDefaults.setWidth(rectWidth);
    	diamondDefaults.setHeight(rectHeight);
    	diamondDefaults.setFillGradient("diamond");
    	node.setDrawItemDefaults(diamondDefaults);
    	nodes.add(node);

    	node = new PaletteNode();
    	node.setTitle("Label");
    	node.setType("DrawLabel");
    	DrawLabel labelDefaults = new DrawLabel();
    	labelDefaults.setContents("Text");
    	labelDefaults.setTop(yc / 2);
    	labelDefaults.setLeft(xc / 2);
    	labelDefaults.setAlignment("center");
    	node.setDrawItemDefaults(labelDefaults);
    	nodes.add(node);

        DrawLabel titleLabelDefaults = new DrawLabel();
        titleLabelDefaults.setLineColor("#222222");
        DrawItem.changeAutoChildDefaults("titleLabel", titleLabelDefaults);

    	// Set default properties on the DrawItems offered in the palette.
    	for (PaletteNode n : nodes) {
    		DrawItem defaults = n.getDrawItemDefaults();
    		if (defaults == null) {
    			defaults = new DrawItem();
    			n.setDrawItemDefaults(defaults);
    		}
    		defaults.setKeepInParentRect(true);
    		defaults.setLineWidth(1);
    		Shadow shadow = new Shadow();
    		shadow.setColor("#333333");
    		shadow.setBlur(2);
    		shadow.setOffset(new Point(1,1));
            if (!n.getType().equals("DrawLine") && !n.getType().equals("DrawCurve") &&
                !n.getType().equals("DrawLinePath"))
            {
                defaults.setShadow(shadow);
            }
    	}

    	return nodes.toArray(new PaletteNode[] {});
    }

    public static class DrawItemTile extends SimpleTile {  
  
        private DrawPane drawPane;  

        private static Gradient[] commonGradients;  

        public static void setCommonGradients(Gradient[] gradients) {  
            commonGradients = gradients;  
        }  

        public DrawItemTile() {  
            super();  

            drawPane = new DrawPane();  
            drawPane.setWidth100();  
            drawPane.setHeight100();  
            drawPane.setGradients(commonGradients);  

            this.addChild(drawPane);  
        }  

        public String getInnerHTML() {  
            // We do not want the default HTML generated by the superclass SimpleTile's  
            // getInnerHTML() method to be drawn, so just return a blank HTML string here.  
            return " ";  
        }  


        protected void onDraw() {  
            super.onDraw();  
            drawRecord(this.getRecord());  
        }  

        private void drawRecord (TileRecord record) {  
            PaletteNode paletteNode = new PaletteNode(record.getJsObj());  

            Palette palette = (TilePalette)this.getCreator();  
            EditNode editNode = palette.makeEditNode(paletteNode);  
            DrawItem drawItem = editNode.getDrawItemLiveObject();  
            drawPane.addDrawItem(drawItem, true);  
        }  
    }  
}