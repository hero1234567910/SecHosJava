package com.basic.javaframe.common.JWT;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.basic.javaframe.entity.Frame_User;

/**
 * 获取token
* <p>Title: TokenService</p>  
* <p>Description: </p>  
* @author hero
 */
@Service("tokenService")
public class TokenService {
	
	public String getToken(Frame_User user) {
        String token="";
        //创建token并设置过期时间
        token= JWT.create().withAudience(user.getRowGuid()).withExpiresAt(new Date(System.currentTimeMillis()+7200000))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}