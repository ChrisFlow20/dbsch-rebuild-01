package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.UserDataType;

public class UserDataTypeExistsDiff extends AbstractExistsDiff {
  public UserDataTypeExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    UserDataType userDataType = (UserDataType)this.pair.getUnit(paramSyncSide.opposite());
    if (userDataType != null) {
      Schema schema = (Schema)this.pair.getParent(paramSyncSide);
      a(schema, userDataType);
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    UserDataType userDataType1 = (UserDataType)this.pair.getUnit(paramSyncSide.opposite()), userDataType2 = (UserDataType)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (userDataType2 != null && !SyncUtil.c(userDataType2))
      alterScript.addAll(dbms.alterUserDataType.a(this.pair, userDataType2)); 
    if (userDataType1 != null && !SyncUtil.c(userDataType1))
      alterScript.addAll(dbms.alterUserDataType.b(this.pair, userDataType1)); 
    return alterScript;
  }
  
  private void a(Schema paramSchema, UserDataType paramUserDataType) {
    UserDataType userDataType = paramSchema.createUserDataType(paramUserDataType.getName());
    userDataType.setScript(paramUserDataType.getScript());
    userDataType.setJavaType(paramUserDataType.getJavaType());
    userDataType.setPrecision(paramUserDataType.getPrecision());
    userDataType.setComment(paramUserDataType.getComment());
    userDataType.setCommentTags(paramUserDataType.getCommentTags());
    userDataType.setVirtual((paramUserDataType.isVirtual() || isMergeVirtual()));
  }
}
