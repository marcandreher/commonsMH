package commons.marcandreher.Input;

import java.sql.SQLException;

import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Commons.MySQL;

public class DatabaseCommand implements Command {

    public MySQL mysql;

    @Override
    public void executeAction(String[] args, Flogger logger) {

        if(Database.dataSource == null) {
            logger.log(Prefix.ERROR, "Database connection is not initialized", 0);
            return;
        }

        try {
            mysql = Database.getConnection();
        } catch (SQLException e) {
            logger.error(e);    
        }
    }

    @Override
    public String getAlias() {
        throw new UnsupportedOperationException("Unimplemented method 'getAlias'");
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Unimplemented method 'getDescription'");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }
    
}
