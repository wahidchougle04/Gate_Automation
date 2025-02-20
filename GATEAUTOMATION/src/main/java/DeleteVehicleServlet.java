import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/DeleteVehicleServlet")
public class DeleteVehicleServlet extends HttpServlet {
    private static final String URI = "mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "GATE_AUTOMATION";
    private static final String COLLECTION_NAME = "vehicles";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Get vehicle ID from request
            String vehicleId = request.getParameter("vehicleId");

            if (vehicleId == null || vehicleId.isEmpty()) {
                out.write("{\"status\":\"error\", \"message\":\"Vehicle ID is required\"}");
                return;
            }

            // Delete vehicle from MongoDB
            collection.deleteOne(new Document("_id", new ObjectId(vehicleId)));

            out.write("{\"status\":\"success\", \"message\":\"Vehicle deleted successfully\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"status\":\"error\", \"message\":\"Error deleting vehicle\"}");
        }
    }
}
