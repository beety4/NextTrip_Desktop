package domain.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourSpotImgDTO {
	private String contentid;
	private String originimgurl;
	private String imgname;
}
