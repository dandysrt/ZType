package game.main.graphics;

import game.main.Game;
import game.main.GameManager;

public class HealthBar
{
	
	private static int x = 500, y = 100;
	private static int w = 100, h = 20;
	private static int count = 0;
	
	public HealthBar(){
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public static int getW(){
		return w;
	}
	
	public int getH(){
		return h;
	}
	
	public static void updateW(int val){
		count++;
		if(count > 500){
			w += val;
			count = 0;
		}
		if(w <= 0){
			
			Game.zombies.clear();
			Game.reset();
			GameManager.doneScreen.setFont(Game.chillerFont);
			GameManager.doneScreen.append("    You Died. \n    Type faster!");
			GameManager.donePane.setVisible(true);
			
		}
	}

}
