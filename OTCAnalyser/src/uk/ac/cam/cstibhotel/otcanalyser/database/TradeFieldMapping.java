package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

/**
 *
 * @author Wai-Wai Ng"
 */
public class TradeFieldMapping {

	private Timestamp strToTimeStamp(String s) {
		// this is the ISO 8601 format used by RTDATA.DTCC.COM
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			Timestamp t = new Timestamp(d.parse(s).getTime());
			return t;
		} catch (ParseException ex) {
			Logger.getLogger(TradeFieldMapping.class.getName()).log(Level.WARNING, "Failed to parse timestamp", ex);
			return null;
		}
	}

	public static HashMap<String, SQLField> getMapping(Trade t) {
		HashMap<String, SQLField> DBNameDBType = new HashMap<>();
		DBNameDBType.put("id", new BigIntSQLField(t.getDisseminationID()));
		DBNameDBType.put("origId", new BigIntSQLField(t.getOriginalDisseminationID()));
		DBNameDBType.put("action", new SmallIntSQLField(t.getAction().getValue()));
		
		DBNameDBType.put("executionTime", (t.getExecutionTimestamp() == null) ? null : 
			new TimestampSQLField(t.getExecutionTimestamp().getTime()));
		DBNameDBType.put("cleared", new BoolSQLField(t.isCleared()));
		DBNameDBType.put("collat", new SmallIntSQLField(t.getCollateralization().getValue()));
		DBNameDBType.put("endUserException", new BoolSQLField(t.isEndUserException()));
		DBNameDBType.put("bespoke", new BoolSQLField(t.isBespoke()));
		DBNameDBType.put("executionVenue", new BoolSQLField(t.isExecutionVenue()));
		DBNameDBType.put("blockTrades", new BoolSQLField(t.isBlockTrades()));
		DBNameDBType.put("effectiveDate", new DateSQLField(t.getEffectiveDate()));
		DBNameDBType.put("endDate", new DateSQLField(t.getEndDate()));
		DBNameDBType.put("dayCountConvention", new VarCharSQLField(255, t.getDayCountConvention()));
		DBNameDBType.put("settlementCurrency", new VarCharSQLField(20, t.getSettlementCurrency()));
		DBNameDBType.put("tradeType", new SmallIntSQLField(t.getTradeType().getValue()));
		DBNameDBType.put("assetClass", new SmallIntSQLField(t.getAssetClass().getValue()));
		DBNameDBType.put("subAssetClass", new VarCharSQLField(255, t.getSubAssetClass().toString())); // TODO write subassetclass
		DBNameDBType.put("taxonomy", new VarCharSQLField(255, t.getTaxonomy().toString())); // TODO write UPI
		if(t.getPriceFormingContinuationData() != null){ // TODO fix this
			DBNameDBType.put("priceFormingContinuationData", new SmallIntSQLField(t.getPriceFormingContinuationData().getValue()));
		}
		DBNameDBType.put("underlyingAsset1", new VarCharSQLField(255, t.getUnderlyingAsset1()));
		DBNameDBType.put("underlyingAsset2", new VarCharSQLField(255, t.getUnderlyingAsset2()));
		DBNameDBType.put("priceNotationType", new VarCharSQLField(255, t.getPriceNotationType()));
		DBNameDBType.put("priceNotation", new FloatSQLField(t.getPriceNotation()));
		DBNameDBType.put("additionalPriceNotationType", new VarCharSQLField(255, t.getAdditionalPriceNotationType()));
		DBNameDBType.put("additionalPriceNotation", new FloatSQLField(t.getAdditionalPriceNotation()));
		DBNameDBType.put("notionalCurrency1", new VarCharSQLField(20, t.getNotionalCurrency1()));
		DBNameDBType.put("notionalCurrency2", new VarCharSQLField(20, t.getNotionalCurrency2()));
		DBNameDBType.put("roundedNotionalAmount1", new VarCharSQLField(255, t.getRoundedNotionalAmount1()));
		DBNameDBType.put("roundedNotionalAmount2", new VarCharSQLField(255, t.getRoundedNotionalAmount2()));
		DBNameDBType.put("paymentFrequency1", new VarCharSQLField(255, t.getPaymentFrequency1()));
		DBNameDBType.put("paymentFrequency2", new VarCharSQLField(255, t.getPaymentFrequency2()));
		DBNameDBType.put("resetFrequency1", new VarCharSQLField(255, t.getResetFrequency1()));
		DBNameDBType.put("resetFrequency2", new VarCharSQLField(255, t.getResetFrequency2()));
		DBNameDBType.put("embeddedOption", new VarCharSQLField(255, t.getEmbeddedOption()));
		DBNameDBType.put("optionStrikePrice", new FloatSQLField(t.getOptionStrikePrice()));
		DBNameDBType.put("optionType", new VarCharSQLField(255, t.getOptionType()));
		DBNameDBType.put("optionFamily", new VarCharSQLField(255, t.getOptionFamily()));
		DBNameDBType.put("optionCurrency", new VarCharSQLField(20, t.getOptionCurrency()));
		DBNameDBType.put("optionPremium", new FloatSQLField(t.getOptionPremium()));
		DBNameDBType.put("optionLockPeriod", new DateSQLField(t.getOptionLockPeriod()));
		DBNameDBType.put("optionExpirationDate", new DateSQLField(t.getOptionExpirationDate()));
		DBNameDBType.put("priceNotation2Type", new VarCharSQLField(255, t.getPriceNotation2Type()));
		DBNameDBType.put("priceNotation2", new FloatSQLField(t.getPriceNotation2()));
		DBNameDBType.put("priceNotation3Type", new VarCharSQLField(255, t.getPriceNotation3Type()));
		DBNameDBType.put("priceNotation3", new FloatSQLField(t.getPriceNotation3()));

		return DBNameDBType;
	}

}
