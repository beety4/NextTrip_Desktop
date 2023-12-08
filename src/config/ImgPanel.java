package config;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImgPanel<T> extends JPanel {
	private static final long serialVersionUID = 1L;
	private String path;
	private T jImg;
	
	// 생성자를 통해 패널과 이미지가 들어간 버튼or라벨 생성
	public ImgPanel(T jImg, String path) {
		this.path = path;
		this.jImg = jImg;
		ImageIcon img = new ImageIcon(path);
		if(jImg instanceof JButton) {
			((JButton) jImg).setIcon(img);
		} else {
			((JLabel) jImg).setIcon(img);
		}
		this.add((Component) jImg);
	}
	
	// 패널 내 버튼or라벨 가져오기
	public T getType() {
		return jImg;
	}
	
	// 패널 내 이미지의 사이즈 조절
	public void setImgSize(int w, int h) {
		this.remove((Component) jImg);
		Image img = new ImageIcon(path).getImage();
		Image change = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(change);
		if(jImg instanceof JButton) {
			((JButton) jImg).setIcon(changeIcon);
		} else {
			((JLabel) jImg).setIcon(changeIcon);
		}
		this.add((Component) jImg);
	}
	
}
