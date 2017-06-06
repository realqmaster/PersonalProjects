package it.shopping.models;

import java.math.BigDecimal;

/**
 * The Class Product.
 * Represents a single product, an associated ProductType and its unitary price
 */
public class Product {

	 /** The desc. */
 	private String desc;
	 
 	/** The type. */
 	private ProductType type;
	 
 	/** The unit price. */
 	private BigDecimal unitPrice;
	 
		
	/**
	 * Instantiates a new product with specified desc, type and unitPrice.
	 *
	 * @param desc the desc
	 * @param type the type
	 * @param unitPrice the unit price
	 */
	public Product(String desc, ProductType type, BigDecimal unitPrice){
		super();
		this.desc = desc;
		this.type = type;
		this.unitPrice = unitPrice;
	}
	
	/**
	 * Instantiates a new product with specified desc and unitPrice.
	 * Type is defaulted to a ProductType with empty desc and discount set to 0
	 *
	 * @param desc the desc
	 * @param unitPrice the unit price
	 */
	public Product(String desc, BigDecimal unitPrice){
		super();
		this.desc = desc;
		this.type = new ProductType();
		this.unitPrice = unitPrice;
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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public ProductType getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(ProductType type) {
		this.type = type;
	}
	
	/**
	 * Gets the unit price.
	 *
	 * @return the unit price
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	
	/**
	 * Sets the unit price.
	 *
	 * @param unitPrice the new unit price
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
}
