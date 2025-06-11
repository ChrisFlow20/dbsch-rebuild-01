package com.wisecoders.dbs.generator.engine.generators.automaton;

public class DatatypesAutomatonProvider implements AutomatonProvider {
  private boolean a;
  
  private boolean b;
  
  private boolean c;
  
  public DatatypesAutomatonProvider() {
    this.a = this.b = this.c = true;
  }
  
  public DatatypesAutomatonProvider(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    this.a = paramBoolean1;
    this.b = paramBoolean2;
    this.c = paramBoolean3;
  }
  
  public Automaton a(String paramString) {
    if ((this.a && Datatypes.b(paramString)) || (this.b && 
      Datatypes.c(paramString)) || (this.c && 
      Datatypes.d(paramString)))
      return Datatypes.a(paramString); 
    return null;
  }
}
