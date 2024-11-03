package id.dojo.kriptografi.method;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Method {

    public static String CaesarEncrypt(String plain, int key) {
        StringBuilder cipher = new StringBuilder();
        for (char c : plain.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char encryptedChar = (char) ((c - base + key) % 26 + base);
                cipher.append(encryptedChar);
            } else {
                cipher.append(c);
            }
        }
        return cipher.toString();
    }

    public static String CaesarDecrypt(String cipher, int key) {
        return CaesarEncrypt(cipher, 26 - (key % 26));
    }

    public static String VigenereEncrypt(String plain, String key) {
        if (plain == null || key == null || plain.isEmpty() || key.isEmpty()) {
            return "";
        }
        StringBuilder cipher = new StringBuilder();
        String upperKey = key.toUpperCase();
        int keyLength = upperKey.length();
        int keyIndex = 0;

        for (char c : plain.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shift = upperKey.charAt(keyIndex) - 'A';
                char encryptedChar = (char) ((c - base + shift) % 26 + base);
                cipher.append(encryptedChar);
                keyIndex = (keyIndex + 1) % keyLength;
            } else {
                cipher.append(c);
            }
        }
        return cipher.toString();
    }

    public static String VigenereDecrypt(String cipher, String key) {
        if (cipher == null || key == null || cipher.isEmpty() || key.isEmpty()) {
            return "";
        }
        StringBuilder plain = new StringBuilder();
        String upperKey = key.toUpperCase();
        int keyLength = upperKey.length();
        int keyIndex = 0;

        for (char c : cipher.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shift = upperKey.charAt(keyIndex) - 'A';
                char decryptedChar = (char) ((c - base - shift + 26) % 26 + base);
                plain.append(decryptedChar);
                keyIndex = (keyIndex + 1) % keyLength;
            } else {
                plain.append(c);
            }
        }
        return plain.toString();
    }

    // RC4 Encryption
    public static String RC4Encrypt(String plain, String key) {
        List<Integer> S = KSA(key);
        return Base64.getEncoder().encodeToString(PRGA(S, plain).getBytes(StandardCharsets.UTF_8));
    }

    // RC4 Decryption
    public static String RC4Decrypt(String cipher, String key) {
        List<Integer> S = KSA(key);
        byte[] decodedBytes = Base64.getDecoder().decode(cipher);
        return PRGA(S, new String(decodedBytes, StandardCharsets.UTF_8));
    }

    private static List<Integer> KSA(String key) {
        int keyLength = key.length();
        List<Integer> S = new ArrayList<>(256);

        for (int i = 0; i < 256; i++) {
            S.add(i);
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S.get(i) + key.charAt(i % keyLength)) % 256;
            int temp = S.get(i);
            S.set(i, S.get(j));
            S.set(j, temp);
        }
        return S;
    }

    private static String PRGA(List<Integer> S, String plaintext) {
        StringBuilder result = new StringBuilder();
        int i = 0, j = 0;

        for (char c : plaintext.toCharArray()) {
            i = (i + 1) % 256;
            j = (j + S.get(i)) % 256;
            int temp = S.get(i);
            S.set(i, S.get(j));
            S.set(j, temp);

            char keystreamByte = (char) S.get((S.get(i) + S.get(j)) % 256).intValue();
            result.append((char) (c ^ keystreamByte));
        }
        return result.toString();
    }


    public static String BlockECBEncrypt(String plain, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String BlockECBDecrypt(String cipher, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher decipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = decipher.doFinal(Base64.getDecoder().decode(cipher));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static final String VIGENERE_KEY = "Ahdzkr";
    private static final String RC4_KEY = "KanangHerdaya";
    private static final String AES_KEY = "Josuawarwukakaka";

    public static String superEncrypt(String plaintext, int key) throws Exception {
        String step1 = CaesarEncrypt(plaintext, key);
        String step2 = VigenereEncrypt(step1, VIGENERE_KEY);
        String step3 = RC4Encrypt(step2, RC4_KEY);
        return BlockECBEncrypt(step3, AES_KEY);
    }

    public static String superDecrypt(String ciphertext, int key) throws Exception {
        String step1 = BlockECBDecrypt(ciphertext, AES_KEY);
        String step2 = RC4Decrypt(step1, RC4_KEY);
        String step3 = VigenereDecrypt(step2, VIGENERE_KEY);
        return CaesarDecrypt(step3, key);
    }
    
}
