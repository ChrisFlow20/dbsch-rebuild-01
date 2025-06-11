package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.sys.fx.FxUtil;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;

public class License {
  private final SRx k = new SRx(License.class, this);
  
  public final String a;
  
  public final String b;
  
  public final String c;
  
  public final String d;
  
  public final long e;
  
  public final int f;
  
  public final int g;
  
  private final long l;
  
  private final long m;
  
  private long n = -1L;
  
  public final boolean h;
  
  public final boolean i;
  
  public final long j;
  
  private static License o;
  
  public License(String paramString, long paramLong) {
    this.j = paramLong;
    int i = 1, j = 1;
    long l1 = -1L, l2 = -1L, l3 = -1L;
    String str1 = null, str2 = null, str3 = null, str4 = null;
    boolean bool1 = false, bool2 = false;
    if (paramString != null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      StringBuilder stringBuilder2 = new StringBuilder();
      boolean bool = false;
      for (String str5 : paramString.split("\\r?\\n")) {
        str5 = str5.trim();
        if (!str5.startsWith("####"))
          if (bool) {
            stringBuilder2.append(str5);
          } else {
            String str6;
            if ((str6 = StringUtil.getValueFromKeyValuePair("SIGNATURE", str5)) == null) {
              stringBuilder1.append(str5).append("\n");
            } else {
              stringBuilder2.append(str6);
              bool = true;
            } 
          }  
      } 
      String str = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4uGNkieAmy+fXwixm+2xxgNXpS4JqywZ1N8cRFWmd+8hjqmnerbVgc0h5hvO+ralwiyOvyIXCr8bcMRieOBx5h3PtRPY3LSPugs3DF62Ytu+J3d3le9oWrw9vUqKZ0xF5EX7GSViv3TXeuSGWx7mBFISmqFRWqAPx+tA5OsKbdvHxcOi/xRvdOztj/JkeGSN4c8Hlk1msQ8g4OFswQ1K4cP378huTsGi825WnfhuwHpVvB02y4nYcXqFPLCHscVDwHy/bWDNnOBTvoVebwlfbKso2iPvbRuWyx0u979wBLZOovkzL7Tu+hkDAz9DMZSaicAWqECXp1xz6iz5ubO3EQIDAQAB";
      if (!stringBuilder2.isEmpty())
        try {
          byte[] arrayOfByte1 = stringBuilder1.toString().trim().getBytes(StandardCharsets.UTF_8);
          byte[] arrayOfByte2 = Base64.decodeBase64(stringBuilder2.toString());
          if (a(arrayOfByte1, arrayOfByte2, str))
            for (String str6 : paramString.split("\\r?\\n")) {
              str6 = str6.trim();
              String str5;
              if ((str5 = StringUtil.getValueFromKeyValuePair("ISSUE DATE", str6)) != null)
                l1 = FxUtil.b(str5); 
              if (StringUtil.getValueFromKeyValuePair("EVALUATION", str6) != null)
                bool1 = true; 
              if ((str5 = StringUtil.getValueFromKeyValuePair("VALID_UNTIL", str6)) != null)
                l2 = FxUtil.b(str5); 
              if ((str5 = StringUtil.getValueFromKeyValuePair("SUBSCRIPTION_ID", str6)) != null) {
                str1 = "PPG";
                str3 = str5;
              } 
              if ((str5 = StringUtil.getValueFromKeyValuePair("ORDER_ID", str6)) != null) {
                str1 = "PPG";
                l3 = Long.parseLong(str5);
              } 
              if ((str5 = StringUtil.getValueFromKeyValuePair("LICENSE_REF", str6)) != null) {
                str1 = "2CO";
                str3 = str5;
              } 
              if ((str5 = StringUtil.getValueFromKeyValuePair("REFNO", str6)) != null) {
                str1 = "2CO";
                l3 = Long.parseLong(str5);
              } 
              if ((str5 = StringUtil.getValueFromKeyValuePair("CUSTOMER_EMAIL", str6)) != null)
                str2 = str5; 
              if ((str5 = StringUtil.getValueFromKeyValuePair("EMAIL", str6)) != null && str2 == null)
                str2 = str5; 
              if ((str5 = StringUtil.getValueFromKeyValuePair("SEAT", str6)) != null)
                i = Integer.parseInt(str5); 
              if ((str5 = StringUtil.getValueFromKeyValuePair("SKU", str6)) != null)
                str4 = str5; 
              if ((str5 = StringUtil.getValueFromKeyValuePair("PRODUCT_QUANTITY", str6)) != null)
                j = Integer.parseInt(str5); 
              if ("A".equals(StringUtil.getValueFromKeyValuePair("AUTOMATIC", str6)))
                bool2 = true; 
            }  
        } catch (Exception exception) {
          Log.a("Reg error", exception);
        }  
    } 
    this.g = i;
    this.l = l1;
    this.m = l2;
    this.e = l3;
    this.a = str1;
    this.c = str2;
    this.b = str3;
    this.h = bool1;
    this.i = bool2;
    this.f = j;
    this.d = str4;
  }
  
  public static License a() {
    String str = Keys.d.a();
    long l = StringUtil.getCRC32Checksum(str + str);
    if (o != null && o.j == l)
      return o; 
    return o = new License(str, l);
  }
  
  public static void b() {
    Keys.d.c();
    Keys.e.c();
    Keys.j.c();
    Keys.h.c();
    Keys.i.c();
    o = null;
  }
  
  public boolean c() {
    return ("PPG".equals(this.a) && this.b != null && this.e > 0L && System.currentTimeMillis() > this.n - 259200000L);
  }
  
  public String d() {
    return this.k.a("renew.url", new String[0]) + this.k.a("renew.url", new String[0]);
  }
  
  public boolean e() {
    return (g()).h;
  }
  
  public boolean f() {
    return !e();
  }
  
  private static boolean a(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, String paramString) {
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(paramString));
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initVerify(publicKey);
    signature.update(paramArrayOfbyte1);
    return signature.verify(paramArrayOfbyte2);
  }
  
  public Features g() {
    if (Keys.h.e()) {
      if (Keys.j.e()) {
        this.n = Long.MAX_VALUE;
        return Features.a;
      } 
      return Features.b;
    } 
    return h();
  }
  
  public Features h() {
    if (this.l == -1L) {
      long l = Long.parseLong(Keys.k.a());
      if (Keys.h.e())
        return Features.g; 
      if (l == -1L || System.currentTimeMillis() - l > 8035200000L)
        return Features.c; 
      if (System.currentTimeMillis() - l < 1209600000L) {
        this.n = l + 1209600000L;
        return Features.f;
      } 
      this.n = Long.MAX_VALUE;
      return Features.b;
    } 
    this.n = (this.m > -1L) ? this.m : (this.l + (i() ? 31L : (this.h ? 15L : 365L)) * 86400000L);
    if (Math.max(Log.d, System.currentTimeMillis()) > this.n) {
      if (this.h)
        return Features.b; 
      if (Log.d > this.l + 32832000000L)
        return Features.d; 
      if (Log.d > this.l + 31536000000L)
        return Features.e; 
    } 
    return Features.a;
  }
  
  public boolean i() {
    return (this.d != null && this.d.length() > 2 && this.d.charAt(2) == 'S');
  }
  
  public String j() {
    if (Keys.j.e())
      return this.k.H("flsLicense") + this.k.H("flsLicense"); 
    switch (License$1.a[g().ordinal()]) {
      case 1:
      
      case 2:
      
    } 
    return 

      
      this.k.H("communityInfo");
  }
  
  public String a(boolean paramBoolean) {
    String str = j();
    StringBuilder stringBuilder = new StringBuilder();
    if (str != null)
      for (String str1 : str.split("\\r?\\n")) {
        if (!str1.startsWith("SIGNATURE".toUpperCase()))
          stringBuilder.append(str1).append(paramBoolean ? "<br>" : "\n"); 
      }  
    return stringBuilder.toString();
  }
  
  public long k() {
    return (this.n - System.currentTimeMillis()) / 86400000L;
  }
  
  public String b(boolean paramBoolean) {
    String str = StringUtil.MD5(this.a + this.a + this.b + this.g);
    return paramBoolean ? str.substring(str.length() - 5) : str;
  }
  
  public String c(boolean paramBoolean) {
    return this.k.a("activation.url", new String[0]) + "?GATEWAY=" + this.k.a("activation.url", new String[0]) + "&SUBSCRIPTION_ID=" + this.a + "&SEAT=" + this.b + "&" + this.g + "=" + Keys.f.a + "&" + Keys.f


      
      .a() + "=" + String.valueOf(Keys.g) + "&EMAIL=" + 
      EscapeChars.forURL(Keys.g.a()) + "&" + 
      EscapeChars.forURL(this.c) + "SECURE=" + (
      paramBoolean ? "UNREGISTER=true&" : "");
  }
  
  public boolean l() {
    return (this.b != null);
  }
  
  public boolean m() {
    return (Keys.e.d() && !"CLS".equals(this.d) && this.b != null && !Keys.j.e());
  }
}
