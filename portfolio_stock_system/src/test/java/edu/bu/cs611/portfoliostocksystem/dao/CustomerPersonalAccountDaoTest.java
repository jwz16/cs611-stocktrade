package edu.bu.cs611.portfoliostocksystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.bu.cs611.portfoliostocksystem.Util;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClientTest;
import edu.bu.cs611.portfoliostocksystem.model.Customer;
import edu.bu.cs611.portfoliostocksystem.model.CustomerPersonalAccount;

public class CustomerPersonalAccountDaoTest {

  private static CustomerDao cxDao;
  private static Customer testCustomer;
  private static CustomerPersonalAccountDao cxPersAcctDao;
  private static CustomerPersonalAccount testCxPersAcct;

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
      "ACTIVE",
      Util.hashPassword("123456789")
    );

    cxPersAcctDao = new CustomerPersonalAccountDao(new DatabaseClient(true));
    testCxPersAcct = new CustomerPersonalAccount(
      0,
      "Test Personal Account",
      123456789L,
      021000021L
    );
  }

  @AfterAll
  static void teardown() {
    DatabaseClientTest.deleteTestDatabase();
  }

  @Test
  void testAdd() {
    cxDao.add(testCustomer);
    testCxPersAcct.setCustomerId(testCustomer.getId());

    assertTrue(cxPersAcctDao.add(testCxPersAcct));

    assertNotNull(cxPersAcctDao.getById(testCxPersAcct.getId()));

    cxPersAcctDao.delete(testCxPersAcct);
    cxDao.delete(testCustomer);
  }

  @Test
  void testUpdate() {
    cxDao.add(testCustomer);
    testCxPersAcct.setCustomerId(testCustomer.getId());

    assertTrue(cxPersAcctDao.add(testCxPersAcct));

    testCxPersAcct.setAccountName("Test Account Changed");
    assertTrue(cxPersAcctDao.update(testCxPersAcct));

    var wantAcct = cxPersAcctDao.getById(testCxPersAcct.getId());

    assertEquals(testCxPersAcct.getAccountName(), wantAcct.getAccountName());

    cxPersAcctDao.delete(testCxPersAcct);
    cxDao.delete(testCustomer);
  }

}
