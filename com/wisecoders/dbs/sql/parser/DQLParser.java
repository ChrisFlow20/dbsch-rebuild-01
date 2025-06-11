package com.wisecoders.dbs.sql.parser;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.schema.VirtualForeignKeySuggestion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class DQLParser {
  private final Project c;
  
  public final List a = new ArrayList();
  
  public final LinkedHashMap b = new LinkedHashMap<>();
  
  public DQLParser(Project paramProject) {
    this.c = paramProject;
  }
  
  public List a(String paramString) {
    MatcherStatement matcherStatement = new MatcherStatement(paramString, this.c.getDbId());
    a(paramString, matcherStatement, a.c);
    return this.a;
  }
  
  private void a(String paramString, MatcherStatement paramMatcherStatement, a parama) {
    for (MatcherCommaPhrase matcherCommaPhrase : paramMatcherStatement)
      for (byte b = 0;; b++); 
  }
  
  private void a(String paramString, AbstractTable paramAbstractTable1, Column paramColumn1, AbstractTable paramAbstractTable2, Column paramColumn2) {
    for (ForeignKey foreignKey1 : paramAbstractTable1.getRelations()) {
      if (foreignKey1.getTargetEntity() == paramAbstractTable2 && foreignKey1.getSourceAttributes().contains(paramColumn1) && foreignKey1.getTargetAttributes().contains(paramColumn2))
        return; 
    } 
    for (ForeignKey foreignKey1 : paramAbstractTable2.getRelations()) {
      if (foreignKey1.getTargetEntity() == paramAbstractTable1 && foreignKey1.getSourceAttributes().contains(paramColumn2) && foreignKey1.getTargetAttributes().contains(paramColumn1))
        return; 
    } 
    ForeignKey foreignKey = paramAbstractTable1.createRelation(paramAbstractTable1.getRelations().proposeName("fk_" + String.valueOf(paramAbstractTable1) + "_" + String.valueOf(paramAbstractTable2)));
    foreignKey.setTargetEntity(paramAbstractTable2);
    foreignKey.setDeduced(true);
    foreignKey.setVirtual(true);
    foreignKey.addColumns(paramColumn1, paramColumn2);
    this.a.add(new VirtualForeignKeySuggestion(paramString, foreignKey, true));
  }
  
  public void a(View paramView) {
    for (String str : this.b.keySet()) {
      AbstractTable abstractTable = (AbstractTable)this.b.get(str);
      ForeignKey foreignKey = paramView.createRelation(paramView.getRelations().proposeName("fk_" + paramView.getName() + "_" + abstractTable.getName()));
      foreignKey.setTargetEntity(abstractTable);
      foreignKey.setDeduced(true);
      foreignKey.setVirtual(true);
    } 
  }
  
  private static final List d = Arrays.asList(new String[] { ",", "WHERE", "GROUP", "JOIN", "LEFT", "RIGHT", "OUTER", "HAVING" });
  
  private int a(MatcherCommaPhrase paramMatcherCommaPhrase, int paramInt) {
    Table table = null;
    int i = 0;
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.a("?.?.?", paramInt)) != null) {
      Schema schema = this.c.getSchema(patternPhrase.f(0), patternPhrase.f(1));
      if (schema != null) {
        table = schema.getTable(patternPhrase.f(2));
        i = paramInt + 5;
      } 
    } else if ((patternPhrase = paramMatcherCommaPhrase.a("?.?", paramInt)) != null) {
      Schema schema = this.c.getSchemaInUseCatalog(patternPhrase.f(0));
      if (schema != null) {
        table = schema.getTable(patternPhrase.f(1));
        i = paramInt + 3;
      } 
    } else if ((patternPhrase = paramMatcherCommaPhrase.a("?", paramInt)) != null) {
      String str = patternPhrase.f(0);
      String[] arrayOfString = str.split("\\.");
      if (arrayOfString.length == 3) {
        Schema schema = this.c.getSchema(arrayOfString[0], arrayOfString[1]);
        if (schema != null)
          table = schema.getTable(arrayOfString[2]); 
      } else if (arrayOfString.length == 2) {
        Schema schema = this.c.getSchemaInUseCatalog(arrayOfString[0]);
        if (schema != null)
          table = schema.getTable(arrayOfString[1]); 
      } else {
        table = a().getTable(patternPhrase.f(0));
        if (table == null && b() != null)
          table = b().getTable(patternPhrase.f(0)); 
        if (table == null)
          table = a().getTable(patternPhrase.f(0)); 
      } 
      if (table != null)
        i = paramInt + 1; 
    } 
    if (table != null) {
      String str = table.getName();
      if (i < paramMatcherCommaPhrase.size()) {
        E e = paramMatcherCommaPhrase.get(i);
        if (e instanceof MatcherToken) {
          MatcherToken matcherToken = (MatcherToken)e;
          if (!d.contains(matcherToken.c().toUpperCase())) {
            str = matcherToken.c();
            i++;
          } 
        } 
      } 
      this.b.put(str, table);
    } 
    return i;
  }
  
  private Schema a() {
    Schema schema = this.c.getInUseSchema();
    if (schema != null)
      return schema; 
    return this.c.getOrCreateSchema("SqlServer".equals(this.c.getDbId()) ? "dbo" : "public");
  }
  
  private Schema b() {
    return this.c.getOrCreateSchema("SqlServer".equals(this.c.getDbId()) ? "dbo" : "public");
  }
}
