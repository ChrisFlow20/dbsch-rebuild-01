package com.wisecoders.dbs.project.convert.store;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.project.convert.model.ConversionDictionary;
import com.wisecoders.dbs.project.convert.model.DataTypeConverter;
import com.wisecoders.dbs.project.convert.model.DataTypeConverterReplacement;
import com.wisecoders.dbs.project.convert.model.DefaultValueConverter;
import com.wisecoders.dbs.project.convert.model.DefaultValueConverterReplacement;
import com.wisecoders.dbs.project.convert.model.OptionsConverter;
import com.wisecoders.dbs.project.convert.model.OptionsConverterReplacement;
import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.sys.Log;
import java.io.File;
import java.io.FileInputStream;

public class ConversionDictionaryLoader extends AbstractContentHandler {
  private ConversionDictionary a;
  
  private DataTypeConverter b;
  
  private DefaultValueConverter c;
  
  private OptionsConverter d;
  
  private final CxKey[] e;
  
  public ConversionDictionaryLoader(ConversionDictionary paramConversionDictionary, File paramFile) {
    this.e = new CxKey[10];
    this.a = paramConversionDictionary;
    if (paramFile.exists())
      try {
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
      } catch (Exception exception) {
        Log.c(exception);
      }  
  }
  
  protected void a(String paramString, int paramInt) {
    CxKey cxKey = CxKey.valueOf(paramString);
    this.e[paramInt] = cxKey;
    a((paramInt > 0) ? this.e[paramInt - 1] : null, cxKey);
  }
  
  private void a(CxKey paramCxKey1, CxKey paramCxKey2) {
    if (paramCxKey2 == CxKey.converters && 
      this.a == null)
      this.a = new ConversionDictionary(get(CxKey.dbms)); 
    if (paramCxKey1 != null)
      switch (ConversionDictionaryLoader$1.a[paramCxKey1.ordinal()]) {
        case 4:
          switch (ConversionDictionaryLoader$1.a[paramCxKey2.ordinal()]) {
            case 1:
              this.b = this.a.a();
              this.b.a(getBoolean(CxKey.generated));
              this.b.a(DbmsTypes.get(this.a.b).getType(get(CxKey.from)));
              if (has(CxKey.min_length))
                this.b.a(getInt(CxKey.min_length)); 
              if (has(CxKey.max_length))
                this.b.b(getInt(CxKey.max_length)); 
              if (has(CxKey.min_decimal))
                this.b.c(getInt(CxKey.min_decimal)); 
              if (has(CxKey.max_decimal))
                this.b.d(getInt(CxKey.max_decimal)); 
              this.b.d(get(CxKey.type_options));
              break;
            case 2:
              this.c = this.a.b();
              this.c.a(getBoolean(CxKey.generated));
              this.c.a(DbmsTypes.get(this.a.b).getType(get(CxKey.data_type)));
              this.c.b(get(CxKey.from));
              this.c.c(getBoolean(CxKey.reg_exp));
              break;
            case 3:
              this.d = this.a.c();
              this.d.a(getBoolean(CxKey.generated));
              this.d.b(get(CxKey.from));
              break;
          } 
          break;
        case 1:
          if (paramCxKey2 == CxKey.replacement) {
            DataTypeConverterReplacement dataTypeConverterReplacement = this.b.b(get(CxKey.dbms));
            dataTypeConverterReplacement.a(DbmsTypes.get(get(CxKey.dbms)).getType(get(CxKey.to)));
            if (has(CxKey.length))
              dataTypeConverterReplacement.a(getInt(CxKey.length)); 
            if (has(CxKey.decimal))
              dataTypeConverterReplacement.b(getInt(CxKey.decimal)); 
            dataTypeConverterReplacement.a(get(CxKey.type_options));
          } 
          break;
        case 2:
          if (paramCxKey2 == CxKey.replacement) {
            DefaultValueConverterReplacement defaultValueConverterReplacement = this.c.c(get(CxKey.dbms));
            defaultValueConverterReplacement.a(get(CxKey.to));
          } 
          break;
        case 3:
          if (paramCxKey2 == CxKey.replacement) {
            OptionsConverterReplacement optionsConverterReplacement = this.d.c(get(CxKey.dbms));
            optionsConverterReplacement.a(get(CxKey.to));
          } 
          break;
      }  
  }
}
