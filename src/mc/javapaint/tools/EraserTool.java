package mc.javapaint.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import mc.javapaint.ImagePanel;
import mc.javapaint.JPLayer;
import mc.javapaint.utils.JPUtils;

public class EraserTool extends JPTool {

	private ImagePanel imagePanel;
	private Color strokeColor;
	private Stroke stroke;
	private int lastPointIndex;
	private BufferedImage transparentImage;
	private ImagePanel iP;
	
	public EraserTool(ImagePanel imagePanel){
		super(imagePanel);
		id = 1;
		transparentImage = new BufferedImage(ImagePanel.WIDTH, ImagePanel.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		iP = imagePanel;
		lastPointIndex = 0;
		id = JPTool.ERASER;
	}
	
	@Override
	public void draw(Point point, Graphics2D g) {
		
		//g = imagePanel.getDisplayImage().createGraphics();
		
		strokeColor = iP.getStrokeColor();
		stroke = iP.getStroke();
		RenderingHints rh = iP.getRenderingHints();
		
		JPLayer layer = iP.getActiveLayer();
		
		Iterator<BufferedImage> itr = layer.getIterator();
		
		while(itr.hasNext()){
			BufferedImage tempImage = itr.next();
			Graphics2D g2 = tempImage.createGraphics();
			g2.setStroke(stroke);
			g2.setRenderingHints(rh);
			g2.setComposite(AlphaComposite.Clear);
			g2.drawLine(point.x, point.y, point.x, point.y);
			super.draw(point, g2);
			g2.setComposite(AlphaComposite.SrcOver);
			g2.dispose();
			
		}
		
	}

	@Override
	public void draw(ArrayList<Point> points, Graphics2D g) {
		
		//g = imagePanel.getDisplayImage().createGraphics();
		
		strokeColor = iP.getStrokeColor();
		stroke = iP.getStroke();
		RenderingHints rh = iP.getRenderingHints();
		
		JPLayer layer = iP.getActiveLayer();
		
//		for(int i = 0; i < points.size() - 1; i++){
//		try{
		if(points.size() - 2 >= 0){
			Point p1 = points.get(points.size() - 2);
			Point p2 = points.get(points.size() - 1);			Iterator<BufferedImage> itr = layer.getIterator();
			while(itr.hasNext()){
				BufferedImage tempImage = itr.next();
				Graphics2D g2 = tempImage.createGraphics();
				g2.setStroke(stroke);
				g2.setRenderingHints(rh);
				g2.setComposite(AlphaComposite.Clear);
				g2.drawLine(p1.x,p1.y,p2.x,p2.y);
				super.draw(points, g2);
				
				if(JPUtils.isTransparent(tempImage) && !tempImage.equals(layer.getBottomImage())){
					itr.remove();
				}
				
				g2.setComposite(AlphaComposite.SrcOver);
				g2.dispose();
				
			}
		}
//		}catch(IndexOutOfBoundsException e){
//			System.out.println("Index out of bounds");
//			return;
//		}
//		}
	}
	
	@Override
	public Cursor getCursor(){		
		return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	}


}
