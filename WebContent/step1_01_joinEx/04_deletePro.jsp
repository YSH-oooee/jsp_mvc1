<%@page import="java.sql.ResultSet"%>
<%@page import="org.apache.catalina.startup.PasswdUserDatabase"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<% 		
		request.setCharacterEncoding("utf-8");
	
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			String jdbcUrl = "jdbc:mysql://localhost:3306/login_ex?serverTimezone=UTC";
			String dbId    = "root";
			String dbPass  = "root";
			
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			pstmt = conn.prepareStatement("select id, passwd from member where id=? and passwd=?");
			
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			
			rs = pstmt.executeQuery();
			
			//반환된 결과값이 있다면
			if(rs.next()) {
				
				String sql = "delete from member where id=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
				pstmt.executeUpdate();
	%>
			<script>
				alert("회원탈퇴 되었습니다.");
				location.href="00_main.jsp";
			</script>
	<% 	
			} else {
	%>
			<script type="text/javascript">
				alert("아이디와 비밀번호를 확인하세요.");
				history.go(-1);
			</script>
	<%
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null)  try {rs.close();} catch (Exception e){}
			if(pstmt != null) try {pstmt.close();} catch (Exception e){}
			if(conn != null)  try {conn.close();} catch (Exception e){}
		}
	
	%>

</body>
</html>