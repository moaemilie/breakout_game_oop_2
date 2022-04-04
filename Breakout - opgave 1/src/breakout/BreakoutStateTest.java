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
		
		// Test hits the bottom of the field
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
		
		// Checks that the game is not lost
		BallState[] ballsNotDead = {new BallState(center, diameter, speed)};
		BreakoutState breakoutNotDead = new BreakoutState(ballsNotDead, blocks, BRMap, paddle);
		breakoutNotDead.tick(1);
		assert(breakoutNotDead.isDead() == false);
		
		// Define objects for hitting blocks
		int diameterB = 2;
		Point TL_B = new Point(4,3);
		Point BR_B = new Point(7,6);
		Point BRMap_B = new Point(10,10);
		Point paddlecenter_B = new Point(5,10);
		Vector paddlesize_B = new Vector(1,1);
		PaddleState paddle_B = new PaddleState(paddlecenter_B, paddlesize_B);
		
		// Checks if ball is mirrored and block removed if ball hits left wall
		Point centerL = new Point(2, 5);
		Vector velocityL = new Vector(3,0);
		BallState[] balls_L = {new BallState(centerL, diameterB, velocityL)};
		BlockState[] blocks_L = {new BlockState(TL_B, BR_B)}; 
		BreakoutState breakout_L = new BreakoutState(balls_L, blocks_L, BRMap_B, paddle_B);
		
		breakout_L.tick(2);
		
		assert(breakout_L.getBalls().length != 0);
		//assertEquals(breakout_L.getBalls()[0].getVelocity().getX(), velocityL.mirrorOver(Vector.RIGHT).getX());
		//assertEquals(breakout_L.getBalls()[0].getVelocity().getY(), velocityL.mirrorOver(Vector.RIGHT).getY());
		assert(breakout_L.getBlocks().length == 0);
		
		// Checks if ball is mirrored and block removed if ball hits top wall
		Point centerT = new Point(6, 1);
		Vector velocityT = new Vector(0,3); 
		BallState[] balls_T = {new BallState(centerT, diameterB, velocityT)};
		BlockState[] blocks_T = {new BlockState(TL_B, BR_B)}; 
		BreakoutState breakout_T = new BreakoutState(balls_T, blocks_T, BRMap_B, paddle_B);
		
		breakout_T.tick(2);
		
		assert(breakout_T.getBalls().length != 0);
		assertEquals(breakout_T.getBalls()[0].getVelocity().getX(), velocityT.mirrorOver(Vector.DOWN).getX());
		assertEquals(breakout_T.getBalls()[0].getVelocity().getY(), velocityT.mirrorOver(Vector.DOWN).getY());
		assert(breakout_T.getBlocks().length == 0);

		// Checks if ball is mirrored and block removed if ball hits right wall of block
		Point centerR = new Point(9, 5);
		Vector velocityR = new Vector(-3,0); 
		BallState[] balls_R = {new BallState(centerR, diameterB, velocityR)};
		BlockState[] blocks_R = {new BlockState(TL_B, BR_B)}; 
		BreakoutState breakout_R = new BreakoutState(balls_R, blocks_R, BRMap_B, paddle_B);
		
		breakout_R.tick(2);
		
		assert(breakout_R.getBalls().length != 0);
		assertEquals(breakout_R.getBalls()[0].getVelocity().getX(), velocityR.mirrorOver(Vector.LEFT).getX());
		assertEquals(breakout_R.getBalls()[0].getVelocity().getY(), velocityR.mirrorOver(Vector.LEFT).getY());
		assert(breakout_R.getBlocks().length == 0);
		
		// Checks if ball is mirrored and block removed if ball hits bottom wall of block
		Point centerB = new Point(5, 7);
		Vector velocityB = new Vector(0,-3); 
		BallState[] balls_B = {new BallState(centerB, diameterB, velocityB)};
		BlockState[] blocks_B = {new BlockState(TL_B, BR_B)}; 
		BreakoutState breakout_B = new BreakoutState(balls_B, blocks_B, BRMap_B, paddle_B);
		
		breakout_B.tick(2);
		
		assert(breakout_B.getBalls().length != 0);
		assertEquals(breakout_B.getBalls()[0].getVelocity().getX(), velocityB.mirrorOver(Vector.UP).getX()); // Blir (-1,-3)
		assertEquals(breakout_B.getBalls()[0].getVelocity().getY(), velocityB.mirrorOver(Vector.UP).getY());
		assert(breakout_B.getBlocks().length == 0);
		
		//Checks if ball hits paddle and if ball has sped up
		Point centerP = new Point(5, 8);
		Vector velocityP = new Vector(0,1); 
		Point paddlecenter_P = new Point(5,10);
		Vector paddlesize_P = new Vector(3,1);
		PaddleState paddle_P = new PaddleState(paddlecenter_P, paddlesize_P);
		BallState[] balls_P = {new BallState(centerP, diameterB, velocityP)};
		BlockState[] blocks_P = {new BlockState(TL_B, BR_B)}; 
		BreakoutState breakout_P = new BreakoutState(balls_P, blocks_P, BRMap_B, paddle_P);
		
		breakout_P.tick(2);
		
		//assert(breakout_P.getBalls()[0].getVelocity().getX() < velocityP.getX());
		assertEquals(breakout_P.getBalls()[0].getVelocity().getX(), velocityP.getX());
		assertEquals(breakout_P.getBalls()[0].getVelocity().getY(), velocityP.getY());
		
		breakout_P.tick(2);
		//assertEquals(breakout_P.getBalls()[0].getVelocity().getX(), velocityP.getX());
		//assertEquals(breakout_P.getBalls()[0].getVelocity().getY(), velocityP.getY());
		
		
		// Test the intersection method
		int x_first = 3;
		int y_first = 1;
		int x_afther = 5;
		int y_afther = 3; 
		int velocityX = 2;
		int velocityY = 2;
		int topLeft_x = 2;
		int topLeft_y = 2;
		int BottomRight_x = 6;
		int BottomRight_y = 6;
		
		Point TopLeftInter = new Point(topLeft_x, topLeft_y);
		Point BottomRightInter = new Point(BottomRight_x, BottomRight_y);
		BlockState[] blockInter = {new BlockState(TopLeftInter, BottomRightInter)};
		
		Point centerInterTop = new Point(x_first, y_first);
		Vector centerInterTopVel = new Vector(velocityX, velocityY);
		BallState[] interTopBall = {new BallState(centerInterTop, 1,centerInterTopVel)};
		
		BreakoutState interTop = new BreakoutState(interTopBall, blockInter, BRMap, paddle);
		
		Vector TestVectorTop = new Vector(2,2);
		
		interTop.tick(1);
		assertEquals(interTop.getBalls()[0].getCenter().getX(), 4);
		assertEquals(interTop.getBalls()[0].getCenter().getY(), 2);
		assertEquals(interTop.getBalls()[0].getVelocity().getX(), TestVectorTop.mirrorOver(Vector.DOWN).getX());
		assertEquals(interTop.getBalls()[0].getVelocity().getY(), TestVectorTop.mirrorOver(Vector.DOWN).getY());
		
		
		
		Point centerInterLeft = new Point(1, 3);
		Vector centerInterLeftVel = new Vector(2, 2);
		BallState[] interLeftBall = {new BallState(centerInterLeft, 1, centerInterLeftVel)};
		
		BreakoutState interLeft = new BreakoutState(interLeftBall, blockInter, BRMap, paddle);
		
		Vector TestVectorLeft = new Vector(2,2);
		
		interLeft.tick(1);
		assertEquals(interLeft.getBalls()[0].getCenter().getX(), 2);
		assertEquals(interLeft.getBalls()[0].getCenter().getY(), 4);
		assertEquals(interLeft.getBalls()[0].getVelocity().getX(), TestVectorLeft.mirrorOver(Vector.RIGHT).getX());
		assertEquals(interLeft.getBalls()[0].getVelocity().getY(), TestVectorLeft.mirrorOver(Vector.RIGHT).getY());
		
		/*
		 * Intersect interTop = new Intersect(x_first, y_first, x_afther, y_afther,
		 * velocityX, velocityY, topLeft_x, topLeft_y, BottomRight_x, BottomRight_y);
		 * assertEquals(interTop.WhereIntersect(), 4);
		 * 
		 * Intersect interLeft = new Intersect(1, 3, 4, 5, 2, 3, 2, 2, 6, 6);
		 * assertEquals(interLeft.WhereIntersect(), 3);
		 * 
		 * 
		 * Intersect interRight = new Intersect(7, 5, 5, 3, -2, -2, 2, 2, 6, 6);
		 * assertEquals(interRight.WhereIntersect(), 4);
		 * 
		 * Intersect interBottomRight = new Intersect(6, 8, 4, 4, -2, -4, 2, 2, 6, 7);
		 * assertEquals(interBottomRight.GetA(), 0);
		 * assertEquals(interBottomRight.WhereIntersect(), 7);
		 * 
		 * Intersect interPlusMinus = new Intersect(7, 6, 5, 3, 1, -1, 2, 2, 6, 6);
		 * assertEquals(interPlusMinus.GetA(), 0);
		 * assertEquals(interPlusMinus.WhereIntersect(), 6);
		 * 
		 * Intersect interVertRight = new Intersect(4, 1, 4, 4, 0, 4, 2, 2, 6, 6);
		 * assertEquals(interVertRight.WhereIntersect(), 4);
		 * 
		 * Intersect interHorDown = new Intersect(9, 4, 5, 4, -4, 0, 2, 2, 6, 6);
		 * assertEquals(interHorDown.WhereIntersect(), 4);
		 * 
		 * 
		 * 
		 * Intersect interHorUp = new Intersect(4, 7, 4, 5, 0, -2, 2, 2, 6, 6);
		 * assertEquals(interHorUp.WhereIntersect(), 4);
		 * 
		 * Intersect interVertLeft = new Intersect(1, 4, 4, 4, 3, 0, 2, 2, 6, 6);
		 * assertEquals(interVertLeft.WhereIntersect(), 4);
		 * 
		 * Intersect interBottomLeft = new Intersect(2, 8, 5, 5, 3, -3, 2, 2, 6, 6);
		 * assertEquals(interBottomLeft.GetA(), -1);
		 * assertEquals(interBottomLeft.WhereIntersect(), 4);
		 * 
		 * Intersect interTopRound = new Intersect(3, 0, 4, 4, 1, 4, 2, 2, 6, 6);
		 * assertEquals(interTopRound.WhereIntersect(), 2);
		 * 
		 * 
		 * 
		 * Intersect interTopRight = new Intersect(6, 1, 4, 3, -2, 2, 2, 2, 6, 6);
		 * assertEquals(interTopRight.WhereIntersect(), 5);
		 * 
		 * Intersect interRightLeft = new Intersect(7, 3, 5, 5, -2, 2, 2, 2, 6, 6);
		 * assertEquals(interRightLeft.WhereIntersect(), 4);
		 * 
		 * Intersect interBottomLeftNeg = new Intersect(5, 7, 3, 5, -2, -2, 2, 2, 6, 6);
		 * assertEquals(interBottomLeftNeg.WhereIntersect(), 4);
		 * 
		 * Intersect interLeftRight = new Intersect(1, 5, 3, 3, 2, -2, 2, 2, 6, 6);
		 * assertEquals(interLeftRight.WhereIntersect(), 4);
		 * 
		 * Intersect interWrong = new Intersect(1, 5, 3, 3, 0, 0, 2, 2, 6, 6);
		 * assertEquals(interWrong.WhereIntersect(), 0);
		 */
		
	}
	


}
