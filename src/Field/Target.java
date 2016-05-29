package Field;

import java.awt.Point;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;

public class Target {
	private Point loc;
	private ImageIcon image;
	private Rectangle rect;
	
	public Target(Point l){
		setLoc(l);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		String imagePath = "Target.gif";
		URL imageURL = cldr.getResource(imagePath);
		image = new ImageIcon(imagePath);
		
		setRect(new Rectangle(l.x, l.y, 80, 100));
	}

	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}

	public ImageIcon getImage() {
		return image;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
}
