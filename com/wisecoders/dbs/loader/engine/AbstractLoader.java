package com.wisecoders.dbs.loader.engine;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.ArrayList;
import java.util.List;

@DoNotObfuscate
public abstract class AbstractLoader {
  final List a = new ArrayList();
  
  private long b = -1L;
  
  public abstract void parse();
  
  public abstract boolean firstLineIsHeader();
  
  public AbstractLoader addConsumer(LoaderConsumer paramLoaderConsumer) {
    this.a.add(paramLoaderConsumer);
    return this;
  }
  
  public void setMaxProcessingTime(long paramLong) {
    this.b = System.currentTimeMillis() + paramLong;
  }
  
  public boolean reachedMaxProcessingTime() {
    return (this.b > -1L && System.currentTimeMillis() > this.b);
  }
}
