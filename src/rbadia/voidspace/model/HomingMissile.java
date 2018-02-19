package rbadia.voidspace.model;

public class HomingMissile extends GameObject{
	
	/**
	 * This class represents a homing missile for the boss in level4state.
	 */
	private static final long serialVersionUID = -698580132570764820L;
	public static final int DEFAULT_SPEED = 4;
	public static final  int WIDTH = 50;
	public static final int HEIGHT = 20;
	private int missileResistance;
	
	public HomingMissile(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setSpeed(DEFAULT_SPEED);
		this.setMissileResistance(5);
	}
	
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	public int getMissileResistance() {
		return missileResistance;
	}
	public void setMissileResistance(int missileResistance) {
		this.missileResistance = missileResistance;
	}
}
