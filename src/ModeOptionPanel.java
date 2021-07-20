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
 * This class defines a mode option panel where user is free to choose playing standard mode or extended mode
 * @author Jerry Xu
 *
 */
public class ModeOptionPanel {

	
	private JFrame frame;
	private StartMenu menu;
	private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 25);
	
	/**
	 * Constructor for the mode option panel.
	 * @param menu
	 * 				menu != null, ie the start menu needs to be open.
	 */
	public ModeOptionPanel(StartMenu menu) {
		this.menu = menu;
		frame = new JFrame("ModeOptionPanel");
		createAndShowUI();
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setUndecorated(true);
	    frame.pack();
	    frame.setLocationRelativeTo(menu); 
	}
	
	/**
	 * 
	 * @return	this option panel window
	 */
	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * display this option panel
	 */
	public void createAndShowUI() {
		
		// create all radio buttons
		JRadioButton standardBtn = new JRadioButton("Standard");
		standardBtn.setFont(BUTTON_FONT);
		standardBtn.setForeground(Color.blue);
		standardBtn.setPreferredSize(new Dimension(100, 60));
		if(menu.getMode() == GameOptions.Mode.Standard) standardBtn.setSelected(true);
		
		JRadioButton extendedBtn = new JRadioButton("Story");
		extendedBtn.setFont(BUTTON_FONT);
		extendedBtn.setForeground(Color.cyan);
		extendedBtn.setPreferredSize(new Dimension(100, 60));
		if(menu.getMode() == GameOptions.Mode.Story) extendedBtn.setSelected(true);
		
		// group all buttons
		ButtonGroup group = new ButtonGroup();
		group.add(standardBtn);
		group.add(extendedBtn);
		
		// put the radio buttons in a column in a panel
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.setPreferredSize(new Dimension(180, 100));
        radioPanel.add(standardBtn);
        radioPanel.add(extendedBtn);
        
        // set layout for content pane
        // and add all components to it
        Container contentPane = this.frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(radioPanel, BorderLayout.CENTER);
        
        // create a button event listener
        ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == standardBtn)
					menu.setMode(GameOptions.Mode.Standard);
				else
					menu.setMode(GameOptions.Mode.Story);
				menu.setTitle(menu.getMode()+" GridLock"+" ("+menu.getLevel()+")");
				menu.setEnabled(true);
				frame.dispose();
			} 	
        };
        
        // register the listener for all the buttons
        standardBtn.addActionListener(buttonListener);
        extendedBtn.addActionListener(buttonListener);
	}
	
}
