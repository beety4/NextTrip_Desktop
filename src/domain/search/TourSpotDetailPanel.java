package domain.search;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import config.api.ApiService;
import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.MainTabPanel;

public class TourSpotDetailPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnBack = new JButton();
	private JXMapViewer jXMapViewer = new JXMapViewer();
	private JLabel lblPhoto = new JLabel("사진");
    private JLabel lblAddress = new JLabel("주소");
	private JLabel lblPlace = new JLabel("장소");
	private JLabel lblDescription = new JLabel("설명");
	private JButton btnBookmark = new JButton("북마크 추가");
	private JLabel lblLink = new JLabel("링크");
	private JLabel lblMap = new JLabel("지도");
	
	
	/**
	 * Create the panel.
	 */
	public TourSpotDetailPanel(JFrame win, String contentid) {
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);	
		
		btnBack.setBounds(12, 10, 40, 36);
		cUtils.setImg(btnBack, "src/resource/element/backIcon.png", 50, 50);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				win.setContentPane(new MainTabPanel(win));
				win.revalidate();
			}
		});
		add(btnBack);
		
		//API로 정보 가져오기
		ApiService apiService = new ApiService();
		TourSpotDetailDTO dto = apiService.getDetailTourSpot(contentid);
		/*** 확인 ***/
		
		
		lblPhoto.setBounds(79, 39, 304, 194);
		add(lblPhoto);
		
		
		lblAddress.setFont(new Font("굴림", Font.PLAIN, 15));
		lblAddress.setBounds(79, 251, 358, 27);
		lblAddress.setText(dto.getAddr1());
		System.out.println(dto.getHomepage());
		add(lblAddress);
		
		
		lblPlace.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace.setBounds(430, 39, 77, 27);
		lblPlace.setText(dto.getTitle());
		add(lblPlace);
		
		
		lblDescription.setFont(new Font("굴림", Font.PLAIN, 13));
		lblDescription.setBounds(430, 73, 591, 126);
		lblDescription.setText(dto.getOverview());
		add(lblDescription);
		
		
		btnBookmark.setBounds(920, 243, 101, 35);
		add(btnBookmark);
		
		
		lblLink.setBounds(430, 218, 260, 15);
		lblLink.setText(dto.getHomepage());
		add(lblLink);
		
		
		
		
		
		//지도
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		GeoPosition geo = new GeoPosition(37.44913, 126.6572);
		
		
		jXMapViewer.setTileFactory(tileFactory);
		jXMapViewer.setBounds(79, 283, 942, 357);
		jXMapViewer.setSize(942, 384);
		jXMapViewer.setAddressLocation(geo);
		jXMapViewer.setZoom(3);
		add(jXMapViewer);
		
		
		// 마우스 이동 이벤트
		MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
		jXMapViewer.addMouseListener(mm);
		jXMapViewer.addMouseMotionListener(mm);
		jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
		
		
		lblMap.setBounds(79, 283, 942, 384);
		add(lblMap);
		
	}

}
