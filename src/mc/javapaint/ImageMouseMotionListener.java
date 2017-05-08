package mc.javapaint;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mc.javapaint.utils.JPUtils;

public class ImageMouseMotionListener implements MouseMotionListener {

	private Drawable drawable;
	private ArrayList<Point> points;
	private BufferedImage originalImage;
	
	public ImageMouseMotionListener(Drawable drawable) {
		super();
		this.drawable = drawable;
		points = new ArrayList<Point>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		points.add(e.getPoint());
		drawable.draw(points);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void clearPoints(){
		points.clear();
	}
	
	public void snapShot(){
		originalImage = JPUtils.copyImage(drawable.getImage());
	}

}
