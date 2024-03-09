<--전체 데이터개수를 보여주는 jsp-->
<%@ page import="api.OpenApi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            text-align: center;
        }
    </style>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

    <% int cnt = OpenApi.getTotalWifiCount(); %>
    <h2><%=cnt%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h2>

    <a href="http://localhost:8080">홈으로 가기</a>

</body>
</html>