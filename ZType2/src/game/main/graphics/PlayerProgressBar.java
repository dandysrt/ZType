package game.main.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class PlayerProgressBar {
	
	private int x = 1050, y = 100;
	private int w = 0, h = 10;
	
	public PlayerProgressBar(){
		
	}
	
	public void adjustY(int val){
		y += val;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getW(){
		return w;
	}
	
	public int getH(){
		return h;
	}
	
	public void adjustW(int val){
		w = val;
	}
	
	public void updateW(int val){
		w += val;
	}
	
	public void renderProgressBar(Graphics2D g, Color color){
		g.setColor(color);
		g.fillRect(getX(), getY(), getW(), getH());
		g.setColor(Color.WHITE);
		g.drawRect(getX(), getY(), 100, 10);
	}

}
