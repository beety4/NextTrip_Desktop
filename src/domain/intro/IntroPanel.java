package domain.intro;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;

public class IntroPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	
	@Override
	public void paintComponent(Graphics g) {
		Image bg = new ImageIcon("src/resource/backgroundimage.png <-- required").getImage();
		g.drawImage(bg, 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	/**
	 * Create the panel.
	 */
	public IntroPanel(JFrame win) {
		CustomUtility cu = new CustomUtility();
		CustomSession session = new CustomSession();
		
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		
		
		
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(235, 112, 50, 15);
		add(lblNewLabel);
		

	}

}
