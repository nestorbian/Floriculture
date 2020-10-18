package com.nestor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.nestor.entity.Result;
import com.nestor.entity.UserAddress;
import com.nestor.entity.WxUser;
import com.nestor.service.UserAddressService;
import com.nestor.service.WxLoginService;
import com.nestor.util.CheckUtil;

/**
 * <p>
 * 用于记录用户选中的地址，再下次用户确认订单时不再选择地址，如果选择新地址则更新
 * </p>
 *
 * @author bzy
 */
@RestController
public class UserAddressController extends BaseController {

	@Autowired
	private UserAddressService userAddressService;

	@Autowired
	private WxLoginService wxLoginService;

	/**
	 * <p>
	 * 更新或保存用户选中的地址
	 * </p>
	 *
	 * @param token
	 * @param userAddress
	 * @return
	 */
	@PostMapping(path = "/user-addresses")
	public Result<Boolean> saveUserAddress(@RequestHeader(name = "authorization") String token,
			@RequestBody UserAddress userAddress) {
		// TODO Token解析
		WxUser wxUser = wxLoginService.getUInfo(token);
		CheckUtil.notEmpty(userAddress.getAddressId(), "address id不能为空");
		userAddress.setOpenid(wxUser.getOpenid());
		userAddressService.saveUserAddress(userAddress);
		return new Result<>(true);
	}

	/**
	 * <p>
	 * 根据openid获取userAddress
	 * </p>
	 *
	 * @param token
	 * @return
	 */
	@GetMapping(path = "/user-addresses")
	public Result<UserAddress> getUserAddress(@RequestHeader(name = "authorization") String token) {
		// TODO Token解析
		WxUser wxUser = wxLoginService.getUInfo(token);
		return new Result<>(userAddressService.getUserAddress(wxUser.getOpenid()));
	}

}
