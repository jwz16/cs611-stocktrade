package edu.bu.cs611.portfoliostocksystem.database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DatabaseClient is a wrapper class to help manage the SQLite database
 * @author Sophie <jwz16@bu.edu>
 */
public class DatabaseClient {
  
  private static Logger logger = LogManager.getLogger();

  private static final String DB_DIR = "data";
  private static final String PROD_DB_FILE_NAME = "portfolio_stock_sys_prod.db";
  public static final String PROD_DB_FILE_PATH = DB_DIR + "/" + PROD_DB_FILE_NAME;
  private static final String PROD_DB_URL = "jdbc:sqlite:" + PROD_DB_FILE_PATH;
  private static final String PROD_DB_USER = "admin";
  private static final String PROD_DB_PASS = "cs611fall2023";

  public static final String TBL_CUSTOMERS = "customers";
  public static final String TBL_MANAGERS = "managers";
  public static final String TBL_SECURITIES = "securities";
  public static final String TBL_CX_PERSONAL_ACCTS = "customer_personal_accounts";
  public static final String TBL_CX_TRADING_ACCTS = "customer_trading_accounts";
  public static final String TBL_CX_MSGS = "customer_messages";
  public static final String TBL_CX_TRADE_ORDERS = "customer_trade_orders";
  public static final String TBL_CX_OWNED_SECURITIES = "customer_owned_securities";

  public interface QueryResultSetHandler {
    public void handleResultSet(ResultSet rs);
  }

  public static boolean initDatabase() {
    return initDatabase(PROD_DB_URL, PROD_DB_USER, PROD_DB_PASS);
  }

  public static boolean initDatabase(String url, String user, String pass) {
    Connection conn = null;
    try {
      new File("data").mkdirs();

      // create a database connection
      conn = DriverManager.getConnection(url, user, pass);
      if (conn != null) {
        var meta = conn.getMetaData();
        logger.info("The driver name is " + meta.getDriverName());
        logger.info("The new database is created.");

        return initTables(url, user, pass);
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

    return false;
  }

  public static Connection getConnection(String url, String user, String pass) {
    try {
      return DriverManager.getConnection(url, user, pass);
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }

    return null; 
  }

  public static Connection getConnection() {
    return getConnection(PROD_DB_URL, PROD_DB_USER, PROD_DB_PASS);
  }

  private static boolean initTables(String url, String user, String pass) {
    logger.info("Initializing database tables ...");
    
    Connection conn = null;
    try {
      conn = getConnection(url, user, pass);
      if (conn == null) {
        logger.error("Database connection is null, cannot initialize tables");
        return false;
      }

      var stmt = conn.createStatement();
      stmt.setQueryTimeout(5);

      var clsLoader = DatabaseClient.class.getClassLoader();
      // Drop all tables before creation
      var dropTablesSql = IOUtils.toString(clsLoader.getResourceAsStream("drop_tables.sql"), StandardCharsets.UTF_8);
      stmt.executeUpdate(dropTablesSql);

      var createTablesSql = IOUtils.toString(clsLoader.getResourceAsStream("create_tables.sql"), StandardCharsets.UTF_8);
      stmt.executeUpdate(createTablesSql);

      return true;
    } catch (SQLException e) {
      logger.error(e.getMessage());
    } catch (IOException e) {
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

    return false;
  }

  public static int executeUpdate(String sql) {
    return executeUpdate(sql, PROD_DB_URL, PROD_DB_USER, PROD_DB_PASS);
  }

  public static void executeQuery(String sql, QueryResultSetHandler handler) {
    executeQuery(sql, PROD_DB_URL, PROD_DB_USER, PROD_DB_PASS, handler);
  }

  public static int executeUpdate(String sql, String url, String user, String pass) {
    Connection conn = null;
    try {
      conn = getConnection(url, user, pass);
      if (conn == null) {
        logger.error("Database connection is null, cannot execute the update");
        return 0;
      }

      var stmt = conn.createStatement();
      stmt.setQueryTimeout(5);

      return stmt.executeUpdate(sql);
    } catch (SQLException e) {
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

    return 0;
  }

  public static boolean executeQuery(String sql, String url, String user, String pass, QueryResultSetHandler handler) {
    Connection conn = null;
    try {
      conn = getConnection(url, user, pass);
      if (conn == null) {
        logger.error("Database connection is null, cannot execute the query");
        return false;
      }

      var stmt = conn.createStatement();
      stmt.setQueryTimeout(5);

      handler.handleResultSet(stmt.executeQuery(sql));
      return true;
    } catch (SQLException e) {
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

    return false;
  }

}
