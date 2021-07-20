import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * ImagePanel class to create a JPanel for an image.
 *
 */
class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image;

	/**
	 * Constructor for the ImagePanel
	 * @param image		image != null
	 */
	public ImagePanel(Image image) {
		this.image = image;
	}

	/**
	 * Draws a component onto the JPanel.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
}