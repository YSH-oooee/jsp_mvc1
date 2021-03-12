package step2_00_loginEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {
	
	//singleTon 패턴
	private MemberDAO() {}
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public Connection getConnection() {
		
		String dbUrl = "jdbc:mysql://localhost:3306/login_ex?serverTimezone=UTC";
		String dbId = "root";
		String dbPass = "root";
		
		try {
			//.cj 없는게 최신버전
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(dbUrl, dbId, dbPass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	//login DAO
	public boolean login(String id, String passwd) {
		
		boolean isValidMember = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from member where id=? and passwd=?");
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				isValidMember = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return isValidMember;
		
	}

}
