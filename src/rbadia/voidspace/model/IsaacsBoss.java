package rbadia.voidspace.model;

public class IsaacsBoss extends GameObject{

	/**
	 * This class is the base of the boss of level4state. This boss is static(doesn't move) 
	 * but fires homing missiles.
	 */
	private static final long serialVersionUID = -564205353079183196L;
	public static final int WIDTH = 60;
	public static final int HEIGHT = 100;
	private int life;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public IsaacsBoss(int xPos, int yPos){
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setLife(40);
	}
	
}
