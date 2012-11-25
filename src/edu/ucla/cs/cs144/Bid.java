package edu.ucla.cs.cs144;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

public class Bid {
	long id;
	long itemID;
	EbayUser bidder;
	Date time;
	BigDecimal amount;
	
	/**
	 * @param id
	 * @param itemID
	 * @param bidder
	 * @param amount
	 * @param time
	 */
	public Bid(long id, long itemID, EbayUser bidder, BigDecimal amount,
			Date time) {
		super();
		this.id = id;
		this.itemID = itemID;
		this.bidder = bidder;
		this.amount = amount;
		this.time = time;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((bidder == null) ? 0 : bidder.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (itemID ^ (itemID >>> 32));
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (bidder == null) {
			if (other.bidder != null)
				return false;
		} else if (!bidder.equals(other.bidder))
			return false;
		if (id != other.id)
			return false;
		if (itemID != other.itemID)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
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
	 * @return the itemID
	 */
	public long getItemID() {
		return itemID;
	}

	/**
	 * @param itemID the itemID to set
	 */
	public void setItemID(long itemID) {
		this.itemID = itemID;
	}

	/**
	 * @return the bidder
	 */
	public EbayUser getBidder() {
		return bidder;
	}

	/**
	 * @param bidder the bidder to set
	 */
	public void setBidder(EbayUser bidder) {
		this.bidder = bidder;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/**
     * Compare current bid with given bid using their bid times
     */
	public static Comparator<Bid> BidTimeComparator = new Comparator<Bid>() {
		public int compare(Bid bid1, Bid bid2) 
		{
			Date time1 = bid1.getTime();
		    Date time2 = bid2.getTime();
		    
		    return time1.compareTo(time2);
		}
	};
	
}
