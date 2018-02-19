package rbadia.voidspace.model;

public class MovingPlatforms extends GameObject{
	
	/**
	 * This class is the base code for a moving platform. It is very similar to the platform class but
	 * can't extend from it because it moves therefore needed the speed and direction methods of the 
	 * object class.
	 */
	private static final long serialVersionUID = -4070340271041047281L;
	public static final int WIDTH = 44;
	public static final int HEIGHT = 14;
	public static final int DEFAULT_SPEED=2;

	public MovingPlatforms(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
	}
	
	
}
