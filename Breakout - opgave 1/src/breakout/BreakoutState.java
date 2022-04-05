package breakout;

import java.util.Arrays;
import java.util.ArrayList; // import the ArrayList class

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
	 * @throws IllegalArgumentException | input_balls == null
	 * @throws IllegalArgumentException | input_blocks == null
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
		if(input_balls == null) {
			throw new IllegalArgumentException();}
		if(input_blocks == null) {
			throw new IllegalArgumentException();}
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
	 * This method checks if two objects intersect.
	 * 
	 * @mutates | this
	 */
	public BallState Intersect(BallState ball, BlockState block) {
		
		Vector normal = Vector.UP;
		
		int x_before = ball.getCenter().getX() - ball.getVelocity().getX();
		int y_before = ball.getCenter().getY() - ball.getVelocity().getY();
		
		if(x_before > block.getTopLeft().getX() && x_before < block.getBottomRight().getX()) {
			if(y_before < block.getTopLeft().getY()) {
				// It has hit the top
				normal = Vector.DOWN;
			}
			else if(y_before > block.getBottomRight().getY()) {
				// It has hit the bottom
				normal = Vector.UP;
			}
		}
		else if(y_before < block.getBottomRight().getY() && y_before > block.getTopLeft().getY()) {
			if(x_before < block.getTopLeft().getX()) {
				// it has hit the left wall	
				normal = Vector.RIGHT;
			}
			else if(x_before > block.getBottomRight().getX()) {
				// It has hit the right wall
				normal = Vector.LEFT;
			}
		}
		else {
			if(y_before < block.getTopLeft().getY()) {
				// It has hit the top
				normal = Vector.DOWN;
			}
			else if(y_before > block.getBottomRight().getY()) {
				// It has hit the bottom
				normal = Vector.UP;
			}
		}
		
		return new BallState(ball.getCenter(), ball.getDiameter(),ball.getVelocity().mirrorOver(normal));
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
		BallState[] newCrashBalls = new BallState[balls.length];
		
		for (int i = 0; i < balls.length; i++) {
			if(balls[i] != null) {
				int size = balls[i].getDiameter()/2;
				Point[] corners = {new Point(balls[i].getCenter().getX() - size, balls[i].getCenter().getY() - size), 
						new Point(balls[i].getCenter().getX() - size, balls[i].getCenter().getY() + size),
						new Point(balls[i].getCenter().getX() + size, balls[i].getCenter().getY() + size),
						new Point(balls[i].getCenter().getX() + size, balls[i].getCenter().getY() - size)};
				        
				for (int j=0; j < blocks.length; j++) {
					
					boolean intersected = false; 
					
					for (int k=0; k < 4; k++) {
						
						if(blocks[j].getTopLeft().getX() < corners[k].getX() &&
								blocks[j].getBottomRight().getX() > corners[k].getX() &&
								blocks[j].getBottomRight().getY() > corners[k].getY() &&
								blocks[j].getTopLeft().getY() < corners[k].getY()){
							
							intersected = true;
							break;
							}
					}
						
					if (intersected){
						newCrashBalls[i] = Intersect(balls[i], blocks[j]);
					}
					else {
						blocksNotHit.add(blocks[j]);
						BallState newBallState = new BallState(balls[i].getCenter(),
	                			balls[i].getDiameter(), 
	                			balls[i].getVelocity());
	    				newCrashBalls[i] = newBallState;
					}	
				}
		}
		BlockState[] newBlocks = new BlockState[blocksNotHit.size()];
		newBlocks = blocksNotHit.toArray(newBlocks);
		blocks = newBlocks;
		
		balls = newCrashBalls;
		}

		
		// Check if ball hits paddle, and bounce and speed it up if it does
		
		BallState[] newPaddleBalls = new BallState[balls.length];
		
		for (int i = 0; i < balls.length; i++) {
			if(balls[i] != null) {
				if((balls[i].getCenter().getX() > paddle.getTopLeft().getX() && balls[i].getCenter().getX() < paddle.getBottomRight().getX()) &&
						(balls[i].getCenter().getY() < paddle.getBottomRight().getY() && balls[i].getCenter().getY() > paddle.getTopLeft().getY())) {
					
					Vector newVelocity = balls[i].getVelocity().mirrorOver(Vector.DOWN).plus(new Vector(1/5 * paddleDir, 0));
					BallState newBallState = new BallState(balls[i].getCenter(), balls[i].getDiameter(), newVelocity);
					newPaddleBalls[i] = newBallState;
				}
				else {
					newPaddleBalls[i] = balls[i];
				}
			}
		}
		balls = newPaddleBalls;
		
				
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
