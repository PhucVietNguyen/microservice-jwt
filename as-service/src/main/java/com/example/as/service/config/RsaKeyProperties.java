package com.example.as.service.config;

import com.example.as.service.util.RsaUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
@ConfigurationProperties("rsa.key")
@Getter
@Setter
public class RsaKeyProperties {

  private String publicKeyFile;

  private String privateKeyFile;

  private String publicKeyString;

  private String privateKeyString;

  private PublicKey publicKey;

  private PrivateKey privateKey;

  @Autowired private RsaUtil rsaUtil;

  @PostConstruct
  public void createRsaKey() throws Exception {
    java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    publicKey = rsaUtil.getPublicKey(publicKeyFile);
    privateKey = rsaUtil.getPrivateKey(privateKeyFile);
    publicKeyString = rsaUtil.getPublicKeyString(publicKeyFile);
    privateKeyString = rsaUtil.getPrivateKeyString(privateKeyFile);
  }
}
