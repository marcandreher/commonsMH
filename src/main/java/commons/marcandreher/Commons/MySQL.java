package commons.marcandreher.Commons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import commons.marcandreher.Commons.Flogger.Prefix;

public final class MySQL {

	public static int LOGLEVEL = 4;
	private final int COLUMN_WIDTH = 20;

    private void printResultSet(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.printf("%-" + COLUMN_WIDTH + "s", columnName);
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.printf("%-" + COLUMN_WIDTH + "s", columnValue);
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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

	public void printQuery(String sql, String... args) {
		ResultSet rs = Query(sql, args);
		printResultSet(rs);
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
			for (int i = 0; i < args.length; i++) {
				stmt.setString(i + 1, args[i]);
			}
	
			int rowsAffected = stmt.executeUpdate();
			Flogger.instance.log(Prefix.INFO, stmt.toString(), LOGLEVEL);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int generatedKey = rs.getInt(1);
				return generatedKey;
			} else {
				return rowsAffected;
			}
		} catch (Exception ex) {
			Flogger.instance.log(Prefix.ERROR, ex.getMessage(), 0);
			return -1;
		}
	}
	

	public void close() {
		try {
			if (!currentCon.isClosed()) {
				Database.currentConnections--;
				currentCon.close();
			}
		} catch (Exception ex) {
			Flogger.instance.log(Prefix.ERROR, "Failed to close connection", 0);
		}

	}

}