package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.Table;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@DoNotObfuscate
public class SqlUtil {
  private static final int a = 40;
  
  public static boolean isDelimiter(char paramChar) {
    return (paramChar == ' ' || paramChar == ',' || paramChar == ';' || paramChar == ':' || paramChar == '{' || paramChar == '}' || paramChar == '(' || paramChar == ')' || paramChar == '[' || paramChar == ']' || paramChar == '+' || paramChar == '-' || paramChar == '/' || paramChar == '%' || paramChar == '<' || paramChar == '=' || paramChar == '>' || paramChar == '!' || paramChar == '&' || paramChar == '|' || paramChar == '^' || paramChar == '~' || paramChar == '*' || paramChar == '\n' || paramChar == '\'' || paramChar == '\t');
  }
  
  public static String getTooltipForSql(String paramString) {
    String str = null;
    if (paramString != null)
      try {
        paramString = paramString.toLowerCase();
        int i;
        if (paramString.startsWith("select ") && (i = paramString.indexOf("from ")) > 0) {
          int j = i + "from ".length(), k = Integer.MAX_VALUE;
          int m;
          if ((m = paramString.indexOf(",", j)) > 0)
            k = Math.min(m, k); 
          if ((m = paramString.indexOf(" ", j)) > 0)
            k = Math.min(m, k); 
          if ((m = paramString.indexOf(")", j)) > 0)
            k = Math.min(m, k); 
          str = "select ... from ";
          if (k < Integer.MAX_VALUE)
            str = str + str; 
        } else if (paramString.length() > 40) {
          str = paramString.substring(0, 40) + "...";
        } else {
          str = paramString;
        } 
      } catch (Exception exception) {
        Log.b(exception);
      }  
    return str;
  }
  
  public static boolean isCommentBlock(String paramString) {
    return (paramString != null && (paramString.startsWith("//") || paramString.toLowerCase().startsWith("rem") || paramString
      .toLowerCase().startsWith("--") || (paramString
      .startsWith("/*") && paramString.endsWith("*/") && 
      
      !paramString.startsWith("/*!"))));
  }
  
  public static boolean isDmlBlock(String paramString) {
    return (paramString != null && (paramString.toLowerCase().startsWith("insert") || paramString.toLowerCase().startsWith("update") || paramString.toLowerCase().startsWith("delete") || paramString.toLowerCase().startsWith("truncate")));
  }
  
  @GroovyMethod
  public static String escapeCommentQuotes(String paramString) {
    if (paramString != null)
      return paramString.replaceAll("'", "''"); 
    return paramString;
  }
  
  public static String escapeCommentQuotesAndNewLine(String paramString) {
    if (paramString != null)
      return paramString.replaceAll("'", "''").replaceAll("\n", Matcher.quoteReplacement("\\n")).replaceAll("\r", ""); 
    return paramString;
  }
  
  public static String unescapeComment(String paramString) {
    if (paramString != null) {
      paramString = paramString.replaceAll("\\\\n", "\n");
      if ((paramString.startsWith("'") || paramString.startsWith("E'")) && paramString.endsWith("'"))
        paramString = paramString.substring(1, paramString.length() - 1); 
    } 
    return paramString;
  }
  
  public static String getUnitNames(List<Unit> paramList) {
    if (paramList.size() == 1)
      return ((Unit)paramList.get(0)).getSymbolicName() + " '" + ((Unit)paramList.get(0)).getSymbolicName() + "'"; 
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (Unit unit : paramList)
      uniqueArrayList.add(unit.getSymbolicName()); 
    return (uniqueArrayList.size() == 1) ? uniqueArrayList.get(0) : "elements";
  }
  
  private static final String[] b = new String[] { "Syntax error or access violation,  message from server:", "; check the manual that corresponds to your MySQL server version for the right syntax to use", "java.sql.SQLException: ", "org.postgresql.util.PSQLException: ", "ERROR: " };
  
  public static String getExceptionText(Throwable paramThrowable) {
    String str;
    Log.b(paramThrowable);
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    byte b = 5;
    Throwable throwable = paramThrowable;
    do {
      String str1 = throwable.getLocalizedMessage();
      uniqueArrayList.add(str1);
    } while (throwable instanceof SQLException && (
      throwable = ((SQLException)throwable).getNextException()) != null && --b > 0);
    b = 5;
    throwable = paramThrowable;
    do {
      str = throwable.getLocalizedMessage();
      uniqueArrayList.add(str);
    } while ((throwable = throwable.getCause()) != null && --b > 0);
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = uniqueArrayList.size() - 1; i > -1; i--) {
      String str1 = uniqueArrayList.get(i);
      for (String str2 : b) {
        str1 = str1.replace(str2, "");
        int j;
        if ((j = str1.indexOf("** BEGIN NESTED EXCEPTION **")) > -1)
          str1 = str1.substring(0, j); 
      } 
      stringBuilder.append(str1.trim()).append("\n");
    } 
    return stringBuilder.toString();
  }
  
  public static String removeDelimiters(String paramString) {
    if (paramString != null && ((
      paramString.startsWith("\"") && paramString.endsWith("\"")) || (paramString
      .startsWith("'") && paramString.endsWith("'")) || (paramString
      .startsWith("[") && paramString.endsWith("]"))))
      return paramString.substring(1, paramString.length() - 1); 
    return paramString;
  }
  
  public static String getInsertQueryWithMultipleValuesSets(Table paramTable, String[] paramArrayOfString, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("INSERT /*+ APPEND */ INTO ").append(paramTable.ref()).append("(");
    boolean bool = false;
    for (String str : paramArrayOfString) {
      if (bool)
        stringBuilder.append(","); 
      bool = true;
      stringBuilder.append(str);
    } 
    stringBuilder.append(") VALUES ");
    for (byte b = 0; b < paramInt; b++) {
      if (b > 0)
        stringBuilder.append("\n,"); 
      stringBuilder.append("(");
      for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
        if (b1 > 0)
          stringBuilder.append(", "); 
        stringBuilder.append("?");
      } 
      stringBuilder.append(")");
    } 
    return stringBuilder.toString();
  }
  
  public static boolean columnsAreMandatory(List paramList) {
    for (Column column : paramList) {
      if (!column.isMandatory())
        return false; 
    } 
    return true;
  }
  
  public static int decodeInOut(String paramString) {
    if (paramString != null)
      switch (paramString) {
        case "IN":
          return 1;
        case "OUT":
          return 3;
        case "INOUT":
          return 2;
      }  
    return 0;
  }
  
  public static boolean isUpdateStatement(String paramString) {
    if (paramString != null) {
      String str = paramString.toLowerCase().trim();
      return (str.startsWith("update") || str
        .startsWith("delete") || str
        .startsWith("insert") || str
        .startsWith("truncate") || str
        .startsWith("merge"));
    } 
    return false;
  }
  
  public static List splitScriptIntoStatements(String paramString) {
    ArrayList<String> arrayList = new ArrayList();
    if (StringUtil.isFilled(paramString)) {
      StringBuilder stringBuilder = new StringBuilder();
      for (String str1 : StringUtil.splitTextInLines(paramString)) {
        String str2 = str1.trim();
        boolean bool = false;
        if ("/".equals(str2)) {
          bool = true;
          str2 = "";
        } else if (str2.trim().endsWith(";")) {
          bool = true;
          str2 = str2.substring(0, str2.length() - 1);
        } 
        if (StringUtil.isFilled(str2)) {
          if (stringBuilder.length() > 0)
            stringBuilder.append("\n"); 
          stringBuilder.append(str2);
        } 
        if (bool && stringBuilder.length() > 0) {
          arrayList.add(stringBuilder.toString());
          stringBuilder.delete(0, stringBuilder.length());
        } 
      } 
      if (stringBuilder.length() > 0) {
        arrayList.add(stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length() - 1);
      } 
    } 
    return arrayList;
  }
}
