package common.types;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@UtilityClass
public final class UtilAes {

    private static final String TIPO = "AES";


    public static String encrypt(String data, String clave) {
        Cipher cipher = null;
        try {
            Key secret = generarKey(clave);
            cipher = Cipher.getInstance(TIPO);
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            byte[] ciphertext = cipher.doFinal(data.getBytes());
            return Base64.encodeBase64String(ciphertext);
        }
        catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String dataEncriptada, String clave) {
        Cipher cipher = null;
        try {
            Key secret = generarKey(clave);
            cipher = Cipher.getInstance(TIPO);
            cipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] decordedValue = Base64.decodeBase64(dataEncriptada);
            byte[] decValue = cipher.doFinal(decordedValue);
            return new String(decValue);
        }
        catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidKeySpecException e) {
        }
        return null;
    }

    private static Key generarKey(String clave) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(clave.getBytes(), TIPO);
    }
}
