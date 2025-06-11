package com.wisecoders.dbs.generator.store;

import com.wisecoders.dbs.generator.engine.plan.Category;
import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.generator.engine.plan.Domain;
import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.sys.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class PatternLoader extends AbstractContentHandler {
  public PatternLoader(File paramFile) {
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
  
  public PatternLoader() {
    try {
      InputStream inputStream = PatternLoader.class.getResourceAsStream("/generator/patterns.xml");
      try {
        parse(inputStream);
        if (inputStream != null)
          inputStream.close(); 
      } catch (Throwable throwable) {
        if (inputStream != null)
          try {
            inputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } catch (Exception exception) {
      Log.c(exception);
    } 
  }
  
  private final StoreKey[] c = new StoreKey[10];
  
  Category a;
  
  DefinedPattern b;
  
  protected void a(String paramString, int paramInt) {
    StoreKey storeKey = StoreKey.valueOf(paramString);
    this.c[paramInt] = storeKey;
    a((paramInt > 0) ? this.c[paramInt - 1] : null, storeKey);
  }
  
  private void a(StoreKey paramStoreKey1, StoreKey paramStoreKey2) {
    switch (PatternLoader$1.a[paramStoreKey2.ordinal()]) {
      case 1:
        this.a = Domain.a(get(StoreKey.name));
        break;
      case 2:
        if (this.a != null) {
          this.b = this.a.a(get(StoreKey.name), get(StoreKey.pattern));
          this.b.b(get(StoreKey.samples));
          this.b.a(getInt(StoreKey.maxlength));
        } 
        break;
      case 3:
        if (this.b != null)
          this.b.a(
              get(StoreKey.table), 
              get(StoreKey.column), 
              getInt(StoreKey.score), 
              get(StoreKey.dataType), 
              get(StoreKey.dbms)); 
        break;
    } 
  }
}
