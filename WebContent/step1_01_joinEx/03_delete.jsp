<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>회원 탈퇴</h2>
	
	<form action="04_deletePro.jsp" method="post">
		아이디 : <input type="text" name="id"><br>
		패스워드 : <input type="password" name="passwd"><br>
		<input type="submit" value="회원탈퇴">
		<input type="reset" value="다시입력">
	</form>

</body>
</html>