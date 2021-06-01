package news.raf.backend.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import news.raf.backend.entities.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class Token {
    private static final String base64EncodedKey = "1QZw++HQ/NTxNFmHhD5Xqc+4uAbC127l6xCdMA/DiGU=";
    //private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Key key;
    static {
        byte[] keyData = Decoders.BASE64.decode(base64EncodedKey);
        key = new SecretKeySpec(keyData,SignatureAlgorithm.HS256.getJcaName());
    }
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
