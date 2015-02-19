package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.gui.DataTable;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Action;
import uk.ac.cam.cstibhotel.otcanalyser.trade.AssetClass;
import uk.ac.cam.cstibhotel.otcanalyser.trade.PriceFormingContinuationData;
import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

public class DataTableTest {

  //tests addRowToStart method
	@Test
	public void testAddRow() {
		DataTable table = new DataTable(new ArrayList<Trade>());
		Trade[] trades = new Trade[10];
		for (int i = 0; i < trades.length; i++) {
			trades[i] = new Trade();
			trades[i].setAction(Action.CORRECT);
			trades[i].setAdditionalPriceNotation(i + 0.2);
			trades[i].setAssetClass(AssetClass.FOREX);
			trades[i].setBespoke(true);
			trades[i].setBlockTrades(true);
			trades[i].setCleared(true);
			trades[i].setDayCountConvention("String");
			trades[i].setDisseminationID(i);
			trades[i].setEffectiveDate(Calendar.getInstance().getTime());
			trades[i].setEmbeddedOption("Option");
			trades[i].setEndDate(Calendar.getInstance().getTime());
			trades[i].setEndUserException(false);
			trades[i].setExecutionTimestamp(Calendar.getInstance().getTime());
			trades[i].setExecutionVenue(false);
			trades[i].setNotionalCurrency1("Currency 1");
			trades[i].setNotionalCurrency2("Currency 2");
			trades[i].setOptionExpirationDate(Calendar.getInstance().getTime());
			trades[i].setOptionFamily("Family");
			trades[i].setOptionLockPeriod(Calendar.getInstance().getTime());
			trades[i].setOptionPremium(i + 0.5);
			trades[i].setOptionStrikePrice(i + 1.0);
			trades[i].setOptionType("Type");
			trades[i].setOriginalDisseminationID(190);
			trades[i].setPaymentFrequency1("Payment Frequency 1");
			trades[i].setPaymentFrequency2("Payment Frequency 2");
			trades[i].setPriceFormingContinuationData(PriceFormingContinuationData.NOVATION);
			trades[i].setPriceNotation(i + 2.5);
			trades[i].setPriceNotation2(i - 0.5);
			trades[i].setPriceNotation3(i - 0.25);
			trades[i].setPriceNotationType("Price Notatino Type");
			trades[i].setPriceNotation2Type("Price Notation Type");
			trades[i].setPriceNotation3Type("Price Notation Type");
			trades[i].setResetFrequency1("Reset Frequency 1");
			trades[i].setResetFrequency2("Reset Frequency 2");
			trades[i].setRoundedNotionalAmount1(1000);
			trades[i].setRoundedNotionalAmount2(130);
			trades[i].setUnderlyingAsset1("Underlying Asset 1");
			trades[i].setUnderlyingAsset2("Underlying Asset 2");
		}
		for (Trade t : trades) {
		  table.addRow(t);
		}
		for (int i = 0; i < trades.length; i++) {
			assertEquals(table.getModel().getValueAt(i, 0), trades[i].getDisseminationID());
			assertEquals(table.getModel().getValueAt(i, 1), trades[i].getOriginalDisseminationID());
			assertEquals(table.getModel().getValueAt(i, 2), trades[i].getAction());
			assertEquals(table.getModel().getValueAt(i, 3), trades[i].getExecutionTimestamp());
			assertEquals(table.getModel().getValueAt(i, 4), trades[i].isCleared());
			assertEquals(table.getModel().getValueAt(i, 5), trades[i].getCollateralization());
			assertEquals(table.getModel().getValueAt(i, 6), trades[i].isEndUserException());
			assertEquals(table.getModel().getValueAt(i, 7), trades[i].isBespoke());
			assertEquals(table.getModel().getValueAt(i, 8), trades[i].isExecutionVenue());
			assertEquals(table.getModel().getValueAt(i, 9), trades[i].isBlockTrades());
			assertEquals(table.getModel().getValueAt(i, 10), trades[i].getEffectiveDate());
			assertEquals(table.getModel().getValueAt(i, 11), trades[i].getEndDate());
			assertEquals(table.getModel().getValueAt(i, 12), trades[i].getDayCountConvention());
			assertEquals(table.getModel().getValueAt(i, 13), trades[i].getSettlementCurrency());
			assertEquals(table.getModel().getValueAt(i, 14), trades[i].getTradeType());
			assertEquals(table.getModel().getValueAt(i, 15), trades[i].getAssetClass());
			assertEquals(table.getModel().getValueAt(i, 16), trades[i].getTaxonomy().getSubProduct());
			assertEquals(table.getModel().getValueAt(i, 17), trades[i].getTaxonomy());
			assertEquals(table.getModel().getValueAt(i, 18), trades[i].getPriceFormingContinuationData());
			assertEquals(table.getModel().getValueAt(i, 19), trades[i].getUnderlyingAsset1());
			assertEquals(table.getModel().getValueAt(i, 20), trades[i].getUnderlyingAsset2());
			assertEquals(table.getModel().getValueAt(i, 21), trades[i].getPriceNotationType());
			assertEquals(table.getModel().getValueAt(i, 22), trades[i].getPriceNotation());
			assertEquals(table.getModel().getValueAt(i, 23), trades[i].getAdditionalPriceNotationType());
			assertEquals(table.getModel().getValueAt(i, 24), trades[i].getAdditionalPriceNotation());
			assertEquals(table.getModel().getValueAt(i, 25), trades[i].getNotionalCurrency1());
			assertEquals(table.getModel().getValueAt(i, 26), trades[i].getNotionalCurrency2());
			assertEquals(table.getModel().getValueAt(i, 27), trades[i].getRoundedNotionalAmount1());
			assertEquals(table.getModel().getValueAt(i, 28), trades[i].getRoundedNotionalAmount2());
			assertEquals(table.getModel().getValueAt(i, 29), trades[i].getPaymentFrequency1());
			assertEquals(table.getModel().getValueAt(i, 30), trades[i].getPaymentFrequency2());
			assertEquals(table.getModel().getValueAt(i, 31), trades[i].getResetFrequency1());
			assertEquals(table.getModel().getValueAt(i, 32), trades[i].getResetFrequency2());
			assertEquals(table.getModel().getValueAt(i, 33), trades[i].getEmbeddedOption());
			assertEquals(table.getModel().getValueAt(i, 34), trades[i].getOptionStrikePrice());
			assertEquals(table.getModel().getValueAt(i, 35), trades[i].getOptionType());
			assertEquals(table.getModel().getValueAt(i, 36), trades[i].getOptionFamily());
			assertEquals(table.getModel().getValueAt(i, 37), trades[i].getOptionCurrency());
			assertEquals(table.getModel().getValueAt(i, 38), trades[i].getOptionPremium());
			assertEquals(table.getModel().getValueAt(i, 39), trades[i].getOptionLockPeriod());
			assertEquals(table.getModel().getValueAt(i, 40), trades[i].getOptionExpirationDate());
			assertEquals(table.getModel().getValueAt(i, 41), trades[i].getPriceNotation2Type());
			assertEquals(table.getModel().getValueAt(i, 42), trades[i].getPriceNotation2());
			assertEquals(table.getModel().getValueAt(i, 43), trades[i].getPriceNotation3Type());
			assertEquals(table.getModel().getValueAt(i, 44), trades[i].getPriceNotation3());
		}
	}
	
	//tests addRowToStart method
	@Test
	public void testAddRowToStart() {
		DataTable table = new DataTable(new ArrayList<Trade>());
		Trade[] trades = new Trade[10];
		for (int i = 0; i < trades.length; i++) {
			trades[i] = new Trade();
			trades[i].setAction(Action.CORRECT);
			trades[i].setAdditionalPriceNotation(i + 0.2);
			trades[i].setAssetClass(AssetClass.FOREX);
			trades[i].setBespoke(true);
			trades[i].setBlockTrades(true);
			trades[i].setCleared(true);
			trades[i].setDayCountConvention("String");
			trades[i].setDisseminationID(i);
			trades[i].setEffectiveDate(Calendar.getInstance().getTime());
			trades[i].setEmbeddedOption("Option");
			trades[i].setEndDate(Calendar.getInstance().getTime());
			trades[i].setEndUserException(false);
			trades[i].setExecutionTimestamp(Calendar.getInstance().getTime());
			trades[i].setExecutionVenue(false);
			trades[i].setNotionalCurrency1("Currency 1");
			trades[i].setNotionalCurrency2("Currency 2");
			trades[i].setOptionExpirationDate(Calendar.getInstance().getTime());
			trades[i].setOptionFamily("Family");
			trades[i].setOptionLockPeriod(Calendar.getInstance().getTime());
			trades[i].setOptionPremium(i + 0.5);
			trades[i].setOptionStrikePrice(i + 1.0);
			trades[i].setOptionType("Type");
			trades[i].setOriginalDisseminationID(190);
			trades[i].setPaymentFrequency1("Payment Frequency 1");
			trades[i].setPaymentFrequency2("Payment Frequency 2");
			trades[i].setPriceFormingContinuationData(PriceFormingContinuationData.NOVATION);
			trades[i].setPriceNotation(i + 2.5);
			trades[i].setPriceNotation2(i - 0.5);
			trades[i].setPriceNotation3(i - 0.25);
			trades[i].setPriceNotationType("Price Notatino Type");
			trades[i].setPriceNotation2Type("Price Notation Type");
			trades[i].setPriceNotation3Type("Price Notation Type");
			trades[i].setResetFrequency1("Reset Frequency 1");
			trades[i].setResetFrequency2("Reset Frequency 2");
			trades[i].setRoundedNotionalAmount1(1000);
			trades[i].setRoundedNotionalAmount2(130);
			trades[i].setUnderlyingAsset1("Underlying Asset 1");
			trades[i].setUnderlyingAsset2("Underlying Asset 2");
		}
		for (Trade t : trades) {
		  table.addRowToStart(t);
		}
		for (int i = 0; i < trades.length; i++) {
			int j = trades.length - 1 - i;
			assertEquals(table.getModel().getValueAt(j, 0), trades[i].getDisseminationID());
			assertEquals(table.getModel().getValueAt(j, 1), trades[i].getOriginalDisseminationID());
			assertEquals(table.getModel().getValueAt(j, 2), trades[i].getAction());
			assertEquals(table.getModel().getValueAt(j, 3), trades[i].getExecutionTimestamp());
			assertEquals(table.getModel().getValueAt(j, 4), trades[i].isCleared());
			assertEquals(table.getModel().getValueAt(j, 5), trades[i].getCollateralization());
			assertEquals(table.getModel().getValueAt(j, 6), trades[i].isEndUserException());
			assertEquals(table.getModel().getValueAt(j, 7), trades[i].isBespoke());
			assertEquals(table.getModel().getValueAt(j, 8), trades[i].isExecutionVenue());
			assertEquals(table.getModel().getValueAt(j, 9), trades[i].isBlockTrades());
			assertEquals(table.getModel().getValueAt(j, 10), trades[i].getEffectiveDate());
			assertEquals(table.getModel().getValueAt(j, 11), trades[i].getEndDate());
			assertEquals(table.getModel().getValueAt(j, 12), trades[i].getDayCountConvention());
			assertEquals(table.getModel().getValueAt(j, 13), trades[i].getSettlementCurrency());
			assertEquals(table.getModel().getValueAt(j, 14), trades[i].getTradeType());
			assertEquals(table.getModel().getValueAt(j, 15), trades[i].getAssetClass());
			assertEquals(table.getModel().getValueAt(j, 16), trades[i].getTaxonomy().getSubProduct());
			assertEquals(table.getModel().getValueAt(j, 17), trades[i].getTaxonomy());
			assertEquals(table.getModel().getValueAt(j, 18), trades[i].getPriceFormingContinuationData());
			assertEquals(table.getModel().getValueAt(j, 19), trades[i].getUnderlyingAsset1());
			assertEquals(table.getModel().getValueAt(j, 20), trades[i].getUnderlyingAsset2());
			assertEquals(table.getModel().getValueAt(j, 21), trades[i].getPriceNotationType());
			assertEquals(table.getModel().getValueAt(j, 22), trades[i].getPriceNotation());
			assertEquals(table.getModel().getValueAt(j, 23), trades[i].getAdditionalPriceNotationType());
			assertEquals(table.getModel().getValueAt(j, 24), trades[i].getAdditionalPriceNotation());
			assertEquals(table.getModel().getValueAt(j, 25), trades[i].getNotionalCurrency1());
			assertEquals(table.getModel().getValueAt(j, 26), trades[i].getNotionalCurrency2());
			assertEquals(table.getModel().getValueAt(j, 27), trades[i].getRoundedNotionalAmount1());
			assertEquals(table.getModel().getValueAt(j, 28), trades[i].getRoundedNotionalAmount2());
			assertEquals(table.getModel().getValueAt(j, 29), trades[i].getPaymentFrequency1());
			assertEquals(table.getModel().getValueAt(j, 30), trades[i].getPaymentFrequency2());
			assertEquals(table.getModel().getValueAt(j, 31), trades[i].getResetFrequency1());
			assertEquals(table.getModel().getValueAt(j, 32), trades[i].getResetFrequency2());
			assertEquals(table.getModel().getValueAt(j, 33), trades[i].getEmbeddedOption());
			assertEquals(table.getModel().getValueAt(j, 34), trades[i].getOptionStrikePrice());
			assertEquals(table.getModel().getValueAt(j, 35), trades[i].getOptionType());
			assertEquals(table.getModel().getValueAt(j, 36), trades[i].getOptionFamily());
			assertEquals(table.getModel().getValueAt(j, 37), trades[i].getOptionCurrency());
			assertEquals(table.getModel().getValueAt(j, 38), trades[i].getOptionPremium());
			assertEquals(table.getModel().getValueAt(j, 39), trades[i].getOptionLockPeriod());
			assertEquals(table.getModel().getValueAt(j, 40), trades[i].getOptionExpirationDate());
			assertEquals(table.getModel().getValueAt(j, 41), trades[i].getPriceNotation2Type());
			assertEquals(table.getModel().getValueAt(j, 42), trades[i].getPriceNotation2());
			assertEquals(table.getModel().getValueAt(j, 43), trades[i].getPriceNotation3Type());
			assertEquals(table.getModel().getValueAt(j, 44), trades[i].getPriceNotation3());
		}
	}
	
}
