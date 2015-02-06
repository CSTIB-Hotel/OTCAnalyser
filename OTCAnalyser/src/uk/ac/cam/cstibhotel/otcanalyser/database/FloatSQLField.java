package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FloatSQLField extends SQLField {
	
	private final double fieldValue;

	public FloatSQLField(double fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public String getType() {
		return "FLOAT";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		p.setDouble(index, fieldValue);
	}

	
	
}
