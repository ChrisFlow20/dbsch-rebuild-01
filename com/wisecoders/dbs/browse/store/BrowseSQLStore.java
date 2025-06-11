package com.wisecoders.dbs.browse.store;

import com.wisecoders.dbs.browse.model.BrowseResult;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.filter.FilterPattern;
import com.wisecoders.dbs.data.filter.Filters;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.connect.model.envoy.StatementParameter;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrowseSQLStore extends BrowseStore {
  public BrowseSQLStore(BrowseResult paramBrowseResult) {
    super(paramBrowseResult);
  }
  
  public void a(Envoy paramEnvoy) {
    this.c = System.currentTimeMillis();
    String str = paramEnvoy.e();
    Dbms dbms = Dbms.get(str);
    this.a.c(false);
    ArrayList<StatementParameter> arrayList = new ArrayList();
    StringBuilder stringBuilder = new StringBuilder("SELECT ");
    if (dbms.selectHint.j())
      stringBuilder.append(" ").append(dbms.selectHint.c_()).append(" "); 
    boolean bool1 = true;
    for (Attribute attribute : this.a.c.c.getAttributes()) {
      if (attribute.getSpec() != AttributeSpec.functional) {
        stringBuilder.append(bool1 ? "" : ", ").append(attribute.ref());
        bool1 = false;
      } 
    } 
    if (bool1)
      stringBuilder.append("* "); 
    stringBuilder.append(" ");
    stringBuilder.append(" FROM ").append(this.a.c.c.ref());
    boolean bool2 = true;
    for (Attribute attribute : this.a.c.f()) {
      stringBuilder.append(bool2 ? " WHERE " : " AND ");
      String str1 = this.a.c.b(attribute);
      FilterPattern filterPattern = Filters.a(attribute, str1);
      if (filterPattern == null) {
        stringBuilder.append(str1);
      } else {
        stringBuilder.append(filterPattern.a(paramEnvoy.e(), str1, attribute.ref()));
      } 
      bool2 = false;
    } 
    if (this.a.c.e() != null) {
      stringBuilder.append(bool2 ? " WHERE " : " AND ");
      stringBuilder.append(this.a.c.e());
    } 
    if (this.a.c.d != null) {
      stringBuilder.append(bool2 ? " WHERE " : " AND ");
      boolean bool3 = ((this.a.c.b.e.h()).length > 1) ? true : false;
      if (bool3)
        stringBuilder.append("("); 
      boolean bool4 = true;
      Integer[] arrayOfInteger;
      int i;
      byte b;
      for (arrayOfInteger = this.a.c.b.e.h(), i = arrayOfInteger.length, b = 0; b < i; ) {
        int j = arrayOfInteger[b].intValue();
        stringBuilder.append(bool4 ? " " : " OR ");
        boolean bool = (this.a.c.d.getColumnCount() > 1) ? true : false;
        if (bool)
          stringBuilder.append("("); 
        for (byte b1 = 0; b1 < this.a.c.d.getColumnCount(); b1++) {
          Attribute attribute1 = this.a.c.d.getColumnAt(b1, this.a.c.g);
          Attribute attribute2 = this.a.c.d.getColumnAt(b1, !this.a.c.g);
          stringBuilder.append((b1 > 0) ? " AND " : " ");
          stringBuilder.append(attribute1.ref()).append("=");
          String str1 = attribute1.getDataType().getUpdateCast();
          stringBuilder.append(StringUtil.isFilled(str1) ? str1 : "?");
          Object object = this.a.c.b.e.a(j, attribute2);
          arrayList.add(new StatementParameter(object, attribute2.getDataType()));
        } 
        bool4 = false;
        stringBuilder.append(" ");
        if (bool)
          stringBuilder.append(")"); 
        b++;
      } 
      stringBuilder.append(" ");
      if (bool3)
        stringBuilder.append(")"); 
    } 
    if (!this.a.c.g().isEmpty()) {
      stringBuilder.append(" ORDER BY ");
      bool2 = true;
      for (Attribute attribute : this.a.c.g()) {
        if (!bool2)
          stringBuilder.append(", "); 
        stringBuilder.append(attribute.ref());
        if (this.a.c.e(attribute) == -1)
          stringBuilder.append(" DESC"); 
        bool2 = false;
      } 
    } else if ((Dbms.get(str)).userOrderBy.b()) {
      Index index = this.a.c.b();
      if (index != null && index.areColumnsMandatory()) {
        stringBuilder.append(" ORDER BY ");
        bool2 = true;
        for (Attribute attribute : index.getColumns()) {
          if (!bool2)
            stringBuilder.append(", "); 
          stringBuilder.append(attribute.ref());
          bool2 = false;
        } 
      } 
    } 
    if (dbms.useLimit.b())
      if (dbms.useOffset.b()) {
        stringBuilder.append(" LIMIT ").append(this.a.u());
        if (this.a.s() > 0) {
          stringBuilder.append(" OFFSET ").append(this.a.s() * this.a.u());
          this.a.c(true);
        } 
      } else {
        stringBuilder.append(" LIMIT ").append((this.a.s() + 1) * this.a.u());
      }  
    SelectStatement selectStatement = paramEnvoy.a(stringBuilder.toString(), new Object[0]);
    try {
      selectStatement.a(arrayList);
      selectStatement.a(dbms.loadTimeout.a());
      Log.d("Browse: " + String.valueOf(stringBuilder));
      paramEnvoy.b(true);
      ResultSet resultSet = selectStatement.j();
      try {
        this.b = true;
        if (resultSet != null && (
          this.d == null || !this.d.isCancelled()))
          this.a.a(str, resultSet); 
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
      paramEnvoy.p();
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
    paramEnvoy.m();
  }
  
  public void b(Envoy paramEnvoy) {
    if (paramEnvoy == null)
      throw new SQLException("No connection to database."); 
    if (Sys.B.readOnly.b())
      throw new SQLException(Sys.B.readOnlyMessage.c_()); 
    String str = paramEnvoy.e();
    Dbms dbms = Dbms.get(str);
    StringBuilder stringBuilder1 = new StringBuilder();
    StringBuilder stringBuilder2 = new StringBuilder();
    ArrayList<StatementParameter> arrayList = new ArrayList();
    stringBuilder1.append(dbms.tableInsert.c_()).append(" ").append(this.a.c.c.ref()).append(" ( ");
    boolean bool = true;
    for (byte b = 0; b < this.a.c.c.getAttributes().size(); b++) {
      Attribute attribute = this.a.c.c.getAttributes().get(b);
      Object object = this.a.a(this.a.A() - 1, b);
      boolean bool1 = (attribute.getAssociatedSequence() != null && dbms.columnInsertFromSequence.j()) ? true : false;
      if (bool1 || object != null) {
        if (!bool) {
          stringBuilder1.append(", ");
          stringBuilder2.append(", ");
        } 
        bool = false;
      } 
      if (bool1) {
        stringBuilder1.append(attribute.ref());
        stringBuilder2.append(dbms.columnInsertFromSequence.c_().replaceAll("\\$\\{name}", attribute.getAssociatedSequence().ref()));
      } else if (object != null) {
        stringBuilder1.append(attribute.ref());
        arrayList.add(new StatementParameter("NULL".equals(object) ? null : object, attribute.getDataType()));
        String str1 = attribute.getDataType().getUpdateCast();
        stringBuilder2.append(StringUtil.isFilled(str1) ? str1 : "?");
      } 
    } 
    stringBuilder1.append(" ) VALUES ( ").append(stringBuilder2).append(" )");
    UpdateStatement updateStatement = paramEnvoy.b(stringBuilder1.toString(), new Object[0]);
    try {
      updateStatement.a(arrayList);
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
  
  public void a(int paramInt, ResultColumn paramResultColumn, Object paramObject, Envoy paramEnvoy) {
    if (Sys.B.readOnly.b())
      throw new SQLException(Sys.B.readOnlyMessage.c_()); 
    Attribute attribute = paramResultColumn.a();
    StringBuilder stringBuilder = new StringBuilder();
    if (Sys.B.readOnly.b())
      throw new SQLException(Sys.B.readOnlyMessage.c_()); 
    stringBuilder.append("UPDATE ")
      .append(this.a.c.c.ref())
      .append(" SET ")
      .append(attribute.ref()).append("=");
    String str = attribute.getDataType().getUpdateCast();
    stringBuilder.append(StringUtil.isFilledTrim(str) ? str : "?");
    stringBuilder.append(" WHERE ");
    ArrayList<StatementParameter> arrayList = new ArrayList();
    arrayList.add(new StatementParameter(paramObject, attribute.getDataType()));
    boolean bool = true;
    if (this.a.c.b() == null) {
      for (Attribute attribute1 : this.a.c.c.getAttributes()) {
        if (!bool)
          stringBuilder.append(" AND "); 
        stringBuilder.append(attribute1.ref()).append("=?");
        Object object = this.a.k() ? this.a.a(attribute1) : this.a.a(paramInt, attribute1);
        arrayList.add(new StatementParameter(object, attribute1.getDataType()));
        bool = false;
      } 
    } else {
      for (Column column : this.a.c.b().getColumns()) {
        if (!bool)
          stringBuilder.append(" AND "); 
        stringBuilder.append(column.ref()).append("=?");
        Object object = this.a.k() ? this.a.a(column) : this.a.a(paramInt, column);
        arrayList.add(new StatementParameter(object, column.getDataType()));
        bool = false;
      } 
    } 
    paramEnvoy.a.throwSQLExceptionIfReadOnly();
    UpdateStatement updateStatement = paramEnvoy.b(stringBuilder.toString(), new Object[0]);
    try {
      updateStatement.a(arrayList);
      updateStatement.a();
      paramEnvoy.p();
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
  }
  
  public void a(Integer[] paramArrayOfInteger, Envoy paramEnvoy) {
    if (paramEnvoy == null)
      throw new SQLException("No connection to database."); 
    if (Sys.B.readOnly.b())
      throw new SQLException(Sys.B.readOnlyMessage.c_()); 
    if ((this.a.h()).length == 0)
      throw new SQLException("Please select one record to delete"); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("DELETE FROM ").append(this.a.c.c.ref()).append(" WHERE ");
    boolean bool = true;
    for (Column column : this.a.c.b().getColumns()) {
      if (!bool)
        stringBuilder.append(" AND "); 
      stringBuilder.append(column.ref()).append("=?");
      bool = false;
    } 
    Integer[] arrayOfInteger;
    int i;
    byte b;
    for (arrayOfInteger = paramArrayOfInteger, i = arrayOfInteger.length, b = 0; b < i; ) {
      int j = arrayOfInteger[b].intValue();
      UpdateStatement updateStatement = paramEnvoy.b(stringBuilder.toString(), new Object[0]);
      try {
        for (Column column : this.a.c.b().getColumns())
          updateStatement.a(new StatementParameter(this.a.a(j, column), column.getDataType())); 
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
      b++;
    } 
    paramEnvoy.p();
  }
}
