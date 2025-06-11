package com.wisecoders.dbs.sql.completion;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;

public final class SqlCompletionSuggestion extends Record {
  private final AbstractUnit a;
  
  private final String b;
  
  public SqlCompletionSuggestion(AbstractUnit paramAbstractUnit, String paramString) {
    this.a = paramAbstractUnit;
    this.b = paramString;
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lcom/wisecoders/dbs/sql/completion/SqlCompletionSuggestion;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #11	-> 0
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lcom/wisecoders/dbs/sql/completion/SqlCompletionSuggestion;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #11	-> 0
  }
  
  public AbstractUnit c() {
    return this.a;
  }
  
  public String d() {
    return this.b;
  }
  
  public String toString() {
    if (this.a instanceof com.wisecoders.dbs.schema.Schema)
      return this.a.ref(); 
    if (this.a instanceof AbstractTable) {
      AbstractTable abstractTable = (AbstractTable)this.a;
      return Dbms.get(abstractTable.getDbId()).quote(this.a.getName());
    } 
    AbstractUnit abstractUnit = this.a;
    if (abstractUnit instanceof Column) {
      Column column = (Column)abstractUnit;
      return (this.b != null) ? (this.b + "." + this.b) : column.ref();
    } 
    return this.b;
  }
  
  public String a() {
    AbstractUnit abstractUnit = this.a;
    if (abstractUnit instanceof AbstractTable) {
      AbstractTable abstractTable = (AbstractTable)abstractUnit;
      String str = this.a.getSymbolicName() + " in " + this.a.getSymbolicName();
      if (abstractTable instanceof Table) {
        long l = ((Table)this.a).getRowCount();
        if (l > -1L)
          str = str + " " + str + " rows."; 
      } 
      return str;
    } 
    if (this.a instanceof com.wisecoders.dbs.schema.Schema)
      return this.a.getSymbolicName(); 
    abstractUnit = this.a;
    if (abstractUnit instanceof Column) {
      Column column = (Column)abstractUnit;
      return column.getTypeString() + " in " + column.getTypeString();
    } 
    return null;
  }
  
  public String b() {
    return (this.a != null && this.a.getComment() != null) ? StringUtil.cutOfNoDots(StringUtil.flatternText(this.a.getComment()), 80) : null;
  }
}
