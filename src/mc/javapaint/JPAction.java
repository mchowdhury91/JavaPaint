package mc.javapaint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

public class JPAction {

	private JPLayer layer;
	ImagePanel imagePanel;
	Stroke stroke;
	RenderingHints renderingHints;
	Color strokeColor;
	
	public JPAction(JPLayer layer, ImagePanel imagePanel) {
		setLayer(layer);
		this.imagePanel = imagePanel;
	}


	public JPLayer getLayer() {
		return layer;
	}


	public void setLayer(JPLayer layer) {
		this.layer = layer;
	}

	public void discard(){
		BufferedImage tempImage = layer.getImage();
		
		if(tempImage == layer.getBottomImage()){
			return;
		}
		
		Graphics2D g = layer.getBottomImage().createGraphics();
		g.drawImage(tempImage, 0, 0, null);
		g.dispose();
		layer.removeImage();
	}
	
	public void undo(){
		layer.removeImage();
		imagePanel.draw();
	}
}
