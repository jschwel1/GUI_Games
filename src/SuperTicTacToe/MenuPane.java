package SuperTicTacToe;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MenuPane extends JPanel{
	public MenuPane(){
		repaint();
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(0,0,800,800);
	}
}
