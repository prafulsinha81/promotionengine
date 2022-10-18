package com.prom.eng.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
	
	private Map<SkuItems,Integer> allCheckedOutItems; 
	private List<Promotion> appliedPromotions;
	private float finalPrice;

}
