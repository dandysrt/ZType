package game.main.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

//import game.main.Game;
import game.main.entities.Zombie;

public class Render 
{
	public static void renderBackground(Graphics2D g, ArrayList<Background> bgList)
	{
		for (int i = 0; i < bgList.size(); i++) 
		{
			Background bg = (Background) bgList.get(i);
			g.drawImage(bg.getImage() , bg.getX(), bg.getY(), bg.getW(),
					bg.getH(), null);
		}
	}
	
	public static void updateBackground(ArrayList<Background> bgList)
	{
		for (int i = 0; i < bgList.size(); i++) 
		{
			Background bg = (Background) bgList.get(i);
			bg.tick();
			if(bg.getX() == - bg.getW())
				bg.setX(bg.getW());
		}
	}
	
	public static void renderHealth(Graphics2D g){
		
		HealthBar hb = new HealthBar();
		g.setColor(Color.RED);
		g.fillRect(hb.getX(), hb.getY(), hb.getW(), hb.getH());
		g.setColor(Color.WHITE);
		g.drawRect(hb.getX(), hb.getY(), 100, 20);
	}
	
	public static void updateHealth(){
		if(HealthBar.getW() > 0)
			HealthBar.updateW(-5);
	}
	
	public static void renderZombies(Graphics2D g, ArrayList<Zombie> zList){
		for(int i = 0; i < zList.size(); i++){
			Zombie zombies = zList.get(i);
			g.drawImage(zombies.getSprite(), zombies.getX(), zombies.getY(), 65, 65, null);

		}
	}
	
	public static void updateZombies(ArrayList<Zombie> zList){
		for(int i = 0; i < zList.size(); i++){
			Zombie zombies = zList.get(i);
			if(zombies.isVisible())
				zombies.tick();
		}
	}
	

}
