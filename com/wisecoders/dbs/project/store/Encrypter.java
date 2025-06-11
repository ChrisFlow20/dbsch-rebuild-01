package com.wisecoders.dbs.project.store;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {
  public void a() {
    try {
      String str1 = "Hello World";
      String str2 = "Bar12345Bar12345";
      SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(1, secretKeySpec);
      byte[] arrayOfByte = cipher.doFinal(str1.getBytes());
      System.err.println("Encrypted " + Arrays.toString(arrayOfByte));
      cipher.init(2, secretKeySpec);
      String str3 = new String(cipher.doFinal(arrayOfByte));
      System.err.println(str3);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public static void main(String[] paramArrayOfString) {
    Encrypter encrypter = new Encrypter();
    encrypter.a();
  }
}
