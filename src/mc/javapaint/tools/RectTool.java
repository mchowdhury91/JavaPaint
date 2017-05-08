package mc.javapaint.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc.javapaint.ImagePanel;
import mc.javapaint.utils.JPUtils;

public class RectTool extends JPTool{

	BufferedImage buffer;
	BufferedImage original;
	private ImagePanel imagePanel;
	
	public RectTool(ImagePanel imagePanel) {
		super(imagePanel);
		this.imagePanel = imagePanel;
		
	}

	@Override
	public void draw(Point point, Graphics2D g) {
		
	}

	@Override
	public void draw(ArrayList<Point> points, Graphics2D g) {
		buffer = JPUtils.copyImage(imagePanel.getImage());
		Graphics2D g2 = buffer.createGraphics();
		g2.setStroke(g.getStroke());
		g2.setColor(g.getColor());
		g2.setRenderingHints(g.getRenderingHints());
		
		int x = points.get(0).x;
		int y = points.get(0).y;
		
		int x2 = points.get(points.size()-1).x;
		int y2 = points.get(points.size()-1).y;
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		g2.setBackground(new Color(255,255,255,255));
		g2.clearRect(0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		g2.drawImage(original, 0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT, null);
		
		int width = (x2 - x);
		int height = (y2 - y);
		
		if(height <= 0 && width <= 0){
			g2.drawRect(x2, y2, Math.abs(width), Math.abs(height));
		}else if(height <= 0){
			g2.drawRect(x, y2, Math.abs(width), Math.abs(height));
		}else if(width <= 0){
			g2.drawRect(x2, y, Math.abs(width), Math.abs(height));
		}else{
			g2.drawRect(x, y, width, height);			
		}
		
		g2.dispose();
		
		g.drawImage(buffer, 0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT, null);
		
	}

	public void snapShot(){
		original = JPUtils.copyImage(imagePanel.getImage());
		System.out.println("RECT TOOL SNAPSHOT");

	}
	
	public void update(Graphics2D g){
//		g.drawImage(buffer, 0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT, null);
		System.out.println("RECT TOOL UPDATED");
		//g.drawImage(original, 0, 0, ImagePanel.WIDTH, ImagePanel.HEIGHT, null);
	}
	
}
