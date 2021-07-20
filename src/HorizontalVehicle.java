import java.awt.Image;
import java.awt.Point;

/**
 * Concrete instance of the Vehicle class
 * Inherits from the Vehicle class
 * @author guerht
 * @invariant height == 1
 */
public class HorizontalVehicle extends Vehicle {

	/**
	 * Standard constructor that instantiates the vehicle with the given parameters
	 * @param p
	 * 			p != null
	 * @param width
	 * 				width == 2 || width == 3
	 * @param imagePath
	 * 				imagePath != null
	 * @param click
	 * 				click != null
	 * @param drag
	 * 				drag != null
	 * @param release
	 * 				release != null
	 */
	public HorizontalVehicle(Point p, int width, String imagePath, String soundPath) {
		super(p, width, 1, imagePath, soundPath);
	}
	
	/**
	 * Extended constructor that instantiates the vehicle with the given parameters
	 * @param p
	 * 			p != null
	 * @param width
	 * 				width == 2 || width == 3
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
	public HorizontalVehicle(Point p, int width, String imagePath, Image image, String soundPath, SoundEffect drag) {
		super(p, width, 1, imagePath, image, soundPath, drag);
	}

	@Override
	public Point nextPosition(Point p) {
		
		int newX = p.getX() > this.currentLocation.getX()
					? this.currentLocation.x + this.MAX_MOVEMENT
					: this.currentLocation.x - this.MAX_MOVEMENT;
		int newY = (int) this.currentLocation.getY();

		return new Point(newX, newY);
	}
	
	@Override
	public Vehicle clone() {
		return new HorizontalVehicle((Point) this.currentLocation.clone(), this.width, this.imagePath, this.image, this.soundPath, this.drag);
	}
}
