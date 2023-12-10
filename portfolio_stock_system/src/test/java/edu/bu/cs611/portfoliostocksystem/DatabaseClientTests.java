package edu.bu.cs611.portfoliostocksystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient.QueryResultSetHandler;

public class DatabaseClientTests {
  
  private static final String DB_DIR = "data";
  private static final String TEST_DB_FILE_NAME = "portfolio_stock_sys_test.db";
  public static final String TEST_DB_FILE_PATH = DB_DIR + "/" + TEST_DB_FILE_NAME;
  private static final String TEST_DB_URL = "jdbc:sqlite:" + TEST_DB_FILE_PATH;
  private static final String TEST_DB_USER = "admin";
  private static final String TEST_DB_PASS = "cs611fall2023";
  
  private static void createTestDatabase() {
    var success = DatabaseClient.initDatabase(TEST_DB_URL, TEST_DB_USER, TEST_DB_PASS);
    assertTrue(success);
  }

  private static void deleteTestDatabase() {
    try {
      Files.deleteIfExists(Paths.get(TEST_DB_FILE_PATH));
    } catch (IOException e) {
      assertTrue(false);
    }
  }

  private static int executeUpdate(String sql) {
    return DatabaseClient.executeUpdate(sql, TEST_DB_URL, TEST_DB_USER, TEST_DB_PASS);
  }

  private static void executeQuery(String sql, QueryResultSetHandler handler) {
    DatabaseClient.executeQuery(sql, TEST_DB_URL, TEST_DB_USER, TEST_DB_PASS, handler);
  }

  @BeforeAll
  static void setup() {
    createTestDatabase();
  }

  @AfterAll
  static void teardown() {
    deleteTestDatabase();
  }

  @Test
  @DisplayName("Test Database creation")
  void testDatabaseCreation() {
    assertTrue(new File(TEST_DB_FILE_PATH).exists());
  }

  @Test
  @DisplayName("Test get database connection")
  void testGetDatabaseConnection() {
    var conn = DatabaseClient.getConnection(TEST_DB_URL, TEST_DB_USER, TEST_DB_PASS);
    assertNotNull(conn);

    try {
      conn.close();
    } catch (SQLException e) {
      assertTrue(false);
    }
  }

  @Test
  @DisplayName("Test Database tables are all created")
  void testDatabaseTablesCreated() {
    var sql = "SELECT name FROM sqlite_master WHERE type='table' AND name!='sqlite_sequence'";

    List<String> tables = new ArrayList<>();
    executeQuery(sql, rs -> {
      try {
        while (rs.next()) {
          tables.add(rs.getString("name"));
        }
      } catch (SQLException e) {
        e.printStackTrace();
        assertTrue(false);
      }
    });

    tables.sort(null);
    String[] wantTablesArr = {
      "customers",
      "managers",
      "securities",
      "customer_messages",
      "customer_owned_securities",
      "customer_personal_accounts",
      "customer_trading_accounts",
      "customer_trade_orders"
    };

    var wantTables = Arrays.asList(wantTablesArr);
    wantTables.sort(null);


    assertTrue(tables.size() == wantTables.size());
    
    for (int i = 0; i < tables.size(); i++) {
      assertTrue(tables.get(i).equals(wantTables.get(i)));
    }
  }

  @Test
  @DisplayName("Test if executeUpdate can work properly")
  void testExecuteUpdate() {
    var sql = "INSERT INTO securities VALUES(NULL, 'GOOGL', 'Alphabet Inc Class A', 'STOCK', 134.99, TRUE)";
    int affectedRows = executeUpdate(sql);

    assertEquals(affectedRows, 1);
    
    sql = "SELECT symbol from securities WHERE symbol='GOOGL'";
    List<String> gotSymbols = new ArrayList<>();
    executeQuery(sql, rs -> {
      try {
        gotSymbols.add(rs.getString("symbol"));
      } catch (SQLException e) {
        e.printStackTrace();
        assertTrue(false);
      }
    });

    assertEquals(gotSymbols.size(), 1);
    
    var gotSymbol = gotSymbols.get(0);
    var wantSymbol = "GOOGL";
    assertEquals(gotSymbol, wantSymbol);
  }

}
