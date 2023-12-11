package domain.bookmark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import config.MysqlConn;
import domain.search.TourSpotDTO;

public class BookMarkDAO {
	private Connection conn = MysqlConn.getConnection();

	// 북마크 추가
	public void addBookMark(TourSpotDTO tourSpotDTO, String name) {
		String query = "INSERT INTO bookmark_table(contentid, firstimage, addr1, name, title) VALUES(?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, tourSpotDTO.getContentid());
			pstmt.setString(2, tourSpotDTO.getFirstimage());
			pstmt.setString(3, tourSpotDTO.getAddr1());
			pstmt.setString(4, name);
			pstmt.setString(5, tourSpotDTO.getTitle());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 북마크 삭제
	public void deleteBookMark(String contentid, String name) {
		String query = "DELETE FROM bookmark_table WHERE contentid = ? AND name = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, contentid);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 북마크 여부 확인
	public boolean isCheck(String contentid, String name) {
		String query = "SELECT COUNT(*) FROM bookmark_table WHERE contentid = ? AND name = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, contentid);
			pstmt.setString(2, name);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	// 북마크 리스트 출력
	public ArrayList<TourSpotDTO> getBookMarkList(String name) {
		String query = "SELECT * FROM bookmark_table WHERE name = ?";
		ArrayList<TourSpotDTO> bookmarkList = new ArrayList<>();
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				TourSpotDTO tourSpotDTO = new TourSpotDTO();
				tourSpotDTO.setContentid(rs.getString(2));
				tourSpotDTO.setFirstimage(rs.getString(3));
				tourSpotDTO.setAddr1(rs.getString(4));
				tourSpotDTO.setTitle(rs.getString(6));
				bookmarkList.add(tourSpotDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookmarkList;
	}

}
