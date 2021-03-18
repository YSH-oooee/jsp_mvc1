package step3_00_boardEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BoardDAO {
	
	private BoardDAO() {}
	private static BoardDAO instnace = new BoardDAO();
	public static BoardDAO getInstance() {
		return instnace;
	}
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
	public Connection getConnection() {
		
		String dbUrl = "jdbc:mysql://localhost:3306/STEP3_BOARD_EX?serverTimezone=UTC";
		String dbId = "root";
		String dbPass = "root";
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");			
			conn = DriverManager.getConnection(dbUrl, dbId, dbPass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	//게시글 등록
	public void insertBoard(BoardDTO bdto) {
		
		try {
			
			conn = getConnection();
			
			String sql = "insert into board (writer, email, subject, password, reg_date, read_count, content)";
				   sql += " values(?, ?, ?, ?, now(), 0, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bdto.getWriter());
			pstmt.setString(2, bdto.getEmail());
			pstmt.setString(3, bdto.getSubject());
			pstmt.setString(4, bdto.getPassword());
			pstmt.setString(5, bdto.getContent());
			
			pstmt.executeUpdate();
			
			System.out.println("게시글이 추가 되었습니다.");
			System.out.println(bdto.getSubject() + " / " + bdto.getWriter());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
		}
		
	}
	
	//게시글 목록 조회
	public ArrayList<BoardDTO> getAllBoard() {
		
		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from board");
			
			rs = pstmt.executeQuery();
			
			BoardDTO bdto;
			
			while (rs.next()) {
				
				bdto = new BoardDTO();
				
				bdto.setNum(rs.getInt(1));						// rs.getInt("num");
				bdto.setWriter(rs.getString(2));				// rs.getString("writer");
				bdto.setEmail(rs.getString(3));					// rs.getString("email");
				bdto.setSubject(rs.getString(4));				// rs.getSrting("subject");
				bdto.setPassword(rs.getString(5));				// rs.getString("password");
				bdto.setReg_date(sdf.format(rs.getDate(6)));	// sdf.format(rs.getDate("reg_date"));
				bdto.setRead_count(rs.getInt(7));				// rs.getInt("read_count");
				bdto.setContent(rs.getString(8));				// rs.getString("content");
				
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
	
	//하나의 게시글 조회
	public BoardDTO getOneBoard(int bNum) {
		
		BoardDTO bdto = new BoardDTO();
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("update board set read_count = read_count+1 where num=?");
			pstmt.setInt(1, bNum);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("select * from board where num=?");
			pstmt.setInt(1, bNum);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				bdto.setNum(rs.getInt(1));
				bdto.setWriter(rs.getString(2));
				bdto.setEmail(rs.getString(3));
				bdto.setSubject(rs.getString(4));
				bdto.setReg_date(sdf.format(rs.getDate(6)));
				bdto.setRead_count(rs.getInt(7));
				bdto.setContent(rs.getString(8));
				
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
	public BoardDTO getOneUpdate(int num) {
		
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
				bdto.setSubject(rs.getString(4));
				bdto.setReg_date(sdf.format(rs.getDate(6)));
				bdto.setRead_count(rs.getInt(7));
				bdto.setContent(rs.getString(8));
				
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
	
	public boolean updateBoard(BoardDTO bdto) {
		
		boolean isUpdate = false;
		
		return isUpdate;
		
	}

}
