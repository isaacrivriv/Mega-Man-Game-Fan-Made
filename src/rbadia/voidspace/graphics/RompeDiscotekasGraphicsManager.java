package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import rbadia.voidspace.main.RompeDiscotekasLevel1State;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.HomingMissile;
import rbadia.voidspace.model.IsaacsBoss;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.MovingPlatforms;
import rbadia.voidspace.model.Platform;

public class RompeDiscotekasGraphicsManager extends GraphicsManager{
	
	private BufferedImage invertedMegaMan;
	private BufferedImage megaFireLImg;
	private BufferedImage megaFallLImg;
	private BufferedImage platformMoving;
	private BufferedImage boss;
	private BufferedImage missile;
	private BufferedImage deadBoss;
	private BufferedImage explosion;
	private BufferedImage christmasAsteroid;
	private BufferedImage christmasBullet;
	private BufferedImage christmasBigBullet;
	private BufferedImage christmasBoss;
	private BufferedImage christmasMissile;
	private BufferedImage deadChristmasBoss;
	private BufferedImage santaMegaMan;
	private BufferedImage santaMegaManLeft;
	private BufferedImage santaFallLeft;
	private BufferedImage santaFallRight;
	private BufferedImage santaFireRight;
	private BufferedImage santaFireLeft;
	private BufferedImage otherPlatform;
	
	public RompeDiscotekasGraphicsManager() {
		super();
		try {
			this.invertedMegaMan = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/invertedMegaMan.png"));
			this.megaFireLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireLeft.png"));
			this.megaFallLImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallLeft.png"));
			this.platformMoving=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform2.png"));
			this.boss=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaBoss.png"));
			this.missile=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/homingMissile.png"));
			this.explosion=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			this.deadBoss=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/deadMegaBoss.png"));
			this.christmasAsteroid=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasAsteroid.png"));
			this.christmasBullet=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasBullet.png"));
			this.christmasBigBullet=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasBigBullet.png"));
			this.christmasBoss=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasBoss.png"));
			this.christmasMissile=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasMissile.png"));
			this.deadChristmasBoss=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/deadChristmasBoss.png"));
			this.santaMegaMan=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasMegaMan.png"));
			this.santaMegaManLeft=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasMegaManLeft.png"));
			this.santaFallLeft=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasFallLeft.png"));
			this.santaFallRight=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasFallRight.png"));
			this.santaFireLeft=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasMegaFireLeft.png"));
			this.santaFireRight=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/christmasMegaFireRight.png"));
			this.otherPlatform=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override//Override to add Christmas graphics
	public void drawMegaMan (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(santaMegaMan, megaMan.x, megaMan.y, observer);
		}
		else
			super.drawMegaMan(megaMan, g2d, observer);
	}
	
	public void drawMegaManLeft(MegaMan megaMan, Graphics2D g2d, ImageObserver observer) {
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(santaMegaManLeft, megaMan.x, megaMan.y, observer);
		}
		else
			g2d.drawImage(invertedMegaMan, megaMan.x, megaMan.y, observer);
	}
	
	@Override//Override to add Christmas graphics
	public void drawMegaFireR (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(santaFireRight, megaMan.x, megaMan.y, observer);
		}
		else
			super.drawMegaFireR(megaMan, g2d, observer);
	}
	
	public void drawMegaFireL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(santaFireLeft, megaMan.x, megaMan.y, observer);
		}
		else
			g2d.drawImage(megaFireLImg, megaMan.x, megaMan.y, observer);
	}
	
	@Override//Override to add Christmas graphics
	public void drawMegaFallR (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(santaFallRight, megaMan.x, megaMan.y, observer);
		}
		else
			super.drawMegaFallR(megaMan, g2d, observer);
	}
	
	public void drawMegaFallL (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(santaFallLeft, megaMan.x, megaMan.y, observer);
		}
		else
			g2d.drawImage(megaFallLImg, megaMan.x, megaMan.y, observer);	
	}
	
	@Override//Override to add Christmas graphics
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(christmasAsteroid, asteroid.x, asteroid.y, observer);
		}
		else
			super.drawAsteroid(asteroid, g2d, observer);
	}
	
	@Override//Override to add Christmas graphics
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(christmasBullet, bullet.x, bullet.y, observer);
		}
		else
			super.drawBullet(bullet, g2d, observer);
	}
	
	@Override//Override to add Christmas graphics
	public void drawBigBullet(BigBullet bigBullet, Graphics2D g2d, ImageObserver observer) {
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(christmasBigBullet, bigBullet.x, bigBullet.y, observer);
		}
		else
			super.drawBigBullet(bigBullet, g2d, observer);
	}
	
	@Override//Override to add Christmas graphics
	public void drawPlatform(Platform platform, Graphics2D g2d, ImageObserver observer, int i){
		if(RompeDiscotekasLevel1State.isBonusLevel()) {
			g2d.drawImage(otherPlatform, platform.x , platform.y, observer);
		}
		else
			super.drawPlatform(platform, g2d, observer, i);
	}
	
	public void drawMovingPlatform(MovingPlatforms platform, Graphics2D g2d, ImageObserver observer, int i) {
		g2d.drawImage(platformMoving, platform.x , platform.y, observer);
	}
	
	public void drawIsaacsBoss(IsaacsBoss megaBoss, Graphics2D g2d, ImageObserver observer) {
		if(RompeDiscotekasLevel1State.isBonusLevel()) {//If it is a bonus level change to Christmas boss
			g2d.drawImage(christmasBoss, megaBoss.x , megaBoss.y, observer);
		}
		else
			g2d.drawImage(boss, megaBoss.x , megaBoss.y, observer);
	}
	
	public void drawIsaacsBossDead(IsaacsBoss megaBoss, Graphics2D g2d, ImageObserver observer) {
		if(RompeDiscotekasLevel1State.isBonusLevel()) {//If it is a bonus level change to Christmas boss
			g2d.drawImage(deadChristmasBoss, megaBoss.x , megaBoss.y, observer);
		}
		else
			g2d.drawImage(deadBoss, megaBoss.x , megaBoss.y, observer);
	}
	
	public void drawMissile(HomingMissile missile, Graphics2D g2d, ImageObserver observer) {
		if(RompeDiscotekasLevel1State.isBonusLevel()) {//If it is a bonus level change to Christmas missile
			g2d.drawImage(christmasMissile, missile.x, missile.y, observer);
		}
		else
			g2d.drawImage(this.missile, missile.x, missile.y, observer);
	}
	
	public void drawExplosion(Rectangle explosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(this.explosion, explosion.x, explosion.y, observer);
	}
	
	
}
