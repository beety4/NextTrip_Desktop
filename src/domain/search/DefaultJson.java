package domain.search;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
// API 로 가져온 JSON 데이터 파싱을 위한 기본 DTO
// 제너릭 타입으로 받아올 리스트 정보의 DTO 를 외부에서 결정할 수 있다.
// 처음엔 생성자를 통한 inner class 생성 방식으로 했지만 오류가 나서..
// static을 통한 inner class 생성으로 방식을 변경하였다.
// 그래서 그런지 제너릭타입을 재귀적으로 넘겨줘야 했다.
public class DefaultJson<T> {
	private Response<T> response;
	private T testDTO;
	
	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Response<T> {
		//private Header header;
		private Body<T> body;
				
		@Getter
		@Setter
		@JsonIgnoreProperties(ignoreUnknown = true)
		// 위 어노테이션으로 DTO에 정의되지 않은 파라미터는 무시
		public static class Body<T> {
			// 받은 Items 값들
			private Items<T> items;
			private int totalCount;
			
			@Getter
			@Setter
			public static class Items<T> {
				// 가져온 Item 값을 외부의 T 타입으로 List 저장
				private List<T> item;
			}
		}
		
	}

	// 최종 List<DTO> 반환 메소드
	public List<T> getDTOList() {
		return response.getBody().getItems().getItem();
	}
	
	// 첫번째 값만 반환
	public T getDTO() {
		return response.getBody().getItems().getItem().get(0);
	}
	
	public int getTotalCnt() {
		return response.getBody().getTotalCount();
	}
}

