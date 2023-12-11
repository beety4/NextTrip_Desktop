package config.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;

public class FtpConn {
	private final String server = "aws.akotis.kr";	// passive mode : 3020, 3021
	private String id = "";
	private String password = "";
	
	// 생성자를 통한 FTPS 유저 설정
	public FtpConn(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	// FTPS 연결 후 객체 가져오기
	public FTPSClient getConnection() {
		FTPSClient ftp = new FTPSClient();
		try {
			// FTP 세부 설정
			ftp.setControlEncoding("UTF-8");
			ftp.connect(server);
			ftp.login(id, password);
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.execPROT("P");
			ftp.execPBSZ(0);
			return ftp;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(ftp == null || ftp.isConnected() == false) {
				System.out.println("===FTP CONNECT FAILED===");
				return null;
			}
		}
		return null;
	}
	
}
