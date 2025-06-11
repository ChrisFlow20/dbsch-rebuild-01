package com.wisecoders.dbs.sql.generator;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.Selection;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.query.model.items.AliasSupport;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SqlScriptGenerator implements ScriptGenerator {
  private static final int a = 120;
  
  private static final Pattern b = Pattern.compile("^[A-Za-z]+$");
  
  private final List c = new ArrayList();
  
  private final String d;
  
  private final LinkedHashMap e = new LinkedHashMap<>();
  
  public SqlScriptGenerator(List paramList, String paramString) {
    this.c.addAll(paramList);
    this.d = paramString;
  }
  
  public SqlScriptGenerator(Unit paramUnit, String paramString) {
    this.c.add(paramUnit);
    this.d = paramString;
  }
  
  private String a() {
    this.e.clear();
    Selection selection = new Selection(this.c);
    selection.a();
    StringBuilder stringBuilder = new StringBuilder();
    for (Entity entity : selection.b) {
      if (!this.e.containsKey(entity))
        a((AbstractTable)entity); 
    } 
    Schema schema = null;
    for (AbstractTable abstractTable : this.e.keySet()) {
      if (schema == null)
        schema = abstractTable.getSchema(); 
    } 
    stringBuilder.append("SELECT\n\t");
    boolean bool1 = true;
    int i = 0;
    for (Entity entity : selection.b) {
      List list = (selection.e.isEmpty() && entity.getAttributes() != null) ? entity.getAttributes() : selection.e;
      for (Attribute attribute : list) {
        if (list.contains(attribute)) {
          if (!bool1)
            stringBuilder.append(", "); 
          if (stringBuilder.length() - i > 120) {
            stringBuilder.append("\n\t");
            i = stringBuilder.length();
          } 
          if (this.e.keySet().size() > 1) {
            stringBuilder.append((String)this.e.get(attribute.getEntity()));
            stringBuilder.append('.');
          } 
          stringBuilder.append(attribute.ref());
          bool1 = false;
        } 
      } 
    } 
    if (bool1)
      stringBuilder.append("* "); 
    stringBuilder.append("\nFROM\n\t");
    boolean bool2 = true;
    HashMap<Object, Object> hashMap = new HashMap<>();
    Dbms dbms = Dbms.get(this.d);
    for (AbstractTable abstractTable : this.e.keySet()) {
      Relation relation = null;
      int j = 0;
      for (Relation relation1 : selection.d) {
        if (relation1.getEntity() == abstractTable && hashMap.containsKey(relation1.getTargetEntity())) {
          relation = relation1;
          j = ((Integer)hashMap.get(relation1.getTargetEntity())).intValue();
        } 
        if (hashMap.containsKey(relation1.getEntity()) && relation1.getTargetEntity() == abstractTable) {
          relation = relation1;
          j = ((Integer)hashMap.get(relation1.getEntity())).intValue();
        } 
      } 
      if (relation == null) {
        if (!bool2)
          stringBuilder.append(",\n\t"); 
        stringBuilder.append(abstractTable.ref());
        if (dbms.getAliasSupport() == AliasSupport.a)
          stringBuilder.append(' ').append((String)this.e.get(abstractTable)); 
        bool2 = false;
      } else {
        boolean bool = true;
        stringBuilder.append("\n");
        for (byte b = 0; b < j; b++)
          stringBuilder.append("\t"); 
        stringBuilder.append("INNER JOIN ");
        stringBuilder.append(abstractTable.ref());
        if (dbms.getAliasSupport() == AliasSupport.a)
          stringBuilder.append(' ').append((String)this.e.get(abstractTable)); 
        stringBuilder.append(" ON ( ");
        Iterator<Attribute> iterator1 = relation.getSourceAttributes().iterator();
        Iterator<Attribute> iterator2 = relation.getTargetAttributes().iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
          if (!bool)
            stringBuilder.append(" AND "); 
          stringBuilder.append((String)this.e.get(relation.getEntity())).append('.').append(((Attribute)iterator1.next()).ref()).append(" = ");
          stringBuilder.append((String)this.e.get(relation.getTargetEntity())).append('.').append(((Attribute)iterator2.next()).ref());
          bool = false;
        } 
        stringBuilder.append(" ) ");
      } 
      hashMap.put(abstractTable, Integer.valueOf(j + 1));
    } 
    stringBuilder.append(dbms.statementDelimiter.c_());
    return stringBuilder.toString();
  }
  
  public String a(StatementType paramStatementType) {
    StringBuilder stringBuilder = new StringBuilder();
    AbstractTable abstractTable = null;
    for (Unit unit : this.c) {
      if (unit instanceof AbstractTable) {
        abstractTable = (AbstractTable)unit;
        continue;
      } 
      if (unit.getEntity() instanceof AbstractTable)
        abstractTable = (AbstractTable)unit.getEntity(); 
    } 
    if (abstractTable != null) {
      int i;
      byte b;
      Dbms dbms = Dbms.get(this.d);
      switch (SqlScriptGenerator$1.a[paramStatementType.ordinal()]) {
        case 1:
          stringBuilder.append("MERGE INTO ").append(abstractTable.ref()).append(" d USING (\nSELECT \n");
          i = 1;
          for (Column column : abstractTable.getAttributes()) {
            if (!i)
              stringBuilder.append(",\n"); 
            stringBuilder.append("<value> as ").append(column.ref());
            i = 0;
          } 
          stringBuilder.append("\nFROM DUAL) s\nON\n(/* insert your merge source here */)\nWHEN MATCHED\nTHEN\nUPDATE SET\n");
          i = 1;
          for (Column column : abstractTable.getAttributes()) {
            if (!i)
              stringBuilder.append(",\n"); 
            stringBuilder.append("d.").append(column.ref()).append(" = s.").append(column.ref());
            i = 0;
          } 
          stringBuilder.append("\nWHEN NOT MATCHED\nTHEN\nINSERT ( ");
          i = 1;
          for (Column column : abstractTable.getAttributes()) {
            if (!i)
              stringBuilder.append(", "); 
            stringBuilder.append("d.").append(column.ref());
            i = 0;
          } 
          stringBuilder.append(" )\nVALUES ( ");
          i = 1;
          for (Column column : abstractTable.getAttributes()) {
            if (!i)
              stringBuilder.append(", "); 
            stringBuilder.append("s.").append(column.ref());
            i = 0;
          } 
          stringBuilder.append(" )").append(dbms.statementDelimiter.c_());
          return stringBuilder.toString();
        case 2:
          stringBuilder.append("UPDATE ").append(abstractTable.ref()).append(" SET ");
          i = 1;
          for (Unit unit : this.c) {
            if (unit instanceof Column) {
              Column column = (Column)unit;
              if (column.getEntity() == abstractTable) {
                if (!i)
                  stringBuilder.append(", "); 
                stringBuilder.append(column.ref()).append(" = ? ");
                i = 0;
              } 
            } 
          } 
          if (i)
            stringBuilder.append("... = ... "); 
          stringBuilder.append(dbms.statementDelimiter.c_());
          return stringBuilder.toString();
        case 3:
          stringBuilder.append(dbms.tableInsert.c_()).append(" ").append(abstractTable.ref()).append("\n\t( ");
          i = 0;
          for (b = 0; b < abstractTable.getAttributes().size(); b++) {
            Column column = (Column)abstractTable.getAttributes().get(b);
            if (b > 0)
              stringBuilder.append(", "); 
            stringBuilder.append(column.ref());
            i += column.ref().length();
            if (i > 150) {
              stringBuilder.append("\n");
              i = 0;
            } 
          } 
          stringBuilder.append(") VALUES ( ");
          for (b = 0; b < abstractTable.getAttributes().size(); b++) {
            if (b > 0)
              stringBuilder.append(", "); 
            stringBuilder.append("?");
          } 
          stringBuilder.append(" )").append(dbms.statementDelimiter.c_());
          return stringBuilder.toString();
        case 4:
          stringBuilder.append("DELETE FROM ").append(abstractTable.ref()).append(dbms.statementDelimiter.c_());
          return stringBuilder.toString();
      } 
      return a();
    } 
    return stringBuilder.toString();
  }
  
  private void a(AbstractTable paramAbstractTable) {
    if (paramAbstractTable == null || this.e.containsKey(paramAbstractTable))
      return; 
    if (Dbms.get(this.d).getAliasSupport() == AliasSupport.a)
      for (byte b = 1; b < 5; b++) {
        for (int i = paramAbstractTable.getName().length() - b; i > 0; i--) {
          String str = paramAbstractTable.getName().substring(i, i + b);
          if (b.matcher(str).matches() && !this.e.containsValue(str)) {
            this.e.put(paramAbstractTable, str);
            return;
          } 
        } 
      }  
    this.e.put(paramAbstractTable, paramAbstractTable.getName());
  }
}
