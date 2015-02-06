package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VarCharSQLField extends SQLField{
	
	private final int size;
	private final String fieldValue;

	public VarCharSQLField(int size, String fieldValue) {
		this.size = size;
		if(fieldValue == null){
			this.fieldValue = "";
		} else {
			this.fieldValue = fieldValue.substring(0, Math.min(size, fieldValue.length()));
		}
	}

	@Override
	public String getType() {
		return "VARCHAR(" + size + ")"; 
	}

	@Override
	public void addToPreparedStatement(PreparedStatement p) throws SQLException {
		p.setString(index, fieldValue);
	}
	
}
