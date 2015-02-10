package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.TradeType;
import uk.ac.cam.cstibhotel.otcanalyser.trade.UPI;

import java.util.Date;

public class Search {
	
	private TradeType tradeType;
	private AssetClass assetClass;
	private String asset;
	private int minPrice, maxPrice;
	private String currency;
	private Date startTime, endTime;
	private UPI upi;
	
	public Search() {
		super();
	}
	
	public Search(TradeType t, AssetClass ac, String asset, int minP, int maxP, String c,
				Date st, Date et, UPI upi) {
		this.tradeType = t;
		this.assetClass = ac;
		this.asset = asset;
		this.minPrice = minP;
		this.maxPrice = maxP;
		this.currency = c;
		this.startTime = st;
		this.endTime = et;
		this.upi = upi;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public AssetClass getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(AssetClass assetClass) {
		this.assetClass = assetClass;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public UPI getUPI() {
		return upi;
	}

	public void setUPI(UPI upi) {
		this.upi = upi;
	}
	
}
