package com.wisecoders.dbs.data.model.result;

import java.util.Map;

public interface ResultRow {
  Object a(ResultColumn paramResultColumn);
  
  void a(ResultColumn paramResultColumn, Object paramObject);
  
  boolean a();
  
  boolean a(String paramString, boolean paramBoolean1, boolean paramBoolean2);
  
  static boolean a(Object paramObject, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramObject == null)
      return false; 
    if (paramObject instanceof Map) {
      Map map = (Map)paramObject;
      for (Object object : map.keySet()) {
        if (a(object, paramString, paramBoolean1, paramBoolean2) || 
          a(map.get(object), paramString, paramBoolean1, paramBoolean2))
          return true; 
      } 
    } else if (paramObject.getClass().isArray()) {
      Object[] arrayOfObject = (Object[])paramObject;
      for (Object object : arrayOfObject) {
        if (a(object, paramString, paramBoolean1, paramBoolean2))
          return true; 
      } 
    } else {
      String str = paramObject.toString();
      if (str != null) {
        if (paramBoolean1)
          return paramString.matches(str); 
        if (paramBoolean2)
          return str.contains(paramString); 
        return str.toLowerCase().contains(paramString.toLowerCase());
      } 
    } 
    return false;
  }
}
