package mc.javapaint;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract interface Drawable {

	void draw(Point point);
	void draw(ArrayList<Point> points);
	void clearPoints();
	BufferedImage getImage();
	ImageMouseMotionListener getIMML();
	void update();

}
