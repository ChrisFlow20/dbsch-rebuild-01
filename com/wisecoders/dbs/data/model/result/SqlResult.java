package com.wisecoders.dbs.data.model.result;

public class SqlResult extends Result {
  private Spooler a;
  
  private boolean b = false;
  
  private int c = 0;
  
  public synchronized void x() {
    super.x();
    this.c = 0;
    this.b = false;
  }
  
  public void a(ResultRow paramResultRow) {
    if (this.a != null) {
      if (!this.b) {
        this.a.a();
        for (ResultColumn resultColumn : this.f)
          this.a.a(resultColumn.a); 
        this.a.b();
        this.b = true;
        if (this.a.a == Spooler$Format.f) {
          this.a.a();
          for (ResultColumn resultColumn : this.f)
            this.a.b(resultColumn.a); 
          this.a.b();
        } 
      } 
      this.a.a();
      for (ResultColumn resultColumn : this.f)
        this.a.a(resultColumn.a, paramResultRow.a(resultColumn)); 
      this.a.b();
    } 
    super.a(paramResultRow);
    this.c++;
  }
  
  public String E() {
    return (this.a == null) ? super.E() : String.format("Saved %s rows to file", new Object[] { Integer.valueOf(this.c) });
  }
  
  public boolean H() {
    return (this.a != null);
  }
  
  public Spooler$Format I() {
    return (this.a != null) ? this.a.a : null;
  }
  
  public void a(Spooler paramSpooler) {
    this.a = paramSpooler;
  }
  
  public boolean v() {
    return (this.a != null || super.v());
  }
  
  public void z() {
    super.z();
    if (this.a != null) {
      this.a.c();
      this.a = null;
    } 
  }
}
