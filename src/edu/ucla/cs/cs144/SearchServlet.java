package edu.ucla.cs.cs144;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
    	String query = request.getParameter("q");
    	String numResultsToSkipString = request.getParameter("numResultsToSkip");
    	String numResultsToReturnString = request.getParameter("numResultsToReturn");
    	int numResultsToSkip= 0;
    	int numResultsToReturn = 0;
    	if (numResultsToSkipString != null)
    		numResultsToSkip = Integer.valueOf(numResultsToSkipString);
    	if (numResultsToReturnString != null)
    		numResultsToReturn = Integer.valueOf(numResultsToReturnString);
    	SearchResult[] results = AuctionSearchClient.basicSearch(query, numResultsToSkip, numResultsToReturn);
    	
    	request.setAttribute("results", results);
    	request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
