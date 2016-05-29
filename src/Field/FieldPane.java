// create static starting point for arrow



package Field;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

public class FieldPane extends JPanel implements ActionListener, KeyListener, MouseListener{
	//=================== INSTANCE FIELDS ============== //
	public int WIDTH = 800;
	public int HEIGHT = 700;
	private Rectangle cameraView;
	private ArrayList<Tree> trees;
	private ArrayList<Point> clouds;
	private Arrow arrow;
	private Timer clock;
	private boolean left, right, launch, shift, dragging, moveToStart, followToggle, startPointToggle;
	private Target target;
	private Point p1, p2; // starting and ending point for the line of initial velocity
	private int multiplier;
	private MouseEvent me;
	
	//==================== CONSTRUCTORS ===============//
	public FieldPane(int w, int h){
		WIDTH = w;
		HEIGHT = h;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		
		cameraView = new Rectangle(0,0,WIDTH,HEIGHT);
		
		trees = new ArrayList<Tree>();
		for (int i = -10000; i < 10000; i+=((int)((Math.random()*500)+1))){
			System.out.println("tree now at " + i);
			trees.add(new Tree(i, HEIGHT/3));
		}
		
		clouds = new ArrayList<Point>();
		for (int i = -10000; i < 100000; i+=((int)((Math.random()*300)+1))){
			System.out.println("cloud now at " + i);
			clouds.add(new Point(i, ((int)(Math.random()*-500)+100)));
		}
		
		target = new Target(new Point(700, HEIGHT*13/24));
		
		arrow = new Arrow(new Point(-100,-100), new Point(0,0));
		
		clock = new Timer(30, this);
		clock.start();
		this.addKeyListener(this);
		
		
		
		left = false;
		right = false;
		shift = false;
		dragging = false;
		moveToStart = false;
		followToggle = true;
		startPointToggle = true;
		
		p1 = new Point(100, (HEIGHT*195/300));
		
		multiplier = 1;
		this.revalidate();
		repaint();
	}
	
	//======================== Methods ===================//
	//------------------------ PaintComponent ------------//
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		// sky (blue)
		g2.setColor(new Color(50,50,255));
		g2.fillRect(0,0+cameraView.y,WIDTH, HEIGHT*2);
		// grass (green)
		g2.setColor(new Color(0,150,0));
		g2.fillRect(0,HEIGHT*2/3-cameraView.y,WIDTH, 1000);
		
		
		// trees
		for (int i = 0; i < trees.size(); i++){
			if ((trees.get(i).getLoc().x - cameraView.x >= -150) && (trees.get(i).getLoc().x - cameraView.x < WIDTH + 150))
				trees.get(i).getImage().paintIcon(this, g2, trees.get(i).getLoc().x - cameraView.x, trees.get(i).getLoc().y - cameraView.y);
		}

		// distance signs
		for (int i = 100; i <= 10100; i+=500){
			g2.setColor(new Color(150,50,0));
			g2.fill3DRect(i-cameraView.x + 20, HEIGHT*7/12+30 - cameraView.y, 10, 50, true);
			g2.fill3DRect(i-cameraView.x, HEIGHT*7/12+20 - cameraView.y, 50, 25, true);
			g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
			g2.setColor(Color.black);
			g2.drawString(""+(i-100), i-cameraView.x + 5, HEIGHT*7/12+40 - cameraView.y);
		}
		
		
		g2.setColor(Color.white);
		for (int i = 0; i < clouds.size(); i++){
			if ((clouds.get(i).x - cameraView.x >= -150) && (clouds.get(i).x - cameraView.x < WIDTH + 150))
				g2.fillOval(clouds.get(i).x - cameraView.x, clouds.get(i).y - cameraView.y, 75, 25);
		}
		// target
		target.getImage().paintIcon(this, g2, target.getLoc().x - cameraView.x, target.getLoc().y-cameraView.y);
		// arrow
		arrow.getImage().paintIcon(this, g2, arrow.getLoc().x - cameraView.x, arrow.getLoc().y - cameraView.y);
		// line of fire
		if (dragging){
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.black);
			mouseClicked(me);
			g2.drawLine(p1.x, p1.y, p2.x, p2.y);
			
		}
		
	}


	//==================== KEYBOARD INPUT METHODS ==============//
	public void keyPressed(KeyEvent e) {
		System.out.println("beep");
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT){
			left = true;
			System.out.println("left");
		}
		if (code == KeyEvent.VK_RIGHT){
			right = true;
			System.out.println("right");
		}
		if (code == KeyEvent.VK_N){
			trees.add(new Tree(cameraView.x+125,HEIGHT/3));
		}
		if (code == KeyEvent.VK_SHIFT){
			multiplier = 5;
		}
		if (code == KeyEvent.VK_SPACE){
			if (moveToStart)
				moveToStart = false;
			else
				cameraView.setLocation(new Point(0,0));
		}
		if (code == KeyEvent.VK_F){
			followToggle = !followToggle;
		}
		if (code == KeyEvent.VK_ESCAPE)
			startPointToggle = !startPointToggle;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT){
			left = false;
		}
		if (code == KeyEvent.VK_RIGHT){
			right = false;
		}
		if (code == KeyEvent.VK_SHIFT){
			multiplier = 1;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

	// ======================= ACTION PERFORMED ==========//
	public void actionPerformed(ActionEvent e) {
		this.repaint();
		
		// movement
		if (left)
			cameraView.x-=(4*multiplier);
		if (right)
			cameraView.x+=(4*multiplier);
		
		// arrow launch
		if (launch){
			launch = false;
			arrow.move = true;
		}
		// move the arrow (and screen)
		if (arrow.move){
			arrow.move();
			if (arrow.hitSomething(target.getRect()) != 0){
				arrow.move = false;
				if (arrow.hitSomething(target.getRect()) == 1){
					System.out.println("hit!!!!!!!!!!!!!");
					int x = ((int)(Math.random()*1000 + 700));
					System.out.println(x);
					target = new Target(new Point(x, HEIGHT*13/24));
					cameraView.x = (x-WIDTH);
					moveToStart = true;
				}
			}
			
			
			if (followToggle){
				if (arrow.getLoc().x >= WIDTH*3/4)
					cameraView.x = arrow.getLoc().x-WIDTH*3/4;
				if (arrow.getLoc().y <= HEIGHT/4)
					cameraView.y = arrow.getLoc().y-HEIGHT/4;
			}
			if (!arrow.move){
				moveToStart = true;
			}
		}
		if (moveToStart){
			// move x into position
			if (cameraView.x>=15)
				cameraView.x-=(15*multiplier);
			if (cameraView.x < 15 && cameraView.x >0)
				cameraView.x--;
			if (cameraView.x<=-15)
				cameraView.x+=(15*multiplier);
			if (cameraView.x > -15 && cameraView.x <0)
				cameraView.x ++;
			
			// move y into position
			if (cameraView.y>=15)
				cameraView.y-=(15*multiplier);
			if (cameraView.y < 15 && cameraView.y >0)
				cameraView.y--;
			if (cameraView.y<=-15)
				cameraView.y+=(15*multiplier);
			if (cameraView.y > -15 && cameraView.y <0)
				cameraView.y ++;
			if (cameraView.x == 0 && cameraView.y == 0)
				moveToStart = false;
		}
		
		if (dragging){
			p2 = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p2, this);
		}
	}

	// ================= MOUSE OPTIONS ==========================//
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dragging = true;
		if (!startPointToggle)
			p1 = e.getPoint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragging = false;
		p2 = e.getPoint();
		arrow = new Arrow(new Point(p2), new Point(p2.x - p1.x, p2.y-p1.y));
		launch = true;
		System.out.println("launching with p2 at " + p2);
		
	}
}
