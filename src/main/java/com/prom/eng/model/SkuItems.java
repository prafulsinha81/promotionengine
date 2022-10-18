package com.prom.eng.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SkuItems {

	private String item;
	private Integer unit = 1;
	private float price;
}
