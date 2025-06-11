package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameFinder {
  private static final Pattern a = Pattern.compile("(.*)_(\\d+)$", 2);
  
  private final Schema b;
  
  public NameFinder(Schema paramSchema) {
    this.b = paramSchema;
  }
  
  public String a(AbstractTable paramAbstractTable, Column paramColumn, IndexType paramIndexType) {
    Dbms dbms = Dbms.get(this.b.project.getDbId());
    switch (NameFinder$1.a[paramIndexType.ordinal()]) {
      default:
        throw new IncompatibleClassChangeError();
      case 1:
      
      case 2:
      case 3:
      
      case 4:
      
      case 5:
      
      case 6:
      
      case 7:
      
      case 8:
      
      case 9:
        break;
    } 
    String str1 = dbms.defaultIndex2Name.c_();
    if (StringUtil.isEmptyTrim(str1))
      str1 = "idx_" + paramAbstractTable.getName(); 
    int i = (dbms.maxColumnNameLength.a() - 5) / 2;
    SimpleStatement simpleStatement = (new SimpleStatement(str1)).set(K.o, StringUtil.cutOfNoDots(paramAbstractTable.getName(), i)).set(K.q, StringUtil.cutOfNoDots((paramColumn != null) ? paramColumn.getName() : "", i));
    str1 = simpleStatement.toString();
    if (str1.endsWith("_"))
      str1 = str1.substring(0, str1.length() - 1); 
    String str2 = b(str1);
    return dbms.toDefaultCases(str2);
  }
  
  public String a(String paramString) {
    return b(paramString);
  }
  
  public String a(AbstractTable paramAbstractTable1, AbstractTable paramAbstractTable2, Column paramColumn) {
    Dbms dbms = Dbms.get(this.b.project.getDbId());
    String str1 = dbms.defaultForeignKeyName.c_();
    if (StringUtil.isEmptyTrim(str1))
      str1 = "fk_" + paramAbstractTable1.getName(); 
    try {
      SimpleStatement simpleStatement = (new SimpleStatement(str1)).set(K.o, paramAbstractTable1).set(K.X, paramAbstractTable2).set(K.q, paramColumn);
      str1 = simpleStatement.toString();
    } catch (Throwable throwable) {
      str1 = "fk_" + paramAbstractTable1.getName();
      Log.b(throwable);
    } 
    if (str1.endsWith("_"))
      str1 = str1.substring(0, str1.length() - 1); 
    String str2 = b(str1);
    return dbms.toDefaultCases(str2);
  }
  
  public String a(AbstractTable paramAbstractTable, Column paramColumn) {
    Dbms dbms = Dbms.get(this.b.project.getDbId());
    String str1 = dbms.defaultConstraintName.c_();
    if (StringUtil.isEmptyTrim(str1))
      str1 = "ck_" + paramAbstractTable.getName(); 
    SimpleStatement simpleStatement = (new SimpleStatement(str1)).set(K.o, paramAbstractTable).set(K.q, paramColumn);
    str1 = simpleStatement.toString();
    if (str1.endsWith("_"))
      str1 = str1.substring(0, str1.length() - 1); 
    String str2 = b(str1);
    return dbms.toDefaultCases(str2);
  }
  
  public String a() {
    Dbms dbms = Dbms.get(this.b.project.getDbId());
    String str1 = dbms.defaultSequenceName.c_();
    if (StringUtil.isEmptyTrim(str1))
      str1 = "seq"; 
    SimpleStatement simpleStatement = new SimpleStatement(str1);
    str1 = simpleStatement.toString();
    if (str1.endsWith("_"))
      str1 = str1.substring(0, str1.length() - 1); 
    String str2 = b(str1);
    return dbms.toDefaultCases(str2);
  }
  
  public String b() {
    Dbms dbms = Dbms.get(this.b.project.getDbId());
    String str1 = dbms.defaultTableName.c_();
    if (StringUtil.isEmptyTrim(str1))
      str1 = "table"; 
    SimpleStatement simpleStatement = new SimpleStatement(str1);
    str1 = simpleStatement.toString();
    if (str1.endsWith("_"))
      str1 = str1.substring(0, str1.length() - 1); 
    String str2 = b(str1);
    return dbms.toDefaultCases(str2);
  }
  
  private String b(String paramString) {
    String str1;
    paramString = Dbms.get(this.b).toDefaultCases(paramString);
    long l = -1L;
    Matcher matcher = a.matcher(paramString);
    if (matcher.matches()) {
      paramString = matcher.group(1);
      l = Long.parseLong(matcher.group(2));
    } 
    String str2 = "_";
    if (paramString.endsWith("_")) {
      l = 0L;
      str2 = "";
    } 
    while (true) {
      if (a(this.b, str1 = paramString + paramString)) {
        l++;
        continue;
      } 
      break;
    } 
    return str1;
  }
  
  private static boolean a(TreeUnit paramTreeUnit, String paramString) {
    if (paramString.equalsIgnoreCase(paramTreeUnit.getName()))
      return true; 
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++) {
      TreeUnit treeUnit = paramTreeUnit.getChildAt(b);
      if (a(treeUnit, paramString))
        return true; 
    } 
    return false;
  }
}
