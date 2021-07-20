import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Builds a gridlock puzzle.
 * Both randomised puzzles and predefined puzzles are made in this class.
 * @author guerht
 * @invariant minMoves >= 4
 */
public class BuildPuzzle {
	private Grid newGrid;
	
	// minMoves is an indicator for difficulty
	private int minMoves;
	
	public BuildPuzzle(GameOptions.Level level) {
		switch(level) {
		case Easy:
			this.minMoves = 4;
			break;
		case Medium:
			this.minMoves = 7;
			break;
		case Hard:
			this.minMoves = 10;
			break;
		case VeryHard:
			this.minMoves = 20;
		}
	}
	/**
	 * Returns a randomised instance of a puzzle to the GUI
	 * @return grid
	 * 				puzzle of grid is solvable
	 */
	public Grid getPuzzle() {
		if(this.minMoves <= 10)
			return randomPuzzle();
		else
			return hardPuzzle();
	}
	
	
	/**
	 * A premade instance of a grid / used for basic testing
	 * @return newGrid
	 *//*
	public Grid easyPuzzle() {
		newGrid = new Grid();

		Vehicle red = new GoalVehicle(new Point(1, 2), new Point(4, 2), 2, "img/red.png", 
			click, drag, release);
		
		Vehicle blue_vertical_long = new VerticalVehicle(new Point(3, 1), 3, "img/3x_blue_vertical.png",
			click, drag, release);
		Vehicle yellow_vertical_long = new VerticalVehicle(new Point(5, 0), 3, "img/3x_yellow_vertical.png",
			click, drag, release);
		Vehicle purple_vertical_long = new VerticalVehicle(new Point(0, 1), 3, "img/3x_purple_vertical.png",
			click, drag, release);

		Vehicle orange_vertical = new VerticalVehicle(new Point(0, 4), 2, "img/2x_orange_vertical.png",
			click, drag, release);

		
		Vehicle blue_horizontal = new HorizontalVehicle(new Point(3, 4), 2, "img/2x_blue_horizontal.png",
			click, drag, release);
		Vehicle green_horizontal = new HorizontalVehicle(new Point(0, 0), 2, "img/2x_green_horizontal.png",
			click, drag, release);
		
		Vehicle green_horizontal_long = new HorizontalVehicle(new Point(2, 5), 3, "img/3x_green_horizontal.png",
			click, drag, release);

		newGrid.addVehicle(red);
		
		newGrid.addVehicle(blue_vertical_long);
		newGrid.addVehicle(yellow_vertical_long);
		newGrid.addVehicle(purple_vertical_long);
		
		newGrid.addVehicle(orange_vertical);
		
		newGrid.addVehicle(blue_horizontal);
		newGrid.addVehicle(green_horizontal);
		
		newGrid.addVehicle(green_horizontal_long);

		loadImage();
		return newGrid;
	}*/
	
	/**
	 * A premade instance of a grid / used for testing efficiency of search algorithms
	 * @return newGrid
	 */
	public Grid hardPuzzle() {
		newGrid = new Grid();
		
		Vehicle red = new GoalVehicle(new Point(3, 2), new Point(4, 2), 2, "img/red.png",
			"music/drag1.wav");
		Vehicle yellow_vertical_long = new VerticalVehicle(new Point(0, 0), 3, "img/3x_yellow_vertical.png",
				"music/drag1.wav");
		Vehicle green_horizontal = new HorizontalVehicle(new Point(1, 0), 2, "img/2x_green_horizontal.png",
				"music/drag1.wav");
		Vehicle blue_vertical = new VerticalVehicle(new Point(1, 1), 2, "img/2x_blue_vertical.png",
				"music/drag1.wav");
		Vehicle pink_vertical = new VerticalVehicle(new Point(2, 1), 2, "img/2x_pink_vertical.png",
				"music/drag2.wav");
		Vehicle orange_vertical = new VerticalVehicle(new Point(4, 0), 2, "img/2x_orange_vertical.png",
				"music/drag2.wav");
		Vehicle deep_blue_horizontal = new HorizontalVehicle(new Point(0, 3), 3, "img/3x_blue_horizontal.png",
				"music/drag2.wav");
		Vehicle purple_vertical = new VerticalVehicle(new Point(3, 3), 2, "img/2x_pink_vertical.png",
				"music/drag3.wav");
		Vehicle green_vertical = new VerticalVehicle(new Point(2, 4), 2, "img/2x_blue_vertical.png",
				"music/drag3.wav");
		Vehicle blue_horizontal = new HorizontalVehicle(new Point(0, 5), 2, "img/2x_blue_horizontal.png",
				"music/drag3.wav");
		Vehicle lime_horizontal = new HorizontalVehicle(new Point(3, 5), 2, "img/2x_lime_horizontal.png",
				"music/drag4.wav");
		Vehicle black_horizontal = new HorizontalVehicle(new Point(4, 4), 2, "img/2x_black_horizontal.png",
				"music/drag4.wav");
		Vehicle purple_vertical_long = new VerticalVehicle(new Point(5, 1), 3, "img/3x_purple_vertical.png",
				"music/drag4.wav");
		
		newGrid.addVehicle(red);
		newGrid.addVehicle(yellow_vertical_long);
		newGrid.addVehicle(green_horizontal);
		newGrid.addVehicle(blue_vertical);
		newGrid.addVehicle(pink_vertical);
		newGrid.addVehicle(orange_vertical);
		newGrid.addVehicle(deep_blue_horizontal);
		newGrid.addVehicle(purple_vertical);
		newGrid.addVehicle(green_vertical);
		newGrid.addVehicle(blue_horizontal);
		newGrid.addVehicle(lime_horizontal);
		newGrid.addVehicle(black_horizontal);
		newGrid.addVehicle(purple_vertical_long);
		
		loadImage();
		loadSound();
		
		return newGrid;
	}
	
	/**
	 * A premade instance of a grid / used for basic testing
	 * @return newGrid
	 *//*
	public Grid simplePuzzle() {
		newGrid = new Grid();
		
		Vehicle red = new GoalVehicle(new Point(2, 2), new Point(4, 2), 2, "img/red.png",
			click, drag, release);
		Vehicle yellow_vertical_long = new VerticalVehicle(new Point(0, 1), 3, "img/3x_yellow_vertical.png",
			click, drag, release);
		Vehicle green_horizontal = new HorizontalVehicle(new Point(0, 0), 2, "img/2x_green_horizontal.png",
			click, drag, release);
		Vehicle blue_vertical = new VerticalVehicle(new Point(1, 1), 2, "img/2x_blue_vertical.png",
			click, drag, release);
		Vehicle pink_vertical = new VerticalVehicle(new Point(2, 0), 2, "img/2x_pink_vertical.png",
			click, drag, release);
		Vehicle orange_vertical = new VerticalVehicle(new Point(4, 0), 2, "img/2x_orange_vertical.png",
			click, drag, release);
		Vehicle deep_blue_horizontal = new HorizontalVehicle(new Point(3, 3), 3, "img/3x_blue_horizontal.png",
			click, drag, release);
		Vehicle purple_vertical = new VerticalVehicle(new Point(3, 0), 2, "img/2x_pink_vertical.png",
			click, drag, release);
		Vehicle green_vertical = new VerticalVehicle(new Point(2, 3), 2, "img/2x_blue_vertical.png",
			click, drag, release);
		Vehicle blue_horizontal = new HorizontalVehicle(new Point(0, 5), 2, "img/2x_blue_horizontal.png",
			click, drag, release);
		Vehicle lime_horizontal = new HorizontalVehicle(new Point(3, 5), 2, "img/2x_lime_horizontal.png",
			click, drag, release);
		Vehicle black_horizontal = new HorizontalVehicle(new Point(0, 4), 2, "img/2x_black_horizontal.png",
			click, drag, release);
		Vehicle purple_vertical_long = new VerticalVehicle(new Point(5, 0), 3, "img/3x_purple_vertical.png",
			click, drag, release);
		
		newGrid.addVehicle(red);
		newGrid.addVehicle(yellow_vertical_long);
		newGrid.addVehicle(green_horizontal);
		newGrid.addVehicle(blue_vertical);
		newGrid.addVehicle(pink_vertical);
		newGrid.addVehicle(orange_vertical);
		newGrid.addVehicle(deep_blue_horizontal);
		newGrid.addVehicle(purple_vertical);
		newGrid.addVehicle(green_vertical);
		newGrid.addVehicle(blue_horizontal);
		newGrid.addVehicle(lime_horizontal);
		newGrid.addVehicle(black_horizontal);
		newGrid.addVehicle(purple_vertical_long);
		
		loadImage();
		
		return newGrid;
	}
	
	*//**
	 * A premade instance of a grid / used for basic testing
	 * @return newGrid
	 *//*
	public Grid mediumPuzzle() {
		newGrid = new Grid();

		Vehicle red = new GoalVehicle(new Point(2, 2), new Point(4, 2), 2, "img/red.png",
				click, drag, release);
	
		Vehicle yellow_vertical_long = new HorizontalVehicle(new Point(0, 0), 3, "img/3x_green_horizontal.png",
				click, drag, release);
		Vehicle orange_vertical = new HorizontalVehicle(new Point(3, 4), 2, "img/2x_blue_horizontal.png",
				click, drag, release);	
		Vehicle blue_horizontal = new HorizontalVehicle(new Point(2, 3), 2, "img/2x_blue_horizontal.png",
				click, drag, release);
		Vehicle green_horizontal = new HorizontalVehicle(new Point(4, 3), 2, "img/2x_green_horizontal.png",
				click, drag, release);
		Vehicle green_horizontal_long = new HorizontalVehicle(new Point(2, 1), 2, "img/2x_blue_horizontal.png",
				click, drag, release);
		
		Vehicle blue_vertical_1 = new VerticalVehicle(new Point(0, 4), 2, "img/2x_blue_vertical.png",
				click, drag, release);
		Vehicle blue_vertical_2 = new VerticalVehicle(new Point(1, 3), 3, "img/3x_blue_vertical.png",
				click, drag, release);
		Vehicle blue_vertical_3 = new VerticalVehicle(new Point(2, 4), 2, "img/2x_blue_vertical.png",
				click, drag, release);
		Vehicle blue_vertical_long = new VerticalVehicle(new Point(1, 1), 3, "img/2x_blue_vertical.png",
				click, drag, release);
		Vehicle purple_vertical_long = new VerticalVehicle(new Point(4, 1), 2, "img/2x_blue_vertical.png",
				click, drag, release);

		newGrid.addVehicle(red);	
		newGrid.addVehicle(blue_vertical_long);
		newGrid.addVehicle(yellow_vertical_long);
		newGrid.addVehicle(purple_vertical_long);	
		newGrid.addVehicle(orange_vertical);	
		newGrid.addVehicle(blue_horizontal);
		newGrid.addVehicle(green_horizontal);
		newGrid.addVehicle(green_horizontal_long);
		newGrid.addVehicle(blue_vertical_1);
		newGrid.addVehicle(blue_vertical_2);
		newGrid.addVehicle(blue_vertical_3);
		
		loadImage();
		
		return newGrid;
	}*/
	
	/**
	 * Creates a randomly generated puzzle.
	 * Logic is as followed:
	 * <ul>
	 * <li>Red(Goal) Vehicle is inserted with a randomised row position.</li>
	 * <li>For all other vehicles, alignment, position, size, and colour is randomised</li>
	 * <li>Prechecks if position is valid; if not, destroy current vehicle and try again</li>
	 * <li>such vehicle is inserted to grid. Check if grid is solvable, if true, continue; else, destroy vehicle</li>
	 * <li>Check if puzzle is solvable with at least minMoves. If true, return.</li>
	 * <li>We might meet a deadlock where there are no ways to add any more vehicles.
	 * 		In such case, keep trying. If addition of vehicle fails 10 times, start entirely over</li>
	 * </ul>
	 * The greater the the value of minMoves, the longer it takes to produce a randomised puzzle.
	 * @return newGrid, newGrid is solvable
	 */
	public Grid randomPuzzle() {
		
		newGrid = new Grid();
		
		// r sets the random row position of the red vehicle
		int r = (int) (Math.random()*4);
		Vehicle red = new GoalVehicle(new Point(r, 2), new Point(4, 2), 2, "img/red.png", "music/drag1.wav");
		
		newGrid.addVehicle(red);
		
		// the following is used for randomising the colours of the vehicles
		String[] v2 = {"img/2x_blue_vertical.png", "img/2x_orange_vertical.png", "img/2x_pink_vertical.png"};
		String[] v3 = {"img/3x_blue_vertical.png", "img/3x_purple_vertical.png", "img/3x_yellow_vertical.png"};
		String[] h2 = {"img/2x_black_horizontal.png", "img/2x_blue_horizontal.png", "img/2x_lime_horizontal.png"};
		String[] h3 = {"img/3x_blue_horizontal.png", "img/3x_green_horizontal.png"};
		
		String[] sounds = {"music/drag1.wav", "music/drag2.wav", "music/drag3.wav", "music/drag4.wav"};
		
		// initial moves is always 1 for the red vehicle to go to the goal state
		int numMoves = 1;
		int failedCount = 0;
		// start of the loop
		while(numMoves < this.minMoves) {
			// size ranges from 2 to 3
			int size = (int) (Math.random()*2)+2;
			// for alignment, 0 implies horizontal, 1 implies vertical
			int alignment = (int) (Math.random()*2);
			int xPosition = (int) (Math.random()*5);
			int yPosition = (int) (Math.random()*5);
			// prechecking validity of position of vehicle
			if(xPosition == 4 && size == 3) xPosition = 3;
			if(yPosition == 4 && size == 3) yPosition = 3;
			if(alignment == 0 && yPosition == 2) yPosition++;
			Vehicle v;
			// isValid checks if position is valid
			boolean isValid = true;
			// random colour assigning value for the vehicle
			int colour = (int) (Math.random()*3);
			// random sound assigning value for the vehicle
			int sound = (int) (Math.random()*4);

//			System.out.println("sound: "+sound);

			if(alignment == 1) {
				if(size == 2)
					v = new VerticalVehicle(new Point(xPosition, yPosition), size, v2[colour], sounds[sound]);
				else 
					v = new VerticalVehicle(new Point(xPosition, yPosition), size, v3[colour], sounds[sound]);
				// logic that checks if position is valid
				for(int j = yPosition; j < yPosition+size; j++) {
					if(newGrid.getVehicle(new Point(xPosition,j)) != null) {
						isValid = false;
						break;
					}
				}
			}
			// case for horizontal vehicles. Logic is virtually the same as above
			else {
				if(size == 2)
					v = new HorizontalVehicle(new Point(xPosition, yPosition), size, h2[colour], sounds[sound]);
				else {
					if(colour == 2) colour = (int) (Math.random()*2);
					v = new HorizontalVehicle(new Point(xPosition, yPosition), size, h3[colour], sounds[sound]);
				}
				for(int j = xPosition; j < xPosition+size; j++) {
					if(newGrid.getVehicle(new Point(j,yPosition)) != null) {
						isValid = false;
						break;
					}
				}
			}
			// if position is not valid, use new random vehicle
			if(isValid == false) {
				failedCount++;
				System.out.println("GOTTEM");
				// at this point, we assume there are no valid positions for vehicles to be inserted. try again
				if(failedCount == 10) return randomPuzzle();
				continue;
			}
			
			// vehicle position is valid. now checks if puzzle is solvable
			Grid testGrid = newGrid.clone();
			testGrid.addVehicle(v);
			SearchStrategy s = new BreadthFirstSearch();
			States states = s.search(testGrid);
			// if puzzle solvable, use the vehicle and continue
			if(states != null) {
				newGrid = testGrid;
				numMoves = states.getStateHistorySize();
				failedCount = 0;
			} else {
				// puzzle not solvable, do not add vehicle and go back
				failedCount++;
				System.out.println("GOTTEM AGAIN");
				if(failedCount == 10) return randomPuzzle();
			}
		}
		loadImage();
		loadSound();
		return newGrid;
	}
	
	/**
	 * Read image of all vehicles after map is generated
	 */
	public void loadImage() {
		for(Vehicle v: this.newGrid.getVehicles()) {
			try {
			    File pathToFile = new File(v.imagePath);
			    v.image = ImageIO.read(pathToFile);
			} catch (IOException ex) {
				System.err.println(v.imagePath);
			    ex.printStackTrace();
			}
		}
	}
	
	public void loadSound() {
		for(Vehicle v: this.newGrid.getVehicles()) {
			try {
				File sound = new File(v.soundPath);
				// Set up an audio input stream piped from the sound file.
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
				DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
				// Get a clip resource.
				Clip clip = (Clip) AudioSystem.getLine(info);
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioInputStream);
				v.drag = new SoundEffect();
				v.drag.setClip(clip);
			}
			catch(UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			catch(LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	
}
