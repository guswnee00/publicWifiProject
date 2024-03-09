<%-- 북마크 그룹 관리의 main jsp --%>
<%@ page import="function.BookmarkGroup"%>
<%@ page import="data.BookmarkGroupData"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>북마크 그룹</title>
	<style>
        table {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            border: 1px solid #ddd;
            width: 100%;
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

        #links {
            text-align: center;
        }

        #before_mark {
            text-align: center;
        }
    </style>
</head>

<body>
    <h1>북마크 그룹</h1>
    <%@include file="links.jsp"%>

    <button onclick="location.href='bookmarkGroupAdd.jsp'">북마크 그룹 이름 추가</button>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>순서</th>
                <th>등록일자</th>
                <th>수정일자</th>
                <th>비고</th>
            </tr>
        </thead>

        <tbody>
            <%
                BookmarkGroup bookmarkGroup = new BookmarkGroup();
                List<BookmarkGroupData> bookmarkGroupList = bookmarkGroup.getShowBookmark();
                if (!bookmarkGroupList.isEmpty()) {
                    for (BookmarkGroupData bookmarkGroupData : bookmarkGroupList) {
                        String modify = bookmarkGroupData.getModifyDttm() == null ? "" : bookmarkGroupData.getModifyDttm();
            %>
            <tr>
                <td><%=bookmarkGroupData.getBookmarkId()%></td>
                <td><%=bookmarkGroupData.getBookmarkName()%></td>
                <td><%=bookmarkGroupData.getSequence()%></td>
                <td><%=bookmarkGroupData.getRegisterDttm()%></td>
                <td><%=modify%></td>
                <td id="links">
                    <a href="bookmarkGroupModify.jsp?bookmarkId=<%=bookmarkGroupData.getBookmarkId()%>">수정</a>
                    <a href="bookmarkGroupDelete.jsp?bookmarkId=<%=bookmarkGroupData.getBookmarkId()%>">삭제</a>
                </td>
            <%
                    }
                } else {
            %>
                <td colspan="6" id="before_mark">정보가 존재하지 않습니다.</td>
            <%
                }
            %>
            </tr>
        </tbody>
    </table>
</body>

</html>