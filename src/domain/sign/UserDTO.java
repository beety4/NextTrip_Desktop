package domain.sign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String id;
	private String password;
	private String name;
	private String birth;
	private int gender;
	private String email;
	private String joindate;
	private String img;
	private String principal;
}
