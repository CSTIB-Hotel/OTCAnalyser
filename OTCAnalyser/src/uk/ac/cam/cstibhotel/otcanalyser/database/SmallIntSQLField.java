package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SmallIntSQLField extends SQLField {

	private final short fieldValue;

	public SmallIntSQLField(short fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public String getType() {
		return "SMALLINT";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		p.setShort(index, fieldValue);
	}
	
}
