package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;

public class UserDataTypeImporter {
  public static void a(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "ListUserDefinedTypes");
    ResultSet resultSet = paramImporter.d.a().getUDTs(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null, new int[] { 
          16, -7, 1111, 2000, 2002, 2001, 12, -3, 4, 91, 
          93, -5, 8, 3, 2, -7, 1, -16 });
    while (resultSet != null && resultSet.next()) {
      UserDataType userDataType = paramSchema.createUserDataType(resultSet.getString(3));
      if (resultSet.getMetaData().getColumnCount() >= 8)
        userDataType.setScript(resultSet.getString(8)); 
      userDataType.removeCreateTypeFromScript();
      userDataType.setJavaType(resultSet.getInt(5));
      Log.j();
    } 
  }
}
