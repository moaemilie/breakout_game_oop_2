package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PaddleStateTest {

	@Test
	void test() {
		Point center = new Point(7,7);
		Vector size = new Vector(4,4);
		PaddleState paddle = new PaddleState(center, size);
		
		assertEquals(paddle.getBottomRight().getX(), 11);
		assertEquals(paddle.getBottomRight().getY(), 11);
		assertEquals(paddle.getTopLeft().getX(), 3);
		assertEquals(paddle.getTopLeft().getX(), 3);
		assert(paddle.getTopLeft().isUpAndLeftFrom(paddle.getBottomRight()));
		
		assertEquals(paddle.getCenter(), center);
	}

}
