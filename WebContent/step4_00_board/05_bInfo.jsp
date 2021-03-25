<%@page import="step4_00_board.BoardDAO"%>
<%@page import="step4_00_board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
</head>
<body>

	<%
		int num = Integer.parseInt(request.getParameter("num"));
	
		BoardDTO bdto = BoardDAO.getInstance().getOneBaord(num);
	%>

	<div align="center">
		<br>
		
		<h2>게시글 보기</h2>
	
		<table border="1" width="700">
				<tr align="center">
					<td width="150">글번호</td>
					<td><%= bdto.getNum() %></td>
				</tr>
				
				<tr align="center">
					<td width="150">조회수</td>
					<td><%= bdto.getRead_count() %></td>
				</tr>
				
				<tr align="center">
					<td width="150">작성자</td>
					<td><%= bdto.getWriter() %></td>
				</tr>
				
				<tr align="center">
					<td width="150">작성일</td>
					<td><%= bdto.getReg_date() %></td>
				</tr>
				
				<tr align="center">
					<td width="150">이메일</td>
					<td><%= bdto.getEmail() %></td>
				</tr>
				
				<tr align="center">
					<td width="150">제목</td>
					<td><%= bdto.getTitle() %></td>
				</tr>
				
				<tr align="center">
					<td width="150">글 내용</td>
					<td><%= bdto.getContent() %></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="button" value="답글쓰기" onclick="location.href='10_bReWrite.jsp?num=<%=bdto.getNum()%>'">
						<input type="button" value="수정하기" onclick="location.href='06_bUpdate.jsp?num=<%=bdto.getNum()%>'">
						<input type="button" value="삭제하기" onclick="location.href='08_bDelete.jsp?num=<%=bdto.getNum()%>'">
						<input type="button" value="목록보기" onclick="location.href='04_bList.jsp'">
					</td>
				</tr>
			</table>
	
	</div>

</body>
</html>