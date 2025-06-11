package com.wisecoders.dbs.sql.parser;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Table$TableType;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.schema.store.ForeignKeyCollector;
import com.wisecoders.dbs.schema.store.ForeignKeyCollector$InternalFk;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.DDLScriptSplitter;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.PrintStream;
import java.util.regex.Pattern;

@DoNotObfuscate
public class DDLParser {
  private static final String b = "Inform technicians using Help / Technical Support";
  
  private final Project c;
  
  private final Dbms d;
  
  private final DbmsTypes e;
  
  private final PrintStream f;
  
  private final Dbms g;
  
  private final ForeignKeyCollector h;
  
  Index a;
  
  public DDLParser(Project paramProject, PrintStream paramPrintStream) {
    this.h = new ForeignKeyCollector();
    this.c = paramProject;
    this.d = Dbms.get(paramProject.getDbId());
    this.e = DbmsTypes.get(paramProject.getDbId());
    this.f = paramPrintStream;
    this.g = Dbms.get(paramProject.getDbId());
  }
  
  @GroovyMethod
  public void generateForeignKeys() {
    this.h.a();
  }
  
  public void splitAndParse(String paramString) {
    for (String str : (new DDLScriptSplitter(this.c.getDbId())).a(paramString))
      parse(str); 
  }
  
  @Action
  public void parse(String paramString) {
    if (Sys.B.DDLParserLogAllStatements.b())
      this.f.println("\n" + StringUtil.cutOfWithDots(paramString, 2000) + "\n"); 
    MatcherStatement matcherStatement = new MatcherStatement(paramString, this.c.getDbId());
    matcherStatement.l();
    if (matcherStatement
      .b("CREATE [OR REPLACE] FUNCTION * AS *", paramPatternPhrase -> parseFunction(paramMatcherStatement, paramString, paramPatternPhrase.d(1))) || matcherStatement
      .b("CREATE [OR REPLACE] AGGREGATE ? *", paramPatternPhrase -> parseFunction(paramMatcherStatement, paramString, paramPatternPhrase.d(1))) || matcherStatement
      .b("CREATE [OR REPLACE] PROCEDURE * AS *", paramPatternPhrase -> parseProcedure(paramMatcherStatement, paramString, paramPatternPhrase.d(1))) || matcherStatement
      .b("CREATE [OR REPLACE] TRIGGER * ON * ", paramPatternPhrase -> parseTrigger(paramMatcherStatement, paramString, paramPatternPhrase.d(1))) || matcherStatement
      
      .b("CREATE * TABLE * CLUSTER BY ~ ~ *", paramPatternPhrase -> {
          Table table = a(paramPatternPhrase.d(1), paramPatternPhrase.e(3), paramPatternPhrase.d(0), paramPatternPhrase.d(4));
          if (table != null)
            a(table, this.g.toDefaultCases("Cluster"), IndexType.CLUSTER, paramPatternPhrase.e(2), (String)null, (String)null); 
          return table;
        }) || matcherStatement
      .b("CREATE * TABLE * PARTITION OF *", paramPatternPhrase -> a(paramPatternPhrase.d(1), paramPatternPhrase.d(0), paramPatternPhrase.d(2))) || matcherStatement
      .b("CREATE * TABLE [IF NOT EXISTS] * ~ *", paramPatternPhrase -> a(paramPatternPhrase.d(2), paramPatternPhrase.e(3), paramPatternPhrase.d(0), paramPatternPhrase.d(4))) || matcherStatement
      .b("ALTER TABLE * RENAME TO ? ", paramPatternPhrase -> {
          Table table = c(paramPatternPhrase.d(0));
          if (table != null)
            table.rename(paramPatternPhrase.f(1)); 
          return Boolean.valueOf((table != null));
        }) || matcherStatement
      .b("DROP TABLE [IF EXISTS] * ", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      
      .b("CREATE DATABASE *", paramPatternPhrase -> a(paramPatternPhrase.d(0))) || matcherStatement
      .b("CREATE [OR REPLACE] SCHEMA *", paramPatternPhrase -> a(paramPatternPhrase.d(1))) || matcherStatement
      .b("CREATE [OR REPLACE] KEYSPACE *", paramPatternPhrase -> a(paramPatternPhrase.d(1))) || matcherStatement
      .b("DROP SCHEMA [IF EXISTS] * ", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("DROP KEYSPACE [IF EXISTS] * ", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("USE ?", paramPatternPhrase -> a(paramPatternPhrase.f(0))) || matcherStatement
      
      .b("CREATE [OR REPLACE] MATERIALIZED VIEW *", paramPatternPhrase -> a(paramMatcherStatement, paramPatternPhrase.d(1), MaterializedView.class)) || matcherStatement
      
      .b("CREATE [OR REPLACE] VIEW *", paramPatternPhrase -> a(paramMatcherStatement, paramPatternPhrase.d(1), View.class)) || matcherStatement
      
      .b("ALTER TABLE [ONLY] * ADD CONSTRAINT ? PRIMARY KEY * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.PRIMARY_KEY, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * PRIMARY KEY ? * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.PRIMARY_KEY, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      
      .b("ALTER TABLE [ONLY] * ADD FULLTEXT INDEX ? * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.INDEX1, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD SPATIAL INDEX ? * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.INDEX2, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD INDEX ? * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.NORMAL, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD KEY ? * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.NORMAL, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD UNIQUE INDEX ? * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.UNIQUE_INDEX, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD UNIQUE KEY ? * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.UNIQUE_INDEX, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD PRIMARY KEY ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), (String)null, IndexType.PRIMARY_KEY, paramPatternPhrase.e(2), (String)null, paramPatternPhrase.f(3))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD CONSTRAINT ? UNIQUE KEY * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.UNIQUE_INDEX, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD CONSTRAINT ? UNIQUE * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), IndexType.UNIQUE_INDEX, paramPatternPhrase.e(4), paramPatternPhrase.f(3), paramPatternPhrase.f(5))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD CONSTRAINT ? DEFAULT * FOR ?", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), paramPatternPhrase.f(4), paramPatternPhrase.e(3))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD DEFAULT * FOR ?", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), (String)null, paramPatternPhrase.f(3), paramPatternPhrase.e(2))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD ~ ", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.e(2))) || matcherStatement
      .b("CREATE UNIQUE * INDEX ? ON * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(2)), paramPatternPhrase.f(1), IndexType.UNIQUE_INDEX, paramPatternPhrase.e(3), paramPatternPhrase.b(0), paramPatternPhrase.f(4))) || matcherStatement
      
      .b("CREATE UNIQUE INDEX [IF NOT EXISTS] * ON [ONLY] * USING * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(3)), paramPatternPhrase.f(1), IndexType.UNIQUE_INDEX, paramPatternPhrase.e(5), "USING " + paramPatternPhrase.b(4), paramPatternPhrase.f(6))) || matcherStatement
      .b("CREATE INDEX [IF NOT EXISTS] * ON [ONLY] * USING * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(3)), paramPatternPhrase.f(1), IndexType.NORMAL, paramPatternPhrase.e(5), "USING " + paramPatternPhrase.b(4), paramPatternPhrase.f(6))) || matcherStatement
      .b("CREATE * INDEX [IF NOT EXISTS] * ON * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(3)), paramPatternPhrase.f(2), IndexType.NORMAL, paramPatternPhrase.e(4), paramPatternPhrase.b(0), paramPatternPhrase.f(5))) || matcherStatement
      .b("DROP INDEX *", paramPatternPhrase -> {
          Index index = g(paramPatternPhrase.d(0));
          index.markForDeletion();
          return index;
        }) || matcherStatement
      .b("ALTER INDEX * RENAME TO ?", paramPatternPhrase -> {
          Index index = g(paramPatternPhrase.d(0));
          index.rename(paramPatternPhrase.f(1));
          return index;
        }) || matcherStatement
      
      .b("ALTER TABLE [ONLY] * [WITH CHECK] ADD CONSTRAINT ? FOREIGN KEY ~ REFERENCES * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(3), paramPatternPhrase.d(5), paramPatternPhrase.e(4), paramPatternPhrase.e(6), paramPatternPhrase.d(7))) || matcherStatement
      .b("ALTER TABLE [ONLY] * [WITH CHECK] ADD FOREIGN KEY ~ REFERENCES * ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), (String)null, paramPatternPhrase.d(4), paramPatternPhrase.e(3), paramPatternPhrase.e(5), paramPatternPhrase.d(6))) || matcherStatement
      .b("ALTER TABLE [ONLY] * [WITH CHECK] ADD CONSTRAINT ? FOREIGN KEY ~ REFERENCES *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(3), paramPatternPhrase.d(5), paramPatternPhrase.e(4), paramPatternPhrase.e(4), paramPatternPhrase.d(5))) || matcherStatement
      
      .b("ALTER TABLE [ONLY] * ADD CONSTRAINT ? CHECK ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), "CHECK", paramPatternPhrase.e(3), paramPatternPhrase.f(4))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD CHECK ~ *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), this.g.toDefaultCases("CkCons"), "CHECK", paramPatternPhrase.e(2), paramPatternPhrase.f(3))) || matcherStatement
      .b("ALTER TABLE [ONLY] * CHECK CONSTRAINT ? ", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("ALTER TABLE [ONLY] * OWNER TO ? ", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("ALTER TABLE * DROP CONSTRAINT ?", paramPatternPhrase -> {
          AbstractUnit abstractUnit = b(c(paramPatternPhrase.d(0)), paramPatternPhrase.f(1));
          abstractUnit.markForDeletion();
          return abstractUnit;
        }) || matcherStatement
      .b("ALTER TABLE * RENAME CONSTRAINT ? TO ?", paramPatternPhrase -> {
          AbstractUnit abstractUnit = b(c(paramPatternPhrase.d(0)), paramPatternPhrase.f(1));
          abstractUnit.rename(paramPatternPhrase.f(2));
          return abstractUnit;
        }) || matcherStatement
      
      .b("ALTER TABLE [ONLY] * ADD COLUMN * ", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), (ChildEntity)null, paramPatternPhrase.d(2), (StringBuilder)null)) || matcherStatement
      .b("ALTER TABLE [ONLY] * ADD * ", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), (ChildEntity)null, paramPatternPhrase.d(2), (StringBuilder)null)) || matcherStatement
      .b("ALTER TABLE [ONLY] * ALTER COLUMN ? * ", paramPatternPhrase -> b(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), paramPatternPhrase.d(3))) || matcherStatement
      .b("ALTER TABLE [ONLY] * ALTER ? * ", paramPatternPhrase -> b(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), paramPatternPhrase.d(3))) || matcherStatement
      .b("ALTER TABLE [ONLY] * MODIFY ? * ", paramPatternPhrase -> b(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), paramPatternPhrase.d(3))) || matcherStatement
      .b("ALTER TABLE [ONLY] * RENAME COLUMN ? TO ? ", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2), paramPatternPhrase.f(3))) || matcherStatement
      .b("ALTER TABLE [ONLY] * DROP COLUMN ? *", paramPatternPhrase -> a(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(2))) || matcherStatement
      
      .b("ALTER TABLE * [SET] COMMENT [=] ? ", paramPatternPhrase -> {
          Table table = c(paramPatternPhrase.d(0));
          if (table != null)
            table.setComment(StringUtil.trimRemoveQuotesAndUnescapeComment(paramPatternPhrase.f(3))); 
          return table;
        }) || matcherStatement
      .b("COMMENT ON SCHEMA * IS *", paramPatternPhrase -> {
          Schema schema = e(paramPatternPhrase.d(0));
          if (schema != null)
            schema.setComment(StringUtil.trimRemoveQuotesAndUnescapeComment(paramPatternPhrase.d(1))); 
          return schema;
        }) || matcherStatement
      .b("COMMENT ON TABLE * IS *", paramPatternPhrase -> {
          Table table = c(paramPatternPhrase.d(0));
          if (table != null)
            table.setComment(StringUtil.trimRemoveQuotesAndUnescapeComment(paramPatternPhrase.d(1))); 
          return table;
        }) || matcherStatement
      .b("COMMENT ON VIEW * IS *", paramPatternPhrase -> {
          View view = (View)a(paramPatternPhrase.d(0), View.class);
          if (view != null)
            view.setComment(StringUtil.trimRemoveQuotesAndUnescapeComment(paramPatternPhrase.d(1))); 
          return view;
        }) || matcherStatement
      .b("COMMENT ON COLUMN * IS *", paramPatternPhrase -> {
          Column column = f(paramPatternPhrase.d(0));
          column.setComment(StringUtil.trimRemoveQuotesAndUnescapeComment(paramPatternPhrase.d(1)));
          return column;
        }) || matcherStatement
      .b("COMMENT ON INDEX * IS *", paramPatternPhrase -> {
          Index index = g(paramPatternPhrase.d(0));
          index.setComment(StringUtil.trimRemoveQuotesAndUnescapeComment(paramPatternPhrase.d(1)));
          return index;
        }) || matcherStatement
      .b("COMMENT ON CONSTRAINT ? ON * IS *", paramPatternPhrase -> {
          AbstractUnit abstractUnit = b(c(paramPatternPhrase.d(1)), paramPatternPhrase.f(0));
          abstractUnit.setComment(StringUtil.trimRemoveQuotesAndUnescapeComment(paramPatternPhrase.d(2)));
          return abstractUnit;
        }) || matcherStatement
      .b("EXEC ?.?.sp_addextendedproperty *", paramPatternPhrase -> a(paramMatcherStatement)) || matcherStatement
      .b("EXEC ?.sp_addextendedproperty *", paramPatternPhrase -> a(paramMatcherStatement)) || matcherStatement
      .b("EXEC sp_addextendedproperty *", paramPatternPhrase -> a(paramMatcherStatement)) || matcherStatement
      
      .b("CREATE [OR REPLACE] SEQUENCE *", paramPatternPhrase -> Boolean.valueOf(b(paramPatternPhrase.d(1)))) || matcherStatement
      
      .b("CREATE [OR REPLACE] TYPE *", paramPatternPhrase -> Boolean.valueOf(a("CREATE TYPE", paramPatternPhrase.d(1)))) || matcherStatement
      .b("CREATE DOMAIN *", paramPatternPhrase -> Boolean.valueOf(a("CREATE DOMAIN", paramPatternPhrase.d(0)))) || matcherStatement
      
      .b("DROP FUNCTION *", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("DROP PROCEDURE *", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      
      .b("PROMPT *", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("SHOW DATABASES", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("PRAGMA *", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("SET *", paramPatternPhrase -> Boolean.valueOf(true)) || matcherStatement
      .b("INSERT INTO *", paramPatternPhrase -> Boolean.valueOf(true))) {
      if (Sys.B.DDLParserLogAllStatements.b())
        this.f.println("Parsed."); 
    } else {
      if (!Sys.B.DDLParserLogAllStatements.b())
        this.f.println("\n" + paramString + "\n"); 
      MatcherException matcherException = matcherStatement.m();
      this.f.println((matcherException != null) ? matcherException.getLocalizedMessage() : "Not understood. Inform technicians using Help / Technical Support");
    } 
  }
  
  private Object a(MatcherCommaPhrase paramMatcherCommaPhrase) {
    Schema schema = e(paramMatcherCommaPhrase);
    if (schema != null)
      schema.setOptions(paramMatcherCommaPhrase.h()); 
    return schema;
  }
  
  private Schema a(String paramString) {
    Schema schema = this.c.getSchema(paramString, null);
    if (schema == null)
      schema = this.c.getSchema(null, paramString); 
    if (schema != null)
      this.c.setInUseSchema(schema); 
    return schema;
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
  
  private boolean b(MatcherCommaPhrase paramMatcherCommaPhrase) {
    Sequence sequence = (Sequence)a(paramMatcherCommaPhrase, Sequence.class);
    if (sequence == null)
      return false; 
    sequence.setOptions(paramMatcherCommaPhrase.h());
    return true;
  }
  
  private boolean a(String paramString, MatcherCommaPhrase paramMatcherCommaPhrase) {
    UserDataType userDataType = (UserDataType)a(paramMatcherCommaPhrase, UserDataType.class);
    if (userDataType == null)
      return false; 
    userDataType.setScript(paramString + " ${name} " + paramString);
    return true;
  }
  
  private Object a(MatcherStatement paramMatcherStatement, MatcherCommaPhrase paramMatcherCommaPhrase, Class<MaterializedView> paramClass) {
    View view = (View)a(paramMatcherCommaPhrase, paramClass);
    if (view == null) {
      this.f.println("Parse Error PVN01.");
    } else {
      String str = paramMatcherStatement.toString();
      paramMatcherCommaPhrase.f();
      if (str != null) {
        String str1 = h(paramMatcherCommaPhrase);
        if (str1 != null)
          str = str.replaceAll(Pattern.quote(str1), (paramClass == MaterializedView.class) ? "\\$\\{materializedView\\}" : "\\$\\{view\\}"); 
        view.setScript(str);
        view.deduceVirtualFks();
      } 
    } 
    return view;
  }
  
  private Table a(MatcherCommaPhrase paramMatcherCommaPhrase1, MatcherCommaPhrase paramMatcherCommaPhrase2, MatcherCommaPhrase paramMatcherCommaPhrase3) {
    Table table = d(paramMatcherCommaPhrase1);
    table.setType(Table$TableType.e);
    if (paramMatcherCommaPhrase2 != null)
      table.setSpecificationOptions(paramMatcherCommaPhrase2.toString()); 
    table.setOptions("PARTITION OF " + String.valueOf(paramMatcherCommaPhrase3));
    return table;
  }
  
  private Table a(MatcherCommaPhrase paramMatcherCommaPhrase1, MatcherStatement paramMatcherStatement, MatcherCommaPhrase paramMatcherCommaPhrase2, MatcherCommaPhrase paramMatcherCommaPhrase3) {
    Table table = d(paramMatcherCommaPhrase1);
    if (paramMatcherStatement == null)
      return null; 
    a(table, paramMatcherStatement);
    paramMatcherCommaPhrase3.c("PRIMARY KEY ~", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("PK_" + paramTable.getName()), IndexType.PRIMARY_KEY, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("PARTITIONED BY ~", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Partition"), IndexType.PARTITION, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("PARTITION BY ~", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Partition"), IndexType.PARTITION, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("CLUSTERED BY ~", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Cluster"), IndexType.CLUSTER, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("DISTKEY ~ ", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Distribution"), IndexType.CLUSTER, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("DISTRIBUTE BY ~ ", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Distribution"), IndexType.CLUSTER, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("SORTED BY ~ INTO ? BUCKETS", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Sort"), IndexType.SORT, paramPatternPhrase.e(0), (String)null, "INTO " + paramPatternPhrase.f(1) + "BUCKETS"));
    paramMatcherCommaPhrase3.c("SORTED BY ~ ", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Sort"), IndexType.SORT, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("SORTKEY ~ ", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Sorting"), IndexType.SORT, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("CLUSTERING ORDER BY ~", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("ClusterOrder"), IndexType.SORT, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("SKEWED BY ~ ON ?", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Skew"), IndexType.INDEX1, paramPatternPhrase.e(0), (String)null, "ON " + paramPatternPhrase.f(1)));
    paramMatcherCommaPhrase3.c("SKEWED BY ~", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("Skew"), IndexType.INDEX1, paramPatternPhrase.e(0), (String)null, (String)null));
    paramMatcherCommaPhrase3.c("COMMENT [=] ?", paramPatternPhrase -> {
          paramTable.setComment(StringUtil.unescapeComment(paramPatternPhrase.f(1)));
          return paramTable;
        });
    paramMatcherCommaPhrase3.b("OPTIONS ~", paramPatternPhrase -> Boolean.valueOf(a(paramTable, paramPatternPhrase.e(0))));
    paramMatcherCommaPhrase3.b("WITH *", paramPatternPhrase -> Boolean.valueOf(a(paramTable, paramPatternPhrase.d(0))));
    if ("Redshift".equals(this.c.getDbId())) {
      paramMatcherCommaPhrase3.c("DISTSTYLE ? ", paramPatternPhrase -> {
            Index index = paramTable.getIndexByType(IndexType.CLUSTER);
            if (index != null)
              index.setSpecificationOptions("DISTSTYLE " + paramPatternPhrase.f(0)); 
            return index;
          });
      paramMatcherCommaPhrase3.c("COMPOUND", paramPatternPhrase -> {
            Index index = paramTable.getIndexByType(IndexType.SORT);
            if (index != null)
              index.setSpecificationOptions("COMPOUND"); 
            return index;
          });
      paramMatcherCommaPhrase3.c("INTERLEAVED", paramPatternPhrase -> {
            Index index = paramTable.getIndexByType(IndexType.SORT);
            if (index != null)
              index.setSpecificationOptions("INTERLEAVED"); 
            return index;
          });
    } 
    table.setOptions(paramMatcherCommaPhrase3.h());
    if (paramMatcherCommaPhrase2 != null)
      table.setSpecificationOptions(paramMatcherCommaPhrase2.toString()); 
    return table;
  }
  
  private Table a(Table paramTable, MatcherStatement paramMatcherStatement) {
    this.a = null;
    if (paramTable == null || paramMatcherStatement == null) {
      this.f.println("Parse Error PTC03.");
    } else {
      for (MatcherCommaPhrase matcherCommaPhrase : paramMatcherStatement) {
        if (this.a == null) {
          if (!matcherCommaPhrase.isEmpty()) {
            String str = ((MatcherNode)matcherCommaPhrase.get(0)).c();
            if (str != null) {
              switch (str.toUpperCase()) {
                case "--":
                  continue;
                case "FULLTEXT":
                case "SPATIAL":
                case "INDEX":
                case "KEY":
                case "UNIQUE":
                case "PRIMARY":
                case "SHARD":
                  c(paramTable, matcherCommaPhrase);
                  continue;
                case "CONSTRAINT":
                case "FOREIGN":
                case "CHECK":
                  b(paramTable, matcherCommaPhrase);
                  continue;
                case "LIKE":
                  matcherCommaPhrase.b("LIKE *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.d(0)));
                  continue;
              } 
              if (matcherCommaPhrase.a("? AS *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), paramPatternPhrase.d(1))) == null && matcherCommaPhrase
                .a("PERIOD FOR SYSTEM_TIME ~", paramPatternPhrase -> a(paramTable, this.g.toDefaultCases("TemporalPeriod"), IndexType.INDEX2, paramPatternPhrase.e(0), (String)null, (String)null)) == null && matcherCommaPhrase
                .a("DISTRIBUTE BY *", paramPatternPhrase -> this.a = a(paramTable, this.g.toDefaultCases("Distribution"), IndexType.CLUSTER, paramPatternPhrase.d(0))) == null && matcherCommaPhrase
                .a("PARTITION BY *", paramPatternPhrase -> this.a = a(paramTable, this.g.toDefaultCases("Partition"), IndexType.PARTITION, paramPatternPhrase.d(0))) == null)
                a(paramTable, (ChildEntity)null, matcherCommaPhrase, (StringBuilder)null); 
            } 
          } 
          continue;
        } 
        DDLParserUtil.a(matcherCommaPhrase, this.a);
      } 
    } 
    return paramTable;
  }
  
  private Object a(Table paramTable, String paramString, MatcherCommaPhrase paramMatcherCommaPhrase) {
    Column column = paramTable.createColumn(paramString, this.e.getDataType(12), AttributeSpec.computed);
    column.setLength(120);
    paramMatcherCommaPhrase.c("NOT NULL", paramPatternPhrase -> {
          paramColumn.setMandatory(true);
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("NULL", paramPatternPhrase -> {
          paramColumn.setMandatory(false);
          return paramColumn;
        });
    PatternPhrase patternPhrase = paramMatcherCommaPhrase.d("~ *");
    if (patternPhrase == null) {
      column.setDefinition(paramMatcherCommaPhrase.toString());
    } else {
      column.setDefinition(patternPhrase.f(0));
      column.setOptions(patternPhrase.f(1));
    } 
    return column;
  }
  
  private Table a(Table paramTable, MatcherCommaPhrase paramMatcherCommaPhrase) {
    Table table = c(paramMatcherCommaPhrase);
    if (table == null)
      return null; 
    boolean bool1 = (paramMatcherCommaPhrase.b("INCLUDE DEFAULTS") != null) ? true : false;
    boolean bool2 = (paramMatcherCommaPhrase.b("INCLUDE CONSTRAINTS") != null) ? true : false;
    boolean bool3 = (paramMatcherCommaPhrase.b("INCLUDE INDEXES") != null) ? true : false;
    boolean bool4 = (paramMatcherCommaPhrase.b("INCLUDE STORAGE") != null) ? true : false;
    boolean bool5 = (paramMatcherCommaPhrase.b("INCLUDE COMMENTS") != null) ? true : false;
    if (paramMatcherCommaPhrase.b("INCLUDE ALL") != null) {
      bool1 = true;
      bool2 = true;
      bool3 = true;
      bool4 = true;
      bool5 = true;
    } 
    for (Column column1 : table.columns) {
      Column column2 = paramTable.createColumn(column1.getName(), column1.getDataType());
      column2.cloneFrom(column1);
      if (!bool1)
        column2.setDefaultValue((String)null); 
      if (!bool5)
        column2.setComment(null); 
    } 
    for (Index index : table.indexes) {
      if (((index.getType() == IndexType.NORMAL || index.getType() == IndexType.UNIQUE_INDEX) && bool3) || bool2) {
        Index index1 = paramTable.createIndex(index.getName() + "_");
        index1.setType(index.getType());
        for (Column column : index.columns)
          index1.addColumn(paramTable.getColumnByNameOrPath(column.getName())); 
      } 
    } 
    paramTable.setSpecificationOptions(table.getSpecificationOptions());
    if (bool4)
      paramTable.setOptions(table.getOptions()); 
    return table;
  }
  
  private Column a(Table paramTable, String paramString1, String paramString2) {
    if (paramTable == null || paramString1 == null || paramString2 == null) {
      this.f.println("Parse Error PRC02.");
    } else {
      Column column = paramTable.getColumnByNameOrPath(paramString1);
      if (column == null) {
        this.f.println("Parse Error PRC03.");
      } else {
        column.rename(paramString2);
      } 
      return column;
    } 
    return null;
  }
  
  private Column a(Table paramTable, String paramString) {
    if (paramTable == null || paramString == null) {
      this.f.println("Parse Error PDC02.");
    } else {
      Column column = paramTable.getColumnByNameOrPath(paramString);
      if (column == null) {
        this.f.println("Parse Error PDC03.");
      } else {
        column.markForDeletion();
      } 
      return column;
    } 
    return null;
  }
  
  private Column b(Table paramTable, String paramString, MatcherCommaPhrase paramMatcherCommaPhrase) {
    if (paramTable == null || paramMatcherCommaPhrase == null) {
      this.f.println("Parse Error PCC01.");
    } else {
      Column column = paramTable.getColumnByNameOrPath(paramString);
      if (column == null) {
        this.f.println("Parse Error PCC02.");
      } else {
        if (this.d.defaultIsUsingBrackets())
          paramMatcherCommaPhrase.c("SET DEFAULT ~", paramPatternPhrase -> a(paramColumn, paramPatternPhrase.e(0).j())); 
        paramMatcherCommaPhrase.c("SET DEFAULT *", paramPatternPhrase -> a(paramColumn, String.valueOf(paramPatternPhrase.d(0))));
        paramMatcherCommaPhrase.c("DROP DEFAULT", paramPatternPhrase -> {
              paramColumn.setDefaultValue((String)null);
              return paramColumn;
            });
        paramMatcherCommaPhrase.c("DROP NOT NULL", paramPatternPhrase -> {
              paramColumn.setMandatory(false);
              return paramColumn;
            });
        paramMatcherCommaPhrase.c("NOT NULL", paramPatternPhrase -> {
              paramColumn.setMandatory(true);
              return paramColumn;
            });
        paramMatcherCommaPhrase.c("NULL", paramPatternPhrase -> {
              paramColumn.setMandatory(false);
              return paramColumn;
            });
        paramMatcherCommaPhrase.c("COMMENT ?", paramPatternPhrase -> {
              paramColumn.setComment(StringUtil.unescapeComment(paramPatternPhrase.f(0)));
              return paramColumn;
            });
      } 
      return column;
    } 
    return null;
  }
  
  private Column a(Column paramColumn, String paramString) {
    if (paramColumn != null && paramString != null) {
      paramString = paramString.trim();
      if ("NULL".equalsIgnoreCase(paramString.trim())) {
        if (this.d.columnUseDefaultNull.b()) {
          paramColumn.setDefaultValue("NULL");
        } else {
          paramColumn.setMandatory(false);
        } 
      } else {
        paramColumn.setDefaultValue(paramString);
      } 
    } 
    return paramColumn;
  }
  
  private Column a(Table paramTable, ChildEntity paramChildEntity, MatcherCommaPhrase paramMatcherCommaPhrase, StringBuilder paramStringBuilder) {
    if (paramTable == null || paramMatcherCommaPhrase == null)
      throw new MatcherException("Parse Error PC01 - Table Not Found."); 
    if (paramMatcherCommaPhrase.size() == 1) {
      if (paramStringBuilder == null)
        throw new MatcherException("Parse Error PC03."); 
      paramStringBuilder.append(paramMatcherCommaPhrase);
      return null;
    } 
    byte b = 2;
    String str1 = ((MatcherNode)paramMatcherCommaPhrase.get(1)).d();
    Schema schema = paramTable.schema;
    if (paramMatcherCommaPhrase.d("? ?.?") != null) {
      schema = this.c.getSchemaInUseCatalog(((MatcherNode)paramMatcherCommaPhrase.get(1)).d());
      str1 = ((MatcherNode)paramMatcherCommaPhrase.get(3)).d();
      if (schema == null)
        schema = paramTable.schema; 
    } 
    if (":".equals(str1) && paramMatcherCommaPhrase.size() > 2) {
      str1 = ((MatcherNode)paramMatcherCommaPhrase.get(2)).d();
      b = 3;
    } 
    if (paramMatcherCommaPhrase.b("character varying") != null) {
      b = 3;
      str1 = "varchar";
    } 
    if (paramMatcherCommaPhrase.b("double precision") != null) {
      b = 3;
      str1 = "double precision";
    } 
    if (str1 == null)
      str1 = "varchar"; 
    if (paramMatcherCommaPhrase.size() > b + 1 && "[".equals(paramMatcherCommaPhrase.b(b)) && "]".equals(paramMatcherCommaPhrase.b(b + 1)))
      str1 = str1 + "[]"; 
    MatcherStatement matcherStatement = (paramMatcherCommaPhrase.size() > b && paramMatcherCommaPhrase.get(b) instanceof MatcherStatement) ? (MatcherStatement)paramMatcherCommaPhrase.get(b) : null;
    DataType dataType = schema.getUserDataType(str1);
    if (dataType == null)
      dataType = this.e.getType(str1); 
    String str2 = ((MatcherNode)paramMatcherCommaPhrase.get(0)).d();
    Column column1;
    if ((paramChildEntity != null && (column1 = (Column)paramChildEntity.columns.getByName(str2)) != null) || (column1 = (Column)paramTable.columns.getByName(str2)) != null)
      return column1; 
    Column column2 = (paramChildEntity != null) ? paramChildEntity.createColumn(str2, dataType) : paramTable.createColumn(str2, dataType);
    if (matcherStatement != null && !matcherStatement.isEmpty())
      if ("<".equals(matcherStatement.b)) {
        if ("GoogleBigQuery".equals(this.c.getDbId()) || "AmazonAthena".equals(this.c.getDbId()) || "Databricks".equals(this.c.getDbId())) {
          StringBuilder stringBuilder = new StringBuilder();
          for (MatcherCommaPhrase matcherCommaPhrase : matcherStatement) {
            if (!stringBuilder.isEmpty())
              stringBuilder.append(","); 
            if (matcherCommaPhrase.size() > 1 && matcherCommaPhrase.get(1) instanceof MatcherStatement) {
              stringBuilder.append(String.valueOf(matcherCommaPhrase.get(0)).trim()).append("<?>");
              ChildEntity childEntity1 = column2.getCreateChildEntity();
              for (MatcherCommaPhrase matcherCommaPhrase1 : matcherCommaPhrase.get(1))
                a(paramTable, childEntity1, matcherCommaPhrase1, (StringBuilder)null); 
              continue;
            } 
            ChildEntity childEntity = column2.getCreateChildEntity();
            a(paramTable, childEntity, matcherCommaPhrase, stringBuilder);
          } 
          if (stringBuilder.isEmpty())
            stringBuilder.append("?"); 
          column2.setDataType(this.e.getType(str1 + "<" + str1 + ">"));
        } else {
          column2.setDataType(this.e.getType(str1 + str1));
        } 
      } else if (dataType.getPrecision() == Precision.ENUMERATION) {
        column2.setEnumeration(matcherStatement.j());
      } else {
        int i = Integer.MIN_VALUE, j = Integer.MIN_VALUE;
        MatcherNode matcherNode = (MatcherNode)((MatcherCommaPhrase)matcherStatement.get(0)).get(0);
        if (DDLParserUtil.a(matcherNode)) {
          i = Integer.parseInt(matcherNode.c());
        } else if ("max".equalsIgnoreCase(matcherNode.c())) {
          column2.setDataType(this.e.getType(str1 + "(max)"));
        } else if ("*".equalsIgnoreCase(matcherNode.c())) {
          if ("Oracle".equals(this.c.getDbId()))
            i = 38; 
        } else if ("ClickHouse".equals(this.c.getDbId())) {
          column2.setTypeOptions(matcherStatement.j());
        } 
        if (((MatcherCommaPhrase)matcherStatement.get(0)).size() > 1) {
          String str = ((MatcherNode)((MatcherCommaPhrase)matcherStatement.get(0)).get(1)).c();
          column2.setTypeOptions(str);
        } 
        if (matcherStatement.size() > 1 && 
          DDLParserUtil.a((MatcherNode)((MatcherCommaPhrase)matcherStatement.get(1)).get(0)))
          j = Integer.parseInt(((MatcherNode)((MatcherCommaPhrase)matcherStatement.get(1)).get(0)).c()); 
        if (i > Integer.MIN_VALUE)
          column2.setLength(i); 
        if (j > Integer.MIN_VALUE)
          column2.setDecimal(j); 
      }  
    paramMatcherCommaPhrase.b("OPTIONS ~", paramPatternPhrase -> Boolean.valueOf(a(paramColumn, paramPatternPhrase.e(0))));
    paramMatcherCommaPhrase.b("WITH *", paramPatternPhrase -> Boolean.valueOf(a(paramColumn, paramPatternPhrase.d(0))));
    paramMatcherCommaPhrase.c("REFERENCES * ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.d(0), paramColumn, paramPatternPhrase.e(1), paramPatternPhrase.d(2)));
    paramMatcherCommaPhrase.c("REFERENCES *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.d(0), paramColumn));
    paramMatcherCommaPhrase.c("CONSTRAINT ? UNIQUE KEY", paramPatternPhrase -> a(paramMatcherCommaPhrase, paramTable, paramColumn, paramPatternPhrase.f(0), IndexType.UNIQUE_KEY));
    paramMatcherCommaPhrase.c("UNIQUE KEY", paramPatternPhrase -> a(paramMatcherCommaPhrase, paramTable, paramColumn, paramTable.schema.nameFinder.a(paramTable, paramColumn, IndexType.UNIQUE_KEY), IndexType.UNIQUE_KEY));
    paramMatcherCommaPhrase.c("UNIQUE", paramPatternPhrase -> a(paramMatcherCommaPhrase, paramTable, paramColumn, paramTable.schema.nameFinder.a(paramTable, paramColumn, IndexType.UNIQUE_KEY), IndexType.UNIQUE_KEY));
    paramMatcherCommaPhrase.c("CONSTRAINT ? PRIMARY KEY", paramPatternPhrase -> a(paramMatcherCommaPhrase, paramTable, paramColumn, paramPatternPhrase.f(0), IndexType.PRIMARY_KEY));
    paramMatcherCommaPhrase.c("PRIMARY KEY", paramPatternPhrase -> a(paramMatcherCommaPhrase, paramTable, paramColumn, paramTable.schema.nameFinder.a(paramTable, paramColumn, IndexType.PRIMARY_KEY), IndexType.PRIMARY_KEY));
    paramMatcherCommaPhrase.c("PRIMARY", paramPatternPhrase -> a(paramMatcherCommaPhrase, paramTable, paramColumn, paramTable.schema.nameFinder.a(paramTable, paramColumn, IndexType.PRIMARY_KEY), IndexType.PRIMARY_KEY));
    paramMatcherCommaPhrase.c("CONSTRAINT ? CHECK ~", paramPatternPhrase -> {
          Constraint constraint = paramTable.createConstraint(paramPatternPhrase.f(0));
          constraint.setText(paramPatternPhrase.e(1).j());
          return constraint;
        });
    paramMatcherCommaPhrase.c("CHECK ~", paramPatternPhrase -> {
          Constraint constraint = paramTable.createConstraint(paramTable.schema.nameFinder.a(paramTable, paramColumn));
          constraint.setText(paramPatternPhrase.e(0).j());
          return constraint;
        });
    paramMatcherCommaPhrase.c("NOT NULL", paramPatternPhrase -> {
          paramColumn.setMandatory(true);
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("UNSIGNED", paramPatternPhrase -> {
          paramColumn.setUnsigned(true);
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("COMMENT IS ?", paramPatternPhrase -> {
          paramColumn.setComment(StringUtil.unescapeComment(paramPatternPhrase.f(0)));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("COMMENT [=] ?", paramPatternPhrase -> {
          paramColumn.setComment(StringUtil.unescapeComment(paramPatternPhrase.f(1)));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("GENERATED * AS ? ~", paramPatternPhrase -> {
          paramColumn.setIdentity("GENERATED " + String.valueOf(paramPatternPhrase.d(0)) + " AS " + paramPatternPhrase.f(1) + " " + String.valueOf(paramPatternPhrase.e(2)));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("GENERATED * AS ROW ?", paramPatternPhrase -> {
          paramColumn.setIdentity("GENERATED " + String.valueOf(paramPatternPhrase.d(0)) + " AS ROW " + paramPatternPhrase.f(1));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("GENERATED * AS ?", paramPatternPhrase -> {
          paramColumn.setIdentity("GENERATED " + String.valueOf(paramPatternPhrase.d(0)) + " AS " + paramPatternPhrase.f(1));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("ENCODE ?", paramPatternPhrase -> {
          paramColumn.appendOptions("ENCODE " + paramPatternPhrase.f(0));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("ON UPDATE *", paramPatternPhrase -> {
          paramColumn.appendOptions("ON UPDATE " + paramPatternPhrase.f(0));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("DEFAULT CHARACTER SET ?", paramPatternPhrase -> {
          paramColumn.appendOptions("DEFAULT CHARACTER SET " + paramPatternPhrase.f(0));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("CHARACTER SET ?", paramPatternPhrase -> {
          paramColumn.appendOptions("CHARACTER SET " + paramPatternPhrase.f(0));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("SPARSE", paramPatternPhrase -> {
          paramColumn.appendOptions("SPARSE");
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("COLLATE ?", paramPatternPhrase -> {
          paramColumn.appendOptions("COLLATE " + paramPatternPhrase.f(0));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("AUTO_INCREMENT", paramPatternPhrase -> {
          paramColumn.setIdentity("AUTO_INCREMENT");
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("AUTOINCREMENT", paramPatternPhrase -> {
          paramColumn.setIdentity("AUTOINCREMENT");
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("IDENTITY ~", paramPatternPhrase -> {
          paramColumn.setIdentity("IDENTITY " + String.valueOf(paramPatternPhrase.d(0)));
          return paramColumn;
        });
    paramMatcherCommaPhrase.c("IDENTITY", paramPatternPhrase -> {
          paramColumn.setIdentity("IDENTITY");
          return paramColumn;
        });
    if (this.d.defaultIsUsingBrackets())
      paramMatcherCommaPhrase.c("DEFAULT ~", paramPatternPhrase -> a(paramColumn, paramPatternPhrase.e(0).j())); 
    paramMatcherCommaPhrase.c("DEFAULT *", paramPatternPhrase -> a(paramColumn, String.valueOf(paramPatternPhrase.d(0))));
    paramMatcherCommaPhrase.c("MASKING POLICY *", paramPatternPhrase -> {
          paramColumn.appendOptions("MASKING POLICY " + String.valueOf(paramPatternPhrase.d(0)));
          return paramColumn;
        });
    return column2;
  }
  
  private boolean a(AbstractUnit paramAbstractUnit, MatcherStatement paramMatcherStatement) {
    for (MatcherCommaPhrase matcherCommaPhrase : paramMatcherStatement)
      a(paramAbstractUnit, matcherCommaPhrase); 
    return true;
  }
  
  private boolean a(AbstractUnit paramAbstractUnit, MatcherCommaPhrase paramMatcherCommaPhrase) {
    paramMatcherCommaPhrase.c("DESCRIPTION [=] ?", paramPatternPhrase -> {
          paramAbstractUnit.setComment(StringUtil.unescapeComment(paramPatternPhrase.f(1)));
          return Boolean.valueOf(true);
        });
    paramMatcherCommaPhrase.c("COMMENT [=] ?", paramPatternPhrase -> {
          paramAbstractUnit.setComment(StringUtil.unescapeComment(paramPatternPhrase.f(1)));
          return Boolean.valueOf(true);
        });
    return true;
  }
  
  private void b(Table paramTable, MatcherCommaPhrase paramMatcherCommaPhrase) {
    paramMatcherCommaPhrase.c("CONSTRAINT ? PRIMARY KEY * ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.PRIMARY_KEY, paramPatternPhrase.e(2), paramPatternPhrase.b(1), paramPatternPhrase.b(3)));
    paramMatcherCommaPhrase.c("CONSTRAINT ? UNIQUE KEY * ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.UNIQUE_KEY, paramPatternPhrase.e(2), paramPatternPhrase.b(1), paramPatternPhrase.b(3)));
    paramMatcherCommaPhrase.c("CONSTRAINT ? UNIQUE INDEX * ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.UNIQUE_INDEX, paramPatternPhrase.e(2), paramPatternPhrase.b(1), paramPatternPhrase.b(3)));
    paramMatcherCommaPhrase.c("CONSTRAINT ? UNIQUE * ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.UNIQUE_KEY, paramPatternPhrase.e(2), paramPatternPhrase.b(1), paramPatternPhrase.b(3)));
    paramMatcherCommaPhrase.c("CONSTRAINT ? FOREIGN KEY ~ REFERENCES * ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), paramPatternPhrase.d(2), paramPatternPhrase.e(1), paramPatternPhrase.e(3), paramPatternPhrase.d(4)));
    paramMatcherCommaPhrase.c("CONSTRAINT ? FOREIGN KEY ~ REFERENCES *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), paramPatternPhrase.d(2), paramPatternPhrase.e(1), paramPatternPhrase.e(1), paramMatcherCommaPhrase));
    paramMatcherCommaPhrase.c("FOREIGN KEY ~ REFERENCES * ~ *", paramPatternPhrase -> a(paramTable, (String)null, paramPatternPhrase.d(1), paramPatternPhrase.e(0), paramPatternPhrase.e(2), paramPatternPhrase.d(3)));
    paramMatcherCommaPhrase.c("FOREIGN KEY ~ REFERENCES *", paramPatternPhrase -> a(paramTable, (String)null, paramPatternPhrase.d(1), paramPatternPhrase.e(0), paramPatternPhrase.e(0), paramMatcherCommaPhrase));
    paramMatcherCommaPhrase.c("CONSTRAINT ? CHECK ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), "CHECK", paramPatternPhrase.e(1), paramPatternPhrase.b(2)));
    paramMatcherCommaPhrase.c("CONSTRAINT ? EXCLUDE USING GIST ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), "EXCLUDE USING GIST", paramPatternPhrase.e(1), paramPatternPhrase.b(2)));
    paramMatcherCommaPhrase.c("CHECK ~ *", paramPatternPhrase -> a(paramTable, paramTable.constraints.proposeName("CK_" + paramTable.getName()), "CHECK", paramPatternPhrase.e(0), paramPatternPhrase.b(1)));
  }
  
  private void c(Table paramTable, MatcherCommaPhrase paramMatcherCommaPhrase) {
    paramMatcherCommaPhrase.c("PRIMARY KEY ? ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.PRIMARY_KEY, paramPatternPhrase.e(1), (String)null, paramPatternPhrase.f(2)));
    paramMatcherCommaPhrase.c("FULLTEXT KEY ? ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.INDEX1, paramPatternPhrase.e(1), (String)null, paramPatternPhrase.f(2)));
    paramMatcherCommaPhrase.c("PRIMARY KEY ~ *", paramPatternPhrase -> a(paramTable, paramTable.schema.nameFinder.a(paramTable, (Column)null, IndexType.PRIMARY_KEY), IndexType.PRIMARY_KEY, paramPatternPhrase.e(0), (String)null, paramPatternPhrase.f(1)));
    paramMatcherCommaPhrase.c("UNIQUE KEY ? ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.UNIQUE_KEY, paramPatternPhrase.e(1), (String)null, paramPatternPhrase.f(2)));
    paramMatcherCommaPhrase.c("SHARD KEY ? ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.PARTITION, paramPatternPhrase.e(1), (String)null, paramPatternPhrase.f(2)));
    paramMatcherCommaPhrase.c("SHARD KEY ~ *", paramPatternPhrase -> a(paramTable, "Shard Key", IndexType.PARTITION, paramPatternPhrase.e(0), (String)null, paramPatternPhrase.f(1)));
    paramMatcherCommaPhrase.c("KEY ? ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.NORMAL, paramPatternPhrase.e(1), (String)null, paramPatternPhrase.f(2)));
    paramMatcherCommaPhrase.c("INDEX ? ~ *", paramPatternPhrase -> a(paramTable, paramPatternPhrase.f(0), IndexType.NORMAL, paramPatternPhrase.e(1), (String)null, paramPatternPhrase.f(2)));
    paramMatcherCommaPhrase.c("FULLTEXT ~ *", paramPatternPhrase -> a(paramTable, "FT", IndexType.INDEX1, paramPatternPhrase.e(0), (String)null, paramPatternPhrase.f(1)));
  }
  
  private Table c(MatcherCommaPhrase paramMatcherCommaPhrase) {
    Schema schema = null;
    String str = null;
    Table table = null;
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.d("?.?.?")) != null) {
      schema = this.c.getSchema(patternPhrase.f(0), patternPhrase.f(1));
      if (schema == null)
        return null; 
      table = schema.getTable(str = patternPhrase.f(2));
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?.?")) != null) {
      schema = this.c.getSchemaInUseCatalog(patternPhrase.f(0));
      if (schema == null)
        return null; 
      table = schema.getTable(str = patternPhrase.f(1));
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?")) != null) {
      String str1 = patternPhrase.f(0);
      String[] arrayOfString = str1.split("\\.");
      if (arrayOfString.length == 3) {
        schema = this.c.getSchema(arrayOfString[0], arrayOfString[1]);
        if (schema == null)
          return null; 
        table = schema.getTable(str = arrayOfString[2]);
      } else if (arrayOfString.length == 2) {
        schema = this.c.getSchemaInUseCatalog(arrayOfString[0]);
        if (schema == null)
          return null; 
        table = schema.getTable(str = arrayOfString[1]);
      } else {
        table = (schema = a()).getTable(str = patternPhrase.f(0));
      } 
    } 
    if (table == null)
      throw new MatcherException("Cannot find table " + String.valueOf(schema) + "." + str + ". Switch to a different schema using 'USE <schemaName>'."); 
    return table;
  }
  
  private Table d(MatcherCommaPhrase paramMatcherCommaPhrase) {
    Table table = null;
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.d("?.?.?")) != null) {
      Schema schema = this.c.getSchema(patternPhrase.f(0), patternPhrase.f(1));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(1), patternPhrase.f(0)); 
      table = schema.getOrCreateTable(patternPhrase.f(2));
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?.?")) != null) {
      Schema schema = this.c.getSchemaInUseCatalog(patternPhrase.f(0));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(0)); 
      table = schema.getOrCreateTable(patternPhrase.f(1));
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?")) != null) {
      String str = patternPhrase.f(0);
      String[] arrayOfString = str.split("\\.");
      if (arrayOfString.length == 3) {
        Schema schema = this.c.getSchema(arrayOfString[0], arrayOfString[1]);
        if (schema == null)
          schema = this.c.createSchema(arrayOfString[1], arrayOfString[0]); 
        table = schema.getOrCreateTable(arrayOfString[2]);
      } else if (arrayOfString.length == 2) {
        Schema schema = this.c.getSchemaInUseCatalog(arrayOfString[0]);
        if (schema == null)
          schema = this.c.createSchema(arrayOfString[0]); 
        table = schema.getOrCreateTable(arrayOfString[1]);
      } else {
        table = a().getTable(patternPhrase.f(0));
        if (table == null && b() != null)
          table = b().getTable(patternPhrase.f(0)); 
        if (table == null)
          table = a().getOrCreateTable(patternPhrase.f(0)); 
      } 
    } 
    if (table == null)
      throw new MatcherException("Cannot find table '" + String.valueOf(paramMatcherCommaPhrase) + "'"); 
    return table;
  }
  
  private Schema e(MatcherCommaPhrase paramMatcherCommaPhrase) {
    Schema schema = null;
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.b("?.?")) != null) {
      schema = this.c.getSchema(patternPhrase.f(0), patternPhrase.f(1));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(1), patternPhrase.f(0)); 
    } else if ((patternPhrase = paramMatcherCommaPhrase.b("?")) != null) {
      schema = this.c.getSchemaInUseCatalog(patternPhrase.f(0));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(0)); 
    } 
    if (schema == null)
      this.f.println("Parse Error SN01."); 
    return schema;
  }
  
  private Column f(MatcherCommaPhrase paramMatcherCommaPhrase) {
    Column column = null;
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.d("?.?.?.?")) != null) {
      Schema schema = this.c.getSchema(patternPhrase.f(0), patternPhrase.f(1));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(1), patternPhrase.f(0)); 
      Table table = schema.getOrCreateTable(patternPhrase.f(2));
      column = table.getColumnByNameOrPath(patternPhrase.f(3));
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?.?.?")) != null) {
      Schema schema = this.c.getSchema(null, patternPhrase.f(0));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(0)); 
      Table table = schema.getOrCreateTable(patternPhrase.f(1));
      column = table.getColumnByNameOrPath(patternPhrase.f(2));
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?.?")) != null) {
      Schema schema = a();
      if (schema != null) {
        Table table = schema.getOrCreateTable(patternPhrase.f(0));
        column = table.getColumnByNameOrPath(patternPhrase.f(1));
      } 
    } 
    if (column == null)
      throw new MatcherException("Column not found '" + String.valueOf(paramMatcherCommaPhrase) + "'"); 
    return column;
  }
  
  private Index g(MatcherCommaPhrase paramMatcherCommaPhrase) {
    Index index = null;
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.d("?.?.?")) != null) {
      Schema schema = this.c.getSchema(patternPhrase.f(0), patternPhrase.f(1));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(1), patternPhrase.f(0)); 
      index = schema.getIndexByName(patternPhrase.f(2));
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?.?")) != null) {
      Schema schema = this.c.getSchema(null, patternPhrase.f(0));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(0)); 
      index = schema.getIndexByName(patternPhrase.f(1));
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?")) != null) {
      Schema schema = a();
      if (schema != null)
        index = schema.getIndexByName(patternPhrase.f(0)); 
    } 
    if (index == null)
      throw new MatcherException("Index not found '" + String.valueOf(paramMatcherCommaPhrase) + "'"); 
    return index;
  }
  
  private Index a(Table paramTable, String paramString1, IndexType paramIndexType, MatcherStatement paramMatcherStatement, String paramString2, String paramString3) {
    if (paramTable == null || paramMatcherStatement == null)
      throw new MatcherException("Parse Error PI01."); 
    if (paramString1 == null)
      paramString1 = paramTable.schema.nameFinder.a(paramTable, (Column)null, paramIndexType); 
    Index index = paramTable.createIndex(paramString1);
    index.setType(paramIndexType);
    for (MatcherCommaPhrase matcherCommaPhrase : paramMatcherStatement) {
      if (matcherCommaPhrase.get(0) instanceof MatcherStatement && "Cassandra".equals(paramTable.getDbId())) {
        Index index1 = paramTable.createIndex("partition_" + paramTable.getName());
        index1.setType(IndexType.PARTITION);
        for (MatcherCommaPhrase matcherCommaPhrase1 : matcherCommaPhrase.get(0)) {
          DDLParserUtil.a(matcherCommaPhrase1, index);
          DDLParserUtil.a(matcherCommaPhrase1, index1);
        } 
        continue;
      } 
      DDLParserUtil.a(matcherCommaPhrase, index);
    } 
    index.setSpecificationOptions(paramString2);
    index.setOptions(paramString3);
    return index;
  }
  
  private Column a(Table paramTable, String paramString1, String paramString2, MatcherStatement paramMatcherStatement) {
    if (paramTable == null || paramMatcherStatement == null || paramString2 == null)
      throw new MatcherException("Parse Error PCD03."); 
    Column column = paramTable.getColumnByNameOrPath(paramString2);
    column.setDefaultValue(paramMatcherStatement.j());
    return column;
  }
  
  private Index a(Table paramTable, String paramString, IndexType paramIndexType, MatcherCommaPhrase paramMatcherCommaPhrase) {
    if (paramTable == null || paramMatcherCommaPhrase == null)
      throw new MatcherException("Parse Error PI01."); 
    Index index = paramTable.createIndex(paramString);
    index.setType(paramIndexType);
    DDLParserUtil.a(paramMatcherCommaPhrase, index);
    return index;
  }
  
  private Constraint a(Table paramTable, String paramString1, String paramString2, MatcherStatement paramMatcherStatement, String paramString3) {
    if (paramTable == null || paramString1 == null || paramMatcherStatement == null)
      throw new MatcherException("Parse Error PCK01."); 
    Constraint constraint = paramTable.createConstraint(paramString1);
    constraint.setType(paramString2);
    constraint.setText(paramMatcherStatement.j());
    constraint.setOptions(paramString3);
    return constraint;
  }
  
  private AbstractUnit b(Table paramTable, String paramString) {
    AbstractUnit abstractUnit = null;
    if (paramTable == null || paramString == null) {
      this.f.println("Parse Error PCN01.");
    } else {
      abstractUnit = paramTable.constraints.getByName(paramString);
      if (abstractUnit == null)
        abstractUnit = paramTable.indexes.getByName(paramString); 
      if (abstractUnit == null)
        abstractUnit = paramTable.foreignKeys.getByName(paramString); 
      if (abstractUnit == null)
        abstractUnit = this.h.a(paramTable, paramString); 
    } 
    if (abstractUnit == null)
      throw new MatcherException("Cannot find constraint '" + paramString + "' on table '" + String.valueOf(paramTable) + "'"); 
    return abstractUnit;
  }
  
  private Index a(MatcherCommaPhrase paramMatcherCommaPhrase, Table paramTable, Column paramColumn, String paramString, IndexType paramIndexType) {
    Index index = paramTable.createIndex(paramString);
    index.setType(paramIndexType);
    index.addColumn(paramColumn);
    if (paramIndexType == IndexType.PRIMARY_KEY)
      paramColumn.setMandatory(true); 
    paramMatcherCommaPhrase.c("CLUSTERED", paramPatternPhrase -> {
          paramIndex.setOptions("CLUSTERED");
          return paramIndex;
        });
    return index;
  }
  
  private ForeignKeyCollector$InternalFk a(Table paramTable, String paramString, MatcherCommaPhrase paramMatcherCommaPhrase1, MatcherStatement paramMatcherStatement1, MatcherStatement paramMatcherStatement2, MatcherCommaPhrase paramMatcherCommaPhrase2) {
    Table table = d(paramMatcherCommaPhrase1);
    if (paramTable == null || paramMatcherStatement1 == null || paramMatcherStatement2 == null)
      throw new MatcherException("Parse Error PFK01."); 
    ForeignKeyCollector$InternalFk foreignKeyCollector$InternalFk = this.h.a(paramString, paramTable);
    foreignKeyCollector$InternalFk.setTargetEntity(table);
    for (byte b = 0; b < paramMatcherStatement1.size() && b < paramMatcherStatement2.size(); b++)
      foreignKeyCollector$InternalFk.a(((MatcherNode)((MatcherCommaPhrase)paramMatcherStatement1.get(b)).get(0)).d(), ((MatcherNode)((MatcherCommaPhrase)paramMatcherStatement2.get(b)).get(0)).d()); 
    if (paramMatcherCommaPhrase2 != null)
      a(paramMatcherCommaPhrase2, foreignKeyCollector$InternalFk); 
    return foreignKeyCollector$InternalFk;
  }
  
  private ForeignKeyCollector$InternalFk a(Table paramTable, MatcherCommaPhrase paramMatcherCommaPhrase1, Column paramColumn, MatcherStatement paramMatcherStatement, MatcherCommaPhrase paramMatcherCommaPhrase2) {
    Table table = c(paramMatcherCommaPhrase1);
    if (paramTable == null || table == null || paramColumn == null || paramMatcherStatement == null || paramMatcherStatement.isEmpty())
      throw new MatcherException("Parse Error PFK02."); 
    ForeignKeyCollector$InternalFk foreignKeyCollector$InternalFk = this.h.a(this.g.toDefaultCases("Fk"), paramTable);
    foreignKeyCollector$InternalFk.setTargetEntity(table);
    foreignKeyCollector$InternalFk.a(paramColumn.getName(), ((MatcherNode)((MatcherCommaPhrase)paramMatcherStatement.get(0)).get(0)).d());
    a(paramMatcherCommaPhrase2, foreignKeyCollector$InternalFk);
    return foreignKeyCollector$InternalFk;
  }
  
  private ForeignKeyCollector$InternalFk a(Table paramTable, MatcherCommaPhrase paramMatcherCommaPhrase, Column paramColumn) {
    Table table = c(paramMatcherCommaPhrase);
    if (paramTable == null || table == null || paramColumn == null)
      throw new MatcherException("Parse Error PFK02."); 
    ForeignKeyCollector$InternalFk foreignKeyCollector$InternalFk = this.h.a(this.g.toDefaultCases("Fk"), paramTable);
    foreignKeyCollector$InternalFk.setTargetEntity(table);
    foreignKeyCollector$InternalFk.a(paramColumn.getName(), paramColumn.getName());
    return foreignKeyCollector$InternalFk;
  }
  
  private void a(MatcherCommaPhrase paramMatcherCommaPhrase, ForeignKey paramForeignKey) {
    paramMatcherCommaPhrase.c("ON DELETE CASCADE", paramPatternPhrase -> {
          paramForeignKey.setDeleteAction(ForeignKeyAction.cascade);
          return paramForeignKey;
        });
    paramMatcherCommaPhrase.c("ON UPDATE CASCADE", paramPatternPhrase -> {
          paramForeignKey.setUpdateAction(ForeignKeyAction.cascade);
          return paramForeignKey;
        });
    paramMatcherCommaPhrase.c("ON DELETE SET NULL", paramPatternPhrase -> {
          paramForeignKey.setDeleteAction(ForeignKeyAction.setNull);
          return paramForeignKey;
        });
    paramMatcherCommaPhrase.c("ON UPDATE SET NULL", paramPatternPhrase -> {
          paramForeignKey.setUpdateAction(ForeignKeyAction.setNull);
          return paramForeignKey;
        });
    paramMatcherCommaPhrase.c("ON DELETE RESTRICT", paramPatternPhrase -> {
          paramForeignKey.setDeleteAction(ForeignKeyAction.restrict);
          return paramForeignKey;
        });
    paramMatcherCommaPhrase.c("ON UPDATE RESTRICT", paramPatternPhrase -> {
          paramForeignKey.setUpdateAction(ForeignKeyAction.restrict);
          return paramForeignKey;
        });
    paramForeignKey.setOptions(paramMatcherCommaPhrase.h());
  }
  
  private Object a(MatcherStatement paramMatcherStatement) {
    Column column;
    String str = null;
    Schema schema = null;
    for (MatcherCommaPhrase matcherCommaPhrase : paramMatcherStatement) {
      AbstractTable abstractTable;
      PatternPhrase patternPhrase = matcherCommaPhrase.b("@value=[N]?");
      if (patternPhrase != null)
        str = patternPhrase.f(1); 
      if ((patternPhrase = matcherCommaPhrase.b("@level0name=[N]?")) != null)
        schema = this.c.getSchemaInUseCatalog(DDLParserUtil.a(patternPhrase.f(1))); 
      if ((patternPhrase = matcherCommaPhrase.b("@level1name=[N]?")) != null && schema instanceof Schema)
        abstractTable = schema.getTableOrViewOrMatViewOrUDT(DDLParserUtil.a(patternPhrase.f(1))); 
      if ((patternPhrase = matcherCommaPhrase.b("@level2name=[N]?")) != null && abstractTable instanceof AbstractTable)
        column = abstractTable.getColumnByNameOrPath(DDLParserUtil.a(patternPhrase.f(1))); 
    } 
    if (column == null) {
      String str1 = null, str2 = null, str3 = null;
      for (byte b = 1; b < paramMatcherStatement.size(); b++) {
        if (((MatcherCommaPhrase)paramMatcherStatement.get(b - 1)).e("ms_description"))
          str = ((MatcherCommaPhrase)paramMatcherStatement.get(b)).j(); 
        if ("schema".equalsIgnoreCase(((MatcherCommaPhrase)paramMatcherStatement.get(b - 1)).j()))
          str1 = ((MatcherCommaPhrase)paramMatcherStatement.get(b)).j(); 
        if ("user".equalsIgnoreCase(((MatcherCommaPhrase)paramMatcherStatement.get(b - 1)).j()))
          str1 = ((MatcherCommaPhrase)paramMatcherStatement.get(b)).j(); 
        if ("table".equalsIgnoreCase(((MatcherCommaPhrase)paramMatcherStatement.get(b - 1)).j()))
          str2 = ((MatcherCommaPhrase)paramMatcherStatement.get(b)).j(); 
        if ("column".equalsIgnoreCase(((MatcherCommaPhrase)paramMatcherStatement.get(b - 1)).j()))
          str3 = ((MatcherCommaPhrase)paramMatcherStatement.get(b)).j(); 
      } 
      if (StringUtil.isFilledTrim(str1)) {
        Schema schema1 = this.c.getSchema(null, str1);
        if (schema1 == null)
          schema1 = a(); 
        Table table = null;
        Column column1 = null;
        if (schema1 != null && StringUtil.isFilledTrim(str2)) {
          table = schema1.getTable(str2);
          if (table != null && StringUtil.isFilledTrim(str3))
            column1 = table.getColumnByNameOrPath(str3); 
        } 
        column = (column1 != null) ? column1 : ((table != null) ? (Column)table : (Column)schema1);
      } 
    } 
    if (column != null)
      column.setComment(StringUtil.unescapeComment(str)); 
    return column;
  }
  
  public Function parseFunction(MatcherStatement paramMatcherStatement, String paramString, MatcherCommaPhrase paramMatcherCommaPhrase) {
    Function function = (Function)a(paramMatcherCommaPhrase, Function.class);
    function.setText(paramString);
    return function;
  }
  
  public Procedure parseProcedure(MatcherStatement paramMatcherStatement, String paramString, MatcherCommaPhrase paramMatcherCommaPhrase) {
    Procedure procedure = (Procedure)a(paramMatcherCommaPhrase, Procedure.class);
    procedure.setText(paramString);
    return procedure;
  }
  
  public Trigger parseTrigger(MatcherStatement paramMatcherStatement, String paramString, MatcherCommaPhrase paramMatcherCommaPhrase) {
    Trigger trigger = (Trigger)a(paramMatcherCommaPhrase, Trigger.class);
    trigger.setText(paramString);
    return trigger;
  }
  
  private AbstractUnit a(MatcherCommaPhrase paramMatcherCommaPhrase, Class paramClass) {
    AbstractUnit abstractUnit = null;
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.d("?.?.?")) != null) {
      Schema schema = this.c.getSchema(patternPhrase.f(0), patternPhrase.f(1));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(1), patternPhrase.f(0)); 
      abstractUnit = schema.getOrCreate(patternPhrase.f(2), paramClass);
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?.?")) != null) {
      Schema schema = this.c.getSchema(null, patternPhrase.f(0));
      if (schema == null)
        schema = this.c.createSchema(patternPhrase.f(0)); 
      abstractUnit = schema.getOrCreate(patternPhrase.f(1), paramClass);
    } else if ((patternPhrase = paramMatcherCommaPhrase.d("?")) != null) {
      abstractUnit = a().getOrCreate(patternPhrase.f(0), paramClass);
    } 
    if (abstractUnit == null)
      this.f.println("Parse Error PSN01."); 
    return abstractUnit;
  }
  
  private String h(MatcherCommaPhrase paramMatcherCommaPhrase) {
    PatternPhrase patternPhrase;
    if ((patternPhrase = paramMatcherCommaPhrase.b("?.?.?")) != null)
      return patternPhrase.f(0) + "." + patternPhrase.f(0) + "." + patternPhrase.f(1); 
    if ((patternPhrase = paramMatcherCommaPhrase.b("?.?")) != null)
      return patternPhrase.f(0) + "." + patternPhrase.f(0); 
    if ((patternPhrase = paramMatcherCommaPhrase.b("?")) != null)
      return patternPhrase.f(0); 
    return null;
  }
}
