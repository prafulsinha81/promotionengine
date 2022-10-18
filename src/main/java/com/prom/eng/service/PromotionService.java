package com.prom.eng.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.prom.eng.model.Promotion;
import com.prom.eng.model.ShopingCart;
import com.prom.eng.model.SkuItems;

@Service
public interface PromotionService {

	public String addSkuItems(String item,Float price);
	public int updateSkuItems(String item,Float price);
	public List<SkuItems> getAllSkuItems();
	public SkuItems getSkuItem(String item);
	public int deleteSkuItem(String item);
	public String addSingleItemPromotion(String item,Integer unit,Float price,String desc);
	public String addMultiItemPromotion(List<String> items,Integer unit,Float price,String desc);
	public Promotion getPromtionById(String promotionId);
	public List<Promotion> getAllPromtionDetails();
	public int removePromotion(String promotionId);
	public String addToCart(String item,Integer qty) ;
	public ShopingCart checkOut();  
}
