package server.main;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.xml.ws.Holder;

public class Server extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private ServerSocket serverSocket;
	private Socket socket;
	
	private PlayerThread players[];
	
	private JTextArea screen;
	private JScrollPane scrollPane;
	private JScrollBar scrollBar;
	
	private DataInputStream input;
	private DataOutputStream output;
	
	final static int PLAYER_LIMIT = 4;
	private static int playerCount = 0;
	
	protected ArrayList<String> scores = new ArrayList<>();
	
	
	Server(){
		
		players = new PlayerThread[PLAYER_LIMIT];
		
		// Create Text Area
		screen = new JTextArea();
		
		// Add Scroll Bar
		scrollPane = new JScrollPane(screen, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar = new JScrollBar();
		scrollPane.add(scrollBar);
		
		// Add Text Area to JFrame
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(300, 100);
		
		// Server is Starting
		screen.append("Starting Server");
		
		// Initialize Socket
		init();
		
		// Server has started and JFrame Visible
		screen.append("\nServer Started");
		setVisible(true);
		
	}
	
	private void init(){
		try{
			serverSocket = new ServerSocket(5000);
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void run(){
		while(true){
			try{
				socket = serverSocket.accept();
				if(playerCount < PLAYER_LIMIT){
					playerCount++;
					printMessage("Client Connected.");
					printMessage("Number of players: " + playerCount);
					output = new DataOutputStream(socket.getOutputStream());
					input = new DataInputStream(socket.getInputStream()); // Not needed in current implementation because server does not issue commands
					for(int i = 0; i < players.length; i++){
						if(players[i] == null || players[i].status == 1){
							players[i] = new  PlayerThread(i,input,output,this);
							players[i].start();
							output.writeInt(i); // Sends id to player based on array location
							output.writeInt(playerCount);
							break;
						}
					}
				}else{
					socket.close();
				}
			}catch(IOException e){}
		}
	}
	
	public void printMessage(String str){
		screen.append("\n"+str);
	}
	
	public void sendPlayerData(int player,String message){
		
		try{
			synchronized(players){
				for(int i = 0; i < PLAYER_LIMIT; i++){
					if(players[i]!= null && i != player){
						players[i].output.writeUTF(message + ";" + playerCount);
					}
				}
			}
		}catch(IOException e){
			
		}
	}
	
	public static int getPlayerCount(){
		return playerCount;
	}
	
	public static void updatePlayerCount(int n){
		playerCount = playerCount + n;
	}
	
	public void sendFinal(){
		for(int i = 0; i < scores.size();i++){
			String[] s = scores.get(i).split(";");
			sendPlayerData(Integer.parseInt(s[0]), scores.get(i));
		}
		sendPlayerData(5, "5;exit;5");
	}
	
//	public void sort(){
//		for(String score : scores){
//			String[] info = score.split(";");
//			for(String score2 : scores){
//				String[] info2 = score.split(";");	
//				if(Integer.parseInt(info[2]) < Integer.parseInt(info2[2])){
//					
//				}
//			}
//		}
//	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		server.run();
	}

}