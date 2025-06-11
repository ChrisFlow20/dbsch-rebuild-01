package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@DoNotObfuscate
public class AlterScript {
  public final String dbId;
  
  public final List statements = new ArrayList();
  
  public AlterScript(String paramString) {
    this.dbId = paramString;
  }
  
  public AlterScript(String paramString, AlterStatement paramAlterStatement) {
    this.dbId = paramString;
    add(paramAlterStatement);
  }
  
  public void add(AlterStatement paramAlterStatement) {
    if (paramAlterStatement != null && !paramAlterStatement.isEmpty())
      for (AlterStatement alterStatement : paramAlterStatement.b()) {
        if (alterStatement != null && !alterStatement.isEmpty())
          this.statements.add(alterStatement); 
      }  
  }
  
  public void addAll(AlterScript paramAlterScript) {
    if (paramAlterScript != null)
      for (AlterStatement alterStatement : paramAlterScript.statements)
        add(alterStatement);  
  }
  
  public boolean isEmpty() {
    return this.statements.isEmpty();
  }
  
  public void writeToOutputStream(Writer paramWriter) {
    for (AlterStatement alterStatement : this.statements) {
      paramWriter.write(alterStatement.getSQLWithTerminator(this.dbId));
      paramWriter.write("\n\n");
      paramWriter.flush();
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (AlterStatement alterStatement : this.statements)
      stringBuilder.append(alterStatement.getSQLWithTerminator(this.dbId)).append("\n\n"); 
    return stringBuilder.toString();
  }
  
  public AlterScript setSortOrder(int paramInt) {
    for (AlterStatement alterStatement : this.statements)
      alterStatement.setSortOrder(paramInt); 
    return this;
  }
  
  public AlterScript resetSortOrder() {
    for (AlterStatement alterStatement : this.statements)
      alterStatement.resetSortOrder(); 
    return this;
  }
  
  public AlterScript sort() {
    this.statements.sort(Comparator.comparingInt(AlterStatement::a));
    return this;
  }
  
  public void injectStatements(List<String> paramList, boolean paramBoolean) {
    for (byte b = 0; b < paramList.size(); b++) {
      if (paramBoolean) {
        this.statements.add(b, new AlterStatement(null, paramList.get(b)));
      } else {
        this.statements.add(new AlterStatement(null, paramList.get(b)));
      } 
    } 
  }
}
