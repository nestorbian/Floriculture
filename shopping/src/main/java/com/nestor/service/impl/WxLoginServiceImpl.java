package com.nestor.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.nestor.common.LogHttpInfo;
import com.nestor.entity.WxUser;
import com.nestor.repository.WxLoginRepository;
import com.nestor.service.WxLoginService;

@Service
public class WxLoginServiceImpl implements WxLoginService {
    @Autowired
    private WxLoginRepository wxLgn;

    private String appid = "wxa7025e69b9d8b749";
    private String appSecret = "abd4247f4848c338717e0a556c3c419e";

    /**
     * 获取获取的openid、session_key、unionid和thirdSession
     * 
     * @param loginUser
     * @return
     */
    @LogHttpInfo
    public String loginUser(String code) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appSecret
                + "&js_code=" + code + "&grant_type=authorization_code";

        // REST将资源的状态以最合适客户端或服务端的形式从服务器端转移到客户端（相反亦可）
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        // 根据返回值进行后续操作
        if (res != null && res.getStatusCode() == HttpStatus.OK) {
            String sessionData = res.getBody();
            // 谷歌爸爸提供用来在java对象和Json数据之间进行映射的类库
            Gson gson = new Gson();
            // 解析从微信服务器获取的openid、session_key、unionid
            WxUser wxSession = gson.fromJson(sessionData, WxUser.class);
            // 3rd_session = openid前十六位 + session_key前十六位
            wxSession.setThirdSession(
                    wxSession.getOpenid().substring(0, 16) + wxSession.getSession_key().substring(0, 16));
            // 将实体写入数据库
            if (wxLgn.existsById(wxSession.getOpenid())) {
                wxLgn.updateOne(wxSession.getOpenid(), wxSession.getSession_key(), wxSession.getThirdSession(),
                        wxSession.getUnionid());
            } else {
                wxLgn.save(wxSession);
            }
            // 返回 3rd_session
            return wxSession.getThirdSession();
        }

        return null;
    }

    /*
     * 用来获取用户个人信息 *
     */
    @LogHttpInfo
    public WxUser getUInfo(String thirdSession) {
        ArrayList<WxUser> wxUsers = wxLgn.findByThirdSession(thirdSession);
        if (wxUsers == null || wxUsers.size() == 0) {
            return null;
        }
        return wxUsers.get(0);
    }

    /*
     * 新增或更新用户个人信息 *
     */
    @LogHttpInfo
    public String setUInfo(WxUser wxUser) {
        if (wxUser == null) {
            return null;
        }

        wxLgn.updateBy3rd(wxUser.getAvatarurl(), wxUser.getNickname(), wxUser.getTelnum(), wxUser.getGender(),
                wxUser.getLocation(), wxUser.getBirthday(), wxUser.getProvince(), wxUser.getCity(), wxUser.getCountry(),
                wxUser.getThirdSession());
        return "ok";
    }
}