package com.example.be_film.components;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private int expiration = 259000;
    private String secretKey = "Y2xtbWphdmFhbmxvbG1lbmd1Y2hvZGVzZXVmaHVzaGV1ZmpmaXNqaWZqc3NzZGZzZGZzZGY="; // Đây là Base64 mã hóa của "butngumoimuaip13"

    public String generateToken(com.example.be_film.model.User user){
        //properties -> claims
        Map<String,Object>claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole().getRoleName());
        try{
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration*1000L))
//                    .claim("roles", user.getRole())
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();

            return token;

        }catch (Exception e){

         System.out.println("loi tao token: " + e);
return null;
        }
    }
    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);


    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public  <T>T extractClaim(String token, Function<Claims,T> claimsResolver  ){
        final  Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);

    }
    public boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extracUserName(String token){
        return  extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken (String token , UserDetails userDetails){
        String username = extracUserName(token);
        return (username.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }

}

