package edu.bu.cs611.portfoliostocksystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import edu.bu.cs611.portfoliostocksystem.model.CustomerMessage;

public class CustomerMessageDaoTest {

  private static CustomerDao cxDao;
  private static Customer testCustomer;
  private static CustomerMessageDao cxMsgDao;
  private static CustomerMessage testCxMsg;

  @BeforeAll
  static void setup() {
    DatabaseClientTest.createTestDatabase();

    cxDao = new CustomerDao(new DatabaseClient(true));
    cxMsgDao = new CustomerMessageDao(new DatabaseClient(true));

    testCustomer = new Customer(
      "John", "Anderson",
      "john001",
      "john001@gmail.com",
      "01/01/1990",
      "123456789",
      "PENDING",
      Util.hashPassword("123456789")
    );

    testCxMsg = new CustomerMessage(
      1,
      "Hello John, welcome to Portfolio Stock System",
      Timestamp.from(Instant.now())
    );
  }

  @AfterAll
  static void teardown() {
    DatabaseClientTest.deleteTestDatabase();
  }

  @Test
  void testAdd() {
    cxDao.add(testCustomer);
    testCxMsg.setCustomerId(testCustomer.getId());

    assertTrue(cxMsgDao.add(testCxMsg));

    var gotCxMsg = cxMsgDao.getById(testCxMsg.getId());
    assertNotNull(gotCxMsg);
    assertEquals(gotCxMsg.getSentAt(), testCxMsg.getSentAt());

    cxDao.delete(testCustomer);
    cxMsgDao.delete(testCxMsg);
  }

  @Test
  void testDelete() {
    cxDao.add(testCustomer);
    testCxMsg.setCustomerId(testCustomer.getId());
    cxMsgDao.add(testCxMsg);

    assertTrue(cxMsgDao.delete(testCxMsg));
    assertNull(cxMsgDao.getById(testCxMsg.getId()));

    cxDao.delete(testCustomer);
  }

  @Test
  void testGetAll() {
    cxDao.add(testCustomer);
    testCxMsg.setCustomerId(testCustomer.getId());

    var testCxMsg1 = new CustomerMessage(testCxMsg);
    testCxMsg1.setMessage("Message 1");
    cxMsgDao.add(testCxMsg1);

    var testCxMsg2 = new CustomerMessage(testCxMsg);
    testCxMsg2.setMessage("Message 2");
    cxMsgDao.add(testCxMsg2);

    assertEquals(2, cxMsgDao.getAll().size());

    assertTrue(cxMsgDao.delete(testCxMsg1));
    assertTrue(cxMsgDao.delete(testCxMsg2));
    assertTrue(cxDao.delete(testCustomer));
  }

  @Test
  void testGetById() {
    cxDao.add(testCustomer);
    testCxMsg.setCustomerId(testCustomer.getId());
    cxMsgDao.add(testCxMsg);

    assertNotNull(cxMsgDao.getById(testCxMsg.getId()));
    
    cxDao.delete(testCustomer);
    cxMsgDao.delete(testCxMsg);
  }

  @Test
  void testUpdate() {
    cxDao.add(testCustomer);
    testCxMsg.setCustomerId(testCustomer.getId());

    var wantMsg = "Hello John, would you like to open a derivative trading account?";

    testCxMsg.setMessage("Hello John, welcome to Portfolio Stock System");
    cxMsgDao.add(testCxMsg);

    testCxMsg.setMessage(wantMsg);
    assertTrue(cxMsgDao.update(testCxMsg));

    assertEquals(wantMsg, cxMsgDao.getById(testCxMsg.getId()).getMessage());

    cxDao.delete(testCustomer);
    cxMsgDao.delete(testCxMsg);
  }
}
