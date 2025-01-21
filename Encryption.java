import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class Encryption {
    private static final String ALGORITHM = "AES";
    private static final String KEY_FILE = "encryption.key";
    private SecretKey secretKey;

    public Encryption() {
        try {
            // Try to load existing key or create new one
            if (new File(KEY_FILE).exists()) {
                loadKey();
            } else {
                generateKey();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateKey() throws Exception {
        // Generate a new encryption key
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128);
        this.secretKey = keyGenerator.generateKey();
        
        // Save the key for future use
        try (FileOutputStream fos = new FileOutputStream(KEY_FILE)) {
            fos.write(secretKey.getEncoded());
        }
    }

    private void loadKey() throws Exception {
        // Load existing key from file
        try (FileInputStream fis = new FileInputStream(KEY_FILE)) {
            byte[] encodedKey = fis.readAllBytes();
            this.secretKey = new SecretKeySpec(encodedKey, ALGORITHM);
        }
    }

    public String encryptData(String data) throws Exception {
        if (data == null || data.isEmpty()) {
            return data;
        }

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptData(String encryptedData) throws Exception {
        if (encryptedData == null || encryptedData.isEmpty()) {
            return encryptedData;
        }

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    // Methods to encrypt/decrypt entire files
    public void encryptFile(String sourceFile, String destFile) throws Exception {
        try (FileReader reader = new FileReader(sourceFile);
             FileWriter writer = new FileWriter(destFile)) {
            
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            
            String encryptedContent = encryptData(content.toString());
            writer.write(encryptedContent);
        }
    }

    public void decryptFile(String sourceFile, String destFile) throws Exception {
        try (FileReader reader = new FileReader(sourceFile);
             FileWriter writer = new FileWriter(destFile)) {
            
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            
            String decryptedContent = decryptData(content.toString());
            writer.write(decryptedContent);
        }
    }
}