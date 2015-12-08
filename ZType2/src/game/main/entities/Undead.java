package game.main.entities;

import game.main.Game;
import game.main.graphics.ImageManager;
import game.main.graphics.Render;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Undead {
	private int x, y = 355;
	private ImageManager iM = Game.uIM;
	
	protected boolean visible = true;
	
	public Undead(){
		randX();
	}
	
	public void randX()
	{
		Random ran = new Random();
		x = ran.nextInt(40) + 400;
	}
	
	public void tick(){
		if(iM.deadRise.fullRun){
			Game.zombies.add(new Zombie(Game.zIM, x));
			iM.deadRise.fullRun = false;
			Game.undead.remove(0);
		}
		x -= 1;
		if(x < -100){
			Game.undead.remove(0);
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
		return iM.deadRise.sprite;
	}

	public boolean isVisible() {
		return visible;
	}
}
