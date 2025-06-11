package com.wisecoders.dbs.project.history;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class History {
  private final String a;
  
  private final String b;
  
  private final String c;
  
  private final List d = new UniqueArrayList();
  
  public History(String paramString) {
    this.a = "History.p." + paramString;
    this.b = "History.f." + paramString;
    this.c = "History.a." + paramString;
    d();
  }
  
  private void d() {
    try {
      if (this.d.isEmpty())
        for (byte b = 0; b < Sys.B.reopenHistorySize.a(); b++) {
          String str1 = Pref.c(this.a + this.a, (String)null);
          String str2 = Pref.c(this.b + this.b, (String)null);
          if (StringUtil.isFilledTrim(str2)) {
            File file = Paths.get(str2, new String[0]).toFile();
            if (file.exists())
              this.d.add(new HistoryFile(str1, file)); 
          } 
        }  
    } catch (Throwable throwable) {
      Log.a("Error loading open history", throwable);
    } 
  }
  
  public synchronized void a(String paramString, File paramFile) {
    if (paramFile != null) {
      HistoryFile historyFile = new HistoryFile(paramString, paramFile);
      this.d.remove(historyFile);
      this.d.add(0, historyFile);
      if (this.d.size() > Sys.B.reopenHistorySize.a())
        this.d.remove(Sys.B.reopenHistorySize.a()); 
      byte b = 0;
      for (HistoryFile historyFile1 : this.d) {
        if (historyFile1 != null) {
          if (historyFile1.a == null) {
            Pref.b(this.a + this.a);
          } else {
            Pref.a(this.a + this.a, historyFile1.a);
          } 
          Pref.a(this.b + this.b, historyFile1.b.getPath());
          b++;
        } 
      } 
    } 
  }
  
  public void a(File paramFile) {
    if (paramFile == null) {
      Pref.b(this.c);
    } else {
      Pref.a(this.c, paramFile.getPath());
    } 
  }
  
  public File a() {
    String str = Pref.c(this.c, (String)null);
    if (str != null) {
      File file = new File(str);
      if (file.exists())
        return file; 
    } 
    return null;
  }
  
  public boolean b() {
    return this.d.isEmpty();
  }
  
  public List c() {
    return new ArrayList(this.d);
  }
}
