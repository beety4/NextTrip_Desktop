package domain.intro;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;

public class IntroPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	

	
	/**
	 * Create the panel.
	 */
	public IntroPanel(JFrame win) {
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(76, 23, 102, 61);
		add(lblNewLabel);
		
		
		
		
				
		
		
		
		
		
		
		
		

	}

}
