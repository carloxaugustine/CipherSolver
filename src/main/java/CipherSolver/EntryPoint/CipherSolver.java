package CipherSolver.EntryPoint;

import CipherSolver.CypherDetectionLogic.CipherDetector;
import CipherSolver.DecryptionLogic.CaesarCipher;
import CipherSolver.DecryptionLogic.TranspositionCipher;
import CipherSolver.DecryptionLogic.VigenereCipher;

import java.util.Scanner;

//Main Class and entry point
public class CipherSolver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter cipher text: ");
        String cipherText = scanner.nextLine();

        // Detect the likely cipher type
        String suggestedCipher = CipherDetector.detectCipher(cipherText);
        System.out.println("Suggested Cipher Type: " + suggestedCipher);

        // Ask user if they want to proceed with decryption
        System.out.println("Do you want to proceed with decryption? (yes/no)");
        String userResponse = scanner.nextLine();

        if (!userResponse.equalsIgnoreCase("yes")) {
            System.out.println("Decryption aborted by user.");
            return;
        }

        // Decryption based on the suggested cipher type
        switch (suggestedCipher) {
            case "Caesar Cipher":
                System.out.println("Decrypting as Caesar Cipher...");
                String caesarDecrypted = new CaesarCipher().decrypt(cipherText);
                System.out.println(caesarDecrypted);
                break;
            case "Vigenère Cipher":
                System.out.print("Enter the Vigenère cipher key: ");
                String key = scanner.nextLine();
                VigenereCipher vigenereCipher = new VigenereCipher(key);
                System.out.println("Decrypting as Vigenère Cipher...");
                String vigenereDecrypted = vigenereCipher.decrypt(cipherText);
                System.out.println(vigenereDecrypted);
                break;

            case "Transposition Cipher":
                System.out.println("Decrypting as Transposition Cipher...");
                String transpositionDecrypted = new TranspositionCipher().decrypt(cipherText);
                System.out.println(transpositionDecrypted);
                break;
            default:
                System.out.println("Cipher type is unknown or not supported. No decryption attempted.");
                break;
        }
    }
}

