package com.wisecoders.dbs.sql.parser;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.editors.text.TokenType;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Table;

public class DDLParserUtil {
  public static Column a(MatcherCommaPhrase paramMatcherCommaPhrase, Index paramIndex) {
    if (paramMatcherCommaPhrase.isEmpty() || ((MatcherNode)paramMatcherCommaPhrase.get(0)).d() == null)
      return null; 
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.b("?.?")) != null) {
      Column column1 = paramIndex.getEntity().getColumnByNameOrPath(patternPhrase.f(1));
      if (column1 != null) {
        paramIndex.addColumn(column1);
        String str1 = paramMatcherCommaPhrase.a(3);
        if (str1 != null)
          paramIndex.setColumnOptions(column1, str1); 
        return column1;
      } 
    } 
    String str = ((MatcherNode)paramMatcherCommaPhrase.get(0)).d();
    Column column = paramIndex.getEntity().getColumnByNameOrPath(str);
    if (column != null) {
      paramIndex.addColumn(column);
      String str1 = paramMatcherCommaPhrase.a(1);
      if (str1 != null)
        paramIndex.setColumnOptions(column, str1); 
    } 
    return column;
  }
  
  public static Column a(MatcherCommaPhrase paramMatcherCommaPhrase, Table paramTable) {
    if (paramMatcherCommaPhrase.isEmpty() || ((MatcherNode)paramMatcherCommaPhrase.get(0)).d() == null)
      return null; 
    String str1 = ((MatcherNode)paramMatcherCommaPhrase.get(0)).d();
    Column column = paramTable.getColumnByNameOrPath(str1);
    if (column != null)
      return column; 
    if (paramMatcherCommaPhrase.size() < 2)
      return null; 
    String str2 = ((MatcherNode)paramMatcherCommaPhrase.get(1)).d();
    int i = DataType.UNSET, j = DataType.UNSET;
    if (paramMatcherCommaPhrase.size() >= 3) {
      E e = paramMatcherCommaPhrase.get(3);
      if (e instanceof MatcherStatement) {
        MatcherStatement matcherStatement = (MatcherStatement)e;
        if (!matcherStatement.isEmpty() && 
          !((MatcherCommaPhrase)matcherStatement.get(0)).isEmpty() && ((MatcherCommaPhrase)matcherStatement.get(0)).b() == TokenType.f)
          i = Integer.parseInt(((MatcherCommaPhrase)matcherStatement.get(0)).d()); 
        if (matcherStatement.size() > 1 && 
          !((MatcherCommaPhrase)matcherStatement.get(1)).isEmpty() && ((MatcherCommaPhrase)matcherStatement.get(1)).b() == TokenType.f)
          j = Integer.parseInt(((MatcherCommaPhrase)matcherStatement.get(0)).d()); 
      } 
    } 
    column = paramTable.createColumn(str1, DbmsTypes.get(paramTable.getDbId()).getType(str2), i);
    column.setDecimal(j);
    return column;
  }
  
  public static String a(String paramString) {
    if (paramString != null) {
      paramString = paramString.trim();
      if ((paramString.startsWith("'") && paramString.endsWith("'")) || (paramString
        .startsWith("\"") && paramString.endsWith("\"")) || (paramString
        .startsWith("[") && paramString.endsWith("]")) || (paramString
        .startsWith("`") && paramString.endsWith("`")))
        return paramString.substring(1, paramString.length() - 1); 
    } 
    return paramString;
  }
  
  public static String b(String paramString) {
    if (paramString != null)
      paramString = paramString.trim(); 
    return paramString;
  }
  
  public static String c(String paramString) {
    paramString = paramString.replaceAll("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/", "");
    paramString = paramString.replaceAll("//.*(?=\\n)", "");
    paramString = paramString.replaceAll("(?mi)^rem\\s+.*", "");
    paramString = paramString.replaceAll("--.*(?=\\n)", "");
    return paramString.trim();
  }
  
  public static String d(String paramString) {
    if (paramString != null) {
      paramString = paramString.trim();
      if (paramString.endsWith(";"))
        paramString = paramString.substring(0, paramString.length() - 1); 
    } 
    return paramString;
  }
  
  public static boolean a(MatcherNode paramMatcherNode) {
    return (paramMatcherNode != null && paramMatcherNode.c() != null && paramMatcherNode.c().matches("-?\\d+"));
  }
}
