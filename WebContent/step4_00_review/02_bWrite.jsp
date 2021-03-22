<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 쓰기</title>
</head>
<body>

	<div align="center">
	<br>
	<br>
	<br>
	<br>
	<br>
		<!-- 작성자, 제목, 이메일, 비밀번호 반드시 입력 -->
		<h2>게시글 쓰기</h2>
	
		<form action="03_bWritePro.jsp" method="post">
			<table border="1" width="700">
				<tr>
					<td width="150" align="center">작성자</td>
					<td><input type="text" name="writer"></td>
				</tr>
				
				<tr>
					<td width="150" align="center">제목</td>
					<td><input type="text" name="title"></td>
				</tr>
				
				<tr>
					<td width="150" align="center">이메일</td>
					<td><input type="email" name="email"></td>
				</tr>
				
				<tr>
					<td width="150" align="center">비밀번호</td>
					<td><input type="password" name="password"></td>
				</tr>
				
				<tr>
					<td width="150" align="center">글내용</td>
					<td><textarea rows="10" cols="50" name="content"></textarea></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="글쓰기">
						<input type="reset" value="다시작성">
						<input type="button" value="전체게시글보기" onclick="location.href='04_bList.jsp'">
					</td>
				</tr>
			</table>
		</form>
	
	</div>

</body>
</html>