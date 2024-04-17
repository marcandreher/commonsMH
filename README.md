# commonsMH - A superset for easy java tools and website developement
[![CodeFactor](https://www.codefactor.io/repository/github/marcandreher/commonsmh/badge/main)](https://www.codefactor.io/repository/github/marcandreher/commonsmh/overview/main) ![JitPack](https://img.shields.io/jitpack/version/com.github.marcandreher/commonsMH) ![GitHub License](https://img.shields.io/github/license/marcandreher/commonsMH) 

# Table of Contents

1. [Database connection](#database)
2. [Flogger](#flogger)
3. [WebServer](#webserver)
4. [Router](#route)
5. [Route types](#routetypes)
6. [RESTAPI route](#restapi)
7. [UploadHandler](#upload)
8. [Authentication](#auth)
9. [CacheTimers](#timers)
10. [GETRequest](#timers)
11. [Discord Webhook](#webhooks)
12. [SitemapGenerator](#sitemapgen)
13. [License](#license)
    
## Database connection <a name="database"></a>

```java
Database database = new Database();
database.setDefaultSettings(); // Adds auto reconnect to mysql connections and cachePrepStmts
database.setMaximumPoolSize(30); // Sets the pool size
database.setConnectionTimeout(3000); // Set the timeout
// You will need to provide a own cfg before
database.connectToMySQL(CONFIG.getServerIp(), CONFIG.getMySQLUserName(), CONFIG.getMySQLPassword(), CONFIG.getMySQLDatabase(), ServerTimezone.UTC); // Connect the Instance to the Server

// You now can pull connections with:
MySQL myConnection = Database.getConnection(); // throws SQLException

myConnection.Query("SELECT * FROM `users` WHERE `id` = ?", 2); // prepStatements in one Line
myConnection.Exec("DELETE FROM `users` WHERE `id` = ?", 2);

// You will need to close this after you finish
myConnection.close();

// Many things in commonsMH will provide you with an connection that will automaticly close
```

## License <a name="license"></a>

This project is licensed under the [MIT License](LICENSE).
