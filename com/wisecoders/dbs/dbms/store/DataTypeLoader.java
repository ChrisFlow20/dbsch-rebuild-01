package com.wisecoders.dbs.dbms.store;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DataTypeLoader extends AbstractContentHandler {
  private final DbmsTypes a;
  
  private final TxKey[] b;
  
  public DataTypeLoader(DbmsTypes paramDbmsTypes) {
    this.b = new TxKey[10];
    this.a = paramDbmsTypes;
    String str = Dbms.getInheritedDbms(paramDbmsTypes.dbId);
    try {
      File file = Sys.a().resolve(StringUtil.escapeForFileName(paramDbmsTypes.dbId) + "/types1.0.xml").toFile();
      if (file.exists()) {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          parse(fileInputStream);
          fileInputStream.close();
        } catch (Throwable throwable) {
          try {
            fileInputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } else if (!a(paramDbmsTypes.dbId) && str != null) {
        a(str);
      } 
      if (!"MongoDb".equals(paramDbmsTypes.dbId))
        paramDbmsTypes.sortAlphabetical(); 
    } catch (Exception exception) {
      Log.c(exception);
    } 
  }
  
  public DataTypeLoader(DbmsTypes paramDbmsTypes, File paramFile) {
    this.b = new TxKey[10];
    this.a = paramDbmsTypes;
    if (paramFile.exists()) {
      FileInputStream fileInputStream = new FileInputStream(paramFile);
      try {
        parse(fileInputStream);
        fileInputStream.close();
      } catch (Throwable throwable) {
        try {
          fileInputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } 
  }
  
  private boolean a(String paramString) {
    InputStream inputStream = DataTypeLoader.class.getResourceAsStream("/dbms/" + StringUtil.escapeForFileName(paramString) + "/types.xml");
    if (inputStream != null) {
      parse(inputStream);
      return true;
    } 
    return false;
  }
  
  protected void a(String paramString, int paramInt) {
    TxKey txKey = TxKey.valueOf(paramString);
    this.b[paramInt] = txKey;
    a((paramInt > 0) ? this.b[paramInt - 1] : null, txKey);
  }
  
  private void a(TxKey paramTxKey1, TxKey paramTxKey2) {
    if (paramTxKey1 != null) {
      int i;
      String str1;
      Precision precision;
      DataType dataType;
      String str2;
      String str3;
      switch (DataTypeLoader$1.a[paramTxKey1.ordinal()]) {
        case 3:
          switch (DataTypeLoader$1.a[paramTxKey2.ordinal()]) {
            case 1:
              i = getInt(TxKey.java);
              str1 = get(TxKey.precision);
              precision = (str1 != null && !str1.isEmpty()) ? Precision.valueOf(str1) : Precision.NONE;
              dataType = this.a.createType(i, get(TxKey.name), get(TxKey.aliases), precision);
              str2 = get(TxKey.defo_length);
              if (StringUtil.isFilledTrim(str2))
                dataType.setDefaultLength(Integer.parseInt(str2)); 
              str3 = get(TxKey.defo_decimal);
              if (StringUtil.isFilledTrim(str3))
                dataType.setDefaultDecimal(Integer.parseInt(str3)); 
              dataType.setGeneric(get(TxKey.generic));
              dataType.setPattern(get(TxKey.pattern));
              dataType.setOptions(get(TxKey.options));
              dataType.setOptionsTitle(get(TxKey.options_title));
              dataType.setUpdateCast(get(TxKey.update_cast));
              dataType.setRegExMatchers(get(TxKey.regexp_matchers));
              break;
          } 
          break;
        case 2:
          switch (DataTypeLoader$1.a[paramTxKey2.ordinal()]) {
          
          } 
          break;
      } 
    } 
  }
}
