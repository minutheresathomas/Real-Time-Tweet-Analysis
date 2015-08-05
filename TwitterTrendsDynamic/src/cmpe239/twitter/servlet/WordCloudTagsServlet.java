package cmpe239.twitter.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.JsonSerializer;

import com.google.gson.Gson;

import cmpe239.twitter.ReadTweetsText;
import cmpe239.twitter.domain.Tags;
import cmpe239.twitter.repository.MongoStorage;

/**
 * Servlet implementation class WordCloudTagsServlet
 */
//@WebServlet("/WordCloudTagsServlet")
public class WordCloudTagsServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public WordCloudTagsServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String location = request.getParameter("loc");
	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    Gson gson = new Gson();
//	    JsonSerializer someJsonSerializer = new JsonSerializer();
	    ReadTweetsText obj = new ReadTweetsText();
		MongoStorage store = new MongoStorage();
//		ArrayList<Tags> msg = obj.getAllTagsCloudPerLocation(store, location);
//		gson.toJson(msg);
//	    String json = someJsonSerializer.serialize(userService.findUsers(group));      
//	    out.print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//	}

}
