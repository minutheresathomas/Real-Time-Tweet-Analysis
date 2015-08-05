package cmpe239.twitter.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cmpe239.twitter.ReadTweetsText;
import cmpe239.twitter.domain.Tags;
import cmpe239.twitter.repository.MongoStorage;

public class WordCloudLocationServlet extends HttpServlet {
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String location = request.getParameter("loc");
		System.out.println("Location passed is :"+location);
		HttpSession session = request.getSession();
		ReadTweetsText obj = new ReadTweetsText();
		MongoStorage store = new MongoStorage();
		HashMap<Integer, String> msg = obj.getAllTagsCloudPerLocation(store, location);
		System.out.println("Setting the attr : Result size : "+msg.size());
		System.out.println("msg "+msg);
		session.setAttribute("WordCloudTagSupport", msg);
		request.setAttribute("location", location);
		
		
		
		// get the locations(arg1) for each cluster centers and display in UI in dropdwon
		RequestDispatcher view = request.getRequestDispatcher("/view/WordCloud.jsp");
		view.forward(request, response);
	}

}
