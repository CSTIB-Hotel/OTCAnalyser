package uk.ac.cam.cstibhotel.otcanalyser.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SQLField {
	
	public int index;
	
	abstract public String getType();
	
	abstract public void addToPreparedStatement(PreparedStatement p) throws SQLException;
		
}
