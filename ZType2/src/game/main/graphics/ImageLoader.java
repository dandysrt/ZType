package game.main.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader 
{
	//finds the specific sprite sheet to load
	public BufferedImage load(String path)
	{
		//returns an error if sprite sheet resource is not available
		try {
			return ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
