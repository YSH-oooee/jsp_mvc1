<%@page import="step4_00_board.BoardDAO"%>
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
	%>
	
	<jsp:useBean id="boardDTO" class="step4_00_board.BoardDTO">
		<jsp:setProperty name="boardDTO" property="*" />
	</jsp:useBean>
	
	<%
		boolean isWrite = BoardDAO.getInstance().writeBoard(boardDTO);
	
		if(isWrite) {
	%>
		<script type="text/javascript">
			alert("게시글이 등록되었습니다.");
			location.href='04_bList.jsp';
		</script>
	<%
		}
	%>

</body>
</html>