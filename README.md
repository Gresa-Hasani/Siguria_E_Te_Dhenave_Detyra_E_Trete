# Implementimi i një Protokolli Secure Shell (SSH) Handshake në modelin Client-Server 

Ky projekt implementon një aplikacion të thjeshtë për komunikim të sigurt mes një klienti dhe një serveri duke përdorur një protokoll të Secure Shell (SSH) Handshake. Ky dokument përshkruan udhëzimet për ekzekutimin e programit, një përshkrim të shkurtër për secilën pjesë të programit dhe shembuj të rezultateve të ekzekutimit.

## Përmbajtja

- [Kërkesat](#kërkesat)
- [Instalimi](#instalimi)
- [Ekzekutimi](#ekzekutimi)
- [Përshkrimi i Algoritmeve të Implementuara](#përshkrimi-i-algoritmeve-të-implementuara)
  - [CryptoUtils.java](#cryptoutilsjava)
  - [Server.java](#serverjava)
  - [Client.java](#clientjava)
- [Shembull Ekzekutimi](#shembull-ekzekutimi)

## Kërkesat

Sigurohuni të keni në dispozicion:
- Versionin Java 8 ose ndonjë version më të ri
- Maven
- IntelliJ IDEA ose një IDE tjetër për Java
- BouncyCastle Library

## Instalimi

1. Shkarkoni dhe instaloni [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html).
2. Shkarkoni dhe instaloni [Maven](https://maven.apache.org/install.html).
3. Klononi këtë repository.
4. Navigoni në dosjen e projektit dhe ndërtoni projektin me Maven.

## Ekzekutimi

1. Startoni serverin.
2. Startoni pastaj klientin.

## Përshkrimi i Algoritmeve të Implementuara

### CryptoUtils.java

Kjo klasë përmban funksionet kriptografike të përdorura në projekt:

- **generateKeyPair()**: Gjeneron një çift çelësash ECDH. Përdoret për të krijuar çelësat publik dhe privat për klientin dhe serverin.
- **generateSharedSecret()**: Gjeneron një shared secret duke përdorur çelësin privat të njërës palë dhe çelësin publik të palës tjetër. Ky sekret përdoret për enkriptim dhe dekriptim të të dhënave.
- **signData()**: Nënshkruan të dhënat me çelësin privat duke përdorur algoritmin ECDSA. Kjo siguron që të dhënat të mos ndryshohen dhe të jenë autentike.
- **verifySignature()**: Verifikon nënshkrimin e të dhënave me çelësin publik për të siguruar integritetin dhe autenticitetin e tyre.
- **generateHMAC()**: Gjeneron një HMAC për informatat e dhëna duke përdorur një çelës sekret. Kjo siguron integritetin e mesazheve gjatë transmetimit.
- **getPublicKeyFromString()**: Konverton një çelës publik nga formati string në objekt PublicKey. Përdoret për të marrë çelësin publik nga një string i koduar në Base64.


### Server.java

Kjo klasë implementon logjikën e serverit për të komunikuar me klientët dhe për të siguruar një lidhje të sigurt:

- Gjeneron një çift çelësash për serverin.
- Dërgon çelësin publik te klienti.
- Merr çelësin publik të klientit.
- Gjeneron një sekret të përbashkët për enkriptim dhe dekriptim të të dhënave.
- Kryen nënshkrim të të dhënave për autentifikim.
- Gjeneron HMAC për integritet të mesazheve.
- Përdor enkriptimin dhe verifikimin e duhur për të parandaluar sulmet MITM.


### Client.java

Kjo klasë implementon logjikën e klientit për të komunikuar me serverin dhe për të siguruar një lidhje të sigurt:

- Gjeneron një çift çelësash për klientin.
- Merr çelësin publik të serverit.
- Dërgon çelësin publik të klientit te serveri.
- Gjeneron një sekret të përbashkët për enkriptim dhe dekriptim të të dhënave.
- Verifikon nënshkrimin e serverit për autentifikim.
- Verifikon HMAC për integritet të mesazheve.
- Përdor enkriptimin dhe verifikimin e duhur për të parandaluar sulmet MITM.
  

## Shembull Ekzekutimi

### Shembull Komunikimi

1. Startoni serverin!

    Output:
   
    ![image](https://github.com/Gresa-Hasani/Siguria_E_Te_Dhenave_Detyra_E_Trete/assets/153296296/acb31a67-443f-465b-b181-a8b3b296a806)

2. Startoni klientin:
   
    Output:

    ![image](https://github.com/Gresa-Hasani/Siguria_E_Te_Dhenave_Detyra_E_Trete/assets/153296296/ed5f9033-bf6d-4334-afa8-5b20cd137215)


3. Komunikimi mes klientit dhe serverit:

    **Klienti**:
    ![image](https://github.com/Gresa-Hasani/Siguria_E_Te_Dhenave_Detyra_E_Trete/assets/153296296/48612a34-68c9-4b89-b822-5bf587dcde65)


    **Serveri**:
    ![image](https://github.com/Gresa-Hasani/Siguria_E_Te_Dhenave_Detyra_E_Trete/assets/153296296/cefd9f53-b8f1-49d1-b3ca-bcbfc366bc0b)


# Punuan:

- [Gresa Halili](#gresa-halili)
- [Gresa Hasani](#gresa-hasani)
- [Gzon Alaj](#gzon-alaj)
- [Haki Pintolli](#haki-pintolli)
