<!DOCTYPE html>
<%@ page import="java.util.*" %>
<%@ page import="edu.ucla.cs.cs144.*" %>
<% 
Item item = (Item) request.getAttribute("item");
String itemName = item.getName();
EbayUser seller = item.getSeller(); 
Bid[] bids = item.getBids();
String[] categories = item.getCategories(); %>
<html>
    <head>
        <title><%= itemName %></title>
        <link rel="stylesheet" type="text/css" href="./eBay.css">
        <script type="text/javascript" src="/eBay/AutoSuggest.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
        <script type="text/javascript">
        var geocoder;
        var map;
        var autoSuggest;

        function initialize() {
            geocoder = new google.maps.Geocoder();
            var latlng = new google.maps.LatLng(0.0, 0.0);
            var mapOptions = {
              zoom: 0,
              center: latlng,
              mapTypeId: google.maps.MapTypeId.ROADMAP
            }
            map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
            
            var address = "<%= seller.getLocation() %>, <%= seller.getCountry() %>";
            geocoder.geocode( { 'address': address}, 
            function(results, status) 
            {
              if (status == google.maps.GeocoderStatus.OK) 
              {
                map.setCenter(results[0].geometry.location);
                map.fitBounds(results[0].geometry.viewport);
                var marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location});
              }
              else {
                  geocoder.geocode( { 'address': "<%= seller.getCountry() %>"}, 
                  function(results, status) 
                  {
                    if (status == google.maps.GeocoderStatus.OK) 
                    {
                        map.setCenter(results[0].geometry.location);
                        map.fitBounds(results[0].geometry.viewport);
                        var marker = new google.maps.Marker({
                            map: map,
                            position: results[0].geometry.location});
                    }
                  })
                }
            });
            
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
        <%= itemName %><br/>
        Seller: <%= seller.getId() %> (<%= seller.getRating() %>)<br/>
        <%= seller.getLocation() %>, <%= seller.getCountry() %><br/>
        <div id="map_canvas" style="width: 50%; height: 400px"></div>
        <br/>
        Current Price: $<%= bids.length > 0 ? bids[bids.length-1].getAmount() : item.getMinimumFirstBid() %><br/>
        <% if (item.getBuyNowPrice() != null) { %>
        Buy Now Price: $<%= item.getBuyNowPrice() %><br/>
        <% } %>
        Start Time: <%= item.getStartTime() %><br/>
        End Time: <%= item.getEndTime() %><br/>
        <br/>
        Description:
        <%= item.getDescription() %><br/>
        <br/>
        Categories:
        <table>
            <% for (String category : categories) { %>
            <tr><td><%= category %></td></tr>
            <% } %>
        </table><br/>
        <%= bids.length %> Bid<% if (bids.length != 1) { %>s<% } %><% if (bids.length > 0) { %><br/>
        <table border="1px solid black" cellpadding="0" cellspacing="0">
            <tr><td>Bidder</td><td>Bid Amount</td><td>Bid Time</td></tr>
            <% for (Bid bid : bids) { 
            EbayUser bidder = bid.getBidder(); %>
            <tr><td><%= bidder.getId() %> (<%= bidder.getRating() %>)</td><td>$<%= bid.getAmount() %></td><td><%= bid.getTime() %></td></tr> 
            <% } %>
        </table>
        <% } %>
    </body>
</html>