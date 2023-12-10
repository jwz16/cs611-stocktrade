package edu.bu.cs611.portfoliostocksystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.bu.cs611.portfoliostocksystem.Util;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClientTest;
import edu.bu.cs611.portfoliostocksystem.model.Customer;

public class CustomerDaoTest {

  private static CustomerDao cxDao;
  private static Customer testCustomer;

  @BeforeAll
  static void setup() {
    DatabaseClientTest.createTestDatabase();

    cxDao = new CustomerDao(new DatabaseClient(true));

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
    assertTrue(cxDao.add(testCustomer));

    assertNotNull(cxDao.getById(testCustomer.getId()));
    assertTrue(cxDao.delete(testCustomer));
  }

  @Test
  void testDelete() {
    cxDao.add(testCustomer);

    assertTrue(cxDao.delete(testCustomer));
    assertNull(cxDao.getById(testCustomer.getId()));
  }

  @Test
  void testGetAll() {
    testCustomer.setUsername("john001");
    testCustomer.setEmail("john001@gmail.com");
    cxDao.add(testCustomer);
    Integer cxId1 = testCustomer.getId();

    testCustomer.setUsername("john002");
    testCustomer.setEmail("john002@gmail.com");
    cxDao.add(testCustomer);
    Integer cxId2 = testCustomer.getId();

    assertEquals(cxDao.getAll().size(), 2);

    assertTrue(cxDao.deleteById(cxId1));
    assertTrue(cxDao.deleteById(cxId2));
  }

  @Test
  void testGetById() {
    cxDao.add(testCustomer);
    assertNotNull(cxDao.getById(testCustomer.getId()));
    cxDao.delete(testCustomer);
  }

  @Test
  void testUpdate() {
    testCustomer.setFirstName("John");
    cxDao.add(testCustomer);

    testCustomer.setFirstName("Michael");
    cxDao.update(testCustomer);

    assertEquals(cxDao.getById(testCustomer.getId()).getFirstName(), "Michael");
    cxDao.delete(testCustomer);
  }
  
}
