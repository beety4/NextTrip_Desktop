package domain.review;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private String[] columnType = {"번호" ,"제목", "지역" ,"작성일", "조회수"};
	private JTextField tbxSearch;
	

	
	
	
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
		
		
		// 1페이지 리뷰 게시판 10개 항목 Data 가져오기
		ReviewDAO reviewDAO = new ReviewDAO();
		ArrayList<ReviewDTO> reviewList = reviewDAO.getReviewList(1, 0, null);
		String[][] tableData = cUtils.listToArray(reviewList);
		
		
		JTable mainTable = new JTable(tableData, columnType);
		mainTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = mainTable.getSelectedRow();
				int reviewNo = Integer.parseInt(mainTable.getValueAt(selectRow, 0).toString());
				win.setContentPane(new ShowReviewPanel(win, reviewNo));
				win.revalidate();
			}
		});
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mainTable.setBounds(82, 89, 935, 551);
		add(mainTable);
		
		
		
		
		
		
		JButton btnSearch = new JButton("검색");
		btnSearch.setFont(new Font("굴림", Font.PLAIN, 15));
		btnSearch.setBounds(919, 42, 98, 37);
		add(btnSearch);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("굴림", Font.PLAIN, 15));
		comboBox.setBounds(495, 42, 129, 37);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"제목", "본문", "제목+본문"}));
		add(comboBox);
		
		tbxSearch = new JTextField();
		tbxSearch.setFont(new Font("굴림", Font.PLAIN, 15));
		tbxSearch.setBounds(636, 42, 275, 38);
		add(tbxSearch);
		tbxSearch.setColumns(10);
		
		

		JButton btnAddReview = new JButton("글 작성");
		btnAddReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				win.setContentPane(new AddReviewPanel(win));
				win.revalidate();
			}
		});
		btnAddReview.setFont(new Font("굴림", Font.PLAIN, 15));
		btnAddReview.setBounds(82, 42, 98, 37);
		add(btnAddReview);
		
		
		
		

	}
}
