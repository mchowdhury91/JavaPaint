package mc.javapaint.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import mc.javapaint.ImagePanel;

public class JPUtils {

	public static BufferedImage copyImage(BufferedImage bi){
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static boolean isTransparent(BufferedImage image){
		
		for(int y = 0; y < ImagePanel.HEIGHT; y++){
			for(int x = 0; x < ImagePanel.WIDTH; x++){
				int pixel = image.getRGB(x, y);
				if(!((pixel>>24) == 0x00)){
					return false;
				}
			}
		}
		
		return true;
	}
	
}
