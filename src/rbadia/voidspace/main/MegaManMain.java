package rbadia.voidspace.main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.RompeDiscotekasGraphicsManager;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game class. Starts the game.
 */
public class MegaManMain {

	//Starts playing menu music as soon as the game frame is created

	public static AudioInputStream audioStream;
	public static Clip audioClip;
	public static File audioFile;
	public static int levelsPlayed;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException, IOException  {


		MainFrame frame = new MainFrame();              		// Main Game Window
		GameStatus gameStatus = new GameStatus();       		// Records overall status of game across all levels
		LevelLogic gameLogic = new RompeDiscotekasLevelLogic();        		// Coordinates among various levels
		InputHandler inputHandler = new InputHandler(); 		// Keyboard listener
		GraphicsManager graphicsMan = new RompeDiscotekasGraphicsManager(); // Draws all graphics for game objects
		SoundManager soundMan = new SoundManager();			// Loads and plays all sounds during the game
		
		
		
		frame.addKeyListener(inputHandler);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int playAgain = 2;
		while(playAgain != 1) {
			levelsPlayed=0;
			gameStatus.setAsteroidsDestroyed(0);
			gameStatus.setLivesLeft(5);
			
			audioFile = new File("audio/menuScreen.wav");
			try {
				audioStream = AudioSystem.getAudioInputStream(audioFile);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			
			LevelState level1State = new RompeDiscotekasLevel1State(1, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			LevelState level2State = new RompeDiscotekasLevel2State(2, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			LevelState level4State=new RompeDiscotekasLevel4State(4, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			LevelState christmasLevel=new ChritmasBonusLevel(5, frame, gameStatus, gameLogic, inputHandler, graphicsMan, soundMan);
			LevelState levels[] = { level1State, level2State, level4State ,christmasLevel};

			String outcome = "CONGRATS!! YOU WON!!";
			for (LevelState nextLevel : levels) {
				
				System.out.println("Next Level Started");
				frame.setLevelState(nextLevel);
				gameLogic.setLevelState(nextLevel);
				inputHandler.setLevelState(nextLevel);
				gameStatus.setLevel(nextLevel.getLevel());
				
				frame.setVisible(true);  // TODO verify whether this is necessary
				if(levelsPlayed==0)//So that the music only starts once and doesn't overlap when starting another game
					startInitialMusic();
				levelsPlayed++;
				// init main game loop
				Thread nextLevelLoop = new Thread(new LevelLoop(nextLevel));
				nextLevelLoop.start();
				nextLevelLoop.join();
				
				System.out.println("Levels played: "+levelsPlayed);
				
				if (nextLevel.getGameStatus().isGameOver()) {
					outcome = "SORRY YOU LOST";
					break;
				}

			}
			playAgain = JOptionPane.showConfirmDialog(null, outcome + " ... Play Again?", "", JOptionPane.YES_NO_OPTION);
			audioClip.stop();
		}
		System.exit(0);
	}
	
	public static void startInitialMusic() throws InterruptedException, IOException {
		// Music 
		// allows music to be played while playing

		AudioFormat format = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);

		try {
			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.start();
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
