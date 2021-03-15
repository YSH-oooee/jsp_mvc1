<%@page import="step2_00_loginEx.MemberDAO"%>
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
		
		//form에서 입력한 id와 passwd가 DB에 등록된 정보가 맞는지 확인
		boolean isLeaveMember = MemberDAO.getInstance().leaveMember(id, passwd);
		
		//DB와 정보가 일치한다면, 회원 정보 삭제
		if(isLeaveMember) {
			session.invalidate();
	%>
		<script type="text/javascript">
			alert("Your account has been deleted successfully.");
			location.href="00_main.jsp";			
		</script>
	<%	//DB와 정보가 일치하지 않는다면, 메세지창+뒤로가기
		} else {
	%>
		<script type="text/javascript">
			alert("Check your ID or Password.");
			history.go(-1);
		</script>
	<%
		}
	%>

</body>
</html>