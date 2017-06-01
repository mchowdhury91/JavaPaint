package mc.javapaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mc.javapaint.tools.DrawTool;
import mc.javapaint.tools.JPTool;

public class ImagePanel extends JPanel implements Drawable {

	private JLabel imageLabel;

	private BufferedImage displayImage;

	private GUI gui;
	
	public static final int WIDTH = 640, HEIGHT = 480;
	public static final BufferedImage BLANK_IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	public static final int H_LIMIT = 20;
	
	private JPLayer activeLayer;
	private JPLayer defaultLayer;

	private ArrayList<JPLayer> layers;
	private Stack<JPAction> history;
	
	private ImageMouseAdapter imageMouseAdapter;
	private ImageMouseMotionListener imageMouseMotionListener;


	private int lastPointIndex;
	private RenderingHints renderingHints;
	private Color strokeColor;
	private Stroke stroke;
	private JPTool activeTool;

	public ImagePanel(GUI gui) {
		super(new GridBagLayout());
		this.setBorder(new EmptyBorder(0, 2, 2, 2));
		this.setSize(640, 480);

		this.gui = gui;
		history = new Stack<JPAction>();
		lastPointIndex = 0;
		strokeColor = Color.BLACK;
		stroke = new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.7f);

		Map<Key, Object> hints = new HashMap<RenderingHints.Key, Object>();

		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		renderingHints = new RenderingHints(hints);

		displayImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		defaultLayer = new JPLayer("Layer 0", new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB), this);
		activeLayer = defaultLayer;

		layers = new ArrayList<JPLayer>();

		layers.add(defaultLayer);

		imageLabel = new JLabel();
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		imageLabel.setIcon(new ImageIcon(displayImage));
		

		imageMouseAdapter = new ImageMouseAdapter(this);
		imageMouseMotionListener = new ImageMouseMotionListener(this);

		imageLabel.addMouseListener(imageMouseAdapter);
		imageLabel.addMouseMotionListener(imageMouseMotionListener);

		activeTool = new DrawTool(this);
		imageLabel.setCursor(activeTool.getCursor());
		
		this.setBackground(Color.WHITE);
		this.add(imageLabel);
		
		

	}

	public void draw(){
		update();
		imageLabel.repaint();
	}
	
	@Override
	public void draw(Point point) {
		BufferedImage newImage = activeLayer.getImage();
		Graphics2D g = newImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(strokeColor);
		g.setStroke(stroke);

		activeTool.snapShot();

		activeTool.draw(point, g);

		g.dispose();
		imageLabel.repaint();
	}

	@Override
	public void draw(ArrayList<Point> points) {
		BufferedImage newImage = activeLayer.getImage();
		Graphics2D g = newImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(strokeColor);
		g.setStroke(stroke);

		activeTool.setStroke(stroke);

		activeTool.draw(points, g);
		g.dispose();

		imageLabel.repaint();
	}

	public void clearPoints() {
		Graphics2D g = activeLayer.getImage().createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(strokeColor);
		g.setStroke(stroke);
		
		imageMouseMotionListener.updateActions(g, renderingHints, stroke, strokeColor);
		lastPointIndex = 0;
		activeTool.resetLastPointIndex();
	}

	public void setStrokeColor(Color c) {
		strokeColor = c;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public int getLastPointIndex() {
		return lastPointIndex;
	}

	public void setActiveTool(JPTool tool) {
		activeTool = tool;
		imageLabel.setCursor(activeTool.getCursor());
	}

	public BufferedImage getImage() {
		return activeLayer.getImage();
	}

	public BufferedImage getDisplayImage() {
		return displayImage;
	}

	public JPLayer getDefaultLayer() {
		return defaultLayer;
	}

	public RenderingHints getRenderingHints(){
		return renderingHints;
	}
	
	public JPTool getActiveTool(){
		return activeTool;
	}
	
	public ImageMouseMotionListener getIMML() {
		return imageMouseMotionListener;
	}

	public void addLayer(int layerNum, JButton layerButton) {
		BufferedImage tmpImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		JPLayer newLayer = new JPLayer("Layer " + layerNum, tmpImage, this);
		newLayer.setLayerButton(layerButton);
		newLayer.setPosition(layers.size());
		layers.add(newLayer);

	}

	public void addLayer(JPLayer layer) {
		layers.add(layer);
	}

	public void removeLayer(JPLayer layer){
		if(layer == defaultLayer){
			System.out.println("Can't delete default layer!");
			return;
		}
		
		if(getActiveLayer() == layer){
			int previousLayer = layer.getPosition() - 1;
			if(previousLayer >= 0){
				layers.get(previousLayer).getLayerButton().doClick();
			}else{
				defaultLayer.getLayerButton().doClick();				
			}
		}
		
		JButton layerButton = layer.getLayerButton();
		
		gui.getJPToolBarRight().removeLayerButton(layerButton);
		
		int layerPosition = layer.getPosition();
		for(int i = layerPosition+1; i < layers.size(); i++){
			JPLayer tmpLayer = layers.get(i);
			tmpLayer.setPosition(tmpLayer.getPosition()-1);
		}
		
		layers.remove(layer);
		draw();
	}
	
	public void setActiveLayer(JPLayer layer) {
		activeLayer = layer;
	}

	public JPLayer getActiveLayer() {
		return activeLayer;
	}

	public JPLayer getLayer(int index) {
		return layers.get(index);
	}
	
	public JPLayer getLayer(String layerName){
		Iterator<JPLayer> itr = layers.iterator();
		while(itr.hasNext()){
			JPLayer tmpLayer = itr.next();
			if(tmpLayer.getName().equals(layerName)){
				return tmpLayer;
			}
		}
		
		return null;
	}
	
	public void addAction(JPAction action){
		history.push(action);
	}
	
	public JPAction popHistory(){
		if(!history.isEmpty()){
			return history.pop();
		}else{
			return null;
		}
	}
	
	public GUI getGUI(){
		return gui;
	}

	public void update() {
		Graphics2D g = displayImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(strokeColor);
		g.setStroke(stroke);

		
		displayImage.setData(BLANK_IMAGE.getData());

		// g.drawImage(new
		// BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB), 0, 0, WIDTH,
		// HEIGHT, null);

		for (int i = 0; i < layers.size(); i++) {
			Iterator<BufferedImage> itr = layers.get(i).getIterator();
			
			while(itr.hasNext()){
				BufferedImage tempImage = itr.next();
				if(tempImage != null){
					g.drawImage(tempImage, 0, 0, WIDTH, HEIGHT, null);	
				}
			}
			
//			BufferedImage tempImage = layers.get(i).getImage();
//			if(tempImage != null){
//				g.drawImage(tempImage, 0, 0, WIDTH, HEIGHT, null);				
//			}
		}

		g.dispose();
	}
}
