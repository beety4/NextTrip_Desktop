package domain.review;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.MainTabPanel;
import javax.swing.JTextPane;

public class ShowReviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnBack = new JButton();

	private JLabel lblTitle = new JLabel("제목");
	private JLabel lblRegion = new JLabel("지역");
	private JLabel lblDate = new JLabel("작성일자");
	private JScrollPane scrollPane = new JScrollPane();
	private JTextPane txtContent = new JTextPane();

	private JButton btnLike = new JButton("좋아요");

	private JScrollPane scrollComment = new JScrollPane();
	private JLabel lblComment = new JLabel();
	private JTextArea textArea = new JTextArea();
	private JButton btnSubmit = new JButton("전송");

	private JButton btnEdit = new JButton("수정");
	private JButton btnDelete = new JButton("삭제");

	// 댓글 목록 새로 고침하여 가져오는 메소드
	public void reloadComment(int reviewNo) {
		ReviewDAO reviewDAO = new ReviewDAO();
		ArrayList<CommentDTO> commentList = reviewDAO.getCommentList(reviewNo);
		if (commentList != null && commentList.isEmpty() == false) {
			lblComment.setText("<html>");
			for (CommentDTO commentDTO : commentList) {
				String origin = lblComment.getText();
				String remake = origin + commentDTO.getName() + " " + commentDTO.getDate() + "<br>"
						+ commentDTO.getContent() + "<br>";
				lblComment.setText(remake);
			}
			lblComment.setText(lblComment.getText() + "</html>");
		}
	}

	/**
	 * Create the panel.
	 */
	public ShowReviewPanel(JFrame win, int reviewNo) {
		setBackground(new Color(249, 252, 255));
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

		// DB로 게시글 상세 정보 DTO로 받아오기
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewDTO reviewDTO = reviewDAO.getReviewDetail(reviewNo);
		
		// 조회수 추가
		reviewDAO.addView(reviewDTO.getReviewNo());

		lblTitle.setFont(new Font("굴림", Font.PLAIN, 15));
		lblTitle.setBounds(80, 51, 429, 36);
		lblTitle.setText("제목 : " + reviewDTO.getTitle());
		add(lblTitle);

		lblRegion.setFont(new Font("굴림", Font.PLAIN, 15));
		lblRegion.setBounds(80, 81, 91, 28);
		lblRegion.setText("지역 : " + reviewDTO.getRegion());
		add(lblRegion);

		lblDate.setFont(new Font("굴림", Font.PLAIN, 13));
		lblDate.setBounds(590, 43, 429, 52);
		lblDate.setText("작성일자 : " + reviewDTO.getDate());
		add(lblDate);

		// 세로 스크롤 설정
		scrollPane = new JScrollPane(txtContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		txtContent.setContentType("text/html");
		if (reviewDTO.getImg() == null) {
			txtContent.setText("<html>" + reviewDTO.getContent() + "</html>");
		} else {
			String imgUrl = "http://aws.akotis.kr:8080/NextTrip/" + reviewDTO.getImg().replace("\\", "/");
			String content = "<html><body>" + reviewDTO.getContent() + "<br><img src='" + imgUrl + "'></body></html>";

			txtContent.setText(content);
		}
		txtContent.setEditable(false);

		scrollPane.setBounds(80, 113, 939, 291);
		scrollPane.setViewportView(txtContent);
		add(scrollPane);

		// 좋아요 눌렀는지 체크
		String name = (String) session.getAttributes("sNAME");
		boolean isLike = reviewDAO.isLike(reviewNo, name);
		if (isLike) {
			btnLike.setBackground(Color.GREEN);
		} else {
			btnLike.setBackground(Color.GRAY);
		}

		// 좋아요 입력
		btnLike.setFont(new Font("굴림", Font.PLAIN, 13));
		btnLike.setBounds(910, 412, 110, 36);
		btnLike.setText("좋아요 + " + reviewDTO.getLikeC());
		btnLike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isLike = reviewDAO.isLike(reviewNo, name);
				if (isLike) {
					reviewDAO.deleteReviewLike(reviewNo, name);
					btnLike.setBackground(Color.GRAY);
				} else {
					reviewDAO.addReviewLike(reviewNo, name);
					btnLike.setBackground(Color.GREEN);
				}

				int likeCnt = reviewDAO.getLikeCnt(reviewNo);
				btnLike.setText("좋아요 + " + likeCnt);
			}
		});
		add(btnLike);

		reloadComment(reviewNo);
		scrollComment.setBounds(79, 458, 940, 115);
		lblComment.setBackground(new Color(249, 252, 255));
		scrollComment.setViewportView(lblComment);
		add(scrollComment);

		textArea.setBounds(80, 583, 829, 52);
		
		// Border 설정
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        textArea.setBorder(border);
        
		add(textArea);

		// 댓글 작성 버튼
		btnSubmit.setBounds(928, 583, 91, 52);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 데이터 가져오기
				String content = textArea.getText();

				// DTO 세팅
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setReviewNo(reviewNo);
				commentDTO.setName(name);
				commentDTO.setContent(content);

				// 댓글 DB 저장
				reviewDAO.addReviewComment(commentDTO);
				textArea.setText("");
				reloadComment(reviewNo);
			}
		});
		add(btnSubmit);

		// 게시글 수정
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				win.setContentPane(new AddReviewPanel(win, reviewDTO));
				win.revalidate();
			}
		});
		btnEdit.setBounds(80, 414, 91, 23);

		// 게시글 삭제
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?");
				if (result == 0) {
					reviewDAO.deleteReview(reviewNo);
					win.setContentPane(new MainTabPanel(win));
					win.revalidate();
				}
			}
		});
		btnDelete.setBounds(183, 414, 91, 23);

		// 게시글 작성자인지 확인
		if (name.equals(reviewDTO.getName())) {
			add(btnEdit);
			add(btnDelete);
		}

	}
}
