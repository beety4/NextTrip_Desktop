package config.email;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class GmailConn extends Authenticator {
	// 이메일 연결
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("202244016@itc.ac.kr", "abddepygycrmewio");
	}
}
