package domain.sign;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.api.ApiService;
import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.search.TourSpotDTO;
import domain.search.TourSpotSearchDTO;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;

public class ProfilePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblChagePwd = new JLabel("Change Password");
	private JLabel lblPD = new JLabel("Personal Detail");
	
	private JLabel lblProfileImg = new JLabel("프로필 사진");
	private JButton btnFile = new JButton("파일 선택");
	private JLabel lblSelectedImg = new JLabel("선택된 파일 없음");
	
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
	private JTextField txtPwd = new JTextField();
	private JLabel lblCP = new JLabel("Change Password");
	private JTextField txtChangePwd = new JTextField();
	private JButton btnUpload = new JButton("Upload");
	
	
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
		
	
		SignDAO signDAO = new SignDAO();
		String id = (String)session.getAttributes("sID");
		UserDTO userDTO = signDAO.getUserInfo(id);
		
		
		String profileImg = userDTO.getImg();
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
		
		
		txtPwd.setFont(new Font("굴림", Font.PLAIN, 15));
		txtPwd.setColumns(10);
		txtPwd.setBounds(350, 468, 291, 36);
		add(txtPwd);
		
		
		lblChagePwd.setFont(new Font("굴림", Font.PLAIN, 15));
		lblChagePwd.setBounds(704, 422, 259, 36);
		add(lblChagePwd);
		
		
		txtChangePwd.setFont(new Font("굴림", Font.PLAIN, 15));
		txtChangePwd.setColumns(10);
		txtChangePwd.setBounds(704, 468, 291, 36);
		add(txtChangePwd);
		
		
		btnFile.setBounds(61, 383, 91, 23);
		add(btnFile);
		
		
		lblSelectedImg.setBounds(164, 387, 146, 15);
		add(lblSelectedImg);
		
		
		btnUpload.setFont(new Font("굴림", Font.PLAIN, 15));
		btnUpload.setBounds(350, 538, 108, 36);
		add(btnUpload);

	}
}
