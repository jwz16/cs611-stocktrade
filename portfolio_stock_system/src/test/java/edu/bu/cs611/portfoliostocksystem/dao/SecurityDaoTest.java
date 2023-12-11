package edu.bu.cs611.portfoliostocksystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.bu.cs611.portfoliostocksystem.database.DatabaseClient;
import edu.bu.cs611.portfoliostocksystem.database.DatabaseClientTest;
import edu.bu.cs611.portfoliostocksystem.model.Security;

public class SecurityDaoTest {

  private static SecurityDao secDao;
  private static Security testSecurity;

  @BeforeAll
  static void setup() {
    DatabaseClientTest.createTestDatabase();

    secDao = new SecurityDao(new DatabaseClient(true));

    testSecurity = new Security("GOOGL", "Alphabet Inc Class A", "STOCK", 134.99, true);
  }

  @AfterAll
  static void teardown() {
    DatabaseClientTest.deleteTestDatabase();
  }

  @Test
  void testAdd() {
    assertTrue(secDao.add(testSecurity));

    assertNotNull(secDao.getById(testSecurity.getId()));
    assertTrue(secDao.delete(testSecurity));
  }

  @Test
  void testDelete() {
    secDao.add(testSecurity);

    assertTrue(secDao.delete(testSecurity));
    assertNull(secDao.getById(testSecurity.getId()));
  }

  @Test
  void testGetAll() {
    var testSecurity1 = new Security(testSecurity);
    testSecurity1.setSymbol("MSFT");
    testSecurity1.setSecurityName("Microsoft Corp");
    testSecurity1.setPrice(373.04);
    secDao.add(testSecurity1);

    var testSecurity2 = new Security(testSecurity);
    testSecurity2.setSymbol("TSLA");
    testSecurity2.setSecurityName("Tesla Inc");
    testSecurity2.setPrice(243.84);
    secDao.add(testSecurity2);

    assertEquals(secDao.getAll().size(), 2);

    assertTrue(secDao.delete(testSecurity1));
    assertTrue(secDao.delete(testSecurity2));
  }

  @Test
  void testGetById() {
    secDao.add(testSecurity);
    assertNotNull(secDao.getById(testSecurity.getId()));
    secDao.delete(testSecurity);
  }

  @Test
  void testUpdate() {
    testSecurity.setPrice(123.45);
    secDao.add(testSecurity);

    testSecurity.setPrice(234.56);
    secDao.update(testSecurity);

    assertEquals(234.56, secDao.getById(testSecurity.getId()).getPrice());
    secDao.delete(testSecurity);
  }

  @Test
  void testSelectWhere() {
    var testSecurity1 = new Security(testSecurity);
    testSecurity1.setSymbol("MSFT");
    testSecurity1.setSecurityName("Microsoft Corp");
    testSecurity1.setPrice(373.04);
    testSecurity1.setTradable(true);
    secDao.add(testSecurity1);

    var testSecurity2 = new Security(testSecurity);
    testSecurity2.setSymbol("TSLA");
    testSecurity2.setSecurityName("Tesla Inc");
    testSecurity2.setPrice(243.84);
    testSecurity2.setTradable(false);
    secDao.add(testSecurity2);

    assertEquals(1, secDao.selectWhere("tradable=0").size());

    secDao.delete(testSecurity1);
    secDao.delete(testSecurity2);
  }

}
