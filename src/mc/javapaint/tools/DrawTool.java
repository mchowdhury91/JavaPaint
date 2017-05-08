package mc.javapaint.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
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
	}

	@Override
	public void draw(ArrayList<Point> points, Graphics2D g) {

		for(int i = 0; i < points.size() - 1; i++){
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			
			g.drawLine(p1.x,p1.y,p2.x,p2.y);
		}
	}

}
