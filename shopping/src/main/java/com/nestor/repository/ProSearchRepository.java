package com.nestor.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nestor.entity.Product;
import com.nestor.vo.ProductWithSingleImage;

public interface ProSearchRepository   extends JpaRepository<Product, String> {
	//原生SQL实现更新方法接口
		//order by  product_name, product_description, flower_material, product_scene, product_package
		@Transactional
		@Query(value = "select p.product_id as productId, p.product_name as productName, p.product_description as productDescription, p.product_original_price as productOriginalPrice," 
						    + "p.product_discount_price as productDiscountPrice ,p.product_stock as productStock, p.flower_material as flowerMaterial , "  
				            + "p.product_package as productPackage, p.product_scene as productScene , p.distribution as distribution, p.sale_volume as saleVolume," 
				            + "t2.image_url as imageUrl from  product p left join (select product_id,max(image_url)as image_url from product_image group by product_id ) t2 on p.product_id =t2.product_id "
				            + "where match(p.product_name,p.product_description,p.flower_material,p.product_package,p.product_scene) against(?1) ORDER BY ?2 desc", nativeQuery = true)  
		@Modifying  
		public ArrayList<ProductWithSingleImage> findProductList(String value,String order); 
		
		//原生SQL实现更新方法接口
		//order by  xiaoliang and jiage
		@Transactional
		@Query(value = "select p.product_id as productId, p.product_name as productName, p.product_description as productDescription, p.product_original_price as productOriginalPrice,"
				+ "p.product_discount_price as productDiscountPrice ,p.product_stock as productStock, p.flower_material as flowerMaterial , "
				+ "p.product_package as productPackage, p.product_scene as productScene , p.distribution as distribution, p.sale_volume as saleVolume,"
				+ "t2.image_url as imageUrl from  product p left join (select product_id,max(image_url)as image_url from product_image group by product_id ) t2 on p.product_id =t2.product_id "
				+ "order by ?1 desc,p.product_discount_price desc", nativeQuery = true)  
		@Modifying  
		public ArrayList<ProductWithSingleImage> findPListOrderBy(String order); 
		


}
