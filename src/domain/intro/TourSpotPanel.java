package domain.intro;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.api.ApiService;
import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.MainTabPanel;
import domain.search.TourSpotDTO;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TourSpotPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblPhoto = new JLabel("사진");
	private JLabel lblPlace = new JLabel("장소");
	private JLabel lblAddr = new JLabel("주소");

	/**
	 * Create the panel.
	 */
	public TourSpotPanel(JFrame win) {
		CustomUtility cu = new CustomUtility();
		CustomSession session = new CustomSession();

		setSize(270, 280);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		ApiService apiService = new ApiService();
		ArrayList<TourSpotDTO> tourSpotList = apiService.getIndexTour();
		
		//랜덤값 설정
		Random random = new Random();
		int num = random.nextInt(tourSpotList.size());
		

		lblPhoto.setBounds(0, 0, 270, 202);
		if (tourSpotList.get(num).getFirstimage() == null) {
			cu.setImg(lblPhoto, tourSpotList.get(num).getFirstimage2(), 270, 202);
		} else{
			cu.setImg(lblPhoto, tourSpotList.get(num).getFirstimage(), 270, 202);
		}
		add(lblPhoto);

		lblPlace.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace.setBounds(11, 212, 247, 29);
		lblPlace.setText(tourSpotList.get(num).getTitle());
		add(lblPlace);

		lblAddr.setFont(new Font("굴림", Font.PLAIN, 12));
		lblAddr.setBounds(11, 251, 247, 19);
		lblAddr.setText(tourSpotList.get(num).getAddr1());
		add(lblAddr);
		
		
		//마우스 클릭 이벤트
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String contentid = tourSpotList.get(num).getContentid();
				win.setContentPane(new TourSpotDetailPanel(win, contentid));
			}
		});

	}
}
