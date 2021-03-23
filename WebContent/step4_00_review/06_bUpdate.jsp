<%@page import="step4_00_board.BoardDAO"%>
<%@page import="step4_00_board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>

	<%
		int num = Integer.parseInt(request.getParameter("num"));
	
		BoardDTO bdto = BoardDAO.getInstance().getUpdateBaord(num);
	%>

	<div align="center">
		<br>
		
		<h2>게시글 수정</h2>
	
		<form action="07_bUpdatePro.jsp" method="post">
			<table border="1" width="700">
				<tr>
					<td width="150" align="center">작성자</td>
					<td><%= bdto.getWriter() %></td>
				</tr>
				
				<tr>
					<td width="150" align="center">작성일</td>
					<td><%= bdto.getReg_date() %></td>
				</tr>
				
				<tr>
					<td width="150" align="center">제목</td>
					<td><input type="text" name="title" value="<%= bdto.getTitle() %>"></td>
				</tr>
				
				<tr>
					<td width="150" align="center">비밀번호</td>
					<td><input type="password" name="password"></td>
				</tr>
				
				<tr>
					<td width="150" align="center">글내용</td>
					<td><textarea rows="10" cols="50" name="content"><%= bdto.getContent() %></textarea></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="hidden" name="num" value="<%= bdto.getNum() %>">
						<input type="submit" value="글수정">
						<input type="button" value="목록보기" onclick="location.href='04_bList.jsp'">
					</td>
				</tr>
			</table>
		</form>
	
	</div>

</body>
</html>