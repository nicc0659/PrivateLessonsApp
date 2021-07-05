package dao;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenUtil {


    public static String createJWTToken(String id, String issuer) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key signingKey = Keys.secretKeyFor(signatureAlgorithm);

        String builder = Jwts.builder()
                .setSubject(issuer)
                .setId(id)
                .setIssuedAt(now)
                .signWith(signingKey)
                .compact();

        return builder;
    }
}
