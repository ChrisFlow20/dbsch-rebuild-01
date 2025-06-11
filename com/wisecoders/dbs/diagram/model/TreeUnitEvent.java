package com.wisecoders.dbs.diagram.model;

public class TreeUnitEvent implements StructureEvent {
  public final TreeUnit a;
  
  public final TreeUnitEvent$EventAction b;
  
  private final Object c;
  
  private final int d;
  
  public TreeUnitEvent(TreeUnit paramTreeUnit, TreeUnitEvent$EventAction paramTreeUnitEvent$EventAction) {
    this(paramTreeUnit, paramTreeUnitEvent$EventAction, -1, null);
  }
  
  public TreeUnitEvent(TreeUnit paramTreeUnit, TreeUnitEvent$EventAction paramTreeUnitEvent$EventAction, int paramInt) {
    this(paramTreeUnit, paramTreeUnitEvent$EventAction, paramInt, null);
  }
  
  public TreeUnitEvent(TreeUnit paramTreeUnit, TreeUnitEvent$EventAction paramTreeUnitEvent$EventAction, int paramInt, Object paramObject) {
    if (paramTreeUnit == null)
      throw new NullPointerException(); 
    this.a = paramTreeUnit;
    this.d = paramInt;
    this.c = paramObject;
    this.b = paramTreeUnitEvent$EventAction;
  }
  
  public String toString() {
    return String.valueOf(this.a) + " " + String.valueOf(this.a) + " " + String.valueOf(this.b) + " " + String.valueOf(this.c);
  }
}
