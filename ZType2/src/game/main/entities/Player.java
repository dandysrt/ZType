package game.main.entities;

import java.awt.Graphics2D;

import game.main.Game;
import game.main.graphics.ImageManager;

public class Player {
	private static int x, y;
	private ImageManager iM;
	
	public static boolean start = false;
	
	int scale = Game.SCALE;
	
	public Player(int x, int y, ImageManager iM){
		this.x = x;
		this.y = y;
		this.iM = iM;
	}
	
	public static int getX(){
		return x;
	}
	
	public void tick(){
	}
	
	public void render(Graphics2D g){
		g.drawImage(iM.player.sprite, x, y, 55,
				65, null);
	}
	
}
