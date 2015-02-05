package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.util.HashMap;

/**
 *
 * @author Wai-Wai Ng"
 */
public class TradeFieldMapping {

    public static HashMap<String, String> DBNameDBType;

    public static HashMap<String, String> getMapping() {

	if (DBNameDBType == null) {
	    DBNameDBType = new HashMap<>();
	    DBNameDBType.put("id", "INTEGER");
	    DBNameDBType.put("origId", "INTEGER");
	    DBNameDBType.put("action", "SMALLINT");
	    DBNameDBType.put("cleared", "BOOLEAN");
	    DBNameDBType.put("collat", "SMALLINT");
	    DBNameDBType.put("endUserException", "BOOLEAN");
	    DBNameDBType.put("bespoke", "BOOLEAN");
	    DBNameDBType.put("executionVenue", "BOOLEAN");
	    DBNameDBType.put("blockTrades", "BOOLEAN");
	    DBNameDBType.put("effectiveDate", "DATE");
	    DBNameDBType.put("endDate", "DATE");
	    DBNameDBType.put("dayCountConvention", "VARCHAR(255)");
	    DBNameDBType.put("settlementCurrency", "VARCHAR(3)");
	    DBNameDBType.put("tradeType", "SMALLINT");
	    DBNameDBType.put("assetClass", "SMALLINT");
	    DBNameDBType.put("subAssetClass", "VARCHAR(255)");
	    DBNameDBType.put("taxonomy", "VARCHAR(255)");
	    DBNameDBType.put("priceFormingContinuationData", "SMALLINT");
	    DBNameDBType.put("underlyingAsset1", "VARCHAR(255)");
	    DBNameDBType.put("underlyingAsset2", "VARCHAR(255)");
	    DBNameDBType.put("priceNotationType", "VARCHAR(255)");
	    DBNameDBType.put("priceNotation", "FLOAT");
	    DBNameDBType.put("additionalPriceNotationType", "VARCHAR(255)");
	    DBNameDBType.put("additionalPriceNotationType", "FLOAT");
	    DBNameDBType.put("notionalCurrency1", "VARCHAR(3)");
	    DBNameDBType.put("notionalCurrency2", "VARCHAR(3)");
	    DBNameDBType.put("roundedNotionalAmount1", "VARCHAR(255)");
	    DBNameDBType.put("roundedNotionalAmount2", "VARCHAR(255)");
	    DBNameDBType.put("paymentFrequency1", "VARCHAR(255)");
	    DBNameDBType.put("paymentFrequency2", "VARCHAR(255)");
	    DBNameDBType.put("resetFrequency1", "VARCHAR(255)");
	    DBNameDBType.put("resetFrequency2", "VARCHAR(255)");
	    DBNameDBType.put("embeddedOption", "VARCHAR(255)");
	    DBNameDBType.put("optionStrikePrice", "FLOAT");
	    DBNameDBType.put("optionType", "VARCHAR(255)");
	    DBNameDBType.put("optionFamily", "VARCHAR(255)");
	    DBNameDBType.put("optionCurrency", "VARCHAR(3)");
	    DBNameDBType.put("optionPremium", "FLOAT");
	    DBNameDBType.put("optionLockPeriod", "DATE");
	    DBNameDBType.put("optionExpirationDate", "DATE");
	    DBNameDBType.put("priceNotation2Type", "VARCHAR(255)");
	    DBNameDBType.put("priceNotation2", "FLOAT");
	    DBNameDBType.put("priceNotation3Type", "VARCHAR(255)");
	    DBNameDBType.put("priceNotation3", "FLOAT");
	}

	return DBNameDBType;
    }

}
