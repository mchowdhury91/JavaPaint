package mc.javapaint;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;

public class JPLayer {

	private BufferedImage image;
	private JButton layerButton;
	private String name;
	private int position;
	private ImagePanel imagePanel;
	
	ArrayList<BufferedImage> imageList;
	
	public JPLayer(String name, BufferedImage image, ImagePanel imagePanel) {
		this.name = name;
		this.image = image;
		this.imagePanel = imagePanel;
		
		imageList = new ArrayList<>();
	}
	
	public void addImage(BufferedImage newImage){
		imageList.add(newImage);
	}
	
	
	// removes and returns the last image in the list
	public void removeImage(){
		if(imageList.size() < 1){
			return;
		}
		
		//BufferedImage lastImage = imageList.get(imageList.size() - 1);
		imageList.remove(imageList.size()-1);
	}
	
	public BufferedImage getBottomImage(){
		if(imageList.size() >= 1){
			return imageList.get(0);
		}else{
			return null;
		}
	}
	
	public BufferedImage getImage() {
		if(imageList.size() < 1){
			return null;
		}
		
		return imageList.get(imageList.size()-1);

	}

	public Iterator<BufferedImage> getIterator(){
		return imageList.iterator();
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
		imagePanel.getGUI().getStatusLabel().setText(name);
	}

	// TODO: Delete later
	public int getListSize(){
		return imageList.size();
	}
}
