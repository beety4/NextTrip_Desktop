package domain.intro;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.MainTabPanel;

public class TourSpotDetailPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnBack = new JButton();
	private JXMapViewer jXMapViewer = new JXMapViewer();

	
	
	
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
		
		
		
		JLabel lblNewLabel = new JLabel("사진");
		lblNewLabel.setBounds(79, 39, 304, 194);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("지도");
		lblNewLabel_1.setBounds(79, 283, 942, 384);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("주소");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(79, 251, 358, 27);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("장소");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(430, 39, 77, 27);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("설명");
		lblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(430, 73, 591, 126);
		add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("북마크 추가");
		btnNewButton.setBounds(920, 243, 101, 35);
		add(btnNewButton);
		
		JLabel lblNewLabel_5 = new JLabel("링크");
		lblNewLabel_5.setBounds(430, 218, 260, 15);
		add(lblNewLabel_5);
		
		
		
		
		
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		GeoPosition geo = new GeoPosition(37.44913, 126.6572);
		
		
		jXMapViewer.setTileFactory(tileFactory);
		jXMapViewer.setBounds(59, 95, 923, 820);
		jXMapViewer.setSize(800,800);
		jXMapViewer.setAddressLocation(geo);
		jXMapViewer.setZoom(3);
		add(jXMapViewer);
		
		
		// Create event mouse move
		MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
		jXMapViewer.addMouseListener(mm);
		jXMapViewer.addMouseMotionListener(mm);
		jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
		
		
		
		
		
	}

}
