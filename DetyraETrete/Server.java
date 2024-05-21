package DetyraETrete;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public class Server {
    private static final int PORT = 8080;
    private static PublicKey clientPublicKey;
    private static KeyPair serverKeyPair;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

  public static void main(String[] args) throws Exception {
        serverKeyPair = CryptoUtils.generateKeyPair();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server starting up...");
            System.out.println("Awaiting client connections...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected! Initiating handshake...");
                    handleClient(clientSocket);
                }
            }
        }
    }
private static void handleClient(Socket clientSocket) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


    out.println(Base64.getEncoder().encodeToString(serverKeyPair.getPublic().getEncoded()));


    String clientPubKeyStr = in.readLine();
    clientPublicKey = CryptoUtils.getPublicKeyFromString(clientPubKeyStr);


    byte[] sharedSecret = CryptoUtils.generateSharedSecret(serverKeyPair.getPrivate(), clientPublicKey);

    String message = "Server Authentication";
    String signature = CryptoUtils.signData(serverKeyPair.getPrivate(), message);
    out.println(signature);

    String hmac = CryptoUtils.generateHMAC(message, sharedSecret);
    out.println(hmac);

        String confirmation = in.readLine();
        if ("Handshake Successful".equals(confirmation)) {
            System.out.println("Handshake successful. Proceeding to establish secure channel...");

            
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                
                String decryptedClientMessage = CryptoUtils.decrypt(clientMessage, Arrays.copyOf(sharedSecret, 16));
                System.out.println("Client: " + decryptedClientMessage);

                System.out.print("");
                String response = consoleIn.readLine();

                
                String encryptedResponse = CryptoUtils.encrypt(response, Arrays.copyOf(sharedSecret, 16));
                out.println(encryptedResponse);
            }
        } else {
            System.out.println("Handshake failed.");
        }
    }

}
