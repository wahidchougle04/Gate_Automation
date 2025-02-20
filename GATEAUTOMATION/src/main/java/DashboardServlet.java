import com.mongodb.client.*;
import org.bson.Document;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final String CONNECTION_STRING = "mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "GATE_AUTOMATION";
    private static final String COLLECTION_NAME = "vehicles";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            
            FindIterable<Document> vehicles = collection.find();
            JSONArray vehicleArray = new JSONArray();

            for (Document doc : vehicles) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("_id", doc.getObjectId("_id").toString());
                jsonObject.put("owner_name", doc.getString("owner_name"));
                jsonObject.put("license_plate", doc.getString("license_plate"));
                jsonObject.put("model", doc.getString("model"));
                jsonObject.put("contact", doc.getString("contact"));

                vehicleArray.put(jsonObject);
            }

            out.print(vehicleArray.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Failed to fetch data.\"}");
        }
    }
}
