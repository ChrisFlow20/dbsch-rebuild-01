package com.wisecoders.dbs.data.model.result;

import com.wisecoders.dbs.dialogs.system.FileType;

public enum SpoolFormat {
  a(FileType.j),
  b(FileType.g, ","),
  c(FileType.i, "\t"),
  d(FileType.i, "|"),
  e(FileType.p),
  f(FileType.n),
  g(FileType.o);
  
  public final String h;
  
  public final FileType i;
  
  SpoolFormat(FileType paramFileType) {
    this.i = paramFileType;
    this.h = null;
  }
  
  SpoolFormat(FileType paramFileType, String paramString1) {
    this.i = paramFileType;
    this.h = paramString1;
  }
  
  public String a(String paramString) {
    if (paramString != null) {
      switch (SpoolFormat$1.a[ordinal()]) {
        case 1:
          return "\"" + paramString.replaceAll("\n", "\\n").replaceAll("\r", "\\r").replaceAll("\"", "\"\"") + "\"";
        case 2:
          return paramString.replaceAll("\n", "\\n").replaceAll("\r", "\\r").replaceAll("\t", "\\t");
        case 3:
          return paramString.replaceAll("\n", "\\n").replaceAll("\r", "\\r").replaceAll("\\|", "||");
      } 
      return paramString;
    } 
    return paramString;
  }
}
