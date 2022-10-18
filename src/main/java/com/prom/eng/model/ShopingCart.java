package com.prom.eng.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopingCart {

	private List<SkuItems> skuFinalPriceLis = new ArrayList<>();
	private float totalPrices=0;
	
	public float getTotalPrices() {
		if(!this.skuFinalPriceLis.isEmpty()) {
			for(SkuItems sku:this.skuFinalPriceLis) {
				this.totalPrices=this.totalPrices+sku.getPrice();
			}
		}
		return this.totalPrices;
	}
}
