package com.wisecoders.dbs.editors.text;

class b {
  private final int a;
  
  private final int b;
  
  private String c;
  
  private b d;
  
  public b(int paramInt1, int paramInt2, String paramString) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramString;
  }
  
  public int a() {
    return this.a;
  }
  
  public int b() {
    return this.b;
  }
  
  public void a(String paramString) {
    if (this.c == null) {
      this.c = paramString;
    } else if (!this.c.equals(paramString)) {
      this.c = this.c + "\n" + this.c;
    } 
  }
  
  public boolean a(Position paramPosition) {
    return (paramPosition.getCharacter() > a() && paramPosition.getCharacter() <= b());
  }
  
  public String c() {
    return this.c;
  }
  
  public void a(b paramb) {
    this.d = paramb;
  }
  
  public b d() {
    return this.d;
  }
}
