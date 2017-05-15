package mc.javapaint;

import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class JPLayer {

	private BufferedImage image;
	private JButton layerButton;
	private String name;
	private int position;
	private ImagePanel imagePanel;
	
	public JPLayer(String name, BufferedImage image, ImagePanel imagePanel) {
		this.name = name;
		this.image = image;
		this.imagePanel = imagePanel;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public JButton getLayerButton() {
		return layerButton;
	}

	public void setLayerButton(JButton layerButton) {
		this.layerButton = layerButton;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void makeActive(){
		imagePanel.setActiveLayer(this);
	}

}
