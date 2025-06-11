package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPrioritizable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;

@DoNotObfuscate
public abstract class Sql extends AbstractUnit implements SyncPrioritizable {
  private String a = getDefaultKey();
  
  private Language b = Language.a;
  
  private boolean c = false;
  
  private String d;
  
  private boolean e = false;
  
  private int f = -1;
  
  private String g;
  
  private File h;
  
  private boolean i;
  
  public Sql(String paramString) {
    super(paramString);
    this.g = null;
    this.h = null;
    this.i = false;
  }
  
  public File getFile() {
    return this.h;
  }
  
  public boolean hasFile() {
    return (this.h != null);
  }
  
  public void setFile(File paramFile) {
    this.h = paramFile;
  }
  
  public boolean hasText() {
    return StringUtil.isFilledTrim(this.g);
  }
  
  public String getText() {
    return this.g;
  }
  
  public void setText(String paramString) {
    this.g = paramString;
  }
  
  public void setLanguage(Language paramLanguage) {
    if (paramLanguage != null)
      this.b = paramLanguage; 
  }
  
  public Language getLanguage() {
    return this.b;
  }
  
  public abstract String getDbId();
  
  public void setIsSystem(boolean paramBoolean) {
    this.i = paramBoolean;
  }
  
  public boolean isSystem() {
    return this.i;
  }
  
  public String getKey() {
    return this.a;
  }
  
  public void setKey(String paramString) {
    if (StringUtil.isFilledTrim(paramString))
      this.a = paramString; 
  }
  
  public void setConfirmed(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public boolean isConfirmed() {
    return this.c;
  }
  
  public void setSchedule(String paramString) {
    this.d = StringUtil.isFilledTrim(paramString) ? paramString : null;
  }
  
  public String getSchedule() {
    return this.d;
  }
  
  public void cloneFrom(Sql paramSql) {
    setText(paramSql.getText());
    setIsSystem(paramSql.isSystem());
    setVirtual(paramSql.isVirtual());
    setSyncPriority(paramSql.getSyncPriority());
  }
  
  public void setFreshCreated(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  public boolean isFreshCreated() {
    return this.e;
  }
  
  public void setSyncPriority(int paramInt) {
    this.f = paramInt;
  }
  
  public int getSyncPriority() {
    return this.f;
  }
  
  public int getDefaultSyncPriority() {
    return 7000;
  }
}
