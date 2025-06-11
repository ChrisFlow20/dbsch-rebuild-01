package com.wisecoders.dbs.loader.tasks;

import com.wisecoders.dbs.loader.engine.AbstractLoader;
import com.wisecoders.dbs.loader.engine.LoaderConsumer;
import com.wisecoders.dbs.loader.model.LoaderMeta;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import java.util.Map;
import javafx.concurrent.Task;

public class NamingDictionaryLoaderTask extends Task implements LoaderConsumer {
  private final AbstractLoader a;
  
  private final LoaderMeta b;
  
  public NamingDictionaryLoaderTask(LoaderMeta paramLoaderMeta, AbstractLoader paramAbstractLoader) {
    this.b = paramLoaderMeta;
    this.a = paramAbstractLoader;
  }
  
  protected Void a() {
    this.a.addConsumer(this);
    this.a.parse();
    return null;
  }
  
  public void consumeRecord(Map paramMap) {
    String str1 = this.b.a(paramMap, "logicalName");
    String str2 = this.b.a(paramMap, "physicalName");
    if (str1 != null && str2 != null) {
      NamingDictionary.c.a(str1, str2);
      this.b.a(1);
    } 
  }
  
  protected String b() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Inserted ").append(this.b.c()).append(" rows. ");
    if (this.b.d() > 0)
      stringBuilder.append("Failed ").append(this.b.d()).append(" rows."); 
    return stringBuilder.toString();
  }
}
