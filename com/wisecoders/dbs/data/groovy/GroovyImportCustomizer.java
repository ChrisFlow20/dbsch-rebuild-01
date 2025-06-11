package com.wisecoders.dbs.data.groovy;

import com.wisecoders.dbs.data.model.data.ObjectId;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.sys.EscapeChars;
import groovy.sql.Sql;
import java.sql.Connection;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

public class GroovyImportCustomizer extends ImportCustomizer {
  public GroovyImportCustomizer() {
    addImports(new String[] { Connection.class
          .getName(), EscapeChars.class
          .getName(), ObjectId.class
          
          .getName(), SyncFilter.class
          
          .getName(), SyncPair.class
          .getName(), Sql.class
          .getName(), Dbms.class
          .getName() });
    addStarImports(new String[] { "com.wisecoders.dbs.schema", "com.wisecoders.dbs.diagram.model" });
  }
}
