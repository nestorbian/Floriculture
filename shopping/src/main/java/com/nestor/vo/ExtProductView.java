package com.nestor.vo;

import java.util.List;

public interface ExtProductView extends ProductView {
	
	List<CategoryItemView> getCategories();
	
}
