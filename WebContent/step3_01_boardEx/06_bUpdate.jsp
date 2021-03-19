<%@page import="step3_00_boardEx.BoardDAO"%>
<%@page import="step3_00_boardEx.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update</title>
</head>
<body>

	<%
		request.setCharacterEncoding("utf-8");
	
		int num = Integer.parseInt(request.getParameter("num"));
		
		BoardDTO bdto = BoardDAO.getInstance().getOneUpdateBaord(num);
	%>
	
	<h1>게시글 수정하기</h1>
	
	<form action="07_bUpdatePro.jsp" method="post">
		<table border="1">
			<tr>
				<td>글번호</td>
				<td><%= bdto.getNum() %></td>
			</tr>
			
			<tr>
				<td>조회수</td>
				<td><%= bdto.getRead_count() %></td>
			</tr>
			
			<tr>
				<td>작성자</td>
				<td><%= bdto.getWriter() %></td>
			</tr>
			
			<tr>
				<td>작성일</td>
				<td><%= bdto.getReg_date() %></td>
			</tr>
			
			<tr>
				<td>이메일</td>
				<td><%= bdto.getEmail() %></td>
			</tr>
			
			<tr>
				<td>제목</td>
				<td><input type="text" name="subject" value="<%= bdto.getSubject() %>"></td>
			</tr>
			
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
			
			<tr>
				<td>글 내용</td>
				<td><textarea rows="10" cols="50" name="content"><%= bdto.getContent() %></textarea></td>
			</tr>
		</table>
		
		<p>
			<input type="hidden" name="num" value="<%= bdto.getNum() %>">
			<input type="hidden" name="writer" value="<%= bdto.getWriter() %>">
			<input type="submit" value="수정하기">
			<input type="button" value="목록으로" onclick="location.href='04_bList.jsp'">
		</p>
	</form>
	
</body>
</html>