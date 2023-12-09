package config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConn {
	// 기본 API 요청 URL 및 파라미터 설정
	// 사용 API : https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15101578#
	private final String baseURL = "https://apis.data.go.kr/B551011/KorService1";
	private final String serviceKey = "&serviceKey=OCFNR8SFXaWj/CJUIqY6GwNYqrEFG9VHHGTquXRjlNdxq8uE98uGvv7XsLXdwfZ1fXcjLW6GDJZIBLMZqC/aDg==";
	private final String defaultParam = "&MobileOS=ETC&MobileApp=NextTrip&_type=json";
	
	public String getJson(String param) {
		StringBuffer result = new StringBuffer();
		try {
			// 생성한 API 주소로 연결
			URL url = new URL(baseURL+ param + serviceKey + defaultParam);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			// Status code로 정상 연결 확인
			BufferedReader br;
			int httpStatus = conn.getResponseCode();
			if(httpStatus >= 200 && httpStatus <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			// 정상 연결 시 페이지  내용 읽기
			String line;
			while((line = br.readLine()) != null) {
				result.append(line + "\n");
			}
			br.close();
			conn.disconnect();
		}catch(Exception e) {
			System.out.println("=====Http Conn ERROR=====");
			e.printStackTrace();
		}
		//System.out.println("URL : " + baseURL+ param + serviceKey + defaultParam);
		//System.out.println(result.toString());
		
		// 받은 json 결과값 반환
		return result.toString();
	}
	
}
