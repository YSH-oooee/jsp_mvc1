<%@page import="step2_00_loginEx.MemberDTO"%>
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
		String name = request.getParameter("name");
		
		boolean isUpdateMember = MemberDAO.getInstance().updateMember(new MemberDTO(id, passwd, name));
		
		if(isUpdateMember) {
	%>
		<script type="text/javascript">
			alert("Update Success!");
			location.href="00_main.jsp";
		</script>
	<%
		} else {
	%>
		<script type="text/javascript">
			alert("Check your ID or Password!");
			history.go(-1);
		</script>
	<%
		}
	%>

</body>
</html>