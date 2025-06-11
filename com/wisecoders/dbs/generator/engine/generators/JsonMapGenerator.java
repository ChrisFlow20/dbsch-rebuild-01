package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import java.text.ParseException;
import java.util.HashMap;

public class JsonMapGenerator extends Generator {
  public static final String a = "json_map:";
  
  public static String b = "json_map:";
  
  public JsonMapGenerator(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    parseProperties(paramString);
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    throw new ParseException("Unknown property " + paramString1, paramInt);
  }
  
  public Object generate(GeneratorTable paramGeneratorTable, Column paramColumn) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    if (paramColumn.hasChildEntity()) {
      byte b = 0;
      for (Column column : (paramColumn.getChildEntity()).columns) {
        Generator generator = Generator.getGenerator(column, b++);
        hashMap.put(column.getName(), generator.generate(paramGeneratorTable, column));
      } 
    } 
    return hashMap;
  }
}
