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
		
		// Test that the velocity is not changed after the tick method
		BallState[] MovedBalls = {new BallState(new Point(4,4), diameter, speed)};
		breakout.tick(0);
		assert breakout.getBalls()[0].getCenter().equals(MovedBalls[0].getCenter());
		assertEquals(breakout.getBalls()[0].getVelocity().getX(), MovedBalls[0].getVelocity().getX());
		assertEquals(breakout.getBalls()[0].getVelocity().getY(), MovedBalls[0].getVelocity().getY());
		
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
		breakoutNR.tick(0);
		assertEquals(breakoutNR.getBalls()[0].getVelocity().getX(), speed.mirrorOver(Vector.RIGHT).getX());
		assertEquals(breakoutNR.getBalls()[0].getVelocity().getY(), speed.mirrorOver(Vector.RIGHT).getY());
		
		// Test hits the left wall
		Point BallNL = new Point(2,2);
		Vector speedNL = new Vector(-3, 3);
		BallState[] ballsNL = {new BallState(BallNL, diameter, speedNL)};
		BreakoutState breakoutNL = new BreakoutState(ballsNL, blocks, BRMap, paddle);
		breakoutNL.tick(0);
		assertEquals(breakoutNL.getBalls()[0].getVelocity().getX(), speedNL.mirrorOver(Vector.LEFT).getX());
		assertEquals(breakoutNL.getBalls()[0].getVelocity().getY(), speedNL.mirrorOver(Vector.LEFT).getY());
		
		// Test hits the Top wall
		Point BallT = new Point(2,2);
		Vector speedT = new Vector(0, -4);
		BallState[] ballsT = {new BallState(BallT, diameter, speedT)};
		BreakoutState breakoutT = new BreakoutState(ballsT, blocks, BRMap, paddle);
		breakoutT.tick(0);
		assertEquals(breakoutT.getBalls()[0].getVelocity().getX(), speedT.mirrorOver(Vector.UP).getX());
		assertEquals(breakoutT.getBalls()[0].getVelocity().getY(), speedT.mirrorOver(Vector.UP).getY());		
		
		// Test hits the bottom of the field
		Point BallDown = new Point(8,8);
		Vector speedDown = new Vector(0, 4);
		
		BallState[] ballsDown = {new BallState(BallDown, diameter, speedDown)};
		BreakoutState breakoutDown = new BreakoutState(ballsDown, blocks, BRMap, paddle);
		
		breakoutDown.tick(0);
		
		assert(breakoutNR.getBalls().length == 1);
		assert(breakoutDown.getBalls().length == 0);
		

		// Checks if the game is won
		Point centerWon = new Point(5,6);
		Vector speedWon = new Vector(0,-5); 
		Point TLWon = new Point(2,0);
		Point BRWon = new Point(8,4);
		Point paddlecenterWon = new Point(9,9);
		Vector paddlesizeWon = new Vector(1,1);
		
		PaddleState paddleWon = new PaddleState(paddlecenterWon, paddlesizeWon);
		BallState[] ballsWon = {new BallState(centerWon, diameter, speedWon)};
		BlockState[] blocksWon = {new BlockState(TLWon, BRWon)}; 
		BreakoutState breakoutWon = new BreakoutState(ballsWon, blocksWon, BRMap, paddleWon);
		breakoutWon.tick(0);
		
		assert(breakoutWon.isWon() == true);
		
		// Checks that the game is not lost
		assert(breakoutWon.isDead() == false);
		
		// Check that the game is not won when there are no balls left.
		Vector speedNotWon = new Vector(0,5); 
		BallState[] ballsNotWon = {new BallState(centerWon, diameter, speedNotWon)};
		
		BreakoutState breakoutNotWon = new BreakoutState(ballsNotWon, blocksWon, BRMap, paddleWon);
		breakoutNotWon.tick(0);
		assert(breakoutNotWon.isWon() == false);
		
		// Chechs that the you are dead if the game is not won
		assert(breakoutNotWon.isDead() == true);

		// Tests that the ball gets mirrorer correctly afther intersection with block
		
		// Checks when hit from right
		int diameterTwo = 2;
		Point BRMap_Two = new Point(25,25);
		
		Point TL_Two = new Point(7,6);
		Point BR_Two = new Point(18,15);
		
		Point centerTwo = new Point(21, 8);
		Vector velocityTwo = new Vector(-5,5); 
		Point paddlecenterTwo = new Point(13,24);
		Vector paddlesizeTwo = new Vector(5,1);
		PaddleState paddleTwo = new PaddleState(paddlecenterTwo, paddlesizeTwo);
		
		
		BallState[] ballsTwo = {new BallState(centerTwo, diameterTwo, velocityTwo)};
		
		
		BlockState[] blocksTwo = {new BlockState(TL_Two, BR_Two)}; 
		BreakoutState breakoutTwo = new BreakoutState(ballsTwo, blocksTwo, BRMap_Two, paddleTwo);
		
		breakoutTwo.tick(0);
		assertEquals(breakoutTwo.getBalls()[0].getVelocity().getX(),velocityTwo.mirrorOver(Vector.LEFT).getX());
		assertEquals(breakoutTwo.getBalls()[0].getVelocity().getY(),velocityTwo.mirrorOver(Vector.LEFT).getY());
		
		
		// Checks when hit from left
		Vector velocityTwoLeft = new Vector(5,5);
		BallState[] ballsTwoLeft = {new BallState(new Point(4,8), diameterTwo, velocityTwoLeft)};
		BreakoutState breakoutTwoLeft = new BreakoutState(ballsTwoLeft, blocksTwo, BRMap_Two, paddleTwo);
		
		breakoutTwoLeft.tick(0);
		assertEquals(breakoutTwoLeft.getBalls()[0].getVelocity().getX(),velocityTwoLeft.mirrorOver(Vector.RIGHT).getX());
		assertEquals(breakoutTwoLeft.getBalls()[0].getVelocity().getY(),velocityTwoLeft.mirrorOver(Vector.RIGHT).getY());
		
		
		// Checks when hit from top
		Vector velocityTwoTop = new Vector(3,6);
		BallState[] ballsTwoTop = {new BallState(new Point(11,3), diameterTwo, velocityTwoTop)};
		BreakoutState breakoutTwoTop = new BreakoutState(ballsTwoTop, blocksTwo, BRMap_Two, paddleTwo);
		
		breakoutTwoTop.tick(0);
		assertEquals(breakoutTwoTop.getBalls()[0].getVelocity().getX(),velocityTwoTop.mirrorOver(Vector.DOWN).getX());
		assertEquals(breakoutTwoTop.getBalls()[0].getVelocity().getY(),velocityTwoTop.mirrorOver(Vector.DOWN).getY());
		

		// Checks when hit from bottom
		Vector velocityTwotBot = new Vector(0,-5);
		BallState[] ballsTwoBot = {new BallState(new Point(14,18), diameterTwo, velocityTwotBot)};
		BreakoutState breakoutTwoBot = new BreakoutState(ballsTwoBot, blocksTwo, BRMap_Two, paddleTwo);

		breakoutTwoBot.tick(0);
		assertEquals(breakoutTwoBot.getBalls()[0].getVelocity().getX(),velocityTwotBot.mirrorOver(Vector.UP).getX());
		assertEquals(breakoutTwoBot.getBalls()[0].getVelocity().getY(),velocityTwotBot.mirrorOver(Vector.UP).getY());
		
		
		// Cheks if last point is on the block
		Vector velocityTwoOn = new Vector(0,4);
		BallState[] ballsTwoOn = {new BallState(new Point(12,6), diameterTwo, velocityTwoOn)};
		BreakoutState breakoutTwoOn = new BreakoutState(ballsTwoOn, blocksTwo, BRMap_Two, paddleTwo);

		breakoutTwoOn.tick(0);
		assertEquals(breakoutTwoOn.getBalls()[0].getVelocity().getX(),velocityTwoOn.mirrorOver(Vector.DOWN).getX());
		assertEquals(breakoutTwoOn.getBalls()[0].getVelocity().getY(),velocityTwoOn.mirrorOver(Vector.DOWN).getY());
		
		// TODO: Make better names
		// Checks when hit from top after two steps
		Vector velocityTopA = new Vector(1,2);
		BallState[] ballsTopA = {new BallState(new Point(11,3), diameterTwo, velocityTopA)};
		
		Point paddlecenterTwoA = new Point(13,24);
		Vector paddlesizeTwoA = new Vector(5,1);
		PaddleState paddleTwoA = new PaddleState(paddlecenterTwoA, paddlesizeTwoA);
		
		Point TL_TwoA = new Point(7,6);
		Point BR_TwoA = new Point(18,15);
		BlockState[] blocksTwoA = {new BlockState(TL_TwoA, BR_TwoA)}; 
		
		
		BreakoutState breakoutTopA = new BreakoutState(ballsTopA, blocksTwoA, BRMap_Two, paddleTwoA);
		
		for(int i = 0; i < 3; ++i) {
			breakoutTopA.tick(0);
		}
		
		assertEquals(1,breakoutTopA.getBalls().length);
		assert(breakoutTopA.getBalls()[0] != null);
		assertEquals(0,breakoutTopA.getBlocks().length);
		
		assertEquals(14,breakoutTopA.getBalls()[0].getCenter().getX());
		assertEquals(1,breakoutTopA.getBalls()[0].getCenter().getY());
		
		assertEquals(1,breakoutTopA.getBalls()[0].getVelocity().getX());
		assertEquals(-2,breakoutTopA.getBalls()[0].getVelocity().getY());
		
		assertEquals(velocityTopA.mirrorOver(Vector.DOWN).getX(), breakoutTopA.getBalls()[0].getVelocity().getX());
		assertEquals(velocityTopA.mirrorOver(Vector.DOWN).getY(), breakoutTopA.getBalls()[0].getVelocity().getY());
		
		
		
		
		//Checks if ball hits paddle and if ball has sped up
		// Define objects 
		
		int diameterP = 2;
		Point BRMap_P = new Point(10,10);
		
		Point TL_B = new Point(0,0);
		Point BR_B = new Point(1,1);
		
		Point centerP = new Point(5, 5);
		Vector velocityP = new Vector(0,3); 
		Point paddlecenter_P = new Point(5,8);
		Vector paddlesize_P = new Vector(2,2);
		PaddleState paddle_P = new PaddleState(paddlecenter_P, paddlesize_P);
		BallState[] balls_P = {new BallState(centerP, diameterP, velocityP)};
		BlockState[] blocks_P = {new BlockState(TL_B, BR_B)}; 
		BreakoutState breakout_PL = new BreakoutState(balls_P, blocks_P, BRMap_P, paddle_P);
		int paddleDirLeft = -1;
		int paddleDirRight = 1;
		
		// Tests when paddle moves to the left
		breakout_PL.tick(paddleDirLeft);
		assertEquals(breakout_PL.getBalls()[0].getVelocity().getX(), velocityP.mirrorOver(Vector.DOWN).getX() + (1/5 * paddleDirLeft));
		assertEquals(breakout_PL.getBalls()[0].getVelocity().getY(), velocityP.mirrorOver(Vector.DOWN).getY());
		
		// Test when the paddle moves to the right
		BreakoutState breakout_PR = new BreakoutState(balls_P, blocks_P, BRMap_P, paddle_P);
		breakout_PR.tick(paddleDirRight);
		assertEquals(breakout_PR.getBalls()[0].getVelocity().getX(), velocityP.mirrorOver(Vector.DOWN).getX() + (1/5 * paddleDirRight));
		assertEquals(breakout_PR.getBalls()[0].getVelocity().getY(), velocityP.mirrorOver(Vector.DOWN).getY());
		
		
	}
	


}
