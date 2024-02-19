package commons.marcandreher.Commons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import commons.marcandreher.Commons.Flogger.Prefix;

public final class MySQL {

	public static int LOGLEVEL = 4;
	
	private Connection currentCon;

	public MySQL(Connection currentCon) {
		
		this.currentCon = currentCon;
	}

	public ResultSet Query(String sql, String... args) {
		try {
			PreparedStatement stmt = currentCon.prepareStatement(sql);
			for (int i = 0; i < args.length; i++)
				stmt.setString(i + 1, args[i]);
				Flogger.instance.log(Prefix.INFO, stmt.toString(), LOGLEVEL);
			return stmt.executeQuery();
		} catch (Exception ex) {

			return null;
		}
	}

	public ResultSet Query(String sql, List<String> args) {
		try {
			PreparedStatement stmt = currentCon.prepareStatement(sql);
			for (int i = 0; i < args.size(); i++)
				stmt.setString(i + 1, args.get(i));
				Flogger.instance.log(Prefix.INFO, stmt.toString(), LOGLEVEL);
			return stmt.executeQuery();
		} catch (Exception ex) {

			return null;
		}
	}

	public int Exec(String sql, String... args) {
	    try {
	        PreparedStatement stmt = currentCon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        for (int i = 0; i < args.length; i++)
	            stmt.setString(i + 1, args[i]);
	
	        stmt.execute();
	        ResultSet rs = stmt.getGeneratedKeys();
			Flogger.instance.log(Prefix.INFO, stmt.toString(), LOGLEVEL);
	        if (rs.next()) {
	            return rs.getInt(1);
	        } else {
	            return 0;
	        }
	    } catch (Exception ex) {
	  
	        return -1;
	    }
	}

	public void close() {

		try {
			Database.currentConnections--;
			currentCon.close();
		} catch (Exception ex) {
		}
	}

}