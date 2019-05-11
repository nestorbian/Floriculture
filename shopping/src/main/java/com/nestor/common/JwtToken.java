package com.nestor.common;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;

/**
 * <p>生成JWT、解析JWT、验证JWT</p>
 * @author Lenovo
 *
 */
@Component
public class JwtToken {
    
    // TODO
    public static void generate() {
        DefaultClaims defaultClaims = new DefaultClaims(null);
        Jwts.builder();
    }
    
}
