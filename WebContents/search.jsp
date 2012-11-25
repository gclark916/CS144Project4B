<!DOCTYPE html>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="edu.ucla.cs.cs144.*" %>
<% SearchResult[] results = (SearchResult[]) request.getAttribute("results"); 
   String query = request.getParameter("q"); 
   String numResultsToSkipString = request.getParameter("numResultsToSkip");
   int numResultsToSkip = 0;
   if (numResultsToSkipString != null)
       numResultsToSkip = Integer.valueOf(numResultsToSkipString);
   String numResultsToReturnString = request.getParameter("numResultsToReturn");
   int numResultsToReturn = 10;
   if (numResultsToReturnString != null)
       numResultsToReturn = Integer.valueOf(numResultsToReturnString); %>
<html>
<head>
    <title><%= query %> | eBay</title>
    <link rel="stylesheet" type="text/css" href="./eBay.css">
    <script type="text/javascript" src="/eBay/AutoSuggest.js"></script>
    <script type="text/javascript">
    var autoSuggest;
    
    function initialize ()
    {
        autoSuggest = new AutoSuggestControl(document.getElementById("searchBox"));
    }
    </script>
</head>
<body onload="initialize()">
<form action="./search" method="GET" autocomplete="off">
<input id="searchBox" type="text" name="q" autocomplete="off">
<input type="hidden" name="numResultsToSkip" value="0">
<input type="hidden" name="numResultsToReturn" value="10">
<input type="submit" value="Search">
</form><br/>

<% if (results.length == 1 && Long.valueOf(results[0].getItemId()) < 0) { %>
Invalid search term<% } else { %>
<% if (results.length == 0) { %>No more results.<% } else { %> 
Results <%= numResultsToSkip+1 %>-<%= numResultsToSkip+results.length %><br/>
<table>
<% for (SearchResult result : results) { %>
<tr>
<td><a href="/eBay/item?id=<%= result.getItemId() %>"><%=result.getName()%></a></td>
</tr>
<% } %>
</table>
<% } %>

<% if (numResultsToSkip > 0) { %>
<a href="/eBay/search?q=<%= query %>&numResultsToSkip=<%= numResultsToSkip - 10 >= 0 ? numResultsToSkip - 10 : 0 %>&numResultsToReturn=10">Prev</a>
<% } 
if (results.length == 10) {%>
<a href="/eBay/search?q=<%= query %>&numResultsToSkip=<%= numResultsToSkip + 10 %>&numResultsToReturn=10">Next</a>
<% }} %>
</body>
</html>