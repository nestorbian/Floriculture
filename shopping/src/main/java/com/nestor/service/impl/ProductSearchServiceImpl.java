package com.nestor.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nestor.repository.ProSearchRepository;
import com.nestor.service.ProductSearchService;
import com.nestor.vo.ProductWithSingleImage;

@Service
public class ProductSearchServiceImpl implements ProductSearchService{
	@Autowired
	private ProSearchRepository proS;

	@Override
	public  HashMap<Boolean,ArrayList<ProductWithSingleImage>>  findProductList(String value, String order) {	
		HashMap<Boolean,ArrayList<ProductWithSingleImage>> pMap = new HashMap<>();
		ArrayList<ProductWithSingleImage> proList = new ArrayList<>();
		//查询结果标志：匹配到：true 。没匹配到：false
		Boolean bl  =new Boolean(true);
		
		//处理order的映射关系
		if(order.equals("1")) {
			order ="product_original_price";
		}else if(order.equals("2")) {
			order ="sale_volume";
		}else if(order.equals("3")) {
			order ="create_time";
		}else {
			order="sale_volume";
		}
		
		proList = proS.findProductList(value,order);
		if(proList == null || proList.size()==0) {
			proList.clear();
			bl = false;
			pMap.put(bl, proS.findPListOrderBy(order));
			return pMap;
		}
		pMap.put(bl, proList);
		return pMap;
	}

	@Override
	public ArrayList<ProductWithSingleImage> findPListOrderBy() {
		return null;
	}

}
