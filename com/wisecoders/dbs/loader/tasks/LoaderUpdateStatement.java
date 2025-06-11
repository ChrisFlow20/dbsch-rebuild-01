package com.wisecoders.dbs.loader.tasks;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.StatementParameter;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.loader.engine.AbstractLoader;
import com.wisecoders.dbs.loader.engine.LoaderConsumer;
import com.wisecoders.dbs.loader.model.LoaderColumn;
import com.wisecoders.dbs.loader.model.LoaderMeta;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoaderUpdateStatement extends UpdateStatement implements LoaderConsumer {
  private final boolean b;
  
  private final AbstractLoader g;
  
  private final LoaderMeta h;
  
  private final Map i = new LinkedHashMap<>();
  
  private PreparedStatement j;
  
  private final Envoy k;
  
  private int l;
  
  private final List m = new ArrayList();
  
  private int n = 10;
  
  private long o;
  
  public LoaderUpdateStatement(Envoy paramEnvoy, String paramString, AbstractLoader paramAbstractLoader, LoaderMeta paramLoaderMeta) {
    super(paramEnvoy);
    this.o = System.currentTimeMillis();
    this.b = "MongoDb".equals(paramString);
    this.g = paramAbstractLoader;
    this.h = paramLoaderMeta;
    this.k = paramEnvoy;
    c("Generate data");
    this.l = paramAbstractLoader.firstLineIsHeader() ? 1 : 0;
    this.c = d(paramString);
    paramAbstractLoader.addConsumer(this);
  }
  
  public void a() {
    this.k.b(true);
    this.k.a(this.c, this.e);
    q();
    try {
      this.g.parse();
    } catch (SQLException sQLException) {
      this.k.e(sQLException.getLocalizedMessage());
      throw sQLException;
    } catch (Exception exception) {
      this.k.e(exception.getLocalizedMessage());
      throw new SQLException(exception);
    } 
    e();
    if (this.j != null)
      this.j.close(); 
    this.k.i();
    i();
  }
  
  public void consumeRecord(Map<?, ?> paramMap) {
    this.m.add(new HashMap<>(paramMap));
    if (this.m.size() >= this.n || 
      System.currentTimeMillis() - this.o > Sys.B.maxMillisPerChunk.a()) {
      e();
      this.o = System.currentTimeMillis();
    } 
  }
  
  public void e() {
    if (this.j != null) {
      this.k.b(false);
      try {
        for (Map map : this.m) {
          a(map);
          this.j.addBatch();
        } 
        this.j.executeBatch();
        this.k.p();
        this.h.a(this.m.size());
        this.l += this.m.size();
        this.n = Math.min(Sys.B.maxRowsPerChunk.a(), this.n * 2);
        this.m.clear();
      } catch (Exception exception) {
        try {
          this.k.q();
        } catch (SQLException sQLException) {}
        q();
        for (Map map : this.m) {
          try {
            a(map);
            this.j.executeUpdate();
            this.k.p();
            this.h.a(1);
          } catch (Exception exception1) {
            this.h.a(this.l, exception.getLocalizedMessage(), this.i);
            q();
          } 
          this.l++;
        } 
        this.m.clear();
        this.n = 1;
      } 
    } 
  }
  
  private void q() {
    try {
      if (this.j != null)
        this.j.close(); 
      this.j = this.k.c().prepareStatement(this.c);
    } catch (Exception exception) {}
  }
  
  private void a(Map paramMap) {
    i();
    this.i.clear();
    if (this.b) {
      a(new StatementParameter(paramMap, 2000));
      for (LoaderColumn loaderColumn : this.h.a) {
        Column column = loaderColumn.a();
        if (column != null && !loaderColumn.h()) {
          Object object = paramMap.get(loaderColumn.a);
          if (StringUtil.isFilledTrim(object))
            this.i.put(loaderColumn, object); 
        } 
      } 
    } else {
      for (LoaderColumn loaderColumn : this.h.a) {
        Column column = loaderColumn.a();
        if (column != null && !loaderColumn.h()) {
          Object object = paramMap.get(loaderColumn.a);
          if (StringUtil.isFilledTrim(object)) {
            Object object1 = (object instanceof String) ? loaderColumn.a((String)object, this.h.f()) : object;
            a(new StatementParameter(object1, column.getDataType()));
            this.i.put(loaderColumn, object);
            continue;
          } 
          a(new StatementParameter(null, column.getDataType()));
        } 
      } 
    } 
    a(this.j);
  }
  
  private String d(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.b) {
      stringBuilder.append("UPDATE ").append(this.h.d.ref());
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder.append((Dbms.get(paramString)).tableInsert.c_()).append(" ")
        .append(this.h.d.ref()).append(" ( ");
      boolean bool = true;
      for (LoaderColumn loaderColumn : this.h.a) {
        Column column = loaderColumn.a();
        if (column != null && !loaderColumn.h()) {
          if (!bool) {
            stringBuilder.append(", ");
            stringBuilder1.append(", ");
          } 
          stringBuilder.append(column.ref());
          if (column.getDataType().getUpdateCast() == null) {
            stringBuilder1.append("?");
          } else {
            stringBuilder1.append(column.getDataType().getUpdateCast());
          } 
          bool = false;
        } 
      } 
      stringBuilder.append(" ) VALUES ( ").append(stringBuilder1).append(" ) ");
    } 
    return stringBuilder.toString();
  }
  
  public int p() {
    return this.l;
  }
}
