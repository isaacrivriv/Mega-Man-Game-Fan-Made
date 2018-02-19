package rbadia.voidspace.main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;


public class RompeDiscotekasLevel2State extends RompeDiscotekasLevel1State{

	/**
	 * Changed extension so that second level has same methods as first level. Only reused the platforms.
	 */
	private static final long serialVersionUID = -6622603072526734577L;


	public RompeDiscotekasLevel2State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic,
			InputHandler inputHandler, GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		try {
			this.background=ImageIO.read(new File("images/newBack2.jpg"));//Changed background
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
	}
	

	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<4)	platforms[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
			if(i==4) platforms[i].setLocation(50 +i*50, getHeight()/2 + 140 - 3*40);
			if(i>4){	
				int k=4;
				platforms[i].setLocation(50 + i*50, getHeight()/2 + 20 + (i-k)*40 );
				k=k+2;
			}
		}
		return platforms;
	}

}
