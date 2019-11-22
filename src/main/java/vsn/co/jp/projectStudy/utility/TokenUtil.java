package vsn.co.jp.projectStudy.utility;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class TokenUtil {
    
    private static final Key KEY = MacProvider.generateKey();
    
    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current Timestamp: " + now);

        // 7日後の日時
        ZonedDateTime expirationDate = now.plusDays(7)
                .atZone(ZoneId.systemDefault());

        // JWTを生成
        System.out.println("*** JWT Create ***");
        String compactJws = Jwts.builder().setSubject("hoge")
                .setExpiration(Date.from(expirationDate.toInstant()))
                .signWith(SignatureAlgorithm.HS512, KEY).compact();
        System.out.println("JWT: " + compactJws);

        // Base64でデコードしてみる
        System.out.println("*** Base64 decode ***");
        String[] jwtSections = compactJws.split("\\.");
        String header = new String(Base64.getDecoder().decode(jwtSections[0]));
        String claim = new String(Base64.getDecoder().decode(jwtSections[1]));
        System.out.println("JWT Header: " + header);
        System.out.println("JWT Claim: " + claim);

        // クレームの確認
        System.out.println("*** Claim ***");
        Date exp = Jwts.parser().setSigningKey(KEY).parseClaimsJws(compactJws)
                .getBody().getExpiration();
        String sub = Jwts.parser().setSigningKey(KEY).parseClaimsJws(compactJws)
                .getBody().getSubject();
        System.out.println("exp(Expiration Time): " + LocalDateTime
                .ofInstant(exp.toInstant(), ZoneId.systemDefault()));
        System.out.println("sub(Subject): " + sub);

        // 署名の検証
        System.out.println("*** Signature Validation ***");
        try {
            Jwts.parser().setSigningKey(KEY).parseClaimsJws(compactJws);
            System.out.println("Use Correct Key: Validation Success");
        } catch (SignatureException e) {
            System.out.println(
                    "Use Correct Key: Validation Fail - " + e.getMessage());
        }

        Key wrongKey = MacProvider.generateKey();
        try {
            // 生成時とは別のキーを使用
            Jwts.parser().setSigningKey(wrongKey).parseClaimsJws(compactJws);
            System.out.println("Use Wrong Key: Validation Success");
        } catch (SignatureException e) {
            System.out.println(
                    "Use Wrong Key: Validation Fail - " + e.getMessage());
        }
    }
}
