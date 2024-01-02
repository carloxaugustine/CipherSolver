package CipherSolver.DecryptionLogic;

import CipherSolver.EntryPoint.Cipher;

public class VigenereCipher implements Cipher {
    private String key;

    public VigenereCipher(String key) {
        this.key = key.toLowerCase();
    }

    @Override
    public String decrypt(String text) {
        StringBuilder decryptedText = new StringBuilder();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int shift = key.charAt(keyIndex) - 'a';
                char shiftedChar = (char) ((c - base - shift + 26) % 26 + base);
                decryptedText.append(shiftedChar);
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                decryptedText.append(c);
            }
        }

        return decryptedText.toString();
    }
}

