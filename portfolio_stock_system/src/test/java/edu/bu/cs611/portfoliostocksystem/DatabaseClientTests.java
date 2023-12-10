package edu.bu.cs611.portfoliostocksystem;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;

public class DatabaseClientTests {
  
  @Test
  @DisplayName("Test Database creation")
  void testDatabaseCreation() {
    DatabaseClient.initDatabase();

    assertTrue(new File(DatabaseClient.DB_FILE_PATH).exists());
  }

  @Test
  @DisplayName("Test get database connection")
  void testGetDatabaseConnection() {
    DatabaseClient.initDatabase();

    assertNotNull(DatabaseClient.getConnection());
  }

}
