package com.wisecoders.dbs.dbms;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.model.explain.ExecutionPlan;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.dbms.plugins.Access;
import com.wisecoders.dbs.dbms.plugins.AmazonAthena;
import com.wisecoders.dbs.dbms.plugins.ApacheDrill;
import com.wisecoders.dbs.dbms.plugins.ApacheIgnite;
import com.wisecoders.dbs.dbms.plugins.AuroraMySql;
import com.wisecoders.dbs.dbms.plugins.AuroraPostgreSQL;
import com.wisecoders.dbs.dbms.plugins.AzurePostgreSQL;
import com.wisecoders.dbs.dbms.plugins.AzureSQL;
import com.wisecoders.dbs.dbms.plugins.AzureSynapse;
import com.wisecoders.dbs.dbms.plugins.CSVFiles;
import com.wisecoders.dbs.dbms.plugins.CTreeAce;
import com.wisecoders.dbs.dbms.plugins.Cassandra;
import com.wisecoders.dbs.dbms.plugins.ClickHouse;
import com.wisecoders.dbs.dbms.plugins.Cockroach;
import com.wisecoders.dbs.dbms.plugins.DBF;
import com.wisecoders.dbs.dbms.plugins.Databricks;
import com.wisecoders.dbs.dbms.plugins.Db2;
import com.wisecoders.dbs.dbms.plugins.Db2ZOS;
import com.wisecoders.dbs.dbms.plugins.Derby;
import com.wisecoders.dbs.dbms.plugins.DocumentDB;
import com.wisecoders.dbs.dbms.plugins.DuckDB;
import com.wisecoders.dbs.dbms.plugins.Elasticsearch;
import com.wisecoders.dbs.dbms.plugins.Exasol;
import com.wisecoders.dbs.dbms.plugins.Filemaker;
import com.wisecoders.dbs.dbms.plugins.Firebird;
import com.wisecoders.dbs.dbms.plugins.FoxPro;
import com.wisecoders.dbs.dbms.plugins.GoogleBigQuery;
import com.wisecoders.dbs.dbms.plugins.GoogleCloudSpanner;
import com.wisecoders.dbs.dbms.plugins.GoogleCloudSpannerPgDialect;
import com.wisecoders.dbs.dbms.plugins.Greenplum;
import com.wisecoders.dbs.dbms.plugins.H2;
import com.wisecoders.dbs.dbms.plugins.HBase;
import com.wisecoders.dbs.dbms.plugins.HSql;
import com.wisecoders.dbs.dbms.plugins.Hive;
import com.wisecoders.dbs.dbms.plugins.Impala;
import com.wisecoders.dbs.dbms.plugins.InfluxDb;
import com.wisecoders.dbs.dbms.plugins.Informix;
import com.wisecoders.dbs.dbms.plugins.Ingres;
import com.wisecoders.dbs.dbms.plugins.Interbase;
import com.wisecoders.dbs.dbms.plugins.JDataStore;
import com.wisecoders.dbs.dbms.plugins.LogicalDesign;
import com.wisecoders.dbs.dbms.plugins.MariaDb;
import com.wisecoders.dbs.dbms.plugins.Mimer;
import com.wisecoders.dbs.dbms.plugins.MonetDB;
import com.wisecoders.dbs.dbms.plugins.MongoDb;
import com.wisecoders.dbs.dbms.plugins.MySql;
import com.wisecoders.dbs.dbms.plugins.Netezza;
import com.wisecoders.dbs.dbms.plugins.NuoDb;
import com.wisecoders.dbs.dbms.plugins.Oracle;
import com.wisecoders.dbs.dbms.plugins.OrientDb;
import com.wisecoders.dbs.dbms.plugins.Pervasive;
import com.wisecoders.dbs.dbms.plugins.PostgreSQL;
import com.wisecoders.dbs.dbms.plugins.Redis;
import com.wisecoders.dbs.dbms.plugins.Redshift;
import com.wisecoders.dbs.dbms.plugins.SAPAdaptiveServer;
import com.wisecoders.dbs.dbms.plugins.SAPHana;
import com.wisecoders.dbs.dbms.plugins.SAPMaxDb;
import com.wisecoders.dbs.dbms.plugins.Salesforce;
import com.wisecoders.dbs.dbms.plugins.ScyllaDB;
import com.wisecoders.dbs.dbms.plugins.SingleStore;
import com.wisecoders.dbs.dbms.plugins.SnappyData;
import com.wisecoders.dbs.dbms.plugins.Snowflake;
import com.wisecoders.dbs.dbms.plugins.SqlAnywhere;
import com.wisecoders.dbs.dbms.plugins.SqlBase;
import com.wisecoders.dbs.dbms.plugins.SqlServer;
import com.wisecoders.dbs.dbms.plugins.SqlServerBefore2006;
import com.wisecoders.dbs.dbms.plugins.Sqlite;
import com.wisecoders.dbs.dbms.plugins.Starburst;
import com.wisecoders.dbs.dbms.plugins.Tarantool;
import com.wisecoders.dbs.dbms.plugins.Teradata;
import com.wisecoders.dbs.dbms.plugins.ThoughtSpot;
import com.wisecoders.dbs.dbms.plugins.Tibero;
import com.wisecoders.dbs.dbms.plugins.TimescaleDB;
import com.wisecoders.dbs.dbms.plugins.Transbase;
import com.wisecoders.dbs.dbms.plugins.Vertica;
import com.wisecoders.dbs.dbms.plugins.XBase;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ForeignKeyImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ImporterUtils;
import com.wisecoders.dbs.dbms.reverseEngineer.model.IndexImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.model.TableImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.model.UserDataTypeImporter;
import com.wisecoders.dbs.dbms.reverseEngineer.model.ViewImporter;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.DbUnit;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.JavaType;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Rule;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.generator.ScriptGenerator;
import com.wisecoders.dbs.sql.generator.SqlScriptGenerator;
import com.wisecoders.dbs.sql.indenter.ScriptComparator;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sql.parser.MatcherStatement;
import com.wisecoders.dbs.sql.parser.PatternPhrase;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class Dbms extends DbmsDef {
  public static final String GROOVY = "Groovy";
  
  private static final String a = "1.5";
  
  private static final String b = "TEMPLATES:db";
  
  public static final String ANY_DBMS = "Any";
  
  private final String c;
  
  public final ScriptComparator scriptComparator = new ScriptComparator();
  
  public Dbms(String paramString) {
    this(paramString, (String)null);
  }
  
  public Dbms(String paramString1, String paramString2) {
    super(paramString1);
    this.c = paramString2;
    this.root.b(paramObject -> {
          this.root.i();
          boolean bool = false;
          if (paramString1 != null)
            bool = this.root.q("/dbms/" + StringUtil.escapeForFileName(paramString1) + "/settings.properties"); 
          bool |= this.root.q("/dbms/" + StringUtil.escapeForFileName(paramString2) + "/settings.properties");
          if (!bool)
            this.root.q("resources/NewDbms.properties"); 
          return null;
        });
    this.root.q();
    this.root.a(Sys.a().resolve(StringUtil.escapeForFileName(paramString1) + "/settings1.5.properties"));
    this.root.h();
  }
  
  private static final Map d = new LinkedHashMap<>();
  
  static {
    a(new Access());
    a(new ApacheDrill());
    a(new ApacheIgnite());
    a(new AuroraMySql());
    a(new AuroraPostgreSQL());
    a(new AmazonAthena());
    a(new AzureSQL());
    a(new AzureSynapse());
    a(new AzurePostgreSQL());
    a(new Redshift());
    a(new Dbms("CacheDb"));
    a(new Dbms("DaffodilDb"));
    a(new Cassandra());
    a(new ScyllaDB());
    a(new ClickHouse());
    a(new CTreeAce());
    a(new Cockroach());
    a(new CSVFiles());
    a(new Databricks());
    a(new Db2());
    a(new Db2ZOS());
    a(new Derby());
    a(new DuckDB());
    a(new Elasticsearch());
    a(new Exasol());
    a(new Firebird());
    a(new Filemaker());
    a(new FoxPro());
    a(new Greenplum());
    a(new GoogleBigQuery());
    a(new GoogleCloudSpanner());
    a(new GoogleCloudSpannerPgDialect());
    a(new DBF());
    a(new HBase());
    a(new Hive());
    a(new Impala());
    a(new Informix());
    a(new Interbase());
    a(new Ingres());
    a(new InfluxDb());
    a(new JDataStore());
    a(new Mimer());
    a(new H2());
    a(new HSql());
    a(new LogicalDesign());
    a(new MariaDb());
    a(new MySql());
    a(new MongoDb());
    a(new DocumentDB());
    a(new MonetDB());
    a(new Netezza());
    a(new NuoDb());
    a(new OrientDb());
    a(new Oracle());
    a(new Pervasive());
    a(new PostgreSQL());
    a(new Redis());
    a(new Dbms("Pervasive"));
    a(new SingleStore());
    a(new Sqlite());
    a(new Salesforce());
    a(new SAPAdaptiveServer());
    a(new SAPMaxDb());
    a(new SAPHana());
    a(new SnappyData());
    a(new SqlBase());
    a(new SqlServer());
    a(new Snowflake());
    a(new SqlServerBefore2006());
    a(new SAPAdaptiveServer());
    a(new SqlAnywhere());
    a(new Starburst());
    a(new Vertica());
    a(new TimescaleDB());
    a(new Teradata());
    a(new Tarantool());
    a(new ThoughtSpot());
    a(new XBase());
    a(new Tibero());
    a(new Transbase());
    reloadImporters();
  }
  
  private static void a(Dbms paramDbms) {
    d.put(paramDbms.dbId, paramDbms);
  }
  
  public static List getKnownDbmsExclude(String... paramVarArgs) {
    ArrayList<Comparable> arrayList = new ArrayList(d.keySet());
    for (String str : paramVarArgs)
      arrayList.remove(str); 
    Collections.sort(arrayList);
    return arrayList;
  }
  
  public static Dbms get(String paramString) {
    if (StringUtil.isEmptyTrim(paramString))
      throw new NullPointerException("Have asked for Rdb with empty name."); 
    Pref.a("TEMPLATES:db", paramString);
    Dbms dbms = (Dbms)d.get(paramString);
    if (dbms == null)
      a(dbms = new Dbms(paramString)); 
    return dbms;
  }
  
  public static Dbms get(DbUnit paramDbUnit) {
    return get(paramDbUnit.getDbId());
  }
  
  public static void reloadImporters() {
    try {
      for (String str : DriverManager.c())
        get(str); 
      File file1 = Sys.a().toFile();
      if (file1.exists()) {
        File[] arrayOfFile = file1.listFiles();
        if (arrayOfFile != null)
          for (File file : arrayOfFile) {
            if (file.isDirectory())
              get(file.getName()); 
          }  
      } 
      File file2 = Sys.e.toFile();
      if (file2.exists()) {
        File[] arrayOfFile = file2.listFiles();
        if (arrayOfFile != null)
          for (File file : arrayOfFile) {
            if (file.isDirectory())
              get(file.getName()); 
          }  
      } 
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    } 
  }
  
  public void setDDLImportedColumnTypes(Table paramTable) {
    for (Column column : paramTable.columns) {
      if (!(column.getDataType() instanceof com.wisecoders.dbs.schema.UserDataType))
        setImportedColumnType(column, column.getDataType().getJavaType(), column.getDataType().getName(), column.getLength(), column.getDecimal(), column.getDefaultValue()); 
    } 
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    DataType dataType = null;
    if (paramString1 != null) {
      int i = paramString1.indexOf(".");
      Schema schema = paramColumn.table.getSchema();
      if (i > -1) {
        schema = (Schema)(paramColumn.table.getSchema()).project.schemas.getByName(paramString1.substring(0, i));
        paramString1 = paramString1.substring(i + 1);
      } 
      if (schema != null)
        dataType = (DataType)schema.userDataTypes.getByName(paramString1); 
    } 
    if ("NUMBER".equalsIgnoreCase(paramString1) && paramInt2 == 38 && paramInt3 == 0)
      paramString1 = "INTEGER"; 
    if (dataType == null)
      dataType = DbmsTypes.get(this.dbId).getOrCreateDataType(paramInt1, paramString1); 
    paramColumn.setDataType(dataType);
    if (paramInt2 != -1 && (paramInt2 != dataType.getDefaultLength() || paramInt3 != dataType.getDefaultDecimal()))
      paramColumn.setLength(paramInt2); 
    if (paramInt3 != -1 && paramInt3 != dataType.getDefaultDecimal())
      paramColumn.setDecimal(paramInt3); 
    paramString2 = formatImportedDefaultValue(dataType, paramString2);
    paramColumn.setDefaultValue(paramString2);
  }
  
  public String formatImportedDefaultValue(DataType paramDataType, String paramString) {
    return ("NULL".equalsIgnoreCase(paramString) && !this.columnUseDefaultNull.b()) ? null : paramString;
  }
  
  public final void loadSchemasAndCatalogs(Envoy paramEnvoy, Project paramProject) {
    paramEnvoy.a.mapping.clearRemoteSchemas();
    for (Dbms$SchemaCatalogEntry dbms$SchemaCatalogEntry : listSchemasAndCatalogs(paramEnvoy)) {
      Schema schema = paramProject.createSchema(dbms$SchemaCatalogEntry.a, dbms$SchemaCatalogEntry.b);
      schema.setComment(dbms$SchemaCatalogEntry.c);
      paramEnvoy.a.mapping.addRemoteSchema(dbms$SchemaCatalogEntry.toString());
    } 
  }
  
  public final void loadSchemaMapping(Envoy paramEnvoy) {
    paramEnvoy.a.mapping.clearRemoteSchemas();
    for (Dbms$SchemaCatalogEntry dbms$SchemaCatalogEntry : listSchemasAndCatalogs(paramEnvoy))
      paramEnvoy.a.mapping.addRemoteSchema(dbms$SchemaCatalogEntry.toString()); 
  }
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    ArrayList<String> arrayList1 = new ArrayList();
    ArrayList<String> arrayList2 = new ArrayList();
    ArrayList<Dbms$SchemaCatalogEntry> arrayList = new ArrayList();
    if ((get(this.dbId)).useDefaultSchema.b()) {
      arrayList1.add("Default");
    } else {
      DatabaseMetaData databaseMetaData = paramEnvoy.a();
      ResultSet resultSet1 = databaseMetaData.getSchemas();
      if (resultSet1 != null) {
        int i = 1;
        try {
          i = resultSet1.getMetaData().getColumnCount();
        } catch (Throwable throwable) {}
        if (i > 1) {
          while (resultSet1.next())
            arrayList.add(new Dbms$SchemaCatalogEntry(resultSet1.getString(1), resultSet1.getString(2))); 
          if (!arrayList.isEmpty())
            return arrayList; 
        } 
        while (resultSet1.next())
          arrayList1.add(resultSet1.getString(1)); 
        resultSet1.close();
      } 
      ResultSet resultSet2 = databaseMetaData.getCatalogs();
      if (resultSet2 != null) {
        while (resultSet2.next())
          arrayList2.add(resultSet2.getString(1)); 
        resultSet2.close();
      } 
      if ((get(this.dbId)).schemaIsCatalogInDatabaseMetaData.b()) {
        arrayList1.addAll(arrayList2);
        arrayList2.clear();
      } else if (arrayList1.isEmpty()) {
        arrayList1.add("Default");
      } 
    } 
    if (arrayList2.isEmpty()) {
      for (String str : arrayList1)
        arrayList.add(new Dbms$SchemaCatalogEntry(str, null)); 
    } else {
      for (String str : arrayList2) {
        for (String str1 : arrayList1)
          arrayList.add(new Dbms$SchemaCatalogEntry(str1, str)); 
      } 
    } 
    return arrayList;
  }
  
  public void loadDbVersion(StructureImporter paramStructureImporter, Project paramProject) {}
  
  public void learnDataTypes(Envoy paramEnvoy) {
    DatabaseMetaData databaseMetaData = paramEnvoy.a();
    ResultSet resultSet = databaseMetaData.getTypeInfo();
    while (resultSet.next()) {
      int i = resultSet.getInt(2);
      JavaType javaType = null;
      for (JavaType javaType1 : JavaType.KNOWN) {
        if (javaType1.javaType == i)
          javaType = javaType1; 
      } 
      Precision precision = Precision.DECIMAL;
      String str = resultSet.getString(6);
      if (str == null || str.length() == 0) {
        precision = Precision.NONE;
      } else if (resultSet.getString(4) != null && resultSet.getString(4).length() > 0) {
        precision = Precision.LENGTH;
      } else if (javaType != null) {
        precision = javaType.precision;
      } 
      DbmsTypes.get(this.dbId).getOrCreateDataType(resultSet.getInt(2), resultSet.getString(1), precision);
    } 
    resultSet.close();
  }
  
  public void listTableAndViewsNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    ImporterUtils.a(paramStructureImporter, paramSchema);
  }
  
  protected String a(String paramString, Schema paramSchema) {
    return a(paramString, paramSchema, (AbstractUnit)null, (AbstractUnit)null);
  }
  
  protected String a(String paramString, Schema paramSchema, AbstractUnit paramAbstractUnit) {
    return a(paramString, paramSchema, paramAbstractUnit, (AbstractUnit)null);
  }
  
  protected String a(String paramString, Schema paramSchema, AbstractUnit paramAbstractUnit1, AbstractUnit paramAbstractUnit2) {
    paramString = a(paramString, "\\$\\{schemaName\\}", (paramSchema != null) ? paramSchema.getName() : null);
    paramString = a(paramString, "\\$\\{catalogName\\}", (paramSchema != null) ? paramSchema.getCatalogName() : null);
    paramString = a(paramString, "\\$\\{name\\}", (paramAbstractUnit1 != null) ? paramAbstractUnit1.getName() : null);
    paramString = a(paramString, "\\$\\{table\\}", (paramAbstractUnit2 != null) ? paramAbstractUnit2.getName() : null);
    return paramString;
  }
  
  private String a(String paramString1, String paramString2, String paramString3) {
    return paramString1.replaceAll(paramString2, (paramString3 != null) ? Matcher.quoteReplacement(paramString3) : "");
  }
  
  public void listProcedureNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    boolean bool = false;
    if (this.loadProcedureName.p())
      try {
        SelectStatement selectStatement = paramStructureImporter.c.a(a(this.loadProcedureName.c_(), paramSchema), new Object[0]);
        try {
          ImporterUtils.a(paramSchema, selectStatement.j());
          paramStructureImporter.a();
          bool = true;
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
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
    if (!bool)
      try {
        ImporterUtils.b(paramStructureImporter, paramSchema);
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
    paramStructureImporter.a();
  }
  
  public void listFunctionNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    boolean bool = false;
    if (this.loadFunctionName.p())
      try {
        SelectStatement selectStatement = paramStructureImporter.c.a(a(this.loadFunctionName.c_(), paramSchema), new Object[0]);
        try {
          ImporterUtils.b(paramSchema, selectStatement.j());
          bool = true;
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
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
    if (!bool)
      try {
        ImporterUtils.c(paramStructureImporter, paramSchema);
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
    paramStructureImporter.a();
  }
  
  public void listTriggerNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    if (this.loadTriggerName.p()) {
      SelectStatement selectStatement = paramStructureImporter.c.a(a(this.loadTriggerName.c_(), paramSchema), new Object[0]);
      try {
        ImporterUtils.a(paramStructureImporter, paramSchema, selectStatement.j());
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
    paramStructureImporter.a();
  }
  
  public void listRuleNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    if (this.loadRuleName.p()) {
      SelectStatement selectStatement = paramStructureImporter.c.a(a(this.loadRuleName.c_(), paramSchema), new Object[0]);
      try {
        ImporterUtils.b(paramStructureImporter, paramSchema, selectStatement.j());
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
    paramStructureImporter.a();
  }
  
  public void listSequences(StructureImporter paramStructureImporter, Schema paramSchema) {
    if (this.loadSequenceName.p()) {
      SelectStatement selectStatement = paramStructureImporter.c.a(a(this.loadSequenceName.c_(), paramSchema), new Object[0]);
      try {
        ImporterUtils.c(paramStructureImporter, paramSchema, selectStatement.j());
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
    paramStructureImporter.a();
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    return false;
  }
  
  public void importColumns(Importer paramImporter, Schema paramSchema) {
    boolean bool = false;
    if (paramImporter.c.allChildrenAreSelected(paramSchema.tables))
      try {
        bool = (new TableImporter()).a(paramImporter, paramSchema);
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
    if (!bool)
      (new TableImporter()).b(paramImporter, paramSchema); 
  }
  
  public void importColumnAdditions(Column paramColumn, ResultSet paramResultSet) {}
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {}
  
  public List importFks(Importer paramImporter, Schema paramSchema) {
    return ForeignKeyImporter.b(paramImporter, paramSchema);
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    (new IndexImporter(paramImporter, paramSchema)).b();
  }
  
  public void importConstraints(Importer paramImporter, Schema paramSchema) {
    if (this.loadConstraintDefinition.p()) {
      SelectStatement selectStatement = paramImporter.d.a(a(this.loadConstraintDefinition.c_(), paramSchema), new Object[0]);
      try {
        TableImporter.a(paramImporter, paramSchema, selectStatement);
        ImporterUtils.a(paramSchema);
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
  
  public void importViews(Importer paramImporter, Schema paramSchema) {
    byte b = 0;
    for (View view : paramSchema.views) {
      if (StringUtil.isFilled(view.getScript()))
        b++; 
    } 
    if ((paramSchema.views.isEmpty() || paramSchema.views.size() != b) && 
      this.loadViewDefinition.p() && paramImporter.c.hasChildrenUnitsSelected(paramSchema.views)) {
      SelectStatement selectStatement = paramImporter.d.a(a(this.loadViewDefinition.c_(), paramSchema), new Object[0]);
      try {
        (new ViewImporter()).a(paramImporter, paramSchema, selectStatement);
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
  
  public void importSchemaComments(Importer paramImporter, Schema paramSchema) {
    if (this.loadSchemaComment.p()) {
      SelectStatement selectStatement = paramImporter.d.a(a(this.loadSchemaComment.c_(), paramSchema), new Object[0]);
      try {
        TableImporter.c(paramImporter, paramSchema, selectStatement);
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
  
  public void importViewColumns(Importer paramImporter, Schema paramSchema) {
    if (paramImporter.c.hasChildrenUnitsSelected(paramSchema.views))
      (new ViewImporter()).a(paramImporter, paramSchema); 
  }
  
  public void importMaterializedViews(Importer paramImporter, Schema paramSchema) {
    byte b = 0;
    for (MaterializedView materializedView : paramSchema.materializedViews) {
      if (StringUtil.isFilled(materializedView.getScript()))
        b++; 
    } 
    if ((paramSchema.materializedViews.isEmpty() || paramSchema.materializedViews.size() != b) && this.loadMaterializedViewsDefinition.p()) {
      SelectStatement selectStatement = paramImporter.d.a(a(this.loadMaterializedViewsDefinition.c_(), paramSchema), new Object[0]);
      try {
        (new ViewImporter()).b(paramImporter, paramSchema, selectStatement);
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
  
  public void importProcedures(Importer paramImporter, Schema paramSchema) {
    byte b = 0;
    for (Procedure procedure : paramSchema.procedures) {
      if (StringUtil.isFilled(procedure.getText()))
        b++; 
    } 
    if ((paramSchema.procedures.isEmpty() || paramSchema.procedures.size() != b) && this.loadProcedureDefinition.p())
      for (Procedure procedure : paramSchema.procedures) {
        if (paramImporter.c.isSelected(procedure)) {
          paramImporter.a("Procedure '" + procedure.ref() + "'");
          SelectStatement selectStatement = paramImporter.d.a(a(this.loadProcedureDefinition.c_(), paramSchema, procedure), new Object[0]);
          try {
            ImporterUtils.a(procedure, selectStatement.j());
            paramImporter.b();
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
  }
  
  public void importProcedureParameters(Importer paramImporter, Schema paramSchema) {
    if (this.loadProcedureDefinition.p())
      try {
        ImporterUtils.a(paramImporter, paramSchema);
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
  }
  
  public void importFunctions(Importer paramImporter, Schema paramSchema) {
    byte b = 0;
    for (Function function : paramSchema.functions) {
      if (StringUtil.isFilled(function.getText()))
        b++; 
    } 
    if ((paramSchema.functions.isEmpty() || paramSchema.functions.size() != b) && this.loadFunctionDefinition.p())
      for (Function function : paramSchema.functions) {
        if (paramImporter.c.isSelected(function)) {
          paramImporter.a("Function '" + function.ref() + "'");
          SelectStatement selectStatement = paramImporter.d.a(a(this.loadFunctionDefinition.c_(), paramSchema, function), new Object[0]);
          try {
            ImporterUtils.a(function, selectStatement.j());
            paramImporter.b();
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
  }
  
  public void importFunctionParameters(Importer paramImporter, Schema paramSchema) {
    if (this.loadFunctionDefinition.p())
      try {
        ImporterUtils.b(paramImporter, paramSchema);
      } catch (Throwable throwable) {
        Log.b(throwable);
      }  
  }
  
  public void importUserDefinedTypes(Importer paramImporter, Schema paramSchema) {
    UserDataTypeImporter.a(paramImporter, paramSchema);
  }
  
  public void importTriggers(Importer paramImporter, Schema paramSchema) {
    byte b = 0;
    for (Trigger trigger : paramSchema.triggers) {
      if (StringUtil.isFilled(trigger.getText()))
        b++; 
    } 
    if ((paramSchema.triggers.isEmpty() || paramSchema.triggers.size() != b) && this.loadTriggerDefinition.p())
      for (Trigger trigger : paramSchema.triggers) {
        if (paramImporter.c.isSelected(trigger)) {
          paramImporter.a("Trigger '" + trigger.ref() + "'");
          SelectStatement selectStatement = paramImporter.d.a(a(this.loadTriggerDefinition.c_(), paramSchema, trigger, trigger.getOwningTable()), new Object[0]);
          try {
            ImporterUtils.a(trigger, selectStatement.j());
            trigger.setText(ImporterUtils.a(paramSchema, trigger.getName(), trigger.getText(), "CREATE TRIGGER", (get(paramSchema.getDbId())).defaultTriggerName.c_()));
            paramImporter.b();
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
  }
  
  public void importRules(Importer paramImporter, Schema paramSchema) {
    byte b = 0;
    for (Rule rule : paramSchema.rules) {
      if (StringUtil.isFilled(rule.getText()))
        b++; 
    } 
    if ((paramSchema.rules.isEmpty() || paramSchema.rules.size() != b) && this.loadRuleDefinition.p())
      for (Rule rule : paramSchema.rules) {
        if (paramImporter.c.isSelected(rule)) {
          paramImporter.a("Rule '" + rule.ref() + "'");
          SelectStatement selectStatement = paramImporter.d.a(a(this.loadRuleDefinition.c_(), paramSchema, rule), new Object[0]);
          try {
            ImporterUtils.a(rule, selectStatement.j());
            rule.setText(ImporterUtils.a(paramSchema, rule.getName(), rule.getText(), "CREATE RULE", (get(paramSchema.getDbId())).defaultRuleName.c_()));
            paramImporter.b();
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
  }
  
  public void importNumberOfRows(Importer paramImporter, Schema paramSchema) {
    if (this.tableRowCount.p()) {
      paramImporter.a("Schema '" + paramSchema.ref() + "' tables row count");
      SelectStatement selectStatement = paramImporter.d.a(a(this.tableRowCount.c_(), paramSchema), new Object[0]);
      try {
        TableImporter.b(paramImporter, paramSchema, selectStatement);
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
  
  public void importFinal(Schema paramSchema) {}
  
  public String getMessageFromException(Throwable paramThrowable, Connector paramConnector) {
    return SqlUtil.getExceptionText(paramThrowable);
  }
  
  private static final Pattern e = Pattern.compile("position[\\s:]*(\\d+)");
  
  private static final Pattern f = Pattern.compile("line[\\s:]*[number]?[\\s:]*(\\d+)");
  
  private static final Pattern g = Pattern.compile("column[\\s:]*[number]?[\\s:]*(\\d+)");
  
  private static final Pattern h = Pattern.compile("near[\\s:]*'(.+)'");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, f, g, e, h);
  }
  
  protected int a(String paramString1, String paramString2, Pattern paramPattern1, Pattern paramPattern2, Pattern paramPattern3, Pattern paramPattern4) {
    byte b1 = (paramPattern1 != null) ? (a(paramString2, paramPattern1) - 1) : -1;
    byte b2 = (paramPattern2 != null) ? (a(paramString2, paramPattern2) - 1) : -1;
    byte b3 = (paramPattern3 != null) ? a(paramString2, paramPattern3) : -1;
    if (b1 > -1 && b2 > -1)
      return StringUtil.getPositionInString(paramString1, b1, b2); 
    if (b3 > -1)
      return b3; 
    if (paramPattern4 != null) {
      Matcher matcher = paramPattern4.matcher(paramString2);
      if (matcher.find()) {
        String str = matcher.group(1);
        int i = StringUtil.getPositionInString(paramString1, b1, 0);
        return (i > -1) ? paramString1.indexOf(str, i) : -1;
      } 
    } 
    return -1;
  }
  
  private int a(String paramString, Pattern paramPattern) {
    try {
      Matcher matcher = paramPattern.matcher(paramString);
      if (matcher.find())
        return Integer.parseInt(matcher.group(1)); 
    } catch (NumberFormatException numberFormatException) {
      Log.a(numberFormatException);
    } 
    return -1;
  }
  
  public boolean canExplainPlan() {
    return false;
  }
  
  public ExecutionPlan explainPlan(Envoy paramEnvoy, String paramString) {
    return null;
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return null;
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {}
  
  protected List a(Envoy paramEnvoy, SelectStatement paramSelectStatement) {
    paramEnvoy.b(false);
    ArrayList<String> arrayList = new ArrayList();
    ResultSet resultSet = paramSelectStatement.j();
    if (resultSet != null) {
      while (resultSet.next()) {
        String str = resultSet.getString(1);
        if (str != null)
          arrayList.add(str); 
      } 
      resultSet.close();
    } 
    return arrayList;
  }
  
  public boolean isPlSqlBlock(String paramString) {
    if (paramString == null)
      return false; 
    MatcherStatement matcherStatement = new MatcherStatement(paramString, this.dbId);
    return (matcherStatement.b("DECLARE *", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("BEGIN *", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("CREATE [OR REPLACE] TRIGGER", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("CREATE [OR REPLACE] PROCEDURE", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("CREATE [OR REPLACE] FUNCTION", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("CREATE [OR REPLACE] SCRIPT", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("DO $$", paramPatternPhrase -> Boolean.valueOf(true)));
  }
  
  public String extractDelimiterFromCommand(String paramString) {
    return null;
  }
  
  public String formatQueryForExecution(String paramString) {
    String str = paramString.replaceAll("\\s+$", "");
    int i = str.lastIndexOf("//");
    if (i > -1 && str.lastIndexOf('"') < i && str.lastIndexOf('\'') < i)
      str = str.substring(0, i); 
    if (!isPlSqlBlock(str) && str.endsWith(";") && (get(this.dbId)).removeSemicolonFromQueries.b())
      str = str.substring(0, str.length() - 1); 
    return str.trim();
  }
  
  public SyncFilter getSynchronizationFilter() {
    return null;
  }
  
  public ScriptGenerator getScriptGenerator(List paramList) {
    return new SqlScriptGenerator(paramList, this.dbId);
  }
  
  public ScriptGenerator getScriptGenerator(Unit paramUnit) {
    return new SqlScriptGenerator(paramUnit, this.dbId);
  }
  
  public static String getLastUsedDbms() {
    return Pref.c("TEMPLATES:db", "MySql");
  }
  
  public static String getInheritedDbms(String paramString) {
    return d.containsKey(paramString) ? ((Dbms)d.get(paramString)).c : null;
  }
  
  private static final Pattern i = Pattern.compile("([^()]+)");
  
  private static final Pattern j = Pattern.compile("([^()]+)\\s*\\(\\s*(\\d*)\\s*\\)");
  
  private static final Pattern k = Pattern.compile("([^()]+)\\s*\\(\\s*(\\d*)\\s*,\\s*(\\d*)\\s*\\)");
  
  public static void setColumnTypeFromString(Column paramColumn, String paramString) {
    if (paramColumn != null && paramString != null) {
      paramString = paramString.trim();
      Matcher matcher = k.matcher(paramString);
      if (matcher.matches()) {
        paramColumn.setDataType(DbmsTypes.get(paramColumn.getDbId()).getType(matcher.group(1)));
        paramColumn.setLength(Integer.parseInt(matcher.group(2)));
        paramColumn.setDecimal(Integer.parseInt(matcher.group(3)));
      } else {
        matcher = j.matcher(paramString);
        if (matcher.matches()) {
          paramColumn.setDataType(DbmsTypes.get(paramColumn.getDbId()).getType(matcher.group(1)));
          paramColumn.setLength(Integer.parseInt(matcher.group(2)));
        } else {
          matcher = i.matcher(paramString);
          if (matcher.matches())
            paramColumn.setDataType(DbmsTypes.get(paramColumn.getDbId()).getType(matcher.group(1))); 
        } 
      } 
    } 
  }
  
  public String getResultAsString(Importer paramImporter, String paramString) {
    Result result = new Result();
    paramImporter.d.b(paramString, result);
    result.q();
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < result.A(); b++)
      stringBuilder.append(result.a(b, 0)).append("\n"); 
    return stringBuilder.toString();
  }
  
  public void validateCreatedUnit(TreeUnit paramTreeUnit) {}
  
  public String getSelectQuery(Entity paramEntity, boolean paramBoolean) {
    if (paramBoolean) {
      StringBuilder stringBuilder = new StringBuilder();
      for (Attribute attribute : paramEntity.getAttributes()) {
        if (attribute instanceof Column) {
          Column column = (Column)attribute;
          if (!isIdentityOrGenerated(column)) {
            if (stringBuilder.length() > 0)
              stringBuilder.append(", "); 
            stringBuilder.append(quote(column));
          } 
        } 
      } 
      return "SELECT " + String.valueOf(stringBuilder) + " FROM " + paramEntity.ref();
    } 
    return "SELECT * FROM " + paramEntity.ref();
  }
  
  public boolean isIdentityOrGenerated(Column paramColumn) {
    return (paramColumn.isIdentity() || (paramColumn.getOptions() != null && paramColumn.getOptions().toLowerCase().contains("generated always")));
  }
}
