package breakout;

import java.util.Arrays;
import java.util.ArrayList; // import the ArrayList class
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
		BallState[] newBalls = new BallState[balls.length];
		for (int i = 0; i < balls.length; i++) {
			Vector velocity = balls[i].getVelocity();
			Point center = balls[i].getCenter();
			Point newCenter = new Point(center.getX() + velocity.getX(), center.getY() + velocity.getY());
			newBalls[i] = new BallState(newCenter, balls[i].getDiameter(), velocity);
		}
		
		for (int i = 0; i < newBalls.length; i++) {
			
			// Checks if the ball hits the left wall and creates a mirrored ball
			if(newBalls[i].getCenter().getX() - (newBalls[i].getDiameter()/2) <= 0 && newBalls[i].getVelocity().product(Vector.LEFT) > 0) {
				BallState newBallState = new BallState(newBalls[i].getCenter(),newBalls[i].getDiameter(), newBalls[i].getVelocity().mirrorOver(Vector.LEFT));
				newBalls[i] = newBallState;
			}
			
			// Checks if the ball hits the right wall and creates a mirrored ball
			if((newBalls[i].getCenter().getX() + (newBalls[i].getDiameter()/2)) >= bottomRight.getX() && newBalls[i].getVelocity().product(Vector.RIGHT) > 0) {
				BallState newBallState = new BallState(newBalls[i].getCenter(),newBalls[i].getDiameter(), newBalls[i].getVelocity().mirrorOver(Vector.RIGHT));
				newBalls[i] = newBallState;
			}
			
			
			// Checks if the ball hits the top and creates a mirrored ball
			if(newBalls[i].getCenter().getY() + (newBalls[i].getDiameter()/2) <= 0 && newBalls[i].getVelocity().product(Vector.UP) > 0) {
				BallState newBallState = new BallState(newBalls[i].getCenter(),newBalls[i].getDiameter(), newBalls[i].getVelocity().mirrorOver(Vector.UP));
				newBalls[i] = newBallState;
			}
			
		
		balls = newBalls;
			
	}
		// Checks if the ball hits the bottom and removes the ball
		ArrayList<BallState> bottomBalls = new ArrayList<BallState>();
		for (int i = 0 ;i < newBalls.length; i++) {
			if(newBalls[i].getCenter().getY() + (newBalls[i].getDiameter()/2) <= bottomRight.getY()) {
				bottomBalls.add(newBalls[i]);
			}
		}
		BallState[] newBottomBalls = new BallState[bottomBalls.size()];
		newBottomBalls = bottomBalls.toArray(newBottomBalls);
		balls = newBottomBalls;
		
		// Check if the ball hits a block and removes the block and bounces the ball
		ArrayList<BlockState> blocksNotHit = new ArrayList<BlockState>();
		for (int i = 0 ;i < balls.length; i++) {
			for (int j =0; j < blocks.length; j++) {
				if(blocks[j].getTopLeft().getX() < (balls[i].getCenter().getX() + (balls[i].getDiameter()/2)) &&
						(balls[i].getCenter().getX() + (balls[i].getDiameter()/2)) < blocks[j].getBottomRight().getX() &&
						blocks[j].getBottomRight().getY() > (balls[i].getCenter().getY() + (balls[i].getDiameter()/2)) &&
						(balls[i].getCenter().getY() + (balls[i].getDiameter()/2)) > blocks[j].getTopLeft().getY()){
					
					if(balls[i].getVelocity().product(Vector.UP) > 0 && balls[i].getVelocity().product(Vector.LEFT) > 0) {
						
					}
					if(balls[i].getVelocity().product(Vector.DOWN) > 0 && balls[i].getVelocity().product(Vector.RIGHT) > 0) {
					
					}}
				else{
					blocksNotHit.add(blocks[i]);
					
				}
			}
		}
		//BlockState[] Newblocks = new BlockState[blocksNotHit.size()];
		//Newblocks = blocksNotHit.toArray(Newblocks);
		//blocks = Newblocks;

		
		
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
	
	/**
	 * This method checks if the game is won, by checking if there is no blocks left, but there are balls left.
	 */
	public boolean isWon() {
		if(blocks.length == 0 && balls.length != 0){
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * This method checks if the game is lost, by looking if there is any balls left.
	 * 
	 */
	public boolean isDead() {
		if(balls.length == 0){
			return true;
		}
		else {
			return false;
		}
	}
}
