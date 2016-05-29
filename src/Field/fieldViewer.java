package Field;

import java.awt.Container;

import javax.swing.JFrame;

public class fieldViewer extends JFrame{
	public static void main(String[] args){
		fieldViewer window = new fieldViewer();
		FieldPane field = new FieldPane(1900, 700);
		window.addKeyListener(field);
		window.addMouseListener(field);
		window.setVisible(true);
		window.setSize(800,700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setContentPane(field);
		/*
		fieldViewer fv = new fieldViewer();
		Container window = fv.getContentPane();
		window.setSize(1200, 800);
		fv.setVisible(true);
		fv.setSize(1200, 800);
		fv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fv.setResizable(true);
		FieldPane fp = new FieldPane(1200, 800);
		window.add(fp);
		*/
		
	}
}
