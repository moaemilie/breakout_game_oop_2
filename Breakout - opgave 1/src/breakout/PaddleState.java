package breakout;

/**
 * @invar | getTopLeft().getX() < getBottomRight().getX()
 * @invar | getTopLeft().getY() < getBottomRight().getY()
 */

public class PaddleState {
	// TODO: implement
	// Contractual programming
	
	private final Vector size;
	private final Point center;
	/**
	 * @pre | center.getX() > 0 
	 * @pre | center.getY() > 0 
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
}
	
