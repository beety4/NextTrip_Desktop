package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoModule {
	// 암호화, 복호화를 위한 임의의 대칭 키
	private final String ALGORITHM = "AES";
    private final String KEY = "Thi5i$Se(retKeY="; // 암호화에 사용할 키 (16, 24, 32 bytes)
	    
	// AES256로 암호화 진행
	public String aesCBCEncode(String plainText) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	// AES256으로 복호화 진행
	public String aesCBCDecode(String encodeText) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encodeText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
	}
	
	// 쿠키 저장 파일의 파일 해쉬 확인 SHA-1
	public String getFileHash(String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[8192];
            int bytesRead;
            while (	(bytesRead = fileInputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            
            byte[] digest = md.digest();
            
            // 해시값을 16진수로 변환
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	

	// 인증 코드용 5자리 문자/숫자
	public String getNewKey() {
        StringBuffer key = new StringBuffer();
        Random rand = new Random();
 
        for (int i = 0; i < 5; i++) {
            int index = rand.nextInt(3);
            switch (index) {
                case 0:	// 소문자
                    key.append((char) ((int) (rand.nextInt(26)) + 97));
                    break;
                case 1:	// 대문자
                    key.append((char) ((int) (rand.nextInt(26)) + 65));
                    break;
                case 2:	// 숫자
                    key.append((rand.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }
	
	
	// 패스워드 암호화를 위한 SHA256 암호화 메소드
	public String getSHA256(String input) {
		String saltValue = "NextTrip WELCOME!";
		StringBuffer result = new StringBuffer();
		try {
        	// 입력한 값에 Hash Algo 적용
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			/****** 가염 처리과정 ******/
			byte[] salt = saltValue.getBytes();
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8")); // Hash 적용 값을 담아준다.
			/******* chars -> 16진수 String으로 변환 *******/
			for(int i = 0; i < chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if(hex.length() == 1) result.append("0"); // 항상 두 자리 16진수로
				result.append(hex);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
