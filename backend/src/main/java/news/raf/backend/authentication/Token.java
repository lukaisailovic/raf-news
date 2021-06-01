package news.raf.backend.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import news.raf.backend.entities.User;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class Token {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static String generate(User user){
        return Jwts.builder()
                .setId(String.valueOf(UUID.randomUUID()))
                .claim("userid",user.getId())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setIssuer("Raf News")
                .signWith(key).compact();
    }
    public static String validate(String token) throws JwtException {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
