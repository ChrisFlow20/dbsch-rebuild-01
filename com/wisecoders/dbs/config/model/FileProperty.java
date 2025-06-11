package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class FileProperty extends Property {
  private File a;
  
  private File b;
  
  private final Path g;
  
  private boolean h = false;
  
  private String i;
  
  FileProperty(Property paramProperty, String paramString, Path paramPath) {
    super(paramProperty, paramString, File.class);
    this.g = paramPath;
  }
  
  public boolean a() {
    return (this.a != null);
  }
  
  public boolean b() {
    return (this.a != null && this.a.exists());
  }
  
  public File f() {
    return this.a;
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l() + ".file");
    if (str == null || str.isEmpty()) {
      this.a = null;
    } else {
      try {
        Path path = Paths.get(str, new String[0]);
        path = Sys.b.resolve(path);
        this.a = path.toFile();
        if (paramBoolean)
          this.b = this.a; 
      } catch (Exception exception) {
        Log.b(exception);
      } 
    } 
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if (paramBoolean || !e())
      if (this.a == null) {
        paramProperties.put(l() + ".file", (this.a != null) ? this.a.getAbsolutePath() : "");
      } else {
        Path path = (this.g != null) ? this.g.relativize(this.a.toPath()) : this.a.toPath();
        paramProperties.put(l() + ".file", path.toString());
      }  
  }
  
  public void a(Object paramObject) {
    if (paramObject instanceof File) {
      this.a = (File)paramObject;
    } else {
      this.a = null;
    } 
  }
  
  public Object c() {
    return this.a;
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilled(paramString))
      try {
        Path path = Paths.get(paramString, new String[0]);
        path = Sys.b.resolve(path);
        this.b = path.toFile();
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
  }
  
  public Object d() {
    return this.b;
  }
  
  public boolean e() {
    return ((this.b == null && this.a == null) || (this.b != null && this.b.equals(this.a)));
  }
  
  public String toString() {
    return (this.a != null) ? this.a.toURI().toASCIIString() : "";
  }
  
  public Glyph g() {
    return BootstrapIcons.file;
  }
  
  public FileProperty h() {
    this.h = true;
    return this;
  }
  
  public boolean i() {
    return this.h;
  }
  
  public FileProperty b(String paramString) {
    this.i = paramString;
    return this;
  }
  
  public List j() {
    return (this.i != null) ? Arrays.<String>asList(this.i.split(",")) : null;
  }
}
