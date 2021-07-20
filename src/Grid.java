import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The backend puzzle that stores a list of vehicles.
 * Also well known as a state; the term state and grid will be used interchangeably
 * This is the Controller from the MVC configuration
 * 
 * @author Seung Hoon Park
 * @invariant vehicles != null
 */
public class Grid {
	// defines the size of the grid
	private static final int GRID_ROWS = 6;
	private static final int GRID_COLS = 6;
	
	// vehicles contain the vehicles that are on the grid
	private List<Vehicle> vehicles;

	/**
	 * Standard constructor. Just initialises vehicles
	 * @post vehicles != null
	 */
	public Grid() {
		this.vehicles = new LinkedList<Vehicle>();
	}

	/**
	 * Adds a vehicle to the grid, or to be specific,
	 * the vehicles list in this grid
	 * @param v
	 * 			v != null && v has a valid configuration (not gridOutOfBounds, etc.)
	 */
	public void addVehicle(Vehicle v) {
		this.vehicles.add(v);
	}

	/**
	 * Returns the list of vehicles in this grid
	 * @return vehicles
	 */
	public List<Vehicle> getVehicles() {
		return this.vehicles;
	}
	
	/**
	 * Gets a vehicle in the grid that is in position p
	 * @param p
	 * 			p != null && p.x and p.y is within grid coordinates
	 * @return	instance of vehicle at point p, null if it does not exist
	 */
	public Vehicle getVehicle(Point p) {
		for (Vehicle v: this.getVehicles()) {
				
			if (v.isCovered(p)) {
				return v;
			}
		}
		
		return null;
	}
	
	/**	
	 * Gets the goal vehicle in the grid.
	 * @return goalVehicle, null is never returned
	 */
	public Vehicle getGoalVehicle() {
		for(Vehicle v : this.getVehicles()) {
			if(v instanceof GoalVehicle) return v;
		}
		return null;
	}
	
	/**
	 * Moves a given vehicle to the point specified by p.
	 * @param v
	 *            v != null && v exists in the vehicles list
	 * @param p
	 * 			  p != null && p.x and p.y are valid grid points
	 */
	public void moveVehicle(Vehicle v, Point p) {
		
		// get the current vehicle from the stored list of vehicles and update that 
		for (Vehicle vehicle: this.vehicles) {
			if (vehicle.equals(v)) {
				vehicle.setLocation(p);
				break;
			}
		}
	}
	
	/**
	 * Moves a given vehicle in the grid by the specified amount amount can be
	 * either positive or negative if vehicle v is aligned horizontally and
	 * amount is positive, vehicle v will move to the right by the specified
	 * amount if vehicle v is aligned horizontally and amount is negative,
	 * vehicle v will move to the left by the specified amount if vehicle v is
	 * aligned vertically and amount is positive, vehicle v will move up by the
	 * specified amount if vehicle v is aligned vertically and amount is
	 * negative, vehicle v will move down by the specified amount
	 * 
	 * @param v
	 *            v != null && v exists in the vehicles list
	 * @param amount
	 *            amount != 0 && v must not collide with any other vehicles or
	 *            go out of bounds from the grid
	 */
	public void moveVehicle(Vehicle v, int n) {
		for (Vehicle vehicle: this.vehicles) {
			if (vehicle.equals(v)) {
				int height = v.getVehicleHeight();
				
				if(height == 1) {
					vehicle.setLocation(new Point(v.getCurrentLocation().x+n, v.getCurrentLocation().y));
					break;
				}
				else {
					vehicle.setLocation(new Point(v.getCurrentLocation().x, v.getCurrentLocation().y+n));
					break;
				}
			}
		}
	}
	
	public LinkedHashMap<Vehicle, Point> isBlocked(Vehicle v) {
		LinkedHashMap<Vehicle, Point> blocks = new LinkedHashMap<Vehicle, Point>();		
		// vertical vehicle
		if(v.width == 1) {
			for(int i = v.currentLocation.y+v.height; i < GRID_ROWS; i++) {
				Point p = new Point(v.currentLocation.x, i);
				for(Vehicle vehicle: this.vehicles) {
					if(vehicle == v) continue;
					if(vehicle.isCovered(p))
						blocks.put(vehicle, p);
				}
			}
			for(int i = v.currentLocation.y-1; i >= 0; i--) {
				Point p = new Point(v.currentLocation.x, i);
				for(Vehicle vehicle: this.vehicles) {
					if(vehicle == v) continue;
					if(vehicle.isCovered(p))
						blocks.put(vehicle, p);
				}
			}
		}			
		// horizontal vehicle
		else {
			for(int i = v.currentLocation.x+v.width; i < GRID_COLS; i++) {
				Point p = new Point(i, v.currentLocation.y);
				for(Vehicle vehicle: this.vehicles) {
					if(vehicle == v) continue;
					if(vehicle.isCovered(p))
						blocks.put(vehicle, p);
				}
			}
			if(v instanceof GoalVehicle) return blocks;
			for(int i = v.currentLocation.x-1; i >= 0; i--) {
				Point p = new Point(i, v.currentLocation.y);
				for(Vehicle vehicle: this.vehicles) {
					if(vehicle == v) continue;
					if(vehicle.isCovered(p))
						blocks.put(vehicle, p);
				}
			}
		}
		return blocks;
	}
	
	public boolean hasCollision(Vehicle v, Vehicle self) {
		if(v.width == 1) {
			for(int i = v.currentLocation.y; i < v.currentLocation.y+v.height; i++) {
				Point p = new Point(v.currentLocation.x, i);
				for(Vehicle vehicle: this.vehicles) {
					if(vehicle.equals(self)) continue;
					if(vehicle.isCovered(p))
						return true;
				}
			}
		}
		else{
			for(int i = v.currentLocation.x; i < v.currentLocation.x+v.width; i++) {
				Point p = new Point(i, v.currentLocation.y);
				for(Vehicle vehicle: this.vehicles) {
					if(vehicle.equals(self)) continue;
					if(vehicle.isCovered(p))
						return true;
				}
			}
		}
		return false;
	}
	
	public boolean isBlockRemovable(Vehicle block, Point p) {
		// block is vertical vehicle
		if(block.width == 1) {
			for(int i = block.getCurrentLocation().y+1; i <= GRID_COLS-block.height; i++) {
				Vehicle v = block.clone();
				v.currentLocation.y = i;
				boolean hasCollision = hasCollision(v, block);
				if(hasCollision) break;
				if(!v.isCovered(p) && !hasCollision) return true;
			}
			for(int i = block.getCurrentLocation().y-1; i >= 0; i--) {
				Vehicle v = block.clone();
				v.currentLocation.y = i;
				boolean hasCollision = hasCollision(v, block);
				if(hasCollision) return false;
				if(!v.isCovered(p) && !hasCollision) return true;
			}
		}
		// block is horizontal vehicle
		else {
			for(int i = block.getCurrentLocation().x+1; i <= GRID_ROWS-block.width; i++) {
				Vehicle v = block.clone();
				v.currentLocation.x = i;
				boolean hasCollision = hasCollision(v, block);
				if(!v.isCovered(p) && !hasCollision) return true;
				if(hasCollision) return false;
			}
			for(int i = block.getCurrentLocation().x-1; i >= 0; i--) {
				Vehicle v = block.clone();
				v.currentLocation.x = i;
				boolean hasCollision = hasCollision(v, block);
				if(!v.isCovered(p) && !hasCollision) return true;
				if(hasCollision) return false;
			}
		}
		return false;
	}
	/** 
	 * Validity check on whether vehicle is movable to a position by a magnitude of n
	 * @param v
	 * 			v != null && v exists in vehicles
	 * @param n
	 * 			abs(n) is at most 6
	 * @return true if vehicle can be moved, false otherwise
	 */
	public boolean isVehicleMovable(Vehicle v, int n) {
		// check recursively if lesser values of n moves is valid
		
		if(n > 1) {
			if(isVehicleMovable(v,n-1) == false) return false;
		}
		else if(n < -1) {
			if(isVehicleMovable(v,n+1) == false) return false;
		}
		
		// origin point of v
		Point p1 = (Point) v.getCurrentLocation().clone();
		// opposite point of v
		Point p2 = (Point) v.getOppositeCorner().clone();
		// used to check which vehicle
		int height = v.getVehicleHeight();
		// instance of horizontalVehicle
		if(height == 1) {
			// check if other space is occupied by another vehicle or not
			p1.x = p1.x + n;
			Vehicle origin = getVehicle(p1);
			if(origin != null && !origin.equals(v)) {
				return false;
			}
			p2.x = p2.x + n;
			Vehicle opposite = getVehicle(p2);
			if(opposite != null && !opposite.equals(v)) {
				return false;
			}
		}
		// instance of verticalVehicle
		else {
			// check if other space is occupied by another vehicle or not
			p1.y = p1.y + n;
			Vehicle origin = getVehicle(p1);
			if(origin != null && !origin.equals(v)) {
				return false;
			}
			p2.y = p2.y + n;
			Vehicle opposite = getVehicle(p2);
			if(opposite != null && !opposite.equals(v)) {
				return false;
			}
		}
		// final check: if position is within grid
		return isWithinGrid(p1) && isWithinGrid(p2);
	}

	@Override
	public String toString() {
		String printStr = "";
		
		for (Vehicle vehicle: this.vehicles) {
			printStr += vehicle.toString() + "\n";
		}
		
		return printStr;
	}
	
	/**
	 * This function checks if the grid can be solved in one move
	 * @return true if solvable instantly, false otherwise
	 */
	public boolean isGameSolved() {
		Vehicle goalVehicle = getGoalVehicle();
		int difference = 4 - (int) goalVehicle.getCurrentLocation().getX();
		return isVehicleMovable(goalVehicle, difference);
	}
	
	@Override
	public boolean equals (Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!this.getClass().equals(obj.getClass())) return false;

		Grid other = (Grid) obj;
		for(int i = 0; i < this.vehicles.size(); i++) {
			if(this.vehicles.get(i).getCurrentLocation().x != other.vehicles.get(i).getCurrentLocation().x || this.vehicles.get(i).getCurrentLocation().y != other.vehicles.get(i).getCurrentLocation().y) return false;
		}
		
		return true;
	}

	@Override
	public Grid clone() {
		Grid newGrid = new Grid();
		
		for (Vehicle v : this.vehicles) {
			newGrid.addVehicle(v.clone());
		}
		
		return newGrid;
	}
	
	private boolean isWithinGrid(Point p) {
		
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		return x >= 0
				&& x < GRID_ROWS
				&& y >= 0 
				&& y < GRID_COLS;
	}


	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		final int prime = 1023;
		int hash = 1;
		hash = prime * hash + vehicles.hashCode();
		
		return hash;
	}
}
