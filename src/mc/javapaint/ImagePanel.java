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
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mc.javapaint.tools.DrawTool;
import mc.javapaint.tools.JPTool;

public class ImagePanel extends JPanel implements Drawable {

	private JLabel imageLabel;

	private BufferedImage activeImage;
	private BufferedImage newImage;
	
	private ArrayList<BufferedImage> layers;
	
	private ImageMouseAdapter imageMouseAdapter;
	private ImageMouseMotionListener imageMouseMotionListener;
	
	public static final int WIDTH = 640,
						 	 HEIGHT = 480;
	
	private int lastPointIndex;
	private RenderingHints renderingHints;
	private Color strokeColor;
	private Stroke stroke;
	private JPTool activeTool;
	
	public ImagePanel() {
		super(new GridBagLayout());
		this.setBorder(new EmptyBorder(0,2,2,2));
		this.setSize(640,480);
		lastPointIndex = 0;
		strokeColor = Color.BLACK;
		stroke = new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.7f);
		
		Map<Key, Object> hints = new HashMap<RenderingHints.Key, Object>();
		
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		renderingHints = new RenderingHints(hints);

		newImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		activeImage = newImage;
		
		layers = new ArrayList<BufferedImage>();
		
		BufferedImage testImage = new BufferedImage(640,480,BufferedImage.TYPE_INT_ARGB);
		Graphics2D test2D = testImage.createGraphics();
		
		test2D.setColor(Color.GREEN);
		test2D.drawOval(20, 20, 100, 100);
		test2D.dispose();
		
		layers.add(testImage);
		layers.add(newImage);
		
		imageLabel = new JLabel();
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		imageLabel.setIcon(new ImageIcon(newImage));
		
		imageMouseAdapter = new ImageMouseAdapter(this);
		imageMouseMotionListener = new ImageMouseMotionListener(this);
		
		imageLabel.addMouseListener(imageMouseAdapter);		
		imageLabel.addMouseMotionListener(imageMouseMotionListener);
		
		activeTool = new DrawTool(this);
		
		this.setBackground(Color.WHITE);
		this.add(imageLabel);
		
	}
	
	@Override
	public void draw(Point point){
		Graphics2D g = activeImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(strokeColor);
		g.setStroke(stroke);
		
		activeTool.snapShot();
		
		activeTool.draw(point, g);
		
		g.dispose();
		imageLabel.repaint();
	}
	
	@Override
	public void draw(ArrayList<Point> points){
		activeTool.setStroke(stroke);
		
		Graphics2D g = activeImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(strokeColor);
		g.setStroke(stroke);
		
		activeTool.draw(points, g);		
		g.dispose();
		
		imageLabel.repaint();
	}
	
	public void clearPoints(){
		imageMouseMotionListener.clearPoints();
		lastPointIndex = 0;
	}
	
	public void setStrokeColor(Color c){
		strokeColor = c;
	}
	
	public void setStroke(Stroke stroke){
		this.stroke = stroke;
	}
	
	public Color getStrokeColor(){
		return strokeColor;
	}
	
	public Stroke getStroke(){
		return stroke;
	}
	
	public int getLastPointIndex(){
		return lastPointIndex;
	}
	
	public void setActiveTool(JPTool tool){
		activeTool = tool;
	}
	
	public BufferedImage getImage(){
		return activeImage;
	}
	
	public ImageMouseMotionListener getIMML(){
		return imageMouseMotionListener;
	}
	
	public void addLayer(){
		
	}
	
	public void update(){
		Graphics2D g = activeImage.createGraphics();
		g.setRenderingHints(renderingHints);
		g.setColor(strokeColor);
		g.setStroke(stroke);
		activeTool.update(g);
		
		for(int i = 0; i < layers.size(); i++){
			g.drawImage(layers.get(0), 0, 0, WIDTH, HEIGHT, null);
		}
		
		g.dispose();
	}
}
