package game.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameManager extends JFrame{

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 600, HEIGHT = 300, SCALE = 2, TILESIZE = 100;
	
	protected static JPanel contentPane;
	protected static JPanel gamePane;
	protected static JPanel typePane;
	private JTextField typed;
	protected static Game game;
	protected static MenuScreen menu;

	public GameManager(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH * SCALE, HEIGHT * SCALE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		typed = new JTextField();
		//typed.setSize(WIDTH * SCALE, 80);
		contentPane = new JPanel();
		//contentPane.setLayout(new CardLayout());
		//contentPane.setLayout(new CardLayout(400,250));
		contentPane.setLayout(new BorderLayout());
		typePane = new JPanel();
		typePane.setLayout(new GridLayout(3,1));
		typePane.add(Box.createHorizontalStrut(80));
		typePane.add(Box.createHorizontalStrut(80));
		typePane.add(typed);
		typePane.setVisible(true);
		
		setContentPane(contentPane);
		gamePane = new JPanel();
		gamePane.setLayout(null);
		gamePane.setVisible(false);
		gamePane.setVisible(true);
		contentPane.add(gamePane);
		contentPane.setVisible(true);

		this.addMouseListener(new MouseManager());
		
		/*menu = new MenuScreen(WIDTH, HEIGHT, SCALE, TILESIZE);
		menu.setPreferredSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		menu.setMaximumSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		menu.setMinimumSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		menu.setBounds(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		//menu.start();
		//menu.revalidate();
		//menu.repaint();
		//gamePane.add(menu);
	
		//contentPane.add(menu, BorderLayout.NORTH);
*/		
		game = new Game(WIDTH, HEIGHT, SCALE, TILESIZE);
		game.setPreferredSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension (WIDTH * SCALE, HEIGHT * SCALE));
		game.setBounds(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		gamePane.add(game);
		gamePane.add(typePane);
		game.start();
	}
	
	public static void main(String[] args){
		GameManager gM = new GameManager();
		@SuppressWarnings("unused")
		//Thread managerThread = new Thread(menu);
		Thread managerThread = new Thread(game);
		gM.setVisible(true);
		
	}
}
