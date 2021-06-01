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
                .claim("userId",user.getId())
                .claim("userType",user.getUserType().toString())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setIssuer("Raf News")
                .signWith(key).compact();
    }
    public static SecurityUser validate(String token) throws JwtException {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return new SecurityUser(
                claims.get("userId",String.class),
                claims.getSubject(),
                claims.get("userType",String.class)
        );
    }
}
