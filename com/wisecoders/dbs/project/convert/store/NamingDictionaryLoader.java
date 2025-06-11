package com.wisecoders.dbs.project.convert.store;

import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.project.convert.model.NamingDictionary$Separator;
import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.sys.Log;
import java.io.File;
import java.io.FileInputStream;

public class NamingDictionaryLoader extends AbstractContentHandler {
  NamingDictionary a;
  
  private final NxKey[] b;
  
  public NamingDictionaryLoader(NamingDictionary paramNamingDictionary, File paramFile) {
    this.b = new NxKey[10];
    this.a = paramNamingDictionary;
    if (paramFile.exists())
      try {
        FileInputStream fileInputStream = new FileInputStream(paramFile);
        try {
          parse(fileInputStream);
          fileInputStream.close();
        } catch (Throwable throwable) {
          try {
            fileInputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (Exception exception) {
        Log.c(exception);
      }  
  }
  
  protected void a(String paramString, int paramInt) {
    NxKey nxKey = NxKey.valueOf(paramString);
    this.b[paramInt] = nxKey;
    a((paramInt > 0) ? this.b[paramInt - 1] : null, nxKey);
  }
  
  private void a(NxKey paramNxKey1, NxKey paramNxKey2) {
    if (paramNxKey1 != null)
      switch (NamingDictionaryLoader$1.a[paramNxKey2.ordinal()]) {
        case 1:
          this.a.a(getBoolean(NxKey.convert_cases));
          this.a.b(NamingDictionary$Separator.valueOf(get(NxKey.logical_separator)));
          this.a.a(NamingDictionary$Separator.valueOf(get(NxKey.physical_separator)));
          break;
        case 2:
          this.a.a(get(NxKey.logical_name), get(NxKey.physical_name));
          break;
      }  
  }
}
