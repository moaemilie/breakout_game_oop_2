package breakout;

/**
 * This class represents a block with a given top left point and bottom right point.
 *  
 *  @invar | getTopLeft().isUpAndLeftFrom(getBottomRight())
 *  
 * @immutable
 */

public class BlockState {
	// TODO: implement
	// Contractual programming
	/**
	 * @invar | top_left.isUpAndLeftFrom(bottom_right)
	 */
	
	private final Point top_left;
	private final Point bottom_right;
	
	/**
	 * @pre | top_left.getX() >= 0 
	 * @pre | top_left.getY() >= 0 
	 * @pre | top_left.isUpAndLeftFrom(bottom_right)
	 * 
	 * @post | getTopLeft() == top_left
	 * @post | getBottomRight() == bottom_right
	 */
	public BlockState(Point top_left, Point bottom_right) {
		this.top_left = top_left; 
		this.bottom_right = bottom_right;
	}
	
	/**
	 * Returns top left corner of the block
	 */
	public Point getTopLeft() {
		return top_left;
	}
	
	/**
	 * Returns bottom right corner of the block
	 */
	public Point getBottomRight() {
		return bottom_right;
	}
	
}
