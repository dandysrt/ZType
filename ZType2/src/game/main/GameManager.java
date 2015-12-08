package game.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class GameManager extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 600, HEIGHT = 300, SCALE = 2, TILESIZE = 100;
	
	protected JPanel contentPane;
	public static JTextArea doneScreen;
	protected static JPanel gamePane;
	protected static JPanel menuPane;
	public static JPanel donePane;
	protected static Game game;
	protected static MenuScreen menu;
	private JButton playButton;
	private JButton restartButton;
	protected static JButton reconnectButton;
	private BufferedImage play;
	private BufferedImage mousedOver;
	private boolean start = true;
	
	public static Timer timer;

	public GameManager(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH * SCALE, HEIGHT * SCALE);
		setResizable(false);
		setLocationRelativeTo(null);
		requestFocus(true);
		
		contentPane = new JPanel();
		contentPane.setLayout(new CardLayout());	
		setContentPane(contentPane);
		
		gamePane = new JPanel();
		contentPane.add(gamePane);
		gamePane.setVisible(false);
		gamePane.setLayout(null);
		gamePane.setBounds(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		menuPane = new JPanel();
		contentPane.add(menuPane);
		menuPane.setVisible(true);
		menuPane.setLayout(null);
		menuPane.setBounds(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		try{
		play = ImageIO.read(new File("./imgRes/playButton.png"));
		mousedOver = ImageIO.read(new File("./imgRes/mouseOver.png"));
		}catch(IOException e){
		}
		playButton = new JButton("Play");
		playButton.setIcon(new ImageIcon(play));
		menuPane.add(playButton);
		playButton.setVisible(true);
		playButton.setBorderPainted(false);
		playButton.setBounds(505, 300, 190, 50);
		playButton.addActionListener(this);
		playButton.addMouseListener(new MouseAdapter(){
			
			public void mouseEntered(MouseEvent arg0){
				playButton.setIcon(new ImageIcon(mousedOver));
			}
			
			public void mouseExited(MouseEvent arg0){

				playButton.setIcon(new ImageIcon(play));
			}
		});
		
		reconnectButton = new JButton("Multiplayer");
		reconnectButton.setBounds(1050, 500, 125, 50);
		menuPane.add(reconnectButton);
		reconnectButton.addActionListener(this);
		reconnectButton.setVisible(true);
		
				
		
		donePane = new JPanel();
		gamePane.add(donePane);
		donePane.setVisible(false);
		donePane.setLayout(null);
		donePane.setBounds(450,200,300,200);
		donePane.setBackground(Color.BLACK);
		doneScreen = new JTextArea();
		donePane.add(doneScreen);
		doneScreen.setBounds(0, 0, 300, 149);
		doneScreen.setBackground(Color.BLACK);
		doneScreen.setSelectedTextColor(Color.RED);
		doneScreen.setEditable(false);
		
		restartButton = new JButton("RESTART");
		donePane.add(restartButton);
		restartButton.setBounds(100,150,100,30);
		restartButton.addActionListener(this);
		
		timer = new Timer(5, this);
		timer.start();
		
	}
	

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		Object action = evt.getSource();
		
		if(start){
			timer.stop();
			menu = new MenuScreen(WIDTH, HEIGHT, SCALE, TILESIZE);
			menu.setPreferredSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
			menu.setMaximumSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
			menu.setMinimumSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
			menu.setBounds(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			menuPane.add(menu);
			menu.start();
			start = false;
		}
		
		if(action == playButton){
			gamePane.setVisible(true);
			donePane.setVisible(false);
			menuPane.setVisible(false);
			menu.stop();
			game = new Game(WIDTH, HEIGHT, SCALE, TILESIZE);
			game.setPreferredSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
			game.setMaximumSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
			game.setMinimumSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
			game.setBounds(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			gamePane.add(game);
			gamePane.requestFocus(true);
			game.start();
		}
		
		if(action == restartButton){
			game.stop();
			menuPane.setVisible(true);
			donePane.setVisible(false);
			gamePane.setVisible(false);
			menu.start();
			Game.reset();
			gamePane.remove(game);
		}
		
		if(action == reconnectButton){
			Game.multiplayer = true;
		}
	}
	
	public static void main(String[] args){
		GameManager gM = new GameManager();
		@SuppressWarnings("unused")
		Thread managerThread = new Thread(game);
		gM.setVisible(true);
		
	}
}
