package com.nestor.common;

import com.nestor.entity.WxUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>生成JWT、解析JWT、验证JWT</p>
 * @author bzy
 *
 */
@Component
public class JwtToken {

    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    public static final String ISSUER = "nanyahuayi";

    @Value("${jwt.key}")
    private String key = "1234567890";

    /**
     * 生成token
     * @param wxUser
     * @return
     */
    public String generate(WxUser wxUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("thirdSession", wxUser.getThirdSession());
        return Jwts.builder().setHeaderParam("typ", "JWT")
                .setClaims(map)
                .setIssuedAt(new Date())
                .setIssuer(ISSUER)
                .setAudience(wxUser.getThirdSession())
                .signWith(SIGNATURE_ALGORITHM, key)
                .compact();
    }

    /**
     * 解析token,包含验证机制
     * @param token
     * @param keyName
     * @return
     */
    public Object resolve(String token, String keyName) {
        // 验证token是否合法
        String[] items = token.split("\\.");
        if (items.length != 3) {
            throw new ParameterException("非法token");
        }
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
//        String validateToken = generate(JSONObject.wrap(claims).toString());
//
//        String sign = validateToken.split("\\.")[2];
//        if (!sign.equals(items[2])) {
//            throw new ParameterException("非法token");
//        }

        return claims.get(keyName);
    }

    /**
     * 生成token
     * @param payload
     * @return
     */
    public String generate(String payload) {
        return Jwts.builder().setHeaderParam("typ", "JWT")
                .setPayload(payload)
                .signWith(SIGNATURE_ALGORITHM, key)
                .compact();
    }

//    public static void main(String[] args) {
//        WxUser wxUser = new WxUser();
//        wxUser.setThirdSession("123456");
//        System.out.println(generate(wxUser));
//        System.out.println(resolve("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aGlyZFNlc3Npb24iOiIxMjM0NTY3IiwiYXVkIjoiMTIzNDU2IiwiaXNzIjoibmFueWFodWF5aSIsImlhdCI6MTU1ODcwODE0OH0.zP-vbG9MwR_rggVGcMX9JO7ajOOJ9IlfRcaqMg_BHlk", "thirdSession"));
//    }
}
