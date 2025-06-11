package com.wisecoders.dbs.project.convert.store;

import com.wisecoders.dbs.project.convert.model.ConversionDictionary;
import com.wisecoders.dbs.project.convert.model.DataTypeConverter;
import com.wisecoders.dbs.project.convert.model.DataTypeConverterReplacement;
import com.wisecoders.dbs.project.convert.model.DefaultValueConverter;
import com.wisecoders.dbs.project.convert.model.DefaultValueConverterReplacement;
import com.wisecoders.dbs.project.convert.model.OptionsConverter;
import com.wisecoders.dbs.project.convert.model.OptionsConverterReplacement;
import com.wisecoders.dbs.project.store.XMLWriter;
import java.io.File;
import java.io.FileWriter;

public class ConversionDictionaryStore {
  public static void a(ConversionDictionary paramConversionDictionary, File paramFile) {
    FileWriter fileWriter = new FileWriter(paramFile);
    XMLWriter xMLWriter = new XMLWriter(fileWriter);
    xMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    xMLWriter.a(CxKey.converters);
    xMLWriter.e(CxKey.dbms, paramConversionDictionary.b);
    for (DataTypeConverter dataTypeConverter : paramConversionDictionary.c) {
      xMLWriter.a(CxKey.data_type)
        .e(CxKey.from, dataTypeConverter.f)
        .a(CxKey.generated, dataTypeConverter.c());
      if (dataTypeConverter.g() != DataTypeConverter.c)
        xMLWriter.e(CxKey.min_length, Integer.valueOf(dataTypeConverter.g())); 
      if (dataTypeConverter.h() != DataTypeConverter.e)
        xMLWriter.e(CxKey.max_length, Integer.valueOf(dataTypeConverter.h())); 
      if (dataTypeConverter.i() != DataTypeConverter.c)
        xMLWriter.e(CxKey.min_decimal, Integer.valueOf(dataTypeConverter.i())); 
      if (dataTypeConverter.j() != DataTypeConverter.e)
        xMLWriter.e(CxKey.max_decimal, Integer.valueOf(dataTypeConverter.j())); 
      xMLWriter.e(CxKey.type_options, dataTypeConverter.f());
      for (DataTypeConverterReplacement dataTypeConverterReplacement : dataTypeConverter.g) {
        if (dataTypeConverterReplacement.a()) {
          xMLWriter.a(CxKey.replacement);
          xMLWriter.e(CxKey.dbms, dataTypeConverterReplacement.a);
          xMLWriter.e(CxKey.to, dataTypeConverterReplacement.b());
          if (dataTypeConverterReplacement.c() != DataTypeConverter.d)
            xMLWriter.e(CxKey.length, Integer.valueOf(dataTypeConverterReplacement.c())); 
          if (dataTypeConverterReplacement.d() != DataTypeConverter.d)
            xMLWriter.e(CxKey.decimal, Integer.valueOf(dataTypeConverterReplacement.d())); 
          xMLWriter.e(CxKey.type_options, dataTypeConverterReplacement.e());
          xMLWriter.b(CxKey.replacement);
        } 
      } 
      xMLWriter.b(CxKey.data_type);
    } 
    for (DefaultValueConverter defaultValueConverter : paramConversionDictionary.d) {
      xMLWriter.a(CxKey.default_value)
        .e(CxKey.data_type, defaultValueConverter.d)
        .a(CxKey.reg_exp, defaultValueConverter.e())
        .e(CxKey.from, defaultValueConverter.c)
        .a(CxKey.generated, defaultValueConverter.c());
      for (DefaultValueConverterReplacement defaultValueConverterReplacement : defaultValueConverter.f) {
        xMLWriter.a(CxKey.replacement);
        xMLWriter.e(CxKey.dbms, defaultValueConverterReplacement.a);
        xMLWriter.e(CxKey.to, defaultValueConverterReplacement.c());
        xMLWriter.b(CxKey.replacement);
      } 
      xMLWriter.b(CxKey.default_value);
    } 
    for (OptionsConverter optionsConverter : paramConversionDictionary.e) {
      xMLWriter.a(CxKey.unit_options)
        .e(CxKey.from, optionsConverter.e())
        .a(CxKey.generated, optionsConverter.c());
      for (OptionsConverterReplacement optionsConverterReplacement : optionsConverter.c) {
        xMLWriter.a(CxKey.replacement);
        xMLWriter.e(CxKey.dbms, optionsConverterReplacement.a);
        xMLWriter.e(CxKey.to, optionsConverterReplacement.c());
        xMLWriter.b(CxKey.replacement);
      } 
      xMLWriter.b(CxKey.unit_options);
    } 
    xMLWriter.b(CxKey.converters);
    xMLWriter.close();
  }
}
