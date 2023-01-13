package org.noahsrak.rsa;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/2
 */
public class RSATest {

    static String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ1+FEyLrRvi5ArGwIPv3gtyXidT/EfWatSj22OQq25gPhfe4JIC2mTjFdLUr4qdvW0oMYDmQ7qbGXpV59j+UvUCAwEAAQ==";
    static String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAnX4UTIutG+LkCsbAg+/eC3JeJ1P8R9Zq1KPbY5CrbmA+F97gkgLaZOMV0tSvip29bSgxgOZDupsZelXn2P5S9QIDAQABAkBQ0GpgIOgyQAneUcBls0cQPp57tcfloOvusyoXfBvLpbu4mjffXqmQEjJ6sfRe3jVug0d6ZWq4UpB3/R2C2819AiEA8BU3mRKLkbVRI7vT1OUewOBPhuavD/ZVT4WkVzNPju8CIQCn7xrk28sqlJvIzub6kSLIK7x2Vm1lAZTSvnkdXTG8WwIhAKgvxOYoFrw5kCD/WPOAmt21hiDUA/mjsLnnvmwUFvzjAiAzIXf8yncXZdvwzh5BmEMQ01A0b6grIsofSGMz75YfFQIgX37OESe+SffGOsSnLda+0xm8WlSYzb5/IRnwKM2suZo=";

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        buildRsaKeys();
        BuildCipher();
    }

    public static void BuildCipher() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String ciphertext = RSAUtils.privateEncrypt("0123456789012345678901234567890123456789012345678901234567890123456789", RSAUtils.getPrivateKey(privateKey));
        System.out.println(ciphertext);
        System.out.println(RSAUtils.publicDecrypt(ciphertext, RSAUtils.getPublicKey(publicKey)));
    }

    public static void buildRsaKeys() {
        Map<String, String> map = RSAUtils.createKeys(512);


        String publicKey = map.get("publicKey");
        String privateKey = map.get("privateKey");

        System.out.println(publicKey);
        System.out.println(privateKey);
    }
}
