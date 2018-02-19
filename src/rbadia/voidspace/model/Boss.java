package rbadia.voidspace.model;

public class Boss extends GameObject {
	
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
//	private static final int Y_OFFSET = 325; // initial y distance of the ship from the bottom of the screen 
	
	public static final int WIDTH = 110;
	public static final int HEIGHT = 100;
	
	public Boss(int xPos, int yPos) {
		super(xPos, yPos, Boss.WIDTH, Boss.HEIGHT);
		this.setSpeed(DEFAULT_SPEED);
	}

	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	
	public boolean touchUpperOfScreen(Boss boss){
		if(boss.getY() <= this.getHeight()-20){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean touchBottomOfScreen(Boss boss){
		if(boss.getY() >= this.getHeight()-100){
			return true;
		}
		else{
			return false;
		}
	}

}
