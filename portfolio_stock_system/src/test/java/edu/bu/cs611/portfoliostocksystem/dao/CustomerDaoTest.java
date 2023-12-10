package edu.bu.cs611.portfoliostocksystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.bu.cs611.portfoliostocksystem.Util;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClientTest;
import edu.bu.cs611.portfoliostocksystem.model.Customer;

public class CustomerDaoTest {

  private static DatabaseClient dbClient;
  private static Customer testCustomer;

  @BeforeAll
  static void setup() {
    DatabaseClientTest.createTestDatabase();

    dbClient = new DatabaseClient(true);
    testCustomer = new Customer(
      -1,
      "John", "Anderson",
      "john001",
      "john001@gmail.com",
      "01/01/1990",
      "123456789",
      "PENDING",
      Util.hashPassword("123456789")
    );
  }

  @AfterAll
  static void teardown() {
    DatabaseClientTest.deleteTestDatabase();
  }

  @Test
  void testAdd() {
    var cxDao = new CustomerDao(dbClient);
    assertTrue(cxDao.add(testCustomer));

    dbClient.executeQuery("SELECT id FROM customers WHERE id=" + testCustomer.getId(), rs -> {
      try {
        assertTrue(rs.next());
        assertEquals(rs.getInt("id"), testCustomer.getId());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  void testDelete() {

  }

  @Test
  void testGetAll() {

  }

  @Test
  void testGetById() {

  }

  @Test
  void testUpdate() {

  }
  
}
