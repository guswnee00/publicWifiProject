<%-- 삭제될 북마크 내용이 전송된 jsp --%>
<%@ page import="function.BookmarkGroup"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>북마크 그룹 삭제</title>
</head>

<body>
    <h1>북마크 그룹 삭제</h1>
    <%@include file="links.jsp"%>

    <%
        BookmarkGroup bookmarkGroup = new BookmarkGroup();

        request.setCharacterEncoding("UTF-8");

        String bookmarkId = request.getParameter("bookmarkId");

        int deleteData = bookmarkGroup.getDeleteBookmark(Integer.parseInt(bookmarkId));
    %>
    <script>
        <%
            String delete = deleteData > 0 ? "북마크 그룹 정보를 삭제하였습니다." : "북마크 그룹 정보 삭제를 실패했습니다.";
        %>
        alert("<%=delete%>");
        location.href = "bookmarkGroup.jsp";
    </script>
</body>
</html>

