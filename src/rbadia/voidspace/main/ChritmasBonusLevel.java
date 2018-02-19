package rbadia.voidspace.main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.MovingPlatforms;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public class ChritmasBonusLevel extends RompeDiscotekasLevel4State{

	/**
	 * This is a bonus Christmas level. The asteroids are now little explosive presents so be careful.
	 * At least now you have a way to defend yourself... you get to fire snow balls!! Like in the last level,
	 * you get to fight a boss only this time, the boss is the Grinch trying to steal Christmas! 
	 * You can't let him! Be careful he fires homing Christmas trees!
	 */
	private static final long serialVersionUID = 3566554233803515836L;

	public ChritmasBonusLevel(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic,
			InputHandler inputHandler, GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		try {
			this.background=ImageIO.read(new File("images/christmasBackground.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override//Override to change all the graphics for the Christmas level.
	public void doStart() {	
		super.doStart();
		numPlatforms=7;
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		bonusLevel=true;
	}
	
	@Override//Override to change the music to Christmas music and draw bonus level
	public void doGettingReady(){
		setCurrentState(GETTING_READY);
		RompeDiscotekasLevelLogic levelLogic=(RompeDiscotekasLevelLogic) getGameLogic();
		levelLogic.drawBonusLevel();
		repaint();
		LevelLogic.delay(2000);
		MegaManMain.audioClip.close();
		MegaManMain.audioFile = new File("audio/christmasLevelMusic.wav");
		try {
			MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
			MegaManMain.audioClip.open(MegaManMain.audioStream);
			MegaManMain.audioClip.start();
			MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override//Override to change platforms
	public Platform[] newPlatforms(int n){
		platforms=new Platform[n];
		movingPlatforms=new MovingPlatforms[1];
		
		int x=0;
		int platformPosition=0;
		for(int i=0;i<n+1;i++) {
			if(i==0) {
				movingPlatforms[i]=new MovingPlatforms(0,0);
				movingPlatforms[i].setLocation(this.getWidth()-MovingPlatforms.WIDTH,this.getHeight()-MegaMan.HEIGHT*4);
				movingPlatforms[i].setDirection(1);
				movingPlatforms[i].setSpeed(MovingPlatforms.DEFAULT_SPEED);
			}
			else if(i<3) {
				platforms[platformPosition]=new Platform(0, 0);
				platforms[platformPosition].setLocation(0+(int)(getWidth()-platforms[platformPosition].getWidth())*(i-1),getHeight()-MegaMan.HEIGHT*2-10);
				platformPosition++;
			}
			
			else if(i>5) {
				this.platforms[platformPosition]=new Platform(0,0);
				platforms[platformPosition].setLocation(0+(int)(getWidth()-platforms[platformPosition].getWidth())*(i-6),0+MegaMan.HEIGHT*2);
				platformPosition++;
			}
			else {
				this.platforms[platformPosition]=new Platform(0,0);
				platforms[platformPosition].setLocation(125+100*x,0+MegaMan.HEIGHT*4);
				x++;
				platformPosition++;
			}
		}
		
		return platforms;
	}
	
}
