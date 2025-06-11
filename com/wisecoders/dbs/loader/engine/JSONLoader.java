package com.wisecoders.dbs.loader.engine;

import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

@DoNotObfuscate
public class JSONLoader extends AbstractLoader {
  private final File b;
  
  private final String c;
  
  private boolean d = true;
  
  public JSONLoader(File paramFile, String paramString) {
    this.b = paramFile;
    this.c = paramString;
  }
  
  @GroovyMethod
  public void setImportLineByLine(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public void parse() {
    if (this.d) {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.b), this.c));
      try {
        String str;
        while ((str = bufferedReader.readLine()) != null && !reachedMaxProcessingTime()) {
          if (str.trim().length() > 0) {
            JSONLoaderCallback jSONLoaderCallback = new JSONLoaderCallback();
            JSONParser.parse(str, jSONLoaderCallback);
            for (LoaderConsumer loaderConsumer : this.a)
              loaderConsumer.consumeRecord(jSONLoaderCallback.a); 
          } 
        } 
        bufferedReader.close();
      } catch (Throwable throwable) {
        try {
          bufferedReader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } else {
      String str = Files.readString(this.b.toPath());
      JSONLoaderCallback jSONLoaderCallback = new JSONLoaderCallback();
      JSONParser.parse(str, jSONLoaderCallback);
      for (LoaderConsumer loaderConsumer : this.a)
        loaderConsumer.consumeRecord(jSONLoaderCallback.a); 
    } 
  }
  
  public boolean firstLineIsHeader() {
    return false;
  }
}
