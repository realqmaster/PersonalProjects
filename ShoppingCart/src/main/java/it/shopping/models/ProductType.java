package it.shopping.models;

import java.math.BigDecimal;

/**
 * The Class ProductType.
 * Defines a product category with an associated discount.
 */
public class ProductType {
	
	/** The desc. */
	private String desc;

	/** The discount. */
	private BigDecimal discount;

	/**
	 * Instantiates a new product type with specified desc and discount values.
	 *
	 * @param desc the desc
	 * @param discount the discount
	 */
	public ProductType(String desc, BigDecimal discount) {
		super();
		this.desc = desc;
		this.discount = discount;
	}

	/**
	 * Instantiates a new product type with specified desc value.
	 * Discount is defaulted to 0
	 *
	 * @param desc the desc
	 */
	public ProductType(String desc) {
		super();
		this.desc = desc;
		this.discount = new BigDecimal("0");
	}
	
	/**
	 * Instantiates a new product type without any arguments.
	 * Defaults to an empty desc value and a 0 discount
	 */
	public ProductType(){
		super();
		this.desc = "";
		this.discount =  new BigDecimal("0");
	}

	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets the desc.
	 *
	 * @param desc the new desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Gets the discount.
	 *
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * Sets the discount.
	 *
	 * @param discount the new discount
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

}
