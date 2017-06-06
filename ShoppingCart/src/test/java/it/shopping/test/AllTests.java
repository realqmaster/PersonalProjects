package it.shopping.test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class AllTests.
 */
@RunWith(Suite.class)
@SuiteClasses({ OrderPrinterTest.class, OrderTest.class, ProductTest.class, ProductTypeTest.class, RoundingUtilsTest.class })
public class AllTests {

}
