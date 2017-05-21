package mc.javapaint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc.javapaint.utils.JPUtils;

public class ImageMouseMotionListener implements MouseMotionListener {

	private ImagePanel imagePanel;
	private ArrayList<Point> points;
	private BufferedImage originalImage;
	
	public ImageMouseMotionListener(ImagePanel drawable) {
		super();
		this.imagePanel = drawable;
		points = new ArrayList<Point>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		points.add(e.getPoint());
		imagePanel.draw(points);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void updateActions(Graphics2D g, RenderingHints renderingHints, Stroke stroke, Color strokeColor){
		ArrayList<Point> pointsClone = new ArrayList<Point>(points);
		JPAction action = new JPAction(imagePanel.getActiveLayer(), imagePanel);
		imagePanel.addAction(action);
		points.clear();
	}
	
	public void snapShot(){
		originalImage = JPUtils.copyImage(imagePanel.getImage());
	}

}
