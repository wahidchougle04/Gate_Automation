import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final String CONNECTION_STRING = "mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "GATE_AUTOMATION";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String vehicleNumber = request.getParameter("vehicle_number");
        String contact = request.getParameter("contact");
        String email = request.getParameter("email");

        // Connect to MongoDB
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection("admins");

            // Check if username already exists
            if (collection.find(Filters.eq("username", username)).first() != null) {
                out.println("<h3 style='color:red;'>Username already exists!</h3>");
            } else {
                // Create new admin document
                Document newAdmin = new Document("username", username)
                        .append("password", password)
                        .append("name", name)
                        .append("vehicle_number", vehicleNumber)
                        .append("contact", contact)
                        .append("email", email);

                collection.insertOne(newAdmin);
                response.sendRedirect("login.html");
            }
        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
