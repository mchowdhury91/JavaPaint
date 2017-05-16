package mc.javapaint.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc.javapaint.ImagePanel;
import mc.javapaint.utils.JPUtils;

public abstract class JPToolHollowShape extends JPTool{

	BufferedImage buffer;
	BufferedImage originalActiveLayerImage;
	BufferedImage originalDisplayImage;
	
	public JPToolHollowShape(ImagePanel imagePanel) {
		super(imagePanel);		
	}

	@Override
	public void draw(Point point, Graphics2D g) {
		
	}
	
	abstract void drawShape(int x, int y, int width, int height, Graphics2D g2);

	@Override
	public void draw(ArrayList<Point> points, Graphics2D g) {
		// we create a temporary buffer image to draw to first
		// buffer is created by copying the active image from the imagepanel
		buffer = JPUtils.copyImage(imagePanel.getImage());
		Graphics2D g2 = buffer.createGraphics();
		g2.setStroke(g.getStroke());
		g2.setColor(g.getColor());
		g2.setRenderingHints(g.getRenderingHints());
		
		int x = points.get(0).x;
		int y = points.get(0).y;
		
		int x2 = points.get(points.size()-1).x;
		int y2 = points.get(points.size()-1).y;
		
		//we need to constantly clear away the current image
		// and redraw the shape until the user releases the mouse button
		// otherwise we won't get an outline only, we'll get remnants of the different 
		// sized shapes that we drew as the user was adjusting the mouse
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
//		g2.setBackground(new Color(255,255,255,255));
//		g2.clearRect(0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT);
//
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

		buffer.setData(originalActiveLayerImage.getData());
		imagePanel.getImage().setData(originalActiveLayerImage.getData());
		imagePanel.getDisplayImage().setData(originalDisplayImage.getData());
//		g2.drawImage(original, 0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT, null);
		
		int width = (x2 - x);
		int height = (y2 - y);
		
		// this section takes care of drawing shapes from origin upwards and to the left
		// by default, drawRect (and oval) draws from the origin to the bottom right
		
		if(height <= 0 && width <= 0){
			drawShape(x2, y2, Math.abs(width), Math.abs(height), g2);
		}else if(height <= 0){
			drawShape(x, y2, Math.abs(width), Math.abs(height), g2);
		}else if(width <= 0){
			drawShape(x2, y, Math.abs(width), Math.abs(height), g2);
		}else{
			drawShape(x, y, width, height, g2);			
		}
		
		g2.dispose();
		
		g.drawImage(buffer, 0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT, null);
		super.draw(points, g);
		
	}

	public void snapShot(){
		originalActiveLayerImage = JPUtils.copyImage(imagePanel.getImage());
		originalDisplayImage = JPUtils.copyImage(imagePanel.getDisplayImage());

	}
	
	public void update(Graphics2D g){
//		g.drawImage(buffer, 0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT, null);
//		System.out.println("RECT TOOL UPDATED");
		//g.drawImage(original, 0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT, null);
	}
	
}