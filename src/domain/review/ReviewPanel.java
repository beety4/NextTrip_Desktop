package domain.review;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

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
import javax.swing.table.DefaultTableModel;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import javax.swing.ListSelectionModel;

public class ReviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel tabName = new JLabel("리뷰 게시판");
	private String[] columnType = {"번호" ,"제목", "지역" ,"작성일", "조회수"};
	private JTextField textField;
	

	
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
	public ReviewPanel(JFrame win) {
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		tabName.setBounds(138, 50, 102, 61);
		add(tabName);
		
		// 1페이지 리뷰 게시판 10개 항목 Data 가져오기
		ReviewDAO reviewDAO = new ReviewDAO();
		ArrayList<ReviewDTO> reviewList = reviewDAO.getReviewList(1, 0, null);
		String[][] tableData = cUtils.listToArray(reviewList);
		
		
		JTable mainTable = new JTable(tableData, columnType);
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mainTable.setBounds(82, 89, 935, 551);
		add(mainTable);
		
		
		
		
		
		
		JButton btnNewButton = new JButton("검색");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton.setBounds(919, 42, 98, 37);
		add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("굴림", Font.PLAIN, 15));
		comboBox.setBounds(495, 42, 129, 37);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"제목", "본문", "제목+본문"}));
		add(comboBox);
		
		textField = new JTextField();
		textField.setFont(new Font("굴림", Font.PLAIN, 15));
		textField.setBounds(636, 42, 275, 38);
		add(textField);
		textField.setColumns(10);
		
		

		JButton btnNewButton_1 = new JButton("글 작성");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton_1.setBounds(82, 42, 98, 37);
		add(btnNewButton_1);
		
		
		
		

	}
}
