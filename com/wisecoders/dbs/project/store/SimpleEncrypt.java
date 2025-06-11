package com.wisecoders.dbs.project.store;

import java.nio.charset.StandardCharsets;

public class SimpleEncrypt {
  public static String a(String paramString) {
    if (paramString == null)
      return null; 
    if (paramString.isEmpty())
      return ""; 
    return a(paramString.getBytes(StandardCharsets.UTF_8));
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramArrayOfbyte.length; b += 3)
      stringBuilder.append(a(paramArrayOfbyte, b)); 
    return stringBuilder.toString();
  }
  
  public static String b(String paramString) {
    if (paramString == null)
      return null; 
    if (paramString.isEmpty())
      return ""; 
    byte b1 = 0;
    int i;
    for (i = paramString.length() - 1; paramString.charAt(i) == '='; i--)
      b1++; 
    i = paramString.length() * 6 / 8 - b1;
    byte[] arrayOfByte = new byte[i];
    byte b2 = 0;
    for (byte b3 = 0; b3 < paramString.length(); b3 += 4) {
      int j = (a(paramString.charAt(b3)) << 18) + (a(paramString.charAt(b3 + 1)) << 12) + (a(paramString.charAt(b3 + 2)) << 6) + a(paramString.charAt(b3 + 3));
      for (byte b = 0; b < 3 && b2 + b < arrayOfByte.length; b++)
        arrayOfByte[b2 + b] = (byte)(j >> 8 * (2 - b) & 0xFF); 
      b2 += 3;
    } 
    return new String(arrayOfByte, StandardCharsets.UTF_8);
  }
  
  private static char[] a(byte[] paramArrayOfbyte, int paramInt) {
    int i = 0;
    int j = paramArrayOfbyte.length - paramInt - 1;
    int k = Math.min(j, 2);
    for (byte b1 = 0; b1 <= k; b1++) {
      byte b = paramArrayOfbyte[paramInt + b1];
      byte b3 = (b < 0) ? (b + 256) : b;
      i += b3 << 8 * (2 - b1);
    } 
    char[] arrayOfChar = new char[4];
    for (byte b2 = 0; b2 < 4; b2++) {
      int m = i >>> 6 * (3 - b2) & 0x3F;
      arrayOfChar[b2] = a(m);
    } 
    if (j < 1)
      arrayOfChar[2] = '='; 
    if (j < 2)
      arrayOfChar[3] = '='; 
    return arrayOfChar;
  }
  
  private static char a(int paramInt) {
    if (paramInt >= 0 && paramInt <= 25)
      return (char)(65 + paramInt); 
    if (paramInt >= 26 && paramInt <= 51)
      return (char)(97 + paramInt - 26); 
    if (paramInt >= 52 && paramInt <= 61)
      return (char)(48 + paramInt - 52); 
    if (paramInt == 62)
      return '+'; 
    if (paramInt == 63)
      return '/'; 
    return '?';
  }
  
  private static int a(char paramChar) {
    if (paramChar >= 'A' && paramChar <= 'Z')
      return paramChar - 65; 
    if (paramChar >= 'a' && paramChar <= 'z')
      return paramChar - 97 + 26; 
    if (paramChar >= '0' && paramChar <= '9')
      return paramChar - 48 + 52; 
    if (paramChar == '+')
      return 62; 
    if (paramChar == '/')
      return 63; 
    if (paramChar == '=')
      return 0; 
    return -1;
  }
}
