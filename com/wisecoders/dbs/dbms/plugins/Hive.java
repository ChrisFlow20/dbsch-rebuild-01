package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.parser.DDLParserUtil;
import com.wisecoders.dbs.sql.parser.MatcherCommaPhrase;
import com.wisecoders.dbs.sql.parser.MatcherStatement;
import com.wisecoders.dbs.sql.parser.PatternPhrase;
import java.util.Iterator;

public class Hive extends Dbms {
  public static final String a = "Hive";
  
  public Hive() {
    super("Hive");
  }
  
  public Hive(String paramString) {
    super(paramString);
  }
  
  public void listFunctionNames(StructureImporter paramStructureImporter, Schema paramSchema) {}
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    Dbms dbms = Dbms.get("Hive");
    for (Iterator<E> iterator = paramSchema.tables.iterator(); iterator.hasNext(); ) {
      Table table = (Table)iterator.next();
      Result result = new Result();
      paramImporter.d.b("SHOW INDEXES ON " + dbms.quote(table.getName()), result);
      result.q();
      for (byte b = 0; b < result.A(); b++) {
        String str3 = (String)result.a(b, 0);
        String str4 = (String)result.a(b, 2);
        if (str3 != null && str4 != null) {
          Index index = table.createIndex(str3.trim());
          for (String str : str4.split(",")) {
            Column column = table.getColumnByNameOrPath(str.trim());
            if (column != null)
              index.addColumn(column); 
          } 
        } 
      } 
      String str1 = getResultAsString(paramImporter, "show create table " + dbms.quote(table.getName()));
      MatcherStatement matcherStatement = new MatcherStatement(str1, this.dbId);
      matcherStatement.a("PARTITIONED BY ~", paramPatternPhrase -> {
            Index index = paramTable.createIndex("Partition");
            index.setType(IndexType.PARTITION);
            for (MatcherCommaPhrase matcherCommaPhrase : paramPatternPhrase.e(0)) {
              Column column = DDLParserUtil.a(matcherCommaPhrase, paramTable);
              if (column != null)
                index.addColumn(column); 
            } 
            return index;
          });
      matcherStatement.a("CLUSTERED BY ~", paramPatternPhrase -> {
            Index index = paramTable.createIndex("Cluster");
            index.setType(IndexType.CLUSTER);
            for (MatcherCommaPhrase matcherCommaPhrase : paramPatternPhrase.e(0))
              DDLParserUtil.a(matcherCommaPhrase, index); 
            return index;
          });
      matcherStatement.a("SORTED BY ~ [INTO ? BUCKETS]", paramPatternPhrase -> {
            Index index = paramTable.createIndex("Sort");
            index.setType(IndexType.SORT);
            for (MatcherCommaPhrase matcherCommaPhrase : paramPatternPhrase.e(0))
              DDLParserUtil.a(matcherCommaPhrase, index); 
            if (paramPatternPhrase.f(2) != null)
              index.setOptions("INTO " + paramPatternPhrase.f(2) + " BUCKETS"); 
            return index;
          });
      matcherStatement.a("SKEWED BY ~ [ON ~]", paramPatternPhrase -> {
            Index index = paramTable.createIndex("Skew");
            index.setType(IndexType.INDEX1);
            for (MatcherCommaPhrase matcherCommaPhrase : paramPatternPhrase.e(0))
              DDLParserUtil.a(matcherCommaPhrase, index); 
            if (paramPatternPhrase.f(2) != null)
              index.setOptions("ON " + paramPatternPhrase.f(2)); 
            return index;
          });
      String str2 = matcherStatement.a("ROW FORMAT SERDE ?\nSTORED AS INPUTFORMAT ?\nOUTPUTFORMAT ?\nLOCATION ?\nTBLPROPERTIES ?");
      table.setOptions(str2);
    } 
  }
}
