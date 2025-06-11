package com.wisecoders.dbs.generator.engine.generators.automaton;

class c {
  State a;
  
  c b;
  
  c c;
  
  b d;
  
  c(State paramState, b paramb) {
    this.a = paramState;
    this.d = paramb;
    if (paramb.a++ == 0) {
      paramb.b = paramb.c = this;
    } else {
      paramb.c.b = this;
      this.c = paramb.c;
      paramb.c = this;
    } 
  }
  
  void a() {
    this.d.a--;
    if (this.d.b == this) {
      this.d.b = this.b;
    } else {
      this.c.b = this.b;
    } 
    if (this.d.c == this) {
      this.d.c = this.c;
    } else {
      this.b.c = this.c;
    } 
  }
}
