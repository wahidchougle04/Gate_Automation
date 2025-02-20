import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import static java.lang.System.out;
import java.util.regex.Pattern;
import org.bson.Document;

@WebServlet("/AddVehicleServlet")
public class AddVehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Pattern VEHICLE_PATTERN = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]{10}$");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String ownerName = request.getParameter("ownerName");
        String vehicleNumber = request.getParameter("vehicleNumber");
        String contactNumber = request.getParameter("contactNumber");
        String model = request.getParameter("model");
        // Validate vehicle number format
        if (!VEHICLE_PATTERN.matcher(vehicleNumber).matches()) {
            out.println("<script>alert('Invalid Vehicle Number! Format should be: AAXXAAXXXX'); window.history.back();</script>");
            return;
        }

        // Validate contact number format
        if (!MOBILE_PATTERN.matcher(contactNumber).matches()) {
            out.println("<script>alert('Invalid Mobile Number! It should be exactly 10 digits.'); window.history.back();</script>");
            return;
        }
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0")) {
            MongoDatabase database = mongoClient.getDatabase("GATE_AUTOMATION");
            MongoCollection<Document> collection = database.getCollection("vehicles");

            Document newVehicle = new Document("contact", "+91"+contactNumber)
                .append("license_plate", vehicleNumber)
                .append("model", model)
                .append("owner_name", ownerName);

            collection.insertOne(newVehicle);
            response.sendRedirect("dashboard.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addVehicle.html?error=Database error");
        }
    }
}
