package edu.bu.cs611.portfoliostocksystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.bu.cs611.portfoliostocksystem.Util;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClientTest;
import edu.bu.cs611.portfoliostocksystem.model.Customer;
import edu.bu.cs611.portfoliostocksystem.model.CustomerOwnedSecurity;
import edu.bu.cs611.portfoliostocksystem.model.CustomerTradingAccount;
import edu.bu.cs611.portfoliostocksystem.model.Security;

public class CustomerOwnedSecurityDaoTest {

  private static SecurityDao secDao;
  private static Security testSecurity;
  private static CustomerDao cxDao;
  private static Customer testCustomer;
  private static CustomerTradingAccountDao cxTradingAcctDao;
  private static CustomerTradingAccount testCxTradingAcct;
  private static CustomerOwnedSecurityDao cxOwnedSecDao;
  private static CustomerOwnedSecurity testCxOwnedSec;

  @BeforeAll
  static void setup() {
    DatabaseClientTest.createTestDatabase();

    secDao = new SecurityDao(new DatabaseClient(true));
    testSecurity = new Security("GOOGL", "Alphabet Inc Class A", "STOCK", 134.99, true);

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

    cxOwnedSecDao = new CustomerOwnedSecurityDao(new DatabaseClient(true));
    testCxOwnedSec = new CustomerOwnedSecurity(
      0,
      0,
      10L,
      123.123,
      Timestamp.from(Instant.now())
    );
  }

  @AfterAll
  static void teardown() {
    DatabaseClientTest.deleteTestDatabase();
  }
  
  @Test
  void testAdd() {
    secDao.add(testSecurity);
    testCxOwnedSec.setSecurityId(testSecurity.getId());

    cxDao.add(testCustomer);
    testCxTradingAcct.setCustomerId(testCustomer.getId());
    
    cxTradingAcctDao.add(testCxTradingAcct);
    testCxOwnedSec.setCxTradingAcctId(testCxTradingAcct.getId());

    assertTrue(cxOwnedSecDao.add(testCxOwnedSec));
    assertNotNull(cxOwnedSecDao.getById(testCxOwnedSec.getId()));

    cxOwnedSecDao.delete(testCxOwnedSec);
    cxTradingAcctDao.delete(testCxTradingAcct);
    cxDao.delete(testCustomer);
    secDao.delete(testSecurity);
  }

  @Test
  void testUpdate() {
    secDao.add(testSecurity);
    testCxOwnedSec.setSecurityId(testSecurity.getId());

    cxDao.add(testCustomer);
    testCxTradingAcct.setCustomerId(testCustomer.getId());
    
    cxTradingAcctDao.add(testCxTradingAcct);
    testCxOwnedSec.setCxTradingAcctId(testCxTradingAcct.getId());

    assertTrue(cxOwnedSecDao.add(testCxOwnedSec));

    testCxOwnedSec.setQuantity(888L);
    cxOwnedSecDao.update(testCxOwnedSec);
    
    assertEquals(testCxOwnedSec.getQuantity(), cxOwnedSecDao.getById(testCxOwnedSec.getId()).getQuantity());

    cxOwnedSecDao.delete(testCxOwnedSec);
    cxTradingAcctDao.delete(testCxTradingAcct);
    cxDao.delete(testCustomer);
    secDao.delete(testSecurity);
  }

}
