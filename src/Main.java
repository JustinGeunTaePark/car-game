
import java.awt.EventQueue;
/**
 * Driver class that runs the game
 * @author guerht
 *
 */
public class Main {

	/***
	 * Main application call thread
	 * @param args
	 * 				not used for this application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			StartMenu m = new StartMenu();
			m.setVisible(true);
		});
	}
}
