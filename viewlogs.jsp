<%@ page import="com.mongodb.client.*, org.bson.Document, java.util.*, javax.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("username") == null) {
        response.sendRedirect("index.html");
        return;
    }

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase database = mongoClient.getDatabase("GateAutomation");
    MongoCollection<Document> logsCollection = database.getCollection("logs");

    List<Document> logs = logsCollection.find().into(new ArrayList<>());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Access Logs</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="dashboard-container">
        <h2>📜 Access Logs</h2>
        <table>
            <thead>
                <tr>
                    <th>Vehicle ID</th>
                    <th>Timestamp</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <% for (Document log : logs) { %>
                <tr>
                    <td><%= log.getString("vehicle_id") %></td>
                    <td><%= log.getString("timestamp") %></td>
                    <td><%= log.getString("status") %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <a href="dashboard.jsp" class="back-btn">⬅ Back</a>
    </div>
</body>
</html>
