package breakout;


/**
 * This class represents a ball with a given ´diameter´, ´center´and ´INIT_BALL_VELOCITY´.
 * 
 * @invar | getDiameter() > 0
 *  
 * @immutable
 */

public class BallState {
	
	/**
	 * @invar | diameter > 0
	 */
	private final Point center;
	private final Vector INIT_BALL_VELOCITY;
	private final int diameter;
	
	/**
	 * @pre | diameter > 0 
	 * 
	 * @post | getCenter() == center
	 * @post | getVelocity() == INIT_BALL_VELOCITY
	 * @post | getDiameter() == diameter
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
	
	/**
	 * Returns the diameter of the ball
	 */
	public int getDiameter() {
		return diameter;
	}
		
	/**
	 * Returns the top left corner of the ball
	 * 
	 * @post | result.isUpAndLeftFrom(this.getBottomRight())
	 * @post | result != null
	 */
	public Point getTopLeft(){
		Vector size = new Vector(this.getDiameter()/2, this.getDiameter()/2);
		Point topLeft = this.getCenter().minus(size);
		return topLeft;
	}
	
	/**
     * Returns the bottom right corner of the ball
     * 
     * @post | result != null
	 */
	public Point getBottomRight(){
		Vector size = new Vector(this.getDiameter()/2, this.getDiameter()/2);
		Point bottomRight = this.getCenter().plus(size);
		return bottomRight;
	}
}