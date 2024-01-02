package CipherSolver.DecryptionLogic;

import CipherSolver.EntryPoint.Cipher;

public class TranspositionCipher implements Cipher {
    @Override
    public String decrypt(String text) {
        int textLength = text.length();
        StringBuilder decryptedText = new StringBuilder();

        for (int numColumns = 2; numColumns < textLength; numColumns++) {
            if (textLength % numColumns == 0) {
                decryptedText.append("Columns ").append(numColumns).append(": ");
                int numRows = textLength / numColumns;

                for (int c = 0; c < numColumns; c++) {
                    int index = c;
                    for (int r = 0; r < numRows; r++) {
                        decryptedText.append(text.charAt(index));
                        index += numColumns;
                    }
                }
                decryptedText.append("\n");
            }
        }
        return decryptedText.length() > 0 ? decryptedText.toString() : "No valid decryption found";
    }
}

