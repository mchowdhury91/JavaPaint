package mc.javapaint.tools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mc.javapaint.ImagePanel;

public abstract class JPTool {

	protected ImagePanel imagePanel;
	public static RenderingHints RENDERING_HINTS;
	private Color strokeColor;
	private Stroke stroke;
	private int lastPointIndex;
	public int id = 0;
	
	
	public static final int ERASER = -1;
	
	public JPTool(ImagePanel imagePanel){
		this.imagePanel = imagePanel;
		Map<Key, Object> hints = new HashMap<RenderingHints.Key, Object>();
		
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		RENDERING_HINTS = new RenderingHints(hints);
		
		strokeColor = imagePanel.getStrokeColor();
		stroke = imagePanel.getStroke();
		lastPointIndex = imagePanel.getLastPointIndex();
	}
	
	public Cursor getCursor(){
		return Cursor.getDefaultCursor();
	}
	
	
	public void draw(Point point, Graphics2D g){
		imagePanel.update();
	}
	
	public void draw(ArrayList<Point> points, Graphics2D g){
		imagePanel.update();		
	}
	
	public void setStroke(Stroke stroke){
		this.stroke = stroke;
	}
	
	public void update(Graphics2D g){
		
	}
	
	public void snapShot(){
		
	}

	public void resetLastPointIndex(){
		lastPointIndex = 0;
	}
	
	public int getLastPointIndex(){
		return lastPointIndex;
	}	
}
