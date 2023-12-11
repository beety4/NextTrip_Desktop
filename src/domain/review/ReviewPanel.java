package domain.review;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import javax.swing.JScrollPane;
import java.awt.Color;

public class ReviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel model = new DefaultTableModel();
	private JButton btnAddReview = new JButton("글 작성");

	private JComboBox comboBox = new JComboBox();
	private JTextField tbxSearch = new JTextField();
	private JButton btnSearch = new JButton("검색");
	private JButton btnRefresh = new JButton("새로고침");
	private final JScrollPane scrollPane = new JScrollPane();
	
	
	//컬럼 너비 설정하는 메소드
		private static void setColumnWidth(JTable table, int columnIndex, int width) {
			TableColumn column = table.getColumnModel().getColumn(columnIndex);
			column.setPreferredWidth(width);
			column.setMaxWidth(width);
			column.setMinWidth(width);
		}

	/**
	 * Create the panel.
	 */
	public ReviewPanel(JFrame win) {
		setBackground(new Color(249, 252, 255));
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();

		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		// 1페이지 리뷰 게시판 10개 항목 Data 가져오기
		ReviewDAO reviewDAO = new ReviewDAO();
		ArrayList<ReviewDTO> reviewList = reviewDAO.getReviewList(1, 0, null);
		String[][] tableData = cUtils.reviewListToArray(reviewList);
		model.addColumn("번호");
		model.addColumn("제목");
		model.addColumn("지역");
		model.addColumn("작성일");
		model.addColumn("조회수");

		for (String[] row : tableData) {
			model.addRow(row);
		}

		comboBox.setFont(new Font("굴림", Font.PLAIN, 15));
		comboBox.setBounds(449, 40, 129, 37);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "제목", "본문", "제목+본문" }));
		add(comboBox);

		tbxSearch.setFont(new Font("굴림", Font.PLAIN, 15));
		tbxSearch.setBounds(588, 40, 275, 38);
		add(tbxSearch);
		tbxSearch.setColumns(10);

		// 검색 버튼
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 검색 변수 설정
				String searchIt = tbxSearch.getText();
				int search = comboBox.getSelectedIndex() + 1;

				ArrayList<ReviewDTO> reviewList = reviewDAO.getReviewList(1, search, searchIt);
				String[][] tableData = cUtils.reviewListToArray(reviewList);

				model.setRowCount(0);
				for (String[] row : tableData) {
					model.addRow(row);
				}

			}
		});
		btnSearch.setFont(new Font("굴림", Font.PLAIN, 15));
		btnSearch.setBounds(871, 42, 98, 37);
		add(btnSearch);

		// 글 작성 패널 변경
		btnAddReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				win.setContentPane(new AddReviewPanel(win, null));
				win.revalidate();
			}
		});
		btnAddReview.setFont(new Font("굴림", Font.PLAIN, 15));
		btnAddReview.setBounds(37, 42, 98, 37);
		add(btnAddReview);

		// 새로고침
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<ReviewDTO> reviewList = reviewDAO.getReviewList(1, 0, null);
				String[][] tableData = cUtils.reviewListToArray(reviewList);

				model.setRowCount(0);
				for (String[] row : tableData) {
					model.addRow(row);
				}
			}
		});
		btnRefresh.setBounds(881, 491, 91, 23);
		add(btnRefresh);
		scrollPane.setBounds(37, 89, 935, 392);

		add(scrollPane);

		// 테이블 클릭 세팅
		JTable mainTable = new JTable(model);
		mainTable.setRowHeight(25);
		mainTable.setFont(new Font("굴림", Font.PLAIN, 15));
		setColumnWidth(mainTable, 0, 50);
		setColumnWidth(mainTable, 1, 580);
		setColumnWidth(mainTable, 2, 110);
		setColumnWidth(mainTable, 3, 120);
		setColumnWidth(mainTable, 4, 73);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER); 

		// 정렬할 테이블의 컬럼모델을 가져오기
		TableColumnModel tcm = mainTable.getColumnModel(); 
		
		tcm.getColumn(0).setCellRenderer(dtcr);
		tcm.getColumn(2).setCellRenderer(dtcr);
		tcm.getColumn(3).setCellRenderer(dtcr);
		tcm.getColumn(4).setCellRenderer(dtcr);
		
		scrollPane.setViewportView(mainTable);

		// 테이블 클릭 세팅
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

	}
}
