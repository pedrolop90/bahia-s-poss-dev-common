package common.util;

import lombok.experimental.UtilityClass;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@UtilityClass
public final class UtilRsa {

    public static final String TIPO = "X.509";
    public static final String TIPO_INSTANCE = "RSA/ECB/PKCS1Padding";

    public static byte[] encrypt(String data, byte[] publicKey) {
        byte[] response = null;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(TIPO_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            response = cipher.doFinal(data.getBytes());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static String decrypt(String data, byte[] dataPrivateKey) {
        byte[] dataBytes = Base64.getDecoder().decode(data.getBytes());
        PrivateKey privateKey = getPrivateKey(dataPrivateKey);
        return decrypt(dataBytes, privateKey);
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) {
        String response = null;
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(TIPO_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            response = new String(cipher.doFinal(data));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static X509Certificate getPublicKey(byte[] dataPublicKey) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(dataPublicKey);
            CertificateFactory cf = CertificateFactory.getInstance(TIPO);
            InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
            return (X509Certificate) cf.generateCertificate(inputStream);
        }
        catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey getPrivateKey(byte[] dataPrivateKey) {
        PrivateKey privateKey = null;
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(dataPrivateKey);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }
}
