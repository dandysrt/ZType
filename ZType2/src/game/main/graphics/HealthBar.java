package game.main.graphics;

public class HealthBar
{
	
	private static int x = 500, y = 100;
	private static int w = 100, h = 20;
	
	public HealthBar(){
		
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
	
	public static void updateW(int val){
		w += val;
	}

}
