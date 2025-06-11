package com.wisecoders.dbs.dbms.store;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.project.store.XMLWriter;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.io.FileWriter;

public class DataTypeStore {
  public static void a(DbmsTypes paramDbmsTypes) {
    File file = Sys.a().resolve(StringUtil.escapeForFileName(paramDbmsTypes.dbId)).toFile();
    if (!file.exists())
      file.mkdirs(); 
  }
  
  public static void a(DbmsTypes paramDbmsTypes, File paramFile) {
    FileWriter fileWriter = new FileWriter(paramFile);
    try {
      XMLWriter xMLWriter = new XMLWriter(fileWriter);
      try {
        xMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        xMLWriter.a(TxKey.database);
        xMLWriter.a(TxKey.types);
        for (DataType dataType : paramDbmsTypes.getDataTypes()) {
          xMLWriter.a(TxKey.type)
            .e(TxKey.name, dataType)
            .e(TxKey.generic, dataType.getGeneric())
            .e(TxKey.aliases, dataType.getAliases())
            .e(TxKey.java, Integer.valueOf(dataType.getJavaType()))
            .e(TxKey.precision, dataType.getPrecision())
            .e(TxKey.pattern, dataType.getPattern())
            .e(TxKey.options, dataType.options.c_())
            .e(TxKey.update_cast, dataType.getUpdateCast())
            .e(TxKey.regexp_matchers, dataType.getRegExMatchers());
          if (dataType.hasDefoLegth())
            xMLWriter.e(TxKey.defo_length, Integer.valueOf(dataType.getDefaultLength())); 
          if (dataType.hasDefoDecimal())
            xMLWriter.e(TxKey.defo_decimal, Integer.valueOf(dataType.getDefaultDecimal())); 
          xMLWriter.a();
        } 
        xMLWriter.b(TxKey.types);
        xMLWriter.b(TxKey.database);
        xMLWriter.close();
      } catch (Throwable throwable) {
        try {
          xMLWriter.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
      fileWriter.close();
    } catch (Throwable throwable) {
      try {
        fileWriter.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
}
