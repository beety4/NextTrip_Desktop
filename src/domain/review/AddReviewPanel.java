package domain.review;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import config.ftp.FtpService;
import domain.MainTabPanel;

public class AddReviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnBack = new JButton();
	
	private JLabel lblReview = new JLabel("여행 후기 작성");
	private JLabel lblTitle = new JLabel("제목");
	private JLabel ltlRegion = new JLabel("지역");
	private JLabel lblContent = new JLabel("내용");
	private JLabel lblFile = new JLabel("파일");
	
	private JScrollPane scrollPane = new JScrollPane();
	private JTextField tbxTitle = new JTextField();
	private JTextField tbxRegion = new JTextField();
	private JTextArea textArea = new JTextArea();
	
	private JFileChooser jFileChooser = new JFileChooser();
	private JLabel lblFileChoose = new JLabel("선택된 파일");
	private JButton btnFile = new JButton("파일 첨부");
	private JButton btnUpload = new JButton("업로드");
	private String originFilePath;
	
	
	
	/**
	 * Create the panel.
	 */
	public AddReviewPanel(JFrame win, ReviewDTO dbreviewDTO) {
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
		
		lblReview.setFont(new Font("굴림", Font.PLAIN, 15));
		lblReview.setBounds(80, 43, 429, 36);
		add(lblReview);
		
		lblTitle.setFont(new Font("굴림", Font.PLAIN, 15));
		lblTitle.setBounds(80, 97, 90, 36);
		add(lblTitle);
		
		ltlRegion.setFont(new Font("굴림", Font.PLAIN, 15));
		ltlRegion.setBounds(80, 143, 90, 36);
		add(ltlRegion);
		
		lblContent.setFont(new Font("굴림", Font.PLAIN, 15));
		lblContent.setBounds(80, 189, 90, 36);
		add(lblContent);
		
		lblFile.setFont(new Font("굴림", Font.PLAIN, 15));
		lblFile.setBounds(80, 549, 90, 36);
		add(lblFile);
		
		
		
		
		tbxTitle.setBounds(182, 105, 840, 21);
		add(tbxTitle);
		tbxTitle.setColumns(10);
		
		tbxRegion.setColumns(10);
		tbxRegion.setBounds(182, 151, 840, 21);
		add(tbxRegion);
		
		scrollPane.setBounds(182, 189, 840, 295);
		scrollPane.setViewportView(textArea);
		add(scrollPane);
		
		lblFileChoose.setBounds(355, 558, 245, 19);
		add(lblFileChoose);
		
		// 파일 첨부 버튼
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jFileChooser.showOpenDialog(null);
				originFilePath = jFileChooser.getSelectedFile().getPath();
				lblFileChoose.setText(originFilePath);
			}
		});
		btnFile.setBounds(183, 556, 97, 23);
		add(btnFile);
		
		
		// 본 패널을 게시글 수정으로 들어왔을 경우
		if(dbreviewDTO != null) {
			tbxTitle.setText(dbreviewDTO.getTitle());
			tbxRegion.setText(dbreviewDTO.getRegion());
			textArea.setText(dbreviewDTO.getContent());
			btnUpload.setText("수정");
		}
		
		
		
		
		
		// 게시글 업로드 버튼
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 파라미터 처리
				String name = (String)session.getAttributes("sNAME");
				String title = tbxTitle.getText();
				String region = tbxRegion.getText();
				String content = textArea.getText();
				
				// 입력값 검사
				if(cUtils.isNullOrEmpty(new String[] {title, region, content})) {
					JOptionPane.showMessageDialog(null, "값을 입력해 주세요!");
					return;
				}
				
				
				// 파일 업로드
				FtpService ftpService = new FtpService();
				String img = ftpService.uploadFile(originFilePath, "reviewIMG");
				ftpService.close();
				
				// 데이터 객체
				ReviewDTO reviewDTO = ReviewDTO.builder()
						.name(name)
						.title(title)
						.region(region)
						.content(content)
						.img(img).build();
				
				// DB 저장
				ReviewDAO reviewDAO = new ReviewDAO();
				if(dbreviewDTO == null) {
					reviewDAO.addReview(reviewDTO);		// 게시글 추가
				} else {
					reviewDTO.setReviewNo(dbreviewDTO.getReviewNo());
					reviewDAO.editReview(reviewDTO);	// 게시글 수정
				}
				
				
				// Panel 화면 전환
				win.setContentPane(new MainTabPanel(win));
				win.revalidate();
			}
		});
		btnUpload.setFont(new Font("굴림", Font.PLAIN, 13));
		btnUpload.setBounds(912, 603, 110, 36);
		add(btnUpload);
		
	}
}
