package com.wisecoders.dbs.sys;

import java.io.File;
import java.io.FileInputStream;

public class BlobInputStream extends FileInputStream {
  public final File a;
  
  public final long b;
  
  public BlobInputStream(File paramFile) {
    super(paramFile);
    this.a = paramFile;
    this.b = paramFile.length();
  }
  
  public String toString() {
    return this.a.toString();
  }
}
