import java.awt.Image;
import java.awt.Point;
/**
 * Vehichles / Blocks of the gridlock game
 * @author guerht
 * @invariant currentLoaction != null && height >= 1 && height <= 3 && width >= 1 && width <= 3 && imagePath != null
 */
public abstract class Vehicle {
	
	protected int MAX_MOVEMENT = 1;

	// a grid reference not related to the gui
	// this refers to the top most or left most grid point
	protected Point currentLocation;

	// size represents the length of the vehicle that starts from rowPosition
	// and columnPosition
	protected int height;
	protected int width;

	protected Image image;
	protected String imagePath;
	protected String soundPath;
	
	protected SoundEffect drag;
	
	/**
	 * Standard constructor that instantiates a vehicle
	 * @param p
	 * 			p != null && p is a valid point in the grid
	 * @param width
	 * 			width >= 1 && width <= 3
	 * @param height
	 * 			height >= 1 && height <= 3
	 * @param imagePath
	 * 			imagePath != null
	 * @param click
	 * 				click != null
	 * @param drag
	 * 				drag != null
	 * @param release
	 * 				release != null
	 */
	public Vehicle(Point p, int width, int height, String imagePath, String soundPath) {
		this.currentLocation = p;
		this.width = width;
		this.height = height;
		this.imagePath = imagePath;
		this.soundPath = soundPath;
	}
	
	/**
	 * Extended constructor that instantiates a vehicle
	 * @param p
	 * 			p != null && p is a valid point in the grid
	 * @param width
	 * 			width >= 1 && width <= 3
	 * @param height
	 * 			height >= 1 && height <= 3
	 * @param imagePath
	 * 			imagePath != null
	 * @param click
	 * 				click != null
	 * @param drag
	 * 				drag != null
	 * @param release
	 * 				release != null
	 */
	public Vehicle(Point p, int width, int height, String imagePath, Image image, String soundPath, SoundEffect drag) {
		this.currentLocation = p;
		this.width = width;
		this.height = height;
		this.imagePath = imagePath;
		this.image = image;
		this.soundPath = soundPath;
		this.drag = drag;
	}
	
	/**
	 * returns the position of the most bottom-left corner of the vehicle
	 * @return point
	 */
	public Point getOppositeCorner() {
		return getOppositeCorner(this.currentLocation);
	}
	
	/**
	 * returns the position of the most bottom-left corner of the vehicle
	 * @param p p != null
	 * @return point
	 */
	public Point getOppositeCorner(Point p) {
		int upperY = p.y + this.height - 1;
		int upperX = p.x + this.width - 1;
		
		return new Point(upperX, upperY);
	}

	
	/**
	 * this function compares the input Point
	 * it then reduces the grid reference by the width and height of this vehicle 
	 * which allows us to compare the incoming point with the currentLocation 
	 * variable above. CurrentLocation only refers to the top most or left most grid point that
	 * the car occupies, so we need to perform this transform to account for selected points that are
	 * not the top most or left most points
	 * 
	 * @param p 
	 * 			p != null
	 * @return true if vehicle covers point p, false otherwise
	 */
	public boolean isCovered(Point p) {
		
		int lowerY = this.currentLocation.y;
		int upperY = this.currentLocation.y + this.height - 1;
		
		int lowerX = this.currentLocation.x;
		int upperX = this.currentLocation.x + this.width - 1;
		
		return p.y >= lowerY 
				&& p.y <= upperY
				&& p.x >= lowerX
				&& p.x <= upperX;
	}

	/**
	 * Returns the current location 
	 * @return currentLocation
	 */
	public Point getCurrentLocation() {
		return this.currentLocation;
	}
	
	/**
	 * Sets the location of the vehicle to point p
	 * @param p
	 * 			p != null && p is a valid point in the grid && p is not covered by any other vehicle in grid
	 */
	public void setLocation(Point p) {
		this.currentLocation = (Point) p.clone();
	}
	
	/**
	 * Returns the image of the vehicle
	 * @return image
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * Sets the grid position, does not matter if the point p 
	 * has already been adjusted or not, this function will sanitise it
	 * 
	 * @see Grid
	 */
	public void moveVehicle(Point p) {

		Point clone = (Point) p.clone();
		Point newPoint = nextPosition(clone);

		this.currentLocation = newPoint;
	}

	/**
	 * Returns the next point that the vehicle will travel too
	 * @param p
	 * @return
	 */
	public abstract Point nextPosition(Point p);

	
	/**
	 * Returns height of vehicle
	 * @return height
	 */
	public int getVehicleHeight() {
		return this.height;
	}

	/**
	 * Returns width of vehicle
	 * @return width
	 */
	public int getVehicleWidth() {
		return this.width;
	}
	
	@Override
	public abstract Vehicle clone();
	
	@Override
	public boolean equals (Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!this.getClass().equals(obj.getClass())) return false;

		Vehicle other = (Vehicle) obj;
		return this.imagePath.equals(other.imagePath) 
				&& this.height == other.height 
				&& this.width == other.width
				&& this.currentLocation.equals(other.currentLocation);
	}

	/**
	 * toString method; may not be needed but used for debugging
	 */
	@Override
	public String toString() {
		return "image: " + this.imagePath + " -> " + this.currentLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 1023;
		int hash = 1;
		hash = hash + currentLocation.hashCode();
		hash = hash + (currentLocation.x+1);
		hash = hash + (currentLocation.y+1);
		hash += (height + width);
		hash *= prime;
		return hash;
	}
	
	
}
