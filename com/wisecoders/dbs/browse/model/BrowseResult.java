package com.wisecoders.dbs.browse.model;

import com.wisecoders.dbs.browse.store.BrowseMongoStore;
import com.wisecoders.dbs.browse.store.BrowseSQLStore;
import com.wisecoders.dbs.browse.store.BrowseStore;
import com.wisecoders.dbs.data.model.result.ResultArrayRow;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultMapRow;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.data.model.result.ResultStatus;
import com.wisecoders.dbs.data.model.result.SqlResult;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.Map;
import javafx.util.Callback;

public class BrowseResult extends SqlResult {
  private static final Integer[] a = new Integer[] { Integer.valueOf(0) };
  
  public final BrowseTable c;
  
  private boolean b = false;
  
  private Integer[] h = a;
  
  protected ResultStatus d = ResultStatus.a;
  
  private Callback i = null;
  
  public final BrowseStore e;
  
  private boolean j;
  
  public boolean g() {
    return (A() > 0);
  }
  
  public Integer[] h() {
    return this.h;
  }
  
  public Attribute a(int paramInt) {
    return (paramInt > -1 && paramInt < this.c.c.getAttributes().size()) ? this.c.c.getAttributes().get(paramInt) : null;
  }
  
  public Object a(int paramInt, Attribute paramAttribute) {
    return a(paramInt, this.c.c.getAttributes().indexOf(paramAttribute));
  }
  
  public Object b(int paramInt) {
    if (m() < A() && paramInt > -1 && paramInt < this.c.c.getAttributes().size())
      return a(m(), paramInt); 
    return null;
  }
  
  public Object a(Attribute paramAttribute) {
    return b(this.c.c.getAttributes().indexOf(paramAttribute));
  }
  
  public void a(Integer[] paramArrayOfInteger) {
    this.h = paramArrayOfInteger;
  }
  
  public BrowseResult(BrowseTable paramBrowseTable) {
    this.j = false;
    this.c = paramBrowseTable;
    i(Dbms.get(paramBrowseTable.c.getSchema().getDbId()).getBrowseRecordsPerPage());
    this.e = paramBrowseTable.c.getSchema().is(UnitProperty.f).booleanValue() ? new BrowseMongoStore(this) : new BrowseSQLStore(this);
  }
  
  public void a(boolean paramBoolean) {
    this.j = paramBoolean;
  }
  
  public boolean i() {
    return this.j;
  }
  
  public ResultStatus j() {
    return this.d;
  }
  
  public void a(ResultStatus paramResultStatus, Cascade paramCascade) {
    if (paramCascade != Cascade.c)
      a(paramResultStatus); 
    if (paramCascade != Cascade.a)
      for (BrowseTable browseTable : this.c.d())
        browseTable.e.a(paramResultStatus, Cascade.b);  
  }
  
  private void a(ResultStatus paramResultStatus) {
    int i;
    ResultArrayRow resultArrayRow;
    if (this.i != null)
      this.i.call(paramResultStatus); 
    this.d = paramResultStatus;
    switch (BrowseResult$1.a[paramResultStatus.ordinal()]) {
      case 1:
        i = this.c.c.getAttributes().size();
        resultArrayRow = new ResultArrayRow(i);
        if (this.j)
          for (ResultColumn resultColumn : this.f) {
            Column column = (Column)AbstractUnit.getByName(this.c.c.getAttributes(), resultColumn.a);
            if (!column.isIdentity() && !column.hasAssociatedSequence())
              resultArrayRow.a(resultColumn, a(resultColumn.a())); 
          }  
        if (this.c.b != null && this.c.b.e.A() != 0)
          for (byte b = 0; b < this.c.d.getColumnCount(); b++) {
            Attribute attribute1 = this.c.d.getColumnAt(b, this.c.g);
            int j = this.c.c.getAttributes().indexOf(attribute1);
            Attribute attribute2 = this.c.d.getColumnAt(b, !this.c.g);
            resultArrayRow.a(d(j), this.c.b.e.a(attribute2));
          }  
        a(resultArrayRow);
        q();
        a(a);
        break;
      case 2:
        q();
        z();
        break;
    } 
  }
  
  public void a(Callback paramCallback) {
    this.i = paramCallback;
  }
  
  public boolean k() {
    return this.b;
  }
  
  public void b(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public String l() {
    StringBuilder stringBuilder;
    switch (BrowseResult$1.a[this.d.ordinal()]) {
      case 3:
        return "Not loaded";
      case 4:
        return "Queued for load";
      case 5:
        return "Loading";
      case 2:
        if (s() == 0 && A() == 0)
          return "No data"; 
        stringBuilder = new StringBuilder();
        if (s() <= 1 && A() < u()) {
          stringBuilder.append(b());
          stringBuilder.append(" / ").append(A());
        } else {
          stringBuilder.append(" Page ").append(s() + 1);
          stringBuilder.append(" (").append(b());
          stringBuilder.append(" / ").append(A()).append(")");
        } 
        return stringBuilder.toString();
      case 6:
        return "Timeout";
      case 7:
        return "Failed";
    } 
    return null;
  }
  
  public void a(Cascade paramCascade) {
    if (FxUtil.a()) {
      b(paramCascade);
    } else {
      try {
        FxUtil.a(() -> b(paramCascade));
      } catch (Exception exception) {
        Log.b(exception);
      } 
    } 
  }
  
  private void b(Cascade paramCascade) {
    if (paramCascade == Cascade.b || paramCascade == Cascade.a)
      y(); 
    if (paramCascade == Cascade.b || paramCascade == Cascade.c)
      for (BrowseTable browseTable : this.c.d())
        browseTable.e.b(Cascade.b);  
  }
  
  public void a(Map paramMap) {
    ResultMapRow resultMapRow = new ResultMapRow(this, paramMap);
    a(resultMapRow);
  }
  
  public boolean c(int paramInt) {
    if (k()) {
      ResultRow resultRow = e(m());
      return (resultRow instanceof ResultMapRow && ((ResultMapRow)resultRow).b(d(paramInt)));
    } 
    return super.c(paramInt);
  }
  
  public int m() {
    return (this.h.length > 0) ? this.h[0].intValue() : 0;
  }
  
  public int n() {
    return (this.h.length > 0) ? this.h[this.h.length - 1].intValue() : 0;
  }
  
  private String b() {
    return (m() == n()) ? ("" + 
      m() + 1) : ("" + 
      m() + 1 + "-" + m() + 1);
  }
}
