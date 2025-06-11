package com.wisecoders.dbs.project.history;

import java.io.File;
import java.util.Objects;

public class HistoryFile {
  public final String a;
  
  public final File b;
  
  HistoryFile(String paramString, File paramFile) {
    this.a = paramString;
    this.b = paramFile;
  }
  
  public String toString() {
    return (this.a != null) ? (this.a + " [" + this.a + "]") : this.b.getPath();
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    HistoryFile historyFile = (HistoryFile)paramObject;
    return Objects.equals(this.b, historyFile.b);
  }
  
  public int hashCode() {
    int i = (this.a != null) ? this.a.hashCode() : 0;
    i = 31 * i + ((this.b != null) ? this.b.hashCode() : 0);
    return i;
  }
}
