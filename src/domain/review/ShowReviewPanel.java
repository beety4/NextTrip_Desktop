package domain.review;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;

public class ShowReviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel lblNewLabel = new JLabel("제목");
	private JLabel lblNewLabel_1 = new JLabel("작성일자");
	private JScrollPane scrollPane = new JScrollPane();
	private JLabel lblNewLabel_2 = new JLabel("게시글");
	private JButton btnNewButton = new JButton("좋아요");
	private JScrollPane scrollPane_1 = new JScrollPane();
	private JTextArea textArea = new JTextArea();
	private JButton btnNewButton_1 = new JButton("전송");

	/**
	 * Create the panel.
	 */
	public ShowReviewPanel(JFrame win, int reviewNo) {
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		
		
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewDTO reviewDTO = reviewDAO.getReviewDetail(reviewNo);
		
		
		

		
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(80, 43, 429, 52);
		add(lblNewLabel);
		
		
		
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(590, 43, 429, 52);
		add(lblNewLabel_1);
		
		
		scrollPane.setBounds(80, 113, 939, 291);
		add(scrollPane);
		
		
		scrollPane.setViewportView(lblNewLabel_2);
		
		
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 13));
		btnNewButton.setBounds(910, 412, 110, 36);
		add(btnNewButton);
		
		
		scrollPane_1.setBounds(80, 483, 940, 115);
		add(scrollPane_1);
		
		
		textArea.setBounds(80, 608, 829, 52);
		add(textArea);
		
		
		btnNewButton_1.setBounds(928, 608, 91, 52);
		add(btnNewButton_1);
		
		

	}

}
