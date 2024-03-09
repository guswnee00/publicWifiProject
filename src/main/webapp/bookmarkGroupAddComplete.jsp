<%-- 추가된 북마크 내용이 전송된 jsp --%>
<%@ page import="function.BookmarkGroup"%>
<%@ page import="data.BookmarkGroupData"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>북마크 그룹 추가</title>
</head>

<body>
    <h1>북마크 그룹 추가</h1>
    <%@include file="links.jsp"%>

    <%
        BookmarkGroup bookmarkGroup = new BookmarkGroup();
        BookmarkGroupData bookmarkGroupData = new BookmarkGroupData();

        request.setCharacterEncoding("UTF-8");

        String bookmarkName = request.getParameter("bookmark_name");
        String sequence = request.getParameter("sequence");

        bookmarkGroupData.setBookmarkName(bookmarkName);
        bookmarkGroupData.setSequence(Integer.parseInt(sequence));
        int saveData = bookmarkGroup.getSaveNewBookmark(bookmarkGroupData);
    %>
    <script>
        <%
            String save = saveData > 0 ? "북마크 그룹 정보를 추가하였습니다." : "북마크 그룹 정보 추가를 실패했습니다.";
        %>
        alert("<%=save%>");
        location.href = "bookmarkGroup.jsp";
    </script>
</body>
</html>

