package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.RompeDiscotekasGraphicsManager;
import rbadia.voidspace.model.HomingMissile;
import rbadia.voidspace.model.IsaacsBoss;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.MovingPlatforms;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public class RompeDiscotekasLevel4State extends RompeDiscotekasLevel1State{

	/**
	 * This level involves a moving platform and a boss. The boss appears after 8 asteroids are destroyed
	 * and you can only win the level destroying the boss. Be careful, he fires homing missiles.
	 */
	private static final long serialVersionUID = -6996400541816230823L;
	protected MovingPlatforms[] movingPlatforms;
	protected boolean bossAppears=false;
	protected boolean bossIsDead=false;
	protected boolean bossStillAlive=false;
	protected boolean bossBattleStarts=false;
	protected IsaacsBoss levelBoss;
	protected int homingMissileDelay=0;
	protected HomingMissile missile;
	protected Rectangle explosion;
	
	public RompeDiscotekasLevel4State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic,
			InputHandler inputHandler, GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		try {
			this.background=ImageIO.read(new File("images/newBack3.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void doStart() {	
		super.doStart();
		numPlatforms--;
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
	}
	
	@Override//Override to add new methods
	public void updateScreen(){
		if(!InputHandler.isPause()) {//Checks if the game is paused
			super.updateScreen();
			drawMovingPlatform();
			if(levelAsteroidsDestroyed>=2*this.getLevel()&&!bossBattleStarts) {
				bossAppears=true;
				bossBattleStarts=true;
				maximumAsteroidSpeed=5;//Reduce maximum speed to make game easier
				//Replace mega man to start of screen so that enemy is at end of screen 
				megaMan.setLocation(5, (int)(this.getHeight()-megaMan.getHeight())-MegaMan.Y_OFFSET*2);
				//Add adrenaline rush to mega man
				this.getGameStatus().setLivesLeft(this.getGameStatus().getLivesLeft()+10);
				JOptionPane.showMessageDialog( null, "Something's not right...");
			}
			if(bossAppears) {
				bossAppears=false;
				newBoss();
				bossStillAlive=true;
			}
			if(bossStillAlive) {
				drawBoss();
				drawMissile();
				checkBulletBossCollision();
				checkMissileMegaManCollision();
				checkBulletMissileCollision();
				checkBigBulletMissileCollision();
				checkBigBulletBossCollision();
			}
		}
		else//Draws paused string 
			super.updateScreen();
	}
	
	@Override
	public boolean isLevelWon() {//To win you must kill the boss
		if(bossIsDead||levelPass)
			return true;
		return false;
	}

	
	@Override//Override to change platforms
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		movingPlatforms=new MovingPlatforms[1];//One Moving Platform
		int k=0;
		int x=0;
		int platformPosition=0;
		for(int i=0;i<n;i++) {
			if(i==0) {
				movingPlatforms[i]=new MovingPlatforms(0, 0);
				movingPlatforms[i].setLocation(0,getHeight()/2 + 120);
				movingPlatforms[i].setDirection(1);
				movingPlatforms[i].setSpeed(MovingPlatforms.DEFAULT_SPEED);
				k++;
			}
			else if(i==5) {
				this.platforms[platformPosition]=new Platform(0,0);
				platforms[platformPosition].setLocation((int)(this.getWidth()/2-platforms[platformPosition].getWidth()/2),getHeight()/2 + 130+22*i - k*125);
				platformPosition++;
			}
			else {
				this.platforms[platformPosition]=new Platform(0,0);
				platforms[platformPosition].setLocation(110+125*x,getHeight()/2 + 180 - k*130);
				x++;
				if(x==3) {
					x=0;
					k++;
				}
				platformPosition++;
				}
		}
		return platforms;	
	}
	
	
	public void drawMovingPlatform() {
		RompeDiscotekasGraphicsManager grd=(RompeDiscotekasGraphicsManager)getGraphicsManager();
		Graphics2D g2d = getGraphics2D();
			for(int i=0;i<movingPlatforms.length;i++) {
				if(movingPlatforms[i].getDirection()==1) {
					if(movingPlatforms[i].getX()+MovingPlatforms.WIDTH<this.getWidth()) {
						movingPlatforms[i].translate(movingPlatforms[i].getSpeed(), 0);
					}
					else
						movingPlatforms[i].setDirection(0);
				}
				else {
					if(movingPlatforms[i].getX()>0) {
						movingPlatforms[i].translate(-movingPlatforms[i].getSpeed(), 0);
					}
					else
						movingPlatforms[i].setDirection(1);
				}
				grd.drawMovingPlatform(movingPlatforms[i], g2d, this, 0);
			}
		
	}
	
	@Override//Override to add moving platform gravity
	public boolean Fall() {
		for(int i=0;i<movingPlatforms.length;i++) {
			if((((movingPlatforms[i].getX() < megaMan.getX()) && (megaMan.getX()< movingPlatforms[i].getX() + MovingPlatforms.WIDTH))
					|| ((movingPlatforms[i].getX() < megaMan.getX() + megaMan.getWidth()) && (megaMan.getX() + megaMan.getWidth()< movingPlatforms[i].getX() + MovingPlatforms.WIDTH)))
					&& ((megaMan.getY() + megaMan.getHeight()) == movingPlatforms[i].getY()))
			{
				return false;
			}
		}
			return super.Fall();
	}
	
	@Override//Override to move up until boss
	public void moveMegaManRight(){
		if(bossStillAlive) {
			if(megaMan.getX()+megaMan.width<this.getWidth()-IsaacsBoss.WIDTH-7) {
				megaMan.translate(megaMan.getSpeed(), 0);
				megaMan.setDirection(1);
			}
		}
		else 
			super.moveMegaManRight();
	}
	
	//Creates a new boss
	public IsaacsBoss newBoss() {
		
		levelBoss=new IsaacsBoss(this.getWidth()-IsaacsBoss.WIDTH-5,this.getHeight()-IsaacsBoss.HEIGHT);
		Graphics2D g2d = this.getGraphics2D();
		RompeDiscotekasGraphicsManager grd=(RompeDiscotekasGraphicsManager) this.getGraphicsManager();
		if(!RompeDiscotekasLevel1State.isBonusLevel()) {
			MegaManMain.audioClip.close();
			MegaManMain.audioFile = new File("audio/BossBattle.wav");//To start boss music
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
		grd.drawIsaacsBoss(levelBoss, g2d, this);
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			JOptionPane.showMessageDialog( null, "Ohh no... To save Christmas... I MUST BEAT THIS GUY!!"+
					"\r\n"+
					"\r\n                 (Life Boost Added)");
			levelBoss.setLife(levelBoss.getLife()+20);//Make Christmas boss a little more difficult
		}
		else
			JOptionPane.showMessageDialog( null, "Ohh no... I MUST BEAT THIS GUY!!"+
											"\r\n"+
											"\r\n           (Life Boost Added)");
		newMissile();
		this.getInputHandler().reset();
		return levelBoss;
	}
	
	//Method to draw the boss
	public void drawBoss() {
		Graphics2D g2d = this.getGraphics2D();
		RompeDiscotekasGraphicsManager grd=(RompeDiscotekasGraphicsManager) this.getGraphicsManager();
		if(levelBoss.getLife()>0) {
			if(homingMissileDelay>=100) {//Fire a missile every 4 seconds approximately
				relocateMissile();
				homingMissileDelay=0;
			}
			else {
				homingMissileDelay++;
			}
			grd.drawIsaacsBoss(levelBoss, g2d, this);
		}
		else {
			//Everything back to normal
			bossIsDead=true;
			bossStillAlive=false;
			maximumAsteroidSpeed=8;
			levelBoss.setLocation(this.getWidth()-IsaacsBoss.HEIGHT,this.getHeight()-IsaacsBoss.WIDTH);
			grd.drawIsaacsBossDead(levelBoss, g2d, this);
			this.getGameStatus().setAsteroidsDestroyed(
					this.getGameStatus().getAsteroidsDestroyed()+2000);
			repaint();			
			}
	}
	
	//Method that creates a new missile so that the boss can fire it
	public void newMissile() {
		Graphics2D g2d = this.getGraphics2D();
		RompeDiscotekasGraphicsManager grd=(RompeDiscotekasGraphicsManager) this.getGraphicsManager();
		missile=new HomingMissile((int)levelBoss.getX(),(int)(levelBoss.getY()+IsaacsBoss.HEIGHT/2));
		grd.drawMissile(missile, g2d, this);
	}
	
	//Method that draws the homing missile
	public void drawMissile() {
		RompeDiscotekasGraphicsManager grd=(RompeDiscotekasGraphicsManager) this.getGraphicsManager();
		if(missile.getX()+missile.width>0&&missile.getX()+missile.width>megaMan.getX()) {
			if(megaMan.getY()+MegaMan.HEIGHT/2<(missile.getY()+HomingMissile.HEIGHT/2)) {
				int dy=(int)((missile.getY()-megaMan.getY())/20);
				missile.translate(-missile.getSpeed(), -dy);
			}
			else if(megaMan.getY()+MegaMan.HEIGHT/2>(missile.getY()+HomingMissile.HEIGHT/2)){
				int dy=(int)((megaMan.getY()-missile.getY())/20);
				missile.translate(-missile.getSpeed(), dy);
			}
			else {
				missile.translate(-missile.getSpeed(), 0);
			}
		}
		else if(missile.getX()+missile.width>0){
			missile.translate(-missile.getSpeed(), 0);
		}
		else {
			missile.setLocation(-HomingMissile.WIDTH,-HomingMissile.HEIGHT);
		}
		grd.drawMissile(missile, this.getGraphics2D(), this);
	}
	
	//Method to reposition the missile for attack
	public void relocateMissile() {
		missile.setMissileResistance(5);
		missile.setLocation((int)(levelBoss.getX()-HomingMissile.WIDTH),(int)(levelBoss.getY()+IsaacsBoss.HEIGHT/2));
	}
	
	//Method to remove missile
	public void removeMissile() {
		this.getSoundManager().playAsteroidExplosionSound();
		RompeDiscotekasGraphicsManager grd=(RompeDiscotekasGraphicsManager) this.getGraphicsManager();
		explosion = new Rectangle(
				(int)missile.getX(),
				(int)missile.getY(),
				HomingMissile.WIDTH,
				HomingMissile.HEIGHT);
		grd.drawExplosion(explosion, this.getGraphics2D(), this);
		missile.setLocation(-HomingMissile.WIDTH,-HomingMissile.HEIGHT);
		missile.setMissileResistance(5);
	}
	
	//Method to check if missile hits mega man
	public void checkMissileMegaManCollision() {
		if(megaMan.intersects(missile)) {
			this.getGameStatus().setLivesLeft(this.getGameStatus().getLivesLeft() - 1);
			removeMissile();
		}
	}
	
	//Method to see if bullets are hitting missile
	public void checkBulletMissileCollision() {
		for(int i=0;i<bullets.size();i++) {
			if(bullets.get(i).intersects(missile)) {
				if(missile.getMissileResistance()>0)
					missile.setMissileResistance(missile.getMissileResistance()-1);
				else {
					removeMissile();
					this.getGameStatus().setAsteroidsDestroyed(
							this.getGameStatus().getAsteroidsDestroyed()+50);
				}
				bullets.remove(i);				
			}
		}
	}
	
	//Method to see if bullets are hitting bad guy and remove from his life
		public void checkBulletBossCollision() {
			for(int i=0;i<bullets.size();i++) {
				if(bullets.get(i).intersects(levelBoss)) {
					levelBoss.setLife(levelBoss.getLife()-1);
					bullets.remove(i);
				}
			}
		}
		
		//Method to check if bigBullet collides with missile
		public void checkBigBulletMissileCollision() {
			for(int i=0;i<bigBullets.size();i++) {
				if(bigBullets.get(i).intersects(missile)) {
					removeMissile();
					this.getGameStatus().setAsteroidsDestroyed(
							this.getGameStatus().getAsteroidsDestroyed()+50);
					bigBullets.remove(i);				
				}
			}
		}
		
		//Method to check big bullet boss collision
		public void checkBigBulletBossCollision() {
			for(int i=0;i<bigBullets.size();i++) {
				if(bigBullets.get(i).intersects(levelBoss)) {
					levelBoss.setLife(levelBoss.getLife()-5);
					bigBullets.remove(i);
				}
			}
		}
	
}
