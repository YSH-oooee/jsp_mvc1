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
		request.setCharacterEncoding("utf-8");
		
		//검색분류
		String searchOP = request.getParameter("searchOP");
		if(searchOP == null) { searchOP = "all"; }
		
		//검색어
		String searchWord = request.getParameter("searchWord");
		if(searchWord == null) { searchWord = ""; }
		
		//페이지 당 게시글 수
		String temp_count = request.getParameter("view_count");
		if(temp_count == null) { temp_count = "10"; }
		int view_count = Integer.parseInt(temp_count);
		
		//현재 페이지
		String temp_page = request.getParameter("cur_page");
		if(temp_page == null) { temp_count = "1"; }
		int cur_page = Integer.parseInt(temp_page);
		
		//전체 게시글 개수
		int total_count = BoardDAO.getInstance().getAllBaordCount(searchOP, searchWord);
		
		//각 페이지의 시작 글번호
		int startNum = (cur_page - 1) * view_count;
		
		//전체 게시글 불러오기
		ArrayList<BoardDTO> boardList = BoardDAO.getInstance().getAllBoard(searchOP, searchWord, startNum, view_count);
	%>

	<div align="right">
		<input type="button" value="테스트 데이터 생성" onclick="location.href='99_dummyCreate.jsp'">
	</div>
	
	<div align="center">
	
		<br>
		<h2>전체 게시글 보기</h2>
		
		<table border="1" width="800">
			<tr>
				<td align="center" width="100">총 게시글 : <%= total_count %>개</td>
				<td colspan="4" align="right">
					<select name=view_count>
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
				└&nbsp;
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
	<!-- 페이징 -->
	<div align="center">	
	<%
		if(total_count > 0) {
			
			int less_count = total_count % view_count == 0 ? 0 : 1;
			int page_count = total_count / view_count + less_count;
			
			//시작 페이지
			int startPage = 1;			
			if(cur_page % 10 == 0) {
				startPage = (cur_page /10 - 1) * 10 + 1;
			} else {
				startPage = (cur_page / 10) * 10 + 1;
			}
			
			//마지막 페이지
			int endPage = startPage + 9;
			if(endPage > page_count) {
				endPage = page_count;
			}
			
			//전체 게시글 개수가 페이지 당 게시글 개수보다 적을 때
			if(view_count > total_count) {
				startPage = 1;
				endPage = 0;
			}
			
			//페이지 출력
			if(startPage > 10) {
	%>
				<a href="04_bList.jsp?cur_page=<%=startPage-10%>&view_count=<%=view_count%>&searchOP=<%=searchOP%>&searchWord=<%=searchWord%>">[이전]</a>
	<%
			}
			
			for(int i = startPage; i <= endPage; i++) {
	%>
				<a href="04_bList.jsp?cur_page=<%=i%>&view_count=<%=view_count%>&searchOP=<%=searchOP%>&searchWord=<%=searchWord%>"> [<%=i %>] </a>
	<%
			}
			
			if(endPage == page_count && endPage > 10) {
	%>
				<a href="04_bList.jsp?cur_page=<%=startPage+10%>&view_count=<%=view_count%>&searchOP=<%=searchOP%>&searchWord=<%=searchWord%>">[다음]</a>
	<%
			}
			
		}
	%>	
	</div>

</body>
</html>