package mc.javapaint;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageMouseAdapter extends MouseAdapter{

	private Drawable drawable;
	private ImageMouseMotionListener imageMouseMotionListener;
	
	public ImageMouseAdapter(Drawable drawable){
		super();
		this.drawable = drawable;
		imageMouseMotionListener = drawable.getIMML();
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		drawable.draw(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e){
		drawable.clearPoints();
		drawable.update();
		//drawable.draw(e.getPoint());
		System.out.println("Mouse released");
	}

}
