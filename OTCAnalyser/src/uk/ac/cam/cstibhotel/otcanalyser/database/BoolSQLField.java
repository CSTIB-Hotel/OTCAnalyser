package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BoolSQLField extends SQLField {

	private final Boolean fieldValue;

	public BoolSQLField(Boolean fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public String getType() {
		return "BOOLEAN";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		if (fieldValue == null) {
			p.setNull(index, Types.BOOLEAN);
		} else {
			p.setBoolean(index, fieldValue);
		}
	}
	
}
