<%@page import="step4_00_board.BoardDAO"%>
<%@page import="step4_00_board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		request.setCharacterEncoding("utf-8");
	
		int ref = Integer.parseInt(request.getParameter("ref"));
		int re_step = Integer.parseInt(request.getParameter("re_step"));
		int re_level = Integer.parseInt(request.getParameter("re_level"));
	%>
	
	<jsp:useBean id="boardDTO" class="step4_00_board.BoardDTO">
		<jsp:setProperty name="boardDTO" property="*" />
	</jsp:useBean>
	
	<%
		boardDTO.setRef(ref);
		boardDTO.setRe_step(re_step);
		boardDTO.setRe_level(re_level);
		
		boolean isReWrite = BoardDAO.getInstance().reWriteBoard(boardDTO);
		
		if(isReWrite) {
	%>
		<script type="text/javascript">
			alert("답변글이 게시되었습니다.");
			location.href="04_bList.jsp";
		</script>
	<%
		} else {
	%>
		<script type="text/javascript">
			alert("답변글 게시에 실패했습니다.");
			history.go(-1);
		</script>
	<%
		}
	%>

</body>
</html>