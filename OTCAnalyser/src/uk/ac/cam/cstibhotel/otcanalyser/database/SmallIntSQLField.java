package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class SmallIntSQLField extends SQLField {

	private final Short fieldValue;

	public SmallIntSQLField(Short fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Override
	public String getType() {
		return "SMALLINT";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		if (fieldValue == null)
			p.setNull(index, Types.SMALLINT);
		else
			p.setShort(index, fieldValue);
	}
	
}
