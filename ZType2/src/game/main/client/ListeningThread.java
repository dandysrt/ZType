package game.main.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import game.main.Game;
import game.main.graphics.PlayerProgressBar;

public class ListeningThread implements Runnable {
	private Game game;
	private DataInputStream input;
	private DataOutputStream output;
	
	public ListeningThread(Game game){
		this.game = game;
		this.input = game.getInputStream();
	}
	@Override
	public void run() {
		//client code
		while(true){
			try{
				String s = input.readUTF();
				String[] info = s.split(";");		
				if(info[1].compareTo("message") == 0){
					int player = Integer.parseInt(info[0]);
					String message = info[1];
					int score = Integer.parseInt(info[2]);
					int pCount = Integer.parseInt(info[3]);
					System.out.println("<" + game.getPlayerId() + ">" + "Player:" + player + "Score:" + score);
					game.updateScores(player, score);
					game.updatePlayerCount(pCount);
				}else{
					System.out.println("Test");
					game.renderFinal(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}	
}
