package com.wisecoders.dbs.data.model.result;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.InputStreamUtil;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.util.Callback;

public class Result {
  private static boolean a = false;
  
  private final List b = new ArrayList();
  
  public final ObservableList f = FXCollections.observableArrayList();
  
  private final List c = new ArrayList();
  
  public final ObservableList g = FXCollections.observableArrayList();
  
  private int d = 0;
  
  private int e = 100;
  
  private String h;
  
  private List i;
  
  private Object j = null;
  
  private boolean k = false;
  
  private Callback l;
  
  private Callback m;
  
  public static void o() {
    a = true;
  }
  
  public void a(List paramList, Object paramObject) {
    this.i = paramList;
    this.j = paramObject;
  }
  
  public boolean p() {
    return (this.i != null && !this.i.isEmpty());
  }
  
  public void a(Statement paramStatement) {
    paramStatement.setFetchSize(10);
  }
  
  public ResultColumn a(String paramString, int paramInt, boolean paramBoolean) {
    ResultColumn resultColumn = new ResultColumn(paramString, this.b.size() + this.f.size(), paramInt, paramBoolean);
    this.b.add(resultColumn);
    return resultColumn;
  }
  
  public void q() {
    if (a || FxUtil.a()) {
      a_();
    } else {
      FxUtil.a(this::a_);
    } 
  }
  
  protected synchronized void a_() {
    if (!this.b.isEmpty()) {
      this.f.addAll(this.b);
      this.b.clear();
      if (this.m != null)
        this.m.call(null); 
    } 
    if (!this.c.isEmpty()) {
      this.g.addAll(this.c);
      this.c.clear();
      if (this.l != null)
        this.l.call(null); 
    } 
  }
  
  public void b(Callback paramCallback) {
    this.l = paramCallback;
  }
  
  public void c(Callback paramCallback) {
    this.m = paramCallback;
  }
  
  public void d(Callback paramCallback) {
    this.m = paramCallback;
    this.l = paramCallback;
  }
  
  public ResultColumn b(Attribute paramAttribute) {
    for (ResultColumn resultColumn : this.f) {
      if (resultColumn.a() == paramAttribute)
        return resultColumn; 
    } 
    return null;
  }
  
  public ResultColumn d(int paramInt) {
    return (paramInt > -1 && paramInt < this.f.size()) ? (ResultColumn)this.f.get(paramInt) : null;
  }
  
  public ResultRow e(int paramInt) {
    return (paramInt > -1 && paramInt < this.g.size()) ? (ResultRow)this.g.get(paramInt) : null;
  }
  
  public Class f(int paramInt) {
    ResultColumn resultColumn = d(paramInt);
    if (resultColumn != null) {
      switch (resultColumn.b) {
        case -6:
        case -5:
        case 4:
        case 5:
        
        case 6:
        case 7:
        
        case 8:
        
        case 2:
        case 3:
        
        case 1:
        
        case 91:
        
        case 92:
        
        case 93:
        
        case -7:
        case 16:
        
        case -1:
        case 12:
        
        case 2005:
        
        case -4:
        case -2:
        case 2004:
        
      } 
      return 















        
        String.class;
    } 
    return String.class;
  }
  
  public boolean g(int paramInt) {
    ResultColumn resultColumn = d(paramInt);
    if (resultColumn != null) {
      int i = resultColumn.b;
      return (i == -7 || i == 6 || i == 7 || i == -5 || i == 4 || i == 2 || i == 5 || i == 8 || i == 3 || i == -6);
    } 
    return false;
  }
  
  public void h(int paramInt) {
    this.d = Math.max(0, this.d + paramInt);
  }
  
  public void r() {
    this.d = 0;
  }
  
  public int s() {
    return this.d;
  }
  
  public final boolean t() {
    return (D() >= this.e);
  }
  
  public final void i(int paramInt) {
    this.e = paramInt;
  }
  
  public final int u() {
    return this.e;
  }
  
  public boolean v() {
    return (D() < this.e);
  }
  
  public int w() {
    return Integer.MAX_VALUE;
  }
  
  public synchronized void x() {
    this.b.clear();
    this.f.clear();
    this.g.clear();
    this.c.clear();
  }
  
  public synchronized void y() {
    this.g.clear();
    this.c.clear();
  }
  
  public void z() {
    q();
  }
  
  public int A() {
    return this.g.size();
  }
  
  public int B() {
    return this.g.size() + this.c.size();
  }
  
  public int C() {
    return this.f.size();
  }
  
  public Object[] j(int paramInt) {
    ResultRow resultRow = e(paramInt);
    Object[] arrayOfObject = null;
    if (resultRow != null) {
      arrayOfObject = new Object[this.f.size()];
      for (ResultColumn resultColumn : this.f)
        arrayOfObject[resultColumn.d] = resultRow.a(resultColumn); 
    } 
    return arrayOfObject;
  }
  
  public Object a(int paramInt1, int paramInt2) {
    ResultColumn resultColumn = d(paramInt2);
    ResultRow resultRow = e(paramInt1);
    return (resultColumn != null && resultRow != null) ? resultRow.a(resultColumn) : null;
  }
  
  public void a(Object paramObject, int paramInt, ResultColumn paramResultColumn) {
    ResultRow resultRow = e(paramInt);
    if (paramResultColumn != null && resultRow != null)
      resultRow.a(paramResultColumn, paramObject); 
  }
  
  public synchronized void a(ResultRow paramResultRow) {
    if (paramResultRow == null)
      throw new NullPointerException("Cannot add NULL record to Result."); 
    this.c.add(paramResultRow);
  }
  
  public String k(int paramInt) {
    ResultColumn resultColumn = d(paramInt);
    return (resultColumn != null) ? resultColumn.a : null;
  }
  
  public int l(int paramInt) {
    ResultColumn resultColumn = d(paramInt);
    return (resultColumn != null) ? resultColumn.b : 12;
  }
  
  public boolean m(int paramInt) {
    ResultColumn resultColumn = d(paramInt);
    return (resultColumn != null && resultColumn.c);
  }
  
  public int a(String paramString) {
    for (ResultColumn resultColumn : this.f) {
      if (paramString.equalsIgnoreCase(resultColumn.a))
        return resultColumn.d; 
    } 
    return -1;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.g.size()).append(" rows; Columns [ ");
    for (byte b = 0; b < this.f.size() && b < 15; b++) {
      if (b > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(((ResultColumn)this.f.get(b)).a);
    } 
    if (this.f.size() >= 15)
      stringBuilder.append("..."); 
    stringBuilder.append(" ]");
    return stringBuilder.toString();
  }
  
  public synchronized int D() {
    return this.c.size() + this.g.size();
  }
  
  public String E() {
    return String.format("Read %s rows", new Object[] { Integer.valueOf(D()) });
  }
  
  public void a(String paramString, ResultSet paramResultSet) {
    ResultSetMetaData resultSetMetaData = paramResultSet.getMetaData();
    if (resultSetMetaData == null)
      throw new SQLException("Driver missing feature: ResultSetMetaData is null."); 
    int i = resultSetMetaData.getColumnCount();
    if (i == 1 && resultSetMetaData.getColumnType(1) == 2000) {
      a(paramResultSet);
      while (v() && paramResultSet.next()) {
        Object object = paramResultSet.getObject(1);
        if (object instanceof Map) {
          a((Map)object);
          continue;
        } 
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("Result", object);
        a(hashMap);
      } 
    } else {
      for (byte b = 0; b < i; b++) {
        int j = resultSetMetaData.getColumnType(b + 1);
        if ("DATETIME".equalsIgnoreCase(resultSetMetaData.getColumnTypeName(b + 1)))
          j = 93; 
        ResultColumn resultColumn = a(resultSetMetaData.getColumnLabel(b + 1), j, resultSetMetaData.isAutoIncrement(b + 1));
        if (!"Hive".equals(paramString))
          try {
            resultColumn.a(resultSetMetaData.isWritable(b + 1));
            resultColumn.a(resultSetMetaData.getSchemaName(b + 1), resultSetMetaData.getTableName(b + 1));
          } catch (Throwable throwable) {
            Log.b(throwable);
          }  
      } 
      q();
      a(paramResultSet);
      while (v() && paramResultSet.next()) {
        ResultArrayRow resultArrayRow = new ResultArrayRow(i);
        for (ResultColumn resultColumn : this.f) {
          if (this.i != null)
            try {
              boolean bool = false;
              for (ResultPosition resultPosition : this.i) {
                if (D() == resultPosition.a() && this.f.indexOf(resultColumn) == (resultPosition.b()).d) {
                  if (this.j instanceof String) {
                    paramResultSet.updateString((resultPosition.b()).d + 1, (String)this.j);
                    bool = true;
                    continue;
                  } 
                  if (this.j instanceof Integer) {
                    paramResultSet.updateInt((resultPosition.b()).d + 1, ((Integer)this.j).intValue());
                    bool = true;
                    continue;
                  } 
                  if (this.j instanceof Double) {
                    paramResultSet.updateDouble((resultPosition.b()).d + 1, ((Double)this.j).doubleValue());
                    bool = true;
                    continue;
                  } 
                  if (this.j instanceof Boolean) {
                    paramResultSet.updateBoolean((resultPosition.b()).d + 1, ((Boolean)this.j).booleanValue());
                    bool = true;
                    continue;
                  } 
                  if (this.j instanceof Date) {
                    paramResultSet.updateDate((resultPosition.b()).d + 1, (Date)this.j);
                    bool = true;
                    continue;
                  } 
                  if (this.j instanceof Date) {
                    paramResultSet.updateDate((resultPosition.b()).d + 1, new Date(((Date)this.j).getTime()));
                    bool = true;
                  } 
                } 
              } 
              if (bool)
                paramResultSet.updateRow(); 
            } finally {
              this.i = null;
              this.j = null;
            }  
          try {
            Object object = a(paramString, paramResultSet, resultColumn.b, resultColumn.d);
            if (object instanceof String)
              object = b((String)object); 
            resultArrayRow.a(resultColumn, object);
          } catch (SQLException sQLException) {
            Log.f("Error by loading column " + resultColumn.a + "[javaType:" + resultColumn.b + "]");
            throw sQLException;
          } 
        } 
        a(resultArrayRow);
      } 
    } 
    try {
      this.h = null;
      SQLWarning sQLWarning = paramResultSet.getWarnings();
      if (sQLWarning != null)
        this.h = sQLWarning.getMessage(); 
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    z();
  }
  
  public void a(Map paramMap) {
    a(new ResultMapRow(this, paramMap));
  }
  
  private void a(ResultSet paramResultSet) {
    if (!this.k) {
      int i = s() * u();
      while (i > 0 && paramResultSet.next())
        i--; 
    } 
  }
  
  public Object a(String paramString, ResultSet paramResultSet, int paramInt1, int paramInt2) {
    boolean bool;
    Reader reader;
    Object object1;
    switch (paramInt1) {
      case -7:
      case 16:
        bool = paramResultSet.getBoolean(paramInt2 + 1);
        return paramResultSet.wasNull() ? null : Boolean.valueOf(bool);
      case -1:
      case 12:
        return paramResultSet.getString(paramInt2 + 1);
      case 2005:
      case 2011:
        reader = paramResultSet.getCharacterStream(paramInt2 + 1);
        try {
          return (reader != null) ? InputStreamUtil.a(reader, Sys.B.clobReadString.a()) : null;
        } catch (IOException iOException) {
          throw new SQLException(iOException);
        } 
      case -2:
        return paramResultSet.getObject(paramInt2 + 1);
      case 2009:
        if ("Oracle".equalsIgnoreCase(paramString))
          return paramResultSet.getString(paramInt2 + 1); 
        object1 = paramResultSet.getObject(paramInt2 + 1);
        if (object1 != null && "org.postgresql.jdbc.PgSQLXML".equals(object1.getClass().getName()))
          try {
            Method method = object1.getClass().getDeclaredMethod("getCharacterStream", (Class[])null);
            Object object = method.invoke(object1, (Object[])null);
            if (object instanceof Reader)
              return InputStreamUtil.a((Reader)object, 1000); 
          } catch (Throwable throwable) {} 
        return object1;
      case -4:
      case -3:
      case 2004:
        try {
          if (InputStreamUtil.a(paramResultSet.getBinaryStream(paramInt2 + 1))) {
            String str = URLConnection.guessContentTypeFromStream(paramResultSet.getBinaryStream(paramInt2 + 1));
            if (str != null && str.startsWith("image"))
              return new Image(paramResultSet.getBinaryStream(paramInt2 + 1)); 
            return StringUtil.readBinaryStringAsHex(paramResultSet.getBinaryStream(paramInt2 + 1), 1024);
          } 
          Reader reader1 = paramResultSet.getCharacterStream(paramInt2 + 1);
          try {
            if (reader1 != null) {
              String str = InputStreamUtil.a(reader1, 100);
              if (reader1 != null)
                reader1.close(); 
              return str;
            } 
            if (reader1 != null)
              reader1.close(); 
          } catch (Throwable throwable) {
            if (reader1 != null)
              try {
                reader1.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } catch (Exception exception) {
          Log.a("Error trying to introspect binary stream", exception);
          return "Right-click to view content";
        } 
        return paramResultSet.getObject(paramInt2 + 1);
      case 91:
        return paramResultSet.getDate(paramInt2 + 1);
      case 93:
        return paramResultSet.getTimestamp(paramInt2 + 1);
    } 
    if ("PostgreSQL".equals(paramString) && "money".equalsIgnoreCase(paramResultSet.getMetaData().getColumnTypeName(paramInt2 + 1)))
      return paramResultSet.getString(paramInt2 + 1); 
    Object object2 = paramResultSet.getObject(paramInt2 + 1);
    try {
      if (object2 instanceof Reader)
        return InputStreamUtil.a((Reader)object2, Sys.B.clobReadString.a()); 
      if (object2 instanceof InputStream)
        return InputStreamUtil.a((InputStream)object2, Sys.B.clobReadString.a()); 
    } catch (IOException iOException) {
      throw new SQLException(iOException);
    } 
    return object2;
  }
  
  private String b(String paramString) {
    if (paramString.length() > w())
      paramString = paramString.substring(0, w()) + "..."; 
    return paramString;
  }
  
  public boolean c(int paramInt) {
    return e(paramInt).a();
  }
  
  public String F() {
    return this.h;
  }
  
  public void c(boolean paramBoolean) {
    this.k = paramBoolean;
  }
  
  public boolean G() {
    return this.k;
  }
}
