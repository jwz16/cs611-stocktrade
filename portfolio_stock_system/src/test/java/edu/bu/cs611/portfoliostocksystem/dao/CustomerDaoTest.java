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
    var testCustomer1 = new Customer(testCustomer);
    testCustomer1.setUsername("john001");
    testCustomer1.setEmail("john001@gmail.com");
    testCustomer1.setSsn("123123123");
    cxDao.add(testCustomer1);

    var testCustomer2 = new Customer(testCustomer);
    testCustomer2.setUsername("john002");
    testCustomer2.setEmail("john002@gmail.com");
    testCustomer1.setSsn("456456456");
    cxDao.add(testCustomer2);

    assertEquals(2, cxDao.getAll().size());

    assertTrue(cxDao.delete(testCustomer1));
    assertTrue(cxDao.delete(testCustomer2));
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
    assertTrue(cxDao.update(testCustomer));

    assertEquals("Michael", cxDao.getById(testCustomer.getId()).getFirstName());
    cxDao.delete(testCustomer);
  }

  @Test
  void testSelectWhere() {
    var testCustomer1 = new Customer(testCustomer);
    testCustomer1.setUsername("john001");
    testCustomer1.setEmail("john001@gmail.com");
    testCustomer1.setSsn("123123123");
    cxDao.add(testCustomer1);

    var testCustomer2 = new Customer(testCustomer);
    testCustomer2.setUsername("john002");
    testCustomer2.setEmail("john002@gmail.com");
    testCustomer1.setSsn("456456456");
    cxDao.add(testCustomer2);

    assertEquals(1, cxDao.selectWhere("username='john002'").size());

    cxDao.delete(testCustomer1);
    cxDao.delete(testCustomer2);
  }
  
}
