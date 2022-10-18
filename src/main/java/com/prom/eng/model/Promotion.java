package com.prom.eng.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Promotion {
	
	private String promotionId;
	private List<String> items;
	private Integer units;
	private float prices;
	private String desc;

}
