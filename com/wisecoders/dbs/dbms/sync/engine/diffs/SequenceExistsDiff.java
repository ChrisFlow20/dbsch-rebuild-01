package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class SequenceExistsDiff extends AbstractExistsDiff {
  public SequenceExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Sequence sequence = (Sequence)this.pair.getUnit(paramSyncSide.opposite());
    if (sequence != null) {
      Schema schema = (Schema)this.pair.getParent(paramSyncSide);
      Sequence sequence1 = schema.createSequence(sequence.getName());
      sequence1.setOptions(sequence.getOptions());
      sequence1.setVirtual((sequence.isVirtual() || isMergeVirtual()));
      sequence1.setComment(sequence.getComment());
      sequence1.setCommentTags(sequence.getCommentTags());
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Sequence sequence1 = (Sequence)this.pair.getUnit(paramSyncSide.opposite()), sequence2 = (Sequence)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (sequence2 != null && !SyncUtil.c(sequence2))
      alterScript.addAll(dbms.alterSequence.a(this.pair, sequence2)); 
    if (sequence1 != null)
      alterScript.addAll(dbms.alterSequence.b(this.pair, sequence1)); 
    return alterScript;
  }
}
