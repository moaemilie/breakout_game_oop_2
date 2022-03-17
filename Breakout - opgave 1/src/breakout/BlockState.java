package breakout;

/**
 * This class represents a block with a gived top left point and botom right point.
 *  
 * @immutable
 */

public class BlockState {
	// TODO: implement
	// Contractual programming
	
	private final Point top_left;
	private final Point bottom_right;
	
	/**
	 * @pre | top_left.getX() >= 0 
	 * @pre | top_left.getY() >= 0 
	 * @pre | bottom_right.getY() > top_left.getY()
	 * @pre | bottom_right.getX() > top_left.getX()
	 */
	
	public BlockState(Point top_left, Point bottom_right) {
		this.top_left = top_left; 
		this.bottom_right = bottom_right;

	}
	
	
}
