package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;

public class Importer {
  private static final String a = "Error occurred during schema reverse engineering. Reasons can be missing rights to select from system tables or query incompatibility ( view Model /Settings /Database /Reverse engineering ). Inform DbSchema team using Help / Technical Support.";
  
  private final Project e;
  
  public final String b;
  
  public final TreeSelection c;
  
  public final Envoy d;
  
  private boolean f = false;
  
  private int g;
  
  private int h;
  
  public Importer(Project paramProject, TreeSelection paramTreeSelection, Envoy paramEnvoy) {
    this.b = paramEnvoy.e();
    this.e = paramProject;
    this.c = paramTreeSelection;
    this.d = paramEnvoy;
    paramEnvoy.b(false);
    g();
  }
  
  private void f() {
    LetterCase letterCase = (LetterCase)(Dbms.get(this.b)).identifierCases.c();
    Dbms dbms = Dbms.get(this.b);
    for (Schema schema : this.e.schemas) {
      for (Table table : schema.tables) {
        if (a(letterCase, table.getName())) {
          dbms.autoLetterCases.a(Boolean.valueOf(false));
          dbms.root.j();
          return;
        } 
        for (Column column : table.columns) {
          if (a(letterCase, column.getName())) {
            dbms.autoLetterCases.a(Boolean.valueOf(false));
            dbms.root.j();
            return;
          } 
        } 
      } 
    } 
  }
  
  private boolean a(LetterCase paramLetterCase, String paramString) {
    if (StringUtil.isFilledTrim(paramString))
      switch (Importer$1.a[paramLetterCase.ordinal()]) {
        case 1:
          return !paramString.equals(paramString.toLowerCase());
        case 2:
          return !paramString.equals(paramString.toUpperCase());
      }  
    return false;
  }
  
  public boolean a() {
    return false;
  }
  
  public void b() {
    this.g = Math.min(++this.g, this.h);
  }
  
  public void a(String paramString) {}
  
  private void a(Throwable paramThrowable) {
    this.f = true;
    Log.a("Error occurred during schema reverse engineering. Reasons can be missing rights to select from system tables or query incompatibility ( view Model /Settings /Database /Reverse engineering ). Inform DbSchema team using Help / Technical Support.", paramThrowable);
  }
  
  private void g() {
    if (a())
      return; 
    this.d.b(false);
    h();
    Dbms dbms = Dbms.get(this.b);
    long l = System.currentTimeMillis();
    DDLParser dDLParser = new DDLParser(this.e, Log.b);
    ArrayList arrayList = new ArrayList();
    ArrayList<Schema> arrayList1 = new ArrayList();
    for (Schema schema : this.e.schemas) {
      if (this.c.hasSelectedChildren(schema)) {
        a("Schema '" + schema.ref() + "' UDT");
        if (a())
          return; 
        try {
          dbms.importUserDefinedTypes(this, schema);
        } catch (Exception exception) {
          a(exception);
        } 
      } 
    } 
    for (Schema schema : this.e.schemas) {
      if (this.c.hasSelectedChildren(schema)) {
        if (dbms.reverseEngineerUsingDDL.b())
          try {
            this.e.setInUseSchema(schema);
            if (dbms.reverseEngineerTablesUsingDDL(this, dDLParser, schema)) {
              for (Table table : schema.tables) {
                dbms.setDDLImportedColumnTypes(table);
                table.removeDuplicateIndexes();
              } 
              arrayList1.add(schema);
            } 
          } catch (Throwable throwable) {
            a(throwable);
          }  
        if (!arrayList1.contains(schema)) {
          a("Schema '" + schema.ref() + "' columns");
          dbms.importColumns(this, schema);
          a("Schema '" + schema.ref() + "' indexes");
          if (a())
            return; 
          try {
            dbms.importIndexes(this, schema);
          } catch (Throwable throwable) {
            a(throwable);
          } 
          a("Schema '" + schema.ref() + "' constraints");
          if (a())
            return; 
          try {
            dbms.importConstraints(this, schema);
          } catch (Throwable throwable) {
            a(throwable);
          } 
        } 
      } 
    } 
    dDLParser.generateForeignKeys();
    for (Schema schema : this.e.schemas) {
      schema.refresh();
      if (Sys.B.orderColumns.b())
        for (Table table : schema.tables)
          table.columns.orderAlphabetically();  
    } 
    for (Schema schema : this.e.schemas) {
      if (this.c.hasSelectedChildren(schema)) {
        if (!arrayList1.contains(schema)) {
          a("Schema '" + schema.ref() + "' " + (Dbms.get(schema.getDbId())).fkAlias.c_());
          if (a())
            return; 
          try {
            arrayList.addAll(dbms.importFks(this, schema));
          } catch (Throwable throwable) {
            a(throwable);
          } 
        } 
        a("Schema '" + schema.ref() + "' comments");
        if (a())
          return; 
        try {
          dbms.importSchemaComments(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' view columns");
        if (a())
          return; 
        try {
          dbms.importViewColumns(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' view scripts");
        if (a())
          return; 
        try {
          dbms.importViews(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' materialized views");
        if (a())
          return; 
        try {
          dbms.importMaterializedViews(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' triggers");
        if (a())
          return; 
        try {
          dbms.importTriggers(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' rules");
        if (a())
          return; 
        try {
          dbms.importRules(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' functions");
        if (a())
          return; 
        try {
          dbms.importFunctions(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' function parameters");
        if (a())
          return; 
        try {
          dbms.importFunctionParameters(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' procedures");
        if (a())
          return; 
        try {
          dbms.importProcedures(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' procedure parameters");
        if (a())
          return; 
        try {
          dbms.importProcedureParameters(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' additions");
        if (a())
          return; 
        try {
          dbms.importSchemaAdditions(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' table rows count");
        if (a())
          return; 
        try {
          dbms.importNumberOfRows(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
        a("Schema '" + schema.ref() + "' groovy code");
        if (a())
          return; 
        try {
          new GroovyImporter(this, schema);
        } catch (Throwable throwable) {
          a(throwable);
        } 
      } 
    } 
    ForeignKeyImporter.a(arrayList);
    for (Schema schema : this.e.schemas) {
      if (this.c.hasSelectedChildren(schema))
        dbms.importFinal(schema); 
      schema.refresh();
    } 
    a("Preparing layouts");
    Log.g("Import Statistics -- " + Log.a(System.currentTimeMillis() - l, true) + "\n");
    this.e.refresh();
  }
  
  private void h() {
    this.h = 0;
    for (Schema schema : this.e.schemas) {
      if (this.c.hasSelectedChildren(schema))
        this.h += this.c
          
          .getSelectedChildrenCount(schema.tables) + this.c
          
          .getSelectedChildrenCount(schema.tables) + this.c
          
          .getSelectedChildrenCount(schema.tables) + this.c
          .getSelectedChildrenCount(schema.views) + this.c
          .getSelectedChildrenCount(schema.triggers) + this.c
          .getSelectedChildrenCount(schema.procedures) + this.c
          .getSelectedChildrenCount(schema.functions); 
    } 
    this.h = Math.max(this.h, 1);
  }
  
  public int c() {
    return this.g;
  }
  
  public int d() {
    return this.h;
  }
  
  public boolean e() {
    return this.f;
  }
}
