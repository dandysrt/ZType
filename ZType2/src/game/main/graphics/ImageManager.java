package game.main.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageManager 
{
	public BufferedImage grass, razorL, razorM, razorR, rHandle, invis, bldg1, bldg2, bldg3;
	ImageLoader loader = new ImageLoader();
	
	
	public ArrayList<BufferedImage> plyr = new ArrayList<BufferedImage>();
	public Animator player = new Animator(plyr);
	public ArrayList<BufferedImage> undead = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> zomB = new ArrayList<BufferedImage>();
	public Animator zombie = new Animator(zomB);
	
	public int eW = 55, eH = 65;
	
	public ImageManager(SpriteSheet ss)
	{
		//Creates buffered image objects based off sprite sheet coordinates
		plyr.add(ss.cropPlayer(0, 0, eW, eH));
		plyr.add(ss.cropPlayer(1, 0, eW, eH));
		plyr.add(ss.cropPlayer(2, 0, eW, eH));
		plyr.add(ss.cropPlayer(3, 0, eW, eH));
		plyr.add(ss.cropPlayer(4, 0, eW, eH));
		plyr.add(ss.cropPlayer(5, 0, eW, eH));
		plyr.add(ss.cropPlayer(0, 1, eW, eH));
		plyr.add(ss.cropPlayer(1, 1, eW, eH));
		plyr.add(ss.cropPlayer(2, 1, eW, eH));
		plyr.add(ss.cropPlayer(3, 1, eW, eH));
		plyr.add(ss.cropPlayer(4, 1, eW, eH));
		plyr.add(ss.cropPlayer(5, 1, eW, eH));
		
		undead.add(ss.cropPlayer(0, 0, eW, eH));
		undead.add(ss.cropPlayer(1, 0, eW, eH));
		undead.add(ss.cropPlayer(2, 0, eW, eH));
		undead.add(ss.cropPlayer(3, 0, eW, eH));
		undead.add(ss.cropPlayer(4, 0, eW, eH));
		undead.add(ss.cropPlayer(5, 0, eW, eH));
		undead.add(ss.cropPlayer(0, 1, eW, eH));
		undead.add(ss.cropPlayer(1, 1, eW, eH));
		
		zomB.add(ss.cropPlayer(0, 0, eW, eH));
		zomB.add(ss.cropPlayer(1, 0, eW, eH));
		zomB.add(ss.cropPlayer(2, 0, eW, eH));
		zomB.add(ss.cropPlayer(3, 0, eW, eH));
		zomB.add(ss.cropPlayer(4, 0, eW, eH));
		zomB.add(ss.cropPlayer(5, 0, eW, eH));
		zomB.add(ss.cropPlayer(0, 1, eW, eH));
		zomB.add(ss.cropPlayer(1, 1, eW, eH));
		
	}
	
}
