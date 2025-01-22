import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

public class Task7 {
    private static final String PLAINTEXT = "This is a top secret.";
    private static final String CIPHERTEXT_HEX = 
        "764aa26b55a4da654df6b19e4bce00f4 ed05e09346fb0e762583cb7da2ac93a2";
    private static final String IV_HEX = 
        "aabbccddeeff00998877665544332211";

    public static void main(String[] args) throws Exception {
        byte[] ciphertext = hexToBytes(CIPHERTEXT_HEX.replaceAll("\\s+", ""));
        byte[] iv = hexToBytes(IV_HEX.replaceAll("\\s+", ""));
        
        Files.lines(Paths.get("words.txt"))
            .filter(word -> word.length() < 16)
            .forEach(word -> {
                try {
                    String paddedKey = padKey(word);
                    byte[] decrypted = decrypt(ciphertext, paddedKey, iv);
                    if (new String(decrypted).equals(PLAINTEXT)) {
                        System.out.println("Found key: " + word);
                    }
                } catch (Exception e) {
                }
            });
    }

    private static String padKey(String key) {
        StringBuilder paddedKey = new StringBuilder(key);
        while (paddedKey.length() < 16) {
            paddedKey.append('#');
        }
        return paddedKey.toString();
    }

    private static byte[] decrypt(byte[] ciphertext, String key, byte[] iv) throws Exception {
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