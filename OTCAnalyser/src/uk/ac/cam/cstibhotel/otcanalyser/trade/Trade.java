package uk.ac.cam.cstibhotel.otcanalyser.trade;

import java.util.Currency;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Trade {

	// The fields in here are a direct copy of the fields of a trade taken
	// from the OTC repository
	protected long disseminationID;
	protected long originalDisseminationID;
	protected Action action;
	protected Date executionTimestamp;
	protected boolean cleared;
	protected Collateralization collateralization;
	protected boolean endUserException;
	protected boolean bespoke;
	protected boolean executionVenue; // true for ON, false for OFF
	protected boolean blockTrades;
	protected Date effectiveDate;
	protected Date endDate;
	protected String dayCountConvention; // e.g. ACT/360 and 1/1 30/360
	protected Currency settlementCurrency;
	protected TradeType tradeType;
	protected AssetClass assetClass;
	protected SubAssetClass subAssetClass;
	protected UPI taxonomy;
	protected PriceFormingContinuationData priceFormingContinuationData;
	protected String underlyingAsset1;
	protected String underlyingAsset2;
	protected String priceNotationType;
	protected double priceNotation;
	protected String additionalPriceNotationType;
	protected double additionalPriceNotation;
	protected Currency notionalCurrency1;
	protected Currency notionalCurrency2;
	protected String roundedNotionalAmount1; //probably shouldn't be a long, since some values are e.g. 95,000,000+
	protected String roundedNotionalAmount2; //also has +
	protected String paymentFrequency1; //e.g. 3M, 1M, 0, but also dates like 2016-01-29
	protected String paymentFrequency2; //e.g. 1D, 1T, 1M
	protected String resetFrequency1; //e.g. 2024-10-10
	protected String resetFrequency2; //e.g. 1D, 1M, 1T
	protected String embeddedOption; //e.g. EMBED1

	// Options
	protected double optionStrikePrice;
	protected String optionType;
	protected String optionFamily;
	protected Currency optionCurrency;
	protected double optionPremium; //e.g. 0.00034 and 90250
	protected Date optionLockPeriod;
	protected Date optionExpirationDate;
	protected String priceNotation2Type;
	protected double priceNotation2;
	protected String priceNotation3Type;
	protected double priceNotation3;

	public Trade() {
		// Sets defaults on all enum and objects
		// Necessary to avoid a lot of try-catch for NPEs in Database
		
		action = Action.NEW;
		executionTimestamp = new Date();
		collateralization = Collateralization.BLANK;
		effectiveDate = new Date();
		endDate = new Date();
		settlementCurrency = Currency.getInstance("GBP");
		tradeType = TradeType.OPTION;
		assetClass = AssetClass.Commodity;
		subAssetClass = new SubAssetClass();
		try {
			taxonomy = new UPI("Rates:Foo:Bar");
		} catch (InvalidTaxonomyException ex) {
			Logger.getLogger(Trade.class.getName()).log(Level.SEVERE, null, ex);
		}
		priceFormingContinuationData = PriceFormingContinuationData.TRADE;
		notionalCurrency1 = Currency.getInstance("GBP");
		notionalCurrency2 = Currency.getInstance("GBP");
		optionCurrency = Currency.getInstance("GBP");
		optionLockPeriod = new Date();
		optionExpirationDate = new Date();
		
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

	public boolean isCleared() {
		return cleared;
	}

	public Date getExecutionTimestamp() {
		return executionTimestamp;
	}

	public Collateralization getCollateralization() {
		return collateralization;
	}

	public boolean isEndUserException() {
		return endUserException;
	}

	public boolean isBespoke() {
		return bespoke;
	}

	public boolean isExecutionVenue() {
		return executionVenue;
	}

	public boolean isBlockTrades() {
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

	public Currency getSettlementCurrency() {
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

	public double getPriceNotation() {
		return priceNotation;
	}

	public String getAdditionalPriceNotationType() {
		return additionalPriceNotationType;
	}

	public double getAdditionalPriceNotation() {
		return additionalPriceNotation;
	}

	public Currency getNotionalCurrency1() {
		return notionalCurrency1;
	}

	public Currency getNotionalCurrency2() {
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

	public double getOptionStrikePrice() {
		return optionStrikePrice;
	}

	public String getOptionType() {
		return optionType;
	}

	public String getOptionFamily() {
		return optionFamily;
	}

	public Currency getOptionCurrency() {
		return optionCurrency;
	}

	public double getOptionPremium() {
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

	public double getPriceNotation2() {
		return priceNotation2;
	}

	public String getPriceNotation3Type() {
		return priceNotation3Type;
	}

	public double getPriceNotation3() {
		return priceNotation3;
	}

}
