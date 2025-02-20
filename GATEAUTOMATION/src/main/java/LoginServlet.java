import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.client.*;
import org.bson.Document;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Connect to MongoDB
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0")) {
            MongoDatabase database = mongoClient.getDatabase("GATE_AUTOMATION");
            MongoCollection<Document> collection = database.getCollection("admins");

            // Find admin by username
            Document admin = collection.find(new Document("username", username)).first();

            if (admin != null) {
                // Check if the password matches
                String dbPassword = admin.getString("password");
                if (dbPassword != null && dbPassword.equals(password)) {
                    // Login successful - Redirect to dashboard.html
                    response.sendRedirect("dashboard.html");
                } else {
                    // Invalid password
                    response.getWriter().write("Invalid Credentials");
                }
            } else {
                // No user found
                response.getWriter().write("No user found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Database Connection Error");
        }
    }
}