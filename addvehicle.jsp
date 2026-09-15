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
    <title>Add Vehicle</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="form-container">
        <h2>🚗 Register a New Vehicle</h2>
        <form action="AddVehicleServlet" method="POST">
            <input type="text" name="vehicle_id" placeholder="Vehicle Number Plate" required>
            <input type="text" name="owner_name" placeholder="Owner Name" required>
            <input type="text" name="owner_contact" placeholder="Owner Contact" required>
            <button type="submit">Register Vehicle</button>
        </form>
    </div>
</body>
</html>
