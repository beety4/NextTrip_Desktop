package domain.review;

import lombok.Data;

@Data
public class CommentDTO {
	private int commentNo;
	private int reviewNo;
	private String name;
	private String content;
	private int available;
	private String date;
}
