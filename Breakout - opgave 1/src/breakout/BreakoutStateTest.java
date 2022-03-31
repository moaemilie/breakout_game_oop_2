package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BreakoutStateTest {

	@Test
	void test() {
		int diameter = 2;
		Point center = new Point(1,1);
		Vector speed = new Vector(3,3); 
		Point TL = new Point(8,0);
		Point BR = new Point(10,1);
		Point BRMap = new Point(10,10);
		Point paddlecenter = new Point(5,9);
		Vector paddlesize = new Vector(1,1);
		
		BallState[] balls = {new BallState(center, diameter, speed)};
		BlockState[] blocks = {new BlockState(TL, BR)}; 
		PaddleState paddle = new PaddleState(paddlecenter, paddlesize);
		BreakoutState breakout = new BreakoutState(balls, blocks, BRMap, paddle);
		
		// Test that the get methods
		assert breakout.getBalls()[0].getCenter().equals(balls[0].getCenter());
		assert breakout.getBlocks()[0].getTopLeft().equals(blocks[0].getTopLeft());
		assert breakout.getBlocks()[0].getBottomRight().equals(blocks[0].getBottomRight());
		assertEquals(breakout.getPaddle(), paddle);
		assertEquals(breakout.getBottomRight(), BRMap);
		
		// Test that the velocity is updated afther the tick method
		BallState[] newBalls = {new BallState(new Point(4,4), diameter, speed)};
		breakout.tick(2);
		assert breakout.getBalls()[0].getCenter().equals(newBalls[0].getCenter());
		assertEquals(breakout.getBalls()[0].getVelocity().getX(), newBalls[0].getVelocity().getX());
		assertEquals(breakout.getBalls()[0].getVelocity().getY(), newBalls[0].getVelocity().getY());
		
		// Test that the paddle is moved left and right afther the methodes is called
		Point paddle_center = breakout.getPaddle().getCenter();
		breakout.movePaddleRight();
		assertEquals(breakout.getPaddle().getCenter().getX(), paddle_center.getX()+10);
		
		Point old_paddle_center = breakout.getPaddle().getCenter();
		breakout.movePaddleLeft();
		assertEquals(breakout.getPaddle().getCenter().getX(), old_paddle_center.getX()-10);
		
		
		// Test the if the ball hits the walls/top
		// Test hits the right wall
		Point BallNR = new Point(9,1);
		BallState[] ballsNR = {new BallState(BallNR, diameter, speed)};
		BreakoutState breakoutNR = new BreakoutState(ballsNR, blocks, BRMap, paddle);
		breakoutNR.tick(1);
		assertEquals(breakoutNR.getBalls()[0].getVelocity().getX(), speed.mirrorOver(Vector.RIGHT).getX());
		assertEquals(breakoutNR.getBalls()[0].getVelocity().getY(), speed.mirrorOver(Vector.RIGHT).getY());
		
		// Test hits the left wall
		Point BallNL = new Point(2,2);
		Vector speedNL = new Vector(-3, 3);
		BallState[] ballsNL = {new BallState(BallNL, diameter, speedNL)};
		BreakoutState breakoutNL = new BreakoutState(ballsNL, blocks, BRMap, paddle);
		breakoutNL.tick(1);
		assertEquals(breakoutNL.getBalls()[0].getVelocity().getX(), speedNL.mirrorOver(Vector.LEFT).getX());
		assertEquals(breakoutNL.getBalls()[0].getVelocity().getY(), speedNL.mirrorOver(Vector.LEFT).getY());
		
		// Test hits the Top wall
		Point BallT = new Point(2,2);
		Vector speedT = new Vector(0, -4);
		BallState[] ballsT = {new BallState(BallT, diameter, speedT)};
		BreakoutState breakoutT = new BreakoutState(ballsT, blocks, BRMap, paddle);
		breakoutT.tick(1);
		assertEquals(breakoutT.getBalls()[0].getVelocity().getX(), speedT.mirrorOver(Vector.UP).getX());
		assertEquals(breakoutT.getBalls()[0].getVelocity().getY(), speedT.mirrorOver(Vector.UP).getY());
		
		
		// Test hits the botom of the feild
		Point BallDown = new Point(8,8);
		Vector speedDown = new Vector(0, 4);
		
		BallState[] ballsDown = {new BallState(BallDown, diameter, speedDown)};
		BreakoutState breakoutDown = new BreakoutState(ballsDown, blocks, BRMap, paddle);
		
		breakoutDown.tick(1);
		
		assert(breakoutNR.getBalls().length == 1);
		assert(breakoutDown.getBalls().length == 0);
		
		// Checks if the game is won
		BlockState[] blocksWon = {}; 
		BreakoutState breakoutWon = new BreakoutState(balls, blocksWon, BRMap, paddle);
		breakoutWon.tick(1);
		assert(breakoutWon.isWon() == true);
		
		// Check that the game is not won when there are no balls left.
		BallState[] ballsNotWon = {};
		BlockState[] blocksNotWon = {}; 
		BreakoutState breakoutNotWon = new BreakoutState(ballsNotWon, blocksNotWon, BRMap, paddle);
		breakoutNotWon.tick(1);
		assert(breakoutNotWon.isWon() == false);
		
		// Checks if the game is lost
		BallState[] ballsDead = {};
		BreakoutState breakoutDead = new BreakoutState(ballsDead, blocks, BRMap, paddle);
		breakoutDead.tick(1);
		assert(breakoutDead.isDead() == true);
		
		// Cheks that the game is not lost
		BallState[] ballsNotDead = {new BallState(center, diameter, speed)};
		BreakoutState breakoutNotDead = new BreakoutState(ballsNotDead, blocks, BRMap, paddle);
		breakoutNotDead.tick(1);
		assert(breakoutNotDead.isDead() == false);
	}
	
	@Test
	void checkIntersectionTest() {
		int diameter = 2;
		Point center = new Point(1,1);
		Vector speed = new Vector(3,3); 
		Point TL = new Point(8,0);
		Point BR = new Point(10,1);
		Point BRMap = new Point(10,10);
		Point paddlecenter = new Point(5,9);
		Vector paddlesize = new Vector(1,1);
		
		BallState[] balls = {new BallState(center, diameter, speed)};
		BlockState[] blocks = {new BlockState(TL, BR)}; 
		PaddleState paddle = new PaddleState(paddlecenter, paddlesize);
		BreakoutState breakout = new BreakoutState(balls, blocks, BRMap, paddle);
		
		//Point OldCenter = new Point(7,3);
		//Point NewCenter = new Point(8,2);
		//Point corner1 = new Point(4,8);
		//Point corner2 = new Point(4,1);
		//assert(breakout.checkIntersection(OldCenter, NewCenter, corner1, corner2)==true);
		
	}

}
