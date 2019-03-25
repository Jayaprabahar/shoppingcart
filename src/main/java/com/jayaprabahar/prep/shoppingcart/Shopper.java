/**
 * 
 */
package com.jayaprabahar.prep.shoppingcart;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.collections4.MapUtils;

import com.jayaprabahar.prep.shoppingcart.vo.Action;
import com.jayaprabahar.prep.shoppingcart.vo.Product;
import com.jayaprabahar.prep.shoppingcart.vo.ShoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p> Project : shoppingcart </p>
 * <p> Title : Shopping.java </p>
 * <p> Description: Shopper simulates a Session of shopping person </p>
 * <p> Created: Mar 25, 2019</p>
 * 
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Shopper {

	int shopperId;
	ShoppingCart shoppingCart;

	/**
	 * @param shopperId
	 */
	public Shopper(int shopperId) {
		this.shopperId = shopperId;
		this.shoppingCart = getEmptyShoppingCart();
	}

	/**
	 * Returns an empty shopping cart for the shopper with this shopperId
	 */
	private ShoppingCart getEmptyShoppingCart() {
		return new ShoppingCart(new HashMap<>(), BigDecimal.ZERO, 0);
	}

	/**
	 * Shopper object performs shopping action 
	 * 
	 * @param shoppingCart
	 * @param product
	 * @param unitCount
	 * @param shopperAction
	 * @return updatedShoppingCart
	 */
	ShoppingCart shopping(Product product, int unitCount, Action shopperAction) {
		if (shoppingCart == null || MapUtils.isEmpty(shoppingCart.getCartedProducts()) || shoppingCart.getTotalPrice() == BigDecimal.ZERO)
			shoppingCart = getEmptyShoppingCart();

		int currentUnitCount = shoppingCart.getCartedProducts().getOrDefault(product, 0);

		shoppingCart.getCartedProducts().put(product, Math.max(0, currentUnitCount + unitCount * (shopperAction.equals(Action.ADD) ? 1 : -1)));

		// Remove products with 0 units
		shoppingCart.getCartedProducts().entrySet().removeIf(k -> k.getValue() == 0);

		shoppingCart.updateTotals();

		return shoppingCart;
	}

}