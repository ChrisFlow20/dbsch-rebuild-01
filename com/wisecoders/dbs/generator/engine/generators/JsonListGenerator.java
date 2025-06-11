package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonListGenerator extends Generator {
  public static final String a = "json_list:";
  
  public static String b = "json_list:count_min=1;count_max=3;";
  
  private double c = 0.0D;
  
  private double d = 3.0D;
  
  private final Map g;
  
  public JsonListGenerator(String paramString, int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
    this.g = new HashMap<>();
    parseProperties(paramString);
  }
  
  public void addProperty(String paramString1, String paramString2, int paramInt) {
    if ("count_min".equalsIgnoreCase(paramString1)) {
      this.c = Double.parseDouble(paramString2);
    } else if ("count_max".equalsIgnoreCase(paramString1)) {
      this.d = Double.parseDouble(paramString2);
    } else {
      throw new ParseException("Unknown property " + paramString1, paramInt);
    } 
  }
  
  public Object generate(GeneratorTable paramGeneratorTable, Column paramColumn) {
    double d1 = this.d - this.c;
    double d2 = this.f.nextDouble();
    int i = (int)(this.c + d2 * d1);
    ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
    if (paramColumn.hasChildEntity())
      for (byte b = 0; b < i; b++) {
        if (paramGeneratorTable == null && this.g.isEmpty()) {
          byte b1 = 0;
          for (Column column : (paramColumn.getChildEntity()).columns)
            this.g.put(column, Generator.getGenerator(column, b1++)); 
        } 
        HashMap<Object, Object> hashMap = new HashMap<>();
        for (Column column : (paramColumn.getChildEntity()).columns) {
          Generator generator = (paramGeneratorTable != null) ? paramGeneratorTable.getCachedGenerator(column) : (Generator)this.g.get(column);
          if (generator != null)
            hashMap.put(column.getName(), generator.generate(paramGeneratorTable, column)); 
        } 
        arrayList.add(hashMap);
      }  
    return arrayList;
  }
}
