package config.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GmailConn extends Authenticator {
	// 이메일 연결
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("202244016@itc.ac.kr", "abddepygycrmewio");
	}
}
