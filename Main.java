// Test the program
public class Main {
    public static void main(String[] args) {
    VigenereCipher cipher = new VigenereCipher();
    
    System.out.println("=== VIGENÈRE ENCRYPTION ===\n");
    
    // Test 1: Basic encryption
    String message = "HELLO WORLD";
    String key = "KEY";
    
    System.out.println("Original: " + message);
    System.out.println("Key: " + key);
    
    String encrypted = cipher.encrypt(message, key);
    System.out.println("Encrypted: " + encrypted);
    }
}