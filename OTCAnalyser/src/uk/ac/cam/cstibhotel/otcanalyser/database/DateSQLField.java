package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Types;

public class DateSQLField extends SQLField{
	
	private final Date fieldValue;

	public DateSQLField(java.util.Date fieldValue) {
		if (fieldValue == null)
			this.fieldValue = null;
		else
			this.fieldValue = new java.sql.Date(fieldValue.getTime());
	}

	@Override
	public String getType() {
		return "DATE";
	}	

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		if (fieldValue == null)
			p.setNull(index, Types.DATE);
		else
			p.setDate(index, fieldValue);
	}
	
}
