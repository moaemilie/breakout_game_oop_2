package breakout;

import java.util.Arrays;
import java.util.stream.IntStream;

// TODO: implement, document
//Defensive programming
/**
 * 
 * This class represents the state of the breakout game.
 *
 */

public class BreakoutState {
	
	private BallState[] balls;
	private BlockState[] blocks;
	private Point bottomRight;
	private PaddleState paddle;
	
	/**
	 * 
	 * Return a new breakout game.
	 * 
	 * @throws IllegalArgumentException | Arrays.stream(balls).allMatch(e -> e!= null)
	 * @throws IllegalArgumentException | Arrays.stream(blocks).allMatch(e -> e!= null)
	 * @throws IllegalArgumentException | bottomRight == null
	 * @throws IllegalArgumentException | paddle == null
	 * 
	 * @post | Arrays.equals(getBalls(), balls)
	 * @post | Arrays.equals(getBlocks(), blocks)
	 * @post | getPaddle() == paddle
	 * @post | getBottomRight() == bottomRight
	 */
	public BreakoutState(BallState[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		if(!Arrays.stream(balls).allMatch(e -> e!= null)) {
			throw new IllegalArgumentException();}
		if(!Arrays.stream(blocks).allMatch(e -> e!= null)) {
			throw new IllegalArgumentException();}
		if(bottomRight == null) {
			throw new IllegalArgumentException();}
		if(paddle == null) {
			throw new IllegalArgumentException();}
		
		this.balls = balls;
		this.blocks = blocks;
		this.bottomRight = bottomRight;
		this.paddle = paddle;
	}
	
	/** Return this breakout state's list of balls. */
	public BallState[] getBalls() {
		return balls;
	}

	/** Return this breakout state's list of blocks. */
	public BlockState[] getBlocks() {
		return blocks;
	}
	
	/** Return this breakout state's paddle. */
	public PaddleState getPaddle() {
		return paddle;
	}

	/** Return the coordinates of this breakout state's bottom right corner. */
	public Point getBottomRight() {
		return bottomRight;
	}
	
	/**
	 * This method performs movement of a ball, including checks of whether the ball hits any other elements.
	 * 
	 * @throws IllegalArgumentException | 
	 * 
	 * @post |
	 * 
	 */
	public void tick(int paddleDir) {
	}

	public void movePaddleRight() {
	}

	public void movePaddleLeft() {
	}
	
	public boolean isWon() {
		return false;
	}

	public boolean isDead() {
		return false;
	}
}
