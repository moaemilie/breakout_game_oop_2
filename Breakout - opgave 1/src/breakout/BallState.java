package breakout;


/**
 * This class represents a ball with a given ´diameter´, ´center´and ´INIT_BALL_VELOCITY´.
 *  
 * @immutable
 */

public class BallState {
	
	/**
	 * @invar | center.getX() > 0
	 * @invar | center.getY() > 0
	 * @invar | diameter > 0
	 */
	private final Point center;
	private final Vector INIT_BALL_VELOCITY;
	private final int diameter;
	
	/**
	 * @pre | diameter > 0 
	 * @pre | center.getY() > 0
	 * @pre | center.getX() > 0
	 * 
	 * @post | getCenter() == center
	 * @post | getVelocity() == INIT_BALL_VELOCITY
	 */
	public BallState(Point center, int diameter, Vector INIT_BALL_VELOCITY) {
		this.center = center; 
		this.diameter = diameter;
		this.INIT_BALL_VELOCITY = INIT_BALL_VELOCITY;
	}
	
	/**
	 * Returns the center of the ball
	 */
	public Point getCenter() {
		return center;
	}
	
	/**
	 * Returns the velocity of the ball
	 */
	public Vector getVelocity() {
		return INIT_BALL_VELOCITY;
	}
}