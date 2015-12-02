package game.main.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.main.*;
import game.main.graphics.ImageManager;
import game.main.graphics.Render;

public class Zombie {
	private int x, y = 350;
	private ImageManager iM;
	
	protected boolean visible = true;
	
	public Zombie(ImageManager iM, int xVal){
		x = xVal;
		this.iM = iM;
	}
	
	public void randX()
	{
		Random ran = new Random();
		x = ran.nextInt(40) + 400;
	}
	
	public void tick(){
		 int zSpeed = 0;
			if(Game.wpm > 49 && Game.wpm < 60){
				zSpeed = 0;
				if(zombieTouch())
					zSpeed = -1;
			}
			else if(Game.wpm >= 60 ){
				if(Game.plyrSpeed > 60)
					Game.plyrSpeed += 10;
				zSpeed = -1;
			}
			else if(Game.wpm < 50 && x < Player.getX() - 20){
				zSpeed = 1;
			}
		x += zSpeed;
		if(x < -100){
			Game.zombies.clear();
		}
		
		if(zombieTouch())
			Render.updateHealth();
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, 65, 30);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	public boolean zombieTouch(){
		int extreme = x + 57;
			
		if(extreme > Player.getX())
			return true;
		return false;
	}
	
	public BufferedImage getSprite(){
		return iM.zombie.sprite;
	}

	public boolean isVisible() {
		return visible;
	}
}
