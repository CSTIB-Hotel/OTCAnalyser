package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BigIntSQLField extends SQLField{
	
	private final long fieldValue;

	public BigIntSQLField(long fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public String getType() {
		return "BIGINT";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		p.setLong(index, fieldValue);		
	}
	
}
