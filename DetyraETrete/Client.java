package DetyraETrete;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;
    private static KeyPair clientKeyPair;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
        clientKeyPair = CryptoUtils.generateKeyPair();

        try (Socket serverSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
             PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
             BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Welcome to Simplified SSH Client.");
            System.out.println("Attempting to connect to the SSH server...");

            String serverPubKeyStr = in.readLine();
            PublicKey serverPublicKey = CryptoUtils.getPublicKeyFromString(serverPubKeyStr);

        byte[] sharedSecret = CryptoUtils.generateSharedSecret(clientKeyPair.getPrivate(), serverPublicKey);

            String signature = in.readLine();
            boolean isVerified = CryptoUtils.verifySignature(serverPublicKey, "Server Authentication", signature);
            if (!isVerified) {
                System.out.println("Server verification failed. Exiting...");
                return;
            }

            String hmac = in.readLine();
            String expectedHmac = CryptoUtils.generateHMAC("Server Authentication", sharedSecret);
            if (!hmac.equals(expectedHmac)) {
                System.out.println("HMAC verification failed. Exiting...");
                return;
            }

