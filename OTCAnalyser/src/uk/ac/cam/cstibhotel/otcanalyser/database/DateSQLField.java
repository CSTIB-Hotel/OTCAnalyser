package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class DateSQLField extends SQLField{
	
	private final Date fieldValue;

	public DateSQLField(java.util.Date fieldValue) {
		this.fieldValue = new java.sql.Date(fieldValue.getTime());
	}

	@Override
	public String getType() {
		return "DATE";
	}	

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		p.setDate(index, fieldValue);
	}
	
}
