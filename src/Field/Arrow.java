package Field;

import java.awt.Point;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;

public class Arrow {
	private Point loc;
	private int G;
	public Point vel;
	public boolean move;
	private ImageIcon image;
	private Rectangle rect;
	
	public Arrow(Point l, Point v){
		vel = v;
		loc = l;
		G = 8;
		move = false;
		
		ClassLoader cldr = this.getClass().getClassLoader();
		String imagePath = "Arrow.gif";
		URL url = cldr.getResource(imagePath);
		image = new ImageIcon(imagePath);
		setRect(new Rectangle(l.x, l.y, 50, 7));
		
	}
	public void move(){
		System.out.println(loc + "  " + vel);
		loc.x += (int)vel.x/10;
		loc.y += (int)vel.y/10;
		rect.setLocation(loc);
		vel.y+= G;
	}
	/**
	 * method: hitSomething
	 * this methods checks if the arrow ran into another object by checking surrounding rectangles
	 * @param other
	 * @return -1 - hit the ground
	 * 			0 - didn't hit anythign
	 * 			1 - hit the target (or Rectangle)
	 */
	public int hitSomething(Rectangle other){
		if (rect.contains(other) || other.contains(rect)){
			System.out.println("a");
			return 1;
		}
		if (rect.getLocation().y >= 1400/3){
			System.out.println("b");
			return -1;
		}
		else {
			System.out.println("c");
			return 0;
		}
	}
	public ImageIcon getImage() {
		return image;
	}
	
	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}
	public Rectangle getRect() {
		return rect;
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	
	
}
