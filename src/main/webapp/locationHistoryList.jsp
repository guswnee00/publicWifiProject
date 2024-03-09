<%-- 위치 검색 기록을 보여주는 jsp --%>
<%@ page import="java.util.*"%>
<%@ page import="function.LatLntHistory"%>
<%@ page import="data.LatLntData"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<html>
<head>
<meta charset="UTF-8">
<title>위치 히스토리 목록</title>
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

        tr:nth-child(odd) {
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

        #delete_history_button {
            text-align: center;
        }

        #before_search {
            text-align: center;
        }
    </style>
</head>

<body>
	<h1>위치 히스토리 목록</h1>
	<%@include file="links.jsp"%>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>조회일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
        <%
            LatLntHistory latLntHistory = new LatLntHistory();
            List<LatLntData> historyList = latLntHistory.getSearchHistory();
            String searchHistoryId = request.getParameter("historyId");

            if (searchHistoryId != null) {
                latLntHistory.getDeleteSearchHistory(searchHistoryId);
            }

            if (historyList != null) {
                for (LatLntData latLntData : historyList) {
        %>
            <tr>
                <td><%=latLntData.getHistoryId()%></td>
                <td><%=latLntData.getLat()%></td>
                <td><%=latLntData.getLnt()%></td>
                <td><%=latLntData.getSearchDttm()%></td>
                <td id="delete_history_button"><button onclick="deleteSearchHistory(<%=latLntData.getHistoryId()%>)">삭제</button></td>
            </tr>
        <%
                }
            } else {
        %>
                <td colspan="5" id="before_search">위치 조회 이력이 없습니다.</td>
        <%
            }
        %>
        </tbody>
    </table>
    <script>
        function deleteSearchHistory(history_id) {
            if (confirm("위치 기록을 삭제하시겠습니까?")) {
                $.ajax({
                    url: "http://localhost:8080/locationHistoryList.jsp",
                    data: {historyId : history_id},
                    success: function () {
                        location.reload();
                    },
                    error: function (e) {
                        alert(e);
                    }
                });
            }
        }
    </script>
</body>
</html>