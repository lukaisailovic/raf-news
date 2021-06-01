package news.raf.backend.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import news.raf.backend.entities.User;

import java.security.Key;

public class Token {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static String generate(User user){
        return Jwts.builder().setId(user.getId()).setSubject(user.getEmail()).signWith(key).compact();
    }
    public static boolean validate(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
