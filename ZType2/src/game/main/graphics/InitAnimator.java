package game.main.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class InitAnimator {
	private ArrayList<BufferedImage> frames;

	public BufferedImage sprite;
	public static boolean fullRun = false;
	private volatile boolean running = false;
	private long prevTime, speed;
	private int frameAtPause, currentFrame = 0;

	public InitAnimator(ArrayList<BufferedImage> frames)
	{
		this.frames = frames;
		currentFrame = 0;
		fullRun = false;
	}

	public void setSpeed(long speed)
	{
		this.speed = speed;
	}

	public void update(long time)
	{
		if (running)
		{
			if (time - prevTime >= speed)// update animation
			{
				currentFrame++;
				try
				{
					sprite = frames.get(currentFrame);
				} catch (IndexOutOfBoundsException e)
				{
					fullRun = true;
					currentFrame = -1;
				}
				prevTime = time;
			}
		}
	}

	public void start()
	{
		running = true;
		prevTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}

	public void stop()
	{
		running = false;
		prevTime = 0;
		frameAtPause = 0;
		currentFrame = 0;
	}

	public void pause()
	{
		frameAtPause = currentFrame;
		running = false;
	}

	public void resume()
	{
		currentFrame = frameAtPause;
		running = true;
	}
}
