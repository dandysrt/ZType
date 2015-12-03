package game.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyManager implements KeyListener {
	private int count = 0;
	public static boolean lastPangram = false;
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(lastPangram){
				Game.sendScore(true);
				Game.complete = true;
				
				return;
			}
			Game.setPangram();
			Game.wordInc = 0;
			Game.charInc = 0;
			Game.updateString = "";
			Game.wordCount++;
			//System.out.println((int) Game.wpm); 
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			
				if(Game.wordInc < Game.wordArray.length){
					if(Game.charInc < Game.wordArray[Game.wordInc].toCharArray().length){
						return;
					}
					Game.wordInc++;
					Game.charInc = 0;
					Game.updateString += " ";
					count++;
					if(count < 3){
						Game.speedUp();
						count = 0;
					}
				}
				
				Game.wordCount++;
		}
		Game.playerStart = true;
		Game.compareString(e);
		if(Game.score >= 100)
			lastPangram = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent evt) {}

}
