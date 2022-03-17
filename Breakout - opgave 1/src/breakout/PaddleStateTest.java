package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PaddleStateTest {

	@Test
	void test() {
		Point center = new Point(7,7);
		Vector size = new Vector(3,4);
		PaddleState paddle = new PaddleState(center, size);
	}

}
