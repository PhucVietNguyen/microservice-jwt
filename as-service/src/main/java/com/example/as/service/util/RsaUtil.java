package com.example.as.service.util;

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

  private byte[] readFile(String filename) throws Exception {
    InputStream resourceAsStream = this.getClass().getResourceAsStream(filename);
    assert resourceAsStream != null;
    return resourceAsStream.readAllBytes();
  }
}
