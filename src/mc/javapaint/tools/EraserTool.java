package mc.javapaint.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc.javapaint.ImagePanel;

public class EraserTool extends JPTool {

	private ImagePanel imagePanel;
	private Color strokeColor;
	private Stroke stroke;
	private int lastPointIndex;
	private BufferedImage transparentImage;
	
	public EraserTool(ImagePanel imagePanel){
		super(imagePanel);
		id = 1;
		transparentImage = new BufferedImage(ImagePanel.WIDTH, ImagePanel.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	}
	
	@Override
	public void draw(Point point, Graphics2D g) {
		
		//g = imagePanel.getDisplayImage().createGraphics();
		g.setComposite(AlphaComposite.Clear);
		g.drawLine(point.x,point.y,point.x,point.y);
		super.draw(point, g);
		g.setComposite(AlphaComposite.SrcOver);
//		g.setComposite(AlphaComposite.SrcOver);
		
	}

	@Override
	public void draw(ArrayList<Point> points, Graphics2D g) {
		
		//g = imagePanel.getDisplayImage().createGraphics();
		
		g.setComposite(AlphaComposite.Clear);
		for(int i = 0; i < points.size() - 1; i++){
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			
			g.drawLine(p1.x,p1.y,p2.x,p2.y);
		}
		super.draw(points, g);
		g.setComposite(AlphaComposite.SrcOver);
	}

}
