package mc.javapaint.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc.javapaint.ImagePanel;
import mc.javapaint.utils.JPUtils;

public class OvalTool extends JPToolHollowShape{

	BufferedImage buffer;
	BufferedImage originalActiveLayerImage;
	BufferedImage originalDisplayImage;
	
	public OvalTool(ImagePanel imagePanel) {
		super(imagePanel);		
	}

	@Override
	public void draw(Point point, Graphics2D g) {
		
	}
	
	void drawShape(int x, int y, int width, int height, Graphics2D g2){
		g2.drawOval(x, y, width, height);
	}

	
}
