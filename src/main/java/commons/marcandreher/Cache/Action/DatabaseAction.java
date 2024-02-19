package commons.marcandreher.Cache.Action;

import commons.marcandreher.Commons.Database;
import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;
import commons.marcandreher.Commons.MySQL;
import commons.marcandreher.Utils.Color;

public class DatabaseAction implements Action {

    public MySQL mysql;


    @Override
    public void executeAction(Flogger logger)  {
        try {
            mysql = Database.getConnection();
        } catch (Exception e) {
            logger.log(Prefix.ERROR, "-> Failed to start DatabaseAction " + Color.RED + this.toString() + Color.RESET, 0);
        }

    }

    
}
