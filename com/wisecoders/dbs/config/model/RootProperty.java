package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SRx;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;
import javafx.util.Callback;

public class RootProperty extends FolderProperty {
  private Path b;
  
  private final Class g;
  
  private final String h;
  
  private Callback i;
  
  private final boolean j;
  
  private final Properties k = new Properties();
  
  public RootProperty(String paramString) {
    super(null, null, paramString);
    this.h = paramString;
    this.g = null;
    this.j = false;
  }
  
  public RootProperty(Class paramClass, String paramString, boolean paramBoolean) {
    super(null, null, "Root");
    try {
      InputStream inputStream = paramClass.getResourceAsStream("resources/" + paramClass.getSimpleName() + "Descriptor.properties");
      try {
        SRx.a(this.k, inputStream);
        if (Sys.l() != null)
          try {
            InputStream inputStream1 = paramClass.getResourceAsStream("resources/" + paramClass.getSimpleName() + "Descriptor_" + Sys.l() + ".properties");
            try {
              SRx.a(this.k, inputStream1);
              if (inputStream1 != null)
                inputStream1.close(); 
            } catch (Throwable throwable) {
              if (inputStream1 != null)
                try {
                  inputStream1.close();
                } catch (Throwable throwable1) {
                  throwable.addSuppressed(throwable1);
                }  
              throw throwable;
            } 
          } catch (IOException iOException) {
            Log.b(iOException);
          }  
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
      Log.b("Missing resources " + paramClass.getSimpleName() + ".properties", exception);
    } 
    this.h = paramString;
    this.g = paramClass;
    this.j = paramBoolean;
  }
  
  public void h() {
    if (this.b != null) {
      File file = this.b.toFile();
      if (file.exists())
        try {
          FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
          try {
            a(fileReader);
            fileReader.close();
          } catch (Throwable throwable) {
            try {
              fileReader.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            } 
            throw throwable;
          } 
        } catch (Exception exception) {
          Log.c(exception);
        }  
    } 
  }
  
  public boolean i() {
    return q("resources/" + this.g.getSimpleName() + ".properties");
  }
  
  public boolean q(String paramString) {
    try {
      InputStream inputStream = this.g.getResourceAsStream(paramString);
      try {
        if (inputStream != null) {
          RootProperty$1 rootProperty$1 = new RootProperty$1(this);
          SRx.a(rootProperty$1, inputStream);
          a(rootProperty$1, true);
          boolean bool = true;
          if (inputStream != null)
            inputStream.close(); 
          return bool;
        } 
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
    } catch (Throwable throwable) {
      Log.b("Error loading configuration from path " + paramString, throwable);
    } 
    return false;
  }
  
  public void a(Reader paramReader) {
    Properties properties = new Properties();
    properties.load(paramReader);
    a(properties, false);
  }
  
  public void j() {
    if (this.b != null)
      try {
        a(this.b.toFile(), false);
      } catch (Exception exception) {
        Log.c(exception);
      }  
  }
  
  public void a(File paramFile, boolean paramBoolean) {
    SortedProperties sortedProperties = new SortedProperties();
    b(sortedProperties, paramBoolean);
    if (sortedProperties.isEmpty() && !paramFile.exists())
      return; 
    if (!paramFile.getParentFile().exists())
      paramFile.getParentFile().mkdir(); 
    FileWriter fileWriter = new FileWriter(paramFile, StandardCharsets.UTF_8);
    try {
      sortedProperties.store(fileWriter, "configuration");
      fileWriter.close();
    } catch (Throwable throwable) {
      try {
        fileWriter.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public String toString() {
    return this.h;
  }
  
  public String p() {
    return this.h;
  }
  
  public void a(Path paramPath) {
    this.b = paramPath;
  }
  
  public void b(Callback paramCallback) {
    this.i = paramCallback;
  }
  
  public void q() {
    this.i.call(null);
  }
  
  public Properties b_() {
    return this.k;
  }
}
