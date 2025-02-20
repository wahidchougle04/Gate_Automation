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

@WebServlet("/VehicleListServlet")
public class VehicleListServlet extends HttpServlet {
    private static final String URI = "mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "GATE_AUTOMATION";
    private static final String COLLECTION_NAME = "vehicles";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            List<JSONObject> vehicleList = new ArrayList<>();
            for (Document doc : collection.find()) {
                JSONObject vehicle = new JSONObject();
                vehicle.put("_id", doc.getObjectId("_id").toString());
                vehicle.put("owner_name", doc.getString("owner_name"));
                vehicle.put("license_plate", doc.getString("license_plate"));
                vehicle.put("model", doc.getString("model"));
                vehicle.put("contact", doc.getString("contact"));
                vehicleList.add(vehicle);
            }

            JSONArray jsonArray = new JSONArray(vehicleList);
            out.print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\":\"Error fetching vehicles\"}");
        }
    }
}
