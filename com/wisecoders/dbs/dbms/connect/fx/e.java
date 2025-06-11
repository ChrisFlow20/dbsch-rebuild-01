package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dbms.connect.model.KeyStoreFile;

class e {
  public final KeyStoreFile a;
  
  public final String b;
  
  public final String c;
  
  private String e;
  
  public final f d;
  
  public e(KeyStoreFile paramKeyStoreFile, f paramf, String paramString1, String paramString2, String paramString3) {
    this.a = paramKeyStoreFile;
    this.d = paramf;
    this.b = paramString1;
    this.e = paramString2;
    this.c = paramString3;
  }
  
  public void a(String paramString) {
    this.e = paramString;
  }
  
  public String a() {
    return this.e;
  }
}
