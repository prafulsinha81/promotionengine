package com.prom.eng.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.prom.eng.model.Promotion;
import com.prom.eng.model.SkuItems;

public class ApplicationConstants {
	
	public static final List<SkuItems> SKU_ITEMS= new ArrayList();
	public static final List<Promotion> PROMOTIONS= new ArrayList();
	public static final Map<String,Integer> CART = new LinkedHashMap<String,Integer>();
	public static final List<String> CART_PROMOTIONS= new ArrayList();
}
