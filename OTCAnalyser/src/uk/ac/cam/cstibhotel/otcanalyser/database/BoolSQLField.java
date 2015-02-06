package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoolSQLField extends SQLField {

	private final boolean fieldValue;

	public BoolSQLField(boolean fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public String getType() {
		return "BOOLEAN";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		p.setBoolean(index, fieldValue);
	}
	
}
