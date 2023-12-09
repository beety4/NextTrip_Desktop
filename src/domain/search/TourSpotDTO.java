package domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
// DTO에 선언한 파라미터만 가져오고 나머진 무시
public class TourSpotDTO {
	private String addr1;
	private String addr2;
	private String areacode;
	private String cat1;
	private String cat2;
	private String cat3;
	private String contentid;
	private String firstimage;
	private String firstimage2;
	private String mapx;
	private String mapy;
	private String title;
	private String zipcode;
}
