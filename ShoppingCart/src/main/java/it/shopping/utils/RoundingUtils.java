package it.shopping.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The Class RoundingUtils.
 * Used to define helper methods in rounding amounts to specific increments.
 * Should be invoked statically.
 */
public class RoundingUtils {

	/**
	 * Private constructor to hide implicit public
	 */
	private RoundingUtils() {
	}

	/**
	 * Rounds given value to the nearest increment specified using a given rounding mode
	 *
	 * @param value the value
	 * @param increment the increment
	 * @param roundingMode the rounding mode
	 * @return the result
	 */
	public static BigDecimal round(BigDecimal value, BigDecimal increment, RoundingMode roundingMode) {
		if (increment.signum() == 0) {
			return value;
		} else {
			BigDecimal divided = value.divide(increment, 0, roundingMode);
			return divided.multiply(increment);
			 
		}
	}
}
