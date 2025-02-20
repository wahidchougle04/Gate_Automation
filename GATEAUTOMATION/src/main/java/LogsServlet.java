import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
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

@WebServlet("/LogsServlet")
public class LogsServlet extends HttpServlet {
    private static final String MONGO_URI = "mongodb+srv://wahid:Wahid123@cluster0.bzg8n.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"; // MongoDB connection URI
    private static final String DATABASE_NAME = "GATE_AUTOMATION"; // Database name
    private static final String COLLECTION_NAME = "logs"; // Collection name

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Fetch all logs from the collection
            FindIterable<Document> logs = collection.find();
            List<Document> logList = new ArrayList<>();
            for (Document log : logs) {
                logList.add(log);
            }

            // Convert the list of logs to a JSON array
            StringBuilder jsonArray = new StringBuilder("[");
            for (Document log : logList) {
                jsonArray.append(log.toJson()).append(",");
            }
            if (!logList.isEmpty()) {
                jsonArray.deleteCharAt(jsonArray.length() - 1); // Remove the last comma
            }
            jsonArray.append("]");

            // Send the JSON array as the response
            PrintWriter out = response.getWriter();
            out.print(jsonArray.toString());
            out.flush();
        } catch (Exception e) {
            // Handle errors
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}