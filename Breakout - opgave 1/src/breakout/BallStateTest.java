package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class BallStateTest {

	@Test
	void test() {
		int diameter = 5;
		Point center = new Point(1,1);
		Vector speed = new Vector(3,4);
		BallState ball = new BallState(center, diameter, speed);
		
		assertEquals(ball.getCenter(), center);
		assertEquals(ball.getVelocity(), speed);
	}
	
	@Test
	void testTopLeft() {
		int diameter = 10;
		Point center = new Point(6,6);
		Vector speed = new Vector(3,4);
		BallState ball = new BallState(center, diameter, speed);
		
		assertEquals(new Point(1,1), ball.getTopLeft());
		assertEquals(new Point(11,11), ball.getBottomRight());
	}

}

