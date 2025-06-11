package com.wisecoders.dbs.project.store;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CipherUtil {
  public static Cipher a(String paramString) {
    return a(paramString, 1);
  }
  
  public static Cipher b(String paramString) {
    return a(paramString, 2);
  }
  
  private static Cipher a(String paramString, int paramInt) {
    DESKeySpec dESKeySpec = new DESKeySpec(paramString.getBytes());
    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = secretKeyFactory.generateSecret(dESKeySpec);
    Cipher cipher = Cipher.getInstance("DES");
    cipher.init(paramInt, secretKey);
    return cipher;
  }
}
