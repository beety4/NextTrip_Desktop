package domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
	private int reviewNo;
	private String name;
	private String title;
	private String region;
	private String content;
	private String date;
	private int viewC;
	private int likeC;
	private int available;
	private String img;
}
