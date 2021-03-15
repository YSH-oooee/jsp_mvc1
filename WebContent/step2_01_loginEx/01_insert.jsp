<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>

	<h2>Join Form</h2>
	
	<form action="02_insertPro.jsp" method="post">
		<fieldset>
			<p>
				<label for="id">ID : </label>
				<input type="text" id="id" name="id" autofocus="autofocus">
			</p>
			<p>
				<label for="pwd">Password : </label>
				<input type="password" id="passwd" name="passwd">
			</p>
			<p>
				<label for="confirmPwd">ConfirmPassword : </label>
				<input type="password" id="confirmPasswd" name="confirmPasswd">
			</p>
			<p>
				<label for="name">Name : </label>
				<input type="text" id="name" name="name">
			</p>
			<input type="submit" value="Join">
			<input type="reset" value="reset">
		</fieldset>
	</form>
	
</body>
</html>