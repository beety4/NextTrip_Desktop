package domain.search;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import config.api.ApiService;
import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.intro.TourSpotDetailPanel;
import javax.swing.JScrollPane;
import java.awt.Color;

public class SearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel model = new DefaultTableModel();

	private JComboBox comboBox = new JComboBox();
	private JTextField tbxSearch = new JTextField();
	private JButton btnSearch = new JButton("검색");

	private Map<String, String> typeMap = new HashMap<>() {
		{
			put("관광지", "12");
			put("문화시설", "14");
			put("행사/공연/축제", "15");
			put("여행코스", "25");
			put("레포츠", "28");
			put("숙박", "32");
			put("쇼핑", "38");
			put("음식점", "39");
		}
	};
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
	public SearchPanel(JFrame win) {
		setBackground(new Color(249, 252, 255));
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		model.addColumn("컨텐츠ID");
		model.addColumn("제목");
		model.addColumn("주소");
		model.addColumn("수정일");

		comboBox.setFont(new Font("굴림", Font.PLAIN, 15));
		comboBox.setBounds(148, 41, 129, 37);
		comboBox.setModel(
				new DefaultComboBoxModel(new String[] { "관광지", "문화시설", "행사/공연/축제", "여행코스", "레포츠", "숙박", "쇼핑", "음식점" }));
		add(comboBox);

		tbxSearch.setFont(new Font("굴림", Font.PLAIN, 15));
		tbxSearch.setBounds(289, 41, 432, 38);
		add(tbxSearch);
		tbxSearch.setColumns(10);

		// 검색 버튼
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword = null;
				String type = null;
				String contenttypeid = null;
				try {
					// 검색 변수 설정
					keyword = URLEncoder.encode(tbxSearch.getText(), "UTF-8");
					type = comboBox.getSelectedItem().toString();
					contenttypeid = typeMap.get(type);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				ApiService apiService = new ApiService();
				ArrayList<TourSpotSearchDTO> tourSpotSearchList = apiService.getTourSpotSearch(keyword, contenttypeid);
				String[][] tableData = cUtils.tourSearchListToArray(tourSpotSearchList);

				model.setRowCount(0);
				for (String[] row : tableData) {
					model.addRow(row);
				}
			}
		});
		btnSearch.setFont(new Font("굴림", Font.PLAIN, 15));
		btnSearch.setBounds(733, 42, 98, 37);
		add(btnSearch);
		scrollPane.setBounds(37, 89, 935, 388);

		add(scrollPane);

		// 테이블 클릭 세팅
		JTable mainTable = new JTable(model);
		mainTable.setRowHeight(25);
		mainTable.setFont(new Font("굴림", Font.PLAIN, 15));
		setColumnWidth(mainTable, 0, 100);
		setColumnWidth(mainTable, 1, 350);
		setColumnWidth(mainTable, 2, 353);
		setColumnWidth(mainTable, 3, 130);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);

		// 정렬할 테이블의 컬럼모델을 가져오기
		TableColumnModel tcm = mainTable.getColumnModel();

		tcm.getColumn(0).setCellRenderer(dtcr);
		tcm.getColumn(3).setCellRenderer(dtcr);

		scrollPane.setViewportView(mainTable);

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

	}

}
