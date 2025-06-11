package com.wisecoders.dbs.editors.text;

public enum TokenType {
  a((byte)1),
  b((byte)2),
  c((byte)3),
  d((byte)4),
  e((byte)5),
  f((byte)6),
  g((byte)7),
  h((byte)8),
  i((byte)9),
  j((byte)10),
  k((byte)11),
  l((byte)12),
  m((byte)13),
  n((byte)14),
  o((byte)15),
  p((byte)16),
  q((byte)17),
  r((byte)18);
  
  public byte s;
  
  TokenType(byte paramByte) {
    this.s = paramByte;
  }
  
  public static boolean a(Token paramToken) {
    if (paramToken != null && (paramToken.a == i || paramToken.a == j))
      return true; 
    return false;
  }
  
  public static boolean b(Token paramToken) {
    if (paramToken != null && (paramToken.a == c || paramToken.a == d))
      return true; 
    return false;
  }
  
  public static boolean c(Token paramToken) {
    if (paramToken != null && (paramToken.a == g || paramToken.a == h))
      return true; 
    return false;
  }
}
