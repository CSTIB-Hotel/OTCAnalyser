package uk.ac.cam.cstibhotel.otcanalyser.trade;

import java.util.Currency;
import java.util.Date;

public class Trade {
	
	// The fields in here are a direct copy of the fields of a trade taken
	// from the OTC repository
	
	long disseminationID;
	long originalDisseminationID;
	Action action;
	boolean cleared;
	Collateralization collateralization;
	boolean endUserException;
	boolean bespoke;
	boolean executionVenue; // true for ON, false for OFF
	boolean blockTrades;
	Date effectiveDate;
	Date endDate;
	String dayCountConvention; // e.g. ACT/360 and 1/1 30/360
	Currency settlementCurrency;
	TradeType tradeType;
	AssetClass assetClass;
	SubAssetClass subAssetClass;
	UPI taxonomy;
	PriceFormingContinuationData priceFormingContinuationData;
	String underlyingAsset1;
	String underlyingAsset2;
	String priceNotationType;
	double priceNotation;
	String additionalPriceNotationType;
	double additionalpriceNotation;
	Currency notionalCurrency1;
	Currency notionalCurrency2;
	long roundedNotionalAmount1; //probably shouldn't be a long, since some values are e.g. 95,000,000+
	long roundedNotionalAmount2; //also has +
	String paymentFrequency1; //e.g. 3M, 1M, 0, but also dates like 2016-01-29
	String paymentFrequency2; //e.g. 1D, 1T, 1M
	String resetFrequency1; //e.g. 2024-10-10
	String resetFrequency2; //e.g. 1D, 1M, 1T
	String embeddedOption; //e.g. EMBED1
	
	// Options
	double optionStrikePrice;
	String optionType;
	String optionFamily;
	Currency optionCurrency;
	double optionPremium; //e.g. 0.00034 and 90250
	Date optionLockPeriod;
	Date optionExpirationDate;
	String priceNotation2Type;
	double priceNotation2;
	String priceNotation3Type;
	double priceNotation3;
}
