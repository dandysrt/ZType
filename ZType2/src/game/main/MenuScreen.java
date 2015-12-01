package game.main;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuScreen extends Canvas implements Runnable{

	public static int WIDTH, HEIGHT, SCALE, TILESIZE;
	public static boolean running = false;
	public static boolean pause = false;
	public static boolean endGame = false;
	public static int playerCount = 0;
	Font chillerFont;
	public static BufferedImage background;

	protected static String drawnString = "Z-Type";
	public Thread menuThread;
	
	public MenuScreen(int WIDTH, int HEIGHT, int SCALE, int TILESIZE){
		MenuScreen.WIDTH = WIDTH;
		MenuScreen.HEIGHT = HEIGHT;
		MenuScreen.SCALE = SCALE;
		MenuScreen.TILESIZE = TILESIZE;
	}
	
	public void init(){
		
		try{
			background = ImageIO.read(new File("./imgRes/bground.jpg"));
		}catch(Exception e){}

		// New Font
		InputStream fontFile;
		try {
			fontFile = new BufferedInputStream(new FileInputStream("fontRes/CHILLER.TTF"));
			chillerFont = Font.createFont(Font.TRUETYPE_FONT,fontFile);
			chillerFont = chillerFont.deriveFont(Font.ITALIC, 100);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(chillerFont);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		// End New Font
			
	}
	public synchronized void start(){
		if(running){
			return;
		}
		running = true;
		menuThread = new Thread(this);
		menuThread.start();
	}
	public synchronized void stop()
	{
		if (!running)
		{
			return;
		}
		running = false;
		try
		{
			menuThread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	public synchronized void pause(){
		if(!pause){
			menuThread.notify();
			return;
		}
		try{
			menuThread.sleep(60000000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void render(){
		BufferStrategy buffStrat = this.getBufferStrategy();
		if(buffStrat == null){
			createBufferStrategy(2);
			return;
		}
		Graphics2D g = (Graphics2D) buffStrat.getDrawGraphics();
		
		g.fillRect(0, 0, WIDTH * SCALE,  HEIGHT * SCALE);
		g.drawImage(background,  0,  0,  WIDTH * SCALE,  HEIGHT * SCALE, null);
		g.setPaint(Color.red);
		g.setFont(chillerFont);
		g.drawString(drawnString, 500, 200);
		
		g.dispose();
		buffStrat.show();
	}

	@Override
	public void run() {
		init();
		while(running){
			render();
		}
		
	}
	

}
