package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class FloatSQLField extends SQLField {
	
	private final Double fieldValue;

	public FloatSQLField(Double fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public String getType() {
		return "FLOAT";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		if (fieldValue == null) {
			p.setNull(index, Types.DOUBLE);
		} else {
			p.setDouble(index, fieldValue);
		}
	}

	
	
}
