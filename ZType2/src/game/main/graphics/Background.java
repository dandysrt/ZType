package game.main.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.Game;
import game.main.graphics.ImageLoader;

public class Background 
{
	private int x, y;
	private final int width = 2460;
	private final int height = 600;
	ImageLoader loader = new ImageLoader();
	BufferedImage bgImage;
	
	public boolean up = false, dn = false, lt = true, rt = false;
	
	public static double speed = 2;
	
	Graphics g;
	
	public Background(int newx, int newy, String graphic)
	{
		try {
			bgImage = ImageIO.read(new File(graphic));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x = newx;
		y = newy;
	}
	
	public void setBackground(BufferedImage bg){
		bgImage = bg;
	}
	
	public void tick()
	{
		
		if(up)
		{
			y -= speed;
		}
		if(dn)
		{
			y += speed;
		}
		if(lt)
		{
			x -= speed;
		}
		if(rt)
		{
			x += speed;
		}
		
		
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	public int getW()
	{
		return width;
	}
	public int getH()
	{
		return height;
	}
	
	public void setX(int otherx)
	{
		x = otherx;
	}
	public BufferedImage getImage()
	{
		return bgImage;
	}
	
	public static void adjustSpeed(double value){
		speed = speed + value;
	}
}