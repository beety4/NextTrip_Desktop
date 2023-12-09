package config.customlib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import config.CryptoModule;
import domain.sign.SignDAO;
import domain.sign.UserDTO;

public class CustomCookie {
	private final String folderPath = System.getProperty("user.home") + "\\Documents\\NextTrip";
	private final String filePath = folderPath + "\\cookie.dat";
	private CustomSession session = new CustomSession();
	private CryptoModule cryptoModule = new CryptoModule();
	
	
	// 쿠키 값 설정
	public void setCookie() {
		try {
			// 디렉토리가 없을 시 생성
			Path chkFolder = Paths.get(folderPath);
			Files.createDirectories(chkFolder);
			
			// 파일이 없을 시 생성
			Path chkFile = Paths.get(filePath);
			Files.createFile(chkFile);
		}catch(Exception e) {
			System.out.println("====File AlreadyExists====");
		}
		
		
		
		try {
			// 쿠키에 넣을 데이터 넣을 데이터 세팅
			String id = (String)session.getAttributes("sID");
			LocalDateTime expireDate = LocalDateTime.now().plusDays(30);
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");
			String datetime = expireDate.format(format);
			
			
			// 쿠키 생성
			CookieDTO cookie = new CookieDTO(id, datetime);
			String digest = cryptoModule.aesCBCEncode(cookie.toString());
			
			// 쿠키 데이터 파일에 저장
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));
			bw.write(digest);
			bw.close();
			
			// 무결성을 위한 SHA-1 값 DB저장
			String fileHash = cryptoModule.getFileHash(filePath);
			SignDAO userDAO = new SignDAO();
			userDAO.setFileHash(id, fileHash);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 쿠키 값 가져오기
	public int getCookie() {
		try {
			// 파일이 존재하지 않다면 생성
			File file = new File(filePath);
			if(file.exists() == false) {
				return 2;
			}
			
			// 쿠키 데이터 가져오기
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = br.readLine();
			br.close();
			if(line == null || line.isBlank()) {
				return 2;
			}
			
			// 쿠키 값 확인
			String original = cryptoModule.aesCBCDecode(line);
			StringTokenizer st = new StringTokenizer(original);
			CookieDTO cookie = new CookieDTO(st.nextToken(), st.nextToken());
			
			String id = cookie.getValue();
			boolean integrity = false;
			
			// 쿠키 데이터 무결성 검사
			SignDAO userDAO = new SignDAO();
			ArrayList<String> hashList = userDAO.getFileHash(id);
			String local = cryptoModule.getFileHash(filePath);
			
			for(String value : hashList) {
				if(local.equals(value)) {
					integrity = true;
				}
			}
			
			// 무결성 검사 실패! - 위변조 감지
			if(integrity == false) {
				invalidate();
				return 1;
			}
			
			// 쿠키 만료 확인 후 재발급
			LocalDateTime now = LocalDateTime.now();
			if(now.isAfter(cookie.getExpires())) {
				// 만료된 쿠키라면 DB에서도 삭제
				userDAO.rmFileHash(id, local);
				invalidate();
			}
			
			UserDTO userDTO = userDAO.getUserInfo(id);
			session.setAttributes("sID", id);
			session.setAttributes("sNAME", userDTO.getName());
			session.setAttributes("sIMG", userDTO.getImg());
		}catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	// 쿠키 파일 내용 삭제
	public void invalidate() {
		try {
			new FileWriter(filePath, false).close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
