package book.packt.fullstackspringbootreact.carapi.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private final int expirationMilliseconds;
    private final String prefix;
    private final Environment environment;

    public JwtService(
            @Value("${app.jwt.expiration.millisec}") int expirationMilliseconds,
            @Value("${app.jwt.token_prefix}") String prefix,
            Environment environment
    ) {
        this.expirationMilliseconds = expirationMilliseconds;
        this.prefix = prefix;
        this.environment = environment;
    }

    public String getToken(String username) {
        SecretKey secretKey = getSecretKey();
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + expirationMilliseconds))
                // or PrivateKey
                .signWith(secretKey)
                .compact();
    }

    public String getAuthUsername(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            return "";
        }
        return Jwts.parser()
                // or PublicKey
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token.replace(prefix, ""))
                .getPayload()
                .getSubject();
    }

    private SecretKey getSecretKey() {
        String secretKeyBase64 = environment.getRequiredProperty("secret.key1");
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyBase64));
    }

    public String getPrefix() {
        return prefix;
    }
}
