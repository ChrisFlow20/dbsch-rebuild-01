package com.wisecoders.dbs.data.model.result;

import com.wisecoders.dbs.schema.DataTypeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultMapRow implements ResultRow {
  public final Map a;
  
  public ResultMapRow(Result paramResult, Map paramMap) {
    this.a = paramMap;
    a(paramResult);
  }
  
  public Object a(ResultColumn paramResultColumn) {
    return this.a.get(paramResultColumn.a);
  }
  
  public void a(ResultColumn paramResultColumn, Object paramObject) {
    this.a.put(paramResultColumn.a, paramObject);
  }
  
  private void a(Result paramResult) {
    ArrayList arrayList = new ArrayList();
    if (this.a != null) {
      arrayList.addAll(this.a.keySet());
      for (ResultColumn resultColumn : paramResult.f)
        arrayList.remove(resultColumn.a); 
      for (String str : arrayList)
        paramResult.a(str, DataTypeUtil.getJavaTypeFromValue(this.a.get(str)), false); 
      if (!arrayList.isEmpty())
        paramResult.q(); 
    } 
  }
  
  private static boolean a(Object paramObject) {
    if (paramObject instanceof List) {
      List list = (List)paramObject;
      for (Object object : list) {
        if (!(object instanceof Map))
          return false; 
      } 
      return true;
    } 
    return false;
  }
  
  public boolean b(ResultColumn paramResultColumn) {
    if (paramResultColumn != null) {
      Object object = this.a.get(paramResultColumn.a);
      if (object instanceof Map) {
        Map map = (Map)object;
        if (!map.isEmpty())
          return true; 
      } 
      return a(object);
    } 
    return false;
  }
  
  public boolean a() {
    return true;
  }
  
  public boolean a(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    return ResultRow.a(this.a, paramString, paramBoolean1, paramBoolean2);
  }
}
