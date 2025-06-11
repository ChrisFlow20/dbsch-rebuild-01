package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class DataTypeUtil {
  public static boolean isTimestamp(int paramInt) {
    return (paramInt == 93);
  }
  
  public static boolean isTime(int paramInt) {
    return (paramInt == 92);
  }
  
  public static boolean isDate(int paramInt) {
    switch (paramInt) {
      case 91:
      case 92:
      case 93:
      case 2014:
      
    } 
    return false;
  }
  
  public static boolean isBoolean(int paramInt) {
    switch (paramInt) {
      case -7:
      case 16:
      
    } 
    return false;
  }
  
  public static boolean isBlob(int paramInt) {
    switch (paramInt) {
      case -4:
      case -2:
      
    } 
    return false;
  }
  
  public static boolean isNumeric(int paramInt) {
    switch (paramInt) {
      case -6:
      case -5:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      
    } 
    return false;
  }
  
  public static boolean isUUID(int paramInt) {
    return (paramInt == 102);
  }
  
  public static boolean isInteger(int paramInt) {
    switch (paramInt) {
      case -6:
      case 4:
      case 5:
      
    } 
    return false;
  }
  
  public static boolean isText(int paramInt) {
    switch (paramInt) {
      case -15:
      case -1:
      case 1:
      case 12:
      
    } 
    return false;
  }
  
  public static boolean isLong(int paramInt) {
    return (paramInt == -5);
  }
  
  public static String getTypeName(int paramInt) {
    switch (paramInt) {
      case -6:
      
      case 5:
      
      case 4:
      
      case -7:
      
      case -5:
      
      case 6:
      
      case 7:
      
      case 8:
      
      case 2:
      
      case 3:
      
      case 1:
      
      case 12:
      
      case -1:
      
      case 91:
      
      case 92:
      
      case 93:
      
      case -2:
      
      case -3:
      
      case -4:
      
      case 0:
      
      case 1111:
      
      case 2000:
      
      case 2001:
      
      case 2002:
      
      case 2003:
      
      case 2004:
      
      case 2005:
      
      case 2006:
      
      case 70:
      
      case 16:
      
    } 
    return 





























      
      "UNKNOWN";
  }
  
  public static Integer[] getJavaTypesArray() {
    return new Integer[] { 
        Integer.valueOf(-6), 
        Integer.valueOf(5), 
        Integer.valueOf(4), 
        Integer.valueOf(-7), 
        Integer.valueOf(-5), 
        Integer.valueOf(6), 
        Integer.valueOf(7), 
        Integer.valueOf(8), 
        Integer.valueOf(2), 
        Integer.valueOf(3), 
        Integer.valueOf(1), 
        Integer.valueOf(12), 
        Integer.valueOf(-1), 
        Integer.valueOf(91), 
        Integer.valueOf(92), 
        Integer.valueOf(93), 
        Integer.valueOf(-2), 
        Integer.valueOf(-3), 
        Integer.valueOf(-4), 
        Integer.valueOf(0), 
        Integer.valueOf(1111), 
        Integer.valueOf(2000), 
        Integer.valueOf(2001), 
        Integer.valueOf(2002), 
        Integer.valueOf(2003), 
        Integer.valueOf(2004), 
        Integer.valueOf(2005), 
        Integer.valueOf(2006), 
        Integer.valueOf(70), 
        Integer.valueOf(16), 
        Integer.valueOf(1111) };
  }
  
  public static Integer getTypeByName(String paramString) {
    if ("TINYINT".equalsIgnoreCase(paramString))
      return Integer.valueOf(-6); 
    if ("SMALLINT".equalsIgnoreCase(paramString))
      return Integer.valueOf(5); 
    if ("INTEGER".equalsIgnoreCase(paramString))
      return Integer.valueOf(4); 
    if ("BIT".equalsIgnoreCase(paramString))
      return Integer.valueOf(-7); 
    if ("BIGINT".equalsIgnoreCase(paramString))
      return Integer.valueOf(-5); 
    if ("FLOAT".equalsIgnoreCase(paramString))
      return Integer.valueOf(6); 
    if ("REAL".equalsIgnoreCase(paramString))
      return Integer.valueOf(7); 
    if ("DOUBLE".equalsIgnoreCase(paramString))
      return Integer.valueOf(8); 
    if ("NUMERIC".equalsIgnoreCase(paramString))
      return Integer.valueOf(2); 
    if ("DECIMAL ".equalsIgnoreCase(paramString))
      return Integer.valueOf(3); 
    if ("CHAR".equalsIgnoreCase(paramString))
      return Integer.valueOf(1); 
    if ("VARCHAR".equalsIgnoreCase(paramString))
      return Integer.valueOf(12); 
    if ("LONGVARCHAR".equalsIgnoreCase(paramString))
      return Integer.valueOf(-1); 
    if ("DATE".equalsIgnoreCase(paramString))
      return Integer.valueOf(91); 
    if ("TIME".equalsIgnoreCase(paramString))
      return Integer.valueOf(92); 
    if ("TIMESTAMP".equalsIgnoreCase(paramString))
      return Integer.valueOf(93); 
    if ("BINARY".equalsIgnoreCase(paramString))
      return Integer.valueOf(-2); 
    if ("VARBINARY".equalsIgnoreCase(paramString))
      return Integer.valueOf(-3); 
    if ("LONGVARBINARY".equalsIgnoreCase(paramString))
      return Integer.valueOf(-4); 
    if ("NULL".equalsIgnoreCase(paramString))
      return Integer.valueOf(0); 
    if ("OTHER".equalsIgnoreCase(paramString))
      return Integer.valueOf(1111); 
    if ("JAVA_OBJECT ".equalsIgnoreCase(paramString))
      return Integer.valueOf(2000); 
    if ("DISTINCT".equalsIgnoreCase(paramString))
      return Integer.valueOf(2001); 
    if ("STRUCT".equalsIgnoreCase(paramString))
      return Integer.valueOf(2002); 
    if ("ARRAY".equalsIgnoreCase(paramString))
      return Integer.valueOf(2003); 
    if ("BLOB".equalsIgnoreCase(paramString))
      return Integer.valueOf(2004); 
    if ("CLOB".equalsIgnoreCase(paramString))
      return Integer.valueOf(2005); 
    if ("REF".equalsIgnoreCase(paramString))
      return Integer.valueOf(2006); 
    if ("DATALINK".equalsIgnoreCase(paramString))
      return Integer.valueOf(70); 
    if ("BOOLEAN".equalsIgnoreCase(paramString))
      return Integer.valueOf(16); 
    return null;
  }
  
  public static int[] getCompatibleJavaTypes(int paramInt) {
    switch (paramInt) {
      case -6:
        (new int[2])[0] = 4;
        (new int[2])[1] = 2;
      case 5:
        (new int[2])[0] = 4;
        (new int[2])[1] = 2;
      case 4:
        (new int[2])[0] = 4;
        (new int[2])[1] = 2;
      case -7:
        (new int[3])[0] = -6;
        (new int[3])[1] = 4;
        (new int[3])[2] = 2;
      case -5:
        (new int[2])[0] = 4;
        (new int[2])[1] = 2;
      case 6:
        (new int[2])[0] = 3;
        (new int[2])[1] = 2;
      case 7:
        (new int[2])[0] = 3;
        (new int[2])[1] = 2;
      case 8:
        (new int[2])[0] = 3;
        (new int[2])[1] = 2;
      case 2:
        (new int[2])[0] = 3;
        (new int[2])[1] = 4;
      case 3:
        (new int[2])[0] = 3;
        (new int[2])[1] = 2;
      case 1:
      
      case 12:
      
      case -1:
      
      case 91:
        (new int[1])[0] = 93;
      case 92:
        (new int[1])[0] = 91;
      case 93:
        (new int[2])[0] = 92;
        (new int[2])[1] = 91;
      case -2:
      
      case -3:
      
      case -4:
      
      case 0:
      
      case 1111:
      
      case 2000:
      
      case 2001:
      
      case 2002:
      
      case 2003:
      
      case 2004:
      
      case 2005:
      
      case 2006:
      
      case 70:
      
      case 16:
        (new int[3])[0] = -6;
        (new int[3])[1] = 4;
        (new int[3])[2] = 2;
    } 
    return new int[0];
  }
  
  public static int getJavaTypeFromValue(Object paramObject) {
    if (paramObject instanceof Integer)
      return 4; 
    if (paramObject instanceof Long)
      return -5; 
    if (paramObject instanceof Double)
      return 8; 
    if (paramObject instanceof java.math.BigDecimal)
      return 2; 
    if (paramObject instanceof java.sql.Timestamp)
      return 93; 
    if (paramObject instanceof java.util.Date)
      return 91; 
    if (paramObject instanceof String)
      return 12; 
    if (paramObject instanceof Boolean)
      return 16; 
    if (paramObject instanceof java.util.List)
      return 4999545; 
    if (paramObject instanceof java.util.Map)
      return 4999544; 
    return 12;
  }
}
