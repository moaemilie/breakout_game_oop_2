package breakout;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMap {
	
	private static final int INIT_BALL_DIAMETER = 700;
	private static final int HEIGHT = 30000;
	private static final int WIDTH = 50000;
	private static int BLOCK_LINES = 9;
	private static int BLOCK_COLUMNS = 10;
	private static final Vector INIT_BALL_VELOCITY = new Vector(5,7);

	private static BlockState createBlock(Point topLeft) {
		Vector marginBL = new Vector(20,20);
		Vector size = new Vector(WIDTH/BLOCK_COLUMNS-70,HEIGHT/BLOCK_LINES-70);
		Point blockTL = topLeft.plus(marginBL);
		Point blockBR = blockTL.plus(size);
		return new BlockState(blockTL, blockBR);
	}
	private static PaddleState createPaddle(Point topLeft) {
		Vector size = new Vector(WIDTH/BLOCK_COLUMNS/2,HEIGHT/BLOCK_LINES/2);
		Point center = topLeft.plus(size);
		return new PaddleState(center, size);
	}
	private static BallState createBall(Point topLeft) {
		Vector centerD = new Vector(WIDTH/BLOCK_COLUMNS/2,HEIGHT/BLOCK_LINES/2);
		Point center = topLeft.plus(centerD);
		int diameter = INIT_BALL_DIAMETER;
		return new BallState(center, diameter, INIT_BALL_VELOCITY);
	}
		
	/**
	 * Return the initial breakout state represented by string `description`.
	 * @pre | description != null
	 * @post | result != null
	 */
	public static BreakoutState createStateFromDescription(String description) {
		String[] lines = description.split("\n", BLOCK_LINES);
		
		Vector unitVecRight = new Vector(WIDTH/BLOCK_COLUMNS,0);
		Vector unitVecDown = new Vector(0,HEIGHT/BLOCK_LINES);
		BlockState[] blocks = new BlockState[BLOCK_COLUMNS*BLOCK_LINES];
		int nblock = 0;
		BallState[] balls = new BallState[BLOCK_COLUMNS*BLOCK_LINES];
		int nball = 0;
		PaddleState paddle = null;
		
		Point topLeft = new Point(0,0);
		assert lines.length <= BLOCK_LINES;
		for(String line : lines) {
			assert line.length() <= BLOCK_COLUMNS;
			Point cursor = topLeft;
			for(char c : line.toCharArray()) {
				switch(c) {
				case '#': blocks[nblock++] = createBlock(cursor); break;
				case 'o': balls[nball++] = createBall(cursor); break;
				case '=': paddle = createPaddle(cursor); break;
				}
				cursor = cursor.plus(unitVecRight);
			}
			topLeft = topLeft.plus(unitVecDown);
		}
		Point bottomRight = new Point(WIDTH, HEIGHT);
		
		return new BreakoutState(Arrays.stream(balls).filter(x -> x != null).toArray(BallState[]::new),
								 Arrays.stream(blocks).filter(x -> x != null).toArray(BlockState[]::new),
								 bottomRight, paddle);
	}
}