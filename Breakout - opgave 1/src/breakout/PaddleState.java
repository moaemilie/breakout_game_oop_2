package breakout;

/**
 * this class represents a Paddle in the game map.
 * 
 * @invar | getTopLeft().getX() < getBottomRight().getX()
 * @invar | getTopLeft().getY() < getBottomRight().getY()
 */

public class PaddleState {
	
	private final Vector size;
	private final Point center;
	
	/**
	 * Returns a Paddle with a given center point and vectore that deside its size.
	 * 
	 * @pre | center.getX() > 0 
	 * @pre | center.getY() > 0 
	 * 
	 * @post |  getCenter() == center
	 */
	public PaddleState(Point center, Vector size) {
		this.center = center; 
		this.size = size;
		}
	
	/**
	 * @post | result.getX() >= 0 
	 * @post | result.getY() >= 0 
	 */
	public Point getTopLeft(){
		return center.minus(size);
	}
	
	/**
	 * @post | result.getX() > 0 
	 * @post | result.getY() > 0 
	 */
	public Point getBottomRight(){
		return center.plus(size);
	}
	
	/**
	 * @post | result.getX() > 0 
	 * @post | result.getY() > 0 
	 */
	public Point getCenter(){
		return center;
	}
	
}
	
