
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * This class defines a initial user interface where player can choose their
 * favorites such as music, difficulties before starting the game.
 *
 */
public class StartMenu extends JFrame {

	/**
	 * "Used during de-serialization to verify that the sender and receiver of a
	 * serialized object have loaded classes for that object that are
	 * compatible" - Java Docs
	 */
	private static final long serialVersionUID = 6535414327709394946L;

	private static final String WINDOW_TITLE = "Gridlock";
	private static final int X_DIMENSION = 700;
	private static final int Y_DIMENSION = 700;
	
	private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);
	private static final Color BUTTON_FOREGROUND_COLOR = Color.black;
	
	private static final int X_PADDING = 100;
	private static final int Y_PADDING = 300;
	
	private LevelOptionPanel lvlOptPanel;
	private ModeOptionPanel modeOptPanel;
	
	private GameOptions.Level level;
	private GameOptions.Mode mode;
	
	private JButton soundButton;
	
	/**
	 * Constructor to instantiate the StartMenu object.
	 */
	public StartMenu() {

		// default level
		setLevel(GameOptions.Level.Easy);
		// default mode
		setMode(GameOptions.Mode.Standard);
		
		setupWindow();
		
		JButton[] buttons = createButtons();

		createLayout(buttons);

		pack();
	}
	/**
	 * Creates and sets up the game window.
	 */
	private void setupWindow() {
		setTitle(WINDOW_TITLE);
		setSize(X_DIMENSION, Y_DIMENSION);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Binds a title and an ActionListener to a button, and returns the new button.
	 * @param title
	 * 			title != null
	 * @param a
	 * 			a != null
	 * @return
	 */
	private JButton createButton(String title, ActionListener a) {
		JButton newButton = new JButton(title);

		// Bind an anonymous inner class of listener to each button, implicitly
		// implement the
		// listener interface and override the method that response when target
		// event is triggered.
		
		newButton.addActionListener(a);
		newButton.setFont(BUTTON_FONT);
		newButton.setForeground(BUTTON_FOREGROUND_COLOR);

		return newButton;
	}

	/**
	 * Creates the start menu buttons.
	 * @return
	 */
	private JButton[] createButtons() {
		
		ActionListener defaultListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(getMode() == GameOptions.Mode.Standard) {
					GUIGame gui = new GUIGame(getLevel(), StartMenu.this);
					gui.getFrame().setVisible(true);
					//StartMenu.this.setVisible(false);
				}
				else {
					dispose();
					EventQueue.invokeLater(() -> {
						PictureSlides t = new PictureSlides(level);
						t.setVisible(true);
					});
				}
			}
		};
		
		ActionListener levelListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setEnabled(false);
				// need to keep a StartMenu object in LevelOptionPanel
				// so that the game level selected by user is saved and updated
				// once user back to start menu from option panel
				lvlOptPanel = new LevelOptionPanel(StartMenu.this);
				lvlOptPanel.getFrame().setVisible(true);
			}		
		};
		
		ActionListener modeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				modeOptPanel = new ModeOptionPanel(StartMenu.this);
				modeOptPanel.getFrame().setVisible(true);
			}
		};
		
		
		ActionListener soundListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(SoundEffect.volume == SoundEffect.Volume.NORMAL) {
					SoundEffect.volume = SoundEffect.Volume.MUTE;
					soundButton.setText("Sound Off");
				}
				else {
					SoundEffect.volume = SoundEffect.Volume.NORMAL;
					soundButton.setText("Sound On");
				}
			}
		};
		this.soundButton = createButton("Sound Off", soundListener);
		
		JButton startButton = createButton("Start Game", defaultListener);
		JButton modeButton = createButton("Choose Mode", modeListener);
		JButton levelButton = createButton("Choose Level", levelListener);
		JButton buttons[] = { startButton, levelButton, modeButton, soundButton };

		return buttons;
	}

	/*
	 * Create a game menu with multiple buttons
	 */
	/**
	 * Creates the layout of the start menu and adds the buttons.
	 * @param buttons
	 * 				 buttons != null
	 */
	private void createLayout(JButton[] buttons) {
		
		Dimension size = new Dimension(X_DIMENSION, Y_DIMENSION);

		JLayeredPane mainPane = new JLayeredPane();
		
		mainPane.setOpaque(true);
		mainPane.setPreferredSize(size);
		mainPane.setBounds(0, 0, size.width, size.height);

		setLayout(new BorderLayout());
		
		// get the image
		File imageFile = new File("img/background.jpg");
		Image image = null;
		
		try {
			image = ImageIO.read(imageFile);
			image = image.getScaledInstance(X_DIMENSION, Y_DIMENSION, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageIcon scaledImage = new ImageIcon(image);
		JLabel imageLabel = new JLabel(scaledImage);
		
		// now build the button layer
		JPanel buttonPanel = new JPanel(new GridLayout(5, 0));
		buttonPanel.setPreferredSize(size);
		buttonPanel.setOpaque(false);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(Y_PADDING, X_PADDING, 0, X_PADDING));
		
		for (int i = 0; i < buttons.length; i++) {
			JButton button = buttons[i];
			buttonPanel.add(button);
		}
		
		mainPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
		mainPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
		
		imageLabel.setBounds(0, 0, X_DIMENSION, Y_DIMENSION);
		buttonPanel.setBounds(0, 0, X_DIMENSION, Y_DIMENSION);

		this.setContentPane(mainPane);
		
		setTitle(getMode()+" GridLock"+" ("+getLevel()+")");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(X_DIMENSION, Y_DIMENSION);
		setLocationRelativeTo(null);
	}

	/**
	 * Returns the current difficulty.
	 * @return
	 */
	public GameOptions.Level getLevel() {
		return level;
	}

	/**
	 * Sets the level of difficulty
	 * @param level
	 */
	public void setLevel(GameOptions.Level level) {
		this.level = level;
	}

	/**
	 * Returns the current game mode.
	 * @return
	 */
	public GameOptions.Mode getMode() {
		return mode;
	}

	/**
	 * Sets the current game mode.
	 * @param mode
	 * 				mode = Standard or mode = Story
	 */
	public void setMode(GameOptions.Mode mode) {
		this.mode = mode;
	}
}
