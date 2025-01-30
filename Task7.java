import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            if (new String(decrypted).equals(PLAINTEXT)) {
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
        if (hex == null || hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string");
        }
        
        byte[] result = new byte[hex.length() / 2];
        try {
            for (int i = 0; i < result.length; i++) {
                result[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex characters in input", e);
        }
    }
}