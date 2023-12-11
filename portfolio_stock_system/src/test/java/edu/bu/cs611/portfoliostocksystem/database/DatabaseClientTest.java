package edu.bu.cs611.portfoliostocksystem.database;

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

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient.QueryResultSetHandler;

public class DatabaseClientTest {
  
  public static void createTestDatabase() {
    var success = new DatabaseClient(true).initDatabase();
    assertTrue(success);
  }

  public static void deleteTestDatabase() {
    try {
      Files.deleteIfExists(Paths.get(DatabaseClient.TEST_DB_FILE_PATH));
    } catch (IOException e) {
      assertTrue(false);
    }
  }

  private static int executeUpdate(String sql) {
    return new DatabaseClient(true).executeUpdate(sql);
  }

  private static void executeQuery(String sql, QueryResultSetHandler handler) {
    new DatabaseClient(true).executeQuery(sql, handler);
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
    assertTrue(new File(DatabaseClient.TEST_DB_FILE_PATH).exists());
  }

  @Test
  @DisplayName("Test get database connection")
  void testGetDatabaseConnection() {
    var conn = new DatabaseClient(true).getConnection();
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

    assertEquals(1, affectedRows);

    sql = "SELECT symbol from securities WHERE symbol='GOOGL'";
    List<String> gotSymbols = new ArrayList<>();
    executeQuery(sql, rs -> {
      try {
        rs.next();
        gotSymbols.add(rs.getString("symbol"));
      } catch (SQLException e) {
        e.printStackTrace();
        assertTrue(false);
      }
    });

    assertEquals(1, gotSymbols.size());

    var gotSymbol = gotSymbols.get(0);
    var wantSymbol = "GOOGL";
    assertEquals(wantSymbol, gotSymbol);
  }

}
