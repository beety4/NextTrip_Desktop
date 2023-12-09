package domain;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import config.customlib.CustomCookie;
import config.customlib.CustomSession;
import config.customlib.CustomUtility;
import domain.bookmark.BookMarkPanel;
import domain.intro.IntroPanel;
import domain.review.ReviewPanel;
import domain.search.SearchPanel;
import domain.sign.SignPanel;

public class MainTabPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel logo = new JLabel("로고");
	
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu mnMyInfo = new JMenu("내 정보");
	private JMenuItem mntmProfile = new JMenuItem("프로필");
	private JMenuItem mntmLogout = new JMenuItem("로그아웃");
	private JMenuItem mntmExit = new JMenuItem("종료");
	
	private JMenu mnConfig = new JMenu("설정");
	private JMenuItem mntmBgColor = new JMenuItem("배경색 설정");
	
	
	
	
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
	public MainTabPanel(JFrame win) {
		CustomUtility cUtils = new CustomUtility();
		CustomSession session = new CustomSession();
		CustomCookie customCookie = new CustomCookie();
		setBounds(0, 0, 1100, 700);
		setSize(1100, 700);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		
		tabbedPane.setBounds(0, 71, 1100, 629);
		add(tabbedPane);
		
		// 탭 패널 추가
		tabbedPane.addTab("소개", null, new IntroPanel(win), null);
		tabbedPane.addTab("여행지", null, new SearchPanel(win), null);
		tabbedPane.addTab("여행후기", null, new ReviewPanel(win), null);
		tabbedPane.addTab("북마크", null, new BookMarkPanel(win), null);
		
		
		logo.setBounds(10, 28, 114, 41);
		cUtils.setImg(logo, "src/resource/logo-full.png", 114, 41);
		add(logo);
		
		menuBar.setBounds(0, 0, 1100, 23);
		add(menuBar);
		
		// 내 정보 메뉴
		menuBar.add(mnMyInfo);
		mnMyInfo.add(mntmProfile);
		mntmProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 패널 변경
				// win.setContentpane(myProfile?);
			}
		});
		
		mnMyInfo.add(mntmLogout);
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.invalidate();
				customCookie.invalidate();
				win.setContentPane(new SignPanel(win));
				win.revalidate();
			}
		});
		
		mnMyInfo.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// 설정 메뉴
		menuBar.add(mnConfig);
		mnConfig.add(mntmBgColor);
		
	}

}
