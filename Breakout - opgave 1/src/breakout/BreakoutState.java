package breakout;

import java.util.Arrays;
import java.util.stream.IntStream;

// TODO: implement, document
//Defensive programming

public class BreakoutState {
	
	private BallState[] balls;
	private BlockState[] blocks;
	private Point bottomRight;
	private PaddleState paddle;
	
	/**
	 * @throws IllegalArgumentException | Arrays.stream(balls).allMatch(e -> e!= null)
	 * @throws IllegalArgumentException | Arrays.stream(blocks).allMatch(e -> e!= null)
	 * @throws IllegalArgumentException | bottomRight == null
	 * @throws IllegalArgumentException | paddle == null
	 * 
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
	
	
	public BallState[] getBalls() {
		return balls;
	}

	public BlockState[] getBlocks() {
		return blocks;
	}

	public PaddleState getPaddle() {
		return paddle;
	}

	public Point getBottomRight() {
		return bottomRight;
	}

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
