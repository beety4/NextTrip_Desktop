package domain.intro;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.api.ApiService;
import domain.search.TourSpotDTO;

import java.awt.Color;

public class IntroPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Create the panel.
	 */
	public IntroPanel(JFrame win) {
		setBackground(new Color(249, 252, 255));
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		
		// 랜덤 관광지 가져오기
		ApiService apiService = new ApiService();
		ArrayList<TourSpotDTO> tourSpotList = apiService.getIndexTour();

		// 랜덤 관광지 패널 출력
		TourSpotPanel[] tourSpotPanels = new TourSpotPanel[6];
		for (int i = 0; i < tourSpotList.size(); i++) {
			tourSpotPanels[i] = new TourSpotPanel(win, tourSpotList.get(i));
			add(tourSpotPanels[i]);
		}
		

		// 위치 조정
		tourSpotPanels[0].setBounds(34, 10, 270, 280);
		tourSpotPanels[1].setBounds(367, 10, 270, 280);
		tourSpotPanels[2].setBounds(699, 10, 270, 280);
		tourSpotPanels[3].setBounds(34, 300, 270, 280);
		tourSpotPanels[4].setBounds(367, 300, 270, 280);
		tourSpotPanels[5].setBounds(699, 300, 270, 280);

	}
}
