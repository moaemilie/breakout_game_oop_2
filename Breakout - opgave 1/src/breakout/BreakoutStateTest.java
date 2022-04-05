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
		
		// Test that the velocity is updated after the tick method
		BallState[] newBalls = {new BallState(new Point(4,4), diameter, speed)};
		breakout.tick(0);
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

		
		// This can be erased....
		// Test the intersection method
		/*
		 * int x_first = 3; int y_first = 1; int velocityX = 2; int velocityY = 2; int
		 * topLeft_x = 2; int topLeft_y = 2; int BottomRight_x = 6; int BottomRight_y =
		 * 6;
		 * 
		 * Point TopLeftInter = new Point(topLeft_x, topLeft_y); Point BottomRightInter
		 * = new Point(BottomRight_x, BottomRight_y); BlockState[] blockInter = {new
		 * BlockState(TopLeftInter, BottomRightInter)};
		 * 
		 * Point centerInterTop = new Point(x_first, y_first); Vector centerInterTopVel
		 * = new Vector(velocityX, velocityY); BallState[] interTopBall = {new
		 * BallState(centerInterTop, 1,centerInterTopVel)};
		 * 
		 * BreakoutState interTop = new BreakoutState(interTopBall, blockInter, BRMap,
		 * paddle);
		 * 
		 * Vector TestVectorTop = new Vector(2,2);
		 * 
		 * interTop.tick(0); assertEquals(interTop.getBalls()[0].getCenter().getX(), 4);
		 * assertEquals(interTop.getBalls()[0].getCenter().getY(), 2);
		 * assertEquals(interTop.getBalls()[0].getVelocity().getX(),
		 * TestVectorTop.mirrorOver(Vector.DOWN).getX());
		 * assertEquals(interTop.getBalls()[0].getVelocity().getY(),
		 * TestVectorTop.mirrorOver(Vector.DOWN).getY());
		 * assert(interTop.getBlocks().length == 0);
		 * 
		 * 
		 * 
		 * Point centerInterLeft = new Point(1, 3); Vector centerInterLeftVel = new
		 * Vector(2, 2); BallState[] interLeftBall = {new BallState(centerInterLeft, 1,
		 * centerInterLeftVel)};
		 * 
		 * BreakoutState interLeft = new BreakoutState(interLeftBall, blockInter, BRMap,
		 * paddle);
		 * 
		 * Vector TestVectorLeft = new Vector(2,2);
		 * 
		 * interLeft.tick(0); assertEquals(interLeft.getBalls()[0].getCenter().getX(),
		 * 2); assertEquals(interLeft.getBalls()[0].getCenter().getY(), 4);
		 * assertEquals(interLeft.getBalls()[0].getVelocity().getX(),
		 * TestVectorLeft.mirrorOver(Vector.RIGHT).getX());
		 * assertEquals(interLeft.getBalls()[0].getVelocity().getY(),
		 * TestVectorLeft.mirrorOver(Vector.RIGHT).getY());
		 * assert(interLeft.getBlocks().length == 0);
		 * 
		 * 
		 * // Line vertical down with crossing on the top. Point centerDown= new
		 * Point(4, 1); Vector centerDownVel = new Vector(0, 4); BallState[]
		 * interDownBall = {new BallState(centerDown, 1, centerDownVel)};
		 * 
		 * BreakoutState interDown = new BreakoutState(interDownBall, blockInter, BRMap,
		 * paddle);
		 * 
		 * Vector TestVectorDown = new Vector(0, 4);
		 * 
		 * interDown.tick(0); assertEquals(interDown.getBalls()[0].getCenter().getX(),
		 * 4); assertEquals(interDown.getBalls()[0].getCenter().getY(), 2);
		 * assertEquals(interDown.getBalls()[0].getVelocity().getX(),
		 * TestVectorDown.mirrorOver(Vector.UP).getX());
		 * assertEquals(interDown.getBalls()[0].getVelocity().getY(),
		 * TestVectorDown.mirrorOver(Vector.UP).getY());
		 * assert(interDown.getBlocks().length == 0);
		 * 
		 * 
		 * // Line horisontal to the left. Point centerHor= new Point(9, 4); Vector
		 * centerHorVel = new Vector(-4, 0); BallState[] interHorBall = {new
		 * BallState(centerHor, 1, centerHorVel)};
		 * 
		 * BreakoutState interHor = new BreakoutState(interHorBall, blockInter, BRMap,
		 * paddle);
		 * 
		 * Vector TestVectorHor = new Vector(-4, 0);
		 * 
		 * interHor.tick(0); assertEquals(interHor.getBalls()[0].getCenter().getX(), 6);
		 * assertEquals(interHor.getBalls()[0].getCenter().getY(), 4);
		 * assertEquals(interHor.getBalls()[0].getVelocity().getX(),
		 * TestVectorHor.mirrorOver(Vector.RIGHT).getX());
		 * assertEquals(interHor.getBalls()[0].getVelocity().getY(),
		 * TestVectorHor.mirrorOver(Vector.RIGHT).getY());
		 * assert(interHor.getBlocks().length == 0);
		 * 
		 * // Cheks when velocity vector is negative in both directions Point centerNeg=
		 * new Point(5, 7); Vector negVel = new Vector(-2, -2); BallState[] interNegBall
		 * = {new BallState(centerNeg, 1, negVel)};
		 * 
		 * PaddleState paddleNeg = new PaddleState(new Point(9,9), paddlesize);
		 * 
		 * BreakoutState interNeg = new BreakoutState(interNegBall, blockInter, BRMap,
		 * paddleNeg);
		 * 
		 * Vector TestVectorNeg = new Vector(-2, -2);
		 * 
		 * interNeg.tick(0); assertEquals(interNeg.getBalls()[0].getCenter().getX(), 4);
		 * assertEquals(interNeg.getBalls()[0].getCenter().getY(), 6);
		 * assertEquals(interNeg.getBalls()[0].getVelocity().getX(),
		 * TestVectorNeg.mirrorOver(Vector.UP).getX());
		 * assertEquals(interNeg.getBalls()[0].getVelocity().getY(),
		 * TestVectorNeg.mirrorOver(Vector.UP).getY());
		 * assert(interNeg.getBlocks().length == 0);
		 */
		
		
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
