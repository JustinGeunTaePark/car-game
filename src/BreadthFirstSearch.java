import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
/**
 * A classic pseudo-brute-force graph-searching algorithm.
 * A State, that is, a specific configuration of a grid, is treated as a vertex.
 * Given two states, if one can move a vehicle in a grid to get the new grid, then there is an edge between the states
 * All edges have a weight cost of one.
 * Visited vertices are stored in a HashMap for an ideal search.
 * @author guerht
 */
public class BreadthFirstSearch implements SearchStrategy {
	/**
	 * Performs the Breath First Search algorithm given a certain grid / state.
	 * Returns a list of movement needed to reach the goal state
	 * Number of movement is optimal, since the cost of all edges is one.
	 * Returns null if puzzle is not solvable, that is, goal state cannot be reached.
	 * @pre grid must be a valid grid with valid vehicle composition
	 * @return states  most optimal path by via a States object, null if not solvable
	 */
	public States search(Grid grid) {
		// cloning done to not mess up original grid configuration
		Grid currentGrid = grid.clone();
		// creates a states object that is to be returned
		States currentState = new States(currentGrid);
		// visited vertices are stored in a HashMap configuration for omega(1) search time.
		Map<Grid, Integer> visited = new HashMap<Grid, Integer>(1023);
		// initial grid is put into visited
		visited.put(grid,1);
		// BFS uses queue. Hence a standard queue structure will be used
		// states will be stored in the queue; but only the last grid in the states will be actually used
		Queue<States> queue = new LinkedList<States>();
		// add initial state to queue
		queue.add(currentState);
		while(!queue.isEmpty()) {
			currentState = queue.poll();
			currentGrid = currentState.getCurrentGrid();
			// return if game is solvable. At this point, states will contain a list of grids which, sequentially, describe
			// the steps in obtaining the optimal path
			if(currentGrid.isGameSolved()) {
				break;
			}
			// note: logic below must consider
			// all possible movements that could be made from the current grid
			// whether the specific grid has already been visited
			for(Vehicle v : currentGrid.getVehicles()) {
				// tests all possible movements of a given vehicle
				for(int i = -5; i < 6; i++) {
					// vehicles that do not move should not even be considered
					if(i == 0) continue;
					// checks if vehicle can move to a certain position
					if(currentGrid.isVehicleMovable(v, i)) {
						// if so, move
						States newStates = currentState.clone();
						newStates.moveVehicle(v, i);
						// if this state was already checked before, skip
						if(visited.containsKey(newStates.getCurrentGrid()) == true) {
							continue;
						}
						// otherwise, add to visited and add to queue
						visited.put(newStates.getCurrentGrid(), 1);
//						System.out.println(visited.size());
						queue.add(newStates);
					}
				}
			}
			// resets state to null. This will be returned if queue is empty, which implies no solution
			currentState = null;
		}
		return currentState;
	}
}
