package domain.sign;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import config.captcha.CaptchaConfig;
import config.customlib.CustomCookie;
import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import config.email.EmailService;
import config.email.TimeThread;
import domain.intro.IntroPanel;
import nl.captcha.Captcha;

public class SignPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel logo = new JLabel();
	
	private JPanel pnlLogin = new JPanel();
	private JLabel lblLogin = new JLabel("Login");
	private JLabel lblID = new JLabel("ID :");
	private JLabel lblPW = new JLabel("PW :");
	private JTextField tbxID = new JTextField();
	private JPasswordField tbxPW = new JPasswordField();
	private JButton btnLogin = new JButton("Login");
	private JCheckBox chkMaintain = new JCheckBox("자동 로그인");
	private JButton btnGoRegister = new JButton("아이디가 없나요? 지금 회원가입하세요!");
	private JTextField tbxCaptcha = new JTextField();
	private JButton btnCaptchaRe = new JButton("새로고침");
	
	private JPanel pnlRegister = new JPanel();
	private JLabel lblRegister= new JLabel("Register");
	private JLabel lblRID = new JLabel("ID :");
	private JLabel lblRPW = new JLabel("PW :");
	private JLabel lblPwChk = new JLabel("PW Chk:");
	private JLabel lblName = new JLabel("Name :");
	private JLabel lblBirth = new JLabel("Birth :");
	private JLabel lblGender = new JLabel("Gender : ");
	private JLabel lblEmail = new JLabel("Email :");
	private JLabel lblEmailchk = new JLabel("EmailChk :");
	private JTextField tbxRID = new JTextField();
	private JPasswordField tbxRPW = new JPasswordField();
	private JPasswordField tbxRPWChk = new JPasswordField();
	private JTextField tbxName = new JTextField();
	private JDateChooser chooserBirth = new JDateChooser();
	private JRadioButton rdbMale = new JRadioButton("M");
	private JRadioButton rdbFemale = new JRadioButton("F");
	private JTextField tbxEmail = new JTextField();
	private JTextField tbxEmailchk = new JTextField();
	private JProgressBar pbTime = new JProgressBar(0,180);
	private JButton btnRegister = new JButton("Register");
	private JButton btnGoLogin = new JButton("아이디가 있으신가요? 로그인하세요!");
	private TimeThread timeThread = new TimeThread(pbTime);
	private String authKey = null;
	private boolean emailChk = false;
	
	
	
	JLabel lblCaptcha = new JLabel("");
	private final JButton btnSendMail = new JButton("전송");
	private final JButton btnConfirm = new JButton("확인");
	
	@Override
	public void paintComponent(Graphics g) {
		Image bg = new ImageIcon("src/resource/background-back.png").getImage();
		g.drawImage(bg, 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	
	/**
	 * Create the panel.
	 */
	public SignPanel(JFrame win) {
		CustomUtility cUtils = new CustomUtility();
		CaptchaConfig captchaConfig = new CaptchaConfig();
		CustomSession session = new CustomSession();
		CustomCookie customCookie = new CustomCookie();
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		


		pnlLogin.setBounds(374, 286, 344, 319);
		pnlLogin.setVisible(true);
		pnlLogin.setLayout(null);
		pnlLogin.setBackground(Color.WHITE);
		add(pnlLogin);
		
		pnlRegister.setBounds(202, 312, 727, 316);
		pnlRegister.setVisible(false);
		pnlRegister.setLayout(null);
		pnlRegister.setBackground(Color.WHITE);
		add(pnlRegister);
		

		
		
		logo.setBounds(408, 74, 301, 136);
		cUtils.setImg(logo, "src/resource/logo-full.png", 301, 136);
		add(logo);
		
		lblLogin.setBounds(131, 10, 87, 38);
		lblLogin.setFont(new Font("굴림", Font.BOLD, 32));
		pnlLogin.add(lblLogin);
		
		lblID.setBounds(27, 45, 56, 38);
		lblID.setFont(new Font("굴림", Font.BOLD, 26));
		pnlLogin.add(lblID);
		
		tbxID.setBounds(84, 48, 231, 29);
		tbxID.setFont(new Font("굴림", Font.PLAIN, 24));
		tbxID.setColumns(10);
		pnlLogin.add(tbxID);
		
		tbxPW.setBounds(84, 96, 231, 29);
		tbxPW.setFont(new Font("굴림", Font.PLAIN, 24));
		pnlLogin.add(tbxPW);
		
		lblCaptcha.setBounds(16, 141, 148, 62);
		Image cap = captchaConfig.getCaptChaImg();
		ImageIcon captchaImg = new ImageIcon(cap);
		lblCaptcha.setIcon(captchaImg);
		pnlLogin.add(lblCaptcha);
		
		tbxCaptcha.setBounds(184, 141, 131, 29);
		pnlLogin.add(tbxCaptcha);
		tbxCaptcha.setColumns(10);
		btnCaptchaRe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 캡챠 이미지 새로고침
				Image cap = captchaConfig.getCaptChaImg();
				ImageIcon captchaImg = new ImageIcon(cap);
				lblCaptcha.setIcon(captchaImg);
			}
		});
		
		btnCaptchaRe.setBounds(208, 180, 91, 23);
		pnlLogin.add(btnCaptchaRe);
		
		btnLogin.setBounds(96, 213, 154, 45);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Captcha saveCaptcha = (Captcha)session.getAttributes("captcha");
				String captchaInput = tbxCaptcha.getText();
				String id = tbxID.getText();
				String pw = new String(tbxPW.getPassword());
				
				// Null or 공백 검사
				if(cUtils.isNullOrEmpty(new String[] {id, pw})) {
					JOptionPane.showMessageDialog(null, "값을 입력해 주세요!");
					return;
				}
				
				
				// 보안 문자 검사 로직
				if(cUtils.isNullOrEmpty(captchaInput)) {
					JOptionPane.showMessageDialog(null, "보안문자를 입력해 주세요!");
					return;
				}
				if(saveCaptcha.isCorrect(captchaInput) == false) {
					JOptionPane.showMessageDialog(null, "보안문자가 틀렸습니다!");
					Image cap = captchaConfig.getCaptChaImg();
					ImageIcon captchaImg = new ImageIcon(cap);
					lblCaptcha.setIcon(captchaImg);
					tbxCaptcha.setText("");
					return;
				}
				
				
				// DB와 로그인 로직 수행
				SignDAO userDAO = new SignDAO();
				int result = userDAO.login(id, pw);
				switch(result) {
					case 1:
						UserDTO userDTO = userDAO.getUserInfo(id);
						session.setAttributes("sID", id);
						session.setAttributes("sNAME", userDTO.getName());
						session.setAttributes("sIMG", userDTO.getImg());
						
						// 자동 로그인 체크 시
						if(chkMaintain.isSelected()) {
							customCookie.setCookie();
						}
						
						win.setContentPane(new IntroPanel(win));
						win.revalidate();
						session.removeAttributes("captcha");
						break;
					case -1:
						JOptionPane.showMessageDialog(null, "Database 오류 입니다. 잠시 후 시도해주세요.");
						break;
					case 0:
						JOptionPane.showMessageDialog(null, "PW가 올바르지 않습니다.");
						break;
					case -2:
						JOptionPane.showMessageDialog(null, "존재하지 않는 ID 입니다.");
						break;
				}
			}
		});
		cUtils.setImg(btnLogin, "src/resource/element/btnLogin.png", 193, 83);
		pnlLogin.add(btnLogin);
		
		lblPW.setBounds(16, 93, 67, 38);
		lblPW.setFont(new Font("굴림", Font.BOLD, 26));
		pnlLogin.add(lblPW);
		
		chkMaintain.setBounds(126, 264, 107, 23);
		pnlLogin.add(chkMaintain);
		
		btnGoRegister.setBounds(44, 293, 271, 15);
		btnGoRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 로그인 회원가입 패널 변경
				pnlLogin.setVisible(false);
				pnlRegister.setVisible(true);
			}
		});
		btnGoRegister.setBorderPainted(false);
		btnGoRegister.setContentAreaFilled(false);
		pnlLogin.add(btnGoRegister);
		
		
		
		
		
		
		
		
		
		lblRegister.setBounds(284, 10, 150, 38);
		lblRegister.setFont(new Font("굴림", Font.BOLD, 32));
		pnlRegister.add(lblRegister);
		
		lblRID.setBounds(50, 60, 56, 24);
		lblRID.setFont(new Font("굴림", Font.BOLD, 20));
		pnlRegister.add(lblRID);
		
		tbxRID.setBounds(148, 61, 178, 24);
		tbxRID.setFont(new Font("굴림", Font.PLAIN, 24));
		tbxRID.setColumns(10);
		pnlRegister.add(tbxRID);
		
		lblRPW.setBounds(356, 60, 67, 24);
		lblRPW.setFont(new Font("굴림", Font.BOLD, 20));
		pnlRegister.add(lblRPW);
		
		tbxRPW.setBounds(469, 59, 200, 24);
		tbxRPW.setFont(new Font("굴림", Font.PLAIN, 24));
		pnlRegister.add(tbxRPW);
		
		lblPwChk.setFont(new Font("굴림", Font.BOLD, 20));
		lblPwChk.setBounds(356, 94, 96, 24);
		pnlRegister.add(lblPwChk);
		
		tbxRPWChk.setFont(new Font("굴림", Font.PLAIN, 24));
		tbxRPWChk.setBounds(469, 93, 200, 24);
		pnlRegister.add(tbxRPWChk);
		

		
		
		
		
		lblName.setFont(new Font("굴림", Font.BOLD, 20));
		lblName.setBounds(50, 94, 96, 24);
		pnlRegister.add(lblName);
		
		
		tbxName.setFont(new Font("굴림", Font.PLAIN, 24));
		tbxName.setColumns(10);
		tbxName.setBounds(148, 98, 178, 24);
		pnlRegister.add(tbxName);
		
		lblBirth.setFont(new Font("굴림", Font.BOLD, 20));
		lblBirth.setBounds(50, 131, 96, 24);
		pnlRegister.add(lblBirth);
		
		chooserBirth.setBounds(148, 132, 178, 24);
		pnlRegister.add(chooserBirth);
		
		lblGender.setFont(new Font("굴림", Font.BOLD, 20));
		lblGender.setBounds(50, 165, 96, 24);
		pnlRegister.add(lblGender);
		
		lblEmail.setFont(new Font("굴림", Font.BOLD, 20));
		lblEmail.setBounds(356, 128, 96, 24);
		pnlRegister.add(lblEmail);
		
		tbxEmail.setFont(new Font("굴림", Font.PLAIN, 12));
		tbxEmail.setColumns(10);
		tbxEmail.setBounds(469, 127, 131, 24);
		pnlRegister.add(tbxEmail);
		
		rdbMale.setBounds(148, 167, 56, 24);
		pnlRegister.add(rdbMale);
		
		rdbFemale.setBounds(272, 168, 54, 23);
		pnlRegister.add(rdbFemale);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbMale);
		bg.add(rdbFemale);
		
		
		lblEmailchk.setFont(new Font("굴림", Font.BOLD, 20));
		lblEmailchk.setBounds(356, 165, 107, 24);
		lblEmailchk.setVisible(false);
		pnlRegister.add(lblEmailchk);
		
		tbxEmailchk.setFont(new Font("굴림", Font.PLAIN, 12));
		tbxEmailchk.setColumns(10);
		tbxEmailchk.setVisible(false);
		tbxEmailchk.setBounds(470, 164, 129, 24);
		pnlRegister.add(tbxEmailchk);
		
		btnRegister.setBounds(284, 222, 150, 55);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = tbxRID.getText();
				String pw = new String(tbxRPW.getPassword());
				String pwchk = new String(tbxRPWChk.getPassword());
				String name = tbxName.getText();
				String birth = null;
				String gender = cUtils.getWhatSelected(new JRadioButton[] {rdbMale, rdbFemale});
				String email = tbxEmail.getText();
				
				
				
				// 유효성 검사
				if(cUtils.isNullOrEmpty(new String[] {id, pw, pwchk, name, gender, email}) || chooserBirth.getDate() == null) {
					JOptionPane.showMessageDialog(null, "값을 입력해 주세요!");
					return;
				} else {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
					birth = df.format(chooserBirth.getDate());
				}
				if(pw.equals(pwchk) == false) {
					JOptionPane.showMessageDialog(null, "패스워드가 일치하지 않습니다!");
					return;
				}
				if(emailChk == false) {
					JOptionPane.showMessageDialog(null, "이메일 인증을 완료해주세요!");
					return;
				}
				
				// 사용자 입력 값으로 DTO 생성
				UserDTO userDTO = UserDTO.builder()
						.id(id).password(pw)
						.name(name)
						.birth(birth)
						.gender(gender.equals("M") ? 1 : 0)
						.email(email)
						.build();
						
				
				// DB와 회원가입 로직 실행
				SignDAO userDAO = new SignDAO();
				int result = userDAO.register(userDTO);
				switch(result) {
					case 1:
						JOptionPane.showMessageDialog(null, "회원가입 성공!");						
						pnlRegister.setVisible(false);
						pnlLogin.setVisible(true);
						break;
					case -1:
						JOptionPane.showMessageDialog(null, "에러 : 관리자에게 문의하세요");
						break;
				}
			}
		});
		cUtils.setImg(btnRegister, "src/resource/element/btnRegister.png", 193, 83);
		pnlRegister.add(btnRegister);
		
		btnGoLogin.setBounds(236, 287, 244, 15);
		btnGoLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 회원가입 로그인 패널 변경
				pnlLogin.setVisible(true);
				pnlRegister.setVisible(false);
			}
		});
		btnGoLogin.setBorderPainted(false);
		btnGoLogin.setContentAreaFilled(false);
		pnlRegister.add(btnGoLogin);
		
		btnSendMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmailService sendMail = new EmailService();
				String email = tbxEmail.getText();
				
				// 정규표현식을 통한 email 유효성 검사
				if(cUtils.isEmail(email) == false) {
					JOptionPane.showMessageDialog(null, "옳바른 형식의 Email 주소가 아닙니다!");
					return;
				}
				
				// 이메일 전송 후, 인증키 저장
				authKey = sendMail.sendtoMail(email);
				
				// 시간제한 스레드 실행 및 화면 표시
				JOptionPane.showMessageDialog(null, "이메일 전송 완료!");
				timeThread.start();
				tbxEmailchk.setVisible(true); btnConfirm.setVisible(true); 
				lblEmailchk.setVisible(true); pbTime.setVisible(true);
			}
		});
		btnSendMail.setBounds(603, 127, 67, 24);
		pnlRegister.add(btnSendMail);
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 인증시간 만료 검사
				if(pbTime.getValue() == 0) {
					JOptionPane.showMessageDialog(null, "인증 시간이 만료되었습니다!");
					return;
				}
				
				// 인증키 검사
				if(authKey.equals(tbxEmailchk.getText())) {
					JOptionPane.showMessageDialog(null, "이메일 인증 완료!");
					emailChk = true;
					tbxEmail.setEnabled(false); tbxEmailchk.setEnabled(false);
					btnSendMail.setEnabled(false); btnConfirm.setEnabled(false); pbTime.setVisible(false);
					timeThread.stopThread();	// 시간제한 쓰레드 종료
				} else {
					JOptionPane.showMessageDialog(null, "인증키가 올바르지 않습니다!");
					return;
				}
				
			}
		});
		btnConfirm.setBounds(602, 165, 67, 24);
		btnConfirm.setVisible(false);
		pnlRegister.add(btnConfirm);
		
		pbTime.setStringPainted(true);
		pbTime.setValue(180);
		pbTime.setBounds(469, 198, 200, 14);
		pbTime.setVisible(false);
		pnlRegister.add(pbTime);
		
		
	}
}

/*
 * 이미지 출처
 * https://kr.freepik.com/free-vector/flat-background-for-world-
 * tourism-day-celebration_58510190.htm#query=%EC%97%AC%ED%96%8
 * 9%20%EB%B0%B0%EA%B2%BD&position=3&from_view=keyword&track=ai
 * s&uuid=b3986836-fd43-4fe4-bcbb-cecf6e9999f8
 */