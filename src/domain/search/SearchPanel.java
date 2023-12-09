package domain.search;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table;
	
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
	public SearchPanel(JFrame win) {
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		
		
		JLabel lblNewLabel = new JLabel("검색 ");
		lblNewLabel.setBounds(76, 23, 102, 61);
		add(lblNewLabel);
		
		

		
		
		
		
		

	}

}
