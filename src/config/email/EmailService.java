package config.email;

import java.util.Properties;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import config.CryptoModule;

public class EmailService {
	CryptoModule cryptoModule = new CryptoModule();
	
	public String sendtoMail(String email) {
		String to = email;
		// 랜덤 인증키 생성
		String authKey = cryptoModule.getNewKey();
		
		// 이메일 내용 작성
		String from = "202244016@itc.ac.kr";
		String subject = "[Cowork] 이메일 인증을 완료해주세요!";
		String content="";
		content+= "<div style='margin:20px;'>";
	    content+= "<h1> Cowork 이메일 인증</h1>";
	    content+= "<br>";
	    content+= "<p>아래 코드를 복사해 입력해주세요<p>";
	    content+= "<br>";
	    content+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
	    content+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
	    content+= "<div style='font-size:130%'>";
	    content+= "CODE : <strong>";
	    content+= authKey +"</strong><div><br/> ";
	    content+= "</div>";
	    
	    // 메일 서버 연결 Properties
	    Properties p = new Properties();
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.fallback", "false");
		p.put("mail.smtp.ssl.protocols", "TLSv1.2");
		p.put("mail.smtp.ssl.enable", "true");

	        
		try{
			// 메세지에 정보 담기
			Authenticator auth = new GmailConn();
		    Session ses = Session.getInstance(p, auth);
		    MimeMessage msg = new MimeMessage(ses);
		    msg.setSubject(subject);
		    Address fromAddr = new InternetAddress(from);
		    msg.setFrom(fromAddr);
		    Address toAddr = new InternetAddress(to);
		    msg.addRecipient(Message.RecipientType.TO, toAddr);
		    msg.setContent(content, "text/html;charset=UTF-8");
		    
		    // 쓰레드로 메일 전송
		    Thread thread = new Thread() {
		    	public void run() {
		    		try {
						Transport.send(msg);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
		    	}
		    };
		    thread.start();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// 생성한 인증키 값 반환
		return authKey;
	}
}
