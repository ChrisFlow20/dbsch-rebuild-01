package com.wisecoders.dbs.dbms.connect.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;

public class KeyStoreFile {
  public final File a;
  
  private final String b;
  
  private KeyStore c;
  
  public KeyStoreFile(File paramFile, String paramString) {
    this.a = paramFile;
    this.b = paramString;
  }
  
  public KeyStoreFile a() {
    if (this.a.exists() && this.a.isFile()) {
      FileInputStream fileInputStream = new FileInputStream(this.a);
      try {
        this.c = KeyStore.getInstance(KeyStore.getDefaultType());
        this.c.load(fileInputStream, this.b.toCharArray());
        fileInputStream.close();
      } catch (Throwable throwable) {
        try {
          fileInputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } 
    return this;
  }
  
  public KeyStore b() {
    return this.c;
  }
  
  public KeyStore c() {
    if (this.c == null) {
      this.c = KeyStore.getInstance(KeyStore.getDefaultType());
      this.c.load(null, null);
    } 
    return this.c;
  }
  
  public void d() {
    if (this.c != null) {
      FileOutputStream fileOutputStream = new FileOutputStream(this.a);
      try {
        this.c.store(fileOutputStream, this.b.toCharArray());
        fileOutputStream.close();
      } catch (Throwable throwable) {
        try {
          fileOutputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } 
  }
  
  public void a(File paramFile) {
    FileInputStream fileInputStream = new FileInputStream(paramFile);
    try {
      BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
      try {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        while (bufferedInputStream.available() > 0) {
          Certificate certificate = certificateFactory.generateCertificate(bufferedInputStream);
          c().setCertificateEntry(paramFile.getName() + "-" + paramFile.getName(), certificate);
        } 
        bufferedInputStream.close();
      } catch (Throwable throwable) {
        try {
          bufferedInputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
      fileInputStream.close();
    } catch (Throwable throwable) {
      try {
        fileInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public void b(File paramFile) {
    ArrayList<Certificate> arrayList = new ArrayList();
    FileInputStream fileInputStream = new FileInputStream(paramFile);
    try {
      CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
      arrayList.addAll(certificateFactory.generateCertificates(fileInputStream));
      fileInputStream.close();
    } catch (Throwable throwable) {
      try {
        fileInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    byte b = 1;
    for (Certificate certificate : arrayList) {
      c().setCertificateEntry("cert" + b, certificate);
      b++;
    } 
  }
}
