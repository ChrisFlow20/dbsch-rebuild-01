package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class SequenceOptionsDiff extends AbstractDiff {
  public SequenceOptionsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    Sequence sequence = (Sequence)this.pair.getUnit(paramSyncSide);
    return sequence.getOptions();
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Sequence sequence1 = (Sequence)this.pair.getUnit(paramSyncSide.opposite()), sequence2 = (Sequence)this.pair.getUnit(paramSyncSide);
    sequence2.setOptions(sequence1.getOptions());
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Sequence sequence1 = (Sequence)this.pair.getUnit(paramSyncSide.opposite()), sequence2 = (Sequence)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (sequence2 != null)
      alterScript.addAll(dbms.alterSequence.a(this.pair, sequence2)); 
    if (sequence1 != null)
      alterScript.addAll(dbms.alterSequence.b(this.pair, sequence1)); 
    return alterScript;
  }
}
