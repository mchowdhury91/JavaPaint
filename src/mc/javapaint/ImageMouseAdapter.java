package mc.javapaint;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImageMouseAdapter extends MouseAdapter{

	private ImagePanel imagePanel;
	private ImageMouseMotionListener imageMouseMotionListener;
	
	public ImageMouseAdapter(ImagePanel imagePanel){
		super();
		this.imagePanel = imagePanel;
		imageMouseMotionListener = imagePanel.getIMML();
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		JPLayer activeLayer = imagePanel.getActiveLayer();
		activeLayer.addImage(new BufferedImage(imagePanel.WIDTH, imagePanel.HEIGHT, BufferedImage.TYPE_INT_ARGB));
		imagePanel.draw(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e){
		imagePanel.clearPoints();
		imagePanel.update();
		BufferedImage img = imagePanel.getActiveLayer().getImage();
		
		boolean isTransparent = true;
		for(int y = 0; y < imagePanel.HEIGHT; y++){
			for(int x = 0; x < imagePanel.WIDTH; x++){
				int pixel = img.getRGB(x, y);
				if((pixel >> 24 == 0x00)){
					isTransparent = true;
				}else{
					isTransparent = false;
					break;
				}
			}
			if(!isTransparent){
				break;
			}
		}
		
		if(isTransparent){
			imagePanel.getActiveLayer().removeImage();
		}
		
		System.out.println(imagePanel.getActiveLayer().getListSize());
	}

}
