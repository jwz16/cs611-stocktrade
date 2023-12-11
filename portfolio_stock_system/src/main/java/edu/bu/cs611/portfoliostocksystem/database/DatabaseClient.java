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

  private static final String TEST_DB_FILE_NAME = "portfolio_stock_sys_test.db";
  public static final String TEST_DB_FILE_PATH = DB_DIR + "/" + TEST_DB_FILE_NAME;
  private static final String TEST_DB_URL = "jdbc:sqlite:" + TEST_DB_FILE_PATH;
  private static final String TEST_DB_USER = "admin";
  private static final String TEST_DB_PASS = "cs611fall2023";

  private boolean testMode = false;

  public interface QueryResultSetHandler {
    public void handleResultSet(ResultSet rs);
  }

  public interface PostUpdateHandler {
    public void handlePostUpdate(Statement stmt);
  }

  public DatabaseClient() { /* EMPTY CONSTRUCTOR */ }

  public DatabaseClient(boolean testMode) {
    this.testMode = testMode;
  }

  public boolean initDatabase() {
    if (testMode) {
      return initDatabase(TEST_DB_URL, TEST_DB_USER, TEST_DB_PASS);
    } else {
      return initDatabase(PROD_DB_URL, PROD_DB_USER, PROD_DB_PASS);
    }
  }

  public boolean initDatabase(String url, String user, String pass) {
    Connection conn = null;
    boolean success = false;
    try {
      new File("data").mkdirs();

      // create a database connection
      conn = DriverManager.getConnection(url, user, pass);
      if (conn != null) {
        var meta = conn.getMetaData();
        logger.info("The driver name is " + meta.getDriverName());
        logger.info("The new database is created.");

        success = initTables(url, user, pass);
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

    return success;
  }

  public Connection getConnection(String url, String user, String pass) {
    try {
      return DriverManager.getConnection(url, user, pass);
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }

    return null; 
  }

  public Connection getConnection() {
    if (testMode) {
      return getConnection(TEST_DB_URL, TEST_DB_USER, TEST_DB_PASS);
    } else {
      return getConnection(PROD_DB_URL, PROD_DB_USER, PROD_DB_PASS);
    }
  }

  private boolean initTables(String url, String user, String pass) {
    logger.info("Initializing database tables ...");
    
    Connection conn = null;
    boolean success = false;
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

      success = true;
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

    return success;
  }

  public int executeUpdate(String sql) {
    return executeUpdate(sql, null);
  }

  public int executeUpdate(String sql, PostUpdateHandler handler) {
    if (testMode) {
      return executeUpdate(sql, TEST_DB_URL, TEST_DB_USER, TEST_DB_PASS, handler);
    } else {
      return executeUpdate(sql, PROD_DB_URL, PROD_DB_USER, PROD_DB_PASS, handler);
    }
  }

  public void executeQuery(String sql, QueryResultSetHandler handler) {
    if (testMode) {
      executeQuery(sql, TEST_DB_URL, TEST_DB_USER, TEST_DB_PASS, handler);
    } else {
      executeQuery(sql, PROD_DB_URL, PROD_DB_USER, PROD_DB_PASS, handler);
    }
  }

  public int executeUpdate(String sql, String url, String user, String pass, PostUpdateHandler handler) {
    Connection conn = null;
    int affectedRows = 0;
    try {
      conn = getConnection(url, user, pass);
      if (conn == null) {
        logger.error("Database connection is null, cannot execute the update");
        return 0;
      }

      var stmt = conn.createStatement();
      stmt.setQueryTimeout(5);

      affectedRows = stmt.executeUpdate(sql);

      if (handler != null)
        handler.handlePostUpdate(stmt);
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

    return affectedRows;
  }

  public boolean executeQuery(String sql, String url, String user, String pass, QueryResultSetHandler handler) {
    Connection conn = null;
    boolean success = false;
    try {
      conn = getConnection(url, user, pass);
      if (conn == null) {
        logger.error("Database connection is null, cannot execute the query");
        return false;
      }

      var stmt = conn.createStatement();
      stmt.setQueryTimeout(5);

      if (handler != null)
        handler.handleResultSet(stmt.executeQuery(sql));
      success = true;
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

    return success;
  }

  public boolean isTestMode() {
    return testMode;
  }

  public void setTestMode(boolean testMode) {
    this.testMode = testMode;
  }

}
