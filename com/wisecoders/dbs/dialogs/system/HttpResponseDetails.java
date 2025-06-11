package com.wisecoders.dbs.dialogs.system;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public final class HttpResponseDetails extends Record {
  private final int e;
  
  private final String f;
  
  public static final int a = 200;
  
  public static final int b = 201;
  
  public static final int c = 409;
  
  public static final int d = 400;
  
  public HttpResponseDetails(int paramInt, String paramString) {
    this.e = paramInt;
    this.f = paramString;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lcom/wisecoders/dbs/dialogs/system/HttpResponseDetails;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8	-> 0
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/dialogs/system/HttpResponseDetails;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/dialogs/system/HttpResponseDetails;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8	-> 0
  }
  
  public int a() {
    return this.e;
  }
  
  public String b() {
    return this.f;
  }
  
  public static HttpResponseDetails a(CloseableHttpResponse paramCloseableHttpResponse) {
    return new HttpResponseDetails(paramCloseableHttpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(paramCloseableHttpResponse.getEntity()));
  }
}
