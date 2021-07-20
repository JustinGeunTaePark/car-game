import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.EventQueue;
import java.awt.FlowLayout;
/**
 * This class defines a initial user interface where player can choose their
 * favorites such as music, difficulties before starting the game.
 *
 */
public class PictureSlides extends JFrame {

    /**
     * "Used during de-serialization to verify that the sender and receiver of a
     * serialized object have loaded classes for that object that are
     * compatible" - Java Docs
     */
    private static final long serialVersionUID = 6535414327709394946L;
    
    private static final String WINDOW_TITLE = "Gridlock";
    private static final int X_DIMENSION = 600;
    private static final int Y_DIMENSION = 600;
    
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Color BUTTON_FOREGROUND_COLOR = Color.DARK_GRAY;
    
    private static final int FRAME_WIDTH = 600;
	private static final int FOOT_WIDTH = FRAME_WIDTH;

	private static final int FOOT_HEIGHT = 50;
	private static final int FOOT_VERTICAL_PAD = 10;
	private static final int FOOT_HORIZONTAL_PAD = 20;
	private JButton startButton;
	private JButton nextButton;
	private JButton prevButton;
	private JButton menuButton;

    private int imageNo;
    private GameOptions.Level o;
    private boolean[] progress;
        //Images Path In Array
    private String[] list = {
            "img/intro.jpg",
            "img/stuck1.jpg",
            "img/racetrack1.jpg",
            "img/crowd.jpg",
            "img/racetrack.jpg",
            "img/villian.jpg",
            "img/trophy.jpg"

    };
    
    /**
     * Constructor for the PictureSlide class.
     * @param o
     */
    public PictureSlides(GameOptions.Level o) {
    	this.o = o;
    	setImageNo(0);
    	progress = new boolean[7];
    	for(int i = 0; i < 7; i++) {
    		progress[i] = false;
    	}
    	
    	setupWindow();
    	
    	JButton[] buttons = createButtons();
    	
    	createLayout(buttons, 0);
    	
    	pack();
    }
    
    /**
     * Second constructor for the PictureSlides class.
     * @param images
     * @param o
     * @param progress
     */
    public PictureSlides(int images, GameOptions.Level o, boolean[] progress) {
    	this.o = o;
        // default level
        setImageNo(images);
        this.progress = progress;

        setupWindow();
        
        JButton[] buttons = createButtons();

        createLayout(buttons, images);

        pack();
    }

    /**
     * Sets up a new window for the Picture slides.
     */
    private void setupWindow() {
        setTitle(WINDOW_TITLE);
        setSize(X_DIMENSION, Y_DIMENSION);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Takes a title and an ActionListener and binds them to a button.
     * @param title
     * 				title != null
     * @param a
     * 				a != null
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
     * Creates the buttons for the PictureSlides window and adds the instantiated buttons.
     * @return
     */
    private JButton[] createButtons() {
        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                EventQueue.invokeLater(() -> {
                    StartMenu t = new StartMenu();
                    t.setVisible(true);
                });
            }
        };

        ActionListener nextListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                EventQueue.invokeLater(() -> {
                    int number = 0;
                    if(getImageNo() == list.length-1) {
                        number = getImageNo();
                    } else if(getImageNo() < list.length) {
                        number = getImageNo()+1;
                    }
                    PictureSlides t = new PictureSlides(number, o, progress);
                    t.setVisible(true);
                });
            }
        };

        ActionListener previousListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                EventQueue.invokeLater(() -> {
                    int number = 0;
                    if(getImageNo() == 0) {
                        number = getImageNo();
                    } else {
                        number = getImageNo()-1;
                    }
                    PictureSlides t = new PictureSlides(number, o, progress);
                    t.setVisible(true);
                });
            }
        };

        ActionListener startListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GameOptions.Level level;
        		switch(imageNo) {
        			case 3: level = GameOptions.Level.Easy;
        					break;
        			case 4: level = GameOptions.Level.Medium;
        					break;
        			case 5: level = GameOptions.Level.Hard;
        					break;
        			default: level = GameOptions.Level.Easy;
        					break;
        		}
                GUIGame gui = new GUIGame(level, (JFrame)PictureSlides.this);
                gui.getFrame().setVisible(true);
                //PictureSlides.this.setVisible(false);
                nextButton.setEnabled(true);
                progress[imageNo] = true;
                revalidate();
                repaint();
            }
        };

        startButton = createButton("start", startListener);
        menuButton = createButton("menu", menuListener);
        prevButton = createButton("previous", previousListener);
        nextButton = createButton("next", nextListener);
        JButton buttons[] = { nextButton, prevButton, menuButton, startButton };

        return buttons;
    }

    /**
     * creates the layout of the window for the image and the buttons.
     * @param buttons
     * 				buttons != null
     * @param images
     * 				0 < images < list.len()-1 where list is a list of picture file paths
     */				
    private void createLayout(JButton[] buttons, int images) {
        
        Dimension size = new Dimension(X_DIMENSION, Y_DIMENSION);       
        Container mainPane = getContentPane();
        
        mainPane.setPreferredSize(size);
        mainPane.setLayout(new BorderLayout());
        
        // get the image
        File imageFile = new File(list[images]);
        Image image = null;
        
        try {
            image = ImageIO.read(imageFile);
            image = image.getScaledInstance(X_DIMENSION, Y_DIMENSION-FOOT_HEIGHT, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // create an image panel
        JPanel imagePanel = new ImagePanel(image);  
		
        // now build the button layer
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, FOOT_HORIZONTAL_PAD, FOOT_VERTICAL_PAD));
        buttonPanel.setPreferredSize(new Dimension(FOOT_WIDTH, FOOT_HEIGHT));
        buttonPanel.setBackground(Color.lightGray);
        
        // for the instructions
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, FOOT_HORIZONTAL_PAD, FOOT_VERTICAL_PAD));
        textPanel.setPreferredSize(new Dimension(FOOT_WIDTH, FOOT_HEIGHT));
        textPanel.setBackground(Color.lightGray);
        
        JLabel text = new JLabel("",SwingConstants.CENTER);
        
        // add all buttons to foot panel
        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            buttonPanel.add(button);
        }
        if(imageNo >= 3) {
    		if(progress[imageNo] == false) {
    			nextButton.setEnabled(false);
    		}
    		
    		if(imageNo != list.length-1) {
    			startButton.setEnabled(true);
    		} else {
    			startButton.setEnabled(false);
    		}
	    }
	    else if(imageNo == 0) {
	    		prevButton.setEnabled(false);
	    		startButton.setEnabled(false);
	    }
	    else if(imageNo <= 2) {
	    		startButton.setEnabled(false);
	    }
        
        switch(imageNo) {
	        case 0:
	        case 1:
	        case 2:
	        	text.setText("<html>click next to continue</html>");
	        	break;
	        case 3:
	        	text.setText("<html>press start to play<br>Difficulty: <span color=green>EASY</span></html>");
	        	break;
	        case 4:
	        	text.setText("<html>press start to play<br>Difficulty: <span color=orange>MEDIUM</span></html>");
	        	break;
	        case 5:
	        	text.setText("<html>press start to play<br>Difficulty: <span color=red>HARD</span></html>");
	        	break;
	        case 6:
	        	text.setText("<html>click menu to quit</html>");
	        	break;
	        default:
	        	text.setText("<html>click menu to quit</html>");
	        	break;
        }
        
        text.setFont(new Font("Arial", Font.BOLD, 17));
        textPanel.add(text);
        // add all components to their place in mainPane
        mainPane.add(imagePanel, BorderLayout.CENTER);
        mainPane.add(textPanel, BorderLayout.PAGE_START);
        mainPane.add(buttonPanel, BorderLayout.PAGE_END);
        
    }

    /**
     * returns the index position of the list
     * @return imageNo
     * 
     */    
    public int getImageNo() {
        return imageNo;
    }
    
    /**
     * @param set the index position for the picture list
     * @pre image >= 0
     */
    public void setImageNo(int imageNo) {
        this.imageNo = imageNo;
    }
}
