package domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourSpotSearchDTO {
	private String addr1;
	private String areacode;
	private String cat1;
	private String cat2;
	private String cat3;
	private String contentid;
	private String contenttypeid;
	private String firstimage;
	private String firstimage2;
	private String mapx;
	private String mapy;
	private String modifiedtime;
	private String title;
}
