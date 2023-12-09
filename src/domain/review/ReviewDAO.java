package domain.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import config.MysqlConn;

public class ReviewDAO {
	private Connection conn = MysqlConn.getConnection();

	// Review 게시글의 마지막 번호 가져오기
	public int getLastNo() {
		String query = "SELECT MAX(reviewNo) FROM review_table WHERE available = 1";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			return rs.next() ? rs.getInt(1)+1 : 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	// Review 게시글 총 개수
	public int getReviewCnt() {
		String query = "SELECT COUNT(reviewNo) FROM review_table WHERE available = 1";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			return rs.next() ? rs.getInt(1) : 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	// 게시글 리스트 가져오기 and 페이징 처리
	public ArrayList<ReviewDTO> getReviewList(int pageNo, int search, String searchIt) {
		String query = null;
		ResultSet rs = null;
		ArrayList<ReviewDTO> reviewList = new ArrayList<>();
		try {
			PreparedStatement pstmt = null;
			int start = (pageNo - 1) * 10;
			
			// 검색 여부에 따른 쿼리문 설정
			switch(search) {
				case 1:		// 제목 검색 쿼리
					query = "SELECT * FROM review_table WHERE available = 1 AND title LIKE ? ORDER BY reviewNo DESC";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, "%"+searchIt+"%");
					break;
				case 2:		// 본문 검색 쿼리
					query = "SELECT * FROM review_table WHERE available = 1 AND content LIKE ? ORDER BY reviewNo DESC";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, "%"+searchIt+"%");
					break;
				case 3:		// 제목+본문 검색 쿼리
					query = "SELECT * FROM review_table WHERE available = 1 AND (title LIKE ? OR content LIKE ?) ORDER BY reviewNo DESC";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, "%"+searchIt+"%");
					pstmt.setString(2, "%"+searchIt+"%");
					break;
				default:	// 기본 쿼리
					query = "SELECT * FROM review_table WHERE available = 1 ORDER BY reviewNo DESC LIMIT ?, 10";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, start);
					break;
			}
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				ReviewDTO reviewDTO = ReviewDTO.builder()
						.reviewNo(rs.getInt(1))
						.name(rs.getString(2))
						.title(rs.getString(3))
						.region(rs.getString(4))
						.content(rs.getString(5))
						.date(rs.getString(6))
						.viewC(rs.getInt(7))
						.build();
				reviewList.add(reviewDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviewList;
	}
	
	
	// 게시글 상세 보기
	public ReviewDTO getReviewDetail(int reviewNo) {
		String query = "SELECT A.*, COUNT(B.name) AS likeC " +
					   "FROM review_table AS A " +
					   "LEFT JOIN review_likeC_table AS B " +
					   "ON A.reviewNo = B.reviewNo " +
					   "WHERE A.reviewNo = ? AND A.available = 1 " +
					   "GROUP BY A.reviewNo";
		
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ReviewDTO reviewDTO = ReviewDTO.builder()
						.reviewNo(rs.getInt(1))
						.name(rs.getString(2))
						.title(rs.getString(3))
						.region(rs.getString(4))
						.content(rs.getString(5))
						.date(rs.getString(6))
						.viewC(rs.getInt(7))
						.img(rs.getString(9))
						.likeC(rs.getInt(10))
						.build();
				return reviewDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	// 게시글 작성하기
	public int addReview(ReviewDTO reviewDTO) {
		String query = "INSERT INTO review_table(name, title, region, content, img) VALUES(?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reviewDTO.getName());
			pstmt.setString(2, reviewDTO.getTitle());
			pstmt.setString(3, reviewDTO.getRegion());
			pstmt.setString(4, reviewDTO.getContent());
			pstmt.setString(5, reviewDTO.getImg());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// 게시글 삭제
	public int deleteReview(int reviewNo) {
		String query = "UPDATE review_table SET available = 0 WHERE reviewNo = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// 게시글 수정
	public int editReview(ReviewDTO reviewDTO) {
		String query = "UPDATE review_table SET title = ?, region = ?, content = ? WHERE reviewNo = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reviewDTO.getTitle());
			pstmt.setString(2, reviewDTO.getRegion());
			pstmt.setString(3, reviewDTO.getContent());
			pstmt.setInt(4, reviewDTO.getReviewNo());

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
	
	
	
	// 조회수 추가
	public void addView(int reviewNo) {
		String query = "UPDATE review_table SET viewC = viewC + 1 WHERE reviewNo = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	// 댓글 작성
	public void addReviewComment(CommentDTO commentDTO) {
		String query = "INSERT INTO review_comment_table(reviewNo, name, content) VALUES(?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, commentDTO.getReviewNo());
			pstmt.setString(2, commentDTO.getName());
			pstmt.setString(3, commentDTO.getContent());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 댓글 삭제
	public void deleteReviewComment(int commentNo) {
		String query = "UPDATE review_comment_table SET available = 0 WHERE commentNo = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, commentNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 댓글 목록 가져오기
	public ArrayList<CommentDTO> getCommentList(int reviewNo) {
		String query = "SELECT * FROM review_comment_table WHERE reviewNo = ? AND available = 1";
		ArrayList<CommentDTO> commentList = new ArrayList<>();
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setCommentNo(rs.getInt(1));
				commentDTO.setReviewNo(reviewNo);
				commentDTO.setName(rs.getString(3));
				commentDTO.setContent(rs.getString(4));
				commentDTO.setDate(rs.getString(6));
				commentList.add(commentDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentList;
	}
	
	// 댓글 정보 하나 가져오기
	public CommentDTO getComment(int commentNo) {
		String query = "SELECT * FROM review_comment_table WHERE commentNo = ? AND available = 1";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, commentNo);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				CommentDTO commentDTO = new CommentDTO();
				commentDTO.setCommentNo(rs.getInt(1));
				commentDTO.setReviewNo(rs.getInt(2));
				commentDTO.setName(rs.getString(3));
				commentDTO.setContent(rs.getString(4));
				commentDTO.setDate(rs.getString(6));
				return commentDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	// 좋아요 추가
	public void addReviewLike(int reviewNo, String name) {
		String query = "INSERT INTO review_likeC_table(reviewNo, name) VALUES(?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 좋아요 삭제
	public void deleteReviewLike(int reviewNo, String name) {
		String query = "DELETE FROM review_likeC_table WHERE reviewNo = ? AND name = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 좋아요 여부 확인
	public boolean isLike(int reviewNo, String name) {
		String query = "SELECT * FROM review_likeC_table WHERE reviewNo = ? AND name = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			pstmt.setString(2, name);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 현재 좋아요 개수
	public int getLikeCnt(int reviewNo) {
		String query = "SELECT COUNT(*) FROM review_likeC_table WHERE reviewNo = ?";
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
