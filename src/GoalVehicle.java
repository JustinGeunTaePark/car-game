import java.awt.Image;
import java.awt.Point;
/**
 * The Red Vehicle in the game, denoted as a goal vehicle.
 * The goal vehicle has a goal point, which defines the goal state of a puzzle
 * @author guerht
 * @invariant goalPoint != null
 */
public class GoalVehicle extends HorizontalVehicle {
	// position where the GoalVehicle should be for the game to end.
	private Point goalPoint;
	/**
	 * Inherited Constructor that takes in an additional parameter of goalPoint
	 * @param p
	 * 			p != null && p.y == 2
	 * @param goalPoint
	 * 			goalPoint != null && goalPoint.x == 4 && goalPoint.y == 2
	 * @param width
	 * 			width == 2
	 * @param imagePath
	 * 			imagePath != null && fileTo imagePath exists
	 * @param click
	 * 				click != null
	 * @param drag
	 * 				drag != null
	 * @param release
	 * 				release != null
	 */
	public GoalVehicle(Point p, Point goalPoint, int width, String imagePath, String soundPath) {
		super(p, width, imagePath, soundPath);
		this.goalPoint = goalPoint;
	}
	/**
	 * Inherited Constructor that takes in an additional parameter of goalPoint
	 * @param p
	 * 			p != null && p.y == 2
	 * @param goalPoint
	 * 			goalPoint != null && goalPoint.x == 4 && goalPoint.y == 2
	 * @param width
	 * 			width == 2
	 * @param imagePath
	 * 			imagePath != null && fileTo imagePath exists
	 * @param image
	 * 			image != null
	 * @param click
	 * 				click != null
	 * @param drag
	 * 				drag != null
	 * @param release
	 * 				release != null
	 */
	public GoalVehicle(Point p, Point goalPoint, int width, String imagePath, Image image, String soundPath, SoundEffect drag) {
		super(p, width, imagePath, image, soundPath, drag);
		this.goalPoint = goalPoint;
	}
	/**
	 * Checks if current position of goalVehicle is in goalPoint
	 * @return true if current point == goal point, false otherwise
	 */
	public boolean isSolved() {
		return goalPoint.equals(this.currentLocation);
	}
	
	@Override
	public Vehicle clone() {
		return new GoalVehicle((Point) this.currentLocation.clone(), this.goalPoint, this.width, this.imagePath, this.image, this.soundPath, this.drag);
	}
}

