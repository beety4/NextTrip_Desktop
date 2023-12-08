package config.customlib;

import java.util.HashMap;
import java.util.Map;

public class CustomSession {
	private static Map<String, Object> sessionMap = new HashMap<>();
	
	// 세션에 값 저장
	public void setAttributes(String name, Object value) {
		sessionMap.put(name, value);
	}
	
	// 세션 값 가져오기
	public Object getAttributes(String name) {
		if(sessionMap.containsKey(name)) {
			return sessionMap.get(name);
		} else {
			return null;
		}
	}
	
	// 세션 값 삭제
	public void removeAttributes(String name) {
		sessionMap.remove(name);
	}
	
	// 세션 값 초기화
	public void invalidate() {
		sessionMap.clear();
	}
}
