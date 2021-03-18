package step3_00_boardEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			if (rs != null) { try { rs.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (Exception e2) { e2.printStackTrace(); } }
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
				
				bdto.setNum(rs.getInt(1));
				bdto.setWriter(rs.getString(2));
				bdto.setEmail(rs.getString(3));
				bdto.setSubject(rs.getString(4));
				
				boardList.add(bdto);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (Exception e2) { e2.printStackTrace(); } }
		}
		
		return boardList;
		
	}

}
