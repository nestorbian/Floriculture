package com.nestor.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.nestor.vo.ProductWithSingleImage;

public interface ProductSearchService {
	public  HashMap<Boolean,ArrayList<ProductWithSingleImage>>  findProductList(String value,String order); 
	
	public ArrayList<ProductWithSingleImage> findPListOrderBy();
	


}
