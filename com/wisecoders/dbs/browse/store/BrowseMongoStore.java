package com.wisecoders.dbs.browse.store;

import com.wisecoders.dbs.browse.model.BrowseResult;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.filter.FilterPattern;
import com.wisecoders.dbs.data.filter.Filters;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultMapRow;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.connect.model.envoy.StatementParameter;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.query.model.engine.MongoExpression;
import com.wisecoders.dbs.query.model.engine.MongoExpression$When;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.MongoValidation;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BrowseMongoStore extends BrowseStore {
  public BrowseMongoStore(BrowseResult paramBrowseResult) {
    super(paramBrowseResult);
  }
  
  public void a(Envoy paramEnvoy) {
    this.c = System.currentTimeMillis();
    this.a.x();
    for (Attribute attribute : this.a.c.c.getAttributes())
      this.a.a(attribute.getName(), attribute.getDataType().getJavaType(), false); 
    this.a.q();
    SelectStatement selectStatement = paramEnvoy.a(a(paramEnvoy.e()), new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      try {
        this.b = true;
        if (resultSet != null && (
          this.d == null || !this.d.isCancelled()))
          this.a.a(paramEnvoy.e(), resultSet); 
        if (resultSet != null)
          resultSet.close(); 
      } catch (Throwable throwable) {
        if (resultSet != null)
          try {
            resultSet.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
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
  }
  
  private String a(String paramString) {
    MongoExpression mongoExpression1 = new MongoExpression();
    mongoExpression1.a(MongoExpression$When.a, ".find(", ")");
    mongoExpression1.a(MongoExpression$When.c, "{$and:[", "]}");
    if (this.a.c.c instanceof ChildEntity)
      mongoExpression1.add((E)("{'" + ((ChildEntity)this.a.c.c).ownerColumn.getNameWithPath() + "':{$exists: true }}")); 
    if (this.a.c.d != null) {
      MongoExpression mongoExpression = new MongoExpression();
      mongoExpression.a(MongoExpression$When.c, "{$or:[", "]}");
      for (Integer integer : this.a.c.b.e.h()) {
        MongoExpression mongoExpression3 = new MongoExpression();
        mongoExpression3.a(MongoExpression$When.c, "{$and:[", "]}");
        for (byte b = 0; b < this.a.c.d.getColumnCount(); b++) {
          Column column1 = (Column)this.a.c.d.getColumnAt(b, this.a.c.g);
          Column column2 = (Column)this.a.c.d.getColumnAt(b, !this.a.c.g);
          Object object = this.a.c.b.e.a(integer.intValue(), column2.getRootParentColumn());
          MongoExpression mongoExpression4 = new MongoExpression();
          mongoExpression4.a(MongoExpression$When.c, "{$or:[", "]}");
          for (Object object1 : MongoValidation.collectObjectValues(object, column2))
            mongoExpression4.add((E)("{" + column1.ref() + ":{$eq:" + MongoValidation.convertObjectToString(object1) + "}}")); 
          mongoExpression3.add((E)mongoExpression4.toString());
        } 
        mongoExpression.add((E)mongoExpression3.toString());
      } 
      mongoExpression1.add((E)mongoExpression.toString());
    } 
    a(this.a.c, paramString, mongoExpression1);
    MongoExpression mongoExpression2 = new MongoExpression();
    mongoExpression2.a(MongoExpression$When.b, ".sort({", "})");
    b(this.a.c, paramString, mongoExpression2);
    Entity entity = this.a.c.c;
    if (entity instanceof ChildEntity)
      entity = this.a.c.c.getEntity(); 
    return entity.ref() + entity.ref() + String.valueOf(mongoExpression1);
  }
  
  private void a(BrowseTable paramBrowseTable, String paramString, List<String> paramList) {
    for (Attribute attribute : paramBrowseTable.f()) {
      String str = paramBrowseTable.b(attribute);
      FilterPattern filterPattern = Filters.a(attribute, str);
      paramList.add((filterPattern != null) ? filterPattern.a(paramString, str, attribute.ref()) : str);
    } 
    for (BrowseTable browseTable : paramBrowseTable.d()) {
      if (!browseTable.d.isVirtual())
        a(browseTable, paramString, paramList); 
    } 
  }
  
  private void b(BrowseTable paramBrowseTable, String paramString, List<String> paramList) {
    for (Attribute attribute : paramBrowseTable.g())
      paramList.add(attribute.ref() + ":" + attribute.ref()); 
    for (BrowseTable browseTable : paramBrowseTable.d()) {
      if (!browseTable.d.isVirtual())
        b(browseTable, paramString, paramList); 
    } 
  }
  
  public void a(int paramInt, ResultColumn paramResultColumn, Object paramObject, Envoy paramEnvoy) {
    if (Sys.B.readOnly.b())
      throw new SQLException(Sys.B.readOnlyMessage.c_()); 
    ResultRow resultRow = this.a.e(paramInt);
    if (resultRow instanceof ResultMapRow) {
      ResultMapRow resultMapRow = (ResultMapRow)resultRow;
      resultMapRow.a.put(paramResultColumn.a, a(paramObject));
      a(resultMapRow.a, paramEnvoy);
    } 
  }
  
  public void b(Envoy paramEnvoy) {
    if (Sys.B.readOnly.b())
      throw new SQLException(Sys.B.readOnlyMessage.c_()); 
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
    for (byte b = 0; b < this.a.c.c.getAttributes().size(); b++) {
      Attribute attribute = this.a.c.c.getAttributes().get(b);
      Object object = a(this.a.a(attribute));
      if (object != null)
        linkedHashMap.put(attribute.getName(), object); 
    } 
    a(linkedHashMap, paramEnvoy);
  }
  
  private void a(Object paramObject, Envoy paramEnvoy) {
    UpdateStatement updateStatement = paramEnvoy.b("update " + this.a.c.c.ref(), new Object[0]);
    try {
      updateStatement.a(new StatementParameter(paramObject, 2000));
      updateStatement.a();
      if (updateStatement != null)
        updateStatement.close(); 
    } catch (Throwable throwable) {
      if (updateStatement != null)
        try {
          updateStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    paramEnvoy.p();
  }
  
  private Object a(Object paramObject) {
    if (paramObject instanceof Timestamp)
      return new Date(((Timestamp)paramObject).getTime()); 
    if (paramObject instanceof Date)
      return new Date(((Date)paramObject).getTime()); 
    return paramObject;
  }
  
  public void a(Integer[] paramArrayOfInteger, Envoy paramEnvoy) {
    if (Sys.B.readOnly.b())
      throw new SQLException(Sys.B.readOnlyMessage.c_()); 
    Integer[] arrayOfInteger;
    int i;
    byte b;
    for (arrayOfInteger = paramArrayOfInteger, i = arrayOfInteger.length, b = 0; b < i; ) {
      int j = arrayOfInteger[b].intValue();
      ResultRow resultRow = this.a.e(j);
      Map map = ((ResultMapRow)resultRow).a;
      UpdateStatement updateStatement = paramEnvoy.b("delete from " + this.a.c.c.ref(), new Object[0]);
      try {
        updateStatement.a(new StatementParameter(map, 2000));
        updateStatement.a();
        if (updateStatement != null)
          updateStatement.close(); 
      } catch (Throwable throwable) {
        if (updateStatement != null)
          try {
            updateStatement.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
      paramEnvoy.p();
      b++;
    } 
  }
}
