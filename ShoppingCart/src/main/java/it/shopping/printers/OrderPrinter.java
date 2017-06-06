package it.shopping.printers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.shopping.models.Order;
import it.shopping.models.Product;
import it.shopping.utils.RoundingUtils;

/**
 * The Class OrderPrinter.
 * Used to process a single order and output the bill to a logger.
 * Should be invoked statically.
 */
public class OrderPrinter {
	
	
	/** The prices format. */
	private static  NumberFormat pricesFormat = NumberFormat.getNumberInstance(Locale.getDefault());
	
	/** The percent format. */
	private static  NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	/** Simple formatting logger */
	private static Logger log = LogManager.getFormatterLogger("console");
	
	/**
	 * Private constructor to hide implicit public
	 */
	private OrderPrinter() {
	}
	
	/**
	 * Prints the bill of a specified order to console.
	 * 
	 * @param order the order
	 */
	public static void printBill(Order order) {
		
		Map<Product, BigDecimal> items = order.getItems();
		
		
		//Formatting settings
		String tableFormat = "%20s %10s %10s %10s %20s";
		pricesFormat.setMaximumFractionDigits(2);
		pricesFormat.setMinimumFractionDigits(2);
		percentFormat.setMaximumFractionDigits(1);
		
		if (items != null && items.size() > 0) {
			
			String tableLine = "-----------------------------------------------------------------------------";

			
			//The Header
			log.debug(tableLine);
		    log.printf(Level.DEBUG,tableFormat, "Product", "Quantity", "Unit Price", "Discount", "Final price");
			
			log.debug(tableLine);
			
			//Retrieve products and iterate through them to generate rows
			Iterator<Entry<Product, BigDecimal>> iterator = items.entrySet().iterator();
			BigDecimal total = new BigDecimal("0");
			BigDecimal totalDiscount = new BigDecimal("0");
			while (iterator.hasNext()) {
				Entry<Product, BigDecimal> entry = iterator.next();

				Product product = entry.getKey();
				BigDecimal amount = entry.getValue();
				
				//Calculate a product price basing on amount and productType discount
				BigDecimal partialPrice = order.getPartialPrice(product);
				
				//Print the row
				log.printf(Level.DEBUG,tableFormat, product.getDesc(), amount, pricesFormat.format(product.getUnitPrice()), percentFormat.format(product.getType().getDiscount()), pricesFormat.format(partialPrice));

				//Update current total
				total = total.add(partialPrice);
				
				//Update current totalDiscount
				totalDiscount = totalDiscount.add(order.getSavedAmount(product));

			}
			
			//Copy total as gross total in case we need it later
			BigDecimal grossTotal = total;
			
			
			//Apply global discount if total exceeds global discount threshold
			total = order.applyGlobalDiscount(total);
			
			//If global discount was applied, also print the gross total
			if(grossTotal.compareTo(total)>0){
				
				BigDecimal globalDiscount = order.getGlobalDiscount();
				
				//We update the total discount with the global
				totalDiscount = totalDiscount.add(globalDiscount.multiply(grossTotal));
				
				log.debug(tableLine);
				log.printf(Level.DEBUG,tableFormat, "GROSS TOTAL", "","","", pricesFormat.format(grossTotal));
							
				log.debug(tableLine);
				log.printf(Level.DEBUG,tableFormat, "VOLUME DISCOUNT", "","",percentFormat.format(globalDiscount), pricesFormat.format(globalDiscount.multiply(grossTotal)));
							

			}
			
			//Footer with totals
			log.debug(tableLine);
			log.printf(Level.DEBUG,tableFormat, "TOTAL", "","","", pricesFormat.format(RoundingUtils.round(total, new BigDecimal("0.05"), RoundingMode.HALF_UP)));						
			log.printf(Level.DEBUG,tableFormat, "(TOTAL DISCOUNT " + pricesFormat.format(totalDiscount) + ")", "","","", "");						
			log.debug(tableLine);
		} else {
			//A message to be returned when the order contains no items to evaluate
			log.debug("L'ordine non contiene elementi.");
		}
	}
}
