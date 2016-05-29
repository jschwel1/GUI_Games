package Field;

import java.awt.Point;
import java.net.URL;

import javax.swing.ImageIcon;

public class Tree {
	private Point loc;
	private ImageIcon image;
	
	//================= Constructors ======
	public Tree(){
		this(new Point(0,0));
	}
	
	public Tree(int x, int y){
		this(new Point(x,y));
	}
	public Tree(Point l){
		setLoc(l);
		ClassLoader cldr = this.getClass().getClassLoader();
		String imagePath = "Tree.gif";
		URL imageURL = cldr.getResource(imagePath);
		image = new ImageIcon(imagePath);
	}
	public ImageIcon getImage(){
		return image;
	}

	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}
	
}
