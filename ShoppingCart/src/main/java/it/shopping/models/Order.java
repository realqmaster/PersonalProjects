package it.shopping.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class Order.
 * Represents a collection of products associated with an amount
 * Includes a global discount coefficent which is applied when the total amount of the partial prices
 * exceeds the field globalDiscountThresold.
 * 
 */
public class Order {

	/** The items. */
	private Map<Product, BigDecimal> items;
	
	/** The global discount. */
	private BigDecimal globalDiscount;
	
	/** The global discount thresold. */
	private BigDecimal globalDiscountThresold;

	/**
	 * Instantiates a new order.
	 */
	public Order() {
		super();
		items = new HashMap<>();
	}

	/**
	 * Adds a product with a specified amount.
	 * If the product is already in the order, the amount is increased instead of adding
	 * a new element.
	 *
	 * @param product the product
	 * @param amount the amount
	 */
	public void addAmount(Product product, BigDecimal amount) {
		items.put(product, items.getOrDefault(product, new BigDecimal("0")).add(amount));
	}

	/**
	 * Calculates the price of a single product with a specified amount
	 * applying the intrinsic discount if the product has one.
	 * Result is rounded to 2 decimal digits
	 *
	 * @param product the product
	 * @return the partial price or 0 if null product was provided
	 */
	public BigDecimal getPartialPrice(Product product) {
		BigDecimal result = new BigDecimal("0");
		if(items.containsKey(product)){
			BigDecimal amount = items.get(product);
			result = product.getUnitPrice().multiply(amount).multiply(new BigDecimal("1").subtract(product.getType().getDiscount()));
			result = result.setScale(2, RoundingMode.HALF_UP);
		}
		return result;

	}
	
	/**
	 * Calculates the difference between whole price of a product in the order and the discounted value
	 * Result is rounded to 2 decimal digits
	 * 
	 * @param product the product
	 * @return the discounted difference or 0 if product was not found in the order items
	 */
	
	public BigDecimal getSavedAmount(Product product){
		BigDecimal result = new BigDecimal("0");
		
		if(items.containsKey(product)){
			BigDecimal amount = items.get(product);
			result = (product.getUnitPrice().multiply(amount)).subtract(getPartialPrice(product));
			result = result.setScale(2, RoundingMode.HALF_UP);

		}
		
		return result;
	}

	/**
	 * Apply global discount if the input, expected to be a price, exceeds the set threshold.
	 *
	 * @param input the input
	 * @return the big decimal
	 */
	public BigDecimal applyGlobalDiscount(BigDecimal input) {
		BigDecimal result  = input;
		
		if (globalDiscount != null && globalDiscountThresold != null && input.compareTo(globalDiscountThresold)>0) {
			result = input.multiply(new BigDecimal("1").subtract(globalDiscount));
		} 
		return result;

	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public Map<Product, BigDecimal> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 *
	 * @param items the items
	 */
	public void setItems(Map<Product, BigDecimal> items) {
		this.items = items;
	}

	/**
	 * Gets the global discount.
	 *
	 * @return the global discount
	 */
	public BigDecimal getGlobalDiscount() {
		return globalDiscount;
	}

	/**
	 * Sets the global discount.
	 *
	 * @param globalDiscount the new global discount
	 */
	public void setGlobalDiscount(BigDecimal globalDiscount) {
		this.globalDiscount = globalDiscount;
	}

	/**
	 * Gets the global discount thresold.
	 *
	 * @return the global discount thresold
	 */
	public BigDecimal getGlobalDiscountThresold() {
		return globalDiscountThresold;
	}

	/**
	 * Sets the global discount thresold.
	 *
	 * @param globalDiscountThresold the new global discount thresold
	 */
	public void setGlobalDiscountThresold(BigDecimal globalDiscountThresold) {
		this.globalDiscountThresold = globalDiscountThresold;
	}
}
