package breakout;

import java.util.Arrays;
import java.util.stream.IntStream;

// TODO: implement, document
//Defensive programming
/**
 * 
 * Each instance of this class stores a breakout game board.
 *
 */

public class BreakoutState {
	/**
	 * @invar | balls != null
	 * 
	 * @representationObject
	 */
	private BallState[] balls;
	
	/**
	 * @invar | blocks != null
	 * 
	 * @representationObject
	 */
	private BlockState[] blocks;
	
	private Point bottomRight;
	private PaddleState paddle;
	
	/**
	 * 
	 * Initialize breakout game with given balls, blocks, bottom right corner and paddle.
	 * 
	 * @throws IllegalArgumentException | Arrays.stream(input_balls).allMatch(e -> e!= null)
	 * @throws IllegalArgumentException | Arrays.stream(input_blocks).allMatch(e -> e!= null)
	 * @throws IllegalArgumentException | bottomRight == null
	 * @throws IllegalArgumentException | paddle == null
	 * 
	 * @post | Arrays.equals(getBalls(), input_balls)
	 * @post | Arrays.equals(getBlocks(), input_blocks)
	 * @post | getPaddle() == paddle
	 * @post | getBottomRight() == bottomRight
	 */
	public BreakoutState(BallState[] input_balls, BlockState[] input_blocks, Point bottomRight, PaddleState paddle) {
		if(!Arrays.stream(input_balls).allMatch(e -> e!= null)) {
			throw new IllegalArgumentException();}
		if(!Arrays.stream(input_blocks).allMatch(e -> e!= null)) {
			throw new IllegalArgumentException();}
		if(bottomRight == null) {
			throw new IllegalArgumentException();}
		if(paddle == null) {
			throw new IllegalArgumentException();}
		
		balls = input_balls;
		blocks = input_blocks;
		this.bottomRight = bottomRight;
		this.paddle = paddle;
	}
	
	/** 
	 * Return this breakout state's list of balls. 
	 * 
	 * @post | result != null
	 * @creates | result
	 */
	public BallState[] getBalls() {
		return balls.clone();
	}

	/** 
	 * Return this breakout state's list of blocks. 
	 * 
	 * @post | result != null
	 * @creates | result 
	 */
	public BlockState[] getBlocks() {
		return blocks.clone();
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
	 * @mutates | this
	 */
	public void tick(int paddleDir) {
		int i;
		BallState[] newBalls = new BallState[balls.length];
		for (i = 0; i < balls.length; i++) {
			Vector velocity = balls[i].getVelocity();
			Point center = balls[i].getCenter();
			Point newCenter = new Point(center.getX() + velocity.getX(), center.getY() + velocity.getY());
			newBalls[i] = new BallState(newCenter, balls[i].getDiameter(), velocity);
		}
		
		for (i = 0; i < newBalls.length; i++) {
			
			// Checks if the ball hits the left wall and creates a mirrored ball
			if(newBalls[i].getCenter().getX() - (newBalls[i].getDiameter()/2) <= 0 && newBalls[i].getVelocity().product(Vector.LEFT) > 0) {
				BallState newBallState = new BallState(newBalls[i].getCenter(),newBalls[i].getDiameter(), newBalls[i].getVelocity().mirrorOver(Vector.LEFT));
				newBalls[i] = newBallState;
			}
			
			// Checks if the ball hits the right wall and creates a mirrored ball
			if((newBalls[i].getCenter().getX() + (newBalls[i].getDiameter()/2)) >= 10 && newBalls[i].getVelocity().product(Vector.RIGHT) > 0) {
				BallState newBallState = new BallState(newBalls[i].getCenter(),newBalls[i].getDiameter(), newBalls[i].getVelocity().mirrorOver(Vector.RIGHT));
				newBalls[i] = newBallState;
			}
			
			
			
			// Checks if the ball hits the top and creates a mirrored ball
			if(newBalls[i].getCenter().getY() + (newBalls[i].getDiameter()/2) <= 0 && newBalls[i].getVelocity().product(Vector.UP) > 0) {
				BallState newBallState = new BallState(newBalls[i].getCenter(),newBalls[i].getDiameter(), newBalls[i].getVelocity().mirrorOver(Vector.UP));
				newBalls[i] = newBallState;
			}
			
			
		}
			
		
		balls = newBalls;
			
	}
	
	/**
	 * This method moves the paddle 10 steps to the right.
	 * 
	 * @mutates | this
	 */
	public void movePaddleRight() {
		Point center = paddle.getCenter();
		int newX = center.getX() + 10;
		Point newCenter = new Point(newX, center.getY());
			
		Vector size = new Vector(center.getX() - paddle.getTopLeft().getX(), center.getY() - paddle.getTopLeft().getY());
		PaddleState newPaddle = new PaddleState(newCenter, size);
		
		this.paddle = newPaddle;
	}
	
	/**
	 * This method moves the paddle 10 steps to the left.
	 * 
	 * @mutates | this
	 */
	public void movePaddleLeft() {
		Point center = paddle.getCenter();
		int newX = center.getX() - 10;
		Point newCenter = new Point(newX, center.getY());
			
		Vector size = new Vector(center.getX() - paddle.getTopLeft().getX(), center.getY() - paddle.getTopLeft().getY());
		PaddleState newPaddle = new PaddleState(newCenter, size);
		
		this.paddle = newPaddle;
	}
	
	public boolean isWon() {
		return false;
	}

	public boolean isDead() {
		return false;
	}
}
