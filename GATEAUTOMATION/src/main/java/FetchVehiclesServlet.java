import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/FetchVehiclesServlet")
public class FetchVehiclesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0")) {
            MongoDatabase database = mongoClient.getDatabase("GATE_AUTOMATION");
            MongoCollection<Document> collection = database.getCollection("vehicles");

            JSONArray vehicleArray = new JSONArray();
            FindIterable<Document> vehicles = collection.find();

            for (Document vehicle : vehicles) {
                JSONObject obj = new JSONObject();
                obj.put("name", vehicle.getString("name"));
                obj.put("vehicle_number", vehicle.getString("vehicle_number"));
                obj.put("contact", vehicle.getString("contact"));
                vehicleArray.put(obj);
            }
            out.print(vehicleArray);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]"); // Return empty JSON array on error
        }
    }
}
