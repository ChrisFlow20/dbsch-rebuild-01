package com.wisecoders.dbs.data.groovy;

import com.wisecoders.dbs.data.model.data.GroovyScriptBaseClass;
import com.wisecoders.dbs.data.model.data.ObjectId;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.sys.EscapeChars;
import java.io.File;
import java.util.List;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

public class GroovyConfig extends CompilerConfiguration {
  public GroovyConfig(File paramFile) {
    setScriptBaseClass(GroovyScriptBaseClass.class.getName());
    ImportCustomizer importCustomizer = new ImportCustomizer();
    importCustomizer.addImports(new String[] { EscapeChars.class
          .getName(), ObjectId.class
          
          .getName(), SyncFilter.class
          
          .getName(), SyncPair.class
          .getName(), Dbms.class
          .getName() });
    addCompilationCustomizers(new CompilationCustomizer[] { (CompilationCustomizer)importCustomizer });
    setVerbose(true);
    if (paramFile != null) {
      List<String> list = getClasspath();
      list.add(paramFile.getParent());
      setClasspathList(list);
    } 
  }
}
