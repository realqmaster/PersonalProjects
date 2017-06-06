package it.shopping.test;
import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.shopping.models.Order;
import it.shopping.models.Product;
import it.shopping.models.ProductType;

/**
 * The Class OrderTest.
 */
public class OrderTest {

	/** Sample Order */
	Order myOrder = new Order();
	
	/** Sample Products */
	Product prod1;
	Product prod2;
	Product prod3;

	/**
	 * Sets up.
	 */
	@Before
	public void setUp() {

		ProductType groceryType = new ProductType("GROCERY", new BigDecimal("0.05"));
		ProductType literaryType = new ProductType("LITERARY", new BigDecimal("0.1"));

		prod1 = new Product("Pasta", groceryType, new BigDecimal("1.10"));
		prod2 = new Product("Book", literaryType, new BigDecimal("10.0"));
		prod3 = new Product("Coffee", groceryType, new BigDecimal("2.25"));

	}

	/**
	 * Checks for items.
	 */
	@Test
	public void hasItems() {

		myOrder.addAmount(prod1, new BigDecimal("1"));
		myOrder.addAmount(prod2, new BigDecimal("1"));
		myOrder.addAmount(prod3, new BigDecimal("1"));
		
		Assert.assertTrue(myOrder.getItems().containsKey(prod1));
		Assert.assertTrue(myOrder.getItems().containsKey(prod2));
		Assert.assertTrue(myOrder.getItems().containsKey(prod3));

	}
	
	/**
	 * Sets the items.
	 */
	@Test
	public void setItems(){
		HashMap<Product, BigDecimal> myItems = new HashMap<>();
		myItems.put(prod1, new BigDecimal("1"));
		
		myOrder.setItems(myItems);
		
		Assert.assertTrue(myOrder.getItems().containsKey(prod1));
		Assert.assertFalse(myOrder.getItems().containsKey(prod2));
		Assert.assertFalse(myOrder.getItems().containsKey(prod3));

		}

	/**
	 * Adds the same type.
	 */
	@Test
	public void addSameType() {
		myOrder.addAmount(prod1, new BigDecimal("1.5"));
		myOrder.addAmount(prod1, new BigDecimal("2.5"));
		Assert.assertTrue(myOrder.getItems().get(prod1).compareTo(new BigDecimal("4.0"))==0);
	}
	
	/**
	 * Default global discount.
	 */
	@Test
	public void defaultGlobalDiscount(){
		Assert.assertTrue(myOrder.getGlobalDiscount()==null);
	}
	
	/**
	 * Sets the global discount.
	 */
	@Test
	public void setGlobalDiscount(){
		myOrder.setGlobalDiscount(new BigDecimal("0.1"));
		Assert.assertTrue(myOrder.getGlobalDiscount().compareTo(new BigDecimal("0.1"))==0);
	}
	
	/**
	 * Default global discount threshold.
	 */
	@Test
	public void defaultGlobalDiscountThresold(){
		Assert.assertTrue(myOrder.getGlobalDiscountThresold()==null);
	}
	
	/**
	 * Sets the global discount threshold.
	 */
	@Test
	public void setGlobalDiscountThresold(){
		myOrder.setGlobalDiscountThresold(new BigDecimal("100"));
		Assert.assertTrue(myOrder.getGlobalDiscountThresold().compareTo(new BigDecimal("100"))==0);
	}
	
	/**
	 * Apply global discount.
	 */
	@Test
	public void applyGlobalDiscount(){
		myOrder.setGlobalDiscount(new BigDecimal("0.1"));
		myOrder.setGlobalDiscountThresold(new BigDecimal("100"));
		Assert.assertTrue(myOrder.applyGlobalDiscount(new BigDecimal("110")).compareTo(new BigDecimal("99.0"))==0);
	}
	
	/**
	 * Get saved amount when discount is set
	 */
	@Test
	public void getSavedPositive(){
		BigDecimal theAmount = new BigDecimal("1.0");
		myOrder.addAmount(prod1, theAmount);
		Assert.assertTrue(myOrder.getPartialPrice(prod1).add(myOrder.getSavedAmount(prod1)).compareTo(prod1.getUnitPrice().multiply(theAmount))==0);

		
	}
	
	/**
	 * Get saved amount when discount is not set (defaulted to 0 by design)
	 */
	@Test
	public void getSavedZero(){
		BigDecimal theAmount = new BigDecimal("1.0");
		prod1.getType().setDiscount(new BigDecimal("0"));
		myOrder.addAmount(prod1, theAmount);
		Assert.assertTrue(myOrder.getSavedAmount(prod1).compareTo(new BigDecimal("0"))==0);
		
	}
	
	/**
	 * Get saved amount with product not contained in order items
	 */
	@Test
	public void getSavedProductNotFound(){
		Assert.assertTrue(myOrder.getSavedAmount(prod1).compareTo(new BigDecimal("0"))==0);
		
	}
	
	/**
	 * Get partial price with product not contained in order items
	 */
	@Test
	public void getPartialProductNotFound(){
		Assert.assertTrue(myOrder.getPartialPrice(prod1).compareTo(new BigDecimal("0"))==0);
		
	}
	
	/**
	 * Get partial price as whole price minus the saved amount
	 */
	@Test
	public void getPartialPriceAsWholeMinusSaved(){
		BigDecimal theAmount = new BigDecimal("1");
		myOrder.addAmount(prod2, theAmount);
		BigDecimal wholePrice = prod2.getUnitPrice().multiply(theAmount);
		Assert.assertTrue(myOrder.getPartialPrice(prod2).equals(wholePrice.subtract(myOrder.getSavedAmount(prod2))));
	}
	
	/**
	 *  Get partial price as whole price multiplied with 1 - discount
	 */
	@Test
	public void getPartialPriceAsCoefficent(){
		BigDecimal theAmount = new BigDecimal("1");
		myOrder.addAmount(prod2, theAmount);
		BigDecimal wholePrice = prod2.getUnitPrice().multiply(theAmount);
		Assert.assertTrue(myOrder.getPartialPrice(prod2).equals(wholePrice.multiply(new BigDecimal("1").subtract(prod2.getType().getDiscount()))));

	}

}
