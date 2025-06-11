package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ColumnDataTypeDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Derby extends Dbms {
  public Derby() {
    super("Derby");
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if (paramString2 != null && paramString2.toLowerCase().startsWith("autoincrement")) {
      paramString2 = null;
      paramColumn.setIdentity((Dbms.get(this.dbId)).columnIdentityOptions.c_());
    } 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    if (StringUtil.isEmptyTrim(paramString1))
      throw new IOException("Please choose a folder"); 
    File file = Paths.get(paramString1, new String[0]).toFile();
    if (file.exists()) {
      if (!file.isDirectory())
        throw new IOException("'" + paramString1 + "' is a file. Please choose an empty folder."); 
      if (file.listFiles() != null && (file.listFiles()).length > 0)
        throw new IOException("'" + paramString1 + "' is not a empty folder. Please choose an empty folder."); 
      FileUtils.b(file);
    } 
    Envoy envoy = paramConnector.startEnvoy("Derby create database");
    try {
      paramConnector.setInstance(paramString1 + ";create=true");
      envoy.p();
      if (envoy != null)
        envoy.close(); 
    } catch (Throwable throwable) {
      if (envoy != null)
        try {
          envoy.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    paramConnector.setInstance(paramString1);
    paramConnector.closeAllEnvoysAndSsh();
  }
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> 
      (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff && (paramAbstractDiff.pair.getUnit() instanceof com.wisecoders.dbs.schema.Index || paramAbstractDiff.pair.getUnit() instanceof com.wisecoders.dbs.schema.ForeignKey)) ? true : (




      
      (paramAbstractDiff instanceof ColumnDataTypeDiff && ((ColumnDataTypeDiff)paramAbstractDiff).columnsAreOfTypes("DECIMAL", "NUMERIC") && ((ColumnDataTypeDiff)paramAbstractDiff).columnsHaveSamePrecisionDecimalMandatoryIdentity()) ? true : (



      
      (paramAbstractDiff instanceof ColumnDataTypeDiff && ((ColumnDataTypeDiff)paramAbstractDiff).columnsAreOfTypes("INT", "INTEGER"))));
  }
}
