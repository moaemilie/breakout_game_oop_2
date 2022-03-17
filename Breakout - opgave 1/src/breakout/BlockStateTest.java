package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BlockStateTest {

	@Test
	void test() {
		Point top_left = new Point(0,0);
		Point bottom_right = new Point(4,4);
		BlockState block = new BlockState(top_left, bottom_right);
	}

}
