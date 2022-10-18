package com.prom.eng.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prom.eng.model.Promotion;
import com.prom.eng.model.ShopingCart;
import com.prom.eng.model.SkuItems;
import com.prom.eng.service.PromotionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@Api("Promotion Engine Services")
@RequiredArgsConstructor
@RequestMapping("/promotion/v1")
@Validated
public class PromotionEngineController {
	
	@Autowired
	PromotionService promotionService;
	
	@PutMapping("/addSkuItem")
	@ApiOperation(value = "Add SKU")
	public String addSkuItem(@RequestParam String item,@RequestParam Float price) {
		return promotionService.addSkuItems(item, price);
	}
	
	@PostMapping("/updateSkuItems")
	@ApiOperation(value = "Update SKU")
	public int updateSkuItems(@RequestParam String item,@RequestParam Float price) {
		return promotionService.updateSkuItems(item, price);
	}
	
	@GetMapping("/getAllSkuItems")
	@ApiOperation(value = "Get all SKU Items")
	public List<SkuItems> getAllSkuItems(){
		return promotionService.getAllSkuItems();
	}
	
	@GetMapping("/getSkuItem")
	@ApiOperation(value = "Get single SKU Item as per Item")
	public SkuItems getSkuItem(@RequestParam String item) {
		return promotionService.getSkuItem(item);
	}
	
	@DeleteMapping("/deleteSkuItem")
	@ApiOperation(value = "delete single SKU Item as per Item")
	public int deleteSkuItem(@RequestParam String item) {
		return promotionService.deleteSkuItem(item);
	}
	
	@PutMapping("/addSingleItemPromotion")
	@ApiOperation(value = "Add Single Item Promotion")
	public String addSingleItemPromotion( @RequestParam String item,@RequestParam Integer unit,@RequestParam Float price, @RequestParam String desc) {
		return promotionService.addSingleItemPromotion(item, unit, price, desc);
	}
	
	@PutMapping("/addMultiItemPromotion")
	@ApiOperation(value = "Add Multiple Item combined as promotion")
	public String addMultiItemPromotion( @RequestParam List<String> item,@RequestParam Integer unit,@RequestParam Float price,@RequestParam String desc) {
		return promotionService.addMultiItemPromotion(item, unit, price, desc);
	}
	
	@GetMapping("/getPromtionBuId")
	@ApiOperation(value = "Get Promotion details per Id")
	public Promotion getPromtionById(@RequestParam String promotionId) {
		return promotionService.getPromtionById(promotionId);
	}
	
	@GetMapping("/getAllPromtionDetails")
	@ApiOperation(value = "Get All Promotion details")
	public List<Promotion> getAllPromtionDetails() {
		return promotionService.getAllPromtionDetails();
	}
	
	@DeleteMapping("/removePromotion")
	@ApiOperation(value = "Remove Specific Promotion details")
	public int removePromotion(@RequestParam String promotionId) {
		return promotionService.removePromotion(promotionId);
	}
	
	@PutMapping("/addItemToCart")
	@ApiOperation(value = "Adding Item to cart")
	public String addToCart(@RequestParam  String item, @RequestParam Integer qty) {
		return promotionService.addToCart( item, qty) ;
	}
	
	@GetMapping("checkoutCart")
	@ApiOperation(value = "Checkout Card")
	public ShopingCart checkoutCart() {
		return promotionService.checkOut() ;
	}
}
