
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class defines a main graphical user interface where it holds a game
 * board with draggable vehicles and some buttons might be helpful to users
 * during the game.
 *
 */
public class GUIGame {

	private static final int FRAME_WIDTH = 600;
	private static final int FOOT_WIDTH = FRAME_WIDTH;
	private static final int BOARD_WIDTH = FRAME_WIDTH;

	private static final int FOOT_HEIGHT = 100;
	private static final int FRAME_HEIGHT = FRAME_WIDTH + FOOT_HEIGHT;
	private static final int BOARD_HEIGHT = FRAME_HEIGHT - FOOT_HEIGHT;

	private static final int HEAD_WIDTH = FRAME_WIDTH;
	private static final int HEAD_HEIGHT = 50;
	
	private static final Color FOOTER_BACKGROUND = Color.DARK_GRAY;
	private static final int FOOT_VERTICAL_PAD = 10;
	private static final int FOOT_HORIZONTAL_PAD = 10;
	
	private static final int BUTTON_HEIGHT = FOOT_HEIGHT - 2 * FOOT_VERTICAL_PAD;
	private static final int BUTTON_WIDTH(int num) { 
		return (FOOT_WIDTH - 2 * FOOT_HORIZONTAL_PAD) / (num + 1); 
	}
	
	private static final Font BUTTON_FONT = new Font("Time", Font.ITALIC, 20);

	// the grid object where the cars are held
	GUIGrid screenGrid;

	// main window frame
	private JFrame frame;
	private JFrame menu;

	// buttons for the game
	private JButton resetButton;
	private JButton hintButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton volumeButton;
	
	private JLabel textField;
	
	protected GameOptions.Level level;
	
	/**
	 * Constructor for GUIGame.
	 * @param level
	 * 				level must be Easy, Medium, Hard or Very Hard
	 * @param menu
	 * 				menu != null
	 */
	public GUIGame(GameOptions.Level level, JFrame menu) {
		this.level = level;
		this.menu = menu;
		initUI();
	}

	/**
	 * Returns the game frame.
	 * @return
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * Initialises the game user interface instance.
	 */
	public void initUI() {

		// create a new window frame
		this.frame = new JFrame();

		// get the main container panel within the window
		Container contentPane = frame.getContentPane();

		// content pane is managed by BorderLayout manager
		contentPane.setLayout(new BorderLayout());

		JPanel headPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		headPanel.setPreferredSize(new Dimension(HEAD_WIDTH, HEAD_HEIGHT));
		this.textField = new JLabel("<html><br>GridLock</html>", SwingConstants.CENTER);
		this.textField.setFont(new Font("Arial", Font.BOLD, 18));
		headPanel.add(this.textField);
		
		// the bottom panel where the buttons and other information will be
		JPanel footPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, FOOT_HORIZONTAL_PAD, FOOT_VERTICAL_PAD));
		
		footPanel.setBackground(FOOTER_BACKGROUND);
		footPanel.setPreferredSize(new Dimension(FOOT_WIDTH, FOOT_HEIGHT));

		contentPane.add(headPanel, BorderLayout.NORTH);
		contentPane.add(footPanel, BorderLayout.SOUTH);

		// create the buttons
		resetButton = new JButton("reset");
		hintButton = new JButton("hint");
		undoButton = new JButton("undo");
		redoButton = new JButton("redo");
		volumeButton = new JButton("off");
		
		// by default, disable the reset, undo, and redo buttons
		resetButton.setEnabled(false);
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		volumeButton.setEnabled(true);

		// add the buttons to the bottom panel
		JButton buttons[] = { resetButton, hintButton, undoButton, redoButton };
		
		Dimension buttonDimension = new Dimension(BUTTON_WIDTH(buttons.length), BUTTON_HEIGHT);
		
		for (JButton b : buttons) {
			b.setPreferredSize(buttonDimension);
			b.setFont(BUTTON_FONT);
			footPanel.add(b);
		}
		
		// create the on screen grid object
		Dimension layeredPaneSize = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
		GUIGrid screenGrid = new GUIGrid(layeredPaneSize, this.level);

		// get the board from GUI Grid and add it to the current frame/window
		JLayeredPane board = screenGrid.getBoard();
		contentPane.add(board);

		GUIMouseAdapter mouseListener = new GUIMouseAdapter();

		board.addMouseListener(mouseListener);
		board.addMouseMotionListener(mouseListener);
		
		resetButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				screenGrid.resetGrid();
				resetButton.setEnabled(false);
				undoButton.setEnabled(false);
				redoButton.setEnabled(false);
				frame.revalidate();
				frame.repaint();
			}
			
		});
		
		undoButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				screenGrid.undo();
				if(screenGrid.getStates().getTotalMovesDone() == 0) {
					resetButton.setEnabled(false);
					undoButton.setEnabled(false);
				}
				redoButton.setEnabled(true);
				frame.revalidate();
				frame.repaint();
			}
			
		});
		
		redoButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				screenGrid.redo();
				if(screenGrid.getStates().getTotalMovesDone() == screenGrid.getStates().getStateHistorySize()-1) {
					redoButton.setEnabled(false);
				}
				resetButton.setEnabled(true);
				undoButton.setEnabled(true);
				frame.revalidate();
				frame.repaint();
			}
			
		});
		
		hintButton.addActionListener(new ActionListener() {
			SearchStrategy s = new BreadthFirstSearch();
			@Override
			public void actionPerformed(ActionEvent e) {
				States state = s.search(screenGrid.getStates().getCurrentGrid());
				int a = state.getTotalMovesDone();
				textField.setText("<html><br/>number of moves left: "+(a+1)+"</html>");
				// maybe have an option that enables the followings
				if(a == 0) return;
				screenGrid.addGrid(state.getNextGrid());
				resetButton.setEnabled(true);
				undoButton.setEnabled(true);
				redoButton.setEnabled(false);
				frame.revalidate();
				frame.repaint();
			}
		});

		frame.setTitle("GridLock"+"("+this.level+")");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null);

		frame.pack();

		this.screenGrid = screenGrid;
	}

	/**
	 * Mouse adapter class for the current frame, dealing with specifics regarding
	 * moving the car around the grid.
	 */
	private class GUIMouseAdapter extends MouseAdapter {

		private JLabel car;

		@Override
		public void mousePressed(MouseEvent me) {
			this.car = screenGrid.startMove(me.getPoint());
		}

		@Override
		public void mouseDragged(MouseEvent me) {			
			// move the car
			screenGrid.newMove(car, me.getPoint());
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			int initial = screenGrid.getStates().getCurrentGridIndex();
			if(SoundEffect.isSoundAvailable) {
				SoundEffect finished = screenGrid.finishMove(car, level);
				this.car = null;
				int after = screenGrid.getStates().getCurrentGridIndex();
				if(initial != after) {
					resetButton.setEnabled(true);
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
				}
				if(finished != null) {
					finished.stopPlay();
					frame.dispose();
					//menu.setVisible(true);
				}
			}
			else {
				boolean finished = screenGrid.boolFinishMove(car, level);
				this.car = null;
				int after = screenGrid.getStates().getCurrentGridIndex();
				if(initial != after) {
					resetButton.setEnabled(true);
					undoButton.setEnabled(true);
					redoButton.setEnabled(false);
				}
				if(finished) {
					frame.dispose();
					//menu.setVisible(true);
				}
			}
		}
	}
}
