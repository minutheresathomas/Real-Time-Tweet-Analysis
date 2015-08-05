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
import cmpe239.twitter.repository.MongoStorage;

/**
 * Servlet implementation class TopLocationsServlet
 */
//@WebServlet("/TopLocationsServlet")
public class TopLocationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ReadTweetsText obj = new ReadTweetsText();
		MongoStorage store = new MongoStorage();
		HashMap<String, Integer> msg = obj.getAllTags(store);
		session.setAttribute("TopLocations", msg);
		
		ArrayList<String> locs = obj.getAllDistinctLocations(store);
		session.setAttribute("LocationForLatsLongs", locs);
		
		RequestDispatcher view = request.getRequestDispatcher("/view/topLocations.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
