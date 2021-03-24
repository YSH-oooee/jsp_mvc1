<%@page import="step4_00_board.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="step4_00_board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
</head>
<body>

	<%
		//전체 게시글 개수
		int count = BoardDAO.getInstance().getAllBaordCount();
	
		//전체 게시글 불러오기
		ArrayList<BoardDTO> boardList = BoardDAO.getInstance().getAllBoard();
	%>

	<div align="right">
		<input type="button" value="테스트 데이터 생성" onclick="location.href='99_dummyCreate.jsp?num=10'">
	</div>
	
	<div align="center">
	
		<br>
		<h2>전체 게시글 보기</h2>
		
		<table border="1" width="800">
			<tr>
				<td align="center" width="100">총 게시글 : <%= count %>개</td>
				<td colspan="4" align="right">
					<select name=board_count>
						<option value="5">5개씩 보기</option>
						<option value="7">7개씩 보기</option>
						<option value="10" selected>10개씩 보기</option>
					</select>
				</td>
			</tr>
			
			<tr align="center">
				<td width="100">번호</td>
				<td width="450">제목</td>
				<td width="100">작성자</td>
				<td width="100">작성일</td>
				<td width="50">조회수</td>
			</tr>
	<%
		for(int i = 0; i < boardList.size(); i++) {
			BoardDTO bdto = boardList.get(i);
	%>
			<tr>
				<td align="center"><%= bdto.getNum() %></td>
				<td>
	<%
			if(bdto.getRe_level() > 1) {
				for(int j = 0; j < (bdto.getRe_level() - 1) * 3; j++) {
	%>
					&nbsp;
	<%
				}
	%>
				└ &nbsp;
	<%
			}
	%>
					<a href="05_bInfo.jsp?num=<%=bdto.getNum()%>"><%= bdto.getTitle() %></a>
				</td>
				<td align="center"><%= bdto.getWriter() %></td>
				<td align="center"><%= bdto.getReg_date() %></td>
				<td align="center"><%= bdto.getRead_count() %></td>
			</tr>
	<%
		}
	%>
			<tr>
				<td colspan="5" align="right">
					<input type="button" value="글쓰기" onclick="location.href='02_bWrite.jsp'">
				</td>
			</tr>
			
			<tr>
				<td colspan="5" align="center">
					<select name="searchOP">
						<option value="all" selected>전체검색</option>
						<option value="writer">작성자</option>
						<option value="title">제목</option>
					</select>
					
					<input type="text" name="searchWord">
					<input type="submit" value="검색">
				</td>
			</tr>
		</table>
	
	</div>

</body>
</html>