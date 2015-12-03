
package game.main;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import game.main.entities.Player;
import game.main.entities.Undead;
import game.main.entities.Zombie;
import game.main.KeyManager;
import game.main.client.ListeningThread;
import game.main.graphics.Background;
import game.main.graphics.ImageManager;
import game.main.graphics.PlayerProgressBar;
import game.main.graphics.Render;
import game.main.graphics.SpriteSheet;
import server.main.Server;

public class Game extends Canvas implements Runnable{
	
	
	

	private static final long serialVersionUID = 1L;
	public static int WIDTH, HEIGHT, SCALE, TILESIZE;
	public static boolean running = false;
	public static boolean pause = false;
	public static boolean complete = false;
	public static boolean endGame = false;
	
	public static int wordCount = 0;
	public static double wpm = 0;
	
	protected static int minutes = 0;
	//public static int playerCount = MenuScreen.playerCount; 
	public static int playerCount;
	
	protected BufferedImage playerSprite;
	protected BufferedImage undeadSprite;
	protected BufferedImage zombieSprite;
	protected static ImageManager eIM;
	public static ImageManager uIM;
	public static ImageManager zIM;
	
	protected static boolean playerStart = false;
	
	//player entity
	private static Player player;
	
	private static PlayerProgressBar p2;
	private static PlayerProgressBar p3;
	private static PlayerProgressBar p4;
	
	private static String drawnString = "";
	public static String updateString = "";
	public static int wordInc = 0;
	public static int charInc = 0;
	public static String[] wordArray;
	protected static int score = 0;
	public static int plyrSpeed = 100;
	protected static boolean render = true;
	
	public static ArrayList<Zombie> zombies;
	public static ArrayList<Undead> undead;
	
	private static ArrayList<Background> fground;
	private static ArrayList<Background> bground;
	
	private String foreg = "./imgRes/foreground.png";
	
	protected Thread lThread;
	
	private static String pangramList(int x){
		String[] pList = {
				"Ebenezer unexpectedly bagged two tranquil aardvarks with his jiffy vacuum cleaner.",
				"Six javelins thrown by the quick savages whizzed forty paces beyond the mark.",
				"The explorer was frozen in his big kayak just after making queer discoveries.",
				"The July sun caused a fragment of black pine wax to ooze on the velvet quilt.",
				"The public was amazed to view the quickness and dexterity of the juggler.",
				"While Suez sailors wax parquet decks, Afghan Jews vomit jauntily abaft.",
				"We quickly seized the black axle and just saved it from going past him.",
				"Six big juicy steaks sizzled in a pan as five workmen left the quarry.",
				"While making deep excavations we found some quaint bronze jewelry.",
				"Jaded zombies acted quaintly but kept driving their oxen forward.",
				"A mad boxer shot a quick, gloved jab to the jaw of his dizzy opponent.",
				"The job requires extra pluck and zeal from every young wage earner.",
				"A quart jar of oil mixed with zinc oxide makes a very bright paint.",
				"We promptly judged antique ivory buckles for the next prize.",
				"How razorback-jumping frogs can level six piqued gymnasts!",
				"Crazy Fredericka bought many very exquisite opal jewels.",
				"Sixty zippers were quickly picked from the woven jute bag.",
				"Amazingly few discotheques provide jukeboxes.",
				"Heavy boxes perform quick waltzes and jigs.",
				"Jinxed wizards pluck ivy from the big quilt.",
				"Big Fuji waves pitch enzymed kex liquor.",
				"The quick brown fox jumps over a lazy dog.",
				"Pack my box with five dozen liquor jugs.",
				"Jackdaws love my big sphinx of quartz.",
				"The five boxing wizards jump quickly.",
				"How quickly daft jumping zebras vex.",
				"Quick zephyrs blow, vexing daft Jim.",
				"Sphinx of black quartz, judge my vow.",
				"Waltz, nymph, for quick jigs vex Bud.",
				"Blowzy night-frumps vex'd Jack Q.",
				"Glum Schwartzkopf vex'd by NJ IQ."};
		return pList[x];
	}
	
	protected static BufferedImage background;
	private static BufferedImage ground;
	public static Font chillerFont;
	
	Random rand = new Random();
	
	//client variables start
	private DataInputStream input;
	private static DataOutputStream output;
	private Socket connection;

	private static int playerId;
	static protected int progress[];
	static protected int scores[];
	static protected int lastscores[];
	//end client variables

	public Thread gameThread;
	
	public Game(int WIDTH, int HEIGHT, int SCALE, int TILESIZE){
		Game.WIDTH = WIDTH;
		Game.HEIGHT = HEIGHT;
		Game.SCALE = SCALE;
		Game.TILESIZE = TILESIZE;
	}
	
	public void init(){
		
		//playerCount = MenuScreen.playerCount;
		playerCount = Server.getPlayerCount();
		
		//client code
		progress = new int[4];
		scores = new int[4];
		lastscores = new int[4];
		//end client code
		
		zombies = new ArrayList<Zombie>();
		undead = new ArrayList<Undead>();
	
		
		try {
			playerSprite = ImageIO.read(new File("./imgRes/RunSSwhiteshad.png"));
			undeadSprite = ImageIO.read(new File("./imgRes/zombieRisingSS.png"));
			zombieSprite = ImageIO.read(new File("./imgRes/zombieWalkSS.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		SpriteSheet pS = new SpriteSheet(playerSprite);
		eIM = new ImageManager(pS);
		SpriteSheet uS = new SpriteSheet(undeadSprite);
		uIM = new ImageManager(uS);
		SpriteSheet zS = new SpriteSheet(zombieSprite);
		zIM = new ImageManager(zS);
		
		player = new Player (600, 350, eIM);
		
		eIM.player.start();
		zIM.zombie.start();
		uIM.deadRise.start();
		
		try{
		background = ImageIO.read(new File("./imgRes/bground.jpg"));
		ground = ImageIO.read(new File("./imgRes/ground.png"));
		}catch(Exception e){}
		
		fground = new ArrayList<Background>();
		bground = new ArrayList<Background>();
		fground.add(new Background(0, 15, foreg));
		bground.add(new Background(3000, 15, foreg));
		
		//client code
		try {
		      connection = new Socket(InetAddress.getByName( "127.0.0.1" ), 5000 );
		      //connection = new Socket(InetAddress.getByName( "131.230.166.154" ), 5000 );
		      input = new DataInputStream(connection.getInputStream());
		      output = new DataOutputStream(connection.getOutputStream());
		      setPlayerId(input.readInt());
		      playerCount = input.readInt();
		}catch ( IOException e ) {
		     e.printStackTrace();         
		}
		ListeningThread listeningThread = new ListeningThread(this);
		lThread = new Thread(listeningThread);
		lThread.start();
		//end client code

		this.addKeyListener(new KeyManager());
		setPangram();
		p2 = new PlayerProgressBar();
		p3 = new PlayerProgressBar();
		p4 = new PlayerProgressBar();
		p3.adjustY(15);
		p4.adjustY(30);
		
		// New Font
		InputStream fontFile;
		try {
			fontFile = new BufferedInputStream(new FileInputStream("fontRes/CHILLER.TTF"));
			chillerFont = Font.createFont(Font.TRUETYPE_FONT,fontFile);
			chillerFont = chillerFont.deriveFont(Font.ITALIC, 45);
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
	
	public DataInputStream getInputStream(){
		return input;
	}
	
	public DataOutputStream getOutputStream(){
		return output;
	}
	
	//starts game thread
	public synchronized void start()
	{
		if (running)
		{
			return;
		}
		running = true;

		   gameThread = new Thread(this);
		   gameThread.start();
	}

	//stops game thread
	public synchronized void stop()
	{
		
		if (!running)
		{
			return;
		}
		running = false;
		try
		{
			gameThread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void pause(){
		if(pause){
			return;
		}
		
	}
	
	/**
	 * Increment or decrement score
	 * @param value set as negative to decrement, positive to increment
	 */
	public static void setScore(int value){
		score = score + value;
	}
	
	public void tick(){
		Render.updateBackground(fground);
		Render.updateBackground(bground);
		if(zombies != null)
			Render.updateZombies(zombies);
		if(undead != null)
			Render.updateUndead(undead);
		
	}
	
	public void render(){
		
		BufferStrategy buffStrat = this.getBufferStrategy();
		if(buffStrat == null){ 
			createBufferStrategy(2);
			return;
		}
		Graphics2D g = (Graphics2D) buffStrat.getDrawGraphics();
		
		
		
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		g.drawImage(background, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.setPaint(Color.gray);
		if(bground != null){
			Render.renderBackground(g, bground);
		}
		if (eIM.player != null)
		{
			eIM.player.update(System.currentTimeMillis());
			player.render(g);
		}
		if(zIM.zombie != null){
			zIM.zombie.update(System.currentTimeMillis());
			Render.renderZombies(g, zombies);
		}
		if(uIM.deadRise != null){
			uIM.deadRise.update(System.currentTimeMillis());
			Render.renderUndead(g, undead);
		}
		
		if(fground != null){
			Render.renderBackground(g, fground);
		}
		
		g.drawImage(ground, 0, 415, WIDTH * SCALE, HEIGHT, null);
		float alpha = (float) 0.5; //draw half transparent
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		g.setComposite(ac);
		g.fillRect(0, 10, WIDTH * SCALE, 80);
		g.setPaint(Color.red);
		g.setFont(chillerFont);
		
		g.drawString(drawnString, 80, 60);
		g.drawString(Integer.toString(score), 100, 150);
		
		g.dispose();
		g = (Graphics2D) buffStrat.getDrawGraphics();
		
		
		
		g.setFont(chillerFont);
		g.setPaint(Color.white);
		g.drawString(updateString, 80, 60);

		eIM.player.setSpeed(plyrSpeed);
		zIM.zombie.setSpeed(200);
		uIM.deadRise.setSpeed(400);
		
		
		updateProgress(playerId);
		Render.renderHealth(g);
		switch(playerCount){
		case 4:
			p4.renderProgressBar(g, Color.BLUE);
		case 3:
			p3.renderProgressBar(g, Color.GREEN);
		case 2:
			p2.renderProgressBar(g, Color.YELLOW);
			break;
		default:
			break;
		}
	
		g.dispose();
		buffStrat.show();
	}
	
	private static String randomPangram(){
		Random select = new Random();
		return pangramList(select.nextInt(31));
	}
	
	public static String[] splitPangram(){
		Scanner myScan = new Scanner(randomPangram());
		String[] tempArray = new String[20];
		int count = 0;
		while(myScan.hasNext()){
			tempArray[count] = myScan.next();
			count++;
		}
		wordArray = new String[count];
		for(int i = 0; i < count; i++){
			wordArray[i] = tempArray[i];
		}
		myScan.close();
		return wordArray;
	}
	
	private static String getPangram(){
		String[] tempArray = splitPangram();
		String pangram = "";
		for(int i = 0; i < tempArray.length; i++){
			if(i < 1)
				pangram = tempArray[i];
			else
				pangram = pangram + " " + tempArray[i];
		}
		return pangram;
	}
	
	public static void setPangram(){
		drawnString = getPangram();
		sendScore(false);
	}
	
	public static void reset(){
		setPangram();
		wordInc = 0;
		charInc = 0;
		score = 0;
		updateString = "";
		complete = false;
		playerStart = false;
		KeyManager.lastPangram = false;
	}

	public static void compareString(KeyEvent e){
		try{
			char[] tempArray = wordArray[wordInc].toCharArray();
			int size = wordArray[wordInc].toCharArray().length;
		
			char[] tempTyped = new char[size];
			for(int i = 0; i < tempTyped.length; i++)
				tempTyped[i] = ' ';
			if(charInc < tempArray.length){
				if(e.getKeyChar() == tempArray[charInc]){
					tempTyped[charInc] = tempArray[charInc];
					updateString += tempTyped[charInc];
					setScore(15);
					charInc++;
				
				}
				setScore(-5);
				//Render.updateHealth();
			}
		}catch(Exception err){
			reset();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER ||
				e.getKeyCode() == KeyEvent.VK_SHIFT){
			setScore(5);
		}
	}
	
	public static void speedUp(){
		if(plyrSpeed > 50){
			plyrSpeed -= 10;
			Background.adjustSpeed(.1);
		}
	}
	
	public static void speedDown(){
		if(plyrSpeed < 130){
			plyrSpeed += 15;
			Background.adjustSpeed(-.15);
		}
	}
	
	//client code START

	public void updateScores(int player,int score){
		scores[player] = score;
		
		for(int i = 0; i < playerCount; i++){
			if(scores[i] >= (lastscores[i]) + 100 && lastscores[i] <= 5000){
				lastscores[i] = scores[i];
				if(progress[i] < 5000)
					progress[i] = scores[i]/50;
				else
					progress[i] = 5000;
			}else if((scores[i] + 100) <= lastscores[i] && scores[i] > 0){
				lastscores[i] = scores[i];
				progress[i] = scores[i]/50;
			}
		}
	}
	
	public void updateProgress(int player){
		switch(player){
		case 0:
			p2.adjustW(progress[1]);
			p3.adjustW(progress[2]);
			p4.adjustW(progress[3]);
			break;
		case 1:
			p2.adjustW(progress[0]);
			p3.adjustW(progress[2]);
			p4.adjustW(progress[3]);
			break;
		case 2:
			p2.adjustW(progress[1]);
			p3.adjustW(progress[0]);
			p4.adjustW(progress[3]);
			break;
		case 3:
			p2.adjustW(progress[1]);
			p3.adjustW(progress[2]);
			p4.adjustW(progress[0]);
			break;
		default:
			p2.adjustW(progress[1]);
			p3.adjustW(progress[2]);
			p4.adjustW(progress[3]);
			break;
		}
	}
	
	public void updatePlayerCount(int pCount) {
		playerCount = pCount;
	}

public static void sendScore(boolean done){		
		
		try{
			if(!done){
				output.writeUTF(getPlayerId() + ";" + "message" + ";" +  score);
			}else{
				output.writeUTF(getPlayerId() + ";" + "done" + ";" +  score);
			}
		}catch(IOException e){
			
		}
	}
	
	//END client code
	
	@Override
	public void run() {

		init();
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60D;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double phi = 0;
		running = true;
		int timer = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(score != 0 && undead.size() < 1 && zombies.size() < 3){
				if((timer + (Math.random()*150)) > 350){
					undead.add(new Undead());
					timer = 0;
				}
			}
			if(delta >= 1){
				tick();
				if(render)
					render();
				timer++;
				delta--;
				if(playerStart){
					phi+= 0.00025;
				}
			}
			wpm = wordCount /phi;
			if(pause){
				pause();
			}
			if(complete){
				
				zombies.clear();
				undead.clear();
				//break;
				complete = false;
			}
			
		}
		phi = 0.0;
		wordCount = 0;
		
	}
	
	public static void renderFinal(String message){
		int wordsPM = (int) wpm;
		String[] s = message.split(";");
		GameManager.doneScreen.setFont(chillerFont);
		GameManager.doneScreen.append("You got " + s[0] + "\n");
		GameManager.doneScreen.append("Score: "+score+"\nW.P.M: "+wordsPM/*+""<--Winner*/);

		GameManager.donePane.setVisible(true);
	}

	public static int getPlayerId() {
		return playerId;
	}

	public static void setPlayerId(int playerId) {
		Game.playerId = playerId;
	}

}
