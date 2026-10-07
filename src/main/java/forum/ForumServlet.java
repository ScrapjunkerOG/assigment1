package forum;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// This servlet handles requests sent to /forum.
@WebServlet("/forum")
public class ForumServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// The object responsible for storing and searching forum entries.
    private EntryHandler entryHandler;

    // init() is called by Tomcat when the servlet is created.
    @Override
    public void init() throws ServletException {

        // Create the EntryHandler that this servlet will use.
        entryHandler = new EntryHandler();
    }

    // doPost() handles data sent TO the server,
    // such as when the user submits the forum form.
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Get the value from the HTML input named "userName".
        String userName = request.getParameter("userName");

        // Get the value from the HTML textarea named "message".
        String message = request.getParameter("message");

        // Give the username and message to EntryHandler.
        // EntryHandler creates and stores the Entry object.
        entryHandler.addEntry(userName, message);

        // Send the browser back to the forum page.
        // This also prevents the browser from submitting the same
        // POST again if the user refreshes the page.
        response.sendRedirect(request.getContextPath() + "/forum");
    }

    // doGet() handles requests for displaying the forum page
    // and performing searches.
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

    	// Tell the browser that we are sending HTML encoded as UTF-8.
    	response.setContentType("text/html;charset=UTF-8");

    	// Get the search text from the URL parameter named "search".
    	String searchTerm = request.getParameter("search");

    	List<Entry> entries;

    	
    	// If no search was entered, display all entries.
    	// Otherwise, ask EntryHandler to return only matching entries.
    	if (searchTerm == null || searchTerm.isBlank()) {
    	    entries = entryHandler.getAllEntries();
    	} else {
    	    entries = entryHandler.search(searchTerm);
    	}

    	PrintWriter out = response.getWriter();

    	// Start the HTML document.
    	out.println("<!DOCTYPE html>");
    	out.println("<html>");
    	out.println("<head>");
    	out.println("<meta charset='UTF-8'>");
    	out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
    	out.println("<title>Forum</title>");

    	// Simple CSS to make the forum look nicer.
    	out.println("<style>");

    	out.println("body {");
    	out.println("    font-family: Arial, sans-serif;");
    	out.println("    background-color: #f2f2f2;");
    	out.println("    margin: 0;");
    	out.println("    padding: 40px;");
    	out.println("}");

    	out.println(".container {");
    	out.println("    max-width: 800px;");
    	out.println("    margin: auto;");
    	out.println("}");

    	out.println("h1 {");
    	out.println("    text-align: center;");
    	out.println("    margin-bottom: 30px;");
    	out.println("}");

    	out.println(".form-box {");
    	out.println("    background: white;");
    	out.println("    padding: 20px;");
    	out.println("    margin-bottom: 20px;");
    	out.println("    border-radius: 10px;");
    	out.println("    box-shadow: 0 2px 6px rgba(0,0,0,0.1);");
    	out.println("}");

    	out.println("input, textarea {");
    	out.println("    width: 100%;");
    	out.println("    box-sizing: border-box;");
    	out.println("    padding: 10px;");
    	out.println("    margin-bottom: 10px;");
    	out.println("    border: 1px solid #ccc;");
    	out.println("    border-radius: 6px;");
    	out.println("    font-size: 14px;");
    	out.println("}");

    	out.println("textarea {");
    	out.println("    height: 100px;");
    	out.println("    resize: vertical;");
    	out.println("}");

    	out.println("button {");
    	out.println("    padding: 10px 18px;");
    	out.println("    border: none;");
    	out.println("    border-radius: 6px;");
    	out.println("    background-color: #333;");
    	out.println("    color: white;");
    	out.println("    cursor: pointer;");
    	out.println("}");

    	out.println("button:hover {");
    	out.println("    background-color: #555;");
    	out.println("}");

    	out.println(".entry {");
    	out.println("    background: white;");
    	out.println("    padding: 20px;");
    	out.println("    margin-bottom: 15px;");
    	out.println("    border-radius: 10px;");
    	out.println("    box-shadow: 0 2px 6px rgba(0,0,0,0.1);");
    	out.println("}");

    	out.println(".username {");
    	out.println("    font-size: 18px;");
    	out.println("    font-weight: bold;");
    	out.println("}");

    	out.println(".message {");
    	out.println("    margin: 10px 0;");
    	out.println("}");

    	out.println(".date {");
    	out.println("    color: #888;");
    	out.println("    font-size: 12px;");
    	out.println("}");

    	out.println("</style>");
    	out.println("</head>");

    	out.println("<body>");

    	out.println("<div class='container'>");

    	out.println("<h1>Forum</h1>");

    	// Form for creating a new forum entry.
    	out.println("<div class='form-box'>");
    	out.println("<h2>New message</h2>");

    	out.println("<form method='post' action='forum'>");

    	out.println("<input type='text' name='userName' placeholder='Your name'>");

    	out.println("<textarea name='message' placeholder='Write your message...'></textarea>");

    	out.println("<button type='submit'>Submit</button>");

    	out.println("</form>");
    	out.println("</div>");

    	// Form for searching existing entries.
    	out.println("<div class='form-box'>");
    	out.println("<h2>Search</h2>");

    	out.println("<form method='get' action='forum'>");

    	out.println("<input type='text' name='search' placeholder='Search username or message'>");

    	out.println("<button type='submit'>Search</button>");

    	out.println("</form>");
    	out.println("</div>");

    	// Display the entries.
    	for (Entry entry : entries) {

    	    out.println("<div class='entry'>");

    	    out.println("<div class='username'>"
    	            + entry.getUserName()
    	            + "</div>");

    	    out.println("<div class='message'>"
    	            + entry.getMessage()
    	            + "</div>");

    	    out.println("<div class='date'>"
    	            + entry.getDateTime()
    	            + "</div>");

    	    out.println("</div>");
    	}

    	out.println("</div>");

    	out.println("</body>");
    	out.println("</html>");

    }
}