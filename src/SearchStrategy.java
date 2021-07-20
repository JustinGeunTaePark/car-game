/**
 * Strategy Pattern interface for the gridlock puzzle
 * Takes in a state / grid, and finds the most optimal path to the goal state
 * @author guerht
 *
 */
public interface SearchStrategy {
	/**
	 * Performs a search algorithm given a certain grid / state.
	 * Returns a list of movement needed to reach the goal state
	 * Returns null if puzzle is not solvable, that is, goal state cannot be reached.
	 * @pre grid must be a valid grid with valid vehicle composition
	 * @return states  path via a States object, null if not solvable
	 */
	public States search(Grid grid);
}
