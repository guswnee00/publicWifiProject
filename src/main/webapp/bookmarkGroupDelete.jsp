<%-- 북마크 그룹 추가에서 북마크 내용을 삭제하는 jsp --%>
<%@ page import="function.BookmarkGroup"%>
<%@ page import="data.BookmarkGroupData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>북마크 그룹 삭제</title>
	<style>
        table {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            border: 1px solid #ddd;
            width: 100%;
            margin-top: 10px;
        }

        tr {
            padding: 8px;
            font-size: 13px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: center;
            border: 1px solid #ddd;
            background-color: #04AA6D;
            color: white;
        }

        td {
            border: 1px solid #ddd;
            padding: 8px;
            font-weight: normal;
            font-size: 13px;
        }

        #delete {
            text-align: center;
        }

    </style>
</head>

<body>
    <h1>북마크 그룹 삭제</h1>
    <%@include file="links.jsp"%>
    <div>북마크 그룹 이름을 삭제하시겠습니까?</div>

    <%
        String bookmarkId = request.getParameter("bookmarkId");
        BookmarkGroup bookmarkGroup = new BookmarkGroup();
        BookmarkGroupData bookmarkGroupData = bookmarkGroup.getBookmark(Integer.parseInt(bookmarkId));
    %>

    <form method="post" action="bookmarkGroupDeleteComplete.jsp">
        <table>
            <tr>
                <th>북마크 이름</th>
                <td><input type="text" name="bookmark_name" value="<%=bookmarkGroupData.getBookmarkName()%>"></td>
            </tr>
            <tr>
                <th>순서</th>
                <td><input type="text" name="sequence" value="<%=bookmarkGroupData.getSequence()%>"></td>
            </tr>
            <tr>
                <td colspan="2" id="delete">
                    <a href="bookmarkGroup.jsp">돌아가기</a>
                    <span>|</span>
                    <input type="submit" value="삭제">
                    <input type="hidden" name="bookmarkId" value="<%=bookmarkGroupData.getBookmarkId()%>">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>