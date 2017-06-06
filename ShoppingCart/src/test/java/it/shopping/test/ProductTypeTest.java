package it.shopping.test;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import it.shopping.models.ProductType;

/**
 * The Class ProductTypeTest.
 */
public class ProductTypeTest {

	/** Labels for frequently used productType descriptions */
	private static final String FOOD_LABEL = "FOOD";
	private static final String GROCERY_LABEL = "GROCERIES";
	
	/** Sample product type. */
	private ProductType myProductType;

	/**
	 * Builds a productType with values not set.
	 */
	@Test
	public void buildValuesNotSet(){
		myProductType = new ProductType();
		Assert.assertTrue("".equals(myProductType.getDesc()));
		Assert.assertTrue(myProductType.getDiscount().compareTo(new BigDecimal("0"))==0);
	}
	
	/**
	 * Builds a productType with values set.
	 */
	@Test
	public void buildValuesSet(){
		myProductType = new ProductType(GROCERY_LABEL, new BigDecimal("0.05"));
		Assert.assertTrue(GROCERY_LABEL.equals(myProductType.getDesc()));
		Assert.assertTrue(myProductType.getDiscount().compareTo(new BigDecimal("0.05"))==0);
	}
	
	/**
	 * Builds a productType with only desc set.
	 */
	@Test
	public void buildValueOnlyDesc(){
		myProductType = new ProductType(FOOD_LABEL);
		Assert.assertTrue(FOOD_LABEL.equals(myProductType.getDesc()));
		Assert.assertTrue(myProductType.getDiscount().compareTo(new BigDecimal("0"))==0);
	}
	
	/**
	 * Changing the desc.
	 */
	@Test
	public void setDesc(){
		myProductType = new ProductType(FOOD_LABEL);
		myProductType.setDesc(GROCERY_LABEL);
		Assert.assertTrue(GROCERY_LABEL.equals(myProductType.getDesc()));
	}
	
	/**
	 * Changing the discount.
	 */
	@Test
	public void setDiscount(){
		myProductType = new ProductType(GROCERY_LABEL, new BigDecimal("0.05"));
		myProductType.setDiscount(new BigDecimal("0"));
		Assert.assertTrue(myProductType.getDiscount().compareTo(new BigDecimal("0"))==0);	
	}
}
