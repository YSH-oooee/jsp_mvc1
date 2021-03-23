package step4_00_board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BoardDAO {
	
	private BoardDAO() {};
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public Connection getConnection() {
		
		String dbUrl = "jdbc:mysql://localhost:3306/step4_board_review?serverTimezone=UTC";
		String dbID = "root";
		String dbPass = "root";
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(dbUrl, dbID, dbPass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	//전체 게시글 개수
	public int getAllBaordCount() {
		
		int count = 0;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select count(*) from board");
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return count;
		
	}
	
	//비밀번호 인증
	public boolean checkPasswd(int num, String password) {
		
		boolean isCheck = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from board where num=? and password=?");
			pstmt.setInt(1, num);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				isCheck = true;
				
				System.out.println("비밀번호 인증 완료");
			} else {
				System.out.println("비밀번호 인증 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return isCheck;
		
	}
	
	//게시글 쓰기(새 게시글 ref=마지막 ref+1 / re_step=1 / re_level=1)
	public boolean writeBoard(BoardDTO bdto) {
		
		boolean isWrite = false;
		
		try {
			
			conn = getConnection();
			
			int ref;
			
			String ref_sql = "select max(ref) from board";
			
			pstmt = conn.prepareStatement(ref_sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				ref = rs.getInt(1);
			} else {
				ref = 1;
			}
			
			String sql = "insert into board (writer, email, title, password, reg_date, ref, re_step, re_level, read_count, content)"
					+ " values(?, ?, ?, ?, now(), ?, 1, 1, 0, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bdto.getWriter());
			pstmt.setString(2, bdto.getEmail());
			pstmt.setString(3, bdto.getTitle());
			pstmt.setString(4, bdto.getPassword());
			pstmt.setInt(5, ref + 1);
			pstmt.setString(6, bdto.getContent());
			
			pstmt.executeUpdate();
			
			isWrite = true;
			
			System.out.println("새 게시글이 등록되었습니다.");
			System.out.println(bdto.getTitle() + "/" + bdto.getWriter());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return isWrite;
		
	}
	
	//전체 게시글 목록
	public ArrayList<BoardDTO> getAllBoard() {
		
		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from board");
			
			rs = pstmt.executeQuery();
			
			BoardDTO bdto;
			
			while (rs.next()) {
				
				bdto = new BoardDTO();
				
				bdto.setNum(rs.getInt(1));
				bdto.setWriter(rs.getString(2));
				bdto.setEmail(rs.getString(3));
				bdto.setTitle(rs.getString(4));
				bdto.setPassword(rs.getString(5));
				bdto.setReg_date(sdf.format(rs.getDate(6)));
				bdto.setRef(rs.getInt(7));
				bdto.setRe_step(rs.getInt(8));
				bdto.setRe_level(rs.getInt(9));
				bdto.setRead_count(rs.getInt(10));
				bdto.setContent(rs.getString(11));
				
				boardList.add(bdto);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return boardList;
		
	}
	
	//하나의 게시글 조회(조회수 증가)
	public BoardDTO getOneBaord(int num) {
		
		BoardDTO bdto = new BoardDTO();
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("update board set read_count = read_count+1 where num=?");
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("select * from board where num=?");
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				bdto.setNum(rs.getInt(1));
				bdto.setWriter(rs.getString(2));
				bdto.setEmail(rs.getString(3));
				bdto.setTitle(rs.getString(4));
				bdto.setPassword(rs.getString(5));
				bdto.setReg_date(sdf.format(rs.getDate(6)));
				bdto.setRef(rs.getInt(7));
				bdto.setRe_step(rs.getInt(8));
				bdto.setRe_level(rs.getInt(9));
				bdto.setRead_count(rs.getInt(10));
				bdto.setContent(rs.getString(11));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return bdto;
		
	}
	
	//하나의 게시글 수정을 위한 조회(조회수 증가X)
		public BoardDTO getUpdateBaord(int num) {
			
			BoardDTO bdto = new BoardDTO();
			
			try {
				
				conn = getConnection();
				
				pstmt = conn.prepareStatement("select * from board where num=?");
				pstmt.setInt(1, num);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					
					bdto.setNum(rs.getInt(1));
					bdto.setWriter(rs.getString(2));
					bdto.setEmail(rs.getString(3));
					bdto.setTitle(rs.getString(4));
					bdto.setPassword(rs.getString(5));
					bdto.setReg_date(sdf.format(rs.getDate(6)));
					bdto.setRef(rs.getInt(7));
					bdto.setRe_step(rs.getInt(8));
					bdto.setRe_level(rs.getInt(9));
					bdto.setRead_count(rs.getInt(10));
					bdto.setContent(rs.getString(11));
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
				if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
				if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
			}
			
			return bdto;
			
		}
		
		//게시글 수정
		public boolean updateBoard(BoardDTO bdto) {
			
			boolean isUpdate = false;
			
			try {
				
				if (checkPasswd(bdto.getNum(), bdto.getPassword())) {
					
					conn = getConnection();
					
					pstmt = conn.prepareStatement("update board set title=?, content=? where num=?");
					pstmt.setString(1, bdto.getTitle());
					pstmt.setString(2, bdto.getContent());
					pstmt.setInt(3, bdto.getNum());
					
					pstmt.executeUpdate();
					
					isUpdate = true;
					
					System.out.println("게시글이 수정되었습니다.");
					System.out.println(bdto.getNum() + "/" + bdto.getTitle() + "/" + bdto.getContent());
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
				if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
				if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
			}
			
			return isUpdate;
			
		}
		
		//게시글 삭제
		public boolean deleteBaord(BoardDTO bdto) {
			
			boolean isDelete = false;
			
			try {
				
				if (checkPasswd(bdto.getNum(), bdto.getPassword())) {
					
					conn = getConnection();
					
					pstmt = conn.prepareStatement("delete from board where num=?");
					pstmt.setInt(1, bdto.getNum());
					
					pstmt.executeUpdate();
					
					isDelete = true;
					
					System.out.println("게시글이 삭제되었습니다.");
					System.out.println(bdto.getNum() + "/" + bdto.getWriter() + "/" + bdto.getTitle());
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
				if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
				if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
			}
			
			return isDelete;
			
		}
		
		//답변글 쓰기(새 게시글 ref=원본 ref / re_step=원본 re_step+1 / re_level=원본 re_level+1)
		public boolean reWriteBoard(BoardDTO bdto) {
			
			boolean isReWrite = false;
			
			try {
				
				conn = getConnection();
				
				int ref;
				
				String ref_sql = "select max(ref) from board";
				
				pstmt = conn.prepareStatement(ref_sql);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					ref = rs.getInt(1);
				} else {
					ref = 1;
				}
				
				String sql = "insert into board (writer, email, title, password, reg_date, ref, re_step, re_level, read_count, content)"
						+ " values(?, ?, ?, ?, now(), ?, ?, ?, 0, ?)";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bdto.getWriter());
				pstmt.setString(2, bdto.getEmail());
				pstmt.setString(3, bdto.getTitle());
				pstmt.setString(4, bdto.getPassword());
				pstmt.setInt(5, bdto.getRef());
				pstmt.setInt(6, bdto.getRe_step()+1);
				pstmt.setInt(7, bdto.getRe_level()+1);
				pstmt.setString(8, bdto.getContent());
				
				pstmt.executeUpdate();
				
				isReWrite = true;
				
				System.out.println("새 게시글이 등록되었습니다.");
				System.out.println(bdto.getTitle() + "/" + bdto.getWriter());
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
				if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
				if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
			}
			
			return isReWrite;
			
		}

}
