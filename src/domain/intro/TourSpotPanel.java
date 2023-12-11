package domain.intro;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomUtility;
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
	public TourSpotPanel(JFrame win, TourSpotDTO tourSpotDTO) {
		CustomUtility cu = new CustomUtility();
		setSize(270, 280);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		

		lblPhoto.setBounds(0, 0, 270, 202);
		if (tourSpotDTO.getFirstimage() == null) {
			cu.setImg(lblPhoto, tourSpotDTO.getFirstimage2(), 270, 202);
		} else{
			cu.setImg(lblPhoto, tourSpotDTO.getFirstimage(), 270, 202);
		}
		add(lblPhoto);

		lblPlace.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPlace.setBounds(11, 212, 247, 29);
		lblPlace.setText(tourSpotDTO.getTitle());
		add(lblPlace);

		lblAddr.setFont(new Font("굴림", Font.PLAIN, 12));
		lblAddr.setBounds(11, 251, 247, 19);
		lblAddr.setText(tourSpotDTO.getAddr1());
		add(lblAddr);
		
		
		//마우스 클릭 이벤트
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String contentid = tourSpotDTO.getContentid();
				win.setContentPane(new TourSpotDetailPanel(win, contentid));
				win.revalidate();
			}
		});

	}
}
