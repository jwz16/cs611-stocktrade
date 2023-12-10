package edu.bu.cs611.portfoliostocksystem.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DatabaseClient is a wrapper class to help manage the SQLite database
 * @author Sophie <jwz16@bu.edu>
 */
public class DatabaseClient {
  
  private static Logger logger = LogManager.getLogger();

  private static final String DB_DIR = "data";
  private static final String DB_FILE_NAME = "portfolio_stock_sys.db";
  public static final String DB_FILE_PATH = DB_DIR + "/" + DB_FILE_NAME;
  private static final String DB_URL = "jdbc:sqlite:" + DB_FILE_PATH;

  public static void initDatabase() {
    Connection conn = null;
    try {
      new File("data").mkdirs();

      // create a database connection
      conn = DriverManager.getConnection(DB_URL);
      if (conn != null) {
        var meta = conn.getMetaData();
        logger.info("The driver name is " + meta.getDriverName());
        logger.info("The new database is created.");
      }
    } catch(SQLException e) {
      // if the error message is "out of memory",
      // it probably means no database file is found
      logger.error(e.getMessage());
    } finally {
      try {
        if(conn != null)
          conn.close();
      } catch(SQLException e) {
        // connection close failed.
        logger.error(e.getMessage());
      }
    }
  }

  public static Connection getConnection() {
    try {
      return DriverManager.getConnection(DB_URL);
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }

    return null;
  }

}
