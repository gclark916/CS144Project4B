package edu.ucla.cs.cs144;

import java.math.BigDecimal;
import java.util.Date;

public class Item {
	long id;
	String name;
	EbayUser seller;
	String description;
	Bid[] bids;
	String[] categories;
	BigDecimal minimumFirstBid;
	BigDecimal buyNowPrice;
	Date startTime;
	Date endTime;
	
	/**
	 * @param id
	 * @param name
	 * @param seller
	 * @param description
	 * @param minimumFirstBid
	 * @param buyNowPrice
	 * @param startTime
	 * @param endTime
	 * @param bids
	 * @param categories
	 */
	public Item(long id, String name, EbayUser seller, String description,
			BigDecimal minimumFirstBid, BigDecimal buyNowPrice, Date startTime,
			Date endTime, Bid[] bids, String[] categories) {
		super();
		this.id = id;
		this.name = name;
		this.seller = seller;
		this.description = description;
		this.minimumFirstBid = minimumFirstBid;
		this.buyNowPrice = buyNowPrice;
		this.startTime = startTime;
		this.endTime = endTime;
		this.bids = bids;
		this.categories = categories;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the seller
	 */
	public EbayUser getSeller() {
		return seller;
	}

	/**
	 * @param seller the seller to set
	 */
	public void setSeller(EbayUser seller) {
		this.seller = seller;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the bids
	 */
	public Bid[] getBids() {
		return bids;
	}

	/**
	 * @param bids the bids to set
	 */
	public void setBids(Bid[] bids) {
		this.bids = bids;
	}

	/**
	 * @return the categories
	 */
	public String[] getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	/**
	 * @return the minimumFirstBid
	 */
	public BigDecimal getMinimumFirstBid() {
		return minimumFirstBid;
	}

	/**
	 * @param minimumFirstBid the minimumFirstBid to set
	 */
	public void setMinimumFirstBid(BigDecimal minimumFirstBid) {
		this.minimumFirstBid = minimumFirstBid;
	}

	/**
	 * @return the buyNowPrice
	 */
	public BigDecimal getBuyNowPrice() {
		return buyNowPrice;
	}

	/**
	 * @param buyNowPrice the buyNowPrice to set
	 */
	public void setBuyNowPrice(BigDecimal buyNowPrice) {
		this.buyNowPrice = buyNowPrice;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
