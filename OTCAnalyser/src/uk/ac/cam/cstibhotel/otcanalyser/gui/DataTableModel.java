package uk.ac.cam.cstibhotel.otcanalyser.gui;

import uk.ac.cam.cstibhotel.otcanalyser.trade.Trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

class DataTableModel extends AbstractTableModel{
  private static final long serialVersionUID = 1L;
  public static final String[] columnNames = {
    "Dissemination ID",
    "Original Dissemination ID",
    "Action",
    "Execution Timestamp",
    "Cleared",
    "Collateralization",
    "End User Exception",
    "Bespoke",
    "Execution Venue",
    "Block Trades",
    "Effective Date",
    "Maturity Date",
    "Day Count Convention",
    "Settlement Currency",
    "Trade Type",
    "Asset Class",
    "Subasset Class",
    "UPI/Taxonomy",
    "Price Forming Continuation Data",
    "Underlying Asset 1",
    "Underlying Asset 2",
    "Price Notation Type",
    "Price Notation",
    "Additional Price Notation Type",
    "Additional Price Notation",
    "Notional Currency 1",
    "Notional Currency 2",
    "Rounded Notional Amount 1",
    "Rounded Notional Amount 2",
    "Payment Frequency 1",
    "Payment Frequency 2",
    "Reset Frequency 1",
    "Reset Frequency 2",
    "Embedded Option",
    "Option Strike Price",
    "Option Type",
    "Option Family",
    "Option Currency",
    "Option Premium",
    "Option Lock Period",
    "Option Expiration Date",
    "Price Notation 2 Type",
    "Price Notation 2",
    "Price Notation 3 Type",
    "Price Notation 3"
  };
  
  private final List<Object[]> data;
            
  public DataTableModel(List<Trade> trades){
    data = new ArrayList<>(trades.size());
    for (Trade t : trades){
      addRow(t, false);
    }
  }
            
  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public int getRowCount() {
    return data.size();
  }
            
  @Override
  public int getColumnCount() {
    return columnNames.length;
  }
  
  //make sure date columns sorted correctly (not alphabetically)
  @Override
  public Class<?> getColumnClass(int columnIndex) {
  	if (!data.isEmpty() && data.get(0)[columnIndex] instanceof Date) {
  	  return Date.class;
  	} else { 
  		return super.getColumnClass(columnIndex);
  	}
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return data.get(rowIndex)[columnIndex];
  }
  
  public final void addRow(Trade t, boolean toBeginning){
    Object[] row = new Object[columnNames.length];
    //fill row
    row[0] = t.getDisseminationID();
    row[1] = t.getOriginalDisseminationID();
    row[2] = t.getAction();
    row[3] = t.getExecutionTimestamp();
    row[4] = t.isCleared();
    row[5] = t.getCollateralization();
    row[6] = t.isEndUserException();
    row[7] = t.isBespoke();
    row[8] = t.isExecutionVenue();
    row[9] = t.isBlockTrades();
    row[10] = t.getEffectiveDate();
    row[11] = t.getEndDate();
    row[12] = t.getDayCountConvention();
    row[13] = t.getSettlementCurrency();
    row[14] = t.getTradeType();
    row[15] = t.getAssetClass();
    row[16] = t.getTaxonomy().getSubProduct();
    row[17] = t.getTaxonomy().getFullTaxonomy();
    row[18] = t.getPriceFormingContinuationData();
    row[19] = t.getUnderlyingAsset1();
    row[20] = t.getUnderlyingAsset2();
    row[21] = t.getPriceNotationType();
    row[22] = t.getPriceNotation();
    row[23] = t.getAdditionalPriceNotationType();
    row[24] = t.getAdditionalPriceNotation();
    row[25] = t.getNotionalCurrency1();
    row[26] = t.getNotionalCurrency2();
    row[27] = t.getRoundedNotionalAmount1();
    row[28] = t.getRoundedNotionalAmount2();
    row[29] = t.getPaymentFrequency1();
    row[30] = t.getPaymentFrequency2();
    row[31] = t.getResetFrequency1();
    row[32] = t.getResetFrequency2();
    row[33] = t.getEmbeddedOption();
    row[34] = t.getOptionStrikePrice();
  	row[35] = t.getOptionType();
  	row[36] = t.getOptionFamily();
  	row[37] = t.getOptionCurrency();
  	row[38] = t.getOptionPremium();
  	row[39] = t.getOptionLockPeriod();
  	row[40] = t.getOptionExpirationDate();
  	row[41] = t.getPriceNotation2Type();
  	row[42] = t.getPriceNotation2();
  	row[43] = t.getPriceNotation3Type();
  	row[44] = t.getPriceNotation3();
    if (toBeginning) {
      data.add(0, row);
    } else {
      data.add(row);
    }
  }
    
}
