package com.nestor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.Constants;
import com.nestor.common.LogHttpInfo;
import com.nestor.entity.Category;
import com.nestor.entity.Result;
import com.nestor.service.CategoryService;
import com.nestor.util.CheckUtil;
import com.nestor.vo.CategoryPageView;
import com.nestor.vo.CategoryItemView;
import com.nestor.vo.CategoryView;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService service;

    /**
     * <p>添加商品分类</p>
     * 
     * @param category
     * @return
     */
    @PostMapping(path = "/admin/categories")
    @LogHttpInfo
    public Result<Map<String, String>> add(@RequestBody Category category) {
        // base check
        commonCheck(category);

        Map<String, String> map = new HashMap<>();
        map.put(Constants.ID, service.add(category));
        return new Result<>(map);
    }

    /**
     * <p>design for admin-ui</p>
     * <p>更新商品分类</p>
     * 
     * @param category
     * @return
     */
    @PutMapping(path = "/admin/categories")
    @LogHttpInfo
    public Result<Boolean> update(@RequestBody Category category) {
        // base check
        CheckUtil.notEmpty(category.getCategoryId(), "分类id不能为空");
        commonCheck(category);

        service.update(category);
        return new Result<>(true);
    }

    private void commonCheck(Category category) {
        CheckUtil.notEmpty(category.getCategoryName(), "分类名称不能为空");
        CheckUtil.notExceedMaxLength(category.getCategoryName(), 20, "分类名称长度不能超过20");
        CheckUtil.notNull(category.getNeedShowInHome(), "是否展示在首页");
        if (category.getNeedShowInHome()) {
            CheckUtil.notEmpty(category.getCategoryDescription(), "分类描述不能为空");
            CheckUtil.notEmpty(category.getImagePath(), "分类路径不能为空");
            CheckUtil.notEmpty(category.getImageUrl(), "分类url不能为空");
            CheckUtil.notExceedMaxLength(category.getCategoryDescription(), 50, "分类描述长度不能超过50");
            CheckUtil.notExceedMaxLength(category.getImagePath(), 200, "分类路径长度不能超过200");
            CheckUtil.notExceedMaxLength(category.getImageUrl(), 200, "分类url长度不能超过200");
        }
    }

    /**
     * <p>design for admin-ui</p>
     * <p>删除商品分类</p>
     * 
     * @param id
     * @return
     */
    @DeleteMapping(path = "/admin/categories")
    @LogHttpInfo
    public Result<Boolean> delete(@RequestParam(value = "id") String id) {
        service.delete(id);
        return new Result<>(true);
    }

    /**
     * <p>design for admin-ui</p>
     * <p>获取所有商品分类</p>
     * 
     * @return
     */
    @GetMapping(path = "/admin/categories")
    @LogHttpInfo
    public Result<List<CategoryItemView>> findCategories() {
        return new Result<>(service.findCategories());
    }

    /**
     * <p>design for admin-ui</p>
     * <p>分页获取所有商品分类</p>
     * 
     * @return
     */
    @GetMapping(path = "/admin/categories/{pageNumber}/{pageSize}")
    @LogHttpInfo
    public Result<Page<CategoryView>> findAll(@PathVariable int pageNumber, @PathVariable int pageSize) {
        // base check
        CheckUtil.notLessThanEqualZero(pageNumber, "pageNumber不能小于等于0");
        CheckUtil.notLessThanEqualZero(pageSize, "pageSize不能小于等于0");

        return new Result<>(service.findAll(pageNumber, pageSize));
    }
    
    /**
     * <p>获取显示在小程序主页的分类信息</p>
     * @return
     */
    @GetMapping(path = "/categories/home")
    @LogHttpInfo
    public Result<List<CategoryPageView>> findCategoryInHome() {
        return new Result<>(service.findCategoryInHome());
    }
}
