package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class TimestampSQLField extends SQLField{

	private final Timestamp fieldValue;

	public TimestampSQLField(long fieldValue) {
		this.fieldValue = new Timestamp(fieldValue);
	}
	
	@Override
	public String getType() {
		return "TIMESTAMP";
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		if (fieldValue == null)
			p.setNull(index, Types.TIMESTAMP);
		else
			p.setTimestamp(index, fieldValue);
	}
	
}
