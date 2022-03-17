package breakout.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;

import breakout.BreakoutState;
import breakout.GameMap;

public class BreakoutApplication {

	public static final String initMap = """
##########
##########
##########
##########
     o

     =

""";
	
	public static void main(String[] args) {
		BreakoutState state = GameMap.createStateFromDescription(initMap);
		EventQueue.invokeLater(() -> {
			GameView mazeView = new GameView(state);
			JFrame frame = new JFrame("Pac-Man");
			frame.getContentPane().add(mazeView);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
