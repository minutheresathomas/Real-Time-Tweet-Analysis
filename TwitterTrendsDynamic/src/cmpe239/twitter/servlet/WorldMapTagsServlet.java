package cmpe239.twitter.servlet;

import java.io.IOException;
import java.util.ArrayList;

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

//@WebServlet("/view/WorldMapTags")
public class WorldMapTagsServlet extends HttpServlet{
	
	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		ReadTweetsText obj = new ReadTweetsText();
		MongoStorage store = new MongoStorage();
		ArrayList<Tags> msg = obj.getAllTagsCloud(store, 3);
		//System.out.println("Size of tags list in map : "+msg.size());
		session.setAttribute("WordlMapTags", msg);
		
		// get the locations(arg1) for each cluster centers and display in UI in dropdwon
		ArrayList<String> locs = obj.getAllDistinctLocations(store);
		session.setAttribute("LocationForLatsLongs", locs);
		RequestDispatcher view = request.getRequestDispatcher("/view/WorldMapTags.jsp");
		view.forward(request, response);
	}

}
