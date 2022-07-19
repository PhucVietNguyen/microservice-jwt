package com.example.ext.service.config;

import com.example.ext.service.util.RsaUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@Component
@ConfigurationProperties("rsa.key")
@Getter
@Setter
public class RsaKeyProperties {

  private String publicKeyFile;

  private String publicKeyString;

  private PublicKey publicKey;

  @Autowired private RsaUtil rsaUtil;

  @PostConstruct
  public void createRsaKey() throws Exception {
    java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    publicKey = rsaUtil.getPublicKey(publicKeyFile);
    publicKeyString = rsaUtil.getPublicKeyString(publicKeyFile);
  }
}
