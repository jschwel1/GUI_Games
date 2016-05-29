package SuperTicTacToe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Slider extends Rectangle{
	int max, min, val;
	
	public Slider(int x, int y, int maximum, int minimum, int value){
		super(x,y,20,200);
		max = maximum;
		min = minimum;
		val = value;
	}
	
	public void setVal(int v){
		val = v;
	}
	
}
