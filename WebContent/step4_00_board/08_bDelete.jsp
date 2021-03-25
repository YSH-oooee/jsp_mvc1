<%@page import="step4_00_board.BoardDAO"%>
<%@page import="step4_00_board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제</title>
</head>
<body>

	<%
		int num = Integer.parseInt(request.getParameter("num"));
		
		BoardDTO bdto = BoardDAO.getInstance().getOneBaord(num);
	%>

	<div align="center">
		<br>
		
		<h2>게시글 수정</h2>
	
		<form action="09_bDeletePro.jsp" method="post">
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
					<td><%= bdto.getTitle() %></td>
				</tr>
				
				<tr>
					<td width="150" align="center">비밀번호</td>
					<td><input type="password" name="password"></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="hidden" name="num" value="<%= bdto.getNum() %>">
						<input type="hidden" name="writer" value="<%= bdto.getWriter() %>">
						<input type="hidden" name="title" value="<%= bdto.getTitle() %>">
						<input type="submit" value="글삭제">
						<input type="button" value="목록보기" onclick="location.href='04_bList.jsp'">
					</td>
				</tr>
			</table>
		</form>
	
	</div>

</body>
</html>