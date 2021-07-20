import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The SingleUserStory class implements the single player story mode.
 * 
 *
 */

public class SingleUserStory extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JLabel slidesLabel = new JLabel();
    private Icon[] icons;
    private int currentSlide = -1;

    /**
     * Standard constructor to instantiate the SingleUserStory.
     */
    public SingleUserStory() {
        try {
            // Personally, I'd use File#listFiles to list all the
            // images in a directory, but that might be consider
            // using our initiative...
            icons = new Icon[]{
                new ImageIcon(ImageIO.read(new File("img/background.jpg"))),
                new ImageIcon(ImageIO.read(new File("img/red.png"))),
                new ImageIcon(ImageIO.read(new File("img/Blue_block.png")))
            };
            slidesLabel.setVerticalAlignment(JLabel.CENTER);
            slidesLabel.setHorizontalAlignment(JLabel.CENTER);
            setLayout(new BorderLayout());
            add(slidesLabel, BorderLayout.CENTER);
            slidesLabel.addMouseListener(new ClickListener());
            //nextSlide();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    /**
     * Increments the current picture slide to the next one.
     */
    public void nextSlide() {
        if (currentSlide < icons.length - 1) {
            currentSlide++;
            slidesLabel.setIcon(icons[currentSlide]);
        }
    }



    public class ClickListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            nextSlide();
        }
    }

    public class KeyListener extends KeyAdapter {

        public void keyClicked(KeyEvent e) {
            System.exit(0);
        }
    }
	
}
