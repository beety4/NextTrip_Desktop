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
import domain.search.TourSpotDTO;
import domain.search.TourSpotSearchDTO;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

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
		
		ApiService apiService = new ApiService();
		ArrayList<TourSpotDTO> tourSpotList = apiService.getIndexTour();
		
	
		
		
		
		JLabel lblAddr6 = new JLabel("위치");
		lblAddr6.setFont(new Font("굴림", Font.PLAIN, 15));
		lblAddr6.setBounds(713, 538, 172, 27);
		add(lblAddr6);
		
		JLabel lblPlace6 = new JLabel("장소");
		lblPlace6.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace6.setBounds(713, 501, 172, 27);
		add(lblPlace6);
		
		JLabel lblAddr1 = new JLabel("위치");
		lblAddr1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblAddr1.setBounds(65, 237, 172, 27);
		add(lblAddr1);
		
		JLabel lblPlace1 = new JLabel("장소");
		lblPlace1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace1.setBounds(65, 200, 172, 27);
		add(lblPlace1);
		
		JLabel lblPhoto1 = new JLabel("사진");
		lblPhoto1.setBounds(55, 0, 274, 185);
		add(lblPhoto1);
		
		JButton btn1 = new JButton("");
		btn1.setBounds(55, 0, 274, 281);
		add(btn1);
		
		JLabel lblPhoto2 = new JLabel("사진");
		lblPhoto2.setBounds(379, 0, 274, 185);
		add(lblPhoto2);
		
		JLabel lblPlace2 = new JLabel("장소");
		lblPlace2.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace2.setBounds(389, 200, 172, 27);
		add(lblPlace2);
		
		JLabel lblAddr2 = new JLabel("위치");
		lblAddr2.setFont(new Font("굴림", Font.PLAIN, 15));
		lblAddr2.setBounds(389, 237, 172, 27);
		add(lblAddr2);
		
		JButton btn2 = new JButton("");
		btn2.setBounds(379, 0, 274, 281);
		add(btn2);
		
		JLabel lblPhoto3 = new JLabel("사진");
		lblPhoto3.setBounds(703, 0, 274, 185);
		add(lblPhoto3);
		
		JLabel lblPlace3 = new JLabel("장소");
		lblPlace3.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace3.setBounds(713, 200, 172, 27);
		add(lblPlace3);
		
		JLabel lblAddr3 = new JLabel("위치");
		lblAddr3.setFont(new Font("굴림", Font.PLAIN, 15));
		lblAddr3.setBounds(713, 237, 172, 27);
		add(lblAddr3);
		
		JLabel lblPhoto4 = new JLabel("사진");
		lblPhoto4.setBounds(55, 301, 274, 185);
		add(lblPhoto4);
		
		JLabel lblPlace4 = new JLabel("장소");
		lblPlace4.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace4.setBounds(65, 501, 172, 27);
		add(lblPlace4);
		
		JLabel lblAddr4 = new JLabel("위치");
		lblAddr4.setFont(new Font("굴림", Font.PLAIN, 15));
		lblAddr4.setBounds(65, 538, 172, 27);
		add(lblAddr4);
		
		JLabel lblPhoto5 = new JLabel("사진");
		lblPhoto5.setBounds(379, 301, 274, 185);
		add(lblPhoto5);
		
		JLabel lblPlace5 = new JLabel("장소");
		lblPlace5.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace5.setBounds(389, 501, 172, 27);
		add(lblPlace5);
		
		JLabel lblAddr5 = new JLabel("위치");
		lblAddr5.setFont(new Font("굴림", Font.PLAIN, 15));
		lblAddr5.setBounds(389, 538, 172, 27);
		add(lblAddr5);
		
		JLabel lblPhoto6 = new JLabel("사진");
		lblPhoto6.setBounds(703, 301, 274, 185);
		add(lblPhoto6);
		
		JButton btn6 = new JButton("");
		btn6.setBounds(703, 301, 274, 281);
		add(btn6);
		
		JButton btn3 = new JButton("");
		btn3.setBounds(703, 0, 274, 281);
		add(btn3);
		
		JButton btn5 = new JButton("");
		btn5.setBounds(379, 301, 274, 281);
		add(btn5);
		
		JButton btn4 = new JButton("");
		btn4.setBounds(55, 301, 274, 281);
		add(btn4);
		

	}
}
