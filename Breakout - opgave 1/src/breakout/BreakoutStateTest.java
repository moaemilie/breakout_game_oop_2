package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BreakoutStateTest {

	@Test
	void test() {
		int diameter = 5;
		Point center = new Point(1,1);
		Vector speed = new Vector(3,4); 
		Point TL = new Point(5,5);
		Point BR = new Point(8,8);
		Point paddlecenter = new Point(10,10);
		Vector paddlesize = new Vector(3,4);
		
		BallState[] balls = {new BallState(center, diameter, speed), 
		                     //new BallState(center, diameter+1, speed)
				};
		BlockState[] blocks = {new BlockState(TL, BR)}; 
		PaddleState paddle = new PaddleState(paddlecenter, paddlesize);
		BreakoutState breakout = new BreakoutState(balls, blocks, BR, paddle);
		
		assert breakout.getBalls()[0].getCenter().equals(balls[0].getCenter());
		assert breakout.getBlocks()[0].getTopLeft().equals(blocks[0].getTopLeft());
		assert breakout.getBlocks()[0].getBottomRight().equals(blocks[0].getBottomRight());
		assertEquals(breakout.getPaddle(), paddle);
		assertEquals(breakout.getBottomRight(), BR);
		
		BallState[] newBalls = {new BallState(new Point(4,5), diameter, speed)};
		breakout.tick(2);
		assert breakout.getBalls()[0].getCenter().equals(newBalls[0].getCenter());
		assertEquals(breakout.getBalls()[0].getVelocity().getX(), newBalls[0].getVelocity().getX());
		assertEquals(breakout.getBalls()[0].getVelocity().getY(), newBalls[0].getVelocity().getY());
	
		
	}

}
