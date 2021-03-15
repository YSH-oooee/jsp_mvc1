<%@page import="step2_00_loginEx.MemberDAO"%>
<%@page import="step2_00_loginEx.MemberDTO"%>
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
		String conrifmPasswd = request.getParameter("confirmPasswd");
		String name = request.getParameter("name");
		
		//인자를 낱개로 보내는 것이 아니라, 묶어서 한번에 보냄
		boolean isFirstMember = MemberDAO.getInstance().insertMember(new MemberDTO(id, passwd, name));
		
		if(isFirstMember) {
	%>
		<script type="text/javascript">
			alert("Congraturation! You are a member.");
			location.href="00_main.jsp";
		</script>
	<%
		} else {
	%>
		<script type="text/javascript">
			alert("This is a duplication ID");
			history.go(-1);
		</script>
	<%
		}
		
	%>

</body>
</html>