package com.wisecoders.dbs.data.task;

import com.wisecoders.dbs.data.groovy.GroovyConfig;
import com.wisecoders.dbs.dbms.driver.model.DriverJarClass;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sql.fx.FxSqlEditor;
import com.wisecoders.dbs.sys.Log;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.concurrent.Task;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.syntax.SyntaxException;

public class FxGroovySyntaxCheckTask extends Task {
  private final StyledEditor a;
  
  private final Workspace b;
  
  public FxGroovySyntaxCheckTask(Workspace paramWorkspace, StyledEditor paramStyledEditor) {
    this.b = paramWorkspace;
    this.a = paramStyledEditor;
    paramWorkspace.getRx().a(this);
  }
  
  protected String a() {
    Object object = null;
    Binding binding = new Binding();
    binding.setVariable("sql", object);
    try {
      ClassLoader classLoader = FxSqlEditor.class.getClassLoader();
      if (this.b != null && this.b.r()) {
        Connector connector = this.b.s();
        DriverJarClass driverJarClass = DriverManager.a(connector.dbId).a(connector);
        if (driverJarClass != null && driverJarClass.e() != null)
          classLoader = driverJarClass.e().getClass().getClassLoader(); 
      } 
      GroovyShell groovyShell = new GroovyShell(classLoader, binding, new GroovyConfig(this.b.m().getFile()));
      groovyShell.parse(this.a.t());
    } catch (MultipleCompilationErrorsException multipleCompilationErrorsException) {
      FxGroovyScriptTask.a(multipleCompilationErrorsException);
    } catch (RuntimeException runtimeException) {
      throw new Exception(runtimeException.toString());
    } 
    return "Done";
  }
  
  protected void succeeded() {
    this.a.w();
  }
  
  protected void failed() {
    Throwable throwable = getException();
    this.a.w();
    if (throwable instanceof SyntaxException) {
      SyntaxException syntaxException = (SyntaxException)throwable;
      if (syntaxException.getStartLine() > 0 && syntaxException.getStartColumn() > -1)
        this.a.a(syntaxException
            .getStartLine() - 1, syntaxException
            .getStartColumn() - 1, syntaxException.getEndColumn() - 1, syntaxException.getLocalizedMessage(), false); 
    } 
    Log.f(throwable.toString());
  }
}
