package CipherSolver.CypherDetectionLogic;

import java.util.*;
import java.util.stream.Collectors;

public class CipherDetector {

    public static String detectCipher(String text) {
        if (isLikelyCaesarCipher(text)) {
            return "Caesar Cipher";
        } else if (isLikelyVigenereCipher(text)) {
            return "Vigenère Cipher";
        } else if (isLikelyTranspositionCipher(text)) {
            return "Transposition Cipher";
        } else {
            return "Unknown Cipher";
        }
    }

    private static boolean isLikelyCaesarCipher(String text) {
        double[] englishFrequencies = {8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074};
        double chiSquared = calculateChiSquared(text.toLowerCase(), englishFrequencies);
        return chiSquared < 150; // Threshold value, may need adjustment
    }

    private static double calculateChiSquared(String text, double[] expectedFrequencies) {
        int[] letterCounts = new int[26];
        int totalLetters = 0;

        for (char c : text.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                letterCounts[c - 'a']++;
                totalLetters++;
            }
        }

        double chiSquared = 0.0;
        for (int i = 0; i < 26; i++) {
            double observed = letterCounts[i];
            double expected = totalLetters * expectedFrequencies[i] / 100;
            if (expected != 0) {
                chiSquared += Math.pow(observed - expected, 2) / expected;
            }
        }

        return chiSquared;
    }

    private static boolean isLikelyVigenereCipher(String text) {
        Map<String, List<Integer>> sequences = findRepeatedSequences(text, 3); // Looking for sequences of length 3
        Set<Integer> spacings = new HashSet<>();

        for (Map.Entry<String, List<Integer>> entry : sequences.entrySet()) {
            List<Integer> positions = entry.getValue();
            for (int i = 0; i < positions.size() - 1; i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    spacings.add(Math.abs(positions.get(j) - positions.get(i)));
                }
            }
        }

        for (int spacing : spacings) {
            if (spacing > 3 && gcdOfSpacingSet(spacings) > 3) {
                return true;
            }
        }

        return false;
    }

    private static Map<String, List<Integer>> findRepeatedSequences(String text, int sequenceLength) {
        Map<String, List<Integer>> sequences = new HashMap<>();

        for (int i = 0; i < text.length() - sequenceLength + 1; i++) {
            String sequence = text.substring(i, i + sequenceLength);
            sequences.computeIfAbsent(sequence, k -> new ArrayList<>()).add(i);
        }

        return sequences.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private static int gcdOfSpacingSet(Set<Integer> spacings) {
        int result = 0;
        for (int spacing : spacings) {
            result = gcd(spacing, result);
        }
        return result;
    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }


    private static boolean isLikelyTranspositionCipher(String text) {
        int length = text.length();
        for (int numColumns = 2; numColumns <= length / 2; numColumns++) {
            if (length % numColumns == 0) {
                String decryptedText = attemptTranspositionDecryption(text, numColumns);
                if (isValidDecryptedText(decryptedText)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String attemptTranspositionDecryption(String text, int numColumns) {
        int numRows = text.length() / numColumns;
        StringBuilder decryptedText = new StringBuilder();
        for (int c = 0; c < numColumns; c++) {
            int index = c;
            for (int r = 0; r < numRows; r++) {
                decryptedText.append(text.charAt(index));
                index += numColumns;
            }
        }
        return decryptedText.toString();
    }

    private static boolean isValidDecryptedText(String text) {
        // This is a simple heuristic: if the decrypted text has spaces roughly where we expect words to be, it might be valid.
        // A more sophisticated approach would involve checking against an actual dictionary of words.
        int wordCount = text.split(" ").length;
        int nonLetterCount = text.length() - text.replace(" ", "").length();
        return wordCount > 5 && nonLetterCount > 5; // These are arbitrary thresholds and might need adjustment.
    }
}
