package domain.sign;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import config.CryptoModule;
import config.MysqlConn;


public class SignDAO {
	private CryptoModule cryptoModule;
	private Connection conn = MysqlConn.getConnection();
	
	public SignDAO() {
		cryptoModule = new CryptoModule();
	}
	
	// 로그인 DAO
	public int login(String id, String password) {
		String query = "SELECT password FROM user_table WHERE id = ?";
		String digest = cryptoModule.getSHA256(password);
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(digest.equals(rs.getString(1))) {
					return 1;
				} else {
					return 0;
				}
			}
			return -2;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// 회원가입 DAO
	public int register(UserDTO userDTO) {
		String query = "INSERT INTO user_table(id,password,name,birth,gender,email,img,principal) VALUES(?,?,?,?,?,?,?,?)";
		String digest = cryptoModule.getSHA256(userDTO.getPassword());
		
		if(userDTO.getImg() == null) {
			userDTO.setImg("default.png");
		}
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userDTO.getId());
			pstmt.setString(2, digest);
			pstmt.setString(3, userDTO.getName());
			pstmt.setString(4, userDTO.getBirth());
			pstmt.setInt(5, userDTO.getGender());
			pstmt.setString(6, userDTO.getEmail());
			pstmt.setString(7, userDTO.getImg());
			pstmt.setString(8, userDTO.getPrincipal());

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// ID로 User Info DTO 가져오기
	public UserDTO getUserInfo(String id) {
		String query = "SELECT * FROM user_table WHERE id = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return UserDTO.builder()
						.id(rs.getString(1))
						.name(rs.getString(3))
						.birth(rs.getString(4))
						.gender(rs.getInt(5))
						.email(rs.getString(6))
						.joindate(rs.getString(7))
						.img(rs.getString(8)).build();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// ID로 User Info DTO 가져오기 Google Oauth2 - 메소드 오버로딩
	public UserDTO getUserInfo(String id, String principal) {
		String query = "SELECT * FROM user_table WHERE id = ? AND principal = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, principal);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return UserDTO.builder()
						.id(rs.getString(1))
						.name(rs.getString(3))
						.birth(rs.getString(4))
						.gender(rs.getInt(5))
						.email(rs.getString(6))
						.joindate(rs.getString(7))
						.img(rs.getString(8))
						.principal(rs.getString(9)).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	// 메소드 오버로딩을 통한 프로필 편집 ( name, password )
	public int editProfile(UserDTO userDTO) {
		String SQL = "UPDATE user_table SET name = ?, password = ?, img = ? WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			String digest = cryptoModule.getSHA256(userDTO.getPassword());
			pstmt.setString(1, userDTO.getName());
			pstmt.setString(2, digest);
			pstmt.setString(3, userDTO.getImg());
			pstmt.setString(4, userDTO.getId());

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
		
		
	
	
	
	
	
	// 파일해쉬 저장 DAO
	public int setFileHash(String id, String fileHash) {
		String query = "INSERT INTO user_hash_table(id, hash) VALUES(?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, fileHash);

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// id값 기준 파일해쉬 값 list로 가져오는 DAO
	public ArrayList<String> getFileHash(String id) {
		String query = "SELECT hash FROM user_hash_table WHERE id = ?";
		ArrayList<String> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// id값 기준 파일해쉬 값 중 선택 값 삭제 DAO
	public void rmFileHash(String id, String hash) {
		String query = "DELETE FROM hash_table WHERE id = ? AND hash = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, hash);
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
