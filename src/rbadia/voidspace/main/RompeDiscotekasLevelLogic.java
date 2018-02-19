package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;

public class RompeDiscotekasLevelLogic extends LevelLogic{
	
	@Override//Override to add new key functions
	public void handleKeysDuringPlay(InputHandler ih, LevelState levelState) {
		if(ih.isMPressed()) {//Mute music
			ih.reset();
			if(!InputHandler.isMute()) {
				InputHandler.setMute(true);
				MegaManMain.audioClip.stop();
			}
			else {
				InputHandler.setMute(false);
				MegaManMain.audioClip.start();
			}
		}
		if(ih.isNPressed()) {//Advance to NewLevel
			RompeDiscotekasLevel1State level=(RompeDiscotekasLevel1State) levelState;
			level.setLevelPass(true);
		}
		
		if(ih.isPPressed()) {//Pause the game
			ih.reset();
			if(!InputHandler.isPause()) {
				InputHandler.setPause(true);
			}
			else
				InputHandler.setPause(false);
		}
		
		super.handleKeysDuringPlay(ih,levelState);
	}
	
	@Override//Override to add new key functions and fix start messages reappearing forever.
	public void handleKeysDuringInitialScreen(InputHandler ih, LevelState levelState) {
		
		if(ih.isSPressed()) {
			ih.reset();
			JOptionPane.showMessageDialog( null, 
					"Item:                Price\r\n"+
							"\r\n"+
							"Extra Life:      1500\r\n"+ 
							"Power Shot:  500\r\n"+
					"\r\n");
			return;
		}
		
		if(ih.isIPressed()) {
			ih.reset();
			JOptionPane.showMessageDialog( null, 
					"Power Up:     Explanation\r\n"+
							"\r\n"+
							"Extra Life:      Gives an extra life (One Extra Life per second)\r\n"+ 
							"                           (Press E to buy, limit of one life per second.)\r\n" +
							"Power Shot:  Activates the Power Shot which kills the asteroid in one hit\r\n"+
					"                       (Also helps towards boss destruction)\r\n"+
					"                           (Press Q to buy and fire it.)\r\n");
			return;
		}
		
		if(ih.isMPressed()) {//Mute music
			ih.reset();
			if(!InputHandler.isMute()) {
				InputHandler.setMute(true);
				MegaManMain.audioClip.stop();
			}
			else {
				InputHandler.setMute(false);
				MegaManMain.audioClip.start();
			}
		}
		super.handleKeysDuringInitialScreen(ih, levelState);
	}
	
	//New method to draw a bonus level string on the screen for Christmas level.
	public void drawBonusLevel() {
		LevelState levelState = getLevelState();
		Graphics2D g2d = levelState.getGraphics2D();

		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		String readyStr = "!!!Bonus Level!!!"; 
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (levelState.getWidth() - strWidth)/2;
		int strY = (levelState.getHeight() + ascent)/2;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(readyStr, strX, strY);
	}

}
