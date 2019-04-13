package com.nestor.vo;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

/**
 * <p>给管理平台的分页查询分类提供视图</p>
 * @author Lenovo
 *
 */
public interface CategoryView {

    String getCategoryId();
    String getCategoryName();
    String getCategoryDescription();
    String getImagePath();
    @Value("#{@viewConvertion.getCategoryImageFullUrl(target)}")
    String getImageUrl();
    Boolean getNeedShowInHome();
    LocalDateTime getCreateTime();
    LocalDateTime getUpdateTime();

}
