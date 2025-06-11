package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Schema;
import java.sql.ResultSet;

public class Db2ZOS extends Db2 {
  public Db2ZOS() {
    super("Db2zOS");
  }
  
  public void importColumns(Importer paramImporter, Schema paramSchema) {
    try {
      SelectStatement selectStatement = paramImporter.d.a("SELECT TBNAME, NAME , COLTYPE, LENGTH, SCALE, DEFAULT, NULLS, REMARKS FROM SYSIBM.SYSCOLUMNS WHERE TBCREATOR=?", new Object[] { paramSchema

            
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next() && !paramImporter.a()) {
          AbstractTable abstractTable = (AbstractTable)paramSchema.tables.getByName(resultSet.getString(1));
          if (abstractTable != null && paramImporter.c.isSelected(abstractTable)) {
            Column column = abstractTable.createColumn(resultSet.getString(2), DbmsTypes.get(paramImporter.d.e()).getOrCreateDataType(-1, resultSet.getString(3)));
            setImportedColumnType(column, -1, resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getString(6));
            column.setMandatory("N".equalsIgnoreCase(resultSet.getString(7)));
            column.setComment(resultSet.getString(8));
          } 
        } 
        resultSet.close();
        if (selectStatement != null)
          selectStatement.close(); 
      } catch (Throwable throwable) {
        if (selectStatement != null)
          try {
            selectStatement.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } catch (Throwable throwable) {
      super.importColumns(paramImporter, paramSchema);
    } 
  }
}
