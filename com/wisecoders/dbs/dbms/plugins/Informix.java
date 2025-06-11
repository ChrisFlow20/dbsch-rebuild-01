package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.IndexImporter;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Informix extends Dbms {
  public Informix() {
    super("Informix");
  }
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    DatabaseMetaData databaseMetaData = paramEnvoy.a();
    ResultSet resultSet = databaseMetaData.getSchemas();
    ArrayList<Dbms$SchemaCatalogEntry> arrayList = new ArrayList();
    if (resultSet != null) {
      while (resultSet.next())
        arrayList.add(new Dbms$SchemaCatalogEntry(resultSet.getString(1), null)); 
      resultSet.close();
    } 
    return arrayList;
  }
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    int i = -1;
    Class<?> clazz = paramSQLException.getClass().getClassLoader().loadClass("com.informix.jdbc.IfmxConnection");
    if (clazz != null) {
      Method method = clazz.getDeclaredMethod("getSQLStatementOffset", (Class[])null);
      if (method != null) {
        Object object = method.invoke(paramEnvoy.c(), (Object[])null);
        if (object instanceof Integer)
          i = ((Integer)object).intValue() - 1; 
      } 
    } 
    return i;
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    try {
      (new IndexImporter(paramImporter, paramSchema)).a();
    } catch (Exception exception) {
      super.importIndexes(paramImporter, paramSchema);
    } 
    SelectStatement selectStatement = paramImporter.d.a("select\n  tab.tabname, i.idxname, constr.constrname, \n  c1.colname col1, \n  c2.colname col2, \n  c3.colname col3, \n  c4.colname col4, \n  c5.colname col5  \nfrom sysconstraints constr\n  join systables tab on tab.tabid = constr.tabid \n  left outer join syschecks chk on chk.constrid = constr.constrid and chk.type = 'T'\n  left outer join sysindexes i on i.idxname = constr.idxname\n  left outer join syscolumns c1 on c1.tabid = tab.tabid and c1.colno = abs(i.part1)\n  left outer join syscolumns c2 on c2.tabid = tab.tabid and c2.colno = abs(i.part2)\n  left outer join syscolumns c3 on c3.tabid = tab.tabid and c3.colno = abs(i.part3)\n  left outer join syscolumns c4 on c4.tabid = tab.tabid and c4.colno = abs(i.part4)\n  left outer join syscolumns c5 on c5.tabid = tab.tabid and c5.colno = abs(i.part5)\nwhere constrtype='U'", new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1).trim();
        String str2 = resultSet.getString(2).trim();
        String str3 = resultSet.getString(3).trim();
        Table table = paramSchema.getTable(str1);
        if (table != null) {
          Index index = (Index)table.indexes.getByName(str2);
          if (index != null && index.getType() == IndexType.UNIQUE_INDEX) {
            index.setType(IndexType.UNIQUE_KEY);
            index.rename(str3);
          } 
        } 
      } 
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
  }
}
