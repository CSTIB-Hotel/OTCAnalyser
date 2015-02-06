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
	private boolean cleared;
	private Collateralization collateralization;
	private boolean endUserException;
	private boolean bespoke;
	private boolean executionVenue; // true for ON, false for OFF
	private boolean blockTrades;
	private Date effectiveDate;
	private Date endDate;
	private String dayCountConvention; // e.g. ACT/360 and 1/1 30/360
	private Currency settlementCurrency;
	private TradeType tradeType;
	private AssetClass assetClass;
	private SubAssetClass subAssetClass;
	private UPI taxonomy;
	private PriceFormingContinuationData priceFormingContinuationData;
	private String underlyingAsset1;
	private String underlyingAsset2;
	private String priceNotationType;
	private double priceNotation;
	private String additionalPriceNotationType;
	private double additionalPriceNotation;
	private Currency notionalCurrency1;
	private Currency notionalCurrency2;
	private String roundedNotionalAmount1; //probably shouldn't be a long, since some values are e.g. 95,000,000+
	private String roundedNotionalAmount2; //also has +
	private String paymentFrequency1; //e.g. 3M, 1M, 0, but also dates like 2016-01-29
	private String paymentFrequency2; //e.g. 1D, 1T, 1M
	private String resetFrequency1; //e.g. 2024-10-10
	private String resetFrequency2; //e.g. 1D, 1M, 1T
	private String embeddedOption; //e.g. EMBED1

	// Options
	private double optionStrikePrice;
	private String optionType;
	private String optionFamily;
	private Currency optionCurrency;
	private double optionPremium; //e.g. 0.00034 and 90250
	private Date optionLockPeriod;
	private Date optionExpirationDate;
	private String priceNotation2Type;
	private double priceNotation2;
	private String priceNotation3Type;
	private double priceNotation3;

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
