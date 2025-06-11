package com.wisecoders.dbs.generator.store;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.generator.engine.plan.Category;
import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.generator.engine.plan.Domain;
import com.wisecoders.dbs.generator.engine.plan.PatternMatcher;
import com.wisecoders.dbs.project.store.XMLWriter;
import java.io.File;
import java.io.FileWriter;

public class PatternStore {
  public static final File a = Sys.d().resolve("patterns.xml").toFile();
  
  public void a(File paramFile) {
    FileWriter fileWriter = new FileWriter(paramFile);
    try {
      XMLWriter xMLWriter = new XMLWriter(fileWriter);
      xMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
      xMLWriter.a(StoreKey.patterns);
      for (Category category : Domain.b()) {
        xMLWriter.a(StoreKey.category).e(StoreKey.name, category);
        for (DefinedPattern definedPattern : category.a()) {
          xMLWriter.a(StoreKey.pattern)
            .e(StoreKey.name, definedPattern.b())
            .e(StoreKey.pattern, definedPattern.e())
            .e(StoreKey.maxlength, Integer.valueOf(definedPattern.d()))
            .e(StoreKey.samples, definedPattern.c());
          for (PatternMatcher patternMatcher : definedPattern.a()) {
            xMLWriter.a(StoreKey.matcher)
              .e(StoreKey.table, patternMatcher.b)
              .e(StoreKey.column, patternMatcher.c)
              .e(StoreKey.score, Integer.valueOf(patternMatcher.f))
              .e(StoreKey.dataType, patternMatcher.d)
              .e(StoreKey.dbms, patternMatcher.e);
            xMLWriter.b(StoreKey.matcher);
          } 
          xMLWriter.b(StoreKey.pattern);
        } 
        xMLWriter.b(StoreKey.category);
      } 
      xMLWriter.b(StoreKey.patterns);
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
