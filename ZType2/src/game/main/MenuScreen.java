package game.main;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuScreen extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	public static int WIDTH, HEIGHT, SCALE, TILESIZE;
	public static boolean running = false;
	public static boolean pause = false;
	public static boolean endGame = false;
	public static int playerCount = 0;

	private JPanel menuPane;
	
	protected static BufferedImage background;
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
		menuPane = new JPanel();
		menuPane.setLayout(new GridLayout(1,1));
		menuPane.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		menuPane.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		menuPane.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		final JButton playButton = new JButton("Play");
		menuPane.add(playButton);
		playButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameManager.contentPane.setLayout(new CardLayout());
				GameManager.menu.setVisible(false);
				GameManager.gamePane.add(GameManager.game);		
				GameManager.gamePane.add(GameManager.typePane);
				GameManager.gamePane.setVisible(true);
				GameManager.game.start();
			}
			
		});
		
			
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
		g.setFont(new Font("Chiller", Font.ITALIC, 100));
		
		g.drawString(drawnString, 500, 200);
		
		
		
		g.dispose();
		buffStrat.show();
	}
	
	public synchronized void start(){
		if(running){
			return;
		}
		running = true;
		menuThread = new Thread(this);
		menuThread.start();
	}
	
	@Override
	public void run() {
		init();
		while(running){
			render();
		}
	}

}
