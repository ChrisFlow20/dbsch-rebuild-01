package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Table;

class i extends j {
  private boolean d = true;
  
  private Class e = Column.class;
  
  public i(FxGridEditor paramFxGridEditor, AbstractUnit paramAbstractUnit) {
    super(paramFxGridEditor, paramAbstractUnit);
  }
  
  public void a(Class paramClass) {
    this.e = paramClass;
    a(this.d);
  }
  
  public Class a() {
    return this.e;
  }
  
  public boolean b() {
    return this.d;
  }
  
  public void a(boolean paramBoolean) {
    this.d = paramBoolean;
    d();
  }
  
  public void c() {
    b(this.e);
  }
  
  public void d() {
    this.b.clear();
    setValue(null);
    if (this.d || this.e == Table.class) {
      for (Table table : this.a.C)
        this.b.add(new j(this.a, table)); 
    } else {
      for (Table table : this.a.C) {
        if (this.e == Column.class) {
          for (Column column : table.columns)
            this.b.add(new j(this.a, column)); 
          continue;
        } 
        if (this.e == ForeignKey.class) {
          for (ForeignKey foreignKey : table.foreignKeys)
            this.b.add(new j(this.a, foreignKey)); 
          continue;
        } 
        if (this.e == Index.class)
          for (Index index : table.indexes)
            this.b.add(new j(this.a, index));  
      } 
    } 
    c();
  }
}
