package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.RompeDiscotekasGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.sounds.SoundManager;

public class RompeDiscotekasLevel1State extends Level1State{

	/**
	 * This is the extension for the first level which other levels will extend from since
	 * it will have most of the base code for playing. Override many methods which would cause 
	 * problems with the extension and added many methods to make playing more fun.
	 */
	private static final long serialVersionUID = 3296372466554839973L;
	protected boolean levelPass=false;
	protected BufferedImage background;
	protected Asteroid[] asteroids;
	protected int maximumAsteroidSpeed=8;
	protected static boolean bonusLevel;
	
	public boolean isLevelPass() {
		return levelPass;
	}

	public void setLevelPass(boolean levelPass) {
		this.levelPass = levelPass;
	}
	
	public Asteroid[] getAsteroids() {
		return asteroids;
	}
	
	public static boolean isBonusLevel() {
		return bonusLevel;
	}

	public RompeDiscotekasLevel1State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic,
			InputHandler inputHandler, GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		bonusLevel=false;
		try {
			this.background=ImageIO.read(new File("images/newBack1.jpg"));//new image background
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override//Override to add new asteroids
	public void doStart() {
		super.doStart();
		moreAsteroids(this);
	}
	
	@Override//Override to set level pass and to set 5 asteroids more each level
	public boolean isLevelWon() {
		if(levelAsteroidsDestroyed>=3+5*(this.getLevel()-1)||levelPass)
			return true;
		return false;
	}
	
	@Override//Override to add new methods to run
	public void updateScreen(){
		if(!InputHandler.isPause()) {//Checks if the game is paused
			super.updateScreen();
			drawManyAsteroids();
			checkBulletManyAsteroidsCollision();
			checkBigBulletManyAsteroidsCollision();
			checkMegaManManyAsteroidsCollision();
			checkManyAsteroidsCollisionFloor();
		}
		else {//Draws paused string
			
			Graphics2D g2d = getGraphics2D();

			if(this.originalFont == null){
				this.originalFont = g2d.getFont();
				this.bigFont = originalFont;
			}

			String readyStr = "Game Paused"; 
			g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 10));
			FontMetrics fm = g2d.getFontMetrics();
			int ascent = fm.getAscent();
			int strWidth = fm.stringWidth(readyStr);
			int strX = (getWidth() - strWidth)/2;
			int strY = (getHeight() + ascent)/2;
			g2d.setPaint(Color.CYAN);
			g2d.drawString(readyStr, strX, strY);
		
		}
		}
	
	@Override//Override to add a new background per level
	protected void clearScreen() {
		super.clearScreen();
		this.getGraphics2D().drawImage(background, 0, 0, this);
	}
	
	@Override//Override to add megaMan falling and firing in left direction
	protected void drawMegaMan() {
		
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		RompeDiscotekasGraphicsManager grd=(RompeDiscotekasGraphicsManager) getGraphicsManager();
		if(!status.isNewMegaMan()){
			if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))){
				if(megaMan.getDirection()==1)
					getGraphicsManager().drawMegaFallR(megaMan, g2d, this);
				else 
					grd.drawMegaFallL(megaMan, g2d, this);
			}
		}

		if((Fire() == true || Fire2()== true) && (Gravity()==false)){
			if(megaMan.getDirection()==1) {
				getGraphicsManager().drawMegaFireR(megaMan, g2d, this);
			}
			else 
				grd.drawMegaFireL(megaMan, g2d, this);
			
		}

		if((Gravity()==false) && (Fire()==false) && (Fire2()==false)){
			if(megaMan.getDirection()==1)
				getGraphicsManager().drawMegaMan(megaMan, g2d, this);
			else
				grd.drawMegaManLeft(megaMan,g2d,this);
		}
	}
	
	@Override//Override to set mega mans direction
	public void moveMegaManLeft(){
		super.moveMegaManLeft();
		megaMan.setDirection(0);
	}
	
	@Override//Override to set mega mans direction
	public void moveMegaManRight(){
		super.moveMegaManRight();
		megaMan.setDirection(1);
	}
	
	
	@Override//Override to make mega man pose correctly
	protected boolean Fire(){
		MegaMan megaMan = this.getMegaMan();
		List<Bullet> bullets = this.getBullets();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(megaMan.getDirection()==1) {
				if((bullet.getX() > megaMan.getX() + megaMan.getWidth()) && 
						(bullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60)){
					return true;
			}
			}
			else {
				if((bullet.getX() < megaMan.getX())&& (bullet.getX()>=megaMan.getX()-60))
					return true;
			}
		}
		return false;
	}
	
	@Override//Override to draw pose when firing big bullet
	protected boolean Fire2(){
		MegaMan megaMan = this.getMegaMan();
		List<BigBullet> bigBullets = this.getBigBullets();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(megaMan.getDirection()==1) {
				if((bigBullet.getX() > megaMan.getX() + megaMan.getWidth()) && 
						(bigBullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60)){
					return true;
			}
			}
			else {
				if((bigBullet.getX() < megaMan.getX())&& (bigBullet.getX()>=megaMan.getX()-60))
					return true;
			}
		}
		return false;
	}
	
	
	
	@Override//Override to set bullet directions and starting positions so that mega man fires correctly
	public void fireBullet(){
		int xPos;
		int yPos;
		if(megaMan.getDirection()==1) {
			xPos=megaMan.x + megaMan.width - Bullet.WIDTH/2;
			yPos=megaMan.y + megaMan.width/2 - Bullet.HEIGHT +2;
		}
		else {
			xPos=megaMan.x + Bullet.WIDTH/2;
			yPos=megaMan.y + megaMan.width/2 - Bullet.HEIGHT +2;
		}
		Bullet bullet = new Bullet(xPos,yPos);
		bullet.setDirection(megaMan.getDirection());
		bullets.add(bullet);	
		this.getSoundManager().playBulletSound();
	}
	
	@Override//Override to set big bullet directions and starting positions so that mega man fires correctly
	public void fireBigBullet(){
		if(this.getGameStatus().getAsteroidsDestroyed()>=500) {
			this.getGameStatus().setAsteroidsDestroyed(this.getGameStatus().getAsteroidsDestroyed()-500);
			int xPos;
			int yPos;
			if(megaMan.getDirection()==1) {
				xPos = megaMan.x + megaMan.width - BigBullet.WIDTH / 2;
				yPos = megaMan.y + megaMan.width/2 - BigBullet.HEIGHT + 4;
			}
			else {
				xPos=megaMan.x + BigBullet.WIDTH/2;
				yPos=megaMan.y + megaMan.width/2 - BigBullet.HEIGHT +2;
			}
			BigBullet  bigBullet = new BigBullet(xPos, yPos);
			bigBullet.setDirection(megaMan.getDirection());
			bigBullets.add(bigBullet);
			this.getSoundManager().playBulletSound();
		}
	}
	
	@Override//Override to move bullets accordingly to directions
	public boolean moveBullet(Bullet bullet){
		if(bullet.getX()<= this.getWidth() && bullet.getDirection()==1){
			bullet.translate(bullet.getSpeed(), 0);
			return false;
		}
		
		else if(bullet.getX()>= 0 && bullet.getDirection()==0) {
			bullet.translate(-bullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	}
	
	@Override//Override to move big bullets accordingly to directions
	public boolean moveBigBullet(BigBullet bigBullet){
		if(bigBullet.getX()<= this.getWidth() && bigBullet.getDirection()==1){
			bigBullet.translate(bigBullet.getSpeed(), 0);
			return false;
		}
		
		else if(bigBullet.getX()>= 0 && bigBullet.getDirection()==0) {
			bigBullet.translate(-bigBullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	}
	
	//Method to set the properties of the asteroid
	public Asteroid setRandomProperties(Asteroid asteroid) {
		
		int option=rand.nextInt(3);
		int xPos;
		int yPos;
		int direction;
		int speed;
		int maximumDirection;
		int minimumDirection;
		if(option==2) {
			xPos = (int) (this.getWidth() - Asteroid.WIDTH);
			yPos = rand.nextInt((int)(this.getHeight() - Asteroid.HEIGHT- 32));
			maximumDirection=210;
			minimumDirection=150;
			direction=rand.nextInt(maximumDirection-minimumDirection+1)+minimumDirection;
			speed=rand.nextInt(maximumAsteroidSpeed-Asteroid.DEFAULT_SPEED+1)+Asteroid.DEFAULT_SPEED;
		}
		else if(option==1) {
			xPos=0;
			yPos = rand.nextInt((int)(this.getHeight() - Asteroid.HEIGHT- 32));
			maximumDirection=390;
			minimumDirection=330;
			direction=rand.nextInt(maximumDirection-minimumDirection+1)+minimumDirection;
			speed=rand.nextInt(maximumAsteroidSpeed-Asteroid.DEFAULT_SPEED+1)+Asteroid.DEFAULT_SPEED;
		}
		else {
			xPos=rand.nextInt(this.getWidth()-2*Asteroid.WIDTH+1)+Asteroid.WIDTH;
			yPos=0;
			maximumDirection=300;
			minimumDirection=240;
			direction=rand.nextInt(maximumDirection-minimumDirection+1)+minimumDirection;
			speed=rand.nextInt(maximumAsteroidSpeed-Asteroid.DEFAULT_SPEED+1)+Asteroid.DEFAULT_SPEED;
		}
		asteroid.setLocation(xPos,yPos);
		asteroid.setSpeed(speed);
		asteroid.setDirection(direction);
		
		return asteroid;
	}
	
	
	//Method to create more asteroids to have simultaneous at the same time.
	public Asteroid[] moreAsteroids(Level1State screen){
		if(this.getLevel()<3)
			asteroids=new Asteroid[this.getLevel()];
		else
			asteroids=new Asteroid[2];//Up to two asteroids plus the third one allowed
		for(int i=0;i<asteroids.length;i++) {
			asteroids[i]=new Asteroid(0,0);
			setRandomProperties(asteroids[i]);
		}
		return asteroids;
	}
	
	//Override to set asteroid random direction, speed, and location	
	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if((asteroid.getX() + asteroid.getWidth() >  0)&&asteroid.getX()<this.getWidth()&&asteroid.getY()+Asteroid.HEIGHT>0){
			int dx=(int)(asteroid.getSpeed()*Math.cos(Math.toRadians(asteroid.getDirection())));
			int dy=(int)(-1*asteroid.getSpeed()*Math.sin(Math.toRadians(asteroid.getDirection())));
			asteroid.translate(dx, dy);
			getGraphicsManager().drawAsteroid(asteroid, g2d, this);	
		}
		else {
			// draw a new asteroid
			status.setNewAsteroid(false);
			setRandomProperties(asteroid);
			}
		
	}
	
	//Method to draw the new asteroids
	public void drawManyAsteroids() {
		Graphics2D g2d = getGraphics2D();
		for(int i=0;i<asteroids.length;i++) {
			if(asteroids[i].getX()+Asteroid.WIDTH>0&&asteroids[i].getX()<this.getWidth()&&asteroids[i].getY()+Asteroid.HEIGHT>0) {
				int dx=(int)(asteroids[i].getSpeed()*Math.cos(Math.toRadians(asteroids[i].getDirection())));
				int dy=(int)(-asteroids[i].getSpeed()*Math.sin(Math.toRadians(asteroids[i].getDirection())));
				asteroids[i].translate(dx, dy);
				getGraphicsManager().drawAsteroid(asteroids[i], g2d, this);
			}
			else {
				setRandomProperties(asteroids[i]);
				}
			}
		}
	
	//Method to check if asteroid is impacted by bullet
	public void checkBulletManyAsteroidsCollision() {
		GameStatus status = getGameStatus();
		for(int i=0;i<asteroids.length;i++) {
			for(int j=0;j<bullets.size();j++) {
				if(bullets.get(j).intersects(asteroids[i])) {
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
					levelAsteroidsDestroyed++;
					damage=0;
					removeAsteroid(asteroids[i]);
					// remove bullet
					bullets.remove(j);
					break;
				}
			}
		}
	}
	
	@Override//Override to fix asteroids destroyed towards the next level counter. Forgot to add in original
	protected void checkBigBulletAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bigBullets.size(); i++){
			if(asteroid.intersects(bigBullets.get(i))){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
				levelAsteroidsDestroyed++;//This line was not added in other class. The asteroid destroyed will not count towards next level
				removeAsteroid(asteroid);
				bigBullets.remove(i);//Also not included. Bullet will not be removed if so.
			}
		}
	}
	
	//Method to check if big bullet hits new asteroids
	public void checkBigBulletManyAsteroidsCollision() {
		GameStatus status = getGameStatus();
		for(int i=0;i<asteroids.length;i++) {
			for(int j=0;j<bigBullets.size();j++) {
				if(bigBullets.get(j).intersects(asteroids[i])) {
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
					levelAsteroidsDestroyed++;
					damage=0;
					removeAsteroid(asteroids[i]);
					// remove big bullet
					bigBullets.remove(j);
					break;
				}
			}
		}
	}
	
	//Method to check if new asteroids collide with mega man
	public void checkMegaManManyAsteroidsCollision() {
		GameStatus status = getGameStatus();
		for(int i=0;i<asteroids.length;i++) 
			if(megaMan.intersects(asteroids[i])) {
				status.setLivesLeft(status.getLivesLeft() - 1);
				removeAsteroid(asteroids[i]);
			}	
	}
	
	//Method to check if the new asteroids created collide with floor
	public void checkManyAsteroidsCollisionFloor() {
		for(int i=0;i<asteroids.length;i++)
			for(int j=0;j<floor.length;j++)
				if(asteroids[i].intersects(floor[j])) {
					removeAsteroid(asteroids[i]);
				}
	}
	
	@Override//Override to fix exception of nullPointer where explosion couldn't occur because it wasn't on the screen
	public void removeAsteroid(Asteroid asteroid){
		// "remove" asteroid
		asteroidExplosion = new Rectangle(
				(int)asteroid.getX(),
				(int)asteroid.getY(),
				asteroid.getPixelsWide(),
				asteroid.getPixelsTall());
		setRandomProperties(asteroid);
		// play asteroid explosion sound
		this.getSoundManager().playAsteroidExplosionSound();
		this.getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, getGraphics2D(), this);
	}
	
}
