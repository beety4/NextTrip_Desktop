package config.customlib;

import java.awt.Image;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import domain.review.ReviewDTO;
import domain.search.TourSpotDTO;
import domain.search.TourSpotSearchDTO;

public class CustomUtility {
	// JLabel 혹은 JButton에 이미지 삽입과 크기를 조정하는 메소드
	public void setImg(Object jImg, String path, int w, int h) {
		Image img = null;
		
		// URL 에서 이미지를 불러오는 경우
		if(path.startsWith("http")) {
			String encodePath = remakeURL(path);
			try {
				URL url = new URL(encodePath);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestProperty("Referer", path);
				img = ImageIO.read(conn.getInputStream());
			}catch(Exception e) {
				e.printStackTrace();
			}
		// 로컬에서 이미지를 불러오는 경우
		} else {
			img = new ImageIcon(path).getImage();
		}
			
		// 이미지 사이즈 조절 후 객체에 맞게 반환
		Image change = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		if(jImg instanceof JButton) {
			((JButton) jImg).setIcon(new ImageIcon(change));
		} else {
			((JLabel) jImg).setIcon(new ImageIcon(change));
		}
	}
	
	// url의 파일명 한글 인코딩 작업 후 반환
	public String remakeURL(String url) {	
		try {
			int index = url.lastIndexOf("/");
			String mainPath = url.substring(0, index + 1);
			String fileName = url.substring(index + 1);
			String encodeVal = URLEncoder.encode(fileName, "UTF-8");
			return mainPath + encodeVal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	
	// review table 세팅을 위한 ArrayList -> 2차원 배열
	public String[][] reviewListToArray(ArrayList<ReviewDTO> list) {
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
	
	// TourSpot 검색 ArrayList -> 2차원 배열
	public String[][] tourSearchListToArray(ArrayList<TourSpotSearchDTO> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		String[][] result = new String[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			result[i][0] = list.get(i).getContentid();
			result[i][1] = list.get(i).getTitle();
			result[i][2] = list.get(i).getAddr1();
			result[i][3] = list.get(i).getModifiedtime();
		}
		return result;
	}
	
	// TourSpot ArrayList -> 2차원 배열
	public String[][] tourSpotListToArray(ArrayList<TourSpotDTO> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		String[][] result = new String[list.size()][3];
		for (int i = 0; i < list.size(); i++) {
			result[i][0] = list.get(i).getContentid();
			result[i][1] = list.get(i).getTitle();
			result[i][2] = list.get(i).getAddr1();
		}
		return result;
	}
	
	
	
	

}
