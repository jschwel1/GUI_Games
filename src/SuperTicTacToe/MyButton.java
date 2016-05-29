package SuperTicTacToe;

import java.awt.Rectangle;

public class MyButton extends Rectangle{
	boolean enabled;
	public MyButton(int x, int y, int width, int height, boolean on){
		super(x,y,width,height);
		enabled = on;	
	}
	public MyButton(int x, int y, int width, int height){
		super(x,y,width,height);
		enabled = false;	
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	public void setEnabled(boolean e){
		enabled = e;
	}
	public void flipSwitch(){
		enabled = !enabled;
	}
}
