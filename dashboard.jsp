<%@ page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("username") == null) {
        response.sendRedirect("index.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="dashboard-container">
        <h2>Welcome, <%= sessionObj.getAttribute("username") %> 🚀</h2>
        <ul>
            <li><a href="addVehicle.jsp">🚗 Add Vehicle</a></li>
            <li><a href="viewVehicles.jsp">📋 View Vehicles</a></li>
            <li><a href="viewLogs.jsp">📜 Access Logs</a></li>
            <li><a href="LogoutServlet">🚪 Logout</a></li>
        </ul>
    </div>
</body>
</html>
