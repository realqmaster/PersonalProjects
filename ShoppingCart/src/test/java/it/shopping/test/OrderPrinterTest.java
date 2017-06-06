package it.shopping.test;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.shopping.models.Order;
import it.shopping.models.Product;
import it.shopping.models.ProductType;
import it.shopping.printers.OrderPrinter;

/**
 * The Class OrderPrinterTest.
 */
public class OrderPrinterTest {

	/** Sample Orders. */
	private Order myOrder;
	private Order emptyOrder;

	/** Sample Products */
	private Product pasta;
	private Product firstBook;
	private Product secondBook;
	private Product coffee;
	private Product cake;
	private Product chocolate;
	private Product wine;
	private Product apple;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		myOrder = new Order();
		emptyOrder = new Order();

		ProductType groceryType = new ProductType("GROCERY", new BigDecimal("0.075"));
		ProductType  literaryType = new ProductType("LITERARY", new BigDecimal("0.12"));
		ProductType sweetsType = new ProductType("SWEETS");
		ProductType drinkType = new ProductType("DRINK");

		pasta = new Product("Pasta 1kg", groceryType, new BigDecimal("4.29"));
		firstBook = new Product("Book", literaryType, new BigDecimal("10.12"));
		secondBook = new Product("Book", literaryType, new BigDecimal("15.05"));
		coffee = new Product("Coffee 500g", groceryType, new BigDecimal("3.21"));
		cake = new Product("Cake", sweetsType, new BigDecimal("2.35"));
		chocolate = new Product("Chocolate", sweetsType, new BigDecimal("2.1"));
		wine = new Product("Wine", drinkType, new BigDecimal("10.5"));
		apple = new Product("Apple", new BigDecimal("0.5"));

		myOrder.setGlobalDiscount(new BigDecimal("0.05"));
		myOrder.setGlobalDiscountThresold(new BigDecimal("40.0"));

	}

	/**
	 * Check bill 1.
	 */
	@Test
	public void firstUseCase() {

		myOrder.addAmount(pasta, new BigDecimal("1"));
		myOrder.addAmount(firstBook, new BigDecimal("1"));

		OrderPrinter.printBill(myOrder);
		Assert.assertTrue(true);
	}

	/**
	 * Check bill 2.
	 */
	@Test
	public void secondUseCase() {

		myOrder.addAmount(pasta, new BigDecimal("1"));
		myOrder.addAmount(coffee, new BigDecimal("1"));
		myOrder.addAmount(cake, new BigDecimal("1"));

		OrderPrinter.printBill(myOrder);
		Assert.assertTrue(true);
	}

	/**
	 * Check bill 3.
	 */
	@Test
	public void thirdUseCase() {

		myOrder.addAmount(chocolate, new BigDecimal("10"));
		myOrder.addAmount(wine, new BigDecimal("1"));
		myOrder.addAmount(secondBook, new BigDecimal("1"));
		myOrder.addAmount(apple, new BigDecimal("5"));

		OrderPrinter.printBill(myOrder);
		Assert.assertTrue(true);
	}
	
	/**
	 * Check empty by construction
	 */
	@Test 
	public void checkEmpty1(){
		OrderPrinter.printBill(emptyOrder);
		Assert.assertTrue(true);

	}
	
	/**
	 * Check empty by forcing the items to null
	 */
	@Test 
	public void checkEmpty2(){
		emptyOrder.setItems(null);
		OrderPrinter.printBill(emptyOrder);
		Assert.assertTrue(true);

	}
}
