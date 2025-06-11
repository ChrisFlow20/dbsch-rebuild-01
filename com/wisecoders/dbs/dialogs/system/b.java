package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class b extends Sql {
  private String c;
  
  public List a = new ArrayList();
  
  public final boolean b;
  
  public b() {
    super("Root");
    this.b = false;
  }
  
  public b(String paramString, File paramFile, boolean paramBoolean) {
    super((paramString != null) ? paramString : StringUtil.getFileNameWithoutExtension(paramFile).replaceAll("-", " "));
    this.b = paramBoolean;
    setFile(paramFile);
    a();
  }
  
  public void a() {
    if (hasFile() && getFile().isDirectory()) {
      this.a.clear();
      for (File file : (File[])Objects.<File[]>requireNonNull(getFile().listFiles()))
        this.a.add(new b(null, file, this.b)); 
    } 
  }
  
  public Language getLanguage() {
    if (hasFile()) {
      if (getFile().getName().endsWith(".groovy"))
        return Language.b; 
      if (getFile().getName().endsWith(".md"))
        return Language.g; 
      if (getFile().getName().equalsIgnoreCase("Makefile"))
        return Language.f; 
      if (getFile().getName().equalsIgnoreCase("Dockerfile"))
        return Language.f; 
    } 
    return Language.a;
  }
  
  public boolean b() {
    return (hasFile() && getFile().isDirectory());
  }
  
  public String getText() {
    try {
      if (this.c == null && hasFile()) {
        FileInputStream fileInputStream = new FileInputStream(getFile());
        try {
          this.c = StringUtil.readStringFromInputStream(fileInputStream);
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
    } catch (IOException iOException) {
      this.c = iOException.getLocalizedMessage();
    } 
    return this.c;
  }
  
  public boolean a(Path paramPath) {
    if (!hasFile())
      return false; 
    Path path = getFile().toPath();
    return (paramPath.equals(path) || !paramPath.relativize(path).isAbsolute());
  }
  
  public void refresh() {}
  
  public TreeUnit getParent() {
    return null;
  }
  
  public String getSymbolicName() {
    return null;
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return null;
  }
  
  public Entity getEntity() {
    return null;
  }
  
  public String getDbId() {
    return "MySql";
  }
  
  public String toString() {
    return (getName() != null || !hasFile()) ? getName() : StringUtil.getFileNameWithoutExtension(getFile()).replaceAll("-", " ");
  }
}
