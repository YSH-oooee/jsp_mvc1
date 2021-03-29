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

<script>
	function showList() {
		
		var onePageViewCount = document.getElementById("onePageViewCount").value;
		var searchKeyword = document.getElementById("searchKeyword").value;
		var searchWord = document.getElementById("searchWord").value;
		
		location.href="04_bList.jsp?searchKeyword=" + searchKeyword + "&searchWord=" + searchWord + "&onePageViewCount=" + onePageViewCount;
		
	}
</script>

<body>

	<!-- 우측 상단에 더미파일 생성 버튼 -->
	<div align="right">
		<input type="button" value="테스트 데이터 생성" onclick="location.href='99_dummyCreate.jsp'">
	</div>
	
	<!-- 게시글 목록 생성 -->
	<%
		request.setCharacterEncoding("utf-8");	
		
		//검색분류
		String searchKeyword = request.getParameter("searchKeyword");
		if (searchKeyword == null) { searchKeyword = "all"; }
		
		//검색어
		String searchWord = request.getParameter("searchWord");
		if (searchWord == null) { searchWord = ""; }
		
		//페이지 당 게시글 수
		String tempCnt = request.getParameter("onePageViewCount");		
		if (tempCnt == null) { tempCnt = "10"; }	
		int onePageViewCount = Integer.parseInt(tempCnt);
		
		//현재 페이지
		String tempPageNum  = request.getParameter("currentPageNumber");
		if (tempPageNum == null){ tempPageNum = "1"; }
		int currentPageNumber = Integer.parseInt(tempPageNum); 
		
		//전체 게시글 수
		int totalBoardCount = BoardDAO.getInstance().getAllBaordCount(searchKeyword, searchWord);
		
		//각 페이지의 시작 글번호
		int startBoardIdx = (currentPageNumber -1) * onePageViewCount;
		
		//전체 게시글 불러오기
		ArrayList<BoardDTO> boardList = BoardDAO.getInstance().getAllBoard(searchKeyword, searchWord, startBoardIdx, onePageViewCount);
	%>

	<div align="center">
	
		<br>
		<h2>전체 게시글 보기</h2>
		
		<table border="1" width="800">
			<tr>
				<td align="center" width="100">총 게시글 : <%= totalBoardCount %>개</td>
				<td colspan="4" align="right">
					<select name=onePageViewCount>
						<option <% if(onePageViewCount == 5) {%>selected<%} %>value="5">5개씩 보기</option>
						<option <% if(onePageViewCount == 7) {%>selected<%} %>value="7">7개씩 보기</option>
						<option <% if(onePageViewCount == 10) {%>selected<%} %>value="10">10개씩 보기</option>
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
		for(int i=0; i < boardList.size(); i++) {
			BoardDTO bdto = boardList.get(i);
	%>
			<tr>
				<td align="center"><%= bdto.getNum() %></td>
				<td>
	<%
			if(bdto.getRe_step() > 1) {
				for(int j=0; j < (bdto.getRe_level() - 1) * 3; j++) {
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
					<select name="searchKeyword">
						<option <% if(searchKeyword.equals("all")) {%>selected<%} %>value="all">전체검색</option>
						<option <% if(searchKeyword.equals("writer")) {%>selected<%} %>value="writer">작성자</option>
						<option <% if(searchKeyword.equals("title")) {%>selected<%} %>value="title">제목</option>
					</select>
					
					<input type="text" name="searchWord" value="<%= searchWord %>">
					<input type="button" value="검색" onclick="showList()">
				</td>
			</tr>
		</table>
	
	</div>
	<!-- 페이징 -->
	<div align="center">	
	<%	//게시글이 존재한다면
		if(totalBoardCount > 0) {
			
			//전체 페이지 수 = 전체 게시글 수 / 페이지 당 보여지는 게시글 수
			int less_count = totalBoardCount % onePageViewCount == 0 ? 0 : 1;		//게시글이 남는다면
			int page_count = totalBoardCount / onePageViewCount + less_count;		//전체 페이지 수 +1
			
			//시작 페이지
			int startPage = 1;		
			
			if(currentPageNumber % 10 == 0) {
				startPage = (currentPageNumber / 10 - 1) * 10 + 1;
			} else {
				startPage = (currentPageNumber / 10) * 10 + 1;
			}
			
			//마지막 페이지
			int endPage = startPage + 9;
			
			if(endPage > page_count) {
				endPage = page_count;
			}
			
			//전체 게시글 개수가 페이지 당 게시글 개수보다 적을 때
			if(onePageViewCount > totalBoardCount) {
				startPage = 1;
				endPage = 0;
			}
			
			//페이지 출력
			if(startPage > 10) {
	%>
				<a href="04_bList.jsp?currentPageNumber=<%=startPage-10%>&onePageViewCount=<%=onePageViewCount%>&searchKeyword=<%=searchKeyword%>&searchWord=<%=searchWord%>">[이전]</a>
	<%
			}
			
			for(int i = startPage; i <= endPage; i++) {
	%>
				<a href="04_bList.jsp?currentPageNumber=<%=i%>&onePageViewCount=<%=onePageViewCount%>&searchKeyword=<%=searchKeyword%>&searchWord=<%=searchWord%>"> [<%=i %>] </a>
	<%
			}
			
			if(endPage <= page_count && endPage >= 10) {
	%>
				<a href="04_bList.jsp?currentPageNumber=<%=startPage+10%>&onePageViewCount=<%=onePageViewCount%>&searchKeyword=<%=searchKeyword%>&searchWord=<%=searchWord%>">[다음]</a>
	<%
			}
			
		}
	%>	
	</div>

</body>
</html>