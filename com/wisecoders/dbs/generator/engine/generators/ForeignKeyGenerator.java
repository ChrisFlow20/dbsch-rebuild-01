package com.wisecoders.dbs.generator.engine.generators;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class ForeignKeyGenerator extends Generator {
  public static final String a = "load_values_from_pk";
  
  public static String b = "load_values_from_pk";
  
  private final List c = new UniqueArrayList();
  
  private boolean d = false;
  
  public ForeignKeyGenerator(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
  }
  
  public Object generate(GeneratorTable paramGeneratorTable, Column paramColumn) {
    int i = this.c.size();
    if (i == 0)
      return this.d ? null : "load_values_from_pk"; 
    float f = this.f.nextFloat();
    int j = (int)(f * i);
    return this.c.get(j);
  }
  
  public int a(Envoy paramEnvoy, Column paramColumn) {
    String str = paramEnvoy.a.isMongo() ? (paramColumn.getEntity().ref() + ".find()") : ("SELECT  " + paramColumn.ref() + " FROM " + paramColumn.getEntity().ref());
    byte b = 0;
    SelectStatement selectStatement = paramEnvoy.a(str, new Object[0]);
    try {
      selectStatement.c("Learn values from Pk column");
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next() && b++ < Sys.B.fkReadCount.a()) {
        Object object = resultSet.getObject(1);
        if (object instanceof Map) {
          Map map = (Map)object;
          if (map.containsKey(paramColumn.getName()))
            object = map.get(paramColumn.getName()); 
        } 
        this.c.add(object);
      } 
      this.d = true;
      if (selectStatement != null)
        selectStatement.close(); 
    } catch (Throwable throwable) {
      if (selectStatement != null)
        try {
          selectStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    return b;
  }
  
  public void a() {
    this.c.clear();
    this.d = false;
  }
  
  public void a(Object paramObject) {
    if (paramObject != null && this.c.size() < 1000)
      this.c.add(paramObject); 
  }
  
  public String toString() {
    return "Foreign Key Generator";
  }
}
