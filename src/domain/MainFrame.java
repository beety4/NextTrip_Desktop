package domain;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import config.customlib.CustomCookie;
import domain.sign.SignPanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					Toolkit kit = Toolkit.getDefaultToolkit();
					Image img = kit.getImage("src/resource/logo.png");
					frame.setIconImage(img);
					
					frame.setSize(1100, 700);
					frame.setLocationRelativeTo(null);
					frame.setResizable(false);
					frame.setTitle("NextTrip");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		// 쿠키 여부 검사 후 메인 패널 실행
		CustomCookie customCookie = new CustomCookie();
		switch(customCookie.getCookie()) {
		case 0:	// 로그인 성공
			setContentPane(new MainTabPanel(this));
			revalidate();
			break;
		case 1:	// 무결성 검사 실패! 위변조 감지
			setContentPane(new SignPanel(this));
			revalidate();
			JOptionPane.showMessageDialog(null, "자동 로그인 중 오류가 발생했습니다. 다시 로그인해주세요!");
			break;
		case 2:	// 쿠키 만료. 로그인 필요
			setContentPane(new SignPanel(this));
			revalidate();
			break;
		default:	// 기본값 -> 로그인 화면
			setContentPane(new SignPanel(this));
			revalidate();
		}
	}
	
	
}
