package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import groovy.lang.Binding;
import groovy.lang.Script;

public class GroovyImporter {
  public GroovyImporter(Importer paramImporter, Schema paramSchema) {
    String str = (Dbms.get(paramSchema.project.getDbId())).groovyCustomCode.c_();
    if (StringUtil.isFilledTrim(str)) {
      Script script;
      try {
        Binding binding = new Binding();
        binding.setVariable("sql", paramImporter.d.d());
        binding.setVariable("schema", paramSchema);
        script = Groovy.a.b(binding, str);
      } catch (Throwable throwable) {
        Log.f("Error parsing Groovy code from Edit/Database Settings/Reverse Engineer/Groovy. " + String.valueOf(throwable));
        return;
      } 
      try {
        script.run();
      } catch (Throwable throwable) {
        try {
          paramImporter.d.q();
        } catch (Exception exception) {
          Log.b(exception);
        } 
        Log.f("Error executing Groovy code from Edit/Database Settings/Reverse Engineer/Groovy. " + String.valueOf(throwable));
      } 
    } 
  }
}
