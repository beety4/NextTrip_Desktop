package domain.sign;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import config.ftp.FtpService;
import domain.MainTabPanel;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class ProfilePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnBack = new JButton();
	private JLabel lblChagePwd = new JLabel("Change Password");
	private JLabel lblPD = new JLabel("Personal Detail");
	
	private JLabel lblProfileImg = new JLabel("프로필 사진");
	private JButton btnFile = new JButton("파일 선택");
	private JLabel lblFileChoose = new JLabel("선택된 파일 없음");
	private JFileChooser jFileChooser = new JFileChooser();
	private String originFilePath = null;
	
	private JLabel lblName = new JLabel("Name");
	private JTextField txtName = new JTextField();
	private JLabel lblID = new JLabel("ID");
	private JTextField txtID = new JTextField();
	private JLabel lblGender = new JLabel("Gender");
	private JTextField txtGender = new JTextField();
	private JLabel lblBirth = new JLabel("Birth");
	private JTextField txtBirth = new JTextField();
	private JLabel lblJoinDate = new JLabel("JoinDate");
	private JTextField txtJoinDate = new JTextField();
	private JLabel lblEmail = new JLabel("Email");
	private JTextField txtEmail = new JTextField();
	
	private JLabel lblPwd = new JLabel("Your Password");
	private JPasswordField txtPwd = new JPasswordField();
	private JLabel lblCP = new JLabel("Change Password");
	private JPasswordField txtPwdChk = new JPasswordField();
	private JButton btnUpload = new JButton("Upload");
	private final JLabel lblAlert = new JLabel("모든 필드를 입력하셔야 변경 가능합니다.");
	
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		Image bg = new ImageIcon("src/resource/backgroundimage.png <-- required").getImage();
		g.drawImage(bg, 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	/**
	 * Create the panel.
	 */
	public ProfilePanel(JFrame win) {
		setBackground(new Color(249, 252, 255));
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
	
		SignDAO signDAO = new SignDAO();
		String id = (String)session.getAttributes("sID");
		UserDTO userDTO = signDAO.getUserInfo(id);
		
		
		String profileImg = (String)session.getAttributes("sIMG");
		lblProfileImg.setBounds(61, 99, 249, 241);
		cu.setImg(lblProfileImg, profileImg, 249, 241);
		add(lblProfileImg);
		
		
		lblName.setFont(new Font("굴림", Font.PLAIN, 15));
		lblName.setBounds(704, 74, 77, 36);
		add(lblName);
		
		
		txtName.setFont(new Font("굴림", Font.PLAIN, 15));
		txtName.setBounds(704, 120, 291, 36);
		txtName.setText(userDTO.getName());
		add(txtName);
		txtName.setColumns(10);
		
		
		lblID.setFont(new Font("굴림", Font.PLAIN, 15));
		lblID.setBounds(350, 74, 77, 36);
		add(lblID);
		
		
		txtID.setFont(new Font("굴림", Font.PLAIN, 15));
		txtID.setColumns(10);
		txtID.setText(userDTO.getId());
		txtID.setEditable(false);
		txtID.setBounds(350, 120, 291, 36);
		add(txtID);
		
		
		lblGender.setFont(new Font("굴림", Font.PLAIN, 15));
		lblGender.setBounds(704, 166, 77, 36);
		add(lblGender);
		
		
		txtGender.setFont(new Font("굴림", Font.PLAIN, 15));
		txtGender.setColumns(10);
		if(userDTO.getGender() == 0) {
			txtGender.setText("Male");
		}else {
			txtGender.setText("Female");
		}
		txtGender.setEditable(false);
		txtGender.setBounds(704, 212, 291, 36);
		add(txtGender);
		
		
		lblBirth.setFont(new Font("굴림", Font.PLAIN, 15));
		lblBirth.setBounds(350, 166, 77, 36);
		add(lblBirth);
		
		
		txtBirth.setFont(new Font("굴림", Font.PLAIN, 15));
		txtBirth.setColumns(10);
		txtBirth.setEditable(false);
		txtBirth.setText(userDTO.getBirth());
		txtBirth.setBounds(350, 212, 291, 36);
		add(txtBirth);
		
		
		lblJoinDate.setFont(new Font("굴림", Font.PLAIN, 15));
		lblJoinDate.setBounds(704, 258, 77, 36);
		add(lblJoinDate);
		
		
		txtJoinDate.setFont(new Font("굴림", Font.PLAIN, 15));
		txtJoinDate.setColumns(10);
		txtJoinDate.setEditable(false);
		txtJoinDate.setText(userDTO.getJoindate());
		txtJoinDate.setBounds(704, 304, 291, 36);
		add(txtJoinDate);
		
		
		lblEmail.setFont(new Font("굴림", Font.PLAIN, 15));
		lblEmail.setBounds(350, 258, 77, 36);
		add(lblEmail);
		
		
		txtEmail.setFont(new Font("굴림", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setEditable(false);
		txtEmail.setText(userDTO.getEmail());
		txtEmail.setBounds(350, 304, 291, 36);
		add(txtEmail);
		
		
		lblPD.setForeground(new Color(0, 64, 128));
		lblPD.setFont(new Font("굴림", Font.PLAIN, 13));
		lblPD.setBounds(350, 28, 148, 36);
		add(lblPD);
		
		
		lblCP.setForeground(new Color(0, 64, 128));
		lblCP.setFont(new Font("굴림", Font.PLAIN, 13));
		lblCP.setBounds(350, 376, 148, 36);
		add(lblCP);
		
		
		lblPwd.setFont(new Font("굴림", Font.PLAIN, 15));
		lblPwd.setBounds(350, 422, 171, 36);
		add(lblPwd);
		
		
		txtPwd.setBounds(350, 468, 291, 36);
		add(txtPwd);
		
		
		lblChagePwd.setFont(new Font("굴림", Font.PLAIN, 15));
		lblChagePwd.setBounds(704, 422, 259, 36);
		add(lblChagePwd);
		
		
		txtPwdChk.setBounds(704, 468, 291, 36);
		add(txtPwdChk);
		
		
		// 파일 업로드
		btnFile.setBounds(61, 383, 91, 23);
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jFileChooser.showOpenDialog(null);
				originFilePath = jFileChooser.getSelectedFile().getPath();
				lblFileChoose.setText(originFilePath);
			}
		});
		add(btnFile);

		lblFileChoose.setBounds(164, 387, 146, 15);
		add(lblFileChoose);
		
		
		// 수정 버튼
		btnUpload.setFont(new Font("굴림", Font.PLAIN, 15));
		btnUpload.setBounds(350, 538, 108, 36);
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//파라미터 처리
				String id = txtID.getText();
				String pw = new String(txtPwd.getPassword());
				String pwchk = new String(txtPwdChk.getPassword());
				String name = txtName.getText();
				
				// 입력값 검사
				if(cu.isNullOrEmpty(new String[] {id, pw, pwchk, name})) {
					JOptionPane.showMessageDialog(null, "모둔 값을 입력해 주세요!");
					return;
				}
				if(pw.equals(pwchk) == false) {
					JOptionPane.showMessageDialog(null, "패스워드가 일치하지 않습니다!");
					return;
				}

				// 파일 업로드
				String img = null;
				if (originFilePath == null) {
					img = "assets\\img\\profileIMG\\default.png";
				} else {
					FtpService ftpService = new FtpService();
					img = ftpService.uploadFile(originFilePath, "profileIMG");
					ftpService.close();
				}

				// 사용자 입력 값으로 DTO 생성
				UserDTO userDTO = UserDTO.builder()
						.id(id)
						.password(pw)
						.name(name)
						.img(img)
						.build();
						
				// DB와 프로필 수정 로직 실행
				SignDAO signDAO = new SignDAO();
				int result = signDAO.editProfile(userDTO);
				switch(result) {
					case 1:
						JOptionPane.showMessageDialog(null, "프로필이 수정되었습니다.");
						String remote = "http://aws.akotis.kr:8080/NextTrip/";
						session.setAttributes("sNAME", userDTO.getName());
						session.setAttributes("sIMG", remote + userDTO.getImg().replace("\\", "/"));
						win.setContentPane(new MainTabPanel(win));
						win.revalidate();
						break;
					case -1:
						JOptionPane.showMessageDialog(null, "에러 : 관리자에게 문의하세요");
						break;
				}
			}
		});
		add(btnUpload);
		lblAlert.setBounds(61, 478, 238, 15);
		
		add(lblAlert);
		
	}
}
