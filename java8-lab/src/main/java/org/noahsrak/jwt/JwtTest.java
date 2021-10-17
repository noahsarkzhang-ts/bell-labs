package org.noahsrak.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/9/15
 */
public class JwtTest {

    private static final String APP_KEY = "2a9bfcc4-e49f-4b55-b936-e2e29caa4051";
    private static final String APP_SECRET = "de9fdfa9-7ce6-4037-a343-198d3132cc47";

    public static void main(String[] args) {
        /* String token = buildToken();*/
        String token= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
                + ".eyJhcHBLZXkiOiJlNTZkY2U2MS05ODgxLTRjNGUtODY3Mi1jYThiOTMzZmNmM2EiLCJ2ZXJzaW9uIjoxfQ"
                + ".1yzFNyDe0g1FQK7rO6CgZLVG94JJQcNnuZ6mylLMQBk";

        System.out.println("jwt = " + token);

        verifyToken(token);
    }

    public static Map<String, Object> getHeadClaims() {
        Map<String, Object> headerClaims = new HashMap<>();

        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
        return headerClaims;
    }

    public static Map<String, Object> getPayloadClaims() {
        Map<String, Object> payloadClaims = new HashMap<>();

        payloadClaims.put("appKey", APP_KEY);
        payloadClaims.put("version", 1);

        return payloadClaims;
    }


    public static String buildToken() {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(APP_SECRET);
            token = JWT.create().withHeader(getHeadClaims())
                    .withPayload(getPayloadClaims())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            token = "";
        }
        return token;
    }

    public static boolean verifyToken(String token) {

        boolean result = false;

        try {
            Algorithm algorithm = Algorithm.HMAC256(APP_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            System.out.println("token = " + token);
            System.out.println("header = " + new String(Base64.getUrlDecoder().decode(jwt.getHeader())));
            System.out.println("payload = " + new String(Base64.getUrlDecoder().decode(jwt.getPayload())));
            System.out.println("signatre = " + jwt.getSignature());

            String signatre = Base64.getUrlEncoder().withoutPadding().encodeToString(algorithm.sign(jwt.getHeader().getBytes(), jwt.getPayload().getBytes()));
            System.out.println("verify signatre = " + signatre);

            result = signatre.equals(jwt.getSignature());
            System.out.println("result = " + result);

        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }

        return result;
    }
}
