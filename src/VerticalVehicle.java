import java.awt.Image;
import java.awt.Point;

/**
 * Concrete instance of the Vehicle class
 * Inherits from the Vehicle class
 * @author guerht
 * @invariant width == 1
 */
public class VerticalVehicle extends Vehicle {

	/**
	 * Standard constructor that instantiates the vehicle with the given parameters
	 * @param p
	 * 			p != null
	 * @param height
	 * 				height == 2 || height == 3
	 * @param imagePath
	 * 				imagePath != null
	 * @param click
	 * 				click != null
	 * @param drag
	 * 				drag != null
	 * @param release
	 * 				release != null
	 */
	public VerticalVehicle(Point p, int height, String imagePath, String soundPath) {
		super(p, 1, height, imagePath, soundPath);
	}
	
	/**
	 * Extended constructor that instantiates the vehicle with the given parameters
	 * @param p
	 * 			p != null
	 * @param height
	 * 				height == 2 || height == 3
	 * @param imagePath
	 * 				imagePath != null
	 * @param image
	 * 				image != null
	 * @param click
	 * 				click != null
	 * @param drag
	 * 				drag != null
	 * @param release
	 * 				release != null
	 */
	public VerticalVehicle(Point p, int height, String imagePath, Image image, String soundPath, SoundEffect drag) {
		super(p, 1, height, imagePath, image, soundPath, drag);
	}
	
	@Override
	public Point nextPosition(Point p) {

		int newX = (int) this.currentLocation.getX();
		int newY = p.getY() > this.currentLocation.getY()
					? this.currentLocation.y + this.MAX_MOVEMENT
					: this.currentLocation.y - this.MAX_MOVEMENT;

		return new Point(newX, newY);
	}
	

	@Override
	public Vehicle clone() {
		return new VerticalVehicle((Point) this.currentLocation.clone(), this.height, this.imagePath, this.image, this.soundPath, this.drag);
	}
}
