package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.RompeDiscotekasGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigAsteroid;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public class RompeDiscotekasLevel3State extends RompeDiscotekasLevel2State {
	
	protected Asteroid asteroid2;
	private int asteroidDirection=0;
	private RompeDiscotekasGraphicsManager rompeDiscotekasGraphicsManager;
	
	
	//Getters
	public int getAsteroidDirection() 						{ return asteroidDirection; }
	public RompeDiscotekasGraphicsManager getRompeDiscotekasGraphicsManager() { return rompeDiscotekasGraphicsManager; }
	
	//Setters
	public void setAsteroidDirection(int asteroidDirection) { this.asteroidDirection = asteroidDirection; }
	
	public RompeDiscotekasLevel3State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		GameStatus status = this.getGameStatus();
		status.setNewAsteroid2(false);
		newAsteroid2(this);
	}
	
	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		
		if((asteroid.getX() + asteroid.getPixelsWide() >  0) 
		&& (asteroid2.getX() + asteroid2.getPixelsWide() > 0)) 
		{
			//First Asteroid
			asteroid.translate(-asteroid.getSpeed(), getAsteroidDirection());
			setAsteroidDirection(getAsteroidDirection() + 1);
			if(getAsteroidDirection()==15)
			{
				setAsteroidDirection(-getAsteroidDirection());
			}
			
			//Second Asteroid
			asteroid2.translate(-asteroid2.getSpeed(), 0);
			
			//Draws Asteroids on Screen
			getGraphicsManager().drawAsteroid(asteroid, g2d, this);	
			getGraphicsManager().drawAsteroid(asteroid2, g2d, this);		
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				asteroid.setLocation(this.getWidth() - asteroid.getPixelsWide(),
						rand.nextInt(this.getHeight() - asteroid.getPixelsTall() - 32));
				asteroid2.setLocation((int) (this.getWidth() - asteroid2.getPixelsWide()),
						(rand.nextInt((int) (this.getHeight() - asteroid2.getPixelsTall() - 32))));
				lastAsteroidTime = currentTime;
			}
			else {
				// draw explosion
				getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	
	}
	
	protected void checkMegaManAsteroid2Collisions() {
		GameStatus status = getGameStatus();
		if(asteroid2.intersects(megaMan)){
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeAsteroid(asteroid2);
		}
	}
	
	protected void checkAsteroidAsteroid2Collision() {
		if(asteroid.intersects(asteroid2)){
			removeAsteroid(asteroid);
			removeAsteroid(asteroid2);

		}
	}
	
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<4)	platforms[i].setLocation(50 + i*50, getHeight()/2 + 140 - i*40);
			if(i==4) platforms[i].setLocation(50 +  i*50, getHeight()/2 + 140 - i*40);
			if(i>4){	
				int k=4;
				platforms[i].setLocation(50 + i*50, getHeight()/2 - 170 + (i-k)*40 );
				k=k-2;
			}
		}
		return platforms;
	}
	
	public Asteroid newAsteroid2(RompeDiscotekasLevel3State screen){
		int xPos = (int) (screen.getWidth() - Asteroid.WIDTH);
		int yPos = rand.nextInt((int)(screen.getHeight() - Asteroid.HEIGHT - 32));
		asteroid2 = new Asteroid(xPos, yPos);
		return asteroid2;
	}
}
