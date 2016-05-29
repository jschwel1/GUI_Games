// EDIT: 



package SuperTicTacToe;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TTTPane extends JPanel implements MouseListener, ActionListener, MouseMotionListener{
	int HEIGHT = 750;
	int WIDTH = 750;
	int freePlaces;
	int winPlaces;
	int checkBox;
	long startTime;
	Timer clock;
	Board[][] bigBoard;
	Board overView;
	ArrayList<Point> playableBoard; 
	boolean freeChoice;
	boolean menuBar;
	boolean menu;
	char player;
	Font lPiece;
	Font bPiece;
	Font smallText;
	Font moderateText;
	MyButton smallPieceCheckBox;
	MyButton bigPieceCheckBox;
	Rectangle menuRect;
	Rectangle menuBarRect;
	Rectangle redSlide;
	Rectangle greenSlide;
	Rectangle blueSlide;
	Color smallColor;
	Color bigColor;
	Slider red;
	Slider blue;
	Slider green;
	
	
	
	public static final char EMPTY = ' ';
	public static final char PLAYER1 = 'X';
	public static final char PLAYER2 = 'O';
	public TTTPane(){
		//		JOptionPane.showMessageDialog(this, "Welcome to java Super-Tic-Tac-Toe! this program was written by me, Jacob Schwell so that you can waste a little more time from your day and hopefully \nenjoy a pretty neat game. \nP.S. I have no clue who made this game or where it came from, but credit to Brian for showing it to me. have fun!");
		// board stuff
		bigBoard = new Board[3][3];
		playableBoard = new ArrayList<Point>();
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				bigBoard[i][j] = new Board();
				playableBoard.add(new Point(i,j));
			}
		}
		player = 'X';
		overView = new Board();
		freeChoice = false;
		freePlaces = 81; // freePlaces >= winPlaces (always)
		winPlaces = 0;
		// pieces
		smallColor = Color.red;
		bigColor = Color.blue;
		
		// other
		menuBar = false;
		menu = false;
		clock = new Timer(30, this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		// fonts
		lPiece = new Font(Font.MONOSPACED, Font.PLAIN, (WIDTH-42)/9);
		bPiece = new Font(Font.MONOSPACED, Font.PLAIN, (WIDTH-5)/3);
		smallText = new Font(Font.SERIF, Font.PLAIN, 10);
		moderateText = new Font(Font.SANS_SERIF, Font.BOLD, 15);
		
		// menu stuff
		menuBarRect = new Rectangle(730, 0, 30, 800);
		menuBarRect = new Rectangle(0, 0, 20, 800);
		menuRect = new Rectangle(0, 25, 730,700);
		smallPieceCheckBox = new MyButton(WIDTH/2 - 75, 100, 50,50);
		bigPieceCheckBox = new MyButton(WIDTH/2 + 25, 100, 50,50);
		red = new Slider(WIDTH/2-100, 250, 255, 0, 0);
		green = new Slider(WIDTH/2-10, 250, 255, 0, 0);
		blue = new Slider(WIDTH/2+80, 250, 255, 0, 0);
		redSlide = new Rectangle(red.x-5, red.y+(red.val/red.height), red.width+10, 10);
		greenSlide = new Rectangle(green.x-5, green.y+(green.val/green.height), green.width+10, 10);
		blueSlide = new Rectangle(blue.x-5, blue.y+(blue.val/blue.height), blue.width+10, 10);		
	}

	public void reset(){
		bigBoard = new Board[3][3];
		playableBoard.clear();
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				bigBoard[i][j] = new Board();
				playableBoard.add(new Point(i,j));
			}
		}
		player = 'X';
		overView = new Board();
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		// Background
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// playable board background
		g.setColor(new Color(0,41,1));
		for(Point p: playableBoard)
			g.fillRect(p.x*(WIDTH)/3, p.y*(HEIGHT)/3, (WIDTH/3)-1, (HEIGHT/3)-1);

		// big lines
		g.setColor(new Color(255,255,0));
		g.fillRect((WIDTH-8)/3, 0, 5, HEIGHT);
		g.fillRect((WIDTH-8)*2/3, 0, 5, HEIGHT);
		g.fillRect(0, (HEIGHT-8)/3, WIDTH, 5);
		g.fillRect(0, (HEIGHT-8)*2/3, WIDTH, 5);
		// little lines
		g.setColor(new Color(200,200,50));
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				g.fillRect((WIDTH-6)/3*i+(WIDTH-6)/9, (HEIGHT)/3*j+15, 3, (HEIGHT)/3-30);
				g.fillRect((WIDTH-6)/3*i+(WIDTH-6)*2/9, (HEIGHT)/3*j+15, 3, (HEIGHT)/3-30);
				g.fillRect((HEIGHT)/3*j+15,(WIDTH-6)/3*i+(WIDTH-6)/9, (HEIGHT)/3-30, 3);
				g.fillRect((HEIGHT)/3*j+15,(WIDTH-6)/3*i+(WIDTH-6)*2/9, (HEIGHT)/3-30, 3);
			}
		}
		
		// Pieces
		g.setFont(lPiece);
		g.setColor(smallColor);
		for (int bR = 0; bR < 3; bR++){
			for (int bC = 0; bC < 3; bC++){
				for (int lR = 0; lR < 3; lR++){
					for (int lC = 0; lC < 3; lC++){
						g.drawString(bigBoard[bR][bC].getCharAt(lR, lC)+"", (int)(WIDTH*(1.0/27.0)) + ((WIDTH-5)*lC/9) + ((WIDTH-5)*bC/3), (int)(HEIGHT*(1.0/10.0)) + (HEIGHT-5)*lR/9 + (HEIGHT-5)*bR/3);	
					}
				}
			}
		}

		g.setColor(bigColor);
		g.setFont(bPiece);
		for (int r = 0; r < 3; r++){
			for (int c = 0; c < 3; c++){
				g.drawString(overView.getCharAt(r,c)+"", WIDTH/16 + (WIDTH*c/3), (int)(HEIGHT*(17.0/64.0))+HEIGHT*r/3);
			}
		}
		if (!menuBar){
			g.setFont(moderateText);
			g.setColor(new Color(100,100,100));
			g.drawString("?", 0, 20);
		}
		// =========================== MENU STUFF ================= //
		if (menu){
			// clear background
			g2.setColor(new Color(.7f, .7f, .7f, .85f));
			g2.fillRect(menuRect.x, menuRect.y, menuRect.width, menuRect.height);
			g2.setColor(new Color(0.1f, .2f, .1f, .85f));
			g2.setFont(moderateText);
			// Texts over boxes
			g2.drawString("Small Pieces",smallPieceCheckBox.x-25, smallPieceCheckBox.y-5);
			g2.drawString("Big Pieces", bigPieceCheckBox.x-15, bigPieceCheckBox.y-5);
			
			// shadow selected box
			g2.setColor(new Color(0.1f, 0.1f, 0.1f, .5f));
			if(smallPieceCheckBox.isEnabled())
				g2.fillRect(smallPieceCheckBox.x+4, smallPieceCheckBox.y+4, smallPieceCheckBox.width, smallPieceCheckBox.height);
			if(bigPieceCheckBox.isEnabled())
				g2.fillRect(bigPieceCheckBox.x+4, bigPieceCheckBox.y+4, bigPieceCheckBox.width, bigPieceCheckBox.height);
			// MyButtons
			g2.setColor(smallColor);
			g2.fillRect(smallPieceCheckBox.x, smallPieceCheckBox.y, smallPieceCheckBox.width, smallPieceCheckBox.height);
			g2.setColor(bigColor);
			g2.fillRect(bigPieceCheckBox.x, bigPieceCheckBox.y, bigPieceCheckBox.width, bigPieceCheckBox.height);
			// border boxes
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke(4));
			g2.drawRect(smallPieceCheckBox.x, smallPieceCheckBox.y, smallPieceCheckBox.width-2, smallPieceCheckBox.height-2);
			g2.drawRect(bigPieceCheckBox.x, bigPieceCheckBox.y, bigPieceCheckBox.width-2, bigPieceCheckBox.height-2);
				
			// RGB
			g2.setColor(new Color(100,100,100));
			g2.fillRect(red.x, red.y, red.width, red.height);
			g2.fillRect(green.x, green.y, green.width, green.height);
			g2.fillRect(blue.x, blue.y, blue.width, blue.height);
			
			g2.setColor(new Color(50,50,50));
			g2.fillRect(redSlide.x, redSlide.y, redSlide.width, redSlide.height);
			g2.fillRect(greenSlide.x, greenSlide.y, greenSlide.width, greenSlide.height);
			g2.fillRect(blueSlide.x, blueSlide.y, blueSlide.width, blueSlide.height);
			
			g2.setColor(Color.red);
			g2.drawString("Red", red.x-5, red.y);
			g2.drawString(red.val + "", red.x, red.y+red.height + 20);
			g2.setColor(new Color(0,50,0));// dark green so it will be visible
			g2.drawString("Green", green.x-10, green.y);
			g2.drawString(green.val + "", green.x, green.y+green.height + 20);
			g2.setColor(Color.blue);
			g2.drawString("blue", blue.x-5, blue.y);
			g2.drawString(blue.val + "", blue.x, blue.y+blue.height + 20);
		}
		if (menuBar){
			g2.setColor(new Color(0.5f, .5f, .5f, .9f));
			g.fillRect(menuBarRect.x, menuBarRect.y, menuBarRect.width, menuBarRect.height);
		}
		
		// name
		g.setFont(smallText);
		g.setColor(Color.white);
		g.drawString("Written by: Jacob Schwell", 600, 745);
	}



	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (menuBarRect.contains(new Point(x,y)) && !menuBar){
			startTime = System.currentTimeMillis();
			clock.start();
		}
		else if (!menuBar){
			int bRow = y*3/(HEIGHT-8);
			int bCol = x*3/(WIDTH-8);

			int lRow = ((y-5)*9/HEIGHT)%3;
			int lCol = ((x-5)*9/WIDTH)%3;

			// place piece
			if (playableBoard.contains(new Point(bCol,bRow))){
				boolean okay = true;
				if (freeChoice){
					okay = false;
					winPlaces = 0;
					freePlaces = 0;
					// get freePlaces and winPlaces
					for (int bR = 0; bR < 3; bR++){
						for (int bC = 0; bC < 3; bC++){
							for (int lR = 0; lR < 3; lR++){
								for (int lC = 0; lC < 3; lC++){
									if (bigBoard[bR][bC].getCharAt(lR, lC) == EMPTY)
										freePlaces++;
									if (bigBoard[bR][bC].givesWin(lR, lC, player))
										winPlaces++;
								}
							}
						}
					}
					//				System.out.println("free places: " + freePlaces + "   winPlaces: " + winPlaces);
					// if freeplaces is == winPlaces, okay
					if (freePlaces == winPlaces)
						okay = true;
					// if freePlaces > winPlaces, check that the spot doesn't give a win
					else{
						if (!bigBoard[bRow][bCol].givesWin(lRow, lCol, player))
							okay = true;
					}
				}
				if (okay) {
					// if piece was place, change turns
					if (bigBoard[bRow][bCol].placePiece(lRow, lCol, player) == 0){
						// check for little board winner or full
						if (bigBoard[bRow][bCol].hasWin()){
							// check for big board winner
							overView.placePiece(bRow, bCol, player);
							if (overView.hasWin()){
								repaint();
								if (JOptionPane.showConfirmDialog(this, "Congratulations player " + overView.getWinner() + ", you won! would you like to start another game?", "WE HAVE A WINNER!!!", JOptionPane.YES_NO_OPTION) == 0);
									reset();
							}
						}
						// change players:
						if (player == PLAYER1)
							player = PLAYER2;
						else
							player = PLAYER1;

						// get playable board(s)
						playableBoard.clear();
						// if you can't play on the next board, check if you can play on the same one
						if (bigBoard[lRow][lCol].isFull() || bigBoard[lRow][lCol].hasWin()){
							System.out.println("a");
							// otherwise add the same board
							if (!bigBoard[bRow][bCol].isFull() && !bigBoard[bRow][bCol].hasWin()){
								freeChoice = false;
								playableBoard.add(new Point(bCol, bRow));
								System.out.println("c");
							}
							// is the same board unplayable?
							else {
								System.out.println("b");
								// add all possible boards
								freeChoice = true;
								for(int i = 0; i < 3; i++){
									for (int j = 0; j < 3; j++){
										System.out.println(i+","+j+" is full? " +  bigBoard[i][j].isFull() + "   has win? " + bigBoard[i][j].hasWin());
										if ((!bigBoard[i][j].isFull()) && (!bigBoard[i][j].hasWin())){
											playableBoard.add(new Point(j,i));
										}
									}
								}
							}

						}
						else{
							playableBoard.add(new Point(lCol, lRow));
							freeChoice = false;
						}
						System.out.println(playableBoard);
					}
				}
			}
		}
		else if (menu && menuBar){
			if (!menuRect.contains(new Point(x,y))) {
				menu = false;
				menuBar = false;
			}
			// change color of small pieces
			if (smallPieceCheckBox.contains(x,y)){
				smallPieceCheckBox.setEnabled(true);
				bigPieceCheckBox.setEnabled(false);
				
				// correct sliders and positions
				red.val=smallColor.getRed();
				green.val=smallColor.getGreen();
				blue.val=smallColor.getBlue();
				redSlide =  new Rectangle(red.x-5, (red.y+(int)(red.height*(double)red.val/(double)red.max)), red.width+10, 10);
				greenSlide =  new Rectangle(green.x-5, (green.y+(int)(green.height*(double)green.val/(double)green.max)), green.width+10, 10);
				blueSlide =  new Rectangle(blue.x-5, (blue.y+(int)(blue.height*(double)blue.val/(double)blue.max)), blue.width+10, 10);		
				
			}
			// change color of big pieces
			if (bigPieceCheckBox.contains(x,y)){
				bigPieceCheckBox.setEnabled(true);
				smallPieceCheckBox.setEnabled(false);
				red.val=bigColor.getRed();
				green.val=bigColor.getGreen();
				blue.val=bigColor.getBlue();
				redSlide = new Rectangle(red.x-5, (red.y+(int)(red.height*(double)red.val/(double)red.max)), red.width+10, 10);
				greenSlide =  new Rectangle(green.x-5, (green.y+(int)(green.height*(double)green.val/(double)green.max)), green.width+10, 10);
				blueSlide =  new Rectangle(blue.x-5, (blue.y+(int)(blue.height*(double)blue.val/(double)blue.max)), blue.width+10, 10);		
			}
			
		}	
		else {// if just menuBar
			if (menuBarRect.contains(new Point(x,y))){
				menu = true;
			}
			else {
				menu = false;
				menuBar = false;
			}
		}
		System.out.println(e.getX() + " " + e.getY());
		repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		clock.stop();
		
		// fix colors
		
		if (smallPieceCheckBox.isEnabled()){
			smallColor = new Color(red.val, green.val, blue.val);
		}
		if (bigPieceCheckBox.isEnabled()){
			bigColor = new Color(red.val, green.val, blue.val);
		}
		repaint();
	}
	
	public void mouseDragged(MouseEvent e) {
		if(red.contains(e.getPoint())){
				redSlide.y = e.getY();
		}
		if(green.contains(e.getPoint())){
				greenSlide.y = e.getY();
		}
		if(blue.contains(e.getPoint())){
				blueSlide.y = e.getY();	
		}
		if(redSlide.y-red.y < 0)
			redSlide.y = red.y;
		if (redSlide.y-red.y > 255)
			redSlide.y = red.y + 255;
		if(greenSlide.y-green.y < 0)
			greenSlide.y = green.y;
		if (greenSlide.y-green.y > 255)
			greenSlide.y = green.y + 255;
		if(blueSlide.y-blue.y < 0)
			blueSlide.y = blue.y;
		if (blueSlide.y-blue.y > 255)
			blueSlide.y = blue.y + 255;
		System.out.println(red.val + ", " + green.val + ", "+ blue.val);
		
		//set the colors:
		red.val = (int)((redSlide.y - red.y)*((double)red.max/((double)red.height)));
		green.val = (int)((greenSlide.y - green.y)*((double)green.max/((double)green.height)));
		blue.val = (int)((blueSlide.y - blue.y)*((double)blue.max/((double)blue.height)));
		
		repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (System.currentTimeMillis() - startTime > 500){
			menuBar = true;
			repaint();
			clock.stop();
		}
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	
}
