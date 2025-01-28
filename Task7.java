import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

class NoKeyFoundException extends Exception {
    public NoKeyFoundException(String message) {
        super(message);
    }
}
public class Task7 {
    private static final String PLAINTEXT = "This is a top secret.";
    private static final String CIPHERTEXT_HEX = 
        "764aa26b55a4da654df6b19e4bce00f4 ed05e09346fb0e762583cb7da2ac93a2";
    private static final String IV_HEX = 
        "aabbccddeeff00998877665544332211";

public static void main(String[] args) throws Exception {
    byte[] ciphertext = hexToBytes(CIPHERTEXT_HEX.replaceAll("\\s+", ""));
    byte[] iv = hexToBytes(IV_HEX.replaceAll("\\s+", ""));

    
    for (String word : Files.readAllLines(Paths.get("words.txt"))) {
        if (word.length() >= 16) continue;
        try {
            String paddedKey = padKey(word);
            byte[] decrypted = decrypt(ciphertext, paddedKey, iv);
            if (new String(decrypted).equals(PLAINTEXT)&& false) {
                System.out.println("Found key with padding: " + paddedKey);
                System.out.println("key word: " + word);
                return; // Now we can break the loop
            }
        } catch (javax.crypto.BadPaddingException e)
        {
            // Wrong key - silently continue
            continue;
        }
        catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    throw new NoKeyFoundException("Key not Found in Word list");
}

    private static String padKey(String key) {
        StringBuilder paddedKey = new StringBuilder(key);
        while (paddedKey.length() < 16) {
            paddedKey.append('#');
        }
        return paddedKey.toString();
    }

    private static byte[] decrypt(byte[] ciphertext, String key, byte[] iv) throws Exception 
    {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        
        return cipher.doFinal(ciphertext);
    }

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}