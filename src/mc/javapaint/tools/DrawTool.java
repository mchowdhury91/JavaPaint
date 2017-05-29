package mc.javapaint.tools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;

import mc.javapaint.ImagePanel;

public class DrawTool extends JPTool {

	private ImagePanel imagePanel;
	private Color strokeColor;
	private Stroke stroke;
	private int lastPointIndex;
	
	public DrawTool(ImagePanel imagePanel){
		super(imagePanel);
		
	}
	
	@Override
	public void draw(Point point, Graphics2D g) {
		g.drawLine(point.x,point.y,point.x,point.y);
		super.draw(point, g);
	}

	@Override
	public void draw(ArrayList<Point> points, Graphics2D g) {

//		for(int i = 0; i < points.size() - 1; i++){
//			Point p1 = points.get(i);
//			Point p2 = points.get(i+1);
//			
//			g.drawLine(p1.x,p1.y,p2.x,p2.y);
//		}
		
		if(points.size()-2 >= 0 && points.size()-1 > 0){
			Point p1 = points.get(points.size()-2);
			Point p2 = points.get(points.size()-1);
			
			g.drawLine(p1.x,p1.y,p2.x,p2.y);
		}
		
		super.draw(points, g);
	}
	
	@Override
	public Cursor getCursor(){
		return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	}

}
