package domain.bookmark;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.intro.TourSpotDetailPanel;
import domain.search.TourSpotDTO;

public class BookMarkPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel model = new DefaultTableModel();

	private final JScrollPane scrollPane = new JScrollPane();
	private final JLabel lblBookMark = new JLabel("나의 여행지 북마크");
	private JButton btnRefresh = new JButton("새로고침");
	

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
	public BookMarkPanel(JFrame win) {
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		setBackground(new Color(249, 252, 255));
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		lblBookMark.setFont(new Font("굴림", Font.PLAIN, 15));
		lblBookMark.setBounds(37, 50, 314, 29);
		add(lblBookMark);
		

		
		
		model.addColumn("컨텐츠ID");
		model.addColumn("제목");
		model.addColumn("주소");
		scrollPane.setBounds(37, 89, 935, 388);

		add(scrollPane);

		// 테이블 클릭 세팅
		JTable mainTable = new JTable(model);
		mainTable.setRowHeight(25);
		mainTable.setFont(new Font("굴림", Font.PLAIN, 15));
		setColumnWidth(mainTable, 0, 130);
		setColumnWidth(mainTable, 1, 300);
		setColumnWidth(mainTable, 2, 503);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);

		// 정렬할 테이블의 컬럼모델을 가져오기
		TableColumnModel tcm = mainTable.getColumnModel();

		tcm.getColumn(0).setCellRenderer(dtcr);

		scrollPane.setViewportView(mainTable);
		
		

		String name = (String)session.getAttributes("sNAME");
		BookMarkDAO bookMarkDAO = new BookMarkDAO();
		ArrayList<TourSpotDTO> bookMarkList = bookMarkDAO.getBookMarkList(name);
		String[][] tableData = cUtils.tourSpotListToArray(bookMarkList);
		
		model.setRowCount(0);
		if(tableData != null) {
			for (String[] row : tableData) {
				model.addRow(row);
			}
		}
		
		

		mainTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = mainTable.getSelectedRow();
				String contentid = mainTable.getValueAt(selectRow, 0).toString();
				win.setContentPane(new TourSpotDetailPanel(win, contentid));
				win.revalidate();
			}
		});
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		
		// 새로고침
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<TourSpotDTO> bookMarkList = bookMarkDAO.getBookMarkList(name);
				String[][] tableData = cUtils.tourSpotListToArray(bookMarkList);

				model.setRowCount(0);
				for (String[] row : tableData) {
					model.addRow(row);
				}
			}
		});
		btnRefresh.setBounds(881, 491, 91, 23);
		add(btnRefresh);
		

	}

}
