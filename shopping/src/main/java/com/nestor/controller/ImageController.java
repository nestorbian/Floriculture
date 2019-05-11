package com.nestor.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nestor.common.LogHttpInfo;
import com.nestor.entity.Result;
import com.nestor.enums.ImageType;
import com.nestor.service.ImageService;
import com.nestor.util.CheckUtil;
import com.nestor.vo.Image;

/**
 * <p>公用图片物理保存接口</p>
 * @author Lenovo
 *
 */
@RestController
@RequestMapping(path = "/admin")
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	/**
	 * 上传单张图片
	 * @param image
	 * @param type
	 * @return
	 */
	@PostMapping(path = "/images/one")
	@LogHttpInfo
	public Result<Image> saveOneImage(@RequestParam(name = "image", required = true) MultipartFile image,
									  @RequestParam(name = "type", required = true) ImageType type) {
		// base check
		CheckUtil.imageFile(image, "上传图片的格式不正确");
		
		return new Result<>(imageService.saveOneImage(image, type));
	}
	
	/**
	 * <p>上传多张图片</p>
	 * @param images
	 * @param type
	 * @return
	 */
	@PostMapping(path = "/images/many")
	@LogHttpInfo
	public Result<List<Image>> saveManyImages(@RequestParam(name = "images", required = true) MultipartFile[] images,
									  		  @RequestParam(name = "type", required = true) ImageType type) {
		// base check
		CheckUtil.imageFiles(images, "上传图片的格式不正确");
		
		return new Result<>(imageService.saveManyImages(Arrays.asList(images), type));
	}
	
}
