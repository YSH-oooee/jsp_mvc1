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
		
		boolean isValidMember = MemberDAO.getInstance().loginMember(id, passwd);
		
		if(isValidMember) {
			
			session.setAttribute("id", id);
			session.setMaxInactiveInterval(60 * 10);	// 로그인 상태 유지 10분으로 설정
			
			response.sendRedirect("00_main.jsp");		//메인 페이지(해당경로)로 이동
			
		} else {
	%>
		<script type="text/javascript">
			alert("check your Id and password");
			history.go(-1);
		</script>
	<%
		}
		
	%>

</body>
</html>

<!-- 
	07_login.jsp → 08_loginPro.jsp → MemberDAO.loginMember() → 08_loginPro.jsp → login!

	07_login.jsp에서 입력받은 정보를 08_loginPro.jsp에서 전송 받아 MemberDAO의 로그인메소드를 호출하여 
	DB의 회원 정보와 비교, 결과값을 반환받아 로그인 여부를 판단한다 (true : 로그인 성공/false : 실패)
 -->