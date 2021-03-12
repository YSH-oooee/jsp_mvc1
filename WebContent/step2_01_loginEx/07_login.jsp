<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginPage</title>
</head>
<body>

	<h2>Login</h2>
	
	<form action="08_loginPro.jsp" method="post">
		<fieldset>
			<p><label for="id">ID : </label>
				<input type="text" id="id" name="id"></p>
				
			<p><label for="passwd">Password : </label>
				<input type="password" id="passwd" name="passwd"></p>
			
			<input type="submit" value="Login">
		</fieldset>
	</form>

</body>
</html>