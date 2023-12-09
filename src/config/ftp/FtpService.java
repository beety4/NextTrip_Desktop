package config.ftp;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPSClient;

import config.FtpConn;
import config.customlib.CustomSession;
import domain.sign.SignDAO;
import domain.sign.UserDTO;

public class FtpService {
	private FtpConn ftpConn;
	private FTPSClient ftp;
	
	// 현재 유저 정보로 FTPS 연결
	public FtpService() {
		CustomSession session = new CustomSession();
		String id = (String)session.getAttributes("sID");
		
		SignDAO signDAO = new SignDAO();
		UserDTO userDTO = signDAO.getUserInfo(id);
		
		ftpConn = new FtpConn(userDTO.getId(), userDTO.getPassword());
		ftp = ftpConn.getConnection();
	}
	
	// 로컬 파일 업로드 후 DB저장 용 경로 반환
	public String uploadFile(String originFilePath, String remoteDIR) {
		String fileName = "";
		try {
			ftp.changeWorkingDirectory("/" + remoteDIR);
			
			File origin = new File(originFilePath);
			fileName = origin.getName();
			FileInputStream fis = new FileInputStream(origin);
			boolean isSuccess = ftp.storeFile(fileName, fis);
			
			// 업로드 실패 시 null 반환
			if(isSuccess == false) {
				System.out.println("===UPLOAD FAILED===");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return "assets\\img\\" + remoteDIR + "\\" + fileName;
	}
	

	// 연결 종료
	public void close() {
		try {
			ftp.logout();
			ftp.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
