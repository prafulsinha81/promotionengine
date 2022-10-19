package com.prom.eng.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prom.eng.model.Promotion;
import com.prom.eng.model.ShopingCart;
import com.prom.eng.model.SkuItems;
import com.prom.eng.service.PromotionService;
import com.prom.eng.util.ApplicationConstants;

@Service
public class PromotionServiceImpl implements PromotionService {


	@Override
	public String addSkuItems(String item, Float price) {
		SkuItems sku= new SkuItems();
		sku.setItem(item);
		sku.setPrice(price);
		ApplicationConstants.SKU_ITEMS.add(sku);
		return "SUCCESS";
	}

	@Override
	public int updateSkuItems(String item, Float price) {
		int noOfRecordUpdated=0;
		if(!ApplicationConstants.SKU_ITEMS.isEmpty()) {
			for(SkuItems sku:ApplicationConstants.SKU_ITEMS) {
				if(sku.getItem().equals(item)) {
					sku.setPrice(price);
					noOfRecordUpdated++;
					break;
				}
			}	
		}

		return noOfRecordUpdated;
	}

	@Override
	public List<SkuItems> getAllSkuItems() {
		return ApplicationConstants.SKU_ITEMS;
	}

	@Override
	public SkuItems getSkuItem(String item) {
		if(!ApplicationConstants.SKU_ITEMS.isEmpty()) {
			for(SkuItems sku:ApplicationConstants.SKU_ITEMS) {
				if(sku.getItem().equals(item)) {
					return sku;
				}
			}
		}
		return null;
	}

	@Override
	public int deleteSkuItem(String item) {
		int noOfRecordUpdated=0;
		if(!ApplicationConstants.SKU_ITEMS.isEmpty()) {
			Iterator<SkuItems> skus = ApplicationConstants.SKU_ITEMS.iterator();
			while(skus.hasNext()) {
				SkuItems sku=skus.next();
				if(sku.getItem().equals(item)) {
					ApplicationConstants.SKU_ITEMS.remove(sku);
					noOfRecordUpdated++;
					break;
				}
			}
		}
		return noOfRecordUpdated;
	}

	@Override
	public String addSingleItemPromotion(String item, Integer unit, Float price, String desc) {
		if(!ApplicationConstants.PROMOTIONS.isEmpty()) {
			for(Promotion promo:ApplicationConstants.PROMOTIONS) {
				if(promo.getItems().contains(item)) {
					return "Item "+item+" is already exist for Promotion Please remove and add the new one";
				}
			}
		}
		Promotion promotion= new Promotion();
		List<String> items = Arrays.asList(item);
		promotion.setItems(items);
		promotion.setUnits(unit);
		promotion.setPrices(price);
		promotion.setDesc(desc);
		promotion.setPromotionId("P"+System.currentTimeMillis());
		ApplicationConstants.PROMOTIONS.add(promotion);
		return "Item "+item+" is added for Promotion";
	}

	@Override
	public String addMultiItemPromotion(List<String> items, Integer unit, Float price, String desc) {
		if(!ApplicationConstants.PROMOTIONS.isEmpty()) {
			for(String item:items) {
				for(Promotion promo:ApplicationConstants.PROMOTIONS) {
					if(promo.getItems().contains(item)) {
						return "Item "+item+" is already exist for Promotion Please remove and add the new one";
					}
				}
			}
		}
		Promotion promotion= new Promotion();
		promotion.setItems(items);
		promotion.setUnits(unit);
		promotion.setPrices(price);
		promotion.setDesc(desc);
		promotion.setPromotionId("P"+System.currentTimeMillis());
		ApplicationConstants.PROMOTIONS.add(promotion);
		return "Item "+items+" is added for Promotion";
	}

	@Override
	public Promotion getPromtionById(String promotionId) {
		if(!ApplicationConstants.PROMOTIONS.isEmpty()) {
			for(Promotion prom:ApplicationConstants.PROMOTIONS) {
				if(prom.getPromotionId().equals(promotionId)) {
					return prom;
				}
			}
		}
		return null;	
	}

	@Override
	public List<Promotion> getAllPromtionDetails(){
		return ApplicationConstants.PROMOTIONS;
	}

	@Override
	public int removePromotion(String promotionId) {
		int noOfRecordUpdated=0;
		if(!ApplicationConstants.PROMOTIONS.isEmpty()) {
			Iterator<Promotion> prom = ApplicationConstants.PROMOTIONS.iterator();
			while(prom.hasNext()) {
				Promotion promo=prom.next();
				if(promo.getPromotionId().equals(promotionId)) {
					ApplicationConstants.PROMOTIONS.remove(promo);
					noOfRecordUpdated++;
					break;
				}
			}
		}
		return noOfRecordUpdated;
	}

	@Override
	public String addToCart(String item,Integer qty) {
		if(!ApplicationConstants.CART.containsKey(item)) {
			ApplicationConstants.CART.put(item, qty);
		}else {
			qty=qty+ApplicationConstants.CART.get(item);
			ApplicationConstants.CART.put(item, qty);
		}
		Promotion promotion = ApplicationConstants.PROMOTIONS.stream().filter(prom->prom.getItems().contains(item))
				.findAny().orElse(null);
		if(promotion!= null && !ApplicationConstants.CART_PROMOTIONS.contains(promotion.getPromotionId())) {
			ApplicationConstants.CART_PROMOTIONS.add(promotion.getPromotionId());
		}
		return "Item "+ item+" added to cart with "+qty+" qty.";
	}

	@Override
	public ShopingCart checkOut() {
		ShopingCart shopingCart = null;
		if(!ApplicationConstants.CART_PROMOTIONS.isEmpty()) {
			shopingCart = new ShopingCart();
			for(String promoId:ApplicationConstants.CART_PROMOTIONS) {
				Promotion promotion = ApplicationConstants.PROMOTIONS.stream().filter(prom->prom.getPromotionId().equals(promoId))
						.findAny().orElse(null);
				if(promotion.getItems().size()>1) {
					promotionWithMultiItems(shopingCart, promotion);
				}else {
					promotionWithSingleItem(shopingCart, promotion);
				}
			}
		}

		if(!ApplicationConstants.CART.isEmpty()) {
			if(shopingCart == null) {
				shopingCart = new ShopingCart();
			}
			for(String item: ApplicationConstants.CART.keySet()) {
				SkuItems skuItem= null;
				if(!shopingCart.getSkuFinalPriceLis().isEmpty()) {
					skuItem=shopingCart.getSkuFinalPriceLis().stream().filter(sku->sku.getItem().equals(item)).findAny().orElse(null);
				}
				if(skuItem == null) {
					skuItem=new SkuItems();
					skuItem.setItem(item);
					skuItem.setUnit(ApplicationConstants.CART.get(item));
					skuItem.setPrice(ApplicationConstants.CART.get(item)*ApplicationConstants.SKU_ITEMS.stream().filter(sku->sku.getItem().equals(item)).findAny().orElse(null).getPrice());
					shopingCart.getSkuFinalPriceLis().add(skuItem);
				}
			}
		}
		ApplicationConstants.CART.clear();
		ApplicationConstants.CART_PROMOTIONS.clear();
		return shopingCart;
	}


	private void promotionWithMultiItems(ShopingCart shopingCart, Promotion promotion) {
		SkuItems skuItems;
		boolean allItemFlag=false;
		int cartItemQty=0;
		for(String promotionItems:promotion.getItems()) {
			if(ApplicationConstants.CART.containsKey(promotionItems)) {
				allItemFlag=true;
				cartItemQty=cartItemQty+ApplicationConstants.CART.get(promotionItems);
			}else {
				allItemFlag=false;
			}
		}
		if(allItemFlag && cartItemQty==promotion.getUnits()) {
			int firstItem=0;
			for(String item:promotion.getItems()) {
				skuItems = new SkuItems();
				skuItems.setItem(item);
				skuItems.setUnit(ApplicationConstants.CART.get(item));
				if(firstItem == 0) {
					skuItems.setPrice(promotion.getPrices());
				}
				else {
					skuItems.setPrice(0);
				}
				firstItem++;
				shopingCart.getSkuFinalPriceLis().add(skuItems);
			}
		}
	}

	private void promotionWithSingleItem(ShopingCart shopingCart, Promotion promotion) {
		SkuItems skuItems;
		for(String promotionItems:promotion.getItems()) {
			if(ApplicationConstants.CART.containsKey(promotionItems) && ApplicationConstants.CART.get(promotionItems)>=promotion.getUnits()) {
				skuItems = new SkuItems();
				skuItems.setItem(promotionItems);
				skuItems.setUnit(ApplicationConstants.CART.get(promotionItems));
				int cartQty=ApplicationConstants.CART.get(promotionItems);
				int qty=cartQty-promotion.getUnits();
				float finalPrice=promotion.getPrices();
				if(qty > 0) {
					int qtyMultiplyer=cartQty/promotion.getUnits();
					float orignalPrice = ApplicationConstants.SKU_ITEMS.stream().filter(sku->sku.getItem().equals(promotionItems)).findAny().orElse(null).getPrice();
					if(qtyMultiplyer==1)
						finalPrice= qtyMultiplyer * promotion.getPrices() + (cartQty-promotion.getUnits()) * orignalPrice;
					else
						finalPrice= qtyMultiplyer * promotion.getPrices() + (cartQty-(promotion.getUnits()*qtyMultiplyer)) * orignalPrice;
					skuItems.setPrice(finalPrice);
				}else {
					finalPrice=promotion.getPrices();
					skuItems.setPrice(finalPrice);
				}
				shopingCart.getSkuFinalPriceLis().add(skuItems);
			}
		}
	}
}
