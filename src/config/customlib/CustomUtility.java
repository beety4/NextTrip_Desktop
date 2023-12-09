package config.customlib;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import domain.review.ReviewDTO;

public class CustomUtility {
	// JLabel 혹은 JButton에 이미지 삽입과 크기를 조정하는 메소드
	public <T> void setImg(T jImg, String path, int w, int h) {
		Image icon = new ImageIcon(path).getImage();
		Image change = icon.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		if(jImg instanceof JButton) {
			((JButton) jImg).setIcon(new ImageIcon(change));
		} else {
			((JLabel) jImg).setIcon(new ImageIcon(change));
		}
	}
	
	// String 혹은 String[]에 Null값 혹은 미입력 값이 있는지 확인
	public boolean isNullOrEmpty(Object comp) {
		if(comp instanceof String) {
			String value = (String)comp;
			return (value == null || value.isBlank());
		} else {
			String[] value = (String[])comp;
			return Arrays.asList(value).stream().allMatch(x -> x == null || x.isBlank());
		}
	}
	
	// radioButton[], CheckButton[] 중 선택값 반환
	public String getWhatSelected(JRadioButton[] rdbtns) {
		for (JRadioButton rdbtn : rdbtns) {
			if (rdbtn.isSelected()) {
				return rdbtn.getText();
			}
		}
		return null;
	}
	
	// 이메일 확인용 정규표현식
	public boolean isEmail(String email) {
		String regex =  "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	
	// review table 세팅을 위한 ArrayList > 2차원 배열
	public String[][] listToArray(ArrayList<ReviewDTO> list) {
		if(list == null || list.isEmpty()) {
			return null;
		}
		
		String[][] result = new String[list.size()][5];
		for(int i=0; i<list.size(); i++) {
			result[i][0] = Integer.toString(list.get(i).getReviewNo());
			result[i][1] = list.get(i).getTitle();
			result[i][2] = list.get(i).getRegion();
			result[i][3] = list.get(i).getDate().split(" ")[0];
			result[i][4] = Integer.toString(list.get(i).getViewC());
		}
		return result;
	}
	
	
	
	
	

}
