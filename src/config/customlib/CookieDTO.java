package config.customlib;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import config.CryptoModule;
import lombok.Data;

@Data
public class CookieDTO {
	private CryptoModule cryptoModule = new CryptoModule();
	private String value;
	private LocalDateTime expires;
	private String checksum;
		
	// String -> LocalDateTime 타입 변환 (쿠키 읽을때)
	public CookieDTO(String value, String expires) {
		this.value = value;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");
		this.expires = LocalDateTime.parse(expires, format);
		
		try {
			checksum = cryptoModule.aesCBCEncode(UUID.randomUUID().toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");
		String datetime = expires.format(format);
		sb.append(value).append(" ").append(datetime).append(" ").append(checksum);
		return sb.toString();
	}
}
