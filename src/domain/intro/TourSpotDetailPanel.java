package domain.intro;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
import domain.bookmark.BookMarkDAO;
import domain.search.TourSpotDTO;
import domain.search.TourSpotDetailDTO;

public class TourSpotDetailPanel extends JPanel {
   private static final long serialVersionUID = 1L;
   private JButton btnBack = new JButton();
   private JXMapViewer jXMapViewer = new JXMapViewer();
   private JLabel lblPhoto = new JLabel("사진");
   private JLabel lblAddress = new JLabel("주소");
   private JLabel lblPlace = new JLabel("장소");
   private JButton btnBookmark = new JButton("북마크 추가");
   private JLabel lblLink = new JLabel("링크");
   private JLabel lblMap = new JLabel("지도");
   private JScrollPane scrollPane = new JScrollPane();
   private JTextArea txtDescription = new JTextArea();
   
   
   /**
    * Create the panel.
    */
   public TourSpotDetailPanel(JFrame win, String contentid) {
      CustomUtility cu = new CustomUtility();
      CustomSession session = new CustomSession();
      setBounds(0, 0, 1100, 700);
      setSize(1100, 700);
      setBorder(new EmptyBorder(5, 5, 5, 5));
      setLayout(null);   
      
      btnBack.setBounds(12, 10, 40, 36);
      cu.setImg(btnBack, "src/resource/element/backIcon.png", 50, 50);
      btnBack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            win.setContentPane(new MainTabPanel(win));
            win.revalidate();
         }
      });
      add(btnBack);
      
     
      
      // contentid 기반 Tour 정보 가져오기
      ApiService apiService = new ApiService();
      TourSpotDetailDTO tourSpotDetailDTO = apiService.getDetailTourSpot(contentid);
      
      lblPhoto.setBounds(79, 39, 304, 194);
      if (tourSpotDetailDTO.getFirstimage() == null) {
         cu.setImg(lblPhoto, tourSpotDetailDTO.getFirstimage2(), 304, 194);
      } else{
         cu.setImg(lblPhoto, tourSpotDetailDTO.getFirstimage(), 304, 194);
      }
      add(lblPhoto);
      
      
      // DetailDTO 일반 DTO 변환
      TourSpotDTO tourSpotDTO = new TourSpotDTO();
      tourSpotDTO.setContentid(contentid);
      tourSpotDTO.setFirstimage(tourSpotDetailDTO.getFirstimage());
      tourSpotDTO.setAddr1(tourSpotDetailDTO.getAddr1());
      tourSpotDTO.setTitle(tourSpotDetailDTO.getTitle());
		
		
		
      // 북마크 여부 확인
      BookMarkDAO bookMarkDAO = new BookMarkDAO();
      String name = (String)session.getAttributes("sNAME");
      boolean isCheck = bookMarkDAO.isCheck(tourSpotDetailDTO.getContentid(), name);
      if (isCheck) {
		btnBookmark.setBackground(Color.GRAY);
		btnBookmark.setText("북마크 삭제");	
      } else {
    	btnBookmark.setBackground(Color.GREEN);
    	btnBookmark.setText("북마크 추가");
      }
		
		
      
      lblAddress.setFont(new Font("굴림", Font.PLAIN, 15));
      lblAddress.setBounds(79, 251, 808, 27);
      lblAddress.setText(tourSpotDetailDTO.getAddr1());
      add(lblAddress);
      
      
      lblPlace.setFont(new Font("굴림", Font.PLAIN, 15));
      lblPlace.setBounds(430, 39, 591, 27);
      lblPlace.setText(tourSpotDetailDTO.getTitle());
      add(lblPlace);
      
      
      scrollPane.setBounds(430, 76, 591, 132);
      add(scrollPane);
      scrollPane.setViewportView(txtDescription);
      
      txtDescription.setEditable(false);
      txtDescription.setLineWrap(true);
      txtDescription.setWrapStyleWord(true);
      txtDescription.setText(tourSpotDetailDTO.getOverview());
      
      
      lblLink.setBounds(430, 205, 591, 36);
      if(tourSpotDetailDTO.getHomepage() == null) {
         lblLink.setText("");
      } else {
    	 lblLink.setText("<html>" + tourSpotDetailDTO.getHomepage() + "</html>");
      }
      add(lblLink);
      
      
      btnBookmark.setBounds(920, 243, 101, 35);
      add(btnBookmark);
      
      
      //북마크 추가
      btnBookmark.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		String contentid = tourSpotDetailDTO.getContentid();

      		boolean isCheck = bookMarkDAO.isCheck(tourSpotDetailDTO.getContentid(), name);
      		if (isCheck) {
            	bookMarkDAO.deleteBookMark(contentid, name);
            	btnBookmark.setBackground(Color.GREEN);
          	  	btnBookmark.setText("북마크 추가");
            } else {
      			bookMarkDAO.addBookMark(tourSpotDTO, name);
      			btnBookmark.setBackground(Color.GRAY);
      			btnBookmark.setText("북마크 삭제");
            }
      	}
      });
      
     

      
      //지도
      double x = Double.parseDouble(tourSpotDetailDTO.getMapx());
      double y = Double.parseDouble(tourSpotDetailDTO.getMapy());
      TileFactoryInfo info = new OSMTileFactoryInfo();
      DefaultTileFactory tileFactory = new DefaultTileFactory(info);
      GeoPosition geo = new GeoPosition(y,x);
      
      
      jXMapViewer.setTileFactory(tileFactory);
      jXMapViewer.setBounds(79, 283, 942, 357);
      jXMapViewer.setSize(942, 384);
      jXMapViewer.setAddressLocation(geo);
      jXMapViewer.setZoom(3);
      add(jXMapViewer);
      
      
      // 지도 마우스 이동 이벤트
      MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
      jXMapViewer.addMouseListener(mm);
      jXMapViewer.addMouseMotionListener(mm);
      jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
      
      
      lblMap.setBounds(79, 283, 942, 384);
      add(lblMap);
      
      
      
      
      
      
   }
}