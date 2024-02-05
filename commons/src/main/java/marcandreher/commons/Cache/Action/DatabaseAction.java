package marcandreher.commons.Cache.Action;


import marcandreher.commons.Commons.Database;
import marcandreher.commons.Commons.Flogger;
import marcandreher.commons.Commons.Flogger.Prefix;
import marcandreher.commons.Utils.Color;
import marcandreher.commons.Commons.MySQL;

public class DatabaseAction implements Action {

    private MySQL mySQL;

    public void closeDb() {
        mySQL.close();
    }

    @Override
    public void executeAction(Flogger logger)  {
        try {
            mySQL = Database.getConnection();
        } catch (Exception e) {
            logger.log(Prefix.ERROR, "-> Failed to start DatabaseAction " + Color.RED + this.toString(), 0);
        }

    }
    
}
