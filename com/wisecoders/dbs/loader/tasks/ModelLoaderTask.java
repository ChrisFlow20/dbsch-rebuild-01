package com.wisecoders.dbs.loader.tasks;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.loader.engine.AbstractLoader;
import com.wisecoders.dbs.loader.engine.LoaderConsumer;
import com.wisecoders.dbs.loader.model.LoaderMeta;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParserUtil;
import com.wisecoders.dbs.sql.parser.MatcherCommaPhrase;
import com.wisecoders.dbs.sql.parser.MatcherNode;
import com.wisecoders.dbs.sql.parser.MatcherStatement;
import java.util.Map;
import javafx.concurrent.Task;

public class ModelLoaderTask extends Task implements LoaderConsumer {
  private final AbstractLoader a;
  
  private final LoaderMeta b;
  
  private Schema c;
  
  private Table d;
  
  private Column e;
  
  protected ModelLoaderTask(LoaderMeta paramLoaderMeta, AbstractLoader paramAbstractLoader) {
    this.b = paramLoaderMeta;
    this.a = paramAbstractLoader;
  }
  
  protected Void a() {
    updateMessage("Load model...");
    this.a.addConsumer(this);
    this.a.parse();
    return null;
  }
  
  public void consumeRecord(Map paramMap) {
    Project project = this.b.d.schema.project;
    String str2 = this.b.a(paramMap, "schemaName");
    if (str2 != null) {
      String str = this.b.a(paramMap, "catalogName");
      this.c = project.getSchema(str, str2);
      if (this.c == null) {
        this.c = project.createSchema(str2, str);
        this.d = null;
        this.e = null;
      } 
    } 
    if (this.c == null && !project.schemas.isEmpty()) {
      this.c = (Schema)project.schemas.get(project.schemas.size() - 1);
      this.d = null;
      this.e = null;
    } 
    String str1;
    if (this.c != null && (str1 = this.b.a(paramMap, "schemaComment")) != null)
      this.c.setComment(str1); 
    String str3 = this.b.a(paramMap, "tableName");
    if (str3 != null && this.c != null) {
      this.d = this.c.getOrCreateTable(str3);
      this.e = null;
    } 
    if (this.d != null) {
      if ((str1 = this.b.a(paramMap, "tableComment")) != null)
        this.d.setComment(str1); 
      if ((str1 = this.b.a(paramMap, "tableOptions")) != null)
        this.d.setOptions(str1); 
    } 
    String str4 = this.b.a(paramMap, "columnName");
    if (str4 != null && this.d != null) {
      this.e = (Column)this.d.getAttributes().getByName(str4);
      if (this.e == null)
        this.e = this.d.createColumn(str4, DbmsTypes.get(project.getDbId()).getDataType(12)); 
    } 
    String str5 = this.b.a(paramMap, "columnType");
    if (str5 != null && this.e != null) {
      MatcherStatement matcherStatement = new MatcherStatement(str5, project.getDbId());
      if (!matcherStatement.isEmpty())
        this.e.setDataType(DbmsTypes.get(project.getDbId()).getType(((MatcherCommaPhrase)matcherStatement.get(0)).toString())); 
      if (matcherStatement.size() > 1) {
        MatcherCommaPhrase matcherCommaPhrase = (MatcherCommaPhrase)matcherStatement.get(0);
        if (!matcherCommaPhrase.isEmpty() && DDLParserUtil.a((MatcherNode)matcherCommaPhrase.get(0)))
          this.e.setLength(Integer.parseInt(((MatcherNode)matcherCommaPhrase.get(0)).c())); 
        if (matcherCommaPhrase.size() > 1 && DDLParserUtil.a((MatcherNode)matcherCommaPhrase.get(1)))
          this.e.setDecimal(Integer.parseInt(((MatcherNode)matcherCommaPhrase.get(1)).c())); 
      } 
    } 
    if (this.e != null) {
      if ((str1 = this.b.a(paramMap, "columnLength")) != null)
        try {
          this.e.setLength(Integer.parseInt(str1));
        } catch (NumberFormatException numberFormatException) {} 
      if ((str1 = this.b.a(paramMap, "columnDecimal")) != null)
        try {
          this.e.setDecimal(Integer.parseInt(str1));
        } catch (NumberFormatException numberFormatException) {} 
      if ((str1 = this.b.a(paramMap, "columnDefault")) != null)
        this.e.setDefaultValue(str1); 
      if ((str1 = this.b.a(paramMap, "columnMandatory")) != null)
        this.e.setMandatory(("true".equalsIgnoreCase(str1) || "y".equalsIgnoreCase(str1) || "yes".equalsIgnoreCase(str1) || "1".equalsIgnoreCase(str1))); 
      if ((str1 = this.b.a(paramMap, "columnNullable")) != null)
        this.e.setMandatory((!"true".equalsIgnoreCase(str1) && !"y".equalsIgnoreCase(str1) && !"yes".equalsIgnoreCase(str1) && !"1".equalsIgnoreCase(str1))); 
      if ((str1 = this.b.a(paramMap, "columnOptions")) != null)
        this.e.setOptions(str1); 
      if ((str1 = this.b.a(paramMap, "columnComment")) != null)
        this.e.setComment(str1); 
    } 
  }
}
