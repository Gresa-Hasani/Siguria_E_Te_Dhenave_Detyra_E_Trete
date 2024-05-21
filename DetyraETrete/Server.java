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

   
}
