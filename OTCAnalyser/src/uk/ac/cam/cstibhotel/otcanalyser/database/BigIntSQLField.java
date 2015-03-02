package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class BigIntSQLField extends SQLField{
	
	private final Long fieldValue;

	public BigIntSQLField(Long fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public String getType() {
		return "BIGINT";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		if (fieldValue == null) {
			p.setNull(index, Types.BIGINT);
		} else {
			p.setLong(index, fieldValue);
		}		
	}
	
}
