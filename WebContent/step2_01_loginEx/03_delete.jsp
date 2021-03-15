<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
</head>
<body>

	<%
		//로그인 할 때, 세션에 등록된 id를 불러옴
		String id = (String)session.getAttribute("id");
	%>
	
	<h2>Delete Member '<%= id %>'</h2>
	
	<form action="04_deletePro.jsp" method="post">
		<fieldset>
			<p>
				<label for="id">ID : </label>
				<input type="text" id="id" name="id" value="<%= id %>" readonly="readonly">
			</p>
			<p>
				<label for="passwd">Password : </label>
				<input type="password" id="passwd" name="passwd">
			</p>
			<input type="submit" value="leave">
			<input type="reset" value="reset">
		</fieldset>
	</form>

</body>
</html>