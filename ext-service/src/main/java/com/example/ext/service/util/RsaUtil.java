package com.example.ext.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class RsaUtil {

  private static final int DEFAULT_KEY_SIZE = 3072;

  //  private final RsaKeyProperties rsaKeyProperties;

  public PublicKey getPublicKey(String filename) throws Exception {
    byte[] bytes = readFile(filename);
    return getPublicKey(bytes);
  }

  public PrivateKey getPrivateKey(String filename) throws Exception {
    byte[] bytes = readFile(filename);
    return getPrivateKey(bytes);
  }

  public String getPublicKeyString(String filename) throws Exception {
    byte[] bytes = readFile(filename);
    return new String(bytes);
  }

  public String getPrivateKeyString(String filename) throws Exception {
    byte[] bytes = readFile(filename);
    return new String(bytes);
  }

  public PublicKey getPublicKey(byte[] bytes) throws Exception {
    bytes = Base64.getDecoder().decode(bytes);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
    KeyFactory factory = KeyFactory.getInstance("RSA");
    return factory.generatePublic(spec);
  }

  public PrivateKey getPrivateKey(byte[] bytes)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    bytes = Base64.getDecoder().decode(bytes);
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
    KeyFactory factory = KeyFactory.getInstance("RSA");
    return factory.generatePrivate(spec);
  }

  //  public void generateKey(
  //          String publicKeyFilename,
  //          String privateKeyFilename,
  //          String secret,
  //          int keySize) throws Exception {
  //    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
  //    SecureRandom secureRandom = new SecureRandom(secret.getBytes());
  //    keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
  //    KeyPair keyPair = keyPairGenerator.genKeyPair();
  //    // get public key and write to a file
  //    byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
  //    publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
  //    writeFile(publicKeyFilename, publicKeyBytes);
  //    // get private key and write to a file
  //    byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
  //    privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
  //    writeFile(privateKeyFilename, privateKeyBytes);
  //  }
  //
  //  private void writeFile(String destPath, byte[] bytes) throws IOException {
  //    File dest = new File(destPath);
  //    if (!dest.exists()) {
  //      dest.createNewFile();
  //    }
  //    Files.write(dest.toPath(), bytes);
  //  }

  private byte[] readFile(String filename) throws Exception {
    InputStream resourceAsStream = this.getClass().getResourceAsStream(filename);
    assert resourceAsStream != null;
    return resourceAsStream.readAllBytes();
    //    return Files.readAllBytes(new File(filename).toPath());
  }

  //  public String encrypt(String plainText) {
  //    try {
  //      Cipher encryptCipher = Cipher.getInstance("RSA");
  //      encryptCipher.init(Cipher.ENCRYPT_MODE, rsaKeyProperties.getPublicKey());
  //      byte[] secretMessageBytes = plainText.getBytes(StandardCharsets.UTF_8);
  //      byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
  //      return Base64.getEncoder().encodeToString(encryptedMessageBytes);
  //    } catch (Exception e) {
  //      throw new RuntimeException("Fail to encrypt plainText");
  //    }
  //  }
  //
  //  public String decrypt(String cipherText) {
  //    try {
  //      Cipher decryptCipher = Cipher.getInstance("RSA");
  //      decryptCipher.init(Cipher.DECRYPT_MODE, rsaKeyProperties.getPrivateKey());
  //      ;
  //      byte[] decode = Base64.getDecoder().decode(cipherText);
  //      byte[] decryptedMessageBytes = decryptCipher.doFinal(decode);
  //      return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
  //    } catch (Exception e) {
  //      throw new RuntimeException("Fail to decrypt cipherText");
  //    }
  //  }
}
