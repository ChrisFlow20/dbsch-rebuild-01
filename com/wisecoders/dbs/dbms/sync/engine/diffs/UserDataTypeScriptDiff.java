package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.UserDataType;

public class UserDataTypeScriptDiff extends AbstractDiff {
  public UserDataTypeScriptDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    return "Script";
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    UserDataType userDataType1 = (UserDataType)this.pair.getUnit(paramSyncSide.opposite()), userDataType2 = (UserDataType)this.pair.getUnit(paramSyncSide);
    userDataType2.setScript(userDataType1.getScript());
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Dbms dbms = Dbms.get(paramString);
    UserDataType userDataType1 = (UserDataType)this.pair.getUnit(paramSyncSide.opposite()), userDataType2 = (UserDataType)this.pair.getUnit(paramSyncSide);
    if (userDataType1 != null && !SyncUtil.c(userDataType1))
      alterScript.addAll(dbms.alterUserDataType.b(this.pair, userDataType1)); 
    if (userDataType2 != null && !SyncUtil.c(userDataType2))
      alterScript.addAll(dbms.alterUserDataType.a(this.pair, userDataType2)); 
    return alterScript;
  }
}
