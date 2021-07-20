
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Algorithm to generate the puzzle.
 *
 */
public class PuzzleAlgorithm {

	private Grid puzzle;
	private Vehicle target;
	private int remainingMoves;
	private HashMap<Vehicle, Integer> visited;
	
	/**
	 * Constructor to instantiate the puzzle algorithm.
	 */
	public PuzzleAlgorithm() {
		this.visited = new HashMap<Vehicle, Integer>();
	}
	
	/**
	 * Gets the remaining moves until completion of the puzzle.
	 * @return
	 */
	public int getRemainingMoves() {
		return this.remainingMoves;
	}
	
	/**
	 * Updates the current puzzle to the new puzzle.
	 * @param puzzle
	 * 				puzzle != null
	 */
	public void updatePuzzle(Grid puzzle) {
		this.puzzle = puzzle;
		this.target = puzzle.getGoalVehicle();
		//System.out.println(puzzle);
	}
	
	/**
	 * Finds out how many moves until the end of the puzzle.
	 */
	public void testHeuristic() {
		this.remainingMoves = 0;
		for(Vehicle v: this.puzzle.getVehicles())
			this.visited.put(v, 0);
		this.remainingMoves += 1;
		search(this.target);
		System.out.println("number of moves left possibly: "+this.remainingMoves);
	}
	
	/**
	 * Searched fo
	 * @param curr
	 */
	public void search(Vehicle curr) {
		// avoid cycle
		if(this.visited.get(curr) == 1)
			return;

		this.visited.put(curr, 1);
		LinkedHashMap<Vehicle, Point> blocks = puzzle.isBlocked(curr);
		this.remainingMoves += blocks.size();

		// no obstacle(vehicle) in the path towards the goal
		if(blocks.isEmpty())
			return;
		for(Vehicle v: blocks.keySet()) {
			if(this.puzzle.isBlockRemovable(v, blocks.get(v)))
				continue;
			search(v);
		}
	}
}
