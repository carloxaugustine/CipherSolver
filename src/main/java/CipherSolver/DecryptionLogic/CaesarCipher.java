package CipherSolver.DecryptionLogic;

import CipherSolver.EntryPoint.Cipher;

public class CaesarCipher implements Cipher {
    private static final int ALPHABET_SIZE = 26;

    @Override
    public String decrypt(String text) {
        StringBuilder decryptedText = new StringBuilder();
        for (int shift = 1; shift < ALPHABET_SIZE; shift++) {
            decryptedText.append("Shift ").append(shift).append(": ");
            for (char character : text.toCharArray()) {
                if (Character.isLetter(character)) {
                    char base = Character.isLowerCase(character) ? 'a' : 'A';
                    int offset = Character.toLowerCase(character) - base;
                    char shifted = (char) ((offset - shift + ALPHABET_SIZE) % ALPHABET_SIZE + base);
                    decryptedText.append(shifted);
                } else {
                    decryptedText.append(character);
                }
            }
            decryptedText.append("\n");
        }
        return decryptedText.toString();
    }
}

