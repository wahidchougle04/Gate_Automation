<%@ page import="com.mongodb.client.*, org.bson.Document, javax.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("username") == null) {
        response.sendRedirect("index.html");
        return;
    }

    String vehicleId = request.getParameter("vehicle_id");
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    MongoDatabase database = mongoClient.getDatabase("GateAutomation");
    MongoCollection<Document> vehiclesCollection = database.getCollection("vehicles");

    Document vehicle = vehiclesCollection.find(new Document("vehicle_id", vehicleId)).first();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Vehicle</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="form-container">
        <h2>✏️ Edit Vehicle</h2>
        <form action="EditVehicleServlet" method="POST">
            <input type="hidden" name="vehicle_id" value="<%= vehicle.getString("vehicle_id") %>">
            <input type="text" name="owner_name" value="<%= vehicle.getString("owner_name") %>" required>
            <input type="text" name="owner_contact" value="<%= vehicle.getString("owner_contact") %>" required>
            <button type="submit">Update Vehicle</button>
        </form>
    </div>
</body>
</html>
