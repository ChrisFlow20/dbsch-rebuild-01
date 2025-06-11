package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncAction;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncDiffFilter;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public abstract class AbstractDiff implements SyncDiff {
  public final SyncPair pair;
  
  protected SyncAction a = SyncAction.noAction;
  
  public AbstractDiff(SyncPair paramSyncPair) {
    this.pair = paramSyncPair;
  }
  
  public String getOperationString(SyncSide paramSyncSide) {
    return "Change";
  }
  
  public OperationType getOperationType(SyncSide paramSyncSide) {
    return OperationType.c;
  }
  
  public void setAction(SyncAction paramSyncAction, boolean paramBoolean) {
    this.a = paramSyncAction;
  }
  
  public SyncAction getAction() {
    return this.a;
  }
  
  public abstract AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean);
  
  public AbstractUnit getUnit(SyncSide paramSyncSide) {
    return this.pair.getUnit(paramSyncSide);
  }
  
  public abstract void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane);
  
  public void mergeFinal(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {}
  
  public abstract String getDiffString(SyncSide paramSyncSide);
  
  public AbstractDiff getNodeDiff() {
    return this;
  }
  
  public String toString() {
    return "Change";
  }
  
  public boolean matches(SyncDiffFilter paramSyncDiffFilter) {
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.Table || this.pair.right instanceof com.wisecoders.dbs.schema.Table) && !paramSyncDiffFilter.b())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.View || this.pair.right instanceof com.wisecoders.dbs.schema.View) && !paramSyncDiffFilter.c())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.Column || this.pair.right instanceof com.wisecoders.dbs.schema.Column) && !paramSyncDiffFilter.d())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.Index || this.pair.right instanceof com.wisecoders.dbs.schema.Index) && !paramSyncDiffFilter.e())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.ForeignKey || this.pair.right instanceof com.wisecoders.dbs.schema.ForeignKey) && !paramSyncDiffFilter.f())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.Constraint || this.pair.right instanceof com.wisecoders.dbs.schema.Constraint) && !paramSyncDiffFilter.g())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.Sequence || this.pair.right instanceof com.wisecoders.dbs.schema.Sequence) && !paramSyncDiffFilter.h())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.Trigger || this.pair.right instanceof com.wisecoders.dbs.schema.Trigger) && !paramSyncDiffFilter.i())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.Procedure || this.pair.right instanceof com.wisecoders.dbs.schema.Procedure) && !paramSyncDiffFilter.j())
      return false; 
    if ((this.pair.left instanceof com.wisecoders.dbs.schema.Function || this.pair.right instanceof com.wisecoders.dbs.schema.Function) && !paramSyncDiffFilter.k())
      return false; 
    if (this instanceof CommentDiff && !paramSyncDiffFilter.l())
      return false; 
    if (paramSyncDiffFilter.n() && this.pair.left == null)
      return false; 
    if (paramSyncDiffFilter.o() && this.pair.right == null)
      return false; 
    if (paramSyncDiffFilter.m() != null)
      return ((this.pair.left != null && this.pair.left.getName().toLowerCase().contains(paramSyncDiffFilter.m().toLowerCase())) || (this.pair.right != null && this.pair.right
        .getName().toLowerCase().contains(paramSyncDiffFilter.m().toLowerCase()))); 
    return true;
  }
}
