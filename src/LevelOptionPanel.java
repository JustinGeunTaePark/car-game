
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * This class defines an level option panel 
 * where user could choose different difficulties
 * of the game based on their own level
 * 
 * @author Jerry Xu
 *
 */
public class LevelOptionPanel {

	private JFrame frame;
	private StartMenu menu;
	private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 25);
	
	/**
	 * constructor
	 * @param menu		object of StartMenu
	 */
	public LevelOptionPanel(StartMenu menu) {
		this.menu = menu;
		frame = new JFrame("LevelOptionPanel");
		createAndShowUI();
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setUndecorated(true);
	    frame.pack();
	    frame.setLocationRelativeTo(menu);  
	}
	
	/**
	 * 
	 * @return	option window
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * Displays this option window on top of the current window.
	 */
	public void createAndShowUI() {
		
		// create all radio buttons
		JRadioButton easyButton = new JRadioButton("Easy");
		easyButton.setFont(BUTTON_FONT);
		easyButton.setForeground(Color.green);
		easyButton.setBackground(Color.DARK_GRAY);
		easyButton.setPreferredSize(new Dimension(100, 60));
		if(menu.getLevel() == GameOptions.Level.Easy) easyButton.setSelected(true);
		
		JRadioButton mediumButton = new JRadioButton("Medium");
		mediumButton.setFont(BUTTON_FONT);
		mediumButton.setForeground(Color.blue);
		mediumButton.setBackground(Color.DARK_GRAY);
		mediumButton.setSelected(false);
		mediumButton.setPreferredSize(new Dimension(100, 60));
		if(menu.getLevel() == GameOptions.Level.Medium) mediumButton.setSelected(true);
		
		JRadioButton hardButton = new JRadioButton("Hard");
		hardButton.setFont(BUTTON_FONT);
		hardButton.setForeground(Color.orange);
		hardButton.setBackground(Color.DARK_GRAY);
		hardButton.setSelected(false);
		hardButton.setPreferredSize(new Dimension(100, 60));
		if(menu.getLevel() == GameOptions.Level.Hard) hardButton.setSelected(true);
		
		JRadioButton veryHardButton = new JRadioButton("Very Hard");
		veryHardButton.setFont(BUTTON_FONT);
		veryHardButton.setForeground(Color.red);
		veryHardButton.setBackground(Color.DARK_GRAY);
		veryHardButton.setSelected(false);
		veryHardButton.setPreferredSize(new Dimension(100, 60));
		if(menu.getLevel() == GameOptions.Level.VeryHard) veryHardButton.setSelected(true);
		
		// group all buttons
		ButtonGroup group = new ButtonGroup();
		group.add(easyButton);
		group.add(mediumButton);
		group.add(hardButton);
		
		// put the radio buttons in a column in a panel
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.setPreferredSize(new Dimension(160, 280));
        radioPanel.add(easyButton);
        radioPanel.add(mediumButton);
        radioPanel.add(hardButton);
        radioPanel.add(veryHardButton);
        
        // set layout for content pane
        // and add all components to it
        Container contentPane = this.frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(radioPanel, BorderLayout.CENTER);
        
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(70, 50));
        leftPanel.setBackground(Color.DARK_GRAY);
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(70, 50));
        rightPanel.setBackground(Color.DARK_GRAY);
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(rightPanel, BorderLayout.EAST);
        
        // create a button event listener
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == easyButton)
					menu.setLevel(GameOptions.Level.Easy);
				else if(e.getSource() == mediumButton)
					menu.setLevel(GameOptions.Level.Medium);
				else if(e.getSource() == hardButton)
					menu.setLevel(GameOptions.Level.Hard);
				else
					menu.setLevel(GameOptions.Level.VeryHard);
				menu.setTitle(menu.getMode()+" GridLock"+" ("+menu.getLevel()+")");
				menu.setEnabled(true);
				frame.dispose();
			}	
		};
		
		// register the listener for all the buttons
		easyButton.addActionListener(buttonListener);
		mediumButton.addActionListener(buttonListener);
		hardButton.addActionListener(buttonListener);
		veryHardButton.addActionListener(buttonListener);
	}
	
}
