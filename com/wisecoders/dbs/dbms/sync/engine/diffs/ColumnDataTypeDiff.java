package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;

@DoNotObfuscate
public class ColumnDataTypeDiff extends AbstractDiff {
  public ColumnDataTypeDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    Column column = (Column)this.pair.getUnit(paramSyncSide);
    return (column != null) ? (Dbms.get(column.getEntity().getSchema())).alterColumn.a(column, false).toString() : "";
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Column column1 = (Column)this.pair.getUnit(paramSyncSide), column2 = (Column)this.pair.getUnit(paramSyncSide.opposite());
    column1.setDataType(column2.getDataType());
    column1.setLength(column2.getLength());
    column1.setDecimal(column2.getDecimal());
    column1.setEnumeration(column2.getEnumeration());
    column1.setMandatory(column2.isMandatory());
    column1.setIdentity(column2.getIdentity());
    column1.setUnsigned(column2.isUnsigned());
    column1.setTypeOptions(column2.getTypeOptions());
    column1.setOptions(column2.getOptions());
    column1.setDefaultValue(((Column)this.pair.right).getDefaultValue());
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Column column1 = getColumn(paramSyncSide.opposite()), column2 = getColumn(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (!SyncUtil.c(column2)) {
      if (!column1.isUsingSameDataType(column2) || column1.isUnsigned() != column2.isUnsigned())
        alterScript.addAll(dbms.alterColumn.c(this.pair, column1, column2)); 
      if (column1.isMandatory() != column2.isMandatory())
        if (column2.isMandatory()) {
          alterScript.addAll(dbms.alterColumn.d(this.pair, column1, column2));
        } else {
          alterScript.addAll(dbms.alterColumn.e(this.pair, column1, column2));
        }  
      if (!StringUtil.areEqualIgnoreCase(column1.getIdentity(), column2.getIdentity()))
        if (column2.isIdentity()) {
          alterScript.addAll(dbms.alterColumn.f(this.pair, column1, column2));
        } else {
          alterScript.addAll(dbms.alterColumn.g(this.pair, column1, column2));
        }  
    } 
    alterScript.setSortOrder(500);
    return alterScript;
  }
  
  public Column getColumn(SyncSide paramSyncSide) {
    return (Column)this.pair.getUnit(paramSyncSide);
  }
  
  public boolean columnsAreOfTypes(String paramString1, String paramString2) {
    String str1 = getColumn(SyncSide.left).getDataType().toString();
    String str2 = getColumn(SyncSide.right).getDataType().toString();
    return ((paramString1.equalsIgnoreCase(str1) && paramString2.equalsIgnoreCase(str2)) || (paramString1
      .equalsIgnoreCase(str2) && paramString2.equalsIgnoreCase(str1)));
  }
  
  public boolean columnsHaveSamePrecisionDecimalMandatoryIdentity() {
    Column column1 = getColumn(SyncSide.left);
    Column column2 = getColumn(SyncSide.right);
    return (column1
      .getLength() == column2.getLength() && column1
      .getDecimal() == column2.getDecimal() && column1
      .isMandatory() == column2.isMandatory() && column1
      .isIdentity() == column2.isIdentity() && column1
      .isUnsigned() == column2.isUnsigned());
  }
}
