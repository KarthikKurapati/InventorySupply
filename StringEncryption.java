import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class StringEncryption {
    private static final String ALGORITHM = "AES";
    private SecretKey secretKey;

    public StringEncryption() throws Exception {
        // Generate a new encryption key
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128); // Using 128-bit AES
        this.secretKey = keyGenerator.generateKey();
    }

    public String encrypt(String input) throws Exception {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        // Create and initialize cipher for encryption
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt the input string
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());

        // Encode to Base64 for easy storage/transmission
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedInput) throws Exception {
        if (encryptedInput == null || encryptedInput.isEmpty()) {
            throw new IllegalArgumentException("Encrypted input cannot be null or empty");
        }

        // Create and initialize cipher for decryption
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Decode from Base64 and decrypt
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));

        // Convert back to string
        return new String(decryptedBytes);
    }

    // Example usage
    public static void main(String[] args) {
        try {
            StringEncryption encryptor = new StringEncryption();
            
            String originalText = "|Red Striped,Blue Cross,Green Floral,Black Solid,White Dotted,Purple Check,Yellow Floral,Grey Solid,Pink Dotted,Orange Striped,Navy Blue,Maroon,Teal,Olive Green,Red Plaid,Blue Paisley,Green Solid,Black Check,White Floral,Purple Striped,Yellow Solid,Grey Check,Pink Floral,Orange Solid,Navy Striped,Maroon Dotted,Teal Check,Olive Plaid,Red Paisley,Blue Solid,Green Check,Black Floral,White Striped,Purple Solid,Yellow Check,Grey Floral,Pink Solid,Orange Check,Navy Floral,Maroon Plaid,Teal Paisley,Olive Solid,Red Check,Blue Floral,Green Striped,Black Dotted,White Check,Purple Floral,Yellow Plaid,Grey Paisley,Brown Solid,1,\n" + //
                                "|Shirt,Jeans,Sari,Kurta,Dress,Skirt,Pants,Blouse,Top,Jacket,Sweater,Scarf,Blazer,Shorts,Leggings,Tunic,Shawl,Suit,Jumpsuit,Cardigan,Hoodie,Vest,Gown,Robe,Palazzo,Kurti,Dhoti,Salwar,Lehnga,Dupatta,Saree,Kimono,Cape,Poncho,Tank Top,Crop Top,Midi Dress,Maxi Dress,Mini Dress,Pencil Skirt,A-line Skirt,Pleated Skirt,Wrap Dress,Shift Dress,Bodycon Dress,Denim Jacket,Bomber Jacket,Trench Coat,Peplum Top,Tulle Skirt,Denim Shirt,Sari,\n" + //
                                "|45.0,32.0,89.0,56.0,78.0,43.0,67.0,34.0,54.0,76.0,45.0,65.0,43.0,87.0,56.0,78.0,34.0,65.0,43.0,98.0,54.0,67.0,89.0,45.0,76.0,34.0,65.0,87.0,43.0,56.0,78.0,98.0,45.0,67.0,34.0,89.0,54.0,76.0,43.0,65.0,87.0,34.0,56.0,78.0,98.0,45.0,67.0,89.0,54.0,76.0,82.0,1.0,\n" + //
                                "|89.0,65.0,123.0,89.0,134.0,87.0,98.0,65.0,87.0,123.0,78.0,98.0,76.0,134.0,89.0,123.0,65.0,98.0,76.0,145.0,87.0,98.0,134.0,78.0,123.0,65.0,98.0,134.0,76.0,89.0,123.0,145.0,78.0,98.0,65.0,134.0,87.0,123.0,76.0,98.0,134.0,65.0,89.0,123.0,145.0,78.0,98.0,134.0,87.0,123.0,156.0,1.0,\n" + //
                                "|101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,1,\n" + //
                                "|Mumbai,Delhi,Chennai,Kolkata,Bangalore,Hyderabad,Pune,Jaipur,Ahmedabad,Surat,Mumbai,Delhi,Chennai,Kolkata,Bangalore,Hyderabad,Pune,Jaipur,Ahmedabad,Surat,Mumbai,Delhi,Chennai,Kolkata,Bangalore,Hyderabad,Pune,Jaipur,Ahmedabad,Surat,Mumbai,Delhi,Chennai,Kolkata,Bangalore,Hyderabad,Pune,Jaipur,Ahmedabad,Surat,Mumbai,Delhi,Chennai,Kolkata,Bangalore,Hyderabad,Pune,Jaipur,Ahmedabad,Surat,Nagpur,1,\n" + //
                                "|";
            System.out.println("Original Text: " + originalText);

            // Encrypt the text
            String encryptedText = encryptor.encrypt(originalText);
            System.out.println("Encrypted Text: " + encryptedText);

            // Decrypt the text
            String decryptedText = encryptor.decrypt(encryptedText);
            System.out.println("Decrypted Text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}