<%@ page import="com.mongodb.client.*, org.bson.Document, java.util.*, javax.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("username") == null) {
        response.sendRedirect("index.html");
        return;
    }

    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase database = mongoClient.getDatabase("GateAutomation");
    MongoCollection<Document> vehiclesCollection = database.getCollection("vehicles");

    List<Document> vehicles = vehiclesCollection.find().into(new ArrayList<>());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Vehicles</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="dashboard-container">
        <h2>🚗 Registered Vehicles</h2>
        <table>
            <thead>
                <tr>
                    <th>Vehicle ID</th>
                    <th>Owner Name</th>
                    <th>Owner Contact</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Document vehicle : vehicles) { %>
                <tr>
                    <td><%= vehicle.getString("vehicle_id") %></td>
                    <td><%= vehicle.getString("owner_name") %></td>
                    <td><%= vehicle.getString("owner_contact") %></td>
                    <td>
                        <a href="editVehicle.jsp?vehicle_id=<%= vehicle.getString("vehicle_id") %>" class="edit-btn">✏️ Edit</a>
                        <a href="DeleteVehicleServlet?vehicle_id=<%= vehicle.getString("vehicle_id") %>" class="delete-btn">🗑 Delete</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <a href="dashboard.jsp" class="back-btn">⬅ Back</a>
    </div>
</body>
</html>
