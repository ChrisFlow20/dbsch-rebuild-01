package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DoNotObfuscate
public interface DbUnit extends Unit {
  String getDbId();
  
  Schema getSchema();
  
  static boolean optionsAreEqual(String paramString1, String paramString2) {
    if (paramString1 == null && paramString2 == null)
      return true; 
    if (paramString1 == null || paramString2 == null)
      return false; 
    List<String> list1 = Arrays.asList(paramString1.split(" "));
    List<String> list2 = Arrays.asList(paramString2.split(" "));
    if (list1.size() != list2.size())
      return false; 
    Collections.sort(list1);
    Collections.sort(list2);
    return list1.equals(list2);
  }
}
