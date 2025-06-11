package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;

@DoNotObfuscate
public class AlterStatement extends SimpleStatement {
  public final SyncPair pair;
  
  private int c = 0;
  
  private final boolean d;
  
  private static final String e = ";;";
  
  public AlterStatement(SyncPair paramSyncPair, String paramString) {
    this(paramSyncPair, paramString, false, false);
  }
  
  public AlterStatement(SyncPair paramSyncPair, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    super(paramString, paramBoolean2);
    this.pair = paramSyncPair;
    this.d = paramBoolean1;
  }
  
  public AlterStatement set(K paramK, Object paramObject) {
    super.set(paramK, paramObject);
    return this;
  }
  
  public String getSQLWithTerminator(String paramString) {
    String str1 = toString().trim();
    String str2 = this.d ? (Dbms.get(paramString)).psqlDelimiter.c_() : (Dbms.get(paramString)).statementDelimiter.c_();
    return str1.endsWith(str2) ? str1 : (str1 + str1);
  }
  
  public AlterStatement setSortOrder(int paramInt) {
    if (this.c == 0 && paramInt != -1)
      this.c = paramInt; 
    return this;
  }
  
  int a() {
    return this.c;
  }
  
  public void resetSortOrder() {
    this.c = 0;
  }
  
  AlterStatement[] b() {
    if (this.b != null && this.b.indexOf(";;") > 0) {
      String[] arrayOfString = this.b.split(";;");
      AlterStatement[] arrayOfAlterStatement = new AlterStatement[arrayOfString.length];
      byte b = 0;
      for (String str : arrayOfString) {
        if (StringUtil.isFilledTrim(str)) {
          AlterStatement alterStatement = new AlterStatement(this.pair, str, this.d, false);
          alterStatement.a.getVariables().putAll(this.a.getVariables());
          arrayOfAlterStatement[b] = alterStatement;
          b++;
        } 
      } 
      return arrayOfAlterStatement;
    } 
    return new AlterStatement[] { this };
  }
}
