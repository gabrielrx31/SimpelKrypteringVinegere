/**
 * Vigenère Cipher Encryption
 * 
 * Vigenère is a polyalphabetic substitution cipher.
 * Instead of shifting all letters by the same amount (like Caesar),
 * we use a keyword where each letter indicates a different shift.
 * 
 * Example with key="ABC":
 * A = shift 0, B = shift 1, C = shift 2
 * 
 * Text:    H E L L O W O R L D
 * Key:     A B C A B C A B C A (repeats)
 * Shift:   0 1 2 0 1 2 0 1 2 0
 * Result:  H F N L P Y O S N D
 */
public class VigenereCipher {
    
    /**
     * Encrypts text using the Vigenère algorithm
     * 
     * @param plaintext The text to be encrypted
     * @param key The keyword (e.g., "SECRET")
     * @return Encrypted text
     */
    public String encrypt(String plaintext, String key) {
        // Validate input
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key must not be empty!");
        }
        
        StringBuilder ciphertext = new StringBuilder();
        
        // Convert to uppercase
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();
        
        // Remove all non-letters from key
        // We only want to use A-Z in the key
        key = key.replaceAll("[^A-Z]", "");
        
        // Check that key still has letters after cleaning
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Key must contain at least one letter!");
        }
        
        // Keep track of position in the keyword
        int keyIndex = 0;
        
        // Go through each character in the text
        for (int i = 0; i < plaintext.length(); i++) {
            char currentChar = plaintext.charAt(i);
            
            if (Character.isLetter(currentChar)) {
                // STEP 1: Find the current key letter
                // We use modulo to "wrap around" in the keyword
                // If key="ABC" and keyIndex=3, then 3%3=0 (starts over)
                char keyChar = key.charAt(keyIndex % key.length());
                
                // STEP 2: Calculate shift from the key letter
                // 'A' gives shift=0, 'B' gives shift=1, etc.
                int shift = keyChar - 'A';
                
                // STEP 3: Find position of current letter (0-25)
                int position = currentChar - 'A';
                
                // STEP 4: Shift the position (like Caesar, but with variable shift)
                int newPosition = (position + shift) % 26;
                
                // STEP 5: Convert back to letter
                char newChar = (char) ('A' + newPosition);
                
                ciphertext.append(newChar);
                
                // IMPORTANT: Only move to next key letter when we encrypt a letter!
                // Spaces and punctuation do NOT affect keyIndex
                keyIndex++;
            } else {
                // Keep non-letters as they are
                ciphertext.append(currentChar);
            }
        }
        
        return ciphertext.toString();
    }
    
    /**
     * Decrypts text encrypted with Vigenère
     * 
     * @param ciphertext Encrypted text
     * @param key The keyword that was used
     * @return Original text
     */
    public String decrypt(String ciphertext, String key) {
        // Validate input
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key must not be empty!");
        }
        
        StringBuilder plaintext = new StringBuilder();
        
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        key = key.replaceAll("[^A-Z]", "");
        
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Key must contain at least one letter!");
        }
        
        int keyIndex = 0;
        
        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);
            
            if (Character.isLetter(currentChar)) {
                // Find key letter
                char keyChar = key.charAt(keyIndex % key.length());
                
                // Calculate shift
                int shift = keyChar - 'A';
                
                // Find position
                int position = currentChar - 'A';
                
                // For decryption, we SUBTRACT the shift
                // We add 26 to avoid negative numbers
                // Example: position=1 (B), shift=3
                // (1 - 3 + 26) % 26 = 24 % 26 = 24 (Y)
                int newPosition = (position - shift + 26) % 26;
                
                char newChar = (char) ('A' + newPosition);
                plaintext.append(newChar);
                
                keyIndex++;
            } else {
                plaintext.append(currentChar);
            }
        }
        
        return plaintext.toString();
    }
    
    /**
     * Helper method: Show step-by-step encryption
     * Good for understanding how the algorithm works
     */
    public void demonstrateEncryption(String plaintext, String key) {
        System.out.println("=== STEP-BY-STEP ENCRYPTION ===\n");
        
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        
        System.out.println("Text:    " + plaintext);
        System.out.print("Key:     ");
        
        int keyIndex = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            if (Character.isLetter(plaintext.charAt(i))) {
                System.out.print(key.charAt(keyIndex % key.length()));
                keyIndex++;
            } else {
                System.out.print(" ");
            }
        }
        System.out.println();
        
        System.out.print("Shift:   ");
        keyIndex = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            if (Character.isLetter(plaintext.charAt(i))) {
                char keyChar = key.charAt(keyIndex % key.length());
                int shift = keyChar - 'A';
                System.out.print(shift);
                keyIndex++;
            } else {
                System.out.print(" ");
            }
        }
        System.out.println();
        
        System.out.println("Result:  " + encrypt(plaintext, key));
    }
}