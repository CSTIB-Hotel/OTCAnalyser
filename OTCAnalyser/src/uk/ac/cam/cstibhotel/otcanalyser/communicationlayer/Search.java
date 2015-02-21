package uk.ac.cam.cstibhotel.otcanalyser.communicationlayer;

import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.TradeType;

import java.util.Date;

public class Search {
	
	private TradeType tradeType;
	private AssetClass assetClass;
	private String asset;
	private long minPrice, maxPrice;
	private String currency;
	private Date startTime, endTime;
	private String upi;
	
	public Search() {
		super();
	}
	
	public Search(TradeType t, AssetClass ac, String asset, long minP, long maxP, String c,
				Date st, Date et, String upi) {
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

	public long getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(long minPrice) {
		this.minPrice = minPrice;
	}

	public long getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(long maxPrice) {
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

	public String getUPI() {
		return upi;
	}

	public void setUPI(String upi) {
		this.upi = upi;
	}
	
}
