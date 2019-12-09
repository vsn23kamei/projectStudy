package vsn.co.jp.projectStudy.utility;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vsn.co.jp.projectStudy.DBModel.AccessToken;
import vsn.co.jp.projectStudy.DBModel.AuthCode;
import vsn.co.jp.projectStudy.DBModel.ClientMaster;
import vsn.co.jp.projectStudy.DBModel.UserMaster;
import vsn.co.jp.projectStudy.repository.AccessTokenRepository;
import vsn.co.jp.projectStudy.repository.AuthCodeRepository;

public class TokenUtil {

    public static String VALIDATE_TIME_KEY = "validateTime";

    public static String CLIENT_CODE_KEY = "clientId";

    public static String USER_ID_KEY = "userId";

    private static String ACCESS_TOKEN_KEY = "accessToken";

    public static String createAuthToken(ClientMaster client, UserMaster user,
            AuthCodeRepository authCodeRepo) {

        String key = client.getClient_id() + client.getClient_code();

        long timeout = 600000l;

        long validTimeMillis = System.currentTimeMillis() + timeout;

        String jwsString = createJwt(client, user, validTimeMillis, key);

        AuthCode authCode = new AuthCode();
        authCode.setAuth_code(jwsString);
        authCode.setClient_id(client.getClient_id());
        authCode.setUser_id(user.getUser_id());
        authCode.setLast_modified(new Timestamp(System.currentTimeMillis()));
        authCode.setValid_datetime(new Timestamp(validTimeMillis));

        authCodeRepo.save(authCode);

        return jwsString;
    }

    public static String createAccessToken(ClientMaster client, UserMaster user,
            AccessTokenRepository accessTokenRepo) {

        long timeout = 86400000l;

        long validTimeMillis = System.currentTimeMillis() + timeout;

        String access_token = createJwt(client, user, validTimeMillis,
                ACCESS_TOKEN_KEY);

        AccessToken accessToken = new AccessToken();
        accessToken.setAccess_token(access_token);
        accessToken.setClient_id(client.getClient_id());
        accessToken.setUser_id(user.getUser_id());
        accessToken.setLast_modified(new Timestamp(System.currentTimeMillis()));
        accessToken.setValid_datetime(new Timestamp(validTimeMillis));

        accessTokenRepo.save(accessToken);

        return access_token;
    }

    public static String createJwt(ClientMaster client, UserMaster user,
            long validTimeMillis, String key) {

        byte[] secretKeyAsBytes = key.getBytes(StandardCharsets.UTF_8);

        String jwt = Jwts.builder().setSubject(client.getClient_code())
                .setAudience(user.getUser_id())
                .setExpiration(new Date(validTimeMillis))
                .signWith(SignatureAlgorithm.HS512, secretKeyAsBytes).compact();

        return jwt;
    }

    public static Map<String, String> instantDecodeJwt(String jwt)
            throws JsonMappingException, JsonProcessingException {

        String[] jwtSections = jwt.split("\\.");
        String claim = new String(Base64.getDecoder().decode(jwtSections[1]));

        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> map = new HashMap<String, String>();

        map = mapper.readValue(claim, new TypeReference<Map<String, String>>() {
        });

        return map;
    }

    public static Map<String, Object> decodeAuthCode(String jwt,
            ClientMaster client) {

        String key = client.getClient_id() + client.getClient_code();

        return decodeJwt(jwt, key);
    }

    public static Map<String, Object> decodeAccessToken(String jwt) {

        return decodeJwt(jwt, ACCESS_TOKEN_KEY);
    }

    public static Map<String, Object> decodeJwt(String jwt, String key) {

        Map<String, Object> ret = new HashMap<String, Object>();

        byte[] secretKeyAsBytes = key.getBytes(StandardCharsets.UTF_8);

        Date exp = Jwts.parser().setSigningKey(secretKeyAsBytes)
                .parseClaimsJws(jwt).getBody().getExpiration();

        String clientCode = Jwts.parser().setSigningKey(secretKeyAsBytes)
                .parseClaimsJws(jwt).getBody().getSubject();

        String userId = Jwts.parser().setSigningKey(secretKeyAsBytes)
                .parseClaimsJws(jwt).getBody().getAudience();

        ret.put(VALIDATE_TIME_KEY, exp);
        ret.put(CLIENT_CODE_KEY, clientCode);
        ret.put(USER_ID_KEY, userId);

        return ret;
    }
}
