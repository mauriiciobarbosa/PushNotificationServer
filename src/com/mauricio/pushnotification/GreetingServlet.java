package com.mauricio.pushnotification;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a simple example of an HTTP Servlet. It responds to the GET method of
 * the HTTP protocol.
 *
 */
@WebServlet("/greeting")
public class GreetingServlet extends HttpServlet {

	private static final String URL = "https://gcm-http.googleapis.com/gcm/send";
	private static final String API_KEY = "AIzaSyDqc1jaBdTNacovjMSTzi_nciS7N9umeXs";
	private static List<String> androidDevices = new ArrayList<>();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setBufferSize(8192);
		PrintWriter out = response.getWriter();

		// then write the data of the response
		out.println("<html lang=\"en\">" + "<head><title>Servlet Hello</title></head>");

		// then write the data of the response
		out.println("<body  bgcolor=\"#ffffff\">" + "<form method=\"get\">"
				+ "<h2>Hello, send message to android devices</h2>" + "<input title=\"Message: \" type=\"text\" "
				+ "name=\"message\" size=\"25\"/>" + "<p></p>" + "<input type=\"submit\" value=\"Submit\"/>"
				+ "<input type=\"reset\" value=\"Reset\"/>" + "</form>");

		String message = request.getParameter("message");
		if (message != null && message.length() > 0) {
			boolean sent = sendMessage(message);
			if (sent)
				out.println("<h6>Message sent<h6>");
			else
				out.println("<h6>Message not sent<h6>");
		}
		out.println("</body></html>");
		out.close();
	}

	private boolean sendMessage(String message) throws IOException {
		try {
			if (androidDevices.size() > 0)  {
				URL url = new URL(URL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Authorization", "key=" + API_KEY);
				
				conn.setDoOutput(true);
				
				String devices = "";
				for (String device : androidDevices) {
					devices += "\"" + device + "\"" + ",";
				}
				devices = devices.substring(0, devices.length()-1); // elimina a virgula no final
				
				String input = "{\"registration_ids\" : [" + devices + "],\"data\" : {\"message\": \"" + message + "\"}}";
				System.out.println(input);
				
				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
				os.close();
				
				return true;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void addAndroidDevices(String token) {
		androidDevices.add(token);
	}

}