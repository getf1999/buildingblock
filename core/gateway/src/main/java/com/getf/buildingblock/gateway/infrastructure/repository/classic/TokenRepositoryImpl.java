package com.getf.buildingblock.gateway.infrastructure.repository.classic;

import com.getf.buildingblock.gateway.domain.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Date;

@Configuration
public class TokenRepositoryImpl implements TokenRepository {
    private static Logger logger = LoggerFactory.getLogger(TokenRepositoryImpl.class);
    /**
     * 秘钥
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * 过期时间(秒)
     */
    @Value("${jwt.expire}")
    private long expire;

    /**
     * 生成jwt token
     */
    public String createToken(Long userId) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

//    public Claims getClaimByToken(String token) {
//        if (StringUtils.isEmpty(token)) {
//            return null;
//        }
//        String[] header = token.split("Bearer");
//        token = header[1];
//        try {
//            return Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(token)
//                    .getBody();
//        }catch (Exception e){
//            logger.debug("validate is token error ", e);
//            return null;
//        }
//    }
}
