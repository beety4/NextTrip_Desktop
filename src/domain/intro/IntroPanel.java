package domain.intro;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.api.ApiService;
import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.review.ShowReviewPanel;
import domain.search.TourSpotDTO;
import domain.search.TourSpotSearchDTO;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		setBackground(new Color(249, 252, 255));
		CustomUtility cu = new CustomUtility();
		CustomSession session = new CustomSession();
		
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		
		TourSpotPanel tourSpot1 = new TourSpotPanel(win);
		tourSpot1.setBounds(44, 10, 270, 280);
		add(tourSpot1);	
		
		TourSpotPanel tourSpot2 = new TourSpotPanel(win);
		tourSpot2.setBounds(377, 10, 270, 280);
		add(tourSpot2);	
		
		TourSpotPanel tourSpot3 = new TourSpotPanel(win);
		tourSpot3.setBounds(709, 10, 270, 280);
		add(tourSpot3);	
		
		TourSpotPanel tourSpot4 = new TourSpotPanel(win);
		tourSpot4.setBounds(44, 300, 270, 280);
		add(tourSpot4);	
		
		TourSpotPanel tourSpot5 = new TourSpotPanel(win);
		tourSpot5.setBounds(377, 300, 270, 280);
		add(tourSpot5);	
		
		TourSpotPanel tourSpot6 = new TourSpotPanel(win);
		tourSpot6.setBounds(709, 300, 270, 280);
		add(tourSpot6);	

	}
}
