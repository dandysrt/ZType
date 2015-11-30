package game.main.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.main.*;
import game.main.graphics.ImageManager;

public class Zombie {
	private int x, y = 350;
	private ImageManager iM;
	
	protected boolean visible = true;
	
	public Zombie(ImageManager iM){
		randX();
		this.iM = iM;
	}
	
	public void randX()
	{
		Random ran = new Random();
		x = ran.nextInt(40) + 400;
	}
	
	public void tick(){
		 int zSpeed = 0;
			if(Game.wpm > 49 && Game.wpm < 60)
				zSpeed = 0;
			else if(Game.wpm >= 60 )
				zSpeed = -1;
			else if(Game.wpm < 50 && x < Player.getX() - 20)
				zSpeed = 1;
		x += zSpeed;
		if(x < -100){
			Game.zombies.remove(0);
		}
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

	
	public BufferedImage getSprite(){
		return iM.zombie.sprite;
	}

	public boolean isVisible() {
		return visible;
	}
}
