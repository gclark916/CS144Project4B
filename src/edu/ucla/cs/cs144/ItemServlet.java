package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
    	try
    	{
	    	String id = request.getParameter("id");
	    	String xml = AuctionSearchClient.getXMLDataForItemId(id);
	    	
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setValidating(false);
	        factory.setIgnoringElementContentWhitespace(true);      
	        DocumentBuilder builder = factory.newDocumentBuilder();

	        StringReader reader = new StringReader(xml);
	    	Document doc = builder.parse(new InputSource(reader));
	    	Element itemElement = doc.getDocumentElement();
	    	long itemID = Long.valueOf(itemElement.getAttribute("ItemID"));
	    	String name = getElementTextByTagNameNR(itemElement, "Name");
	    	String sellerLocation = getElementTextByTagNameNR(itemElement, "Location");
	    	String sellerCountry = getElementTextByTagNameNR(itemElement, "Country");
	    	
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss", Locale.ENGLISH);
	    	String startTimeString = getElementTextByTagNameNR(itemElement, "Started");
	    	Date startTime = dateFormat.parse(startTimeString);
	    	String endTimeString = getElementTextByTagNameNR(itemElement, "Ends");
	    	Date endTime = dateFormat.parse(endTimeString);
	    	
	    	String description = getElementTextByTagNameNR(itemElement, "Description");
	    	BigDecimal minimumBid = new BigDecimal(strip(getElementTextByTagNameNR(itemElement, "First_Bid")));
	    	String unstrippedBuyNowPriceString = getElementTextByTagNameNR(itemElement, "Buy_Price");
	    	BigDecimal buyNowPrice = null;
	    	if (unstrippedBuyNowPriceString != null && !unstrippedBuyNowPriceString.isEmpty())
	    	{
	    		buyNowPrice = new BigDecimal(strip(unstrippedBuyNowPriceString));
	    	}
	    	
	    	// Parse Seller
	    	Element sellerElement = getElementByTagNameNR(itemElement, "Seller");
	    	String sellerID = sellerElement.getAttribute("UserID");
	    	int sellerRating = Integer.valueOf(sellerElement.getAttribute("Rating"));
	    	
	    	// Create EbayUser object
	    	EbayUser seller = new EbayUser(sellerID, sellerRating, sellerCountry, sellerLocation);
	    	
	    	// Parse categories
	    	Element categoryElements[] = getElementsByTagNameNR(itemElement, "Category");
	    	Set<String> categorySet = new HashSet<String>();
	    	for (Element category : categoryElements)
	    	{
	    		// Parse Category
	    		String categoryName = getElementText(category);
	    		categorySet.add(categoryName);
	    	}
	    	String[] categoryArray = new String[categorySet.size()];
	    	categoryArray = categorySet.toArray(categoryArray);
	    	Arrays.sort(categoryArray, String.CASE_INSENSITIVE_ORDER);
	    	
	    	// Parse bids
	    	Element bidsParent = getElementByTagNameNR(itemElement, "Bids");
	    	Element bidElements[] = getElementsByTagNameNR(bidsParent, "Bid");
	    	Set<Bid> bidSet = new HashSet<Bid>();
	    	for (Element bidElement : bidElements)
	    	{
	    		// Parse bid
	        	String bidTimeString = getElementTextByTagNameNR(bidElement, "Time");
	        	Date bidTime = dateFormat.parse(bidTimeString);
	    		BigDecimal bidAmount = new BigDecimal(strip(getElementTextByTagNameNR(bidElement, "Amount")));
	    		
	    		// Parse bidder
	    		Element bidderElement = getElementByTagNameNR(bidElement, "Bidder");
	    		String bidderID = bidderElement.getAttribute("UserID");
	    		int bidderRating = Integer.valueOf(bidderElement.getAttribute("Rating"));
	    		String bidderLocation = getElementTextByTagNameNR(bidderElement, "Location");
	        	String bidderCountry = getElementTextByTagNameNR(bidderElement, "Country");
	    		
	    		// Create EbayUser object and add to set
	    		EbayUser bidder = new EbayUser(bidderID, bidderRating, bidderCountry, bidderLocation);
	    		
	    		// Create Bid object and add to set
	    		Bid bid = new Bid(0, itemID, bidder, bidAmount, bidTime);
	    		bidSet.add(bid);
	    	}
	    	Bid[] bidArray = new Bid[bidSet.size()];
	    	bidArray = bidSet.toArray(bidArray);
	    	Arrays.sort(bidArray, Bid.BidTimeComparator);
	    	
	    	// Create Item object and add to set
	    	Item item = new Item(itemID, name, seller, description, minimumBid, buyNowPrice, startTime, endTime, bidArray, categoryArray);
	    	
	    	request.setAttribute("item", item);
	    	request.getRequestDispatcher("/item.jsp").forward(request, response);
    	} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {}
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    protected Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    protected Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    protected String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    protected String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    protected String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
}
