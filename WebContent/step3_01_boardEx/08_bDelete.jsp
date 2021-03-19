<%@page import="step3_00_boardEx.BoardDTO"%>
<%@page import="step3_00_boardEx.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delete</title>
</head>
<body>

	<%
		int num = Integer.parseInt(request.getParameter("num"));
	
		BoardDTO bdto = BoardDAO.getInstance().getOneUpdateBaord(num);
	%>

	<h1>게시글 삭제</h1>
	
	<form action="09_bDeletePro.jsp" method="post">	
		<table border="1">
			<tr>
				<td>작성자</td>
				<td><%= bdto.getWriter() %></td>
			</tr>
			
			<tr>
				<td>작성일</td>
				<td><%= bdto.getReg_date() %></td>
			</tr>
			
			<tr>
				<td>제목</td>
				<td><%= bdto.getSubject() %></td>
			</tr>
			
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
		</table>
		
		<p>
			<input type="hidden" name="num" value="<%= bdto.getNum() %>">
			<input type="hidden" name="writer" value="<%= bdto.getWriter() %>">
			<input type="hidden" name="subject" value="<%= bdto.getSubject() %>">
			<input type="submit" value="삭제하기">
			<input type="button" value="목록보기" onclick="location.href='04_bList.jsp'">
		</p>	
	</form>

</body>
</html>