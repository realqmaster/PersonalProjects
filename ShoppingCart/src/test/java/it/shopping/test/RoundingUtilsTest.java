package it.shopping.test;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

import it.shopping.utils.RoundingUtils;

/**
 * The Class RoundingUtilsTest.
 */
public class RoundingUtilsTest {

	/** The increment. */
	private BigDecimal increment;

	/**
	 * Round positive.
	 */
	@Test
	public void roundPositive() {
		increment = new BigDecimal("0.05");
		Assert.assertTrue(RoundingUtils.round(new BigDecimal("1.04"), increment, RoundingMode.HALF_UP)
				.compareTo(new BigDecimal("1.05"))==0);
	}

	/**
	 * Round to zero.
	 */
	@Test
	public void roundToZero() {
		increment = new BigDecimal("0.0");
		Assert.assertTrue(RoundingUtils.round(new BigDecimal("1.04"), increment, RoundingMode.HALF_UP).compareTo(new BigDecimal("1.04"))==0);
	}

	/**
	 * Round negative.
	 */
	@Test
	public void roundNegative() {
		increment = new BigDecimal("0.05");
		Assert.assertTrue(RoundingUtils.round(new BigDecimal("-1.04"), increment, RoundingMode.HALF_UP)
				.compareTo(new BigDecimal("-1.05"))==0);
	}
}
