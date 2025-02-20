/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.bson.Document;

/**
 *
 * @author wahid
 */
@WebServlet("/RemoveVehicleServlet")
public class RemoveVehicleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String vehicleNumber = request.getParameter("number");

        MongoClient mongoClient = MongoClients.create("your_mongodb_connection_string");
        MongoDatabase database = mongoClient.getDatabase("GateAutomation");
        MongoCollection<Document> collection = database.getCollection("vehicles");

        collection.deleteOne(Filters.eq("vehicle_number", vehicleNumber));

        mongoClient.close();
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
