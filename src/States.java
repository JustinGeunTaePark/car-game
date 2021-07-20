import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

/**
 * The States class contains a history of states of the grid. The States class
 * is comprised of stateHistory, a list of Grid. The first element in
 * stateHistory is the initial state of the game, and the last element in
 * stateHistory ins the current state of the game. Storing a history of states
 * allow the user to undo an action or reset the game entirely to the original
 * state
 * 
 * @author guerht
 * @variance stateHistory != null && currentIndex >= 0
 */
public class States {

	// stores the list of actions the user performed
	private List<Grid> stateHistory;
	
	// the pointer reference to the current state
	private Grid currentState;
	// index of the current state
	private int currentIndex;
	
	/**
	 * Default constructor used for the purpose of cloning
	 */
	public States() {
		this.stateHistory = new LinkedList<Grid>();
		this.currentState = null;
		this.currentIndex = 0;
	}

	/**
	 * standard constructor for creating a States object Note that an instance
	 * of a grid is a state of a game
	 * 
	 * @param g
	 *            g is the initial state of the game; vehicles needed for the
	 *            game are already added to g, and g is also solvable
	 */
	public States(Grid g) {
		Grid gClone = g.clone();
		this.stateHistory = new LinkedList<Grid>();
		this.stateHistory.add(gClone);
		this.currentState = gClone;
		this.currentIndex = 0;
	}

	/**
	 * Moves vehicle to point p
	 * @see Vehicle.java
	 * @pre the movement must be valid
	 * @param v
	 * 			v != null && v is a valid vehicle that exists in grid
	 * @param p
	 * 			p != null && point is a valid point within grid bounds
	 */
	public void moveVehicle(Vehicle v, Point p) {

		Grid currentGrid = getCurrentGrid().clone();
	
		currentGrid.moveVehicle(v, p);
		// removes all following states, not needed anymore
		if(getCurrentGridIndex() != getStateHistorySize()-1) {
			stateHistory.subList(getCurrentGridIndex()+1, getStateHistorySize()).clear();
		}
		this.stateHistory.add(currentGrid);
		this.currentState = currentGrid;
		this.currentIndex = stateHistory.size()-1;
	}
	
	/**
	 * Adds a grid to the list, used for the search algorithm
	 * @param g
	 * 			g != null
	 */
	public void addGrid(Grid g) {
		// removes all following states
		if(getCurrentGridIndex() != getStateHistorySize()-1) {
			stateHistory.subList(getCurrentGridIndex()+1, getStateHistorySize()).clear();
		}
		this.stateHistory.add(g);
		this.currentState = g;
		this.currentIndex = stateHistory.size()-1;
	}
	
	/**
	 * Moves vehicle by a magnitude of n
	 * @see Vehicle.java
	 * @pre the movement must be valid
	 * @param v
	 * 			v != null && v is a valid vehicle that exists in grid
	 * @param n
	 * 			n >= -6 && n <= 6
	 */
	public void moveVehicle(Vehicle v, int n) {
		Grid currentGrid = getCurrentGrid().clone();
		
		currentGrid.moveVehicle(v, n);
		if(getCurrentGridIndex() != getStateHistorySize()-1) {
			stateHistory.subList(getCurrentGridIndex()+1, getStateHistorySize()).clear();
		}
		this.stateHistory.add(currentGrid);
		this.currentState = currentGrid;
		this.currentIndex = stateHistory.size()-1;
	}
	
	public Vehicle getVehicle(Point current) {
		
		Grid currentGrid = getCurrentGrid();
		return currentGrid.getVehicle(current);
	}

	/**
	 * Checks if the current state / grid is solved by checking the position of
	 * the red car (horizontal vehicle)
	 * 
	 * @return true if position of red car is at goal state, false otherwise
	 */
	public boolean isSolved() {
		
		Boolean solved = false;
		for (Vehicle v: getCurrentGrid().getVehicles()) {

			if (v instanceof GoalVehicle && ((GoalVehicle) v).isSolved()) {
				solved = true;
				break;
			}
		}
		
		return solved;
	}

	
	/**
	 * Resets the game to its original state
	 */
	public void resetGame() {
		Grid firstState = this.stateHistory.get(0);
		
		this.stateHistory.clear();
		this.stateHistory.add(firstState);
		this.currentState = firstState;
		this.currentIndex = 0;
	}

	/**
	 * reverts an action most recently performed by the user
	 */
	public void undoAction() {
		int index = getCurrentGridIndex();
		this.currentState = stateHistory.get(index-1);
		this.currentIndex--;
	}
	
	public void redoAction() {
		int index = getCurrentGridIndex();
		this.currentState = stateHistory.get(index+1);
		this.currentIndex++;
	}

	/**
	 * Returns the current state / grid
	 * 
	 * @return stateHistory.get(stateHistory.size()-1) last element in the
	 *         stateHistory list
	 */
	public Grid getCurrentGrid() {
		return this.currentState;
	}
	
	/**
	 * Used by the search strategy; gets next state when hint is used
	 * @return grid
	 *
	 */
	public Grid getNextGrid() { 
		return this.stateHistory.get(1);
	}
	
	/**
	 * Gets the current grid index
	 * @return currentIndex
	 */
	public int getCurrentGridIndex() {
		return this.currentIndex;
	}
	
	/**
	 * Gets size of the states list
	 * @return stateHistory.size()
	 */
	public int getStateHistorySize() {
		return stateHistory.size();
	}
	
	/**
	 * Returns the list
	 * @return stateHistory
	 */
	public List<Grid> getStateHistory() {
		return stateHistory;
	}
	
	/**
	 * Returns the total number of moves done by the user Total number of moves
	 * is exactly dependent of the size of the history - 1.
	 * 
	 * @return stateHistory.size()-1
	 */
	public int getTotalMovesDone() {
		return getCurrentGridIndex();
	}

	/**
	 * Obsolete function, but may be used in the future
	 * @param s2
	 * @return index compared
	 */
	public int compareTo(States s2) {
		return this.currentIndex > s2.currentIndex ? 1 : -1;
	}
	
	@Override
	public States clone() {
		
		States states = new States();
		states.currentIndex = this.currentIndex;
		states.currentState = this.currentState.clone();
		for(Grid g : this.stateHistory) {
			states.stateHistory.add(g.clone());
		}
		return states;
	}
}
