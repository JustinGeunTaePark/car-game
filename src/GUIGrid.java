import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * This is the Model in the MVC configuration
 * @author Tom
 *
 */
public class GUIGrid {

	private int gridTileWidth = 0;
	private int gridTileHeight = 0;

	private static final int GRID_ROWS = 6;
	private static final int GRID_COLS = 6;
	private static final int HGAP = 15;
	private static final int VGAP = 15;

	// the grid where the tiles on the board are displayed
	private JPanel background;
	
	// layer the cars will reside on
	private JPanel carPanel;

	// this stores the background and the cars on top as separate elements
	private JLayeredPane board;
	
	/**
	 * Currently:
	 * 	When the user picks up a car a JLabel is returned to the GUI,
	 * 	The GUI will handle the mouse etc,
	 * 	However when the car moves we need to be able to call the vehicle methods within this class
	 * 
	 *	For the time being this variable will hold the currently being moved vehicle by the user 
	 *	Until we can improve the implementation
	 */
	private Vehicle temporaryVehicle;
	private Vehicle initialVehicle;

	// the game states
	private States states;
	
	private Point lastGUIPos;

	public GUIGrid(Dimension layerSize, GameOptions.Level level) {
		
		States state = getInitialState(level);
		
		this.states = state;

		this.background = buildBackground(layerSize);
		this.carPanel = buildCarLayer(layerSize);

		JLayeredPane board = new JLayeredPane();

		board.setOpaque(true);
		board.setPreferredSize(layerSize);
		board.setBounds(0, 0, layerSize.width, layerSize.height);

		board.add(this.background,  JLayeredPane.DEFAULT_LAYER);
		board.add(this.carPanel,  JLayeredPane.PALETTE_LAYER);

		// set to display correctly in the layer
		this.background.setBounds(0, 0, layerSize.width, layerSize.height);
		this.carPanel.setBounds(0, 0, layerSize.width, layerSize.height);

		this.board = board;

	}
	
	/**
	 * Returns the initial state of a puzzle
	 * @param level
	 * @return
	 */
	private States getInitialState(GameOptions.Level level) {
		
//		this.puzzleAlgorithm = new PuzzleAlgorithm();
		BuildPuzzle puzzle = new BuildPuzzle(level);

		// store the initial state
		States state = new States(puzzle.getPuzzle());

		return state;
	}

	/**
	 * Builds the background image, with the squares
	 * @param dimension
	 * @return
	 */
	private JPanel buildBackground(Dimension dimension) {

		// GRID_ROWS -> the rows in the grid
		// GRID_COLS -> columns in the grid
		// HGAP -> the gap between grid tiles horizontally
		// VGAP -> the gap between grid tiles vertically
		GridLayout boardLayout = new GridLayout(GRID_ROWS, GRID_COLS, HGAP, VGAP);
		JPanel backgroundPanel = new JPanel(boardLayout);

		backgroundPanel.setBackground(Color.WHITE);
		backgroundPanel.setPreferredSize(dimension);
		backgroundPanel.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));

		JPanel[][] panelGrid = new JPanel[GRID_ROWS][GRID_COLS];

		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);

		// instantiate the grid tiles
		for (int row = 0; row < GRID_ROWS; row++) {
			for (int col = 0; col < GRID_COLS; col++) {
				JPanel current = new JPanel(new GridBagLayout());
				
				current.setBorder(border);
				backgroundPanel.add(current);

				panelGrid[row][col] = current;
			}
		}
		
		// calculate the size of each of the tiles on the grid 
		int gridWidth = (int) dimension.getWidth() - 2 * HGAP;
		int gridHeight = (int) dimension.getHeight() - 2 * VGAP;
		
		this.gridTileWidth = (gridWidth - (GRID_COLS - 1) * HGAP) / GRID_COLS;
		this.gridTileHeight = (gridHeight - (GRID_ROWS - 1) * VGAP) / GRID_ROWS;

		return backgroundPanel;
	}

	/**
	 * Builds the layer that will have the cars 
	 * Then adds the cars from the state to the layer
	 * @param dimension
	 * @return
	 */
	private JPanel buildCarLayer(Dimension dimension) {

		JPanel innerPanel = new JPanel();
		
		innerPanel.setLayout(null);

		int newWidth = dimension.width - HGAP * (GRID_ROWS - 1);
		int newHeight = dimension.height - VGAP * (GRID_COLS - 1);
		Dimension innerDimension = new Dimension(newWidth, newHeight);

		innerPanel.setPreferredSize(innerDimension);
		innerPanel.setOpaque(false);
		
		// add cars from initial state here
		for (Vehicle v: this.states.getCurrentGrid().getVehicles()) {
			this.addCar(innerPanel, v);	
		}
		
		return innerPanel;
	}
	
	/**
	 * This function adds a vehicle onto a panel, in this case a puzzle grid.
	 * @param panel
	 * @param v
	 */
	private void addCar(JPanel panel, Vehicle v) {
		
		// get the place on the screen 
		Point screenPosition = translateGridToGUI(v.getCurrentLocation());
		
		// build the size of the car 
		int width = v.getVehicleWidth() * gridTileWidth + (v.getVehicleWidth() - 1) * HGAP;
		int height = v.getVehicleHeight() * gridTileHeight + (v.getVehicleHeight() - 1) * VGAP;
		
		Dimension vehicleSize = new Dimension(width, height);
		
		// build the on screen component
		JLabel vehicleLabel = new JLabel();
		
		// set the preferred size of the label
		vehicleLabel.setPreferredSize(vehicleSize);	
		
		// set the image of the vehicle
		Image image = v.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon icon = new ImageIcon(image);
		vehicleLabel.setIcon(icon);
	
		vehicleLabel.setBounds(screenPosition.x, screenPosition.y, width, height);
		
		panel.add(vehicleLabel, 0);
	}
	
	/**
	 * This function takes in an internal grid reference and translates it to
	 * the location on the GUI, used to translate the vehicle coordinates to
	 * the on screen positions
	 * 
	 * @param gridLocation
	 * @return
	 * @throws Exception
	 */
	private Point translateGridToGUI(Point p) {
		
		int x = (int) p.getX();
		int y = (int) p.getY();

		// check valid grid reference
		if (x > GRID_ROWS || x < 0 || y > GRID_COLS || y < 0) {
			// TODO: Throw error here instead of just crashing
			System.err.println("Invalid grid reference");
			System.exit(-1);
		}
		
		/**
		 * 1. top right corner is VGAP from the top and HGAP from the left
		 * 2. take the size of the grid tile, 
		 */
	
		int firstGridCenterX = HGAP + (this.gridTileWidth + HGAP) * x;
		int firstGridCenterY = VGAP + (this.gridTileHeight + VGAP) * y;
	
		return new Point(firstGridCenterX, firstGridCenterY);
	}
	
	/**
	 * Takes in a point that is relative to the GUI as a whole
	 * and converts it to a point that can be used to access one of the grid points
	 * 
	 * @param p - GUI point
	 * @return
	 */
	public Point translateGUIToGrid(Point p) {
		
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		int row = 0;
		int col = 0;
		
		int xReducer = this.gridTileWidth + HGAP;
		int yReducer = this.gridTileHeight + VGAP;
		
		while (x >= xReducer && row < GRID_ROWS ) {
			row++;
			x -= xReducer;
		}

		while (y >= yReducer && col < GRID_COLS ) {
			col++;
			y -= yReducer;
		}	
		
		return new Point(row, col);
	}
	
	/**
	 * Returns the 
	 * @param guiPoint
	 * @return
	 */
	private JLabel getPanel(Point guiPoint) {
		
		Component object = this.carPanel.getComponentAt(guiPoint);
	
		if ( !(object instanceof JLabel) ) {
			return null;
		}
		
		return (JLabel) object;
	}
	
	private boolean isWithinGrid(Point p) {
		
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		return x >= 0
				&& x < GRID_ROWS
				&& y >= 0 
				&& y < GRID_COLS;
	}
	
	/**
	 * 
	 * @param label
	 * @param nextPoint
	 * @return
	 */
	private boolean isValidLocation(Point nextGridPoint) {	
		
		Vehicle vehicleAtNext = this.states.getVehicle(nextGridPoint);
		
		// move if the next grid has no vehicle = null
		// or if the next grid location already has the current car in it
		// NOTE:
		// ONLY relevant if the tile is either to the left or above 
		if (vehicleAtNext != null && !vehicleAtNext.equals(this.initialVehicle)) {
			return false;
		}
		
		Point oppositeVehiclePoint = this.temporaryVehicle.getOppositeCorner(nextGridPoint);
		Vehicle vehicleAtOpposite = this.states.getVehicle(oppositeVehiclePoint);
		
		// NOTE:
		// ONLY relevant if the tile is either to the right or below
		if (vehicleAtOpposite != null && !vehicleAtOpposite.equals(this.initialVehicle)) {
			return false;
		}
		
		return isWithinGrid(nextGridPoint) && isWithinGrid(oppositeVehiclePoint);
	}
	
	public JLabel startMove(Point clickedPoint) {
		
		// if the location does not have a car just return 
		JLabel label = getPanel(clickedPoint);
		if (label == null) {
			return null;
		}
		
		Point gridLocation = translateGUIToGrid(clickedPoint);
		Vehicle v = this.states.getVehicle(gridLocation);
		
		this.temporaryVehicle = v.clone();
		this.initialVehicle = v.clone();
		
		this.lastGUIPos = label.getLocation();
		
		return label;
	}

	/**
	 * 
	 * @param object - the currently held object
	 * @param draggedPoint - the point the cursor is currently hovering over
	 * @return
	 */
	public boolean newMove(JLabel object, Point draggedPoint) {
		
		if (this.temporaryVehicle == null) {
			return false;
		}
		
		// the grid point that the cursor is currently hovering over
		Point mouseGrid = translateGUIToGrid(draggedPoint);
		
		Point currGUIPos = object.getLocation();
		int numOfGridPassed;
		if(this.temporaryVehicle.width == 1)
			numOfGridPassed = Math.abs((currGUIPos.y - lastGUIPos.y) / this.gridTileHeight);			
		else
			numOfGridPassed = Math.abs((currGUIPos.x - lastGUIPos.x) / this.gridTileWidth);
		
		int times = numOfGridPassed - 1;
		if(times != -1 && SoundEffect.isSoundAvailable)
			this.initialVehicle.drag.play(times);
		
		lastGUIPos = currGUIPos;
		
		// does the mouse still cover the vehicle, 
		// if the mouse still covers the vehicle that was selected then don't 
		// do anything as they haven't dragged far enough to move it to a new square 
		if (this.temporaryVehicle.isCovered(mouseGrid)) {
			return false;
		}
		
		// if the mouse does not cover the vehicle then we are hovering over 
		// a new location, call the next position function so the vehicle
		// class can handle the move
		Point newGridCoords = this.temporaryVehicle.nextPosition(mouseGrid);
									
		// test if this returned location is a valid position for the grid
		if (!isValidLocation(newGridCoords)) {
			return false;
		} 
		
		// if the coordinates are valid then move the car 
		this.temporaryVehicle.moveVehicle(newGridCoords);
		
		// now translate the grid coordinates back to screen coordinates 
		Point screenCoordinates = translateGridToGUI(newGridCoords);	
		
		// get the car 
		object.setLocation(screenCoordinates);
		
		return true;
	}

	/**
	 * This function moves a JLabel from it's initial position on the grid
	 * to a new position, and plays a sound file if the puzzle is solved
	 * via this move.
	 * @param object
	 * @param level
	 * @return
	 */
	public SoundEffect finishMove(JLabel object, GameOptions.Level level) {
		
		if (this.temporaryVehicle == null) {
			return null;
		}
		
		this.states.moveVehicle(this.initialVehicle, this.temporaryVehicle.currentLocation);
		
		if (this.states.isSolved()) {			
			if(SoundEffect.isSoundAvailable) {
				SoundEffect winSound = new SoundEffect();
				File sound = null;
				Clip clip = null;			
				if(level == GameOptions.Level.Easy)
					sound = new File("music/winEasy.wav");
				else if(level == GameOptions.Level.Medium)
					sound = new File("music/winEasy.wav");
				else if(level == GameOptions.Level.Hard)
					sound = new File("music/winMedium.wav");
				else
					sound = new File("music/winHard.wav");			
				try {
			        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
			        clip = AudioSystem.getClip();
			        clip.open(audioInputStream);
			    } catch (UnsupportedAudioFileException e) {
			    	System.exit(1);
			    } catch (IOException e) {
			    	System.exit(1);
			    } catch (LineUnavailableException e) {
			    	System.exit(1);
			    } catch (IllegalArgumentException e) {
			    	System.exit(1);
			    }
				winSound.setClip(clip);
				winSound.play(0);
				JOptionPane.showMessageDialog(null, "Congratulations! You have solved the puzzle!\nTotal moves done: " + states.getTotalMovesDone(), "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
				return winSound;
			}
			else {
				JOptionPane.showMessageDialog(null, "Congratulations! You have solved the puzzle!\nTotal moves done: " + states.getTotalMovesDone(), "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
				return new SoundEffect();
			}
		}
		 
		this.temporaryVehicle = null;
		this.initialVehicle = null;
		return null;
	}
	
	public boolean boolFinishMove(JLabel object, GameOptions.Level level) {
		
		if (this.temporaryVehicle == null) {
			return false;
		}
		
		this.states.moveVehicle(this.initialVehicle, this.temporaryVehicle.currentLocation);
		
		if (this.states.isSolved()) {			
			JOptionPane.showMessageDialog(null, "Congratulations! You have solved the puzzle!\nTotal moves done: " + states.getTotalMovesDone(), "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		 
		this.temporaryVehicle = null;
		this.initialVehicle = null;
		return false;
	}

	/**
	 * Returns the board object, which stores the tile background and
	 * the panel that the cars reside on.
	 * @return
	 */
	public JLayeredPane getBoard() {
		return this.board;
	}
	
	/**
	 * Resets the current puzzle and the window back to their
	 * original states.
	 */
	public void resetGrid() {
		
		// clear the game states
		this.states.resetGame();
		
		this.reloadWindow();
	}
	
	/**
	 * Undoes the previous action and updates the board accordingly.
	 */
	public void undo() {
		this.states.undoAction();
		
		this.reloadWindow();
	}
	
	/**Redoes the move if it was previously undone, and updates
	 * the boar accordingly.
	 */
	public void redo() {
		this.states.redoAction();
		
		this.reloadWindow();
	}
	
	/**
	 * Adds a Grid object g to the list of states of grids in the current puzzle,
	 * and reloads the Window.
	 * @param g
	 */
	public void addGrid(Grid g) {
		this.states.addGrid(g);
		
		this.reloadWindow();
	}
	
	/**
	 * Reloads the window back to its original state.
	 */
	public void reloadWindow() {
		// clear the panels attached to the car panel
		for (Component c: this.carPanel.getComponents()) {			
			this.carPanel.remove(c);
		}
		
		// add cars from initial state here
		for (Vehicle v: this.states.getCurrentGrid().getVehicles()) {
			this.addCar(this.carPanel, v);	
		}
		
		this.temporaryVehicle = null;
		this.initialVehicle = null;
	}
	
	/**
	 * Returns the state list of the current puzzle.
	 * @return
	 */
	public States getStates() {
		return this.states;
	}
}

