package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;

public class NuoDb extends Dbms {
  public NuoDb() {
    super("NuoDB");
  }
  
  public void importUserDefinedTypes(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "UserDefinedTypes");
    SelectStatement selectStatement = paramImporter.d.a("SELECT domainname, declared_type, defaultvalue, flags, remarks FROM system.domains WHERE schema=?", new Object[] { paramSchema
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1).trim();
        String str2 = resultSet.getString(2);
        String str3 = resultSet.getString(3);
        int i = resultSet.getInt(4);
        String str4 = resultSet.getString(5);
        Log.d("Import domain " + str1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE DOMAIN ${name} AS ");
        if (str2 != null) {
          str2 = str2.trim();
          stringBuilder.append(str2).append(" ");
        } 
        if (str3 != null)
          stringBuilder.append(str3).append(" "); 
        if ((i & 0x1) > 0)
          stringBuilder.append(" NOT NULL"); 
        UserDataType userDataType = paramSchema.createUserDataType(str1);
        userDataType.setScript(stringBuilder.toString());
        userDataType.setComment(str4);
        if (str2 != null) {
          int j = str2.indexOf("(");
          if (j > -1)
            str2 = str2.substring(0, j); 
          DataType dataType = DbmsTypes.get(this.dbId).getType(str2);
          if (dataType != null)
            userDataType.setJavaType(dataType.getJavaType()); 
        } 
        Log.j();
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
  }
}
