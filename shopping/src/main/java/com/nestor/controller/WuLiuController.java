package com.nestor.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/wuliuController")
public class WuLiuController extends BaseController {

	// 暂时弃用
	@GetMapping(path = "/getWuLiu")
	public ResponseEntity<String> getWuliu(String type, String postid, String temp, String phone) {
		String url = "https://googleads.g.doubleclick" + ".net/pagead/conversion/?ai=CCs0THc6xXOrUBJbgqAG"
				+ "-n5moAcyp6YVWvZXAx_QI8C4QASCzsa0dYJ3R1IGwBcgBCakCsMhuPRsFhD6oAwHIA5sEqgTCAU_Qf2QpHXiQeo9uP0MhTGszzLmVvb9-j8z0C2sHVCwlGqW0zyGBjO7oqAlc_odPrcej4rkJMpCuL9g-Lqxe_n-zNrCCDtv2DK5hyVODx3Ov36RVNYG0qIo1bkzJsunJpJurt-n0wpynVKZRDu96io70jRrKxWoQp9h1ttgcSWq-WFnEDOSeCHLwXkLnPmr-r3PeUR85s525tG-7PDqhEvND2rTuaIaNn9k4gWKmrrHO8Nwr6Nbun7c1PTF3dz_Y7Y-b4AQDkAYBoAZMgAf-xMZriAcBkAcCqAeOzhuoB9XJG6gHwdMbqAfg0xuoB7oGqAfZyxuoB8_MG6gHpr4b2AcA0ggGCAAQAhgCgAoBsBOvqrkG0BMA2BMD2BQB&sigh=Ydk93lOlOtI&cid=CAQSMADwy9IZlhTZeGo2PfhrHlrExXRwPtGn-pImmEstkP0bbLmUHqSEHILtP37iNiy3ow&label=window_focus&gqid=Hc6xXLLxA43drQS5ir3oCA&qqid=CKrXrLqBzeECFRYwKgodvk8GFQ&fg=1";

		// REST将资源的状态以最合适客户端或服务端的形式从服务器端转移到客户端（相反亦可）
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		System.out.println(res);
		return res;
	}

}
