// Project Super Tic-Tac-Toe
// Written by: Jacob Schwell
// written on 1/9/14
// updated on 1/10/14,
// This is an intense version of Tic-Tac-Toe... it is not for the faint of heart
// 
package SuperTicTacToe;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TTTViewer extends JFrame {
	Container window;
	TTTPane game;
	MenuPane menu;
	JPanel pane;
	public static void main(String[] args){
		TTTViewer t = new TTTViewer();
	}
	
	public TTTViewer(){
		window = this.getContentPane();
		game = new TTTPane();
		menu = new MenuPane();
		pane = game;
		this.setSize(750,770);
		this.setTitle("Super Tic-Tac-Toe ¨");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		window.add(pane);
		this.setVisible(true);
		
		
	}



}
