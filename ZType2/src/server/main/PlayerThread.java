package server.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerThread extends Thread{	
	int playerId;
	protected int status = 0;

	protected Server server;
	protected DataInputStream input;
	protected DataOutputStream output;
	
	public PlayerThread(int playerId, DataInputStream input,DataOutputStream output,Server server){
		this.input = input;
		this.output = output;
		this.server = server;
		this.playerId = playerId;
	}
	
	public void run() {
		while(true){
			try{
				server.printMessage("Player " + playerId + " is listening...");
				String message = input.readUTF();
				server.sendPlayerData(playerId,message);
				server.printMessage(message);
			}catch(IOException e){
				output = null;
				input = null;
				status = 1;
				server.updatePlayerCount(-1);
			}
		}
		
	}
	
}