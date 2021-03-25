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
			//.cj >> 신버전
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(dbUrl, dbId, dbPass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}

	//insert
	public boolean insertMember(MemberDTO mdto) {
		
		boolean isFirstMember = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from member where id=?");
			pstmt.setString(1, mdto.getId());
			
			rs = pstmt.executeQuery();
			
			//��ȸ�� id�� ���ٸ�, ���� ����
			if (!rs.next()) {
				
				pstmt = conn.prepareStatement("insert into member values(?, ?, ?, now())");
				pstmt.setString(1, mdto.getId());
				pstmt.setString(2, mdto.getPasswd());
				pstmt.setString(3, mdto.getName());
				
				pstmt.executeUpdate();
				
				isFirstMember = true;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (Exception e2) { e2.printStackTrace(); } }
		}
		
		return isFirstMember;
		
	}
	
	//leave(delete)
	public boolean leaveMember(String id, String passwd) {
		
		boolean isLeaveMember = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from member where id=? and passwd=?");
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				pstmt = conn.prepareStatement("delete from member where id=?");
				pstmt.setString(1, id);
				
				pstmt.executeUpdate();
				
				isLeaveMember = true;
				
				System.out.println("Member ���̺��� ������ �����Ǿ����ϴ�.");
				System.out.println(id + " / " + passwd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (Exception e2) { e2.printStackTrace(); } }
		}
		
		return isLeaveMember;
		
	}
	
	//update
	public boolean updateMember(MemberDTO mdto) {
		
		boolean isUpdateMember = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("select * from member where id=? and passwd=?");
			pstmt.setString(1, mdto.getId());
			pstmt.setString(2, mdto.getPasswd());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				pstmt = conn.prepareStatement("update member set name=? where id=?");
				pstmt.setString(1, mdto.getName());
				pstmt.setString(2, mdto.getId());
				
				pstmt.executeUpdate();
				
				isUpdateMember = true;
				
				System.out.println("Member ���̺��� �����Ǿ����ϴ�.");
				System.out.println(mdto.getId() + " / " + mdto.getPasswd() + " / " + mdto.getName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (Exception e2) { e2.printStackTrace(); } }
		}
		
		return isUpdateMember;
		
	}
	
	//login DAO
	public boolean loginMember(String id, String passwd) {
		
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
			if (rs != null) { try { rs.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (pstmt != null) { try { pstmt.close(); } catch (Exception e2) { e2.printStackTrace(); } }
			if (conn != null) { try { conn.close(); } catch (Exception e2) { e2.printStackTrace(); } }
		}
		
		return isValidMember;
		
	}
	
}
