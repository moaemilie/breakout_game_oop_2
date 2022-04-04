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
	

	//TODO: document this
	//TODO: write tests
	/**
	 * This method checks if two objects intersect.
	 * 
	 * @mutates | this
	 */
public BallState Intersect(BallState ball, BlockState block) {
	
		BallState ballAftherHit;
		Vector normal = new Vector(0,0);
		Point centerAftherHit = new Point(ball.getCenter().getX(), ball.getCenter().getY());
		
		int x_first = ball.getCenter().getX() - ball.getVelocity().getX();
		int y_first = ball.getCenter().getY() - ball.getVelocity().getY();
		int x_afther = ball.getCenter().getX();
		int y_afther =  ball.getCenter().getY();
		int velocityX = ball.getVelocity().getX();
		int velocityY = ball.getVelocity().getY();
		int topLeft_x = block.getTopLeft().getX();
		int topLeft_y = block.getTopLeft().getY();
		int BottomRight_x = block.getBottomRight().getX();
		int BottomRight_y = block.getBottomRight().getY();
		int a;
		
		if (y_afther == y_first || x_afther == x_first) {
			a = 0;
			
			if(velocityX == 0 && velocityY != 0) {
				if(velocityY < 0) {
					normal = Vector.UP; // It has hit the bottom
					centerAftherHit = new Point(x_afther, BottomRight_y);
				}
				else if(velocityY > 0) {
					normal = Vector.DOWN; // It has hit the top
					centerAftherHit = new Point(x_afther, topLeft_y);
				}
			}
			
			else {
				if(velocityX < 0){
					centerAftherHit = new Point(BottomRight_x ,y_afther); // It has hit the right wall
					normal = Vector.LEFT;
				}
				else if(velocityX > 0) {
					centerAftherHit = new Point(topLeft_x ,y_afther); // It has hit the left wall
					normal = Vector.RIGHT;
				}
			}
		}
		
		else {
			a = (x_afther - x_first)/(y_afther - y_first);
			
			if(a == 0 && velocityX != 0 && velocityY != 0) {

					if(velocityY > 0) {
						centerAftherHit = new Point(x_afther ,topLeft_y); // It has hit the top
						normal = Vector.DOWN;
					}
					if(velocityY < 0) {
						centerAftherHit = new Point(x_afther ,BottomRight_y); // It has hit the bottom
						normal = Vector.UP;
					}
			}
		}
		
		
		int b = y_afther - a * x_afther;
		
		if(velocityX > 0 && velocityY != 0) {
			if(velocityY > 0) {
				int cross_top_x = (topLeft_y - b)/a;
				int cross_left_y = a*topLeft_x + b;
				
				if(topLeft_x < cross_top_x && cross_top_x < BottomRight_x) {
					centerAftherHit = new Point(cross_top_x , topLeft_y); // It has then hit the top
					normal = Vector.DOWN;
					}
				else {
					centerAftherHit = new Point(topLeft_x , cross_left_y); // It had hit the left wall	
					normal = Vector.RIGHT;
				}
			}
			else if(velocityY < 0) {
				int cross_left_y = a*topLeft_x + b;
				int cross_bottom_x = (BottomRight_y - b)/a;
				
				if(topLeft_x < cross_bottom_x && cross_bottom_x < BottomRight_x) {
					centerAftherHit = new Point(cross_bottom_x , BottomRight_y); // It has then hit the bottom
					normal = Vector.UP;
					}
				else {
					centerAftherHit = new Point(topLeft_x , cross_left_y); // It had hit the left wall
					normal = Vector.RIGHT;
				}
			}

			}
		else if(velocityX < 0 && velocityY != 0){
			
			if(velocityY > 0) {
				int cross_top_x = (topLeft_y - b)/a;
				int cross_right_y = a*BottomRight_x + b;
				
				if(topLeft_x < cross_top_x && cross_top_x < BottomRight_x) {
					centerAftherHit = new Point(cross_top_x , topLeft_y); // It has then hit the top
					normal = Vector.DOWN;
					}
				else {
					centerAftherHit = new Point(BottomRight_x , cross_right_y); // It had hit the right wall
					normal = Vector.LEFT;
				}
			}
			else if(velocityY < 0) {
				int cross_right_y = a*BottomRight_x + b;
				int cross_bottom_x = (BottomRight_y - b)/a;
				
				if(topLeft_x < cross_bottom_x && cross_bottom_x < BottomRight_x) {
					centerAftherHit = new Point(cross_bottom_x , BottomRight_y); // It has then hit the bottom
					normal = Vector.UP;
					}
				else {
					centerAftherHit = new Point(BottomRight_x , cross_right_y); // It had hit the right wall	
					normal = Vector.LEFT;
				}
			}
			}
		
		return ballAftherHit = new BallState(centerAftherHit, ball.getDiameter(), ball.getVelocity().mirrorOver(normal));
	}

	
	//TODO: less ambigous check of block
	//TODO: check paddle
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
			
			int newCenterX = balls[i].getCenter().getX();
			int newCenterY = balls[i].getCenter().getY();
			int oldCenterX = balls[i].getCenter().getX() - balls[i].getVelocity().getX();
			int oldCenterY = balls[i].getCenter().getY() - balls[i].getVelocity().getY();
			int topLeftX = Math.min(oldCenterX, newCenterX);
			int topLeftY = Math.min(oldCenterY, newCenterY);
			int width = Math.abs(balls[i].getVelocity().getX());
	        int height = Math.abs(balls[i].getVelocity().getY());
	        
			for (int j=0; j < blocks.length; j++) {
				
				if(blocks[j].getTopLeft().getX() < (balls[i].getCenter().getX() + (balls[i].getDiameter()/2)) &&
						(balls[i].getCenter().getX() + (balls[i].getDiameter()/2)) < blocks[j].getBottomRight().getX() &&
						blocks[j].getBottomRight().getY() > (balls[i].getCenter().getY() + (balls[i].getDiameter()/2)) &&
						(balls[i].getCenter().getY() + (balls[i].getDiameter()/2)) > blocks[j].getTopLeft().getY()){
					
					// check left wall of block
					if (balls[i].getVelocity().product(Vector.RIGHT) > 0){
						BallState newBallState = new BallState(
								balls[i].getCenter(),
	                			balls[i].getDiameter(), 
	                			balls[i].getVelocity().mirrorOver(Vector.RIGHT));
	    				newCrashBalls[i] = newBallState;
					}
					
					// check top wall
					else if (balls[i].getVelocity().product(Vector.DOWN) > 0){
							BallState newBallState = new BallState(
									balls[i].getCenter(),
		                			balls[i].getDiameter(), 
		                			balls[i].getVelocity().mirrorOver(Vector.DOWN));
		    				newCrashBalls[i] = newBallState;
						}
					
					// check right wall
					else if (balls[i].getVelocity().product(Vector.LEFT) > 0){
							BallState newBallState = new BallState(
									balls[i].getCenter(),
		                			balls[i].getDiameter(), 
		                			balls[i].getVelocity().mirrorOver(Vector.LEFT));
		    				newCrashBalls[i] = newBallState;
						}
					
					// check bottom wall
					else if (balls[i].getVelocity().product(Vector.UP) > 0){
							BallState newBallState = new BallState(
									balls[i].getCenter(),
		                			balls[i].getDiameter(), 
		                			balls[i].getVelocity().mirrorOver(Vector.UP));
		    				newCrashBalls[i] = newBallState;
						}
					
					
					else {
						blocksNotHit.add(blocks[i]);
						BallState newBallState = new BallState(balls[i].getCenter(),
	                			balls[i].getDiameter(), 
	                			balls[i].getVelocity());
	    				newCrashBalls[i] = newBallState;
					}		
				}
				else {
					blocksNotHit.add(blocks[i]);
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

		// Check if ball hits paddle, and bounce and speed it up if it does
		
		//TODO: implement
		//TODO: Make test run 
		//TODO: test
		BallState[] newPaddleBalls = new BallState[balls.length];
		
		for (int i = 0; i < balls.length; i++) {	
			if((balls[i].getVelocity().getX() > paddle.getTopLeft().getX() && balls[i].getVelocity().getX() < paddle.getBottomRight().getX()) &&
					(balls[i].getVelocity().getY() > paddle.getBottomRight().getY() && balls[i].getVelocity().getY() < paddle.getTopLeft().getY())) {
				
				
				Vector newVelocity = balls[i].getVelocity().mirrorOver(Vector.DOWN).plus(new Vector(1/5 * paddleDir, 0));
				BallState newBallState = new BallState(balls[i].getCenter(), balls[i].getDiameter(), newVelocity);
				newPaddleBalls[i] = newBallState;
			}
			else {
				newPaddleBalls[i] = balls[i];
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
