<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정</title>
</head>
<body>

	<%
		String id = (String)session.getAttribute("id");
	%>
	
	<h2>Modify Form</h2>
	
	<form action="06_updatePro.jsp" method="post">
		<fieldset>
			<p>
				<label for="id">ID : </label>
				<input type="text" id="id" name="id" value="<%= id %>" readonly="readonly">
			</p>
			<p>
				<label for="passwd">Password : </label>
				<input type="password" id="passwd" name="passwd">
			</p>
			<p>
				<label for="name">Name : </label>
				<input type="text" id="name" name="name">
			</p>
			<input type="submit" value="update">
		</fieldset>
	</form>

</body>
</html>