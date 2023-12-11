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
import edu.bu.cs611.portfoliostocksystem.model.CustomerTradingAccount;

public class CustomerTradingAccountDaoTest {

  private static CustomerDao cxDao;
  private static Customer testCustomer;
  private static CustomerTradingAccountDao cxTradingAcctDao;
  private static CustomerTradingAccount testCxTradingAcct;

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

    cxTradingAcctDao = new CustomerTradingAccountDao(new DatabaseClient(true));
    testCxTradingAcct = new CustomerTradingAccount(
      0,
      "Test Trading Account",
      123456789L,
      123456.789,
      1234.567,
      123456.789 - 1234.567,
      123.456,
      456.789,
      CustomerTradingAccount.Status.ACTIVE
    );
  }

  @AfterAll
  static void teardown() {
    DatabaseClientTest.deleteTestDatabase();
  }

  @Test
  void testAdd() {
    cxDao.add(testCustomer);
    testCxTradingAcct.setCustomerId(testCustomer.getId());

    assertTrue(cxTradingAcctDao.add(testCxTradingAcct));
    assertNotNull(cxTradingAcctDao.getById(testCxTradingAcct.getId()));

    cxTradingAcctDao.delete(testCxTradingAcct);
    cxDao.delete(testCustomer);
  }

  @Test
  void testUpdate() {
    cxDao.add(testCustomer);
    testCxTradingAcct.setCustomerId(testCustomer.getId());

    assertTrue(cxTradingAcctDao.add(testCxTradingAcct));

    testCxTradingAcct.setAccountStatus(CustomerTradingAccount.Status.DENIED);
    assertTrue(cxTradingAcctDao.update(testCxTradingAcct));

    var gotAcct = cxTradingAcctDao.getById(testCxTradingAcct.getId());
    
    assertEquals(testCxTradingAcct.getAccountStatus(), gotAcct.getAccountStatus());

    cxTradingAcctDao.delete(testCxTradingAcct);
    cxDao.delete(testCustomer);
  }

}
