package uk.ac.cam.cstibhotel.otcanalyser.trade;

import java.util.Currency;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Trade {

	// The fields in here are a direct copy of the fields of a trade taken
	// from the OTC repository
	private long disseminationID;
	private long originalDisseminationID;
	private Action action;
	private Date executionTimestamp;
	private Boolean cleared;
	private Collateralization collateralization;
	private Boolean endUserException;
	private Boolean bespoke;
	private Boolean executionVenue; // true for ON, false for OFF
	private Boolean blockTrades;
	private Date effectiveDate;
	private Date endDate;
	private String dayCountConvention; // e.g. ACT/360 and 1/1 30/360
	private String settlementCurrency;
	private TradeType tradeType;
	private AssetClass assetClass;
	private SubAssetClass subAssetClass;
	private UPI taxonomy;
	private PriceFormingContinuationData priceFormingContinuationData;
	private String underlyingAsset1;
	private String underlyingAsset2;
	private String priceNotationType;
	private Double priceNotation;
	private String additionalPriceNotationType;
	private Double additionalPriceNotation;
	private String notionalCurrency1;
	private String notionalCurrency2;
	
	//TODO: Not sure about the types in the upcoming fields
	private String roundedNotionalAmount1; //probably shouldn't be a long, since some values are e.g. 95,000,000+ TODO: probably should be long
	private String roundedNotionalAmount2; //also has + TODO: Probably should be long
	private String paymentFrequency1; //e.g. 3M, 1M, 0, but also dates like 2016-01-29
	private String paymentFrequency2; //e.g. 1D, 1T, 1M
	private String resetFrequency1; //e.g. 2024-10-10
	private String resetFrequency2; //e.g. 1D, 1M, 1T
	private String embeddedOption; //e.g. EMBED1

	// Options
	private Double optionStrikePrice;
	private String optionType;
	private String optionFamily;
	private String optionCurrency;
	private Double optionPremium; //e.g. 0.00034 and 90250
	private Date optionLockPeriod;
	private Date optionExpirationDate;
	private String priceNotation2Type;
	private Double priceNotation2;
	private String priceNotation3Type;
	private Double priceNotation3;

	public Trade() {
		// Sets defaults on all enum and objects
		// Necessary to avoid a lot of try-catch for NPEs in Database
		
		action = Action.NEW;
		executionTimestamp = new Date();
		collateralization = Collateralization.BLANK;
		effectiveDate = new Date();
		endDate = new Date();
		settlementCurrency = "";
		tradeType = TradeType.OPTION;
		assetClass = AssetClass.COMMODITY;
		subAssetClass = new SubAssetClass();
		try {
			taxonomy = new UPI("ForeignExchange:ForeignExchange:Bar");
		} catch (InvalidTaxonomyException ex) {
			Logger.getLogger(Trade.class.getName()).log(Level.SEVERE, null, ex);
		} catch (EmptyTaxonomyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		priceFormingContinuationData = PriceFormingContinuationData.TRADE;
		notionalCurrency1 = "";
		notionalCurrency2 = "";
		optionCurrency = "";
		optionLockPeriod = new Date();
		optionExpirationDate = new Date();
		priceNotation = new Double(0);
		priceNotation2 = new Double(0);
		priceNotation3 = new Double(0);
		additionalPriceNotation = new Double(0);
		optionStrikePrice = new Double(0);
		optionPremium = new Double(0);
		
	}
	
	public void setDisseminationID(long disseminationID) {
		this.disseminationID = disseminationID;
	}

	public void setOriginalDisseminationID(long originalDisseminationID) {
		this.originalDisseminationID = originalDisseminationID;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setExecutionTimestamp(Date executionTimestamp) {
		this.executionTimestamp = executionTimestamp;
	}

	public void setCleared(Boolean cleared) {
		this.cleared = cleared;
	}

	public void setCollateralization(Collateralization collateralization) {
		this.collateralization = collateralization;
	}

	public void setEndUserException(Boolean endUserException) {
		this.endUserException = endUserException;
	}

	public void setBespoke(Boolean bespoke) {
		this.bespoke = bespoke;
	}

	public void setExecutionVenue(Boolean executionVenue) {
		this.executionVenue = executionVenue;
	}

	public void setBlockTrades(Boolean blockTrades) {
		this.blockTrades = blockTrades;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setDayCountConvention(String dayCountConvention) {
		this.dayCountConvention = dayCountConvention;
	}

	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public void setAssetClass(AssetClass assetClass) {
		this.assetClass = assetClass;
	}

	public void setSubAssetClass(SubAssetClass subAssetClass) {
		this.subAssetClass = subAssetClass;
	}

	public void setTaxonomy(UPI taxonomy) {
		this.taxonomy = taxonomy;
	}

	public void setPriceFormingContinuationData(PriceFormingContinuationData priceFormingContinuationData) {
		this.priceFormingContinuationData = priceFormingContinuationData;
	}

	public void setUnderlyingAsset1(String underlyingAsset1) {
		this.underlyingAsset1 = underlyingAsset1;
	}

	public void setUnderlyingAsset2(String underlyingAsset2) {
		this.underlyingAsset2 = underlyingAsset2;
	}

	public void setPriceNotationType(String priceNotationType) {
		this.priceNotationType = priceNotationType;
	}

	public void setPriceNotation(Double priceNotation) {
		this.priceNotation = priceNotation;
	}

	public void setAdditionalPriceNotationType(String additionalPriceNotationType) {
		this.additionalPriceNotationType = additionalPriceNotationType;
	}

	public void setAdditionalPriceNotation(Double additionalPriceNotation) {
		this.additionalPriceNotation = additionalPriceNotation;
	}

	public void setNotionalCurrency1(String notionalCurrency1) {
		this.notionalCurrency1 = notionalCurrency1;
	}

	public void setNotionalCurrency2(String notionalCurrency2) {
		this.notionalCurrency2 = notionalCurrency2;
	}

	public void setRoundedNotionalAmount1(String roundedNotionalAmount1) {
		this.roundedNotionalAmount1 = roundedNotionalAmount1;
	}

	public void setRoundedNotionalAmount2(String roundedNotionalAmount2) {
		this.roundedNotionalAmount2 = roundedNotionalAmount2;
	}

	public void setPaymentFrequency1(String paymentFrequency1) {
		this.paymentFrequency1 = paymentFrequency1;
	}

	public void setPaymentFrequency2(String paymentFrequency2) {
		this.paymentFrequency2 = paymentFrequency2;
	}

	public void setResetFrequency1(String resetFrequency1) {
		this.resetFrequency1 = resetFrequency1;
	}

	public void setResetFrequency2(String resetFrequency2) {
		this.resetFrequency2 = resetFrequency2;
	}

	public void setEmbeddedOption(String embeddedOption) {
		this.embeddedOption = embeddedOption;
	}

	public void setOptionStrikePrice(Double optionStrikePrice) {
		this.optionStrikePrice = optionStrikePrice;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public void setOptionFamily(String optionFamily) {
		this.optionFamily = optionFamily;
	}

	public void setOptionCurrency(String optionCurrency) {
		this.optionCurrency = optionCurrency;
	}

	public void setOptionPremium(Double optionPremium) {
		this.optionPremium = optionPremium;
	}

	public void setOptionLockPeriod(Date optionLockPeriod) {
		this.optionLockPeriod = optionLockPeriod;
	}

	public void setOptionExpirationDate(Date optionExpirationDate) {
		this.optionExpirationDate = optionExpirationDate;
	}

	public void setPriceNotation2Type(String priceNotation2Type) {
		this.priceNotation2Type = priceNotation2Type;
	}

	public void setPriceNotation2(Double priceNotation2) {
		this.priceNotation2 = priceNotation2;
	}

	public void setPriceNotation3Type(String priceNotation3Type) {
		this.priceNotation3Type = priceNotation3Type;
	}

	public void setPriceNotation3(Double priceNotation3) {
		this.priceNotation3 = priceNotation3;
	}

	

	public long getDisseminationID() {
		return disseminationID;
	}

	public long getOriginalDisseminationID() {
		return originalDisseminationID;
	}

	public Action getAction() {
		return action;
	}

	public Boolean isCleared() {
		return cleared;
	}

	public Date getExecutionTimestamp() {
		return executionTimestamp;
	}

	public Collateralization getCollateralization() {
		return collateralization;
	}

	public Boolean isEndUserException() {
		return endUserException;
	}

	public Boolean isBespoke() {
		return bespoke;
	}

	public Boolean isExecutionVenue() {
		return executionVenue;
	}

	public Boolean isBlockTrades() {
		return blockTrades;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getDayCountConvention() {
		return dayCountConvention;
	}

	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public AssetClass getAssetClass() {
		return assetClass;
	}

	public SubAssetClass getSubAssetClass() {
		return subAssetClass;
	}

	public UPI getTaxonomy() {
		return taxonomy;
	}

	public PriceFormingContinuationData getPriceFormingContinuationData() {
		return priceFormingContinuationData;
	}

	public String getUnderlyingAsset1() {
		return underlyingAsset1;
	}

	public String getUnderlyingAsset2() {
		return underlyingAsset2;
	}

	public String getPriceNotationType() {
		return priceNotationType;
	}

	public Double getPriceNotation() {
		return priceNotation;
	}

	public String getAdditionalPriceNotationType() {
		return additionalPriceNotationType;
	}

	public Double getAdditionalPriceNotation() {
		return additionalPriceNotation;
	}

	public String getNotionalCurrency1() {
		return notionalCurrency1;
	}

	public String getNotionalCurrency2() {
		return notionalCurrency2;
	}

	public String getRoundedNotionalAmount1() {
		return roundedNotionalAmount1;
	}

	public String getRoundedNotionalAmount2() {
		return roundedNotionalAmount2;
	}

	public String getPaymentFrequency1() {
		return paymentFrequency1;
	}

	public String getPaymentFrequency2() {
		return paymentFrequency2;
	}

	public String getResetFrequency1() {
		return resetFrequency1;
	}

	public String getResetFrequency2() {
		return resetFrequency2;
	}

	public String getEmbeddedOption() {
		return embeddedOption;
	}

	public Double getOptionStrikePrice() {
		return optionStrikePrice;
	}

	public String getOptionType() {
		return optionType;
	}

	public String getOptionFamily() {
		return optionFamily;
	}

	public String getOptionCurrency() {
		return optionCurrency;
	}

	public Double getOptionPremium() {
		return optionPremium;
	}

	public Date getOptionLockPeriod() {
		return optionLockPeriod;
	}

	public Date getOptionExpirationDate() {
		return optionExpirationDate;
	}

	public String getPriceNotation2Type() {
		return priceNotation2Type;
	}

	public Double getPriceNotation2() {
		return priceNotation2;
	}

	public String getPriceNotation3Type() {
		return priceNotation3Type;
	}

	public Double getPriceNotation3() {
		return priceNotation3;
	}

}
