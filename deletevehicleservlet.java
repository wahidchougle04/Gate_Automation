import com.mongodb.client.*;
import org.bson.Document;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeleteVehicleServlet")
public class DeleteVehicleServlet extends HttpServlet {
    private static final String DB_URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "GateAutomation";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleId = request.getParameter("vehicle_id");

        try (MongoClient mongoClient = MongoClients.create(DB_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> vehiclesCollection = database.getCollection("vehicles");

            vehiclesCollection.deleteOne(new Document("vehicle_id", vehicleId));
        }

        response.sendRedirect("viewVehicles.jsp");
    }
}
