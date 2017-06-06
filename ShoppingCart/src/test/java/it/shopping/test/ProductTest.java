package it.shopping.test;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.shopping.models.Product;
import it.shopping.models.ProductType;

/**
 * The Class ProductTest.
 */
public class ProductTest {

	/** The my product. */
	private Product myProduct;

	/**
	 * Builds a sample producType with desc and discount
	 */

	@Before
	public void setUp() {
		ProductType groceryType  = new ProductType("GROCERY", new BigDecimal("5.0"));
		myProduct = new Product("Pasta", groceryType, new BigDecimal("1.10"));
	}

	/**
	 * Product create.
	 */
	@Test
	public void productCreate() {

		Assert.assertTrue("Pasta".equals(myProduct.getDesc()));
		Assert.assertTrue("GROCERY".equals(myProduct.getType().getDesc()));
		Assert.assertTrue(myProduct.getType().getDiscount().compareTo(new BigDecimal("5.0"))==0);
		Assert.assertTrue(myProduct.getUnitPrice().compareTo(new BigDecimal("1.10"))==0);
	}

	/**
	 * Sets the desc.
	 */
	@Test
	public void setDesc() {
		myProduct.setDesc("Spaghetti");
		Assert.assertTrue("Spaghetti".equals(myProduct.getDesc()));
	}
	
	/**
	 * Sets the type no discount.
	 */
	@Test
	public void setTypeNoDiscount(){
		ProductType anotherType = new ProductType("FOOD");
		myProduct.setType(anotherType);
		
		Assert.assertTrue("FOOD".equals(myProduct.getType().getDesc()));
		Assert.assertTrue(myProduct.getType().getDiscount().compareTo(new BigDecimal("0"))==0);
	}
	
	/**
	 * Sets the type with discount.
	 */
	@Test
	public void setTypeWithDiscount(){
		ProductType anotherType = new ProductType("FOOD", new BigDecimal("0.08"));
		myProduct.setType(anotherType);

		Assert.assertTrue("FOOD".equals(myProduct.getType().getDesc()));
		Assert.assertTrue(myProduct.getType().getDiscount().compareTo(new BigDecimal("0.08"))==0);
	}
	
	/**
	 * Sets the unit price.
	 */
	@Test
	public void setUnitPrice(){
		myProduct.setUnitPrice(new BigDecimal("1.23"));
		Assert.assertTrue(myProduct.getUnitPrice().compareTo(new BigDecimal("1.23"))==0);
	}

}
