package com.wisecoders.dbs.project.convert.store;

import com.wisecoders.dbs.project.convert.model.NameConverter;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.project.store.XMLWriter;
import java.io.File;
import java.io.FileWriter;

public class NamingDictionaryStore {
  public static void a(NamingDictionary paramNamingDictionary, File paramFile) {
    FileWriter fileWriter = new FileWriter(paramFile);
    XMLWriter xMLWriter = new XMLWriter(fileWriter);
    xMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    xMLWriter.a(NxKey.dictionary);
    xMLWriter.e(NxKey.logical_separator, paramNamingDictionary.b());
    xMLWriter.e(NxKey.physical_separator, paramNamingDictionary.a());
    xMLWriter.a(NxKey.convert_cases, paramNamingDictionary.d());
    for (NameConverter nameConverter : paramNamingDictionary.d)
      xMLWriter.a(NxKey.record)
        .e(NxKey.logical_name, nameConverter.a())
        .e(NxKey.physical_name, nameConverter.b())
        .b(NxKey.record); 
    xMLWriter.b(NxKey.dictionary);
    xMLWriter.close();
  }
}
