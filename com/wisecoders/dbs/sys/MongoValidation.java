package com.wisecoders.dbs.sys;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DoNotObfuscate
public class MongoValidation {
  public static String convertObjectToString(Object paramObject) {
    if (paramObject == null)
      return "null"; 
    if (paramObject instanceof Number)
      return paramObject.toString(); 
    return "org.bson.types.ObjectId".equals(paramObject.getClass().getName()) ? ("ObjectId(\"" + String.valueOf(paramObject) + "\")") : ("'" + 
      String.valueOf(paramObject) + "'");
  }
  
  public static List collectObjectValues(Object paramObject, Column paramColumn) {
    ArrayList<Object> arrayList = new ArrayList();
    if (paramColumn.getParentColumn() == null) {
      arrayList.add(paramObject);
    } else {
      String str = paramColumn.getNameWithPath();
      int i = str.indexOf('.');
      if (i > -1)
        str = str.substring(i + 1); 
      a(paramObject, str, "", arrayList);
    } 
    return arrayList;
  }
  
  private static void a(Object paramObject, String paramString1, String paramString2, List<Object> paramList) {
    if (paramString1.equals(paramString2))
      paramList.add(paramObject); 
    if (paramObject != null)
      if (paramObject instanceof Map) {
        Map map = (Map)paramObject;
        for (Object object : map.keySet())
          a(map.get(object), paramString1, ((paramString2.length() > 0) ? (paramString2 + ".") : "") + ((paramString2.length() > 0) ? (paramString2 + ".") : ""), paramList); 
      } else if (paramObject.getClass().isArray()) {
        for (Object object : (Object[])paramObject)
          a(object, paramString1, paramString2, paramList); 
      } else if (paramObject instanceof List) {
        for (Object object : paramObject)
          a(object, paramString1, paramString2, paramList); 
      }  
  }
  
  @GroovyMethod
  public static String getValidator(Table paramTable) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    a("object", paramTable.getComment(), paramTable.columns, hashMap);
    return (new GsonBuilder()).setPrettyPrinting().create().toJson(hashMap);
  }
  
  private static void a(String paramString1, String paramString2, List paramList, Map<String, String> paramMap) {
    paramMap.put("bsonType", paramString1);
    if (paramString2 != null)
      paramMap.put("description", paramString2); 
    List list = a(paramList);
    if (!list.isEmpty())
      paramMap.put("required", list); 
    HashMap<Object, Object> hashMap = new HashMap<>();
    paramMap.put("properties", hashMap);
    for (Column column : paramList) {
      if (!"_id".equals(column.getName()))
        hashMap.put(column.getName(), a(column)); 
    } 
  }
  
  private static Map a(Column paramColumn) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    String str = paramColumn.getDataType().getName();
    if (paramColumn.getComment() != null)
      hashMap.put("description", paramColumn.getComment()); 
    if (paramColumn.getTypeOptions() != null) {
      JsonObject jsonObject = parseStringAsJsonObject(paramColumn.getTypeOptions());
      for (String str1 : jsonObject.keySet())
        hashMap.put(str1, jsonObject.get(str1)); 
    } 
    if ("enum".equals(str) && paramColumn.getEnumeration() != null) {
      hashMap.put("enum", parseStringAsJsonArray(paramColumn.getEnumeration()));
    } else if ("object".equals(str) && paramColumn.hasChildEntity()) {
      a("object", paramColumn.getComment(), (paramColumn.getChildEntity()).columns, hashMap);
    } else if (str.startsWith("array[")) {
      hashMap.put("bsonType", "array");
      HashMap<Object, Object> hashMap1 = new HashMap<>();
      hashMap.put("items", hashMap1);
      String str1 = str.substring("array[".length(), str.length() - 1);
      hashMap1.put("bsonType", str1);
      if ("object".equals(str1) && paramColumn.hasChildEntity())
        a("object", paramColumn.getComment(), (paramColumn.getChildEntity()).columns, hashMap1); 
    } else if (str.startsWith("array")) {
      hashMap.put("bsonType", "array");
      HashMap<Object, Object> hashMap1 = new HashMap<>();
      hashMap.put("items", hashMap1);
    } else {
      hashMap.put("bsonType", str);
    } 
    return hashMap;
  }
  
  private static List a(List paramList) {
    ArrayList<String> arrayList = new ArrayList();
    for (Column column : paramList) {
      if (column.isMandatory() && !"_id".equals(column.getName()))
        arrayList.add(column.getName()); 
    } 
    return arrayList;
  }
  
  public static JsonArray parseStringAsJsonArray(String paramString) {
    if (!paramString.startsWith("[") && !paramString.endsWith("]"))
      paramString = "[" + paramString + "]"; 
    return JsonParser.parseString(paramString).getAsJsonArray();
  }
  
  public static JsonObject parseStringAsJsonObject(String paramString) {
    if (!paramString.startsWith("{") && !paramString.endsWith("}"))
      paramString = "{" + paramString + "}"; 
    return JsonParser.parseString(paramString).getAsJsonObject();
  }
}
