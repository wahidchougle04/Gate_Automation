import com.mongodb.client.*;
import org.bson.Document;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AddVehicleServlet")
public class AddVehicleServlet extends HttpServlet {
    private static final String DB_URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "GateAutomation";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleId = request.getParameter("vehicle_id");
        String ownerName = request.getParameter("owner_name");
        String ownerContact = request.getParameter("owner_contact");

        try (MongoClient mongoClient = MongoClients.create(DB_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> vehiclesCollection = database.getCollection("vehicles");

            Document newVehicle = new Document("vehicle_id", vehicleId)
                    .append("owner_name", ownerName)
                    .append("owner_contact", ownerContact)
                    .append("status", "Active");

            vehiclesCollection.insertOne(newVehicle);
        }

        response.sendRedirect("viewVehicles.jsp");
    }
}
