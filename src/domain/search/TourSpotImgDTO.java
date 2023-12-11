package domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
//DTO에 선언한 파라미터만 가져오고 나머진 무시
public class TourSpotImgDTO {
	private String contentid;
	private String originimgurl;
	private String imgname;
}
